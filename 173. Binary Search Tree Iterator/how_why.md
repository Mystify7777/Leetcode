# How_Why.md – Binary Search Tree Iterator (173)

## ❌ Brute Force Approach: Precompute In-Order Traversal

### Idea

* Perform a **full in-order traversal** of the BST.
* Store all nodes in an array or list.
* Use an index pointer to iterate.

```java
class BSTIterator {
    private List<Integer> inorder;
    private int index = 0;

    public BSTIterator(TreeNode root) {
        inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
    }

    private void inorderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inorderTraversal(node.left, list);
        list.add(node.val);
        inorderTraversal(node.right, list);
    }

    public int next() {
        return inorder.get(index++);
    }

    public boolean hasNext() {
        return index < inorder.size();
    }
}
```

### Example Walkthrough

BST:

```
       7
      / \
     3   15
        /  \
       9    20
```

* Inorder traversal = `[3, 7, 9, 15, 20]`.
* `next()` calls just move an index.

### Complexity

* **Time**: `O(n)` preprocessing, then `O(1)` per operation.
* **Space**: `O(n)` to store traversal.

👉 **Limitation**: Uses extra memory proportional to the number of nodes, not efficient for large trees.

---

## ✅ Stack-Based Iterator (Your Approach)

### Idea

* Don’t precompute the whole traversal.
* Use a **stack** to simulate recursion and fetch nodes lazily.
* Always push the **leftmost path** from a node.

```java
public class BSTIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        pushAll(root);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        TreeNode node = stack.pop();
        pushAll(node.right);
        return node.val;
    }

    private void pushAll(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}
```

### Example Walkthrough

BST:

```
       7
      / \
     3   15
        /  \
       9    20
```

Steps:

1. Init → stack = `[7,3]`.
2. `next()` → pop 3 → output = 3 → stack = `[7]`.
3. `next()` → pop 7 → output = 7 → push left chain of 15 → stack = `[15,9]`.
4. `next()` → pop 9 → output = 9 → stack = `[15]`.
5. `next()` → pop 15 → output = 15 → push left chain of 20 → stack = `[20]`.
6. `next()` → pop 20 → output = 20 → stack = `[]`.

### Complexity

* **Time**: `O(1)` amortized per operation.
* **Space**: `O(h)` where `h` = height of tree (stack size).

👉 **Advantage**: Efficient, lazy evaluation, does not store the whole tree.

---

## 🚀 Optimized Discussion

* The **stack-based solution** is already optimal: `O(h)` memory and `O(1)` amortized time.
* A **Morris traversal variant** (threaded binary tree) could achieve `O(1)` space, but it modifies the tree structure, which is usually not allowed in this problem.
* Thus, the stack-based iterator is the **best practical approach**.

---

✅ **Final Recommendation**: Use the **stack-based lazy iterator** → it balances memory efficiency and fast runtime without modifying the tree.

---
