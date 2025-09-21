# How\_Why.md: Binary Tree Level Order Traversal

## Problem

Given the root of a binary tree, return the **level order traversal** of its nodes' values.
That is, traverse the tree **level by level from top to bottom**.

**Example:**

```java
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
```

---

## Brute-force Approach

### Idea

* Use **Breadth-First Search (BFS)** with a **queue**.
* At each step, process **all nodes at the current level**, then move to the next level.
* Straightforward and intuitive.

### Code

```java
public List<List<Integer>> levelOrderBF(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        result.add(level);
    }
    return result;
}
```

### Example Walkthrough

* Input: `[3,9,20,null,null,15,7]`
* Queue initially: `[3]`
* Level 0 → `[3]`, add children `[9,20]`
* Level 1 → `[9,20]`, add children `[15,7]`
* Level 2 → `[15,7]`
* Result: `[[3],[9,20],[15,7]]`

### Limitations

* BFS requires **extra space for the queue** → O(width of tree).
* Conceptually simple, but some prefer **recursive DFS** for cleaner code in certain cases.

---

## DFS with Recursion (Your Approach)

### Idea_

* Use **Depth-First Search (DFS)** but track **current level**.
* When visiting a node, add its value to a list corresponding to that level.
* Recursively process **left and right subtrees** with `level + 1`.

### Code_

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    traverse(root, 0, result);
    return result;
}

private void traverse(TreeNode node, int level, List<List<Integer>> result) {
    if (node == null) return;
    if (level == result.size()) {
        result.add(new ArrayList<>());
    }
    result.get(level).add(node.val);
    traverse(node.left, level + 1, result);
    traverse(node.right, level + 1, result);
}
```

### Example Walkthrough_

* Input: `[3,9,20,null,null,15,7]`
* Start at root `3`, level `0` → add `[3]`
* Go left → `9`, level `1` → add `[9]`
* Go right → `20`, level `1` → add `[20]`
* Go deeper → `15,7`, level `2` → add `[15,7]`
* Result: `[[3],[9,20],[15,7]]`

### Advantages

* **No explicit queue needed**.
* Elegant and clean DFS implementation.
* Recursive call stack acts as implicit queue for level tracking.

---

## Key Takeaways

1. **BFS (Queue)** → iterative, intuitive, uses explicit extra space.
2. **DFS with recursion** → elegant, stack-based, requires careful level tracking.
3. Both approaches **yield O(n) time** and **O(n) space** in worst case.

---
