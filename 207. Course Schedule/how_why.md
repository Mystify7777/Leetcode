# How\_Why.md — Course Schedule (LeetCode 207)

## ❌ Brute Force (Baseline)

### Idea

* Try to simulate course completion by repeatedly checking all prerequisites until no more progress can be made.
* If you can finish all courses, return true.

### Limitation

* Time complexity could explode (`O(V*E)`) because you repeatedly scan edges.
* Doesn’t scale for large inputs.

---

## ⚡ Approach 1: BFS (Kahn’s Algorithm for Topological Sort) ✅ (your first code)

### Idea

* Model courses as nodes in a directed graph.
* `prerequisite -> course` is a directed edge.
* Compute **indegree** (number of prerequisites) for each course.
* Start with nodes of indegree `0` (no prerequisites).
* Repeatedly remove them and decrease indegree of their neighbors.
* If you can process all nodes → no cycle → return true.

### Example Walkthrough

Input: `n=2, prereq=[[1,0]]`

* indegree = \[0,1], adj\[0] = \[1].
* Queue = \[0]. Process → remove 0, reduce indegree\[1] → \[0,0], queue=\[1].
* Process 1 → done. All courses taken → return true.

If a cycle exists (e.g. `[[1,0],[0,1]]`), indegree never reduces to zero for some nodes → return false.

### Complexity

* **Time:** `O(V+E)` (visit each node & edge once).
* **Space:** `O(V+E)` (graph + indegree + queue).

---

## ⚡ Approach 2: DFS Cycle Detection ✅ (your second code)

### Idea

* Another way: check for cycles directly.
* Use `visited[]` to mark if a node was processed.
* Use `onPath[]` to track recursion stack.
* If you encounter a node that is already on the current DFS path → cycle exists → return false.

### Example Walkthrough

Input: `n=2, prereq=[[1,0],[0,1]]`

* Start DFS from `0 → 1 → 0` (already on path) → cycle → return false.

Input: `n=2, prereq=[[1,0]]`

* DFS from 0 → 1, no cycle → return true.

### Complexity

* **Time:** `O(V+E)` (each node and edge visited once).
* **Space:** `O(V+E)` recursion stack in worst case.

---

## 🏆 Summary

* **Brute force:** `O(V*E)` worst case, too slow.
* **BFS (Kahn’s):** clean, iterative, checks indegree; great for topological ordering problems.
* **DFS cycle detection:** intuitive, recursive; directly finds cycles.

👉 Both are accepted and optimal (`O(V+E)`), but:

* Use **BFS (Kahn’s)** if you also want the actual **course order**.
* Use **DFS** if the question is only about **detecting cycles**.

---
