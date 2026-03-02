# How Why Explanation - 1536. Minimum Swaps to Arrange a Binary Grid

## Problem

You have an `n x n` binary grid. In one operation you may swap any two adjacent rows. Arrange the grid so that for every row `i` (0-indexed from top), the number of trailing zeros in that row is at least `n - i - 1`. Return the minimum swaps, or `-1` if impossible.

## Intuition

Row order is the only thing that matters. The requirement depends solely on each row's trailing zeros. So compute trailing zeros per row, then greedily pull up the first row that satisfies the current need; swapping adjacent rows bubbles that row into place with minimal extra moves.

## Approach (greedy bubble-up)

1. For each row, count trailing zeros; store in `zeros[]`.
2. For position `i` (top to bottom), we need `needed = n - i - 1` trailing zeros.
3. Find the first row `j >= i` with `zeros[j] >= needed`. If none, return `-1`.
4. Swap-adjacent to bring row `j` up to `i` (cost `j - i`), shifting intervening entries down. Update swaps.
5. Continue until all rows placed. Implementation in [1536. Minimum Swaps to Arrange a Binary Grid/Solution.java](1536.%20Minimum%20Swaps%20to%20Arrange%20a%20Binary%20Grid/Solution.java#L4-L30).

## Complexity

- Time: O(n^2) in worst case (search + bubbling per row) for the first implementation; still fine for `n <= 200`.
- Space: O(n) for trailing zeros.

## Edge Cases

- A row of all zeros has `n` trailing zeros (always usable).
- Already valid grid → swaps = 0.
- No row can satisfy a required `needed` → return `-1` early.

## Alternate Approaches

- **Same greedy, cleaner helpers:** The second version factors trailing-zero counting and search into helpers; logic identical, still O(n^2). See [Solution2](1536.%20Minimum%20Swaps%20to%20Arrange%20a%20Binary%20Grid/Solution.java#L32-L75).
- **Selection-stable view:** Conceptually, this is stable selection where each pick costs distance; any BFS/DP over permutations is unnecessary because greedy is optimal: pulling the earliest feasible row minimizes current cost and doesn't harm future needs (which are weaker).
