
# How & Why: LeetCode 1123 - Lowest Common Ancestor of Deepest Leaves

## Problem

Find the lowest common ancestor (LCA) of all the deepest leaves in a binary tree.

## Intuition

- The answer depends on depths. If left and right subtrees have the same deepest depth, the current node is the LCA. Otherwise the deeper side already contains the LCA.
- Pair each subtree with its max depth and the LCA for its deepest leaves; combine bottom-up.

## Brute Force Approach

- **Idea:** Compute tree height; collect all deepest leaves; then run a standard pairwise LCA over that set.
- **Complexity:** $O(N^2)$ in worst case (repeated LCA calls) plus extra storage for deepest nodes.

## My Approach (Depth + LCA pair) — from Solution.java

- **Idea:** Post-order DFS returns `(depth, lca)` for each node. Compare left/right depths:
	- Equal → current node is LCA, depth = left.depth + 1.
	- Left deeper → propagate left.lca, depth = left.depth + 1.
	- Right deeper → propagate right.lca, depth = right.depth + 1.
- **Complexity:** Time $O(N)$, Space $O(H)$ recursion stack.

- **Core snippet:**

```java
Result dfs(TreeNode node) {
		if (node == null) return new Result(0, null);
		Result L = dfs(node.left), R = dfs(node.right);
		if (L.depth == R.depth) return new Result(L.depth + 1, node);
		return (L.depth > R.depth)
				? new Result(L.depth + 1, L.lca)
				: new Result(R.depth + 1, R.lca);
}
```

## Most Optimal Approach

- The one-pass DFS with depth+LCA tuple is optimal: single traversal, $O(N)$ time, $O(H)$ space. An iterative stack variant would be equivalent.

## Edge Cases

- Empty tree → return null.
- Single node → that node is the LCA.
- Completely skewed tree → recursion depth equals height; iterative version avoids stack overflow.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Collect deepest leaves + pairwise LCA | Find deepest nodes, LCA them | Up to O(N^2) | O(N) | Extra passes and repeated LCA |
| DFS returning (depth, lca) (used) | Bottom-up depth compare | O(N) | O(H) | Simple, single pass |

## Example Walkthrough

Tree: `[3,5,1,6,2,0,8,null,null,7,4]`

- Deepest leaves: 7 and 4 at depth 4 under node 2.
- At node 2: left depth = right depth → LCA becomes node 2.
- Propagates up; final answer = node 2.

## Insights

- Pairing auxiliary info (depth) with the target (lca) in one DFS is a common pattern to avoid multiple traversals.
- Equality of depths is the exact signal that current node binds deepest leaves on both sides.

## References to Similar Problems

- Lowest Common Ancestor of a Binary Tree (236)
- Smallest Subtree with all the Deepest Nodes (same core idea)
