
# 994. Rotting Oranges — how/why

## Recap

You are given a grid with values:

- `0` = empty cell
- `1` = fresh orange
- `2` = rotten orange

Each minute, any fresh orange adjacent (up/down/left/right) to a rotten orange becomes rotten. Return the minimum minutes needed to rot all fresh oranges, or `-1` if impossible.

## Intuition

All initially rotten oranges spread rot to neighbors simultaneously each minute. This is a classic **multi-source BFS**:

- Enqueue all starting sources (all `2`s) at time 0.
- Expand in waves (levels). Each level corresponds to one minute.
- Track how many fresh oranges remain; when the queue finishes, if any fresh are left, it’s impossible.

## Approach (Multi-source BFS)

1) Scan the grid once:
   - Count `fresh` oranges.
   - Push all initially rotten cells `(r, c)` into a queue.
2) If `fresh == 0`, return `0` (nothing to rot).
3) BFS loop while queue not empty:
   - Process the current queue size as one “minute” (level).
   - For each cell, try its 4 neighbors; if neighbor is fresh, turn it rotten, decrement `fresh`, and enqueue it.
   - After finishing this level, increment `minutes`.
4) If `fresh == 0`, return `minutes`; else return `-1`.

Why level-order? All infections in the same minute occur in parallel; using the queue size as the boundary accurately models that simultaneous spread.

## Code (Java)

```java
class Solution {
	public int orangesRotting(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();
		int fresh = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 2) q.offer(new int[]{i, j});
				else if (grid[i][j] == 1) fresh++;
			}
		}

		if (fresh == 0) return 0;

		int minutes = 0;
		int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

		while (!q.isEmpty()) {
			int size = q.size();
			boolean progressed = false;
			for (int s = 0; s < size; s++) {
				int[] cur = q.poll();
				for (int[] d : dirs) {
					int r = cur[0] + d[0], c = cur[1] + d[1];
					if (r < 0 || r >= m || c < 0 || c >= n) continue;
					if (grid[r][c] != 1) continue;
					grid[r][c] = 2; // rot it
					fresh--;
					progressed = true;
					q.offer(new int[]{r, c});
				}
			}
			if (progressed) minutes++;
		}

		return fresh == 0 ? minutes : -1;
	}
}
```

## Correctness

- Multi-source: starting with all rotten cells ensures the first wave models minute 1 infections correctly.
- BFS levels = minutes: each breadth level expands to immediate neighbors, matching the “one minute per edge” propagation.
- Termination: we either infect all reachable fresh cells or the queue empties with some fresh isolated by empties → return `-1`.

## Complexity

- Time: `O(m*n)` — each cell enqueued/dequeued at most once.
- Space: `O(m*n)` in worst case for the queue.

## Edge Cases

- No fresh oranges → 0 minutes.
- No rotten sources but fresh exist → `-1` (no spread possible).
- Islands of fresh separated by empties → unreachable fresh remain → `-1`.
- Single row/column grids still follow the same BFS logic.

## Alternatives / Notes

- You can track minutes by storing `(r, c, t)` in the queue; the level-based approach avoids extra fields and is slightly cleaner.
- Modifying the grid in place (marking `1 -> 2`) avoids a separate visited structure.
