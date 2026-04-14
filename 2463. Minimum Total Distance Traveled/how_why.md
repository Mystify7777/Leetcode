# 2463. Minimum Total Distance Traveled - How Why Explanation

[Minimum Total Distance Travelled](https://leetcode.com/problems/minimum-total-distance-traveled/)

## Goal

Assign every robot to a factory (respecting each factory capacity) so the total travel distance is minimized.

## Idea in 3 lines

- Sort robots and factories by position so optimal assignments are order-preserving.
- Use DP where we decide how many of the current rightmost robots are handled by the current factory.
- Try assigning `k` robots (from 0 up to factory limit) and keep the minimum cost.

## DP (matches `Solution`)

Let:

- `n = number of robots`, `m = number of factories`
- `dp[i][j]` = minimum distance to fix first `i` sorted robots using first `j` sorted factories.

Initialization:

- `dp[0][j] = 0` for all `j` (no robots costs 0)
- other states start as INF.

Transition for factory `j` with position `pos` and capacity `limit`:

1. Skip this factory:
	- `dp[i][j] = dp[i][j-1]`
2. Use this factory for `k` robots (`1..limit`, with `i-k >= 0`):
	- assign robots `i-k .. i-1` to `pos`
	- maintain cumulative `dist += |robot[i-k] - pos|`
	- candidate: `dp[i-k][j-1] + dist`
	- take minimum.

Answer is `dp[n][m]`.

## Why it works

- After sorting, crossing assignments are never better than non-crossing ones, so DP over prefixes is valid.
- For each factory, all feasible counts `k` of robots it can absorb are explicitly considered.
- Each state combines an optimal previous prefix (`dp[i-k][j-1]`) with exact added distance for the `k` robots assigned now.

## Complexity (Solution)

- Time: `O(m * n * limit)` in the nested loops (worst-case `O(m * n^2)`).
- Space: `O(n * m)`.

## Note on `Solution2`

`Solution2` computes the same DP relation in reverse with a monotonic deque optimization, reducing transition cost per state and improving runtime while keeping correctness.
