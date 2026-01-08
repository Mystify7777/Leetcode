# 1458. Max Dot Product of Two Subsequences — How & Why

## Problem

- Given two integer arrays, pick non-empty subsequences (keeping order) to maximize the dot product. Both subsequences must be non-empty.

## Key Idea

- Use 2D DP where each state chooses to take a pair `(i, j)`, or skip an index in either array. Enforce the non-empty requirement with a negative sentinel so that returning `0` by skipping everything is not allowed.

## DP Definition

- `dp[i][j]`: maximum dot product using `nums1[i..]` and `nums2[j..]` with at least one pair taken.
- Sentinel: use a large negative constant (e.g., `-1e9`) to represent impossible (empty) selections.

## Transition

- Let `prod = nums1[i] * nums2[j]`. Options:
  - Take and continue: `prod + dp[i+1][j+1]`
  - Take and stop: `prod`
  - Skip `nums1[i]`: `dp[i+1][j]`
  - Skip `nums2[j]`: `dp[i][j+1]`

```text
dp[i][j] = max(
    prod + dp[i+1][j+1],
    prod,
    dp[i+1][j],
    dp[i][j+1]
)
```

## Base Case

- If `i == n` or `j == m`, return `-INF` (cannot form a non-empty pair from this state).

## Implementations in This Repo

- Top-down memoized (`Solution`):
  - Recursion with `NEG_INF = -1e9`; memo initialized to `Integer.MIN_VALUE`.
  - Includes the take-only option (`prod`) so the result cannot be empty.
- Bottom-up 1D (`Solution2`):
  - Iterative DP with rolling arrays `dp` and `ndp`, using `NEG` as sentinel.
  - `take = (dp[j-1] == NEG) ? prod : max(prod, dp[j-1] + prod)`
  - `best = max(take, dp[j], ndp[j-1])`

## Complexity

- Time: `O(n * m)` for both approaches.
- Space:
  - Top-down: `O(n * m)` memo plus recursion stack.
  - Bottom-up 1D: `O(m)` additional space.

## Why the Alternate Approach Is Fast

- Avoids recursion overhead and memo lookups in hot paths.
- Uses O(m) memory with tight, cache-friendly loops.
- Fewer allocations and better locality reduce constant factors.

## Edge Cases

- All numbers negative in both arrays: still valid; choose the best single pair or a combination that yields the highest product.
- Zeros or mixed signs: handled naturally by max over transitions.
- Single-element arrays: answer is their product.

## Reference

- See [1458. Max Dot Product of Two Subsequences/Solution.java](1458.%20Max%20Dot%20Product%20of%20Two%20Subsequences/Solution.java).
