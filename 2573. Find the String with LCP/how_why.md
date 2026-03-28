# 2573. Find the String with LCP - How Why Explanation

## Goal

Given an `n x n` LCP matrix where `lcp[i][j]` is the length of the longest common prefix between suffixes starting at `i` and `j`, construct the lexicographically smallest lowercase string of length `n` that matches the matrix, or return empty if impossible.

## Idea in 3 lines

- Equal letters imply `lcp[i][j] > 0`; different letters imply `lcp[i][j] == 0`.
- Greedily assign the smallest available character to the next unassigned position, propagating that same character to all later positions `j` with `lcp[i][j] > 0`.
- After assignment, validate the entire matrix using the LCP recurrence: if `s[i] == s[j]`, then `lcp[i][j]` must equal `1` at the edges or `lcp[i+1][j+1] + 1` inside; otherwise `lcp[i][j]` must be `0`.

## Algorithm (matches `Solution`)

1. Initialize `word` with null chars, `current = 'a'`.
2. For `i` from `0` to `n-1`:
	- If `word[i]` is unset, assign `current` (fail if past `'z'`).
	- For every `j > i`, if `lcp[i][j] > 0`, set `word[j] = word[i]` (tie positions sharing the first char of their suffixes).
	- Increment `current` for the next new group.
3. Validate matrix consistency by scanning from bottom-right to top-left:
	- If `word[i] != word[j]`, require `lcp[i][j] == 0`.
	- If equal: on last row/col require `lcp[i][j] == 1`; else require `lcp[i][j] == lcp[i+1][j+1] + 1`.
	- Any violation ⇒ return empty string.
4. Return the constructed string if validation passes.

## Why it works

- Grouping by `lcp[i][j] > 0` enforces identical starting characters for suffixes with nonzero LCP, using the smallest lexicographic labels first yields the lexicographically smallest feasible string.
- The standard LCP recurrence fully characterizes the matrix for a given string; validating it ensures global consistency beyond first-character grouping.
- If a character beyond `'z'` would be needed, no valid lowercase string exists, so return empty.

## Complexity

- Time: O(n^2) for grouping and validation.
- Space: O(n) for the output string.
