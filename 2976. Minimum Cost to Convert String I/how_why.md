# How Why Explanation - 2976. Minimum Cost to Convert String I

## Problem

You have two strings `source` and `target` of equal length. You may convert a character `a -> b` with a given cost (multiple conversion rules provided). You can also chain conversions; total cost is the sum of rule costs along the path. Find the minimum total cost to convert `source` into `target` position-wise, or return `-1` if any position is impossible.

## Intuition

The alphabet is small (26 lowercase letters). For each pair of letters, we want the cheapest conversion cost using any sequence of rules. That is an all-pairs shortest path problem on 26 nodes. Once we know the cheapest `c1 -> c2` for all pairs, we sum the costs for each index of `source`/`target`.

## Brute Force (Not Used)

- For each differing position, try all possible conversion paths; exponential in path length.
- Infeasible; redundant since the graph is tiny and Floyd–Warshall gives all pairs directly.

## Approach (Floyd–Warshall on 26x26 graph)

1. Build a 26x26 matrix `graph` initialized to `INF`, with `graph[i][i] = 0`.
2. For every rule `original[i] -> changed[i]` with cost `cost[i]`, set `graph[from][to] = min(current, cost[i])` (keep the cheapest parallel edge).
3. Run Floyd–Warshall: for all intermediates `k`, relax `graph[i][j] = min(graph[i][j], graph[i][k] + graph[k][j])` when both sides are reachable.
4. Walk through `source`/`target`: if chars differ and `graph[s][t]` is `INF`, return `-1`; else add that cost to the answer.

Why it works: Floyd–Warshall finds the minimum path cost between every pair in `O(26^3)`, trivial for the small alphabet. Summing per position then yields the minimal total cost.

## Complexity

- Time: $O(26^3 + n)$; the cubic term is constant-sized.
- Space: $O(26^2)$ for the cost matrix.

## Optimality

Given fixed alphabet size, Floyd–Warshall is effectively constant-time. Any shortest-path approach would be dominated by per-position processing for long strings. Taking the min for duplicate rules ensures we never lose a cheaper direct edge.

## Edge Cases

- Identical characters at a position: cost 0.
- Missing path for a needed conversion: return `-1`.
- Multiple rules between same letters: keep the lowest cost.
- Empty strings: cost 0.

## Comparison Table

| Aspect | Solution (named helpers) | Solution2 (inline) |
| --- | --- | --- |
| Build graph | Helper `buildConversionGraph` | Inline fill `dis` |
| APSP | Floyd–Warshall | Floyd–Warshall |
| Differences | Style/structure only | Style/structure only |

## Key Snippet (Java)

```java
for (int i = 0; i < cost.length; i++) {
	int from = original[i] - 'a';
	int to = changed[i] - 'a';
	graph[from][to] = Math.min(graph[from][to], cost[i]);
}

for (int k = 0; k < 26; k++) {
	for (int i = 0; i < 26; i++) if (graph[i][k] < INF) {
		for (int j = 0; j < 26; j++) if (graph[k][j] < INF) {
			graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
		}
	}
}
```

## Example Walkthrough

`source = "abcd"`, `target = "bcda"`

Rules:

- `a -> b` cost 2
- `b -> c` cost 3
- `c -> d` cost 4

After Floyd–Warshall, we have costs: `a->b=2`, `b->c=3`, `c->d=4`, and chained `a->c=5`, `a->d=9`, `b->d=7`.

- `a->b`: 2
- `b->c`: 3
- `c->d`: 4
- `d->a`: unreachable (no rule), so answer is `-1`.

If we add a rule `d->a` cost 1, total becomes `2+3+4+1 = 10`.

## Insights

- Small alphabet makes all-pairs shortest paths trivial in cost.
- Checking reachability before summing avoids overflow on `INF`.
- Keeping only the cheapest parallel edge simplifies the graph without loss of optimality.

## References

- Solution implementation in [2976. Minimum Cost to Convert String I/Solution.java](2976.%20Minimum%20Cost%20to%20Convert%20String%20I/Solution.java)
