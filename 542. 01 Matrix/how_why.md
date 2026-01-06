# 542. 01 Matrix

## Problem Statement

Given an `m x n` binary matrix `mat`, return the distance of the nearest `0` for each cell.

The distance between two adjacent cells is `1`.

**Example 1:**

```m
Input: mat = [[0,0,0],
              [0,1,0],
              [0,0,0]]
              
Output:       [[0,0,0],
              [0,1,0],
              [0,0,0]]
```

**Example 2:**

```m
Input: mat = [[0,0,0],
              [0,1,0],
              [1,1,1]]
              
Output:       [[0,0,0],
              [0,1,0],
              [1,2,1]]
```

---

## Approach 1: Multi-Source BFS (Optimal)

### Why This Approach?

- **Key Insight:** Instead of starting BFS from each `1` to find nearest `0`, we start from ALL `0`s simultaneously
- This is a **multi-source BFS** where all `0`s act as sources
- Each cell is visited only once, making it very efficient
- **Time Complexity:** O(m × n) - each cell visited once
- **Space Complexity:** O(m × n) - for the queue in worst case

### How It Works

```md
1. Initialize queue with ALL cells that contain 0
2. Set all cells with 1 to a large value (MAX_VALUE)
3. Perform BFS from all 0s simultaneously:
   - For each cell in queue, check all 4 neighbors
   - If neighbor's distance can be improved, update it and add to queue
   - Distance = current cell's distance + 1
4. Return the updated matrix
```

### Visual Example

**Initial Matrix:**

```c
0  0  0
0  1  0
1  1  1
```

**Step 1: Add all 0s to queue, set 1s to MAX_VALUE**

```c
0  0  0
0  ∞  0
∞  ∞  ∞

Queue: [(0,0), (0,1), (0,2), (1,0), (1,2)]
```

**Step 2: Process (0,0) - Check neighbors**

- Down (1,0): already 0, skip
- Right (0,1): already 0, skip

**Step 3: Process all 0s, update their neighbors**

```c
After processing all 0s:
0  0  0
0  1  0
1  2  1

Queue contains: [(1,1), (2,0), (2,2)]
```

**Step 4: Process (1,1) - distance 1**

- Down (2,1): current ∞ > 1 + 1, update to 2

**Final Result:**

```c
0  0  0
0  1  0
1  2  1
```

### Code Walkthrough

```java
public int[][] updateMatrix(int[][] mat) {
    if (mat == null || mat.length == 0 || mat[0].length == 0)
        return new int[0][0];

    int m = mat.length, n = mat[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int MAX_VALUE = m * n;  // Maximum possible distance
    
    // Step 1: Initialize - add all 0s to queue, set 1s to MAX_VALUE
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) {
                queue.offer(new int[]{i, j});
            } else {
                mat[i][j] = MAX_VALUE;
            }
        }
    }
    
    // Four directions: down, up, right, left
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
    // Step 2: BFS from all 0s
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        
        // Check all 4 neighbors
        for (int[] dir : directions) {
            int r = cell[0] + dir[0];
            int c = cell[1] + dir[1];
            
            // If valid cell and distance can be improved
            if (r >= 0 && r < m && c >= 0 && c < n && 
                mat[r][c] > mat[cell[0]][cell[1]] + 1) {
                
                queue.offer(new int[]{r, c});
                mat[r][c] = mat[cell[0]][cell[1]] + 1;
            }
        }
    }
    
    return mat;
}
```

### Key Points

1. **Multi-Source BFS:**
   - All `0`s start as sources simultaneously
   - Like ripples spreading from multiple points at once
   - Guarantees shortest path to nearest `0`

2. **Why MAX_VALUE = m * n?**
   - Maximum distance in matrix is at most `m + n - 2` (corner to corner)
   - Using `m * n` ensures it's always larger than any actual distance

3. **Distance Update Condition:**
   - Only update if `mat[r][c] > mat[current] + 1`
   - This ensures we only update when we find a shorter path
   - Prevents revisiting cells unnecessarily

---

## Approach 2: Dynamic Programming (Two-Pass)

### Why This Approach?*

- No extra space for queue needed
- Updates matrix in-place with two passes
- **Time Complexity:** O(m × n) - two complete passes
- **Space Complexity:** O(1) - no extra space (in-place)

### How It Works*

```md
1. First Pass (Top-Left to Bottom-Right):
   - For each cell, check top and left neighbors
   - Update distance as min(top, left) + 1
   
2. Second Pass (Bottom-Right to Top-Left):
   - For each cell, check bottom and right neighbors
   - Update distance as min(current, min(bottom, right) + 1)
```

### Visual Example*

**Initial Matrix:**

```c
0  0  0
0  1  0
1  1  1
```

**After Pass 1 (Top-Left → Bottom-Right):**

```c
0  0  0
0  1  0
1  2  1
```

- Cell (2,1): min(top=1, left=1) + 1 = 2

**After Pass 2 (Bottom-Right → Top-Left):**

```c
0  0  0
0  1  0
1  2  1
```

- Already optimal, no changes needed

### Code Implementation

```java
public int[][] updateMatrix(int[][] matrix) {
    final int m = matrix.length;
    final int n = matrix[0].length;
    final int INF = m + n + 1;
    
    // Pass 1: Top-left to bottom-right
    // Check top and left neighbors
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] == 1) {
                int top = (i > 0) ? matrix[i-1][j] : INF;
                int left = (j > 0) ? matrix[i][j-1] : INF;
                matrix[i][j] = Math.min(top, left) + 1;
            }
        }
    }
    
    // Pass 2: Bottom-right to top-left
    // Check bottom and right neighbors
    for (int i = m - 1; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--) {
            if (matrix[i][j] > 1) {
                int bottom = (i < m - 1) ? matrix[i+1][j] : INF;
                int right = (j < n - 1) ? matrix[i][j+1] : INF;
                matrix[i][j] = Math.min(matrix[i][j], 
                                       Math.min(bottom, right) + 1);
            }
        }
    }
    
    return matrix;
}
```

---

## Comparison: BFS vs DP

| Aspect | Multi-Source BFS | Dynamic Programming |
|--------|-----------------|---------------------|
| **Time** | O(m × n) | O(m × n) |
| **Space** | O(m × n) queue | O(1) in-place |
| **Intuition** | Spreading from sources | Propagating from neighbors |
| **Passes** | 1 (BFS traversal) | 2 (forward + backward) |
| **Best For** | When extra space is acceptable | Space-constrained environments |

---

## Time & Space Complexity Analysis

### BFS Approach

| Aspect | Complexity | Explanation |
|--------|-----------|-------------|
| **Time** | O(m × n) | Each cell is enqueued and processed once |
| **Space** | O(m × n) | Queue can contain all cells in worst case |

### DP Approach

| Aspect | Complexity | Explanation |
|--------|-----------|-------------|
| **Time** | O(m × n) | Two complete passes through matrix |
| **Space** | O(1) | Updates in-place, no extra data structures |

---

## Edge Cases to Consider

1. **All zeros:**

   ```c
   [[0,0,0],
    [0,0,0]]
   ```

   Output: Same as input (all distances are 0)

2. **Single cell:**
   - `[[0]]` → `[[0]]`
   - `[[1]]` → `[[0]]` (distance to itself is 0? Actually this case needs clarification)

3. **All ones except one zero:**

   ```c
   [[1,1,1],
    [1,0,1],
    [1,1,1]]
   ```

   Maximum distance will be 2 at corners

4. **Large matrix:**
   - BFS handles efficiently with O(m × n) time
   - DP uses less space

5. **Alternating pattern:**

   ```c
   [[0,1,0],
    [1,0,1],
    [0,1,0]]
   ```

   Each 1 is adjacent to a 0, so all distances are 1

---

## Why Not DFS from Each 1?

**Naive Approach Issues:**

- DFS/BFS from each cell with `1` to find nearest `0`
- **Time Complexity:** O(m × n × m × n) = O((m×n)²) in worst case
- Very inefficient when matrix is mostly 1s

**Multi-Source BFS Advantage:**

- Start from all sources (0s) at once
- Each cell visited only once
- Guaranteed shortest distance on first visit

---

## Related Problems

```md
- 286. Walls and Gates (Premium) - Similar multi-source BFS
- 994. Rotting Oranges - Multi-source BFS concept
- 1162. As Far from Land as Possible - Water to land distance
- 317. Shortest Distance from All Buildings (Premium)
- 847. Shortest Path Visiting All Nodes
```
