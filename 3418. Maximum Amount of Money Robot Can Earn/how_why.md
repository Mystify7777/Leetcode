# 3418. Maximum Amount of Money Robot Can Earn - How Why Explanation

## Goal

Move from top-left to bottom-right (only right or down) and maximize collected money. You may neutralize up to 2 cells, meaning you pass through those cells without adding their value.

## Idea in 3 lines

- Use dynamic programming with position and neutralization usage count.
- Let `dp[i][j][k]` be the best money at cell `(i,j)` after using `k` neutralizations (`k` in `[0,2]`).
- At each move, either take the current cell value (keep `k`) or neutralize current cell (increase used count).

## Algorithm (matches Solution.java)

1. Create `dp[n][m][3]` and initialize all states to a very negative number.
2. Initialize start cell:
	 - `dp[0][0][0] = coins[0][0]` (take start value)
	 - `dp[0][0][1] = 0`, `dp[0][0][2] = 0` (start can be treated as neutralized in the code’s state setup)
3. For each cell `(i,j)` and each `k`:
	 - From top or left, take current value:
		 - `dp[i][j][k] = max(dp[i][j][k], prev[k] + coins[i][j])`
	 - If `k > 0`, from top or left, neutralize current value:
		 - `dp[i][j][k] = max(dp[i][j][k], prev[k-1])`
4. Answer is `max(dp[n-1][m-1][0], dp[n-1][m-1][1], dp[n-1][m-1][2])`.

## Why it works

- Any valid path to `(i,j)` must come from `(i-1,j)` or `(i,j-1)`, so these transitions cover all possibilities.
- For each predecessor, the only two choices at current cell are exactly: collect value or neutralize it.
- Keeping the best total for every `(i,j,k)` ensures optimal substructure; taking max at destination gives the global optimum under the 2-neutralization limit.

## Complexity

- Time: `O(n * m * 3)` = `O(nm)`.
- Space: `O(n * m * 3)` = `O(nm)`.

## Alternate Faster-Space DP (matches `Solution2`)

- `Solution2` compresses DP to one row: `dp[col][k]`, where `k` is neutralizations budget/state (`0..2`).
- While scanning each row left-to-right:
	- `dp[j][*]` before update = value from top.
	- `dp[j-1][*]` after update = value from left.
- For each cell value `x`, transitions are the same choices as full DP:
	- take current cell (`+x`) from top/left with same `k`
	- neutralize current cell from top/left with `k-1`

This keeps the same `O(nm)` time but reduces memory to `O(m * 3)`.
