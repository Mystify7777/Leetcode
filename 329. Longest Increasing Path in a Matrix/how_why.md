
# How & Why: LeetCode 329 - Longest Increasing Path in a Matrix

## Problem

Given an `m x n` matrix, return the length of the longest path where every next cell is strictly larger. Moves allowed: up, down, left, right only.

## Intuition

- The path must strictly increase, so from a cell you can only move to larger neighbors.
- That creates a DAG (no cycles if we follow increasing edges), so each cell’s best answer can be cached and reused.

## Brute Force Approach

- **Idea:** From every cell, DFS all increasing paths without caching.
- **Cost:** Exponential time in worst case; revisits the same subproblems many times. Space up to recursion depth.
- **Why it fails:** Overlapping subproblems explode; will TLE on anything but tiny grids.

## My Approach (DFS + Memo) — from Solution.java

- **Idea:** DFS from each cell, but memoize the longest path starting there. Treat grid as DAG along increasing edges.
- **Key step:** `memo[r][c] = 1 + max(dfs of larger neighbors)`.
- **Complexity:** $O(mn)$ time (each cell solved once), $O(mn)$ memo + recursion stack.
- **Core snippet:**

```java
int dfs(int r, int c) {
    if (memo[r][c] != -1) return memo[r][c];
    int best = 1;
    for (int[] d : DIRS) {
        int nr = r + d[0], nc = c + d[1];
        if (inBounds(nr,nc) && a[nr][nc] > a[r][c]) {
            best = Math.max(best, 1 + dfs(nr, nc));
        }
    }
    return memo[r][c] = best;
}
```

## Most Optimal Approach

- Same DFS + memo is already optimal asymptotically; alternative is topological DP over all cells sorted by value, also $O(mn)$.
- **Topological flavor (conceptual):** process cells in increasing order; for each cell, relax neighbors with larger values: `dp[next] = max(dp[next], dp[curr] + 1)`.

## Edge Cases

- Single cell matrix → answer 1.
- All equal values → no increasing step; answer 1.
- Strictly increasing snake (e.g., 1..mn) → recursion depth up to `mn`; ensure stack is okay or convert to iterative topo DP if needed.
- Non-rectangular inputs are invalid; assume well-formed `m x n` per problem.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force DFS | Explore all increasing paths from each cell without cache | Exponential | O(mn) stack worst case | TLE; repeated work |
| DFS + memo (used) | Cache best path per cell; reuse | O(mn) | O(mn) memo + stack | Simple to implement; fast |
| Topological DP | Sort cells by value; relax larger neighbors | O(mn log mn) or O(mn) with counting sort | O(mn) | Avoids recursion depth concerns |

## Example Walkthrough

Matrix:

```m
[
  [9, 9, 4],
  [6, 6, 8],
  [2, 1, 1]
]
```

- Start `(2,1)=1` → `(2,0)=2` → `(1,0)=6` → `(0,0)=9` length 4.
- `(1,2)=8` can go to `(0,1)=9` length 2.
- Best overall length = 4.

## Insights

- Treat the grid as a DAG along increasing edges; memoizing on DAG nodes is a recurring pattern (longest path in DAG).
- Any strictly monotone path length is bounded by number of distinct values; duplicate plateaus break paths.

## References to Similar Problems

- [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
- [Longest Path in Directed Acyclic Graph] (general pattern)
- [Path With Maximum Minimum Value](https://leetcode.com/problems/path-with-maximum-minimum-value/) (different scoring, similar grid graph thinking)
