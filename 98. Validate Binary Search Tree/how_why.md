# How_Why.md: Validate Binary Search Tree

## Problem

Given the root of a binary tree, determine if it is a **valid binary search tree (BST)**.

A valid BST is defined as:

* The **left subtree** of a node contains only nodes with values **less than** the node's value
* The **right subtree** of a node contains only nodes with values **greater than** the node's value
* Both left and right subtrees must also be valid BSTs

**Example:**

```s
Input: root = [2,1,3]
Output: true

Input: root = [5,1,4,null,null,6,7]
Output: false (4 is in the right subtree of 5 but should be > 5)
```

---

## Approach: In-Order DFS Traversal (Your Solution)

### Idea

* **In-order traversal** of a BST produces values in **strictly increasing order**
* Traverse the tree using **stack-based DFS** (mimicking in-order traversal)
* Track the **previous node** value
* If current value ≤ previous value, it's not a valid BST

### Code

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;  // Previous node in in-order traversal
        
        while (root != null || !stack.isEmpty()) {
            // Traverse to the leftmost node
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            // Current node is the one at top of stack
            root = stack.pop();
            
            // Check BST property: current value must be > previous value
            if (pre != null && root.val <= pre.val) {
                return false;
            }
            
            // Update previous and move to right subtree
            pre = root;
            root = root.right;
        }
        
        return true;
    }
}
```

### Why This Works

* **In-order Traversal:** For a BST, visiting nodes in order produces strictly increasing sequence
  - Left subtree → Node → Right subtree

* **Stack-based Approach:** Simulates recursive in-order traversal iteratively

* **Example:** root = [2, 1, 3]

  ```java
  In-order traversal: 1 → 2 → 3
  
  Step 1: Push 2, then 1 (go left)
  Step 2: Pop 1, pre=null, pre=1, check passed
  Step 3: Go to 1.right (null)
  Step 4: Pop 2, check 2 > 1? Yes, pre=2
  Step 5: Go to 2.right (3)
  Step 6: Push 3 (no left child)
  Step 7: Pop 3, check 3 > 2? Yes, pre=3
  Result: true ✓
  ```

* Time Complexity: **O(n)** - visit each node once
* Space Complexity: **O(h)** - stack depth (h = height)

---

## Approach 2: Recursive with Min-Max Bounds

### Idea*

* For each node, maintain **valid range** [min, max]
* Left child must be in range [min, node.val)
* Right child must be in range (node.val, max]
* Recursively validate both subtrees

### Code*

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean isValid(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        
        // Check if current value is in valid range
        if (root.val <= minVal || root.val >= maxVal) {
            return false;
        }
        
        // Recursively check left and right subtrees with updated bounds
        return isValid(root.left, minVal, root.val) && 
               isValid(root.right, root.val, maxVal);
    }
}
```

**Pros:**

- Clean and intuitive
- Directly checks BST constraint
- Easy to understand

**Cons:**

- Uses recursion (stack space)
- Uses `Long.MIN_VALUE` and `Long.MAX_VALUE` for boundary

---

## Approach 3: Recursive with Previous Node

### Idea**

* Similar to in-order approach but recursive
* Traverse left → check node → traverse right
* Maintain previous value during traversal

### Code**

```java
class Solution {
    TreeNode prev = null;
    
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        
        // Check left subtree
        if (!isValidBST(root.left)) return false;
        
        // Check current node
        if (prev != null && root.val <= prev.val) return false;
        
        // Update prev and check right subtree
        prev = root;
        return isValidBST(root.right);
    }
}
```

---

## Comparison

| Approach | Time | Space | Pros | Cons |
| ---------- | ------ | ------- | ------ | ------ |
| In-Order (Iterative) | O(n) | O(h) | **Uses in-order property** | More complex |
| Min-Max Bounds | O(n) | O(h) | Cleaner code | Uses `Long.MIN/MAX_VALUE` |
| In-Order (Recursive) | O(n) | O(h) | Simple and intuitive | More stack space |

---

## Why This Approach (In-Order Iterative)

* **Mathematically Sound:** Leverages fundamental BST property (in-order = sorted)
* **Efficient:** O(h) space vs O(n) for storing all values
* **Interview Ready:** Shows understanding of tree traversal and BST properties
* **Iterative:** Avoids recursion overhead
* **Practical:** Works for very deep trees without stack overflow risk

**Key Insight:** For any problem involving BSTs, think about in-order traversal properties first!
