# 496. Next Greater Element I — How & Why

## Problem

- Given two arrays `nums1` and `nums2` where all elements are distinct and `nums1` is a subset of `nums2`, for each element in `nums1` find the next element in `nums2` that is greater and to its right; if none exists, return `-1`.

## Approach: Monotonic Stack + Map (Optimal)

- Build a mapping `nextGreater[value] = next greater in nums2 or -1` by scanning `nums2` once with a monotonic decreasing stack.
- Then construct the answer for `nums1` by looking up the map.

### Why it works

- The stack keeps a decreasing sequence of values seen so far. When a new value `x` is greater than the stack top, `x` is the next greater for that top, so we pop and record the mapping. Repeat while `x` is greater than the new top.
- Each value is pushed once and popped at most once → linear time.

### Complexity

- Time: O(n + m), where `n = len(nums2)` and `m = len(nums1)`.
- Space: O(n) for the map and stack.

### Pseudocode

```java
stack = []           // holds values from nums2 in decreasing order
nextGreater = {}     // value -> next greater value

for v in nums2:
    while stack not empty and v > stack.top():
        nextGreater[stack.pop()] = v
    stack.push(v)

while stack not empty:
    nextGreater[stack.pop()] = -1

ans = [ nextGreater[v] for v in nums1 ]
```

## Complete Example Walkthrough

### Input

- `nums1 = [4, 1, 2]`
- `nums2 = [1, 3, 4, 2]`

### Step-by-step over `nums2`

- Start: `stack = []`, `nextGreater = {}`

1) See `1`
   - stack empty → push `1`
   - `stack = [1]`

2) See `3`
   - `3 > 1` → pop `1`, record `nextGreater[1] = 3`
   - stack empty → push `3`
   - `stack = [3]`, `nextGreater = {1: 3}`

3) See `4`
   - `4 > 3` → pop `3`, record `nextGreater[3] = 4`
   - stack empty → push `4`
   - `stack = [4]`, `nextGreater = {1: 3, 3: 4}`

4) See `2`
   - `2 > 4`? No → push `2`
   - `stack = [4, 2]`

End of array: assign `-1` to all remaining in stack

- Pop `2` → `nextGreater[2] = -1`
- Pop `4` → `nextGreater[4] = -1`

Final map:

- `{1: 3, 3: 4, 2: -1, 4: -1}`

### Build result for `nums1`

- `4 → -1`
- `1 → 3`
- `2 → -1`

Answer: `[-1, 3, -1]`

## Alternate Approach: Brute Force (Simple but Slower)

- For each `x` in `nums1`, linearly search its index in `nums2`, then scan to the right until you find a value greater than `x` or finish.
- Time: O(n*m) in the worst case; easy to implement but not optimal.

## Notes & Edge Cases

- Distinct elements: LeetCode 496 guarantees distinct values, which lets us use values as keys directly. If values could repeat, store indices in the stack and map by index.
- Empty arrays: Handle `nums1` or `nums2` empty → result is empty or all `-1` respectively.

## Java Snippet (for reference)

```java
public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    java.util.Map<Integer, Integer> next = new java.util.HashMap<>();
    java.util.Deque<Integer> st = new java.util.ArrayDeque<>();

    for (int v : nums2) {
        while (!st.isEmpty() && v > st.peek()) {
            next.put(st.pop(), v);
        }
        st.push(v);
    }
    while (!st.isEmpty()) next.put(st.pop(), -1);

    int[] res = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) res[i] = next.get(nums1[i]);
    return res;
}
```
