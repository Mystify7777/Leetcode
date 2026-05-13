# 1674. Minimum Moves to Make Array Complementary

This problem asks for the minimum number of changes needed so that every mirrored pair in the array has the same sum.

## Core idea

For each pair `(a, b)` and a chosen target sum `s`, the pair can fall into one of three ranges:

- `2 moves` if neither number can stay as-is.
- `1 move` if one of the two values can be adjusted to make the sum `s`.
- `0 moves` if `a + b == s` already.

Instead of checking every target sum separately for every pair, the solution uses a difference array to mark how the cost changes across the whole range of possible sums.

## Why the difference array works

For each mirrored pair:

- Every possible sum starts with cost `2`.
- Sums in the one-move range reduce that cost by `1`.
- The exact current sum reduces the cost by another `1`, making it `0`.

After all pairs are processed, a prefix sum over the difference array reconstructs the total cost for each target sum.

## Complexity

- Time: `O(n + limit)`
- Space: `O(limit)`
