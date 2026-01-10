# Recap

Given the root of a binary search tree (BST) and an integer `k`, find the kth smallest element in the BST. A BST has the property that for every node, all values in its left subtree are smaller and all values in its right subtree are larger.

## Intuition

In a BST, an in-order traversal (left → root → right) visits nodes in increasing order. The kth element we encounter during this traversal is our answer. We can count down from `k` and return the value when the count reaches zero.

## Approach

**In-order Traversal with Counter:**

1. Perform an in-order traversal of the BST.
2. Maintain a counter `k` that decrements with each node visit.
3. When the counter reaches `0`, we've found the kth smallest element; return its value.
4. Visit nodes in order: left subtree → current node → right subtree.

**Why this works:** In-order traversal of a BST yields nodes in ascending order. The kth node visited is the kth smallest value.

**Time optimization:** We can stop traversal early once we find the answer, rather than traversing the entire tree.

## Code (Java)

```java
class Solution {
    private static int number = 0;
    private static int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper(root);
        return number;
    }
    
    public void helper(TreeNode n) {
        if (n.left != null) helper(n.left);      // Visit left subtree
        count--;                                   // Decrement counter
        if (count == 0) {
            number = n.val;                        // Found kth smallest
            return;
        }
        if (n.right != null) helper(n.right);     // Visit right subtree
    }
}
```

## Correctness

- **In-order property:** In-order traversal of a BST visits nodes in sorted (ascending) order.
- **Kth element:** By decrementing `k` at each node visit, the counter reaches `0` exactly when we've visited `k` nodes, so we return the kth node's value.
- **Early termination:** Once `count == 0`, we stop traversal and return immediately.

## Complexity

- **Time:** `O(k)` in the best case (if the kth smallest is near the start of traversal), `O(n)` in the worst case (if the kth smallest is at the end).
  - Average case for a balanced BST: `O(log n + k)`.
- **Space:** `O(h)` for recursion stack, where `h` is the height of the tree (`h = log n` for balanced BST, `h = n` for skewed tree).

## Edge Cases

- `k = 1`: Return the smallest element (leftmost node).
- `k = n`: Return the largest element (rightmost node).
- Single node tree: Return that node's value if `k = 1`.
- Skewed tree (all left or all right): Still works correctly with in-order traversal.

## Alternative Approach: Iterative In-order with Stack

```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // Current is null, pop from stack
            curr = stack.pop();
            k--;
            
            if (k == 0) return curr.val;  // Found kth smallest
            
            // Visit right subtree
            curr = curr.right;
        }
        
        return -1;  // Should not reach here
    }
}
```

This iterative approach avoids recursion and uses explicit stack management.

## Takeaways

- **BST In-order Traversal:** Always produces sorted order; fundamental for many BST problems.
- **Counter-based Approach:** Simple way to find the kth element without storing all values.
- **Early Termination:** Can optimize by stopping as soon as the answer is found.
- **Space Trade-off:** Iterative approach uses explicit stack but avoids recursion depth issues.
- **Generalization:** This approach extends to finding the kth largest (reverse in-order) or any other ordered query on BSTs.
