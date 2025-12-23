# How & Why — 343. Integer Break

## Problem

Given an integer `n (2 ≤ n ≤ 58)`, split it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.

## Intuition

To maximize product under a fixed sum, the parts should be as equal as possible (AM-GM). In the integer setting, the best parts are `2` and `3`, with `3` preferred because `3 · (n-3)` grows faster than using more `2`s. Avoid leftover `1` because `3 + 1` yields `3 · 1 = 3`, which is worse than `2 + 2` yielding `4`.

Key observations:

- For `n ≥ 4`, breaking `n` improves product over keeping `n` whole.
- Use as many `3`s as possible.
- If a remainder `1` would occur, convert one `3 + 1` into `2 + 2`.

## Algorithm (Greedy with 3s)

1. Handle small base cases: if `n == 2` → `1`; if `n == 3` → `2`.
2. Initialize `product = 1`.
3. While `n > 4`: multiply `product *= 3`, and reduce `n -= 3`.
4. Finally multiply the remainder: `product *= n` (where `n` is `2`, `3`, or `4`).

This ensures we never end with remainder `1` and we replace `(3,1)` with `(2,2)` when needed.

### Pseudocode

```c
if n == 2: return 1
if n == 3: return 2

product = 1
while n > 4:
    product *= 3
    n -= 3
return product * n  # n is 2, 3, or 4
```

## Complexity

- Time: O(n/3) loop iterations → O(n) in the worst case, but constant work per iteration; for typical constraints this is effectively O(1).
- Space: O(1).

## Edge Cases

- `n = 2` → `1` (only split is `1 + 1`).
- `n = 3` → `2` (best split `2 + 1`).
- Avoid remainder `1`: replace `3 + 1` with `2 + 2`.

## Why This Works (Proof Sketch)

- By AM-GM, for positive reals with fixed sum `n`, the product is maximized when all parts are equal: if there are `k` parts, each is `n/k`, product `(n/k)^k`. The maximum occurs near `k ≈ e`.
- In integers, parts near `e` are `2` and `3`. Empirically and by inequalities: `3 · (x-3)` ≥ `2 · (x-2)` for `x ≥ 5`, and `2 · 2 = 4` beats `3 · 1 = 3`. Thus prefer `3`s, and convert any leftover `1` into `2 + 2`.

## Alternative (DP)

For completeness or when teaching dynamic programming:

- Let `dp[i]` be the maximum product for integer `i` when it must be split.
- Transition: `dp[i] = max_{1 ≤ j < i} max(j · (i - j), j · dp[i - j])`.
- Base: `dp[2] = 1`, `dp[3] = 2`.
- Complexity: O(n^2) time, O(n) space.

## Quick Tests

- `n = 2` → `1`.
- `n = 3` → `2`.
- `n = 4` → `4` (`2 + 2`).
- `n = 7` → `12` (`3 + 2 + 2`).
- `n = 10` → `36` (`3 + 3 + 4`).

## Notes

- The greedy method is simple, fast, and fits typical constraints.
- DP is helpful to build intuition and verify the greedy result for small `n`.
