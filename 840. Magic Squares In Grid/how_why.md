# 840. Magic Squares In Grid

## Problem

Given an `m x n` grid of integers, count how many `3 x 3` subgrids form a magic square. A magic square here means:

- All numbers are distinct and between 1 and 9 (inclusive).
- The sums of each row, each column, and both diagonals are equal.

## Approach (scan every 3x3, validate)

1. Slide a `3 x 3` window over the grid (top-left corner `(i, j)` where `0 ≤ i ≤ m-3`, `0 ≤ j ≤ n-3`).
2. For each window, validate with `isMagicSquare`:

    - **Distinct + range**: Use a boolean[10] to ensure each cell is in `[1, 9]` and no duplicates.
    - **Target sum**: Compute the first row sum; all other rows, columns, and both diagonals must match it.

3. Count windows that pass all checks.

## Why this works

- The magic square constraints are local to the `3 x 3` block, so each window can be checked independently.
- Distinctness and range `[1, 9]` are necessary for a 3x3 Lo Shu–style magic square; any violation short-circuits.
- Equal-sum rows, columns, and diagonals characterize the magic property; using the first row as the benchmark keeps checks constant-time per window.

## Example (conceptual)

For a valid block like

```m
4 9 2
3 5 7
8 1 6
```

- Distinct 1–9 ✓
- Row sums = 15, column sums = 15, diagonal sums = 15 ✓
→ counts as 1 magic square.

## Complexity

- Time: `O((m-2) * (n-2) * 9) ≈ O(mn)` because each window touches 9 cells with O(1) checks.
- Space: O(1) extra (boolean array of size 10 and counters).

## Edge considerations

- Grids smaller than 3x3 yield 0 by construction of the loops.
- Early exit in validation skips further work when range/distinctness or sum checks fail.
