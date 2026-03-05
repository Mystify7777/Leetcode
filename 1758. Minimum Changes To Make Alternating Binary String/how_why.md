# How Why Explanation - 1758. Minimum Changes To Make Alternating Binary String

## Problem

Given a binary string `s`, flip the minimum number of characters so that the string becomes alternating (either `0101...` or `1010...`). Return that minimum flips count.

## Intuition

There are only two valid alternating patterns for a given length: start with `0` or start with `1`. Count mismatches against both and take the smaller.

## Approach (two-pattern mismatch)

- Pattern A starts with `s[0]`; pattern B starts with the opposite.
- Traverse the string, flipping expectation each step. Count mismatches for pattern A, and similarly for pattern B (offset by flipping the starting bit).
- Answer is `min(countA, countB)`. Implementation in [1758. Minimum Changes To Make Alternating Binary String/Solution.java](1758.%20Minimum%20Changes%20To%20Make%20Alternating%20Binary%20String/Solution.java#L4-L18).

## Complexity

- Time: O(n).
- Space: O(1).

## Edge Cases

- Length 1 → already alternating, 0 flips.
- All same char → flips equal to `n/2` or `ceil(n/2)` depending on start.

## Alternate Approaches

- **Direct formula:** Count zeros/ones at even/odd indices and compute cost for starting with `0` or `1` in O(n) without simulating expectations.
- **Bitwise view:** Treat chars as bits; mismatches for start=0 is count of positions where `bit != (idx % 2)`; start=1 just flips that parity.
