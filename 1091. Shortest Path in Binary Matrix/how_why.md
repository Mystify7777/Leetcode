# 1091. Shortest Path in Binary Matrix — how/why

## Recap

Given an `n x n` binary grid where `grid[r][c] == 0` denotes an open cell and `1` denotes blocked, find the length of the shortest clear path from the top-left `(0,0)` to the bottom-right `(n-1,n-1)`. Moves are allowed in 8 directions (including diagonals). Return `-1` if no such path exists.

Path length counts the number of cells on the path (so the starting cell counts as 1).

## Intuition

This is a shortest-path-in-unweighted-graph problem. Each cell is a node; edges connect to up to eight neighbors. The optimal tool is **BFS** because it explores in increasing distance layers and guarantees the first time we reach the end is via a shortest path.

## Approach (Level-order BFS, matching `Solution`)

1) Quick rejects: if grid is empty, or `grid[0][0] == 1`, or `grid[n-1][n-1] == 1`, return `-1`.
2) Initialize a queue with `(0,0)` and a `visited` boolean matrix; set `visited[0][0] = true`.
3) Store the 8-direction vectors: `(-1,-1),(-1,0),(-1,1),(0,-1),(0,1),(1,-1),(1,0),(1,1)`.
4) Run BFS by levels: for each layer size, increment `pathLen`, and expand all nodes in that layer.
   - If we pop `(r,c)` and it equals `(n-1,n-1)`, return `pathLen` — first hit is shortest.
   - For each neighbor `(nr,nc)`, if in bounds, not visited, and `grid[nr][nc] == 0`, mark visited and enqueue.
5) If the queue empties without reaching the end, return `-1`.

This mirrors the provided `Solution` with a `Queue<int[]>`, `visited` grid, 8-direction array, and `pathLen` incremented per level.

### Why level-order counting works

Each BFS layer represents all cells at the same path length from the start. Incrementing `pathLen` once per layer means when you dequeue the target, the current `pathLen` is exactly the number of steps (cells) on the shortest path.

## Code sketch (Java)

```java
int shortestPathBinaryMatrix(int[][] grid) {
	int n = grid.length;
	if (n == 0 || grid[0].length == 0) return -1;
	if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

	int[][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
	boolean[][] vis = new boolean[n][n];
	ArrayDeque<int[]> q = new ArrayDeque<>();
	q.offer(new int[]{0,0});
	vis[0][0] = true;

	int dist = 0;
	while (!q.isEmpty()) {
		int sz = q.size();
		dist++;
		for (int s = 0; s < sz; s++) {
			int[] cur = q.poll();
			int r = cur[0], c = cur[1];
			if (r == n-1 && c == n-1) return dist;
			for (int[] d : dirs) {
				int nr = r + d[0], nc = c + d[1];
				if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
				if (vis[nr][nc] || grid[nr][nc] == 1) continue;
				vis[nr][nc] = true;
				q.offer(new int[]{nr, nc});
			}
		}
	}
	return -1;
}
```

## Correctness

- Unweighted shortest path: BFS explores in rings around the start; first time we reach `(n-1,n-1)` is optimal.
- 8-direction adjacency is enforced by the direction list; blocked cells (`1`) and visited cells are skipped.
- Level counting ensures the returned `dist` is the number of cells in the path.

## Complexity

- Time: `O(n^2)` — each cell is enqueued at most once; each has up to 8 neighbor checks.
- Space: `O(n^2)` — `visited` matrix and queue in the worst case.

## Edge Cases

- Start or end blocked → `-1`.
- `n == 1`: if `grid[0][0] == 0`, answer is `1` immediately; if blocked, `-1`.
- Narrow paths with diagonals only: covered because diagonals are allowed.

## Alternate Approach (Bidirectional BFS, see `Solution2`)

An optimized alternative runs BFS from both ends simultaneously and stops when frontiers meet. The included `Solution2`:

- Marks cells visited by the start-side with `'s'` and by the end-side with `'e'` directly in `grid` to avoid extra memory.
- Alternates expanding each frontier one level at a time, incrementing the step count per expansion, and returns when a cell marked by the opposite frontier is reached.

Pros:

- Often faster in practice by shrinking the explored area (roughly square root of states).
- Avoids a separate `visited` grid by in-place marking.

Cons:

- Slightly more complex bookkeeping (two queues, step synchronization, sentinel marks overwriting `grid`).
- Still `O(n^2)` worst-case time/space.

Both single-source BFS and bidirectional BFS produce the same shortest path length; choose the simpler single-source version unless performance dictates otherwise.
