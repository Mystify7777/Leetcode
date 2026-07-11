# 2685. Count the Number of Complete Components

## Problem Summary

Given `n` nodes (labeled `0` to `n-1`) and a list of `edges` in an undirected graph, count how many **connected components** are **complete graphs** — i.e., every pair of nodes within the component is directly connected by an edge.

A connected component with `V` nodes is complete if and only if it has exactly:

```
E = V * (V - 1) / 2
```

edges (the number of unique pairs among `V` nodes). This single formula is the key check both solutions rely on.

---

## Approach 1: DFS (`Solution2`)

### How it works

1. Build an adjacency list `A` from the edge list.
2. For every unvisited node `i`, run a DFS to explore its entire connected component.
3. During the DFS, track two static counters:
   - `V` — number of nodes visited in this component.
   - `D` — **sum of degrees** of all nodes in this component (each node contributes `A[x].size()`).
4. Since each edge is counted twice when summing degrees (once from each endpoint), the true edge count of the component is `D / 2`. Instead of dividing, the code compares directly:
   ```
   D == V * (V - 1)
   ```
   which is just `2 * E == V * (V - 1)`, algebraically equivalent to `E == V * (V - 1) / 2`.
5. If the condition holds, the component is complete, and `res` is incremented.

### Why it works

- DFS naturally partitions the graph into connected components — every node reachable from the start node belongs to the same component, and `vis[]` ensures each node is processed exactly once.
- Summing degrees during traversal is a cheap way to get `2 * E` without a separate edge pass — this avoids double-counting issues you'd get from naively adding `A[x].size()` per node if you tried to infer edges directly.
- Comparing `D == V*(V-1)` (rather than `D/2 == V*(V-1)/2`) avoids integer-division parity concerns entirely.

### Complexity

- **Time:** `O(V + E)` — standard DFS traversal.
- **Space:** `O(V + E)` for the adjacency list, plus `O(V)` recursion stack in the worst case (a path-like or star-like component).

### Caveats

- Uses **recursion**, so a very large, deeply chained component (e.g., `n` close to `10^5` in a line) risks a `StackOverflowError`.
- Uses `static` fields (`V`, `D`) — works here because the method resets them before each DFS call, but it's not thread-safe and is generally poor practice for reusable classes.

---

## Approach 2: Union-Find / DSU (`Solution`)

### How it works

1. Initialize `n` singleton components: each node is its own parent, `nodeCount[i] = 1`, `edgeCount[i] = 0`.
2. For each edge `(u, v)`:
   - Find the roots of `u` and `v` (with **path compression** for efficiency).
   - **If they're in different components:** merge them — attach `rootV` under `rootU`, add up their node counts, and add up their edge counts **plus 1** for the new bridging edge itself.
   - **If they're already in the same component:** this edge is an *internal* edge of an already-merged group, so just increment that root's `edgeCount`.
3. After processing all edges, every component's edge count and node count are aggregated at its root.
4. Iterate over all nodes; for each root (`parent[i] == i`), check:
   ```
   edgeCount == nodeCount * (nodeCount - 1) / 2
   ```
   If true, it's a complete component.

### Why it works

- Union-Find naturally groups nodes into disjoint sets that correspond exactly to connected components, without needing an adjacency list or explicit graph traversal.
- The critical correctness detail is **how edges are counted exactly once, regardless of merge order**:
  - When merging two *different* components, the single edge being processed is the bridge between them — it's counted once (`+ 1`), and each side's *already-accumulated internal* edges (`edgeCount[rootU]`, `edgeCount[rootV]`) are carried over untouched.
  - When an edge connects two nodes *already* in the same component, it must be an edge internal to that component (not a bridge), so it's simply added to that component's running total.
  - Since each edge in `edges[]` is processed exactly once, and every edge falls into exactly one of these two cases, no edge is double-counted and none is missed.
- Path compression (`parent[i] = find(parent[i], parent)`) keeps subsequent `find` calls fast, and since only **roots** carry the aggregated `nodeCount`/`edgeCount`, look-ups after all unions are `O(1)` amortized.

### Complexity

- **Time:** `O((V + E) * α(V))`, where `α` is the inverse Ackermann function (effectively constant). Note: this implementation uses union-by-attachment without union-by-rank/size, so worst-case tree depth could theoretically be larger, but path compression alone still keeps it very fast in practice.
- **Space:** `O(V)` for the `parent`, `nodeCount`, and `edgeCount` arrays — no adjacency list needed.

### Caveats

- No union-by-rank/size is used (`parent[rootV] = rootU` always attaches `V`'s root under `U`'s root regardless of size), which *can* degrade path lengths compared to a fully optimized DSU — but path compression still keeps it efficient for this problem's constraints.
- Purely iterative — no recursion depth risk (aside from the recursive `find`, which is bounded by path length after compression, so also low risk).

---

## Comparing the Two Approaches

| | DFS (`Solution2`) | Union-Find (`Solution`) |
|---|---|---|
| **Core idea** | Traverse each component, sum degrees | Merge nodes into sets, track counts at each root |
| **Extra structure** | Adjacency list | Parent/count arrays only |
| **Component detection** | Explicit traversal (`vis[]`) | Implicit via `find()` |
| **Edge counting trick** | Sum of degrees `D`, compare `D == V*(V-1)` | Direct edge tally per component, compare `E == V*(V-1)/2` |
| **Risk** | Recursion stack depth on large components | Slightly weaker DSU (no rank/size heuristic) |
| **Time** | `O(V + E)` | `O((V + E) * α(V))` |

Both approaches boil down to the same mathematical check — **a component is complete iff its edge count equals `V*(V-1)/2`** — just computed via two different graph-processing paradigms: explicit traversal vs. incremental set-merging.