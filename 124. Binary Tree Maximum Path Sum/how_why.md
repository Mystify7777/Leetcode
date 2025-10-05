# 🌲 How_Why.md — Binary Tree Maximum Path Sum (LeetCode 124)

---

## ❌ 1️⃣ Brute Force Approach — Explore All Paths

### **Idea**

The *maximum path sum* in a binary tree can start and end at **any node**.
So the brute force idea is to:

* Explore **all possible paths** between pairs of nodes.
* Compute their sums.
* Return the largest sum.

---

### **Algorithm**

1. Generate all possible paths (from each node to all descendants).
2. For each path, compute the total sum.
3. Keep track of the maximum among all.

---

### **Example**

```bash
     1
    / \
   2   3
```

All possible paths:

* `[1] = 1`
* `[2] = 2`
* `[3] = 3`
* `[1, 2] = 3`
* `[1, 3] = 4`
  ✅ Max Path Sum = **6**

---

### **Limitations**

* Exponential complexity due to re-traversing subtrees.
* Extremely inefficient for large trees.

**⛔ Time:** O(n²) (roughly)
**⛔ Space:** O(n) recursion depth

---

## ✅ 2️⃣ Your Approach — Optimized DFS with Global Max Tracking

### **Core Idea**

For each node, compute the **maximum sum of a path starting from that node and going downwards** (only one branch).
While doing that, keep updating a **global maximum** that considers the case when the path passes *through* that node (using both left and right subtrees).

---

### **Key Observations**

* Each node can be part of a maximum path in **two ways**:

  1. As a **bridge node** (path passes through it):
     `left + node.val + right`
  2. As part of **one branch** going upward:
     `node.val + max(left, right)`

* Negative sums decrease path totals, so we **ignore negative branches** (by taking `Math.max(helper(child), 0)`).

---

### **Algorithm Steps**

1. Use a helper function that returns the **maximum gain** from the current node.
2. For each node:

   * Compute left and right branch gains recursively.
   * Calculate `pathSumThroughRoot = node.val + leftGain + rightGain`.
   * Update global `max` with `pathSumThroughRoot`.
   * Return `node.val + max(leftGain, rightGain)` to parent (since a branch can only extend one direction upward).

---

### **Code**

```java
public class Solution {
    int max = Integer.MIN_VALUE;
    
    public int maxPathSum(TreeNode root) {
        helper(root);
        return max;
    }
    
    // helper returns the maximum downward path sum from this node
    int helper(TreeNode root) {
        if (root == null) return 0;
        
        // compute left and right max paths; ignore negatives
        int left = Math.max(helper(root.left), 0);
        int right = Math.max(helper(root.right), 0);
        
        // path passing through this node
        max = Math.max(max, root.val + left + right);
        
        // return the best single-branch path upwards
        return root.val + Math.max(left, right);
    }
}
```

---

### **Walkthrough Example**

#### Input Tree

```bash
       -10
       /  \
      9   20
          / \
         15  7
```

#### Step-by-Step Trace

| Node | Left Gain | Right Gain | Path Through Node | Global Max | Return (to Parent) |
| :--- | :-------- | :--------- | :---------------- | :--------- | :----------------- |
| 15   | 0         | 0          | 15                | 15         | 15                 |
| 7    | 0         | 0          | 7                 | 15         | 7                  |
| 20   | 15        | 7          | 42                | 42         | 35                 |
| 9    | 0         | 0          | 9                 | 42         | 9                  |
| -10  | 9         | 35         | 34                | **42**     | —                  |

✅ Final Answer = **42**

Path → `15 → 20 → 7`

---

### **Complexity**

| Metric | Value | Explanation                   |
| :----- | :---- | :---------------------------- |
| Time   | O(n)  | Each node is visited once     |
| Space  | O(h)  | Recursion depth = tree height |

---

## ⚡ 3️⃣ Comparison & Insight

| Approach        | Time  | Space | Idea                          | Remarks                  |
| :-------------- | :---- | :---- | :---------------------------- | :----------------------- |
| **Brute Force** | O(n²) | O(n)  | Try all paths                 | Exponential re-traversal |
| **Your DFS**    | O(n)  | O(h)  | Recursive postorder traversal | Optimal and elegant      |
| **DP Variant**  | O(n)  | O(n)  | Same logic, iterative storage | Rarely needed            |

---

## 💡 Key Takeaways

* **Divide & Conquer on Trees** → compute from bottom up.
* Each node contributes:

  * Either as *turning point* of path (update global max)
  * Or as *part of one branch* (returned upward)
* **Ignore negative paths**: they reduce sum.
* This is a standard template for:

  * *Maximum Path Problems*
  * *Diameter of Binary Tree*
  * *Longest Path with Constraints*

---
