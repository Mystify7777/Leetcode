# 279. Perfect Squares — How & Why

## Problem

- Given `n`, return the minimum number of perfect square numbers that sum to `n`.

## Approaches in This Repo

### 1) DP (Unbounded Coin Change)

- Define `dp[i]` = minimum number of squares to sum to `i`.
- For every square `sq` up to `n`, relax `dp[i] = min(dp[i], dp[i - sq] + 1)` for all `i >= sq`.
- Implementation: see the first `numSquares` in [279. Perfect Squares/Solution.java](279.%20Perfect%20Squares/Solution.java).

Complexity: `O(n * sqrt(n))` time, `O(n)` space.

### 2) Number Theory (Legendre + Lagrange)

- Implementation: `Solution2.numSquares` in [279. Perfect Squares/Solution.java](279.%20Perfect%20Squares/Solution.java).
- Uses classical results:
  - If `n` is a perfect square → answer `1`.
  - Remove factors of `4`: while `n % 4 == 0`, set `n = n / 4` (Legendre’s three-square theorem normalization).
  - If `n % 8 == 7` → answer `4` (Legendre’s three-square theorem: numbers of form `4^a(8b+7)` require four squares).
  - Check if `n` can be written as sum of two squares: existence of `i` such that `n - i^2` is a perfect square → answer `2`.
  - Otherwise → answer `3` (Lagrange’s four square theorem guarantees ≤4; remaining cases are 3).

## Solution 2 — Detailed Explanation

1. Small `n` and perfect square check:
	- If `n < 4`, trivial: `n` itself is the sum of `n` ones (1 is a square), so return `n`.
	- If `n` is a perfect square, return `1`.

2. Remove factors of four (`n >>= 2` while `n % 4 == 0`):
	- Scaling by `4` doesn’t change the minimal count of squares, because squares scale similarly: if `n = 4k`, any representation of `k` by squares can be scaled by `2^2` to represent `n`.
	- Normalizing `n` improves the discriminatory checks to follow.

3. Check the `8b + 7` form: if `n % 8 == 7`, return `4`.
	- By Legendre’s three-square theorem, integers of form `4^a(8b+7)` cannot be represented as the sum of three squares; combined with Lagrange’s four-square theorem, the minimal count must be `4`.

4. Test sum of two squares:
	- Let `x = floor(sqrt(n))`. For `i` from `1` to `x`, if `n - i^2` is a perfect square, return `2`.
	- This leverages the sum-of-two-squares characterization practically without full prime factorization.

5. Default case → `3`:
	- If none of the above match, the number is representable by three squares (and needs exactly three). Lagrange ensures any integer needs at most four; since it’s not in the `8b+7` class, three suffice.

Notes from code:

- `validSquare(n)` checks `int root = (int) Math.sqrt(n)` and compares `root * root == n`.
- Bitwise checks: `(n & 3) == 0` is `n % 4 == 0`; `(n & 7) == 7` is `n % 8 == 7`.

## Complexity

- DP: `O(n * sqrt(n))` time, `O(n)` space.
- Solution2: `O(sqrt(n))` time for the two-squares scan; `O(1)` space.

## Edge Cases

- `n = 0` → `0`.
- Perfect squares: `1, 4, 9, ...` → `1`.
- Numbers of the form `4^a(8b+7)` → `4`.
- Small values `n < 4` handled explicitly in Solution2.

## Reference

- See implementations in [279. Perfect Squares/Solution.java](279.%20Perfect%20Squares/Solution.java).
