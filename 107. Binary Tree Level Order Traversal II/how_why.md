# How_Why.md: Binary Tree Level Order Traversal II

## Problem

Given the root of a binary tree, return the **bottom-up level order traversal** of its nodes' values — i.e., from the lowest level up to the root.

**Example:**

```java
Input: root = [3,9,20,null,null,15,7]
Output: [[15,7],[9,20],[3]]
```

---

## BFS + Reverse (Straightforward)

### Idea

* Use **Breadth-First Search (BFS)** with a queue to gather nodes level by level (top-down).
* Store each level in a list, then **reverse the list of levels once** at the end to get bottom-up order.

### Code

```java
public List<List<Integer>> levelOrderBottomBF(TreeNode root) {
	List<List<Integer>> levels = new ArrayList<>();
	if (root == null) return levels;

	Queue<TreeNode> q = new LinkedList<>();
	q.offer(root);

	while (!q.isEmpty()) {
		int size = q.size();
		List<Integer> level = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			TreeNode node = q.poll();
			level.add(node.val);
			if (node.left != null) q.offer(node.left);
			if (node.right != null) q.offer(node.right);
		}

		levels.add(level);
	}

	Collections.reverse(levels);
	return levels;
}
```

### Example Walkthrough

* Input: `[3,9,20,null,null,15,7]`
* Top-down levels collected: `[[3],[9,20],[15,7]]`
* Reverse → `[[15,7],[9,20],[3]]`

### Notes

* Two passes over levels (collect, then reverse), but still **O(n)** time and **O(n)** space.

---

## BFS with Prepend (Your Approach)

### Idea_

* Keep the BFS queue, but push each completed level **to the front** of a linked list (`LinkedList<List<Integer>>`).
* This builds bottom-up order on the fly, avoiding the final reverse.

### Code_

```java
public List<List<Integer>> levelOrderBottom(TreeNode root) {
	LinkedList<List<Integer>> result = new LinkedList<>();
	if (root == null) return result;

	Queue<TreeNode> q = new LinkedList<>();
	q.offer(root);

	while (!q.isEmpty()) {
		int size = q.size();
		List<Integer> level = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			TreeNode node = q.poll();
			level.add(node.val);
			if (node.left != null) q.offer(node.left);
			if (node.right != null) q.offer(node.right);
		}

		result.addFirst(level); // prepend current level
	}

	return result;
}
```

### Why This Works_

* Each level is processed exactly once (**O(n)** time).
* Using a linked list makes `addFirst` **O(1)**, so no extra traversal or reverse pass.

---

## Key Takeaways

1. Both solutions use **BFS** and run in **O(n)** time with **O(n)** space.
2. **Reverse at end** vs. **prepend during traversal** — choose based on preference; prepend saves one reverse pass.
3. A DFS alternative is possible by tracking depth and inserting at the front, but BFS keeps level boundaries simple.

---
