# How Why - Explanation 796. Rotate String

[796. Rotate String](https://leetcode.com/problems/rotate-string/description/)

## Problem

Given two strings `s` and `goal`, return `true` if `s` can become `goal` after some number of shifts (rotations), otherwise `false`.

A rotation moves the first character of a string to the end.

## Key Idea

Concatenate `s` with itself: any rotation of `s` must appear as a contiguous substring of `s + s`. So check whether `goal` is a substring of `s + s` and lengths match.

## Implementation

- Build `doubleS = s + s`.
- Return `doubleS.contains(goal)` if `s.length() == goal.length()`.

This is implemented in [796. Rotate String/Solution.java](796.%20Rotate%20String/Solution.java).

## Complexity

- Time: O(n) expected with an efficient substring search (Java's `indexOf` is optimized); worst-case may be higher with naive searches.
- Space: O(n) for the concatenated string.

## Edge Cases

- Different lengths → `false` immediately.
- Empty strings: considered rotations (both empty) → `true`.

## Notes

- For explicit linear worst-case guarantees, use KMP to check substring membership instead of `contains`.
