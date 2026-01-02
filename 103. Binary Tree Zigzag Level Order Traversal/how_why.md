# How_Why.md: Binary Tree Zigzag Level Order Traversal

## Problem

Given the root of a binary tree, return the **zigzag level order traversal** of its nodes' values. That is, traverse the tree level by level from top to bottom, but **alternate the direction** of each level (left-to-right, then right-to-left, etc.).

**Example:**

```shell
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
```

---

## Approach: DFS with Level Tracking

### Idea

* Use **Depth-First Search (DFS)** to traverse the tree and track the current level.
* For each level, **alternate the insertion direction**:

  * **Even levels** (0, 2, 4, ...): Add values to the **end** of the list
  * **Odd levels** (1, 3, 5, ...): Add values to the **beginning** of the list

* This zigzag pattern is achieved by checking `level % 2`.

### Code

```java
public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> sol = new ArrayList<>();
        travel(root, sol, 0);
        return sol;
    }
    
    private void travel(TreeNode curr, List<List<Integer>> sol, int level) {
        if (curr == null) return;
        
        // Create a new level list if needed
        if (sol.size() <= level) {
            List<Integer> newLevel = new LinkedList<>();
            sol.add(newLevel);
        }
        
        List<Integer> collection = sol.get(level);
        
        // Alternate insertion direction based on level
        if (level % 2 == 0) {
            collection.add(curr.val);  // Even level: add to end
        } else {
            collection.add(0, curr.val);  // Odd level: add to beginning
        }
        
        travel(curr.left, sol, level + 1);
        travel(curr.right, sol, level + 1);
    }
}
```

### Why This Works

* **LinkedList** is used for efficient `add(0, value)` operations at the beginning
* The **level tracking** ensures nodes are placed at the correct depth
* The **zigzag pattern** is controlled by checking if the level is even or odd
* Time Complexity: **O(n)** - visit each node once
* Space Complexity: **O(h)** - recursion stack depth (h = height of tree)

### Example Walkthrough

* Input: `[3,9,20,null,null,15,7]`
* Level 0 (even): Visit `3` → add to end → `[3]`
* Level 1 (odd): Visit `9` → add to beginning, then `20` → add to beginning → `[20,9]`
* Level 2 (even): Visit `15` → add to end, then `7` → add to end → `[15,7]`
* Result: `[[3],[20,9],[15,7]]`

### Why This Approach

* **Simpler than BFS** with queue reversal logic
* **Efficient insertion** using LinkedList
* **Cleaner code** than maintaining alternating directions in a queue-based solution
