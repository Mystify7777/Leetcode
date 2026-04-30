# How Why - Explanation 3742. Maximum Path Score in a Grid

[3742. Maximum Path Score in a Grid](https://leetcode.com/problems/maximum-path-score-in-a-grid/)

## Problem

Given a grid of integers and an integer `k`, move from the top-left to the bottom-right, moving only right or down. You may "collect" positive cells, but each positive cell costs 1 unit from your `k` budget. Maximize the sum of collected values, using at most `k` positive cells. Return -1 if impossible.

## Intuition

This is a grid DP problem with an extra constraint: you can only collect up to `k` positive cells. The DP state must track both position and how many positive cells have been used so far.

Two approaches are provided:

### 1. AltSolution (Classic 3D DP, Space Optimized)

- Uses a DP array `dp[i][j][c]` (optimized to 2D per row) where `c` is the number of positive cells collected so far.
- For each cell, considers both coming from above and from the left, updating the best score for each possible count of positive cells used.
- Only allows transitions if the number of positive cells used does not exceed `k` and the path is valid.
- At the end, the answer is the best score at the bottom-right cell using up to `k` positive cells.

### 2. Solution (DP with Early Pruning and Min Path Check)

- First, checks if the minimum possible number of positive cells needed to reach the end exceeds `k` (using a helper for min path sum). If so, returns -1 immediately.
- Uses a DP table `f[j][k]` where `f[j][c]` is the best score at column `j` using `c` positive cells.
- For each cell, iterates over possible positive cell counts, updating the DP table by considering both right and down moves, and whether the current cell is positive.
- This approach is more efficient due to early pruning and careful DP state management.

## Complexity

- Both approaches: Time and space are `O(m * n * k)` for grid size `m x n` and budget `k`.
- The second solution may be faster in practice due to early pruning and tighter DP updates.

## Key Insights

- When a grid DP problem has a "limited resource" (like a budget or quota), track that resource as an extra DP dimension.
- Early pruning (checking feasibility before DP) can save time on impossible cases.

## References

- Both solutions are implemented in [3742. Maximum Path Score in a Grid/Solution.java](3742.%20Maximum%20Path%20Score%20in%20a%20Grid/Solution.java)