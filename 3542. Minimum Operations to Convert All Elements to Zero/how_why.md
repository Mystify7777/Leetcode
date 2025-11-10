# 3542. Minimum Operations to Convert All Elements to Zero

## Recap

You have an array `nums` of non‑negative integers. In one operation you choose a subarray `[l, r]` and set **all occurrences of the minimum value in that subarray** to `0`. Repeat until the whole array is zero. Return the minimum number of operations.

## Key Observation

Zeros act like separators: any subarray you choose cannot bridge over a zero to reduce a value on both sides simultaneously (because zero is the minimum and would be the only value eliminated). Thus the process decomposes over stretches of strictly positive numbers delimited by zeros. Inside a positive stretch, picking a subarray and removing all of its minimum value(s) can be viewed as peeling the current lowest “layer” of that stretch. This is analogous to repeatedly removing plateaus from a skyline from bottom to top.

Therefore the number of operations equals the count of distinct positive “height levels” encountered when scanning each positive segment from left to right while maintaining a non‑decreasing stack of active heights. Each new height that isn’t already present and isn’t overshadowed by a smaller upcoming height contributes one operation.

## Intuition (Monotonic Stack)

Consider a positive segment. Suppose we list the heights encountered left to right. Whenever we “start seeing” a new value `v` that was not active before (i.e. it was not on our monotonic increasing stack), we will eventually need an operation dedicated to value `v` (some subarray where `v` is the minimum after all its smaller values have been removed). If later we see a smaller value `< v`, then `v` can never again be the minimum beyond that point without first that smaller value being removed. So the presence of a smaller value “invalidates” the larger values to its left for future consideration, and they can be popped.

We can thus simulate the layering: maintain a strictly increasing stack of currently “open” distinct values for the current positive run. When a zero boundary is hit, all active values are flushed because a new run begins isolated from the previous.

## Provided Implementation Walkthrough

```java
// Pseudocode sketch (NOT exact Java code):
int ans = 0;                       // total operations
boolean[] seen = new boolean[MAX]; // is value currently active in stack
int[] stack = new int[n];
int size = 0;                      // stack size

for each value curr in nums:
  if (curr == 0) {
    // boundary between segments -> clear active values
    while (size > 0) {
      seen[ stack[--size] ] = false;
    }
    continue;
  }
  while (size > 0 && stack[size - 1] > curr) {
    // larger values cannot stay active after seeing smaller curr
    seen[ stack[--size] ] = false;
  }
  if (!seen[curr]) {
    ans++;
    seen[curr] = true;
  }
  stack[size++] = curr;
return ans;
```

### Why `seen`?

If the same height reappears contiguously or after popping larger heights (without being separated by a zero), we should not count a new operation—its minimal “layer” has already been accounted for and its instances can be merged into the same eventual subarray choice (since a subarray covering all occurrences of that minimum can be extended left/right across equal values without cost). The boolean array lets us tell whether a height is already currently active on the monotonic stack.

### Why clear on zero?

Zeros are already eliminated. They form hard boundaries: a future operation’s subarray cannot cross a zero to cover values on both sides while having a positive minimum. So heights on the left side cannot merge with heights on the right side into a single operation. Clearing ensures counting restarts per segment.

## Correctness Argument

1. (Lower Bound) Each distinct positive height that becomes “exposed” as the minimum within its segment at some stage requires at least one operation dedicated to removing all its occurrences present at that time.
2. (Exposure Criterion) A height becomes exposed exactly when we first encounter it while all smaller heights that would appear to its left (or appear later but before a zero) are either absent or will be removed earlier; enforcing a strictly increasing stack ensures we remember only the ascending chain of active candidate minima.
3. (No Overcount) A height already on the stack will be part of a previously counted layer; further occurrences merge into the same subarray (we can widen that subarray when we perform its operation).
4. (Completeness) Every counted new height corresponds to a necessary operation: we can schedule operations in ascending order of the first-seen time per height within each segment, choosing subarrays that span all contiguous occurrences after prior smaller layers are removed.
5. (Segment Independence) Zero boundaries disconnect influence; operations cannot cross them. Summing segments is optimal.

## Complexity

- Let `n = nums.length`.
- Each element is pushed at most once and popped at most once from the stack: `O(n)` time.
- Auxiliary arrays: `O(n)` for stack plus `O(U)` for `seen` where `U <= 1e5` (value cap). Given constraints, this is acceptable. If values were large, we could use a hash set.
- Space: `O(n + U)` worst case, effectively `O(n)` under constraints.

## Edge Cases

- All zeros: answer is `0` (loop never increments `ans`).
- Strictly increasing positives: every value is new; answer = number of distinct values.
- Strictly decreasing positives: stack repeatedly pops; answer = number of distinct values (each first appearance is counted, pops do not decrement).
- Repeated plateau (e.g. `[5,5,5]`): counted once.
- Interleaving zeros (e.g. `[2,0,2]`): counted twice because segments are independent.

## Alternative Simplified Stack Variant

The commented alternate solution uses a sentinel at index 0 and counts pops directly, returning `ans + top` at the end (remaining stack heights each need one operation). This avoids an explicit `seen` array by treating:

- Each time we pop a higher value due to a smaller incoming element we finalize an operation for that popped height.
- Residual stack heights after processing need one operation each.

Pseudo-version of that idea:

```java
int[] stack = new int[n + 1]; // stack[0] = 0 sentinel
int top = 0, ops = 0;
for (int v : nums) {
    if (v == 0) { // boundary: finalize all active heights
        ops += top;
        top = 0;
        continue;
    }
    while (top > 0 && stack[top] > v) { // each pop finalizes one height
        top--; ops++;
    }
    if (top == 0 || stack[top] != v) stack[++top] = v; // push new distinct height
}
ops += top; // finalize leftover heights
return ops;
```

## Why Both Approaches Yield the Same Count

In the boolean+stack version we increment when a new height first becomes active within the current segment. In the pop-counting variant we increment on finalization (pop) and then add outstanding heights at segment end. Algebraically, “count on push” vs “count on pop + leftovers” are dual perspectives of the same partition of heights (each height contributes exactly once).

## Takeaways

- Transform layered elimination problems into counting distinct levels via a monotonic structure.
- Zeros (or already-eliminated markers) often partition the array, allowing independent processing.
- Counting either on activation (push of new level) or on finalization (pop) are equivalent strategies as long as every level is charged exactly once.

