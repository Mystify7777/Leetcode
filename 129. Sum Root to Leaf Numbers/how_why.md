
# How & Why: LeetCode 129 - Sum Root to Leaf Numbers

## Problem

Given a binary tree where each node contains a digit `0-9`, each root-to-leaf path forms a number by concatenating digits. Return the sum of all such numbers.

## Intuition

- Traversing down a path, we’re effectively building a base-10 number. Each move multiplies the current prefix by 10 and adds the next digit.
- The sum is just the accumulation of all completed root-to-leaf numbers.

## Brute Force Approach

- **Idea:** Enumerate all root-to-leaf paths, build strings, parse to int, and sum.
- **Complexity:** Time $O(N\cdot H)$ (string work) and extra space for path strings; unnecessary conversions.

## My Approach (DFS carry prefix) — from Solution.java

- **Idea:** DFS while carrying the numeric prefix. On entering a node: `curr = prefix * 10 + node.val`. When at leaf, add `curr` to global sum.
- **Complexity:** Time $O(N)$, Space $O(H)$ recursion stack.
- **Core snippet:**

```java
void dfs(TreeNode node, int prefix) {
	if (node == null) return;
	int curr = prefix * 10 + node.val;
	if (node.left == null && node.right == null) { total += curr; return; }
	dfs(node.left, curr);
	dfs(node.right, curr);
}
```

## Most Optimal Approach

- The prefix-carry DFS/BFS is optimal: single pass, $O(N)$ time, $O(H)$ space. An iterative stack/queue variant is equivalent.

## Edge Cases

- Empty tree → sum 0.
- Single node → value of that node.
- Leading zeros are naturally handled by arithmetic (e.g., path 0→1→2 forms 12).

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute paths as strings | Build strings per path, parse | O(N·H) | O(N·H) for strings | Wasteful conversions |
| DFS with carried prefix (used) | Accumulate numeric value along path | O(N) | O(H) | Simple and optimal |
| Iterative stack/queue | Carry prefix in stack/queue entries | O(N) | O(H) | Avoids recursion depth concerns |

## Example Walkthrough

Tree:
   1
  / \
 2   3

- Path 1→2 forms 12
- Path 1→3 forms 13
- Sum = 25

## Insights

- Carrying state (prefix) avoids reconstructing paths; same trick applies to path-product or path-boolean problems.

## References to Similar Problems

- Path Sum (carry running sum, boolean)
- Sum of Root To Leaf Binary Numbers (base-2 variant)
