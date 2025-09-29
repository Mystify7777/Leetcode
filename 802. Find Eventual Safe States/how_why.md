# How_Why.md – Find Eventual Safe States (LeetCode 802)

## ❌ Brute Force Idea

We want to find all nodes in a directed graph that are **eventually safe** – meaning if we start from them and follow outgoing edges, we will **never get stuck in a cycle**.

**Naïve thought:**

* For every node, run a DFS to check if it ends up in a cycle.
* If no cycle is detected → mark safe.
* Otherwise → unsafe.

**Why it fails:**

* This repeats work across multiple DFS calls.
* Cycle checks are re-done unnecessarily, leading to **O(n·(n+e))** complexity in worst cases.

---

## ✅ Your Approach (DFS + Coloring / State Marking)

You used **DFS with state marking**:

* Each node has a state:

  * `0` → unvisited
  * `1` → visiting (currently in recursion stack)
  * `2` → safe (already confirmed not leading to a cycle)

### Key Idea:

* If we visit a node already marked `1` → cycle detected → not safe.
* If DFS for all its children confirms safety → mark as `2` (safe).
* Safe nodes are collected into the result list.

This avoids re-checking cycles multiple times because **memoization via state[]** ensures each node is processed once.

### Complexity:

* **Time:** O(V + E), since each node and edge is visited at most once.
* **Space:** O(V) for recursion + state array.

---

## 🚀 Alternative Optimized Approach (Reverse Graph + Topological Sort)

Another clean method:

1. Build the reverse graph.
2. Start from **terminal nodes** (nodes with no outgoing edges).
3. Perform **Kahn’s algorithm (topological sort)** backwards.
4. Nodes that can reach a terminal node are safe.

This also gives **O(V+E)** performance, and avoids recursion stack issues.

---

## 🔎 Example Walkthrough

Graph:

```
graph = [[1,2],[2,3],[5],[0],[5],[],[]]
```

Meaning:

* Node 0 → {1,2}
* Node 1 → {2,3}
* Node 2 → {5}
* Node 3 → {0}
* Node 4 → {5}
* Node 5 → {}
* Node 6 → {}

---

### Step 1 – DFS(0)

* state[0] = 1 (visiting).
* Go to 1 → DFS(1).

### Step 2 – DFS(1)

* state[1] = 1.
* Go to 2 → DFS(2).

### Step 3 – DFS(2)

* state[2] = 1.
* Go to 5 → DFS(5).

### Step 4 – DFS(5)

* state[5] = 1.
* No outgoing edges → safe.
* state[5] = 2.

Back to 2 → safe since 5 is safe → state[2] = 2.

Back to 1 → next neighbor is 3 → DFS(3).

### Step 5 – DFS(3)

* state[3] = 1.
* Next neighbor is 0, but 0 is already `1` (visiting) → cycle detected.
* So 3 = unsafe.

Back to 1 → since 3 is unsafe → 1 = unsafe.

Back to 0 → since 1 is unsafe → 0 = unsafe.

### Step 6 – DFS(4)

* state[4] = 1.
* Goes to 5 (safe).
* So 4 = safe.

### Step 7 – DFS(6)

* No edges → safe.

---

### ✅ Final Safe Nodes

```java
[2,4,5,6]
```

---

## ✅ Key Takeaways

* **DFS + coloring** is efficient to detect cycles.
* **Safe nodes** = nodes that **cannot** reach a cycle.
* Alternative **topological approach** avoids recursion, better for very deep graphs.

---
