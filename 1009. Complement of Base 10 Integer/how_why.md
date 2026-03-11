# How Why Explanation - 1009. Complement of Base 10 Integer

## Problem

Given a non-negative integer `n`, return the bitwise complement of its binary representation (flip each bit) interpreted back as a decimal integer.

## Intuition

For any positive `n`, its complement flips only within the span of its highest set bit. If `len` is the bit-length of `n`, then the mask of all 1s in that width is `(1 << len) - 1`. The complement is simply `mask ^ n` (or `mask - n`). For `n = 0`, the binary is `0` and its complement should be `1`.

## Approach (mask-based)

1. If `n == 0`, return 1.
2. Compute `len = bit length of n` (e.g., `Integer.toBinaryString(n).length()`).
3. Build `mask = (1 << len) - 1` (all 1s across that width).
4. Answer is `mask ^ n` (or equivalently `mask - n`). Implemented in [1009. Complement of Base 10 Integer/Solution.java](1009.%20Complement%20of%20Base%2010%20Integer/Solution.java#L4-L19).

## Alternate Approach (manual bit flip)

- Walk bits of `n`, add `2^k` whenever the `k`-th bit is 0, accumulating the complemented number directly. Shown in [Solution](1009.%20Complement%20of%20Base%2010%20Integer/Solution.java#L20-L37); same O(log n) time/space but avoids string/bit-length helper.

## Complexity

- Time: O(log n) (bit-length of `n`).
- Space: O(1).

## Edge Cases

- `n = 0` → return 1.
- Powers of two (e.g., `8` / `1000b`) complement to all lower bits set (`0111b`).
- Large `n` still fits in 32-bit; shifting by bit-length is safe because `len >= 1` and `< 32`.
