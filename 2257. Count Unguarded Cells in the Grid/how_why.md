# 2257. Count Unguarded Cells in the Grid — how and why

## Problem recap

You have an m × n grid with three kinds of cells:

- Guards at given positions
- Walls at given positions
- Empty cells

Each guard watches along its row and column in the four cardinal directions until a wall or another guard blocks the view. A cell is considered guarded if it lies in at least one guard’s line of sight. Count how many cells are unguarded (i.e., empty and not seen by any guard).

## Core intuition

This is a visibility problem with blockers. For each guard, sweep in the four directions and mark cells as guarded until you hit a wall or another guard. At the end, empty cells that were never marked are unguarded.

Two practical implementations:

- Mark-and-count at the end: mark all visible cells, then scan the grid to count unguarded
- Count-as-you-mark: keep a running count of newly marked cells to avoid a final scan

Both run in linear time in the size of the grid plus total sweep distance.

## Approach 1 — mark visibility and count at the end (matches `Solution.java`)

Represent the grid as a char matrix:

- `\0` for empty
- `'G'` for guard
- `'W'` for wall
- `'A'` for cells marked as guarded (attacked/visible)

From every guard, walk in each of the four directions until you hit a wall or another guard. Mark previously empty cells as `'A'`. Finally, count cells still `\0`.

### Implementation

```java
class Solution {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        char[][] grid = new char[m][n];

        // Place guards and walls
        for (int[] g : guards) grid[g[0]][g[1]] = 'G';
        for (int[] w : walls) grid[w[0]][w[1]] = 'W';

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

        // Sweep from each guard in 4 directions
        for (int[] g : guards) {
            for (int[] d : dirs) {
                int x = g[0] + d[0], y = g[1] + d[1];
                while (0 <= x && x < m && 0 <= y && y < n) {
                    // Blockers stop sight
                    if (grid[x][y] == 'W' || grid[x][y] == 'G') break;
                    // Mark empty cells as guarded
                    if (grid[x][y] == '\0') grid[x][y] = 'A';
                    x += d[0];
                    y += d[1];
                }
            }
        }

        // Count unguarded empties
        int unguarded = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '\0') unguarded++;
            }
        }
        return unguarded;
    }
}
```

## Approach 2 — faster counted sweep (more readable rewrite)

The commented “alternate fast approach” in your source keeps a running count of newly guarded cells and avoids the final grid-wide count. Here’s a clearer version with descriptive names and unified directional logic:

```java
import java.util.*;

class Solution {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        // 0 = empty, 1 = guarded, 2 = blocked (guard or wall)
        int[][] state = new int[m][n];

        for (int[] w : walls) state[w[0]][w[1]] = 2;
        for (int[] g : guards) state[g[0]][g[1]] = 2;

        int newlyGuarded = 0;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}}; // down, up, right, left

        for (int[] g : guards) {
            int gx = g[0], gy = g[1];
            for (int[] d : dirs) {
                int x = gx + d[0], y = gy + d[1];
                while (0 <= x && x < m && 0 <= y && y < n) {
                    if (state[x][y] == 2) break; // blocked by wall/guard
                    if (state[x][y] == 0) {      // first time this cell becomes guarded
                        state[x][y] = 1;
                        newlyGuarded++;
                    }
                    // if already 1 (guarded), just continue sweeping
                    x += d[0];
                    y += d[1];
                }
            }
        }

        int total = m * n;
        int numGuards = guards.length;
        int numWalls = walls.length;
        return total - (numGuards + numWalls + newlyGuarded);
    }
}
```

What changed vs the compact version:

- Descriptive names (`state`, `newlyGuarded`, `dirs`, `gx/gy`)
- Unified the four directional sweeps using a single `dirs` array
- Count a cell only when it transitions from 0 → 1 to avoid double counting
- Clear comments for each condition

## Why this works

Visibility from guards is monotonic along the four rays until a blocker is hit. Marking cells from each guard in each direction covers exactly the union of all visible cells. Counting unguarded cells is then:

- Total cells
- Minus guard cells
- Minus wall cells
- Minus cells marked as guarded by at least one sweep

The counted-sweep variant simply maintains the “guarded cells” count as it marks, avoiding a separate pass.

## Complexity

- Time: O(mn + G·L), where G is number of guards and L is average sweep length until a blocker or boundary. In dense blocker setups, L is small; in open grids, total work is still linear in the number of cells touched.
- Space: O(mn) for the grid/state array.

## Example

Consider m = 4, n = 6 with one guard at (2, 2) and walls at (1, 2) and (2, 4):

- From (2, 2), the upward ray is blocked by (1, 2)
- To the right, the ray stops before/at (2, 4) due to the wall
- Left and down rays mark all cells until the boundary

The unguarded count is total cells minus the union of marked cells and blockers.

## Edge cases to consider

- No guards: all non-wall cells are unguarded
- No walls: guards’ rays extend to borders
- Multiple guards with overlapping rays: overlaps are naturally handled (cells marked once)
- Guards adjacent to walls: rays in that direction stop immediately

## Takeaways

- A ray-sweep per guard with walls/guards as blockers solves this cleanly
- You can either mark then count, or count-as-you-mark to skip a final pass
- A small state encoding (0/1/2) keeps the implementation simple and fast

