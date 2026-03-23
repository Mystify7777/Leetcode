# 1594. Maximum Non Negative Product in a Matrix - How Why Explanation

## Goal

Move from top-left to bottom-right in a grid (only right or down). Return the maximum non-negative path product modulo 1e9+7, or `-1` if every path product is negative.

## Idea in 3 lines

- Multiplying by a negative can swap max and min; track both the maximum and minimum product reaching each cell.
- For cell `(i,j)`, candidates come from top `(i-1,j)` and left `(i,j-1)` multiplied by `grid[i][j]`.
- The best non-negative result is the max product at the bottom-right if it is ≥ 0.

## Algorithm (DP with max/min)

1. Let `mx[i][j]` = max product to `(i,j)`, `mn[i][j]` = min product to `(i,j)`.
2. Initialize `(0,0)` with `grid[0][0]`. Fill first row/col by cumulative products (only one path each).
3. For each cell `(i,j)` from `(1,1)`:
	- Compute candidates: `a = mx[i-1][j]*x`, `b = mn[i-1][j]*x`, `c = mx[i][j-1]*x`, `d = mn[i][j-1]*x`, where `x = grid[i][j]`.
	- Set `mx[i][j] = max(a,b,c,d)` and `mn[i][j] = min(a,b,c,d)`.
4. Let `ans = mx[m-1][n-1]`; if `ans < 0` return `-1`, else return `ans % 1_000_000_007`.

## Why it works

- Any path to `(i,j)` ends at either the top or left neighbor; tracking both extremes captures the effect of negative factors.
- The minimum can become maximum after multiplying by a negative, so keeping both preserves optimality.
- The bottom-right `mx` is the largest possible product over all paths; checking its sign enforces the non-negative requirement.

## Complexity

- Time: O(m * n).
- Space: O(m * n) for the DP tables; can be reduced to O(n) with rolling arrays.
