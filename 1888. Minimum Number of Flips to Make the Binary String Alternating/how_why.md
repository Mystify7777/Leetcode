# How Why Explanation - 1888. Minimum Number of Flips to Make the Binary String Alternating

## Goal

Given a binary string `s`, choose a rotation and flip as few bits as possible so the result alternates (`0101…` or `1010…`). Return that minimum flips count.

## Idea in 3 lines

- Only two alternating patterns exist; parity tells which one a position should match.
- Sliding a window over the doubled string tracks, for the current rotation, how many characters already match each pattern.
- The minimum flips for a rotation is the smaller mismatch count of the two patterns; take the best over all rotations.

## Core trick (XOR parity)

- For index `i`, pattern bit = `i mod 2` for `0101…` and `i+1 mod 2` for `1010…`.
- `s.charAt(i) ^ i` (characters treated as `'0'`/`'1'` codes) equals `0` when `s[i]` matches `0101…`, `1` when it matches `1010…`.
- Maintaining counts in `op[0]` and `op[1]` lets us know, for any window, how many bits already agree with each target pattern.

## Algorithm (O(n), O(1))

1. Count initial matches for the first `n` chars against both patterns via the XOR trick; store in `op`.
2. Initialize `res` to `n` (worst case).
3. Slide a window of size `n` across the doubled string indices `0..2n-1` by virtually moving one char from front to back:
	- Remove its contribution from `op` for the outgoing index.
	- Add its contribution for the incoming (shifted) index `n + i`.
	- After each shift, possible flips for this rotation = `min(op[0], op[1])`; update `res`.
4. Return `res`.

## Why it works

- For any rotation, mismatches to a pattern = window size − matches. Since window size is fixed at `n`, minimizing mismatches is equivalent to maximizing matches, so tracking matches per pattern is enough.
- Checking both patterns each step ensures we pick the better alignment (starting with `0` or `1`).
- Sliding over `2n` covers all rotations exactly once.

## Complexity

- Time: O(n) single pass with constant work per index.
- Space: O(1) for the `op` array and a few scalars.
