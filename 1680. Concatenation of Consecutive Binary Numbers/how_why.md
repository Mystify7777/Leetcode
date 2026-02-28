# How Why Explanation - 1680. Concatenation of Consecutive Binary Numbers

## Problem

For each integer from 1 to `n`, write its binary representation (without leading zeros) and concatenate them in order to form one big binary number. Return that value modulo $10^9+7$.

## Intuition

Appending a number `i` in binary to an existing bitstring is the same as left-shifting by `len(i)` bits and adding `i`. Only the bit-length of `i` matters, which increases by 1 exactly when `i` is a power of two.

## Approach (single pass)

- Maintain `result` as the rolling concatenation under modulo, and `bits` as the current bit-length of `i`.
- Iterate `i` from 1 to `n`:
	- If `i` is a power of two (`i & (i-1) == 0`), increment `bits`.
	- Update `result = ((result << bits) + i) % MOD`.
- Return `result`.

This matches [1680. Concatenation of Consecutive Binary Numbers/Solution.java](1680.%20Concatenation%20of%20Consecutive%20Binary%20Numbers/Solution.java#L4-L15).

## Complexity

- Time: O(n).
- Space: O(1).

## Edge Cases

- `n = 1` → result is 1.
- Powers of two grow `bits` at 1, 2, 4, 8, ...
- Large `n` still safe due to modulo after each step.
