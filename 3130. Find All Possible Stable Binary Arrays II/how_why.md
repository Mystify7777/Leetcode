# 3130. Find All Possible Stable Binary Arrays II

## Problem

Count binary arrays with exactly `zero` zeros and `one` ones such that no more than `limit` identical bits appear consecutively. Return the count modulo $10^9 + 7$.

## Intuition

Compared to version I, this solution keeps explicit run-length windows via subtraction rather than storing run length. We can still use two DP tables keyed by counts and last bit, subtracting the over-limit sequences that would create a run of `limit + 1` identical bits.

## Approach (3D DP with window subtraction)

- Let `dp[i][j][0]` be ways to arrange `i` zeros and `j` ones ending with `0`; `dp[i][j][1]` similarly ends with `1`.
- To append `0`, we can extend any sequence at `(i-1, j, *)`, but must exclude those that already had `limit` trailing zeros. Those forbidden sequences are exactly the ones ending with `1` at `(i - (limit + 1), j)` followed by `limit + 1` zeros. So:
	- `dp[i][j][0] = dp[i-1][j][0] + dp[i-1][j][1] - (i - limit - 1 >= 0 ? dp[i - limit - 1][j][1] : 0)`
- Symmetrically for appending `1`:
	- `dp[i][j][1] = dp[i][j-1][0] + dp[i][j-1][1] - (j - limit - 1 >= 0 ? dp[i][j - limit - 1][0] : 0)`
- Initialize pure-zero and pure-one strings up to `limit`. Answer is `(dp[zero][one][0] + dp[zero][one][1]) % MOD`. Implemented in [3130. Find All Possible Stable Binary Arrays II/Solution.java](3130.%20Find%20All%20Possible%20Stable%20Binary%20Arrays%20II/Solution.java#L4-L33).

## Complexity

- Time: O(zero * one).
- Space: O(zero * one * 2) for the DP table (could be reduced with rolling arrays if needed).

## Edge Cases

- One of the counts is 0: valid only if the other count ≤ `limit` (handled by init loops).
- Very small `limit` (e.g., 1) enforces strict alternation.
- `limit >= max(zero, one)` means no run restriction beyond counts, so answer is `C(zero + one, zero)` combinatorially; DP still yields it.

## Alternate Approaches

- **Rolling arrays with queues:** Like problem 3129’s optimized version, maintain only recent rows/cols to cut memory from O(zero * one) to O(one * min(limit, zero)).
- **Run-length explicit DP:** Track `(i, j, last, runLen)`; larger state O(zero * one * limit) and unnecessary with window subtraction.
