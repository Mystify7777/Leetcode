# How Why Explanation - 3129. Find All Possible Stable Binary Arrays I

## Problem

Count binary arrays that use exactly `zero` zeros and `one` ones, with no more than `limit` identical bits consecutively. Return the count modulo $10^9 + 7$.

## Intuition

The constraint depends only on runs. A DP over counts `(i, j)` and last bit captures whether adding another bit would break the `limit`. To enforce the run cap efficiently, we can include or exclude windows of length `limit + 1` instead of tracking run length explicitly.

## Approach (2D DP with window subtraction)

Let `dp0[i][j]` be ways to form strings with `i` zeros, `j` ones ending in `0`; `dp1` similarly ending in `1`.

- Transition to `dp0[i][j]`: append `0` to any string ending with `0` or `1` at `(i-1, j)`, but subtract those where we would create a run of `limit+1` zeros. That forbidden set corresponds to strings ending with `1` at `(i - (limit+1), j)` followed by `limit+1` zeros. So `dp0[i][j] = dp0[i-1][j] + dp1[i-1][j] - (i >= L ? dp1[i-L][j] : 0)` where `L = limit + 1`.
- Symmetrically, `dp1[i][j] = dp1[i][j-1] + dp0[i][j-1] - (j >= L ? dp0[i][j-L] : 0)`.
- Initialize single-color prefixes up to `limit`. Answer is `(dp0[zero][one] + dp1[zero][one]) % MOD`. This is implemented in [3129. Find All Possible Stable Binary Arrays I/Solution.java](3129.%20Find%20All%20Possible%20Stable%20Binary%20Arrays%20I/Solution.java#L25-L54) as `Solution2`.

## Memory-optimized variant

- Observes symmetry: swapping zeros/ones doesn’t change the count. Swap so zeros >= ones to shrink arrays.
- Only keeps the previous `dp0`/`dp1` row over ones, plus a queue of the last `L` `dp1` rows to access `dp1[i-L][*]` for subtraction. Runs in O(zero * one) time, `O(one * min(L, zero))` space. See [Solution.java](3129.%20Find%20All%20Possible%20Stable%20Binary%20Arrays%20I/Solution.java#L4-L52).

## Complexity

- Time: O(zero * one) for both implementations.
- Space: O(zero * one) for the straightforward DP; `O(one * min(L, zero))` for the optimized version after swapping.

## Edge Cases

- `limit = 0` → impossible unless `zero + one <= 1` (transitions handle via subtraction).
- One of the counts is 0: valid only if the other count ≤ `limit`.
- Very small `limit` (e.g., 1) enforces strict alternation.

## Alternate Approaches

- **Run-length DP:** Track `(i, j, last, runLen)`; correct but larger `O(zero * one * limit)` state, avoidable via window subtraction used here.
- **Combinatorial with inclusion–exclusion:** Possible but messy; DP with prefix-sum subtraction is simpler and efficient for constraints.
