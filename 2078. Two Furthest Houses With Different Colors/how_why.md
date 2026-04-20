# 2078. Two Furthest Houses With Different Colors - How Why Explanation

## Problem

Given an array `colors` where `colors[i]` is the color of the house at position `i`, find the maximum distance between two houses with different colors.

The distance between houses `i` and `j` is `|i - j|`, so the task is to maximize that value subject to `colors[i] != colors[j]`.

## Intuition

The farthest valid pair must involve one of the ends of the array.

- If a house differs from the last house, then its distance to the last house is a candidate.
- If a house differs from the first house, then its distance to the first house is a candidate.

Because we want the farthest possible distance, checking the first mismatch from each end is enough.

## Approach

1. Scan from the left until finding the first house whose color differs from the last house. That gives the farthest distance to the right end.
2. Scan from the right until finding the first house whose color differs from the first house. That gives the farthest distance to the left end.
3. Return the larger of those two distances.

This is implemented in [2078. Two Furthest Houses With Different Colors/Solution.java](2078.%20Two%20Furthest%20Houses%20With%20Different%20Colors/Solution.java#L4-L23).

## Complexity

- Time: O(n).
- Space: O(1).

## Edge Cases

- All houses have the same color: answer is `0`.
- Only two houses: answer is `1` if colors differ, otherwise `0`.
- If the first and last houses already differ, the answer is `n - 1`.

## Alternate Approaches

- **Single pass from both ends:** The alternate solution in [Solution2](2078.%20Two%20Furthest%20Houses%20With%20Different%20Colors/Solution.java#L25-L36) checks both ends simultaneously and stops as soon as it finds a mismatch.
- **Brute force:** Compare all pairs, but that is O(n^2) and unnecessary because the maximum distance must come from an endpoint.
