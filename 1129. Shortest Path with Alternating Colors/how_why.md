
# How & Why: LeetCode 1129 - Shortest Path with Alternating Colors

## Problem

In a directed graph with `n` nodes, edges are either red or blue. Starting from node 0, return the length of the shortest path to every node where consecutive edges must alternate in color. If a node is unreachable under this rule, return `-1` for it.

## Intuition

- Shortest paths with constraints → Breadth-First Search.
- Alternation means state = (node, color of last edge). From node 0 we can start with either red or blue as the next edge.
- Prevent revisiting the same (node, incoming-color) to avoid cycles and redundant work.

## Brute Force Approach

- **Idea:** Generate all paths via DFS and enforce alternation, track minimum per node.
- **Complexity:** Exponential in path length; impractical for the constraints.

## My Approach (BFS with color state) — from Solution / Solution3

- **Idea:** BFS over states `(node, lastColor)`; enqueue starts `(0, red)` and `(0, blue)`. At each pop, expand only edges of the opposite color. Record first time we reach each node.
- **Data:** Adjacency lists for red and blue edges; visited[node][color] to avoid repeats.
- **Complexity:** Time $O(n + r + b)$, Space $O(n + r + b)$ for graph + queues + visited.
- **Core snippet:**

```java

void bfs(int startColor) {
	Queue<int[]> q = new ArrayDeque<>(); // {node, color}
	boolean[][] seen = new boolean[n][2];
	q.offer(new int[]{0, startColor});
	int dist = 0;
	while (!q.isEmpty()) {
		for (int sz = q.size(); sz-- > 0; ) {
			int[] cur = q.poll();
			int v = cur[0], c = cur[1];
			res[v] = Math.min(res[v], dist);
			for (int nxt : (c == 0 ? blueAdj[v] : redAdj[v])) {
				int nc = 1 - c;
				if (seen[nxt][nc]) continue;
				seen[nxt][nc] = true;
				q.offer(new int[]{nxt, nc});
			}
		}
		dist++;
	}
}
```

## Most Optimal Approach

- The BFS with state (node, lastColor) is optimal for shortest paths under alternation. Implementation variants: single BFS with both start colors enqueued, or two BFS runs (start red / start blue) merged into one `res` array.

## Edge Cases

- No outgoing edges from 0 → only node 0 reachable.
- Multiple parallel edges of different colors between two nodes (handle both).
- Self-loops: only usable if alternating rule allows; visited state prevents infinite loops.
- Disconnected nodes → remain `-1`.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| DFS enumerate paths | Try all alternating paths | Exponential | O(n + e) stack | Not feasible |
| BFS with matrix (Solution) | State (node,color) over dense matrix | O(n^2) | O(n^2) | Simpler to code but heavy for sparse graphs |
| BFS with adj lists (Solution3) | State (node,color) with lists | O(n + e) | O(n + e) | Preferred, sparse-friendly |
| Queue-array micro-optimized (Solution2) | Hand-rolled queues | O(n + e) | O(n + e) | Faster but less readable |

## Example Walkthrough

Input: `n=3`, red=`[[0,1],[1,2]]`, blue=`[]`

- Start states: (0,lastRed) and (0,lastBlue). Distance=0 sets res[0]=0.
- From (0,lastBlue) we can take red to 1 → res[1]=1; enqueue (1,lastRed).
- From (1,lastRed) we need blue next, but none → stop. Node 2 unreachable → `-1`.
Output: `[0,1,-1]`.

## Insights

- Modeling constraints as state expansion in BFS is the key; here the constraint is “next edge color differs from last edge color”.
- Tracking `(node, lastColor)` avoids revisiting nodes in the same state while still allowing the other color to explore shorter paths.

## References to Similar Problems

- 787. Cheapest Flights Within K Stops (layered BFS over constraints)
- 847. Shortest Path Visiting All Nodes (BFS over augmented state)
