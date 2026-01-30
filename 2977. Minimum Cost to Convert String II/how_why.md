# How Why Explanation - 2977. Minimum Cost to Convert String II

## Problem

Given strings `source` and `target` of equal length, and conversion rules `original[i] -> changed[i]` with cost `cost[i]`, you may replace any contiguous substring of `source` that exactly matches `original[i]` with `changed[i]` paying `cost[i]`. Rules can be chained; the overall cost is the sum of rule costs applied in sequence. Find the minimum total cost to transform `source` into `target`, or return `-1` if impossible.

## Intuition

Each rule converts one string token to another at a fixed cost. If we treat every string that appears in `original` or `changed` as a node, we can precompute the cheapest conversion cost between every pair of tokens using all-pairs shortest paths. Then, walking left to right over `source/target`, we decide how many characters to consume next: if the next `t` characters of `source` and `target` map to known tokens, we can pay the precomputed token-to-token cost to jump `t` positions. This is a one-dimensional DP over positions.

## Brute Force (Not Used)

- Try all partitions of the string into substrings and all conversion path choices for each part.
- Exponential in string length; infeasible.

## Approach (APSP over tokens + position DP)

1. **Index tokens**: gather all unique strings appearing in `original` or `changed`, assign each an ID.
2. **Build graph**: `dis[u][v] =` cheapest direct rule cost from token `u` to `v` (keep the min for duplicates); set `dis[i][i] = 0`, others to `INF`.
3. **Floyd–Warshall** over `dis` to get minimum conversion cost between every token pair.
4. **Length set**: collect all token lengths seen in `original` (these are the only substring sizes worth trying in DP).
5. **DP over positions**: `dp[i] =` min cost to convert prefix of length `i`.
   - Initialize `dp[0] = 0`, others `INF`.
   - Transition: if `source[i] == target[i]`, propagate `dp[i+1] = min(dp[i+1], dp[i])` (skip cost for matching char).
   - For each token length `t`, if `i+t <= n`, look up IDs of `source[i..i+t)` and `target[i..i+t)`; if both exist and `dis[idS][idT]` is finite, relax `dp[i+t] = min(dp[i+t], dp[i] + dis[idS][idT])`.
6. Answer is `dp[n]` if finite, else `-1`.

Why it works: precomputing token-to-token minima turns every DP transition into an $O(1)$ lookup. Limiting to token lengths prunes the state space. The left-to-right DP ensures substrings do not overlap.

## Complexity

- Let `T` be the number of distinct tokens, `L` the set of token lengths, and `n` the string length.
- APSP: $O(T^3)$ with Floyd–Warshall (acceptable because tokens come only from rule lists).
- DP: $O(n \cdot |L|)$ substring extractions and lookups.
- Space: $O(T^2)$ for `dis` and $O(n)$ for `dp`.

## Optimality

Floyd–Warshall is standard for dense APSP on small graphs; here `T` is bounded by rule count, not by `n`. The DP is linear in `n` times the number of candidate lengths, which is near-minimal because we must at least consider each position/length pair that could match a rule.

## Edge Cases

- Characters already match: zero cost to advance by one.
- Missing token or unreachable conversion: that transition is skipped; if no path covers the full length, return `-1`.
- Duplicate rules: keep the cheapest direct edge before APSP.
- Empty strings: cost 0.

## Comparison Table

| Aspect | Map + substring DP (Solution) | Trie + streaming scan (Solution2) |
| --- | --- | --- |
| Token lookup | HashMap on substrings | Trie to index tokens while scanning |
| APSP | Floyd–Warshall on `dis` | Same Floyd–Warshall |
| DP transitions | Over precomputed lengths set | Grows substrings incrementally via trie |
| Memory | `O(T^2 + n)` | Similar, plus trie nodes |

## Key Snippet (Java)

```java
// Floyd–Warshall over token graph
for (int k = 0; k < dis.length; k++) {
	for (int i = 0; i < dis.length; i++) if (dis[i][k] < Long.MAX_VALUE) {
		for (int j = 0; j < dis.length; j++) if (dis[k][j] < Long.MAX_VALUE) {
			dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
		}
	}
}

// Position DP
for (int i = 0; i < n; i++) {
	if (dp[i] == Long.MAX_VALUE) continue;
	if (source.charAt(i) == target.charAt(i)) {
		dp[i + 1] = Math.min(dp[i + 1], dp[i]);
	}
	for (int t : lengths) {
		if (i + t > n) continue;
		Integer a = idx.get(source.substring(i, i + t));
		Integer b = idx.get(target.substring(i, i + t));
		if (a != null && b != null && dis[a][b] < Long.MAX_VALUE) {
			dp[i + t] = Math.min(dp[i + t], dp[i] + dis[a][b]);
		}
	}
}
```

## Example Walkthrough

`source = "abcd"`, `target = "zzcd"`

Rules:

- `"ab" -> "z"` cost 3
- `"z" -> "zz"` cost 1
- `"c" -> "c"` cost 0 (implicit via identity)

Token graph yields costs: `ab -> z = 3`, `z -> zz = 1`, so `ab -> zz = 4` via chaining.

DP:

- `i=0`: substring `"ab"` to `"zz"` length 2 valid with cost 4; set `dp[2] = 4`.
- `i=2`: characters `c` vs `c` match, `dp[3] = 4`; `d` vs `d` match, `dp[4] = 4`.
Answer: 4.

## Insights

- Precomputing all token pair costs collapses each DP step to constant time per candidate length.
- Limiting lengths to those present in rules avoids quadratic blowup over all substring sizes.
- A trie (Solution2) can replace substring creation, reducing overhead for long strings.

## References

- Solution implementation in [2977. Minimum Cost to Convert String II/Solution.java](2977.%20Minimum%20Cost%20to%20Convert%20String%20II/Solution.java)
