# How Why Explanation - 1545. Find Kth Bit in Nth Binary String

## Problem

Define strings: `S1 = "0"`; `Sn = S(n-1) + "1" + reverse(invert(S(n-1)))`. Given `n` (1-indexed) and `k` (1-indexed), return the `k`-th bit of `Sn`.

## Intuition

`Sn` is symmetric around its middle bit (which is always `1`). The right half is the inverted reverse of the left half. So to locate `k`, compare it to the midpoint and map it into the left half recursively; if it falls in the right half, mirror it and invert.

## Approach (recursive position mapping)

- Length of `Sn` is `len = 2^n - 1`; midpoint index `mid = len / 2 + 1`.
- If `k == mid`, answer is `'1'`.
- If `k < mid`, the answer equals `findKthBit(n-1, k)` (left half).
- If `k > mid`, mirror into the left half: position `len - k + 1`, compute `findKthBit(n-1, len - k + 1)`, then invert the result.
- Base case `n == 1`: return `'0'`. Implementation in [1545. Find Kth Bit in Nth Binary String/Solution.java](1545.%20Find%20Kth%20Bit%20in%20Nth%20Binary%20String/Solution.java#L4-L22).

## Complexity

- Time: O(n) recursion depth (one level per `n`).
- Space: O(n) call stack.

## Edge Cases

- `n = 1` → `S1 = "0"`, only `k = 1` valid.
- `k` exactly at midpoint returns `'1'` without further recursion.
- Large `n` (<=20 per constraints) is fine for recursion depth.

## Alternate Approaches

- **Iterative mapping:** Loop downward from `n` while adjusting `k` relative to midpoints to avoid recursion; same O(n) time/space (O(1) stack).
- **Construct string (naive):** Build `Sn` iteratively; works for small `n` but is exponential in length and infeasible for constraints.
