# 1878. Get Biggest Three Rhombus Sums in a Grid - How Why Explanation

## Goal

In an `m x n` grid, compute the sums of all rhombus borders (including size-0 single cells) and return the three largest distinct sums.

## Idea in 3 lines

- A rhombus is defined by a center `(i, j)` and a radius `k` (distance to any corner); radius `0` is just the single cell.
- For each valid center and radius, walk the four edges and add their border values; skip if the shape goes out of bounds.
- Maintain the top three distinct sums on the fly to avoid storing all results.

## Algorithm (matching `Solution2`)

1. Seed the top-3 tracker with all single-cell values (`k = 0`).
2. For every cell `(i, j)` as center, iterate radius `k >= 1` while the diamond stays inside bounds (`i ± k` and `j ± k` valid).
3. For a given `(i, j, k)`, traverse each edge length `k` and accumulate border cells:
	- Up-left to up-right, up-right to down-right, down-right to down-left, down-left to up-left.
4. After each rhombus sum, insert it into the top-3 tracker (skip duplicates) by simple comparisons and shifts.
5. Return the collected top three (or fewer if not available).

## Why it works

- Iterating all centers and radii enumerates every possible rhombus exactly once; the bound check prunes invalid shapes.
- Border traversal adds each edge cell once, matching the problem’s definition of perimeter-only sums.
- Keeping only the top three distinct values avoids extra storage and repeated sorting.

## Complexity

- Time: `O(m * n * R^2)` in this straightforward edge-walk, where `R = min(m, n)`; acceptable for the given constraints.
- Space: O(1) beyond the grid and the three tracked sums.
