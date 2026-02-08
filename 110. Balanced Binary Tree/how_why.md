# How Why Explanation - 110. Balanced Binary Tree

## Problem

Given a binary tree, return `true` if it is height-balanced: for every node, the height difference between its left and right subtrees is at most 1.

## Intuition

Balance is a local property at every node but depends on subtree heights. We can compute heights bottom-up; if any subtree is already unbalanced, propagate a sentinel to short-circuit further work.

## Brute Force (Not Used)

- For each node, recompute heights of its subtrees independently and check the difference.
- Complexity: $O(n^2)$ in the worst case (skewed tree) due to repeated height recomputation.

## Approach (Post-order with sentinel)

1. Recursively compute the height of a subtree.
2. If a child is unbalanced, return `-1` upward immediately.
3. If left/right heights differ by more than 1, return `-1` to mark unbalance.
4. Otherwise, return `max(left, right) + 1`.
5. The tree is balanced iff the root call does not return `-1`.

Why it works: Post-order ensures children are validated before their parent. The `-1` sentinel lets us stop as soon as an imbalance is found, keeping the traversal linear.

## Complexity

- Time: $O(n)$ — each node visited once.
- Space: $O(h)$ recursion stack, where $h$ is tree height; $O(n)$ worst-case skew, $O(\log n)$ for balanced.

## Optimality

Any algorithm must touch each node to guarantee balance; this achieves $O(n)$ with early exit on imbalance.

## Edge Cases

- Empty tree is balanced.
- Single node is balanced.
- Highly skewed tree becomes unbalanced quickly and short-circuits via `-1`.

## Comparison Table

| Aspect | Post-order with sentinel (Solution) | Naive per-node height recompute |
| --- | --- | --- |
| Time | $O(n)$ | $O(n^2)$ worst case |
| Space | $O(h)$ | $O(h)$ |
| Early exit | Yes on first `-1` | No |

## Key Snippet (Java)

```java
int dfs(TreeNode node) {
	if (node == null) return 0;
	int lh = dfs(node.left);
	int rh = dfs(node.right);
	if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1) return -1;
	return Math.max(lh, rh) + 1;
}

boolean isBalanced(TreeNode root) {
	return dfs(root) != -1;
}
```

## Example Walkthrough

Tree: `[3,9,20,null,null,15,7]`

- Leaves 9,15,7 have height 1.
- Node 20: left=1 (15), right=1 (7) → height 2.
- Node 3: left=1 (9), right=2 (20) → diff=1 → height 3.
- No sentinel `-1` encountered → balanced.

Skewed example `[1,2,2,3,3,null,null,4,4]` triggers `-1` when diff>1.

## Insights

- Sentinel return simplifies combining height and balance checks in one traversal.
- Post-order is natural for aggregating subtree properties like height.

## References

- Solution implementation in [110. Balanced Binary Tree/Solution.java](110.%20Balanced%20Binary%20Tree/Solution.java)
