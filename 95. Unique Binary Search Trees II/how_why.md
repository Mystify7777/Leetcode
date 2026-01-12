# How & Why: LeetCode 95 - Unique Binary Search Trees II

## Problem

Generate all structurally unique BSTs that store values 1..n and return the list of their roots.

## Intuition

- Choose each value `i` in `[l, r]` as root; all values `< i` must live in the left subtree, all `> i` in the right.
- Number of unique trees is Cartesian product of all unique left and right subtree combinations for every root choice.

## Brute Force Approach

- **Idea:** Pure recursion: for every root `i`, recursively build all left/right trees and combine. No caching.
- **Complexity:** Super-exponential without memo (recomputes subranges many times); infeasible beyond small `n`.

## My Approach (Recursive + Memo) — from Solution2

- **Idea:** Use recursion on a range `[start, end]`, but memoize the list of trees for each range to avoid recomputation.
- **Recurrence:** For each root `i` in the range, combine every left in `solve(start, i-1)` with every right in `solve(i+1, end)`; if range is empty, return list containing `null` (empty subtree placeholder).
- **Complexity:** Still bounded by number of unique BSTs (Catalan $C_n$) but avoids duplicate work; roughly $O(C_n \cdot n)$ time, $O(C_n \cdot n)$ space for storing tree nodes plus recursion.
- **Core snippet:**

```java
List<TreeNode> solve(int l, int r) {
	if (l > r) return List.of((TreeNode) null);
	if (memo[l][r] != null) return memo[l][r];
	List<TreeNode> res = new ArrayList<>();
	for (int root = l; root <= r; root++) {
		for (TreeNode L : solve(l, root - 1))
			for (TreeNode R : solve(root + 1, r)) {
				TreeNode t = new TreeNode(root);
				t.left = L; t.right = R;
				res.add(t);
			}
	}
	return memo[l][r] = res;
}
```

## Most Optimal Approach

- Top-down with memo (above) is asymptotically optimal for generating all trees. A bottom-up DP over lengths is equivalent; memoized recursion is simpler to code.

## Edge Cases

- `n = 0` (if allowed): return empty list.
- Single node (`n = 1`): one tree with just the root.
- Ensure empty subtree represented by `null` so combinations work.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force recursion (Solution) | Recurse on every root, no caching | Super-exponential; repeated ranges | O(height) stack | Simple but redundant work |
| Recursion + memo (Solution2) | Cache list for each `[l,r]` range | ~$O(C_n \cdot n)$ | O(C_n \cdot n)$ for trees + stack | Practical; avoids recomputation |
| Bottom-up DP | Build by length, combine precomputed partitions | ~$O(C_n \cdot n)$ | Similar | Iterative alternative |

## Example Walkthrough (n = 3)

- Roots tried: 1, 2, 3.
- Root 1: left = empty, right = trees for [2,3] (two forms) → 2 trees.
- Root 2: left = tree with 1, right = tree with 3 → 1 tree.
- Root 3: mirror of root 1 case → 2 trees.
- Total 5 trees (Catalan $C_3 = 5$).

## Insights

- Number of unique BSTs with `n` nodes is Catalan: $C_n = \frac{1}{n+1}\binom{2n}{n}$.
- Memoizing subranges is the key to turning explosive recursion into feasible generation.

## References to Similar Problems

- Unique Binary Search Trees I (count only)
- Different Ways to Add Parentheses (range-split + cartesian product pattern)
- Full Binary Trees generation (similar combine-empty-subtree technique)
