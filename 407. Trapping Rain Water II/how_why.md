# How_Why.md – Trapping Rain Water II (LeetCode 407)

## ❌ Brute Force Idea

Think of each cell:

* Look up, down, left, right to find the **maximum boundary height** in each direction.
* The water level at that cell is `min(maxLeft, maxRight, maxUp, maxDown)`.
* Trapped water = water level - height (if > 0).

⚠️ Issue: Finding max in each direction for every cell is costly → `O(m*n * (m+n))`, too slow.

---

## ✅ Optimized Idea – Boundary-first BFS + Min-Heap

Instead of checking each cell independently, flip the perspective:

* Water is limited by the **lowest boundary** around it.
* Start from the **outer boundary** (since they can’t trap water).
* Always expand from the **lowest cell on the boundary** inward using a min-heap.

### Intuition

* Like filling a container from outside → the lowest “wall” defines how much water flows inward.
* If a neighbor cell is lower, it traps water up to the current boundary’s height.
* Otherwise, that neighbor becomes the new boundary.

---

## 🚀 Step-by-step Algorithm

1. **Initialize**:

   * A min-heap priority queue storing `[height, row, col]`.
   * Mark all boundary cells as visited and push them into the PQ.

2. **Process with BFS**:

   * Pop the smallest height cell from PQ.
   * Check its neighbors:

     * If neighbor is lower → water trapped = `currentHeight - neighborHeight`.
     * Update neighbor’s effective boundary height = `max(currentHeight, neighborHeight)`.
   * Push neighbor into PQ and mark visited.

3. **Accumulate** trapped water volume.

---

### Example Walkthrough

```
heightMap =
[
  [1,4,3,1,3,2],
  [3,2,1,3,2,4],
  [2,3,3,2,3,1]
]
```

* Put all boundary cells into PQ. Lowest boundary is `(0,0)=1`.
* Expand inward.
* Cell `(1,1)` has height 2, surrounded by boundary 3 → traps `3-2=1`.
* Cell `(1,2)` has height 1, boundary 3 → traps `3-1=2`.
* Continue until all are processed.
* Final water trapped = **4**.

---

## 📄 Code (PriorityQueue + BFS)

```java
class Solution {
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length, n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);

        // Add all boundary cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i==0 || i==m-1 || j==0 || j==n-1) {
                    pq.offer(new int[]{heightMap[i][j], i, j});
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        int trapped = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int h = cur[0], r = cur[1], c = cur[2];

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr>=0 && nr<m && nc>=0 && nc<n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    if (heightMap[nr][nc] < h) {
                        trapped += h - heightMap[nr][nc];
                        pq.offer(new int[]{h, nr, nc});
                    } else {
                        pq.offer(new int[]{heightMap[nr][nc], nr, nc});
                    }
                }
            }
        }

        return trapped;
    }
}
```

---

## 📊 Complexity

* **Time:** `O(m*n log(m*n))`

  * Each cell is pushed/popped once into the PQ, and PQ operations are `log(m*n)`.
* **Space:** `O(m*n)` for visited + PQ.

---

## ✅ Key Takeaways

* This is a **2D extension** of the “Trapping Rain Water” problem.
* Always expand from the **lowest boundary** inward (heap guarantees this).
* Think in terms of **water flow from outside in**, not from inside out.

---
