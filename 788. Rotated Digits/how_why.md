# How Why - Explanation 788. Rotated Digits

[788. Rotated Digits](https://leetcode.com/problems/rotated-digits/)

## Problem

Given a positive integer `n`, count how many numbers in the range `[1, n]` are valid rotated digits. A number is valid if, after rotating each digit by 180 degrees, the resulting number is different and all digits are valid under rotation. Digits `0,1,8` rotate to themselves; `2<->5`, `6<->9` rotate to each other; `3,4,7` are invalid.

## Two Approaches

### 1) DP over integers (bottom-up)

- Use an integer array `dp` of length `n+1` with states:
  - `0`: invalid (contains 3/4/7)
  - `1`: valid but unchanged after rotation (only 0,1,8 digits)
  - `2`: valid and changed (contains at least one of 2,5,6,9)

- For single-digit numbers assign base states directly.
- For multi-digit `i`, derive `dp[i]` from `dp[i/10]` and `dp[i%10]`:
  - if both parts are `1`, whole is `1`;
  - if both parts at least `1` and at least one `2`, whole is `2` (count it);
  - otherwise `0`.

- Time: `O(n)`. Space: `O(n)`.

This is implemented in `Solution.java` as `Solution`.

### 2) Digit DP (top-down tight DP)

- Convert `n` to digits and run a recursive DP with parameters `(pos, tight, changed)`:
  - `pos`: current digit index,
  - `tight`: whether prefix equals `n`'s prefix,
  - `changed`: whether we already placed a digit that will change after rotation (2/5/6/9).

- At each position try all allowed digits (skip 3,4,7). Update `tight` and `changed` accordingly. Use memoization over `(pos,tight,changed)` when `tight==0`.

- Count numbers that end with `changed==1`.

- Time: `O(#digits * states * 10)` which is efficient for typical `n` (n up to 10^9+). Space: `O(#digits * states)` for memo.

This is implemented as `Solution2` in `Solution.java`.

## Correctness

- The bottom-up DP correctly composes digit-level properties because validity and whether a number changes after rotation are local to digits and combine across concatenation.
- The digit-DP enumerates all numbers ≤ `n` and counts only those that are valid and changed; memoization avoids recomputation for loose (non-tight) prefixes.

## Edge Cases

- `n < 10`: handled directly by base states.
- Leading zeros are implicitly handled by the digit-DP recursion.

## References

- Implementations: `788. Rotated Digits/Solution.java`
