# How Why Explanation - 3783. Mirror Distance of an Integer

## Problem

Given an integer `n`, create its digit-reversed value (mirror), then return the absolute difference between the mirrored number and `n`.

In short: if `rev` is `n` with digits reversed, answer is `|rev - n|`.

## Intuition

The mirror of a number is built by reading digits from right to left:

- take last digit with `% 10`
- append it to `rev` with `rev = rev * 10 + digit`
- remove last digit from source with `/ 10`

Once `rev` is complete, the required distance is simply absolute difference.

## Approach

1. Initialize `rev = 0`.
2. Copy `n` into a loop variable `x`.
3. While `x > 0`:
	- extract digit `x % 10`
	- append to `rev`
	- shrink `x` by dividing by 10
4. Return `Math.abs(rev - n)`.

This is exactly what the implementation does in [3783. Mirror Distance of an Integer/Solution.java](3783.%20Mirror%20Distance%20of%20an%20Integer/Solution.java#L4-L10).

## Complexity

- Time: O(d), where `d` is number of digits in `n`.
- Space: O(1).

## Edge Cases

- `n = 0` -> reverse is `0`, distance is `0`.
- Palindrome numbers (like `121`) -> reverse equals original, distance is `0`.
- Numbers ending in zero (like `120`) -> reverse becomes `21`, still handled correctly.

## Alternate Approaches

- Convert `n` to string, reverse characters, parse back to integer, then compute absolute difference.
- The numeric approach used here is preferred: no extra string allocations and straightforward O(1) space.
