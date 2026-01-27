# How Why Explanatioon - 3650. Minimum Cost Path with Edge Reversals

## Problem

Directed graph with `n` nodes (`0` to `n-1`) and weighted edges `u -> v` of cost `w`. You may traverse an edge in its given direction for cost `w`, or reverse it on the fly and traverse `v -> u` for cost `2*w`. Find the minimum cost path from node `0` to node `n-1`, or `-1` if unreachable.

## Intuition

Reversing an edge just means there is a second way to move between the same two nodes with a known, deterministic cost (`2*w`). So we can model each directed edge as two directed edges: forward cost `w` and reverse cost `2*w`. Then it is a standard single-source shortest path problem with non-negative weights.

## Brute Force (Not Used)

- Enumerate which edges to reverse, then run shortest path on the resulting graph.
- Exponential in edge count; infeasible.

## Approach (Dijkstra on expanded edges)

1. Build adjacency where each original edge `u -> v (w)` contributes:
   - `u -> v` with cost `w` (keep as-is)
   - `v -> u` with cost `2*w` (reverse-on-traverse)
2. Run Dijkstra from node `0` to compute min cost to every node.
3. If distance to `n-1` is infinity, return `-1`; else return that distance.

Why it works: every choice to reverse is local to an edge traversal and has fixed cost. Encoding both directions directly captures all possible paths without separately tracking which edges were reversed.

## Complexity

- Time: $O((n + m) \log n)$ with a binary-heap priority queue.
- Space: $O(n + m)$ for the adjacency list and distance array.

## Optimality

Edge weights are non-negative, so Dijkstra is optimal and linearithmic in graph size. No extra state is required because reverse cost is static per edge.

## Edge Cases

- `n == 1`: cost is 0 (start is destination).
- No outgoing edges from 0 or no incoming to `n-1`: may be unreachable -> `-1`.
- Multiple edges between same nodes: keep the cheapest per direction implicitly handled by Dijkstra.

## Comparison Table

| Aspect | Two-way adjacency (Solution2) | Split in/out lists (Solution) |
| --- | --- | --- |
| Modeling | Adds reverse edges with cost `2*w` directly | Keeps forward/back lists, tries reverse traversal conditionally |
| State size | `dist[n]` | `dist[n][state]` though only one state used |
| Complexity | $O((n+m)\log n)$ | Same intention, but state handling unused |
| Clarity | Simple symmetric graph | More bookkeeping for in/out lists |

## Key Snippet (Java)

```java
for (int[] e : edges) {
	int u = e[0], v = e[1], w = e[2];
	graph[u].add(new Edge(v, w));     // forward
	graph[v].add(new Edge(u, 2 * w)); // reversed traversal cost
}

PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[0] = 0;
pq.add(new int[]{0, 0});

while (!pq.isEmpty()) {
	int[] cur = pq.poll();
	int u = cur[0], d = cur[1];
	if (d != dist[u]) continue;
	if (u == n - 1) break;
	for (Edge ed : graph[u]) {
		int v = ed.to, nd = d + ed.weight;
		if (nd < dist[v]) {
			dist[v] = nd;
			pq.add(new int[]{v, nd});
		}
	}
}
return dist[n - 1] == Integer.MAX_VALUE ? -1 : dist[n - 1];
```

## Example Walkthrough

Assume `n = 3`, edges: `0 -> 1 (2)`, `2 -> 1 (3)`.

- Build edges: `0->1 (2)`, `1->0 (4)`; `2->1 (3)`, `1->2 (6)`.
- Shortest path 0 to 2: `0 -> 1 (2)`, then reversed `1 -> 2` costs 6, total 8; if no other path, answer is 8. If unreachable, answer would be `-1`.

## Insights

- Reversal cost is edge-local; expanding edges removes the need for extra DP/bitmask states.
- Dijkstra terminates early when the destination is popped, saving work on large sparse graphs.

## References

- Solution implementation in [3650. Minimum Cost Path with Edge Reversals/Solution.java](3650.%20Minimum%20Cost%20Path%20with%20Edge%20Reversals/Solution.java)
