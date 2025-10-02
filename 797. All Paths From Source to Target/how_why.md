# How_Why.md – All Paths From Source to Target (LeetCode 797)

## ❌ Brute Force Idea

We’re asked to find all possible paths from node `0` (source) to node `n-1` (target) in a **DAG** (directed acyclic graph).

**Naive approach:**

* Enumerate every possible sequence of nodes starting at `0`.
* Check if it ends at `n-1`.
* If yes, add it to the answer.

Example: For `graph = [[1,2],[3],[3],[]]`
Possible sequences starting at `0`:

* 0 → 1 → 3 ✅
* 0 → 2 → 3 ✅
* 0 → 1 → 2 (dead end ❌)

This works but is very inefficient since it explores invalid paths unnecessarily.

* **Time:** exponential (up to O(2^n)).
* **Space:** O(n) recursion depth.

---

## ✅ Optimized Approach – DFS with Backtracking

Since the graph is a DAG:

* Start DFS from node `0`.
* Maintain a **path list** of visited nodes.
* When you reach target `n-1`, add a copy of the path to the result.
* Use **backtracking**: remove the last node after exploring a branch.

---

### Your Implementation

```java
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> ans = new LinkedList();
        List<Integer> current = new ArrayList();
        current.add(0);
        dfs(0, current, graph, graph.length - 1, ans);
        return ans; 
    }

    private void dfs(int src, List<Integer> current, int[][] graph, int dest, List<List<Integer>> ans) {
        if (src == dest) {
            ans.add(new ArrayList(current)); // add a copy
            return;
        }
        for (int n : graph[src]) {
            current.add(n);
            dfs(n, current, graph, dest, ans);
            current.remove(current.size() - 1); // backtrack
        }
    }
}
```

---

### Alternative Equivalent Version

```java
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(0);
        dfs(0, graph, path, res);
        return res;
    }

    private void dfs(int node, int[][] graph, List<Integer> path, List<List<Integer>> res) {
        if (node == graph.length - 1) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int nei : graph[node]) {
            path.add(nei);
            dfs(nei, graph, path, res);
            path.remove(path.size() - 1);
        }
    }
}
```

Both do the same thing — just minor differences in how parameters are passed.

---

## 🔎 Example Walkthrough

Input:

```
graph = [[1,2],[3],[3],[]]
```

1. Start path: `[0]`
   Neighbors of 0 → [1,2]

2. Go to 1 → path `[0,1]`
   Neighbors → [3]
   → Go to 3 → path `[0,1,3]`
   Found target! ✅ Add to result.

3. Backtrack, now from 0 → 2 → path `[0,2]`
   Neighbors → [3]
   → Go to 3 → path `[0,2,3]`
   Found target! ✅ Add to result.

Final output:

```
[[0,1,3], [0,2,3]]
```

---

## 📊 Complexity

* **Time:** O(2^n) worst-case (since all paths must be explored).
* **Space:** O(n) recursion depth + O(paths) for storing results.

---

## ✅ Key Takeaways

* DFS + backtracking is the standard pattern for **all path-finding problems** in DAGs.
* Use `new ArrayList<>(path)` to store a *copy* of the path at the target, not the reference.
* Since graph is a DAG, we don’t need a visited set (no cycles).

---
