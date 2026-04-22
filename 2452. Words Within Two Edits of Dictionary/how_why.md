# How Why Explanation - 2452. Words Within Two Edits of Dictionary

## Problem

You are given:

- `queries`: words to test
- `dictionary`: reference words

All words have the same length. A query is valid if it can become at least one dictionary word by changing at most 2 characters. Return all such queries in original order.

## Intuition

For each query, compare it against every dictionary word character-by-character.

- Count mismatches.
- If mismatches exceed 2, that dictionary word can no longer help, so stop early for that comparison.
- If any dictionary word has mismatches `<= 2`, the query should be included.

Since word lengths are fixed, Hamming distance is exactly what we need.

## Approach

1. For each `query`, call helper `hasMatch(query, dictionary)`.
2. In `hasMatch`:
	- iterate each dictionary word
	- start `diffMax = 2`
	- for each position `i`, if chars differ, decrement `diffMax`
	- if `diffMax < 0`, break early (already more than 2 edits)
	- if loop finishes with `diffMax >= 0`, return `true`
3. If helper returns `true`, add query to answer list.
4. Return final list.

This matches [2452. Words Within Two Edits of Dictionary/Solution.java](2452.%20Words%20Within%20Two%20Edits%20of%20Dictionary/Solution.java#L4-L33).

## Why It Works

- A word needs at most 2 substitutions exactly when Hamming distance is `<= 2`.
- The helper checks this condition for every dictionary candidate.
- Returning true on first valid candidate is sufficient because the requirement is existence of at least one match.
- Collecting all queries that satisfy this gives the correct output set in order.

## Complexity

Let:

- `q = queries.length`
- `d = dictionary.length`
- `m = word length`

Worst-case time: O(q * d * m).

The early break often improves practical runtime when differences exceed 2 quickly.

Space: O(1) extra (excluding output list).

## Edge Cases

- Exact match (0 edits) is valid.
- 1 or 2 differences is valid.
- 3+ differences against all dictionary words means query is excluded.
- Empty result is possible if no query has a valid dictionary match.

## Alternate Approaches

- Preprocessing patterns is possible, but because limit is only 2 and constraints are moderate, direct comparison is simpler and clear.
- Trie-based search with mismatch budget can solve a generalized version, but is unnecessary overhead for this problem.
