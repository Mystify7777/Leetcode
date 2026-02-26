# 1404. Number of Steps to Reduce a Number in Binary Representation to One — How/Why

## Problem

Given a binary string `s` representing a positive integer, repeatedly apply: if the number is even, divide by 2; if odd (and > 1), add 1. Count the steps to reach `1`.

## Intuition

Process the string from right to left while keeping a carry that represents whether prior additions turned trailing bits into a pending increment. Each bit with the carry determines whether the current suffix is odd or even and thus whether the step cost is 1 (even) or 2 (odd: add then divide).

## Approach

- Track `carry` (0 or 1) and iterate index `l` from the least significant bit to just above the most significant bit.
- For each bit `b = s[l]`:
	- Evaluate `b + carry`:
		- `0`: even → one division step; `carry` stays 0.
		- `2`: even (1 with carry 1) → one division step; `carry` stays 1.
		- `1`: odd → needs add then divide → two steps; set `carry = 1`.
	- Move left.
- After the loop, if `carry == 1`, one extra step is needed to clear the final overflow (binary `10` -> `1`).

## Correctness

- Scanning from LSB to MSB preserves the effect of additions: adding 1 to an odd suffix sets that bit to 0 and propagates a carry; the algorithm stores that carry and applies it to higher bits.
- When `b + carry` is even, only a divide-by-two step occurs; when odd, an add-then-divide (2 steps) matches the required operations. This mirrors the real arithmetic on the represented number.
- The final extra step when `carry == 1` accounts for the top-level overflow to `10`, which needs one division to reach `1`. Thus the counted steps equal the sequence of allowed operations that reduce the number to `1`.

## Complexity

- Time: $O(n)$ over the length of `s`.
- Space: $O(1)`.

## Edge Cases

- Minimal input `"1"`: loop skips (no bits to the left), `carry = 0`, result `0` steps (already at 1).
- String of all zeros is invalid per constraints (positive integer) and would be handled as even steps if it appeared.
- Long runs of ones create carry that persists; logic handles via `carry` flag.

## Alternatives

- Similar right-to-left pass but condensed: treat `bit + carry == 1` as the odd case needing 2 steps and setting `carry = 1` (see `Solution2`).
- Simulate with `BigInteger`, simpler but $O(n^2)$ due to repeated divisions and additions on large values.

## Key Code

- Carry-aware right-to-left counting: [1404. Number of Steps to Reduce a Number in Binary Representation to One/Solution.java](1404.%20Number%20of%20Steps%20to%20Reduce%20a%20Number%20in%20Binary%20Representation%20to%20One/Solution.java#L4-L35)
