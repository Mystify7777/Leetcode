# 2839. Check if Strings Can be Made Equal With Operations I - How Why Explanation

## Goal

Given two length-4 strings `s1` and `s2`, you may swap `s1[0]` with `s1[2]` and/or `s1[1]` with `s1[3]` any number of times. Decide if `s1` can become `s2`.

## Idea in 2 lines

- Positions {0,2} can be permuted arbitrarily among themselves; likewise positions {1,3}.
- Therefore `s1` can match `s2` iff the multiset of characters at even indices matches, and the multiset at odd indices matches.

## Algorithm (matches `Solution`)

1. Check even pair: either `s1[0]==s2[0] && s1[2]==s2[2]` or crossed `s1[0]==s2[2] && s1[2]==s2[0]`.
2. Check odd pair similarly: `s1[1]==s2[1] && s1[3]==s2[3]` or crossed.
3. Return true if both pairs satisfy; otherwise false.

## Why it works

- Swapping inside {0,2} cannot affect {1,3}, so equality requires independent matches of those two buckets.
- With only two positions per bucket, checking direct vs swapped covers all permutations.

## Complexity

- Time: O(1).
- Space: O(1).
