# How_Why.md: Flatten Binary Tree to Linked List

## Problem

Given the root of a binary tree, flatten it **in-place** into a singly linked list following **preorder traversal** (`root -> left -> right`). Each node's `left` should become `null` and `right` should point to the next node in preorder.

**Example:**

```java
Input:  root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]
```

---

## Iterative Preorder with Stack (Straightforward)

### Idea

* Simulate preorder using a stack.
* Rewire pointers on the fly: current `right` points to previous node's `right`, and `left` is cleared.
* Push `right` before `left` so `left` is processed first.

### Code

```java
public void flattenIterative(TreeNode root) {
	if (root == null) return;
	Deque<TreeNode> stack = new ArrayDeque<>();
	stack.push(root);
	TreeNode prev = null;

	while (!stack.isEmpty()) {
		TreeNode curr = stack.pop();
		if (prev != null) {
			prev.right = curr;
			prev.left = null;
		}

		if (curr.right != null) stack.push(curr.right);
		if (curr.left != null) stack.push(curr.left);

		prev = curr;
	}
}
```

### Notes

* Preorder order is enforced by stack push order.
* Space: O(h) for stack (h = height), Time: O(n).

---

## Reverse Postorder Recursion (Your Approach)

### Idea_

* Traverse in **reverse preorder** (`right -> left -> root`) so you always know the next node in the flattened list.
* Maintain a `prev` pointer to the already-flattened part; link current `right` to `prev` and nullify `left`.

### Code_

```java
private TreeNode prev = null;

public void flatten(TreeNode root) {
	if (root == null) return;
	flatten(root.right);
	flatten(root.left);

	root.right = prev;
	root.left = null;
	prev = root;
}
```

### Why This Works_

* Processing `right` first means `prev` always points to the next node in preorder when we return.
* Single pass, O(n) time; recursion stack uses O(h) space.

---

## Key Takeaways

1. Preorder is required; maintain order either with a stack (iterative) or reverse preorder recursion.
2. Always set `left = null` when rewiring; only `right` remains.
3. Both methods run in **O(n)** time and **O(h)** extra space; choose based on iterative vs recursive preference.

---
