# How Why Explanation - 1582. Special Positions in a Binary Matrix

## Problem

In a binary matrix `mat`, a position `(i, j)` is *special* if `mat[i][j] == 1` and all other cells in its row and column are `0`. Count the number of special positions.

## Intuition

A special 1 must be the only 1 in its row and also the only 1 in its column. We can scan row by row: if a row has exactly one 1, then check that column to ensure no other 1s.

## Approach (row scan + column check)

1. For each row `i`, find the unique column `index` containing a 1; if the row has 0 or >1 ones, skip.
2. Verify column `index` has no other 1s (besides `(i, index)`).
3. Increment answer when both conditions hold. Implementation in [1582. Special Positions in a Binary Matrix/Solution.java](1582.%20Special%20Positions%20in%20a%20Binary%20Matrix/Solution.java#L4-L35).

## Complexity

- Time: O(m * n) where `m` is rows, `n` is cols.
- Space: O(1) extra.

## Edge Cases

- All zeros → 0 special positions.
- Single row/col: any solitary 1 is special if alone in its row/col.
- Rows with multiple 1s are never contributors.

## Alternate Approaches

- **Precompute counts:** Build `rowCount[]` and `colCount[]` in one pass, then count cells where `mat[i][j] == 1 && rowCount[i] == 1 && colCount[j] == 1`; same time but clearer O(mn) with O(m+n) space.
- **Early breaks:** While scanning a row, bail as soon as a second 1 is seen (already done in `checkRow`).
