
# How_Why.md – Pacific Atlantic Water Flow (417)

## ❌ Brute Force Approach: Start From Each Cell

### Idea

* For each cell `(i, j)`:

  * Perform a DFS/BFS to check if water can flow to **Pacific** (top/left edges).
  * Perform a DFS/BFS to check if water can flow to **Atlantic** (bottom/right edges).
* If both are reachable, add `(i, j)` to result.

### Example Walkthrough

Matrix:

```
heights = [
  [1, 2, 2, 3, 5],
  [3, 2, 3, 4, 4],
  [2, 4, 5, 3, 1],
  [6, 7, 1, 4, 5],
  [5, 1, 1, 2, 4]
]
```

* Cell `(0,4)` (height 5):

  * Can flow left to Pacific.
  * Already on Atlantic boundary.
  * ✅ Include `(0,4)`.

### Complexity

* **Time**: `O((rows * cols) * (rows * cols))` → Each cell explores the whole grid in worst case.
* **Space**: `O(rows * cols)` recursion/queue storage.

👉 Not efficient, will time out for large grids.

---

## ✅ Optimized Approach (Your Code): Reverse DFS from Oceans

### Idea

* Instead of checking from each cell → oceans, **reverse the search**:

  * Start from **Pacific edge cells**, DFS inward to mark all cells reachable by Pacific.
  * Start from **Atlantic edge cells**, DFS inward to mark all cells reachable by Atlantic.
* Any cell marked by both → water flows to both oceans.

```java
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];
        
        for (int col = 0; col < cols; col++) {
            dfs(0, col, pacific, heights[0][col], heights);          // Pacific top
            dfs(rows - 1, col, atlantic, heights[rows - 1][col], heights); // Atlantic bottom
        }
        for (int row = 0; row < rows; row++) {
            dfs(row, 0, pacific, heights[row][0], heights);          // Pacific left
            dfs(row, cols - 1, atlantic, heights[row][cols - 1], heights); // Atlantic right
        }
        
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }
    
    private void dfs(int row, int col, boolean[][] visited, int prevHeight, int[][] heights) {
        if (row < 0 || row >= heights.length || col < 0 || col >= heights[0].length 
            || visited[row][col] || prevHeight > heights[row][col]) return;
        
        visited[row][col] = true;
        dfs(row + 1, col, visited, heights[row][col], heights);
        dfs(row - 1, col, visited, heights[row][col], heights);
        dfs(row, col + 1, visited, heights[row][col], heights);
        dfs(row, col - 1, visited, heights[row][col], heights);
    }
}
```

### Example Walkthrough

From above matrix:

1. Start from **Pacific edges** (top/left): mark flowable cells (increasing heights).
2. Start from **Atlantic edges** (bottom/right): mark flowable cells.
3. Overlap =

```
[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
```

### Complexity

* **Time**: `O(rows * cols)` → each cell visited at most twice (once per ocean).
* **Space**: `O(rows * cols)` for visited arrays + recursion stack.

👉 Much more efficient.

---

## 🚀 Optimized Discussion

* DFS and BFS both work equally well (DFS recursion, BFS queue).
* Further optimization is unnecessary since we already achieve linear time relative to grid size.

✅ **Final Recommendation**: Use **reverse DFS/BFS from oceans** → optimal solution.

---
