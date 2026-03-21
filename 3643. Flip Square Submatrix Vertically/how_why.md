# How Why Explanation - 3643. Flip Square Submatrix Vertically

## Goal

Given a grid, pick the `k x k` square whose top-left is `(x, y)` and flip it vertically (reverse its rows in place). Return the modified grid.

## Idea in 2 lines

- Vertical flip = swap row `top` with row `bottom` inside the chosen square while keeping columns fixed.
- Do this for `k/2` row pairs; columns iterate across all `k` positions.

## Algorithm (matches `Solution2`/`Solution3`)

1. Set `top = x`, `bottom = x + k - 1`.
2. While `top < bottom`:
	- For each column `j` from `y` to `y + k - 1`, swap `grid[top][j]` with `grid[bottom][j]`.
	- Increment `top`, decrement `bottom`.
3. Return the grid.

## Notes

- `Solution` indexes `(x + j, y + i)` (iterating columns then half rows) — equivalent row swapping inside the square.
- No extra space besides a temp variable; operates in place.

## Complexity

- Time: O(k^2) swaps for the `k x k` region.
- Space: O(1) auxiliary.
