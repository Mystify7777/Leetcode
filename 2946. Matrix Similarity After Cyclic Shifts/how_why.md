# How Why Explanation - 2946. Matrix Similarity After Cyclic Shifts

## Goal

Check if applying `k` cyclic shifts to each row makes no change to the matrix. Rows at even indices shift left by `k`, odd indices shift right by `k` (equivalent to left by `n-k`).

## Idea in 3 lines

- Normalize `k` with `k %= n` since shifting by a full row length does nothing.
- For each row `i`, compare every column `j` to its would-be shifted position; if any mismatch, matrices are not similar.
- Even rows use `(j + k) % n`, odd rows use `(j - k + n) % n` (or `(j + n - k) % n`).

## Algorithm (matches `Solution`)

1. Let `m = rows`, `n = cols`; set `k = k % n`.
2. For each row `i` and column `j`:
	- If `i` is even, expected value = `mat[i][(j + k) % n]`.
	- If `i` is odd, expected value = `mat[i][(j - k + n) % n]`.
	- If `mat[i][j]` differs from expected, return false.
3. If all positions match, return true.

## Why it works

- A cyclic shift permutes indices modulo `n`; comparing each entry to its shifted counterpart ensures the row is invariant under the required shift direction.
- Parity determines direction per problem statement; the modulo handles wrap-around and negative offsets.
- Scanning all cells guarantees detection of any mismatch.

## Complexity

- Time: O(m * n).
- Space: O(1).
