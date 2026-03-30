# 2840. Check if Strings Can be Made Equal With Operations II - How Why Explanation

## Goal

Two strings `s1`, `s2` of equal length allow swapping characters at even indices among themselves, and at odd indices among themselves. Decide if `s1` can be permuted into `s2` under these constraints.

## Idea in 2 lines

- Even positions form one independent bucket; odd positions form another. Within each bucket, any permutation is reachable via swaps.
- Therefore the multiset of chars at even indices must match between the strings, and likewise for odd indices.

## Algorithm (matches `Solution`)

1. Use a frequency array of size 52: indices 0–25 track evens, 26–51 track odds.
2. For each position `i`, add `s1[i]` to its parity bucket and subtract `s2[i]` from the same bucket.
3. If all frequencies end at zero, buckets match → return true; else false.

## Why it works

- Swaps within a parity bucket can realize any permutation of that bucket, so only the character counts matter per parity.
- Separating frequencies by parity ensures we don’t mix even and odd positions.

## Complexity

- Time: O(n).
- Space: O(1) (fixed-size freq array).

## Example

- s1 = "abcd", s2 = "cbad"
- Even indices (0,2): s1 → {a, c}, s2 → {c, a} (match)
- Odd indices (1,3): s1 → {b, d}, s2 → {b, d} (match)
- Both parity buckets match, so allowed swaps can reorder s1 into s2 (e.g., swap positions 0 and 2).
