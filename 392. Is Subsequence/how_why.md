# How & Why — 392. Is Subsequence

## Problem

Given strings `s` and `t`, determine whether `s` is a subsequence of `t` — that is, you can delete some (or none) characters from `t` so that the remaining sequence equals `s` (preserving order).

## Intuition

You only need to match the characters of `s` in order while scanning `t`. Each time a matching character is found, advance in `s`. If you finish `s` before `t` ends, then `s` is a subsequence.

## Algorithm (Two Pointers)

1. Initialize two indices: `i = 0` for `s`, `j = 0` for `t`.
2. While `i < len(s)` and `j < len(t)`:
   - If `s[i] == t[j]`, increment `i` (we matched this character).
   - Always increment `j` to continue scanning `t`.
3. At the end, return `i == len(s)`.

### Pseudocode

```c
i = 0
for each char c in t:
    if i < len(s) and s[i] == c:
        i += 1
return i == len(s)
```

## Complexity

- Time: O(|t|) — each character of `t` is checked at most once.
- Space: O(1) — only a couple of indices.

## Edge Cases

- `s` is empty → always true.
- `t` is empty but `s` is not → false.
- Repeated characters (e.g., `s = "aaa"`, `t = "aaaaa"`) → ensure you only advance `s` on matches.
- `s` longer than `t` → early false.

## Alternatives & When to Use Them

If you must answer many queries `s1, s2, ...` against the same `t`, preprocess `t`:

- Build a map from character → sorted list of positions where it occurs in `t`.
- For each query string `s`, greedily find the next position using binary search (lower_bound) on the corresponding list, starting after the previous match.
- Complexity per query: O(|s| log K), where K is the average occurrences per character, with O(|t|) preprocessing.

This approach is ideal for large-scale, multi-query scenarios; the simple two-pointer scan is best for single query or small inputs.

## Why This Works (Correctness)

We maintain the invariant: `i` is the length of the longest prefix of `s` found in order so far within the scanned prefix of `t`. Since we only advance `i` when we see the next required character, once `i == len(s)`, all of `s` has appeared in order in `t`.

## Variants You Might See

- Check if `s` is a subsequence of any rotation/concatenation → adapt scanning order or use the preprocessed index method.
- Count how many subsequences equal `s` → dynamic programming (distinct from the decision problem here).

## Quick Tests

- `s = "abc"`, `t = "ahbgdc"` → true (`a`→`h`→`b`→`g`→`d`→`c`).
- `s = "axc"`, `t = "ahbgdc"` → false (no `x` after `a`).
- `s = ""`, `t = "anything"` → true.

## Notes

- Stream-friendly: you can process `t` as a stream without storing it.
- Case sensitivity: treat characters consistently (convert case if required).
