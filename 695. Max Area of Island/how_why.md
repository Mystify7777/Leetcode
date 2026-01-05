# How_Why.md: Max Area of Island

## Problem

Given an `m x n` grid of `0`s (water) and `1`s (land), return the **maximum area** of an island. An island is formed by **4-directionally** connected `1`s. If no land, return `0`.

**Example:**

```java
Input:  grid = [[0,0,1,0,0],
                [0,1,1,1,0],
                [0,0,0,0,0],
                [1,1,0,0,1]]
Output: 5
```

---

## DFS (In-Place Mark) — Straightforward

### Idea

* Iterate every cell; when you find land, launch a **DFS** to count that island's area.
* Mark visited cells as `0` (or use a visited array) to avoid recounting.
* Track the maximum area seen.

### Code

```java
public int maxAreaOfIslandDFS(int[][] grid) {
	int m = grid.length, n = grid[0].length, best = 0;
	for (int r = 0; r < m; r++) {
		for (int c = 0; c < n; c++) {
			if (grid[r][c] == 1) {
				best = Math.max(best, dfs(grid, r, c));
			}
		}
	}
	return best;
}

private int dfs(int[][] g, int r, int c) {
	if (r < 0 || c < 0 || r >= g.length || c >= g[0].length || g[r][c] == 0) return 0;
	g[r][c] = 0; // mark visited
	int area = 1;
	area += dfs(g, r + 1, c);
	area += dfs(g, r - 1, c);
	area += dfs(g, r, c + 1);
	area += dfs(g, r, c - 1);
	return area;
}
```

### Notes

* In-place marking saves extra space; recursion stack is O(mn) worst case (all land).
* Time: O(mn) since each cell is visited once.

---

## BFS Flood Fill (Your Approach)

### Idea_

* Use a **queue** to flood-fill each discovered island iteratively.
* Pop cells, push unvisited land neighbors, accumulate area, and mark visited in-place.

### Code_

```java
public int maxAreaOfIsland(int[][] grid) {
	int m = grid.length, n = grid[0].length, best = 0;
	int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

	for (int r = 0; r < m; r++) {
		for (int c = 0; c < n; c++) {
			if (grid[r][c] == 1) {
				int area = 0;
				Deque<int[]> q = new ArrayDeque<>();
				q.offer(new int[]{r, c});
				grid[r][c] = 0;

				while (!q.isEmpty()) {
					int[] cell = q.poll();
					area++;
					for (int[] d : dirs) {
						int nr = cell[0] + d[0], nc = cell[1] + d[1];
						if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
							grid[nr][nc] = 0;
							q.offer(new int[]{nr, nc});
						}
					}
				}

				best = Math.max(best, area);
			}
		}
	}
	return best;
}
```

### Why This Works_

* Each land cell enters the queue once → **O(mn)** time.
* In-place marking keeps extra space to **O(min(mn, queue width))**; queue width bounded by island perimeter.

---

## Key Takeaways

1. Flood fill via DFS or BFS; both visit each cell once for **O(mn)** time.
2. Mark in-place to avoid extra visited structure; ensures no double counting.
3. Stack depth for DFS vs queue size for BFS; pick based on recursion limits or style.

---
