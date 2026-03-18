# 3070. Count Submatrices with Top-Left Element and Sum Less Than k - How Why Explanation

## Goal

Count submatrices whose top-left corner is at `(0,0)` and whose total sum is at most `k`.

## Idea in 3 lines

- The sum of a `(0,0) -> (i,j)` submatrix is just the 2D prefix sum at `(i,j)`.
- While building prefixes row by row, values in a row are non-decreasing with `j`; once one exceeds `k`, later ones will too.
- Increment the answer for each prefix ≤ `k`, and break the row scan when a prefix crosses `k`.

## Algorithm (matches `Solution`)

1. Iterate rows `i` and columns `j`, update in-place 2D prefix: `grid[i][j] += up + left - diag`.
2. If `grid[i][j] <= k`, increment `res`; otherwise break out of the current row loop (no larger `j` can fit).
3. Return `res`.

## Alternative (matches `Solution2`)

- Maintain column running sums `cols[j]` up to current row.
- For each row, accumulate a running row prefix `rows += cols[j]`; if `rows <= k`, count it (that `rows` equals prefix sum to `(i,j)`).

## Why it works

- Any submatrix anchored at `(0,0)` is uniquely identified by its bottom-right `(i,j)`; its sum is the prefix at `(i,j)`.
- Prefix sums across a fixed row are non-decreasing because all entries are non-negative additions; thus the early break is safe.
- Checking every feasible `(i,j)` ensures all qualifying submatrices are counted exactly once.

## Complexity

- Time: O(m * n).
- Space: O(1) extra using in-place prefixes, or O(n) for the `cols` array in the alternative version.

## Example

Grid = [[1, 2, 1], [0, 1, 3]], k = 5

- Build row 0 prefixes: [1, 3, 4] ⇒ all counted (3).
- Row 1 prefixes (with up/left/diag):
	- (1,0): 1 ⇒ count (4)
	- (1,1): 4 ⇒ count (5)
	- (1,2): 8 (>5) ⇒ break row
- Total counted submatrices = 5 (bottom-right at (0,0), (0,1), (0,2), (1,0), (1,1)).
