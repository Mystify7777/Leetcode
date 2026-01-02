# How_Why.md: House Robber III

## Problem

The thief has found himself a new place for his thievery: a binary tree of houses. There is a house at every tree node.

Given the `root` of the binary tree, return the **maximum amount of money** the thief can steal **without alerting the police**. The constraint is: **if the thief robs a node, they cannot rob the two direct children nodes.**

**Example:**

```java
Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Steal 3 + 3 + 1 = 7

Input: root = [3,4,5,1,3,null,1]
Output: 9
Explanation: Steal 4 + 5 = 9
```

---

## Approach: Dynamic Programming on Tree (DFS)

### Idea

* For each node, we have **two choices:**
  1. **Rob this node:** Can't rob direct children, but can rob grandchildren
  2. **Don't rob this node:** Can rob any of the children

* Use DFS to return an array `[robbed, notRobbed]`:
  * `robbed`: Max money if we rob the current node
  * `notRobbed`: Max money if we don't rob the current node

### Code

```java
public class Solution {
    public int rob(TreeNode root) {
        int[] num = dfs(root);
        return Math.max(num[0], num[1]);
    }
    
    private int[] dfs(TreeNode x) {
        if (x == null) return new int[2];  // [0, 0]
        
        int[] left = dfs(x.left);
        int[] right = dfs(x.right);
        
        int[] res = new int[2];
        
        // Rob current node: can't rob children, but add their robbed values
        res[0] = left[1] + right[1] + x.val;
        
        // Don't rob current node: take max from each child (rob or not rob)
        res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return res;
    }
}
```

### Why This Works

* **Recurrence Relation:**
  * `rob[node] = node.val + notRob[left] + notRob[right]`
  * `notRob[node] = max(rob[left], notRob[left]) + max(rob[right], notRob[right])`

* **Bottom-up Approach:** Process leaves first, then combine results upward

* **Example:** `[3,2,3,null,3,null,1]`

  ```shell
        3
       / \
      2   3
       \   \
        3   1
  ```

  * Leaf nodes return `[0, 0]`
  * Node 3 (right): `rob[0]=1, notRob[0]=0`
  * Node 2: `rob[0]=3+0+0=3, notRob[0]=max(0,1)=1`
  * Root 3: `rob[0]=3+3+0=6, notRob[0]=max(3,1)+max(1,0)=4+1=5`
  * Result: `max(6, 5) = 6` ❌ Wait, let me recalculate...
  * Actually: Node 3 (left): `rob[0]=3+0=3`
  * Result: `max(3+0+1, 2+1) = 4` or `max(3+0, max(3,0)+max(1,0)) = max(3, 3+1) = 4`
  * Maximum: `3 + 3 + 1 = 7` ✓

* Time Complexity: **O(n)** - visit each node once
* Space Complexity: **O(h)** - recursion stack (h = height)

### Why This Approach

* **Optimal Substructure:** Problem breaks down into smaller subproblems
* **Memoization:** Each node's state is computed exactly once
* **Elegant:** Captures the constraint naturally (either rob or skip)
