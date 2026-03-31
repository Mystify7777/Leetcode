# 3474. Lexicographically Smallest Generated String - How Why Explanation

## Goal

Build the lexicographically smallest string `ans` of length `n+m-1` such that for every `i`:

- If `S[i] == 'T'`, then `ans[i..i+m-1]` **equals** pattern `t`.
- If `S[i] == 'F'`, then `ans[i..i+m-1]` **does not equal** `t`.
Return empty if impossible.

## Idea in 3 lines

- Force all required `T` occurrences first; overlapping `T` windows must be consistent. Use Z-function on `t` to validate overlaps quickly.
- Fill all undecided positions with `'a'` (lexicographically smallest) to start.
- For each `F` window that currently equals `t`, flip the rightmost available flexible position inside it to `'b'`; if none exists, impossible.

## Algorithm (matches `Solution`)

1. Convert `S` to char array `s`; init `ans` length `n+m-1` with `'?'`.
2. Process all `T` positions left-to-right:
	- Track last `T` index `pre`. Overlap size with previous `T` is `size = max(pre + m - i, 0)`.
	- Use Z on `t` to ensure `t` prefix of length `size` equals suffix of `t` of length `size`; if not, return empty.
	- Fill `ans[i+size .. i+m-1]` with corresponding `t` chars; update `pre`.
3. Initialize any remaining `'?'` in `ans` to `'a'`; compute `preQ[i]` = nearest `'?'` (now `'a'`) at or before `i` for quick fallback positions.
4. Build Z over `t + ans` to test equality of any window `ans[i..i+m-1]` with `t` in O(1) via `z[m+i]`.
5. For each `F` position `i`:
	- If `z[m+i] < m`, window already differs → continue.
	- Else find latest flexible pos `j = preQ[i+m-1]` within window; if `j < i`, no place to change → return empty.
	- Set `ans[j] = 'b'` (still smallest char differing from `t[j-i]`); jump `i = j` to skip reprocessing.
6. Return `ans`.

## Why it works

- Overlapping `T` windows must agree on shared characters; the Z check enforces this in O(1) per `T`.
- Filling `'a'` everywhere else yields the smallest baseline string; adjusting to `'b'` only when forced by an `F` keeps lexicographic minimality.
- Changing the rightmost available position in an offending `F` window ensures that future windows to the right remain as small as possible while breaking equality.

## Complexity

- Time: O(n + m + (n+m) + n) = O(n + m) using Z-function for equality checks.
- Space: O(n + m) for arrays and the constructed string.

## Example

- S = "TFT", t = "ab" (n = 3, m = 2; answer length = 4)
- Place T at i = 0 → ans[0..1] = "ab" → ? a b ?
- Place T at i = 2 → ans[2..3] = "ab"; overlap is consistent → ? a b b
- Fill remaining ? with 'a' → a a b b
- Check F at i = 1: window ans[1..2] = "ab" equals t, so flip rightmost flexible position in that window (index 1) to 'b' → a b b b
- Final answer "abbb" is lexicographically smallest satisfying all constraints.
