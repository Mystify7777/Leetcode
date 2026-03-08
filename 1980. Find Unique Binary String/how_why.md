# How Why Explanation - 1980. Find Unique Binary String

## Goal

Given `n` distinct binary strings of length `n`, output any binary string of length `n` not in the array.

## Idea in 2 lines

- Use Cantor’s diagonal trick: flip the `i`-th bit of the `i`-th string to form a new string.
- The new string differs from string `i` at position `i`, so it cannot equal any input string.

## Algorithm (O(n), O(1))

1. Let `n = nums.length`; create a `StringBuilder sb`.
2. For each index `i` from `0` to `n-1`:
	- If `nums[i][i]` is `'0'`, append `'1'`; otherwise append `'0'`.
3. Return `sb.toString()`.

## Why it works

- By construction, the result differs from each `nums[i]` at position `i`, so it cannot be any of the provided strings.
- Distinctness is guaranteed without hashing or search because diagonal flipping encodes a unique mismatch per row.

## Complexity

- Time: O(n) single pass over the diagonal.
- Space: O(n) for the output string; auxiliary O(1).
