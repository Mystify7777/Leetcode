
# How_Why.md – Shortest Bridge (LeetCode 934)

## ❌ Brute Force Idea

The naive idea would be:

1. Identify both islands separately.
2. For every cell of the first island, compute the Manhattan distance to every cell of the second island.
3. Take the minimum distance – 1 as the answer.

### Why it’s inefficient

* Suppose the grid is `n × n`.
* If each island covers ~n²/2 cells, the pairwise distance computation is **O(n⁴)**.
* Completely infeasible for grids up to 100×100.

---

## ✅ Your Approach (DFS + BFS)

You used a **two-phase algorithm**:

1. **DFS Phase**

   * Find one island (first `1` you encounter).
   * Perform DFS to mark all connected land (`grid[i][j] = -1`) and store them in a queue.
   * Now the queue contains all boundary cells of the first island.

2. **BFS Phase**

   * Start BFS from all cells of the first island simultaneously (multi-source BFS).
   * Expand level by level through water (`0`), marking visited as `-1`.
   * When BFS touches the second island (`1`), the current BFS level is the minimum number of `0`s to flip.

### Why this works better

* DFS ensures we capture the *entire first island*.
* BFS guarantees the shortest path (minimum flips) since it explores in layers.
* Complexity: **O(n²)**

  * DFS visits each cell once.
  * BFS visits each cell once.
  * Much faster than brute force.

---

## 🚀 Optimized Approach (Still DFS + BFS but with refinements)

Even though your solution is already efficient, optimizations include:

1. **Bitmask visited tracking** instead of modifying `grid`. (Saves restoring grid later.)
2. **Early stopping BFS** when the second island is found (which you already do).
3. **Avoid full DFS if islands are small** by breaking once the first island is captured.

Time complexity is still **O(n²)**, but with reduced constants.

---

## 🔎 Example Walkthrough

Input:

```java
grid = [
 [0,1,0],
 [0,0,0],
 [0,0,1]
]
```

### Step 1 – DFS to mark first island

* Start at `(0,1)` → DFS marks this cell as `-1`.
* Queue = `[(0,1)]`.

### Step 2 – BFS expansion

* **Level 0**: queue = `[(0,1)]`. Expand to water `(1,1), (0,0), (0,2)`.
* **Level 1**: queue = `[(1,1), (0,0), (0,2)]`. Expand further.
* **Level 2**: eventually reach `(2,2)` which is the second island.

Answer = `2`.

---

## ✅ Key Takeaways

* Brute force (all pairs) → **O(n⁴)** → impractical.
* DFS + BFS (multi-source) → **O(n²)** → optimal.
* BFS guarantees the minimum number of flips since it explores shortest paths first.

---
