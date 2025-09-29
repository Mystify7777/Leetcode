# How_Why.md – Minimum Height Trees (LeetCode 310)

## ❌ Brute Force Idea

For every node:

* Treat it as a root.
* Do a BFS/DFS to compute tree height.
* Track the node(s) with minimum height.

**Why it’s bad:**

* Each BFS = O(n + e). Doing this for all nodes → O(n²).
* With `n` up to **2e4**, this will TLE.

---

## ✅ Your Approach (Topological Trimming / Leaf Removal)

This is basically a **reverse BFS**:

1. Build adjacency list for the graph.
2. Start with all **leaves** (nodes with degree 1).
3. Iteratively remove leaves layer by layer.
4. The last remaining **1 or 2 nodes** are the root(s) of the minimum height tree(s).

This works because:

* The "center(s)" of a tree minimize height.
* Removing leaves layer by layer converges to the center.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

---

## 🚀 Other Optimized Approaches

1. **Diameter Approach (Longest Path Method)**

   * Find the tree diameter (longest path).
   * The center(s) of this path are the MHT roots.
   * Also O(n), but trickier to implement.

2. **DP on Trees**

   * Root tree arbitrarily, compute subtree heights, then re-root dynamically.
   * Also O(n), but more complex than leaf-trimming.

---

## 🔎 Example Walkthrough

Input:

```c
n = 6
edges = [[0,3],[1,3],[2,3],[4,3],[5,4]]
```

Graph looks like:

```c
   0   1   2
    \  |  /
      (3)
       |
      (4)
       |
      (5)
```

### Step 1 – Initial leaves

Leaves = `[0,1,2,5]`.

### Step 2 – Trim leaves

* Remove `[0,1,2,5]`.
* Remaining = `[3,4]`.

### Step 3 – Next leaves

* After removal, `[3,4]` are left.

### Step 4 – Stop

Remaining nodes = `[3,4]`.
These are the centers → roots of minimum height trees.

Answer:

```
[3, 4]
```

---

## ✅ Key Takeaways

* Brute force BFS from all nodes is too slow.
* Trimming leaves layer by layer is optimal and elegant.
* Final 1–2 nodes are always the centers of MHTs.

---
