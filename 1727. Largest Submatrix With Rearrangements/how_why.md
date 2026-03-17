# 1727. Largest Submatrix With Rearrangements - How Why Explanation

## Goal

In a binary matrix, you may reorder columns **independently for each row**. Find the maximum possible area of an all-1 submatrix after such rearrangements.

## Idea in 3 lines

- Compute column heights of consecutive 1s ending at each row (histogram trick).
- For a fixed row, you can permute columns; sorting that row’s heights lets you pair taller bars to the left, so width shrinks as you move right.
- For each position `j` in the sorted row, area = `height[j] * (cols - j)`; track the maximum.

## Algorithm (matches `Solution`/`Solution2`)

1. Build heights: for each cell `(r, c)`, if `matrix[r][c] == 1`, add the height from the row above; else leave as 0.
2. For every row `r`:
	- Sort its `col` heights (ascending in code; descending works too if you adjust indexing).
	- For each column index `j`, candidate area = `matrix[r][j] * (col - j)`; update answer.
3. Return the maximum area found.

## Why it works

- After fixing a row, reordering columns is equivalent to reordering the histogram bars; the best rectangle using a bar as the limiting height uses the widest suffix that includes it.
- Sorting exposes that suffix width as `(col - j)` for bar at position `j`; multiplying by its height gives the best rectangle where that bar is the minimum height.
- Taking the max over all rows covers all possible bottoms of a rectangle of 1s.

## Complexity

- Time: O(m * n log n) due to sorting each of the `m` rows of length `n`.
- Space: O(1) extra beyond the input matrix (heights stored in place).
