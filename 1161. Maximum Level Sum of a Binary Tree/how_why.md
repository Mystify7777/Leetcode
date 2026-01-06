# 1161. Maximum Level Sum of a Binary Tree

## Problem Statement

Given the `root` of a binary tree, the level of a node is defined as the number of edges in the path from the root node to that node.

Return the level of the tree (counting from level 1, not 0) such that the sum of all the node values in that level is maximum.

---

## Approach: Level Order Traversal (BFS)

### Why This Approach?

- We need to process nodes **level by level** to calculate the sum at each level
- BFS (Breadth-First Search) naturally traverses the tree level by level
- We keep track of the maximum sum and the corresponding level
- **Time Complexity:** O(n) where n is the number of nodes (each node visited once)
- **Space Complexity:** O(w) where w is the maximum width of the tree (queue size)

### How It Works

```text
1. Initialize a queue with the root node
2. For each level:
   - Get the current queue size (number of nodes at this level)
   - Iterate through all nodes in the current level
   - Add their values to get the level sum
   - Add their children to the queue for the next level
3. Track the maximum sum and its corresponding level
4. Return the level with the maximum sum
```

### Step-by-Step Example

Consider a simple tree:

```s
        1
       / \
      2   3
     / \
    4   5
```

- **Level 1:** sum = 1, max = 1, maxLevel = 1
- **Level 2:** sum = 2 + 3 = 5, max = 5, maxLevel = 2 (updated)
- **Level 3:** sum = 4 + 5 = 9, max = 9, maxLevel = 3 (updated)
- **Return:** 3

### Key Implementation Details

1. **Queue Management:**
   - Start with the root in the queue
   - For each level, process exactly `q.size()` nodes (using a for loop)
   - This ensures we process one level at a time

2. **Sum Calculation:**
   - Initialize `sum = 0` for each level
   - Accumulate all node values in the current level
   - Compare with the maximum found so far

3. **Level Tracking:**
   - Increment level counter after processing each level
   - Update `maxLevel` whenever we find a new maximum sum

### Code Walkthrough

```java
public int maxLevelSum(TreeNode root) {
    int max = Integer.MIN_VALUE;  // Initialize with minimum value
    int maxLevel = 1;              // Start from level 1
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    
    for (int level = 1; !q.isEmpty(); ++level) {
        int sum = 0;
        // Process all nodes in the current level
        for (int sz = q.size(); sz > 0; --sz) {
            TreeNode n = q.poll();
            sum += n.val;
            // Add children to queue for next level
            if (n.left != null) { 
                q.offer(n.left);
            }
            if (n.right != null) {
                q.offer(n.right);
            }
        }
        // Update maximum if current level sum is greater
        if (max < sum) {
            max = sum;
            maxLevel = level;
        }
    }
    return maxLevel;
}
```

---

## Alternative Approach: DFS (Depth-First Search)

**Why DFS?**

- Can also solve this problem by tracking the level during recursion
- Array stores sum for each level, then we find the maximum

**Trade-offs:**

- DFS requires explicit level tracking in recursion
- May require more recursive calls depending on tree structure
- Still O(n) time complexity but potentially more memory for recursion stack

---

## Time & Space Complexity Analysis

| Aspect | Complexity | Explanation |
| -------- | ----------- | ------------- |
| **Time** | O(n) | Visit each node exactly once |
| **Space** | O(w) | Queue stores at most w nodes (maximum width) |

For a balanced binary tree, w = O(n) worst case is when we have a nearly complete tree.

---

## Edge Cases to Consider

1. **Single node tree:** Returns 1 (the only level)
2. **Negative values:** Algorithm still works since we track maximum sum
3. **All negative values:** Returns the level with the least negative sum
4. **Skewed tree:** Time is still O(n), but space is O(1) effectively

---

## Related Problems

- `102. Binary Tree Level Order Traversal`
- `103. Binary Tree Zigzag Level Order Traversal`
- `111. Minimum Depth of Binary Tree`
- `637. Average of Levels in Binary Tree`
