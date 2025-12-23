# How & Why — 151. Reverse Words in a String

## Problem

Given a string `s`, reverse the order of the words. A word is defined as a sequence of non-space characters. Remove extra spaces so that the result has single spaces between words with no leading or trailing spaces.

## Intuition

We only need to normalize spacing and reverse the list of words. Splitting into words and re-joining is simple and efficient. Alternatively, for in-place transformations (on mutable buffers), reverse by segments: trim, collapse spaces, reverse whole string, then reverse each word.

## Algorithm (Split & Join)

1. Trim leading/trailing spaces.
2. Split by one or more spaces and discard empty tokens.
3. Reverse the list of words.
4. Join with a single space.

### Pseudocode

```java
words = split(s.trim(), / +/)         # one or more spaces
reverse(words)
return join(words, " ")
```

## Complexity

- Time: O(n) — scanning, splitting, and reversing over the input length.
- Space: O(k) — where `k` is total characters in words (or O(n)); in-place variants can achieve O(1) extra.

## Edge Cases

- Only spaces → returns empty string.
- Leading/trailing spaces → trimmed.
- Multiple spaces between words → collapsed to single spaces.
- Single word → unchanged after trim.

## Alternative (In-Place, Two-Pass)

Useful when working on mutable arrays/buffers:

1. Trim and collapse spaces in-place to build a cleaned buffer.
2. Reverse the entire cleaned buffer.
3. Walk and reverse each word segment back to normal order.

This yields the same result with O(1) extra space beyond the buffer.

## Why This Works (Correctness)

Let `W = [w1, w2, ..., wm]` be the words of `s` in order. The target string is `wm wm-1 ... w1` with single spaces. Splitting by runs of spaces produces exactly `W`. Reversing and joining enforces both order and spacing constraints.

## Quick Tests

- `s = "the sky is blue"` → `"blue is sky the"`.
- `s = "  hello   world  "` → `"world hello"`.
- `s = "a good   example"` → `"example good a"`.
- `s = "   "` → `""`.

## Notes

- Be consistent with definition of word: any non-space run.
- If the environment’s `split` keeps empty tokens, filter them out.
