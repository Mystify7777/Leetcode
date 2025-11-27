# 3190. Find Minimum Operations to Make All Elements Divisible by Three — how/why

## Recap

You are given an integer array `nums`. In one operation you may choose any element and add 1 to it. Return the minimum number of operations needed so that every element becomes divisible by 3.

## Intuition

Only the residue of each number modulo 3 matters. Adding 1 cycles residues: 0 → 1 → 2 → 0. For an element with residue:

- 0: already divisible, needs 0 operations.
- 1: needs 2 increments (1 → 2 → 0).
- 2: needs 1 increment (2 → 0).

Thus each element contributes an independent cost determined purely by its residue; there is no interaction between elements.

## Approach

1. Iterate through `nums`.
2. For each value `x`:
   * If `x % 3 == 1`, add 2 to the answer.
   * Else if `x % 3 == 2`, add 1 to the answer.
   * Else add 0.
3. Return the accumulated sum.

This can be implemented either by counting the number of residue-1 and residue-2 elements (`c1`, `c2`) and computing `2*c1 + c2`, or by a direct running sum.

## Code (Java)

```java
class Solution {
    public int minimumOperations(int[] nums) {
        int ops = 0;
        for (int x : nums) {
            int r = x % 3;
            if (r == 1) ops += 2;
            else if (r == 2) ops += 1;
        }
        return ops;
    }
}
```

## Correctness

For any integer `x` with residue `r = x % 3`, the smallest nonnegative `k` such that `(r + k) % 3 == 0` is:

* `k = 0` if `r = 0`
* `k = 2` if `r = 1`
* `k = 1` if `r = 2`

Adding 1 affects only the chosen element, so the minimal total number of operations is the sum of minimal per-element `k`. There is no benefit in over-incrementing an element (that would increase operations). Therefore summing these minimal adjustments yields the global optimum.

## Complexity

- Time: `O(n)` — single pass over the array.
- Space: `O(1)` — constant extra state.

## Edge Cases

- All elements already divisible by 3 → answer 0.
- Large values: only their residue matters; no overflow since we sum small integers.
- Negative numbers: ensure language modulo returns a nonnegative residue (Java `%` behaves so that `(-1) % 3 == -1`; adjust with `(r + 3) % 3` if negatives can appear).
- Empty array: answer 0.

## Takeaways

- Problems with operations that only affect one element often decompose into independent per-element costs.
- Cyclic residue transitions (here length 3) let you map minimal steps directly from residue.
- Counting residues first can simplify reasoning and produce a closed-form formula (`2*c1 + c2`).

## Alternative (Counting)

```python
def minimum_operations(nums):
    c1 = c2 = 0
    for x in nums:
        r = x % 3
        if r == 1:
            c1 += 1
        elif r == 2:
            c2 += 1
    return 2 * c1 + c2
```

Both implementations are equivalent; choose based on style preference.
