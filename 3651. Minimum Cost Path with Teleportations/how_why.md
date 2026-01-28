# How Why Explanation - 3651. Minimum Cost Path with Teleportations

## Problem

Given an `n x m` grid of positive costs and an integer `k`, you start at `(0,0)` and want to reach `(n-1,m-1)`. Moving right or down into a neighbor costs the value of the cell you enter. Additionally, up to `k` times you may **teleport** from the current cell to any other cell whose value is **<= current cell's value** at zero move cost; after landing, you continue paying entry costs as usual. Find the minimum total cost; return `-1` if unreachable.

## Intuition

With no teleports, it is the classic right/down DP from bottom-right. A teleport lets you jump to the best cell whose value is not greater than the current one. If we know, for every value `v`, the best possible remaining cost starting from any cell with value `<= v`, we can decide whether to walk or teleport in one step. Repeating this relaxation up to `k` times propagates the benefit of using teleports.

## Brute Force (Not Used)

- Enumerate which `k` cells to teleport to (or when), then compute path costs for each choice.
- Exponential in `k` and grid size; infeasible.

## Approach (Layered DP with value-prefix minima)

1. Find `maxVal` in the grid to size helper arrays.
2. Base layer (`k = 0`): standard bottom-up DP with right/down moves; `dp[i][j]` is min cost to reach target from `(i,j)` (excluding cost of `grid[i][j]`, since cost is paid when entering the next cell). Track `temp[val]` = best `dp` among cells with that value.
3. For each teleport allowance `x` from `1..k`:
   - Build prefix minima `best[v] = min(best[v-1], temp[v])`, so `best[v]` is the cheapest cost starting from any cell with value `<= v`.
   - Recompute the grid DP: for each cell, `walk = min(down,right)` (entering-neighbor costs already included) and `teleport = best[grid[i][j]]`; set `dp[i][j] = min(walk, teleport)`. Update `temp[grid[i][j]]` with improved costs for the next layer.
4. Answer is `dp[0][0]` after `k` relaxations.

Why it works: teleport destinations are value-bounded, not position-bounded, so a prefix-min over values captures the best reachable cost with one teleport. Iterating `k` times propagates the effect of chained teleports (up to `k`).

## Complexity

- Time: $O(k \cdot n \cdot m + k \cdot V)$ where $V$ is `maxVal` (prefix build). Dominant term is usually $O(k \cdot n \cdot m)$.
- Space: $O(n \cdot m + V)$ for `dp`, `temp`, `best`.

## Optimality

The per-layer scan is linear in grid size; `k` layers are required because each teleport can further relax costs. Without extra assumptions on value distribution, this is close to optimal for the stated DP model.

## Edge Cases

- `k == 0`: reduces to standard right/down path DP.
- `n == 1 && m == 1`: cost is 0 (already at target).
- Strictly increasing values along all right/down paths: teleports may be impossible if no lower/equal values exist.
- Large value range with small grid: `V` term can be notable; compressing values could reduce memory.

## Comparison Table

| Aspect | Layered DP with prefix minima (Solution) | Rolling-row DP with suffix minima (Solution2) |
| --- | --- | --- |
| Teleport handling | Prefix min over values `<= v` each layer | Suffix min array `sufMinF` updated per value | 
| Memory | `dp[n][m] + temp[V] + best[V]` | `O(n + V)` via rolling row `f` |
| Time | $O(k n m + k V)$ | $O(k n m + k V)$ similar order |
| Early stop | Fixed `k` iterations | Breaks early when no improvements |

## Key Snippet (Java)

```java
// Build reverse-prefix best costs by value
best[0] = temp[0];
for (int v = 1; v <= maxVal; v++) {
	best[v] = Math.min(best[v - 1], temp[v]);
}

// Relax grid with teleport option
for (int i = n - 1; i >= 0; i--) {
	for (int j = m - 1; j >= 0; j--) {
		if (i == n - 1 && j == m - 1) continue;
		int down = (i + 1 < n) ? dp[i + 1][j] + grid[i + 1][j] : Integer.MAX_VALUE;
		int right = (j + 1 < m) ? dp[i][j + 1] + grid[i][j + 1] : Integer.MAX_VALUE;
		int walk = Math.min(down, right);
		int teleport = best[grid[i][j]];
		dp[i][j] = Math.min(walk, teleport);
		if (dp[i][j] != Integer.MAX_VALUE) {
			temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
		}
	}
}
```

## Example Walkthrough

Grid:

```m
[5, 4]
[3, 1]
```

`k = 1`

- Without teleport: path 5 -> 4 -> 1 costs 4 + 1 = 5; path 5 -> 3 -> 1 costs 3 + 1 = 4.
- Teleport layer: from 5 we can teleport to any value <= 5; best cost among those values is 1 (at cell with value 1). Teleport then walk 0 cost more, so total becomes 1 (strictly better than walking). Answer: 1.

## Insights

- Value-based prefix minima compress the teleport choice set to $O(1)$ per cell after a linear preprocess per layer.
- Iterating `k` times acts like multi-source BFS in value-space, propagating improved costs from teleports.
- Early exit is possible when a relaxation pass makes no changes (as in Solution2).

## References

- Solution implementation in [3651. Minimum Cost Path with Teleportations/Solution.java](3651.%20Minimum%20Cost%20Path%20with%20Teleportations/Solution.java)
