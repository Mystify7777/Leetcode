# 99. Recover Binary Search Tree — How & Why

## Problem

- Two nodes in a BST are swapped by mistake. Restore the BST by swapping back those two nodes’ values. Prefer `O(h)` space or better and avoid changing tree structure.

## Key Idea

- An inorder traversal of a valid BST yields a non-decreasing sequence. If two nodes are swapped, we will see one or two “inversions” where `prev.val > curr.val`. The first offending `prev` is the first node to swap; the current node at the last offending pair is the second node.

## Approach Used in This Repo

- Inorder traversal with a `pre` pointer to track the previous visited node. On each visit to `root`:
	- If `first == null` and `pre != null` and `pre.val > root.val`, set `first = pre`.
	- If `first != null` and `pre.val > root.val`, set `second = root`.
	- After traversal, swap `first.val` and `second.val`.

See code: [99. Recover Binary Search Tree/Solution.java](99.%20Recover%20Binary%20Search%20Tree/Solution.java)

## Complexity

- Time: `O(n)` — each node is visited once.
- Space: `O(h)` for recursion (or `O(n)` worst-case for a skewed tree). Using an explicit stack would be `O(h)` as well.

## Edge Cases

- Adjacent swapped nodes (single inversion) vs. non-adjacent swapped nodes (two inversions) — logic above captures both.
- Duplicate keys if allowed — treat as non-decreasing inorder (use `pre.val > curr.val` strict check).
- Single-node or empty tree — no changes needed.

## Answer: What is Morris Traversal? How could it improve the code?

- Morris traversal is an inorder (or preorder) traversal technique that runs in `O(n)` time and `O(1)` extra space by temporarily “threading” the tree. It creates a temporary right link from the rightmost node of the left subtree (the inorder predecessor) back to the current node, allowing traversal without a recursion stack or an explicit stack. After visiting, it removes the thread, restoring the original structure.

Benefits here:

- `O(1)` extra space: Eliminates the recursion stack used by the current solution.
- Same logic to detect inversions: maintain `prev` during inorder; whenever `prev.val > curr.val`, record `first` and `second` as above.

Morris inorder outline (for this problem):

```py
prev = null; first = null; second = null; cur = root
while cur != null:
	if cur.left == null:
		// visit cur
		if prev != null and prev.val > cur.val:
			if first == null: first = prev
			second = cur
		prev = cur
		cur = cur.right
	else:
		// find predecessor
		pred = cur.left
		while pred.right != null and pred.right != cur:
			pred = pred.right
		if pred.right == null:
			pred.right = cur        // create thread
			cur = cur.left
		else:
			pred.right = null       // remove thread
			// visit cur
			if prev != null and prev.val > cur.val:
				if first == null: first = prev
				second = cur
			prev = cur
			cur = cur.right

// finally swap first.val and second.val
```

Summary: Morris traversal improves the current solution by reducing auxiliary space from `O(h)` to `O(1)` while keeping `O(n)` time, and it restores the tree structure after traversal.
