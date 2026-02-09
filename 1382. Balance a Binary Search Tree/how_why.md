# How Why Explanation - 1382. Balance a Binary Search Tree

## Problem

Given a Binary Search Tree (BST), rebuild it into a height-balanced BST (balance factor at each node within 1). Return the new root.

## Intuition

An inorder traversal of a BST yields sorted keys. If we take that sorted list and build a tree by always choosing the middle element as root, we get a height-balanced BST containing the same values. So the task becomes: flatten to sorted array, then build a balanced BST from the array.

## Brute Force (Not Used)

- Insert nodes one by one into a self-balancing BST (AVL/Red-Black) and reconstruct pointers.
- More complex; $O(n \log n)$ insertions vs a simpler linear rebuild.

## Approach (Inorder to array, then mid-build)

1. Inorder traverse original BST, store values in a list (sorted order).
2. Recursively build a balanced BST from the list:
   - Pick middle index as root.
   - Recursively build left from left half, right from right half.
3. Return the constructed root.

Why it works: Choosing mid splits the list as evenly as possible at each level, yielding a balanced tree. Inorder guarantees BST ordering is preserved.

## Complexity

- Time: $O(n)$ to traverse + $O(n)$ to build = $O(n)$.
- Space: $O(n)$ for the list; recursion stack $O(\log n)$ for balanced output, $O(n)$ worst-case if input skewed (but values list already holds all nodes).

## Optimality

We touch each node once and rebuild in linear time; asymptotically optimal. Using the array avoids repeated rotations or insert costs.

## Edge Cases

- Empty tree -> return `null`.
- Single node -> already balanced.
- Duplicate values: preserved order still valid for BST property (duplicates allowed per problem conventions).

## Comparison Table

| Aspect | Value-list + rebuild (Solution) | Node-list reuse (Solution2) |
| --- | --- | --- |
| Storage | List of ints | List of TreeNode references |
| Build step | New nodes | Reuses nodes, rewires pointers |
| Complexity | $O(n)$ / $O(n)$ | $O(n)$ / $O(n)$ |

## Key Snippet (Java)

```java
void inorder(TreeNode node, List<Integer> vals) {
	if (node == null) return;
	inorder(node.left, vals);
	vals.add(node.val);
	inorder(node.right, vals);
}

TreeNode build(List<Integer> vals, int l, int r) {
	if (l > r) return null;
	int m = (l + r) / 2;
	TreeNode node = new TreeNode(vals.get(m));
	node.left = build(vals, l, m - 1);
	node.right = build(vals, m + 1, r);
	return node;
}
```

## Example Walkthrough

Input BST (inorder): `[1, 2, 3, 4, 5]`

- Pick mid=2 (`3`) as root; left from `[1,2]` (mid=`2`), right from `[4,5]` (mid=`5`).
- Resulting tree has height 3 and is balanced, inorder still `[1,2,3,4,5]`.

## Insights

- Mid-selection ensures height near $\log n$; any consistent midpoint choice works (left-middle or right-middle).
- Reusing nodes (Solution2) saves allocations but must rewrite child pointers carefully.

## References

- Solution implementation in [1382. Balance a Binary Search Tree/Solution.java](1382.%20Balance%20a%20Binary%20Search%20Tree/Solution.java)
