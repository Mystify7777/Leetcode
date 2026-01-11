# 684. Redundant Connection - How & Why

## Problem Description

You are given an undirected graph with n nodes labeled 1..n that started as a tree, then had one extra edge added. Return the edge that creates a cycle; if multiple, return the one that appears last in the input order.

---

## Approaches (from simple to optimal)

### 1) Brute Force (remove-and-check)

- For each edge e in input order:
  - Temporarily remove e.
  - Check if the remaining edges form a tree (connected and acyclic) via DFS/BFS.
  - If still connected, e is redundant.
- Time: O(m * (n + m)) ~ O(n^3) worst case.
- Space: O(n + m) for graph during checks.

### 2) DFS Cycle Detection (incremental)

- Add edges one by one; before adding edge (u, v), run DFS from u to see if v is reachable.
- If reachable, (u, v) closes a cycle -> redundant; otherwise add the edge.
- Time: O(m * (n + m)) worst case (each DFS over current graph).
- Space: O(n + m) for adjacency.

### 3) Union-Find / Disjoint Set Union (DSU) — Most Optimal

- Maintain parent/rank arrays.
- For each edge (u, v): if find(u) == find(v), the edge forms a cycle -> redundant; else union.
- Time: O(m * α(n)) ~ O(m) with path compression + union by rank/size.
- Space: O(n) for DSU arrays.

---

## How the Provided Code Solves It

- Solution: DSU with parent and rank; detects cycle when find(u) == find(v); otherwise join(u, v) with union by rank and path compression.
- Solution2: DSU with parent initialized to -1 and rank as component size; union returns false when an edge connects nodes already in the same set, that edge is returned.

---

## Why It Works (DSU Correctness)

1) Invariant: DSU partitions nodes into connected components of processed edges.
2) If u and v are already in the same component, there is an existing path between them; adding (u, v) creates a cycle, so that edge is redundant in input order.
3) Path compression and union by rank ensure near-constant operations, so one pass suffices.

---

## Complexities

- Brute Force: Time O(n^3) worst; Space O(n + m).
- DFS incremental: Time O(m * (n + m)); Space O(n + m).
- DSU (solutions here): Time O(m * α(n)) ~ O(m); Space O(n).

---

## Edge Cases

- Self-loop edge (u == v): immediately redundant.
- Multiple cycles: return the first edge that closes a cycle in given order (matches problem spec of one extra edge).
- Minimal graph (n = 2): only one edge possible; no redundancy.

---

## Example Walkthrough (DSU)

Edges: [[1, 2], [1, 3], [2, 3]]

| Step | Edge | find(u) | find(v) | Action | Result |
|------|------|---------|---------|--------|--------|
| 1 | (1,2) | 1 | 2 | union | keep |
| 2 | (1,3) | 1 | 3 | union | keep |
| 3 | (2,3) | 1 | 1 | same set -> cycle | return [2,3] |

---

## Comparison

| Aspect | Brute Force | DFS Incremental | DSU (Solution/Solution2) |
|--------|-------------|-----------------|-------------------------|
| Time | O(n^3) | O(m*(n+m)) | O(m) |
| Space | O(n+m) | O(n+m) | O(n) |
| Implementation | Simple, slow | Moderate | Simple & fast |
| Constant factors | High | Medium | Low |
| When to use | Tiny inputs only | If DSU unavailable | Always preferred |

---

## Which Approach to Choose?

- Best: DSU (Solution, Solution2) — linear-ish time, minimal space, straightforward.
- Fallback: DFS incremental — slower but works without DSU boilerplate.
- Avoid: Brute force — only for pedagogy or very small inputs.
