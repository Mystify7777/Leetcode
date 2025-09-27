# How_Why.md – Course Schedule II (210)

## ❌ Brute Force Approach: Try All Permutations

### Idea

* Generate all permutations of courses.
* Check each one if it satisfies prerequisites.
* Very inefficient because permutations grow factorially: `O(n!)`.

### Limitations

* Works only for very small `numCourses`.
* Impossible for large inputs (e.g., 200 courses).

---

## ✅ Working Approach: DFS + Topological Sort (Your Code)

### Idea

1. Represent prerequisites as a **graph**:
   `graph.get(prev)` contains all courses that depend on `prev`.
2. Use DFS to detect **cycles**:

   * `visited[i] == 0` → unvisited.
   * `visited[i] == 1` → visiting (in current DFS path).
   * `visited[i] == 2` → visited (DFS finished for this node).
3. If a cycle is detected → no valid course order → return `[]`.
4. Otherwise, **add course to result after visiting all its dependencies** → topological order.
5. Reverse the result list because DFS adds nodes **post-order**.

### Example Walkthrough

#### Input

```
numCourses = 4
prerequisites = [[1,0],[2,0],[3,1],[3,2]]
```

Graph representation:

```
0 → 1, 2
1 → 3
2 → 3
3 → -
```

#### DFS Steps

1. Start at course `0`:

   * Mark `0` as visiting (`1`).
   * Visit neighbors `1` and `2`.

2. Visit course `1`:

   * Mark `1` as visiting.
   * Visit neighbor `3`.

3. Visit course `3`:

   * Mark `3` as visiting.
   * No neighbors.
   * Mark `3` as visited (`2`) and add to result: `[3]`.

4. Back to course `1`:

   * All neighbors processed.
   * Mark `1` as visited and add: `[3, 1]`.

5. Visit course `2`:

   * Mark `2` as visiting.
   * Visit neighbor `3` → already visited, skip.
   * Mark `2` as visited and add: `[3, 1, 2]`.

6. Back to course `0`:

   * All neighbors processed.
   * Mark `0` as visited and add: `[3, 1, 2, 0]`.

7. Reverse result → `[0, 2, 1, 3]` (valid order).

### Key Insights

* Adding to the result **after DFS** ensures all dependencies appear first.
* Using `visited` with three states prevents **cycles** and revisiting nodes.
* Reversing the list gives the correct **topological order**.

### Complexity

* **Time**: `O(V + E)` → each node and edge visited once.
* **Space**: `O(V)` for `visited` map + recursion stack + result list.

---

## ⚡ Notes on Alternative Implementation

* The second version uses arrays instead of lists/maps → lower overhead.
* Uses manual stack index (`top`) instead of reversing list at the end.
* Conceptually the same: DFS + cycle detection → topological order.

---
