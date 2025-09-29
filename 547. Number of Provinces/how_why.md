# How_Why.md – Number of Provinces (LeetCode 547)

## ❌ Brute Force Idea

The naive approach would be:

1. For each city `i`, check if it is directly or indirectly connected to another city `j`.
2. Perform repeated scanning to build groups of connected cities.
3. Count how many disjoint groups exist.

### Why it’s inefficient:

* Each time you try to check connections, you might end up scanning the entire adjacency matrix repeatedly.
* Complexity can blow up to **O(n³)** in worst cases.

---

## ✅ Your Approach (DFS on Adjacency Matrix)

You implemented:

1. Use a **visited[] array** to track which cities have already been assigned to a province.
2. For each unvisited city:

   * Increase the province count.
   * Run DFS to mark all directly/indirectly connected cities as visited.
3. Continue until all cities are visited.

### Why this works:

* The problem is equivalent to counting **connected components in an undirected graph**.
* Each `M[i][j] == 1` represents an edge between city `i` and city `j`.
* DFS traversal ensures that all cities in the same province are discovered before moving to the next unvisited city.
* Complexity: **O(n²)** (since the adjacency matrix has `n²` entries).

---

## 🚀 Optimized Approaches

1. **BFS Instead of DFS**

   * Same complexity, but uses a queue instead of recursion (avoids stack overflow for very large `n`).
2. **Union-Find (Disjoint Set Union – DSU)**

   * Each city starts in its own set.
   * For every connection `(i, j)` with `M[i][j] == 1`, perform `union(i, j)`.
   * At the end, the number of distinct parents = number of provinces.
   * Time complexity: ~ **O(n² α(n))**, where α(n) is the inverse Ackermann function (very small, almost constant).
   * Often considered the cleanest and most scalable approach.

---

## 🔎 Example Walkthrough

Input:

```java
M = [
 [1,1,0],
 [1,1,0],
 [0,0,1]
]
```

### Step 1 – Initialize

* `visited = [false, false, false]`
* `count = 0`

### Step 2 – Iterate cities

* City `0` not visited → start DFS → visit `0` and `1` → mark `[true, true, false]` → count = 1
* City `1` already visited → skip.
* City `2` not visited → start DFS → visit `2` → count = 2

### Step 3 – Done

Final Answer = `2` provinces.

---

## ✅ Key Takeaways

* Brute force checking leads to **O(n³)** scans.
* DFS/BFS solution → **O(n²)** (good enough since input is matrix-based).
* Union-Find is often considered the most elegant optimized solution.

---
