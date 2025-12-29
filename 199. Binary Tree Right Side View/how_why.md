# 199. Binary Tree Right Side View - Explanation

## Problem Understanding

Given the root of a binary tree, return the values of the nodes you can see ordered from top to bottom when looking at the tree from the **right side**.

**Key Points:**

- We need to capture the rightmost visible node at each level
- If a level has nodes, we want the rightmost one (even if it comes from the left subtree)
- View is from top to bottom, so maintain level order

**Example:**

```bash
Input: root = [1,2,3,null,5,null,4]

      1         <- level 0: see 1
     / \
    2   3       <- level 1: see 3 (rightmost)
     \   \
      5   4     <- level 2: see 4 (rightmost)

Output: [1, 3, 4]
```

## Approach 1: DFS (Recursive) - Primary Solution

### Algorithm Overview

The key insight is to use **depth-first search with right-first traversal**:

1. Visit the right subtree before the left subtree
2. Track the current depth/level
3. For each depth, the first node we visit is the rightmost visible node
4. If we haven't seen this depth before (depth == result.size()), add the node's value

### Why This Approach Works

- **Right-first traversal** ensures we always encounter the rightmost node at each level first
- **Depth tracking** allows us to know when we're visiting a new level
- **First-visit-per-level** guarantees we only add the rightmost node, even if left subtree has deeper nodes

### Code Walkthrough

```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<Integer>();
    rightView(root, result, 0);
    return result;
}

public void rightView(TreeNode curr, List<Integer> result, int currDepth) {
    // Base case: null node
    if (curr == null) {
        return;
    }
    
    // First time visiting this depth - this is the rightmost node
    if (currDepth == result.size()) {
        result.add(curr.val);
    }
    
    // Visit right subtree first (right-first traversal)
    rightView(curr.right, result, currDepth + 1);
    
    // Then visit left subtree
    rightView(curr.left, result, currDepth + 1);
}
```

**Key Logic:**

- `currDepth == result.size()` - If the result list has 3 elements (indices 0, 1, 2), size is 3, meaning we haven't seen depth 3 yet
- Right-first traversal ensures the rightmost node at each depth is encountered first
- Even if the left subtree is deeper, we won't add its nodes if we've already seen that depth

### Example Trace

```java
Tree:     1
         / \
        2   3
         \   \
          5   4

rightView(1, [], 0):
  currDepth(0) == result.size(0) → add 1, result=[1]
  
  rightView(3, [1], 1):  // right child first
    currDepth(1) == result.size(1) → add 3, result=[1,3]
    
    rightView(4, [1,3], 2):  // right child of 3
      currDepth(2) == result.size(2) → add 4, result=[1,3,4]
      rightView(null, [1,3,4], 3)  // no children
      rightView(null, [1,3,4], 3)
    
    rightView(null, [1,3,4], 2)  // no left child
  
  rightView(2, [1,3,4], 1):  // left child of 1
    currDepth(1) != result.size(3) → don't add 2
    
    rightView(5, [1,3,4], 2):  // right child of 2
      currDepth(2) != result.size(3) → don't add 5
      rightView(null, [1,3,4], 3)
      rightView(null, [1,3,4], 3)
    
    rightView(null, [1,3,4], 2)

Final result: [1, 3, 4]
```

### Complexity Analysis

**Time Complexity**: O(N)

- Visit each node exactly once
- N = number of nodes in the tree

**Space Complexity**: O(H)

- H = height of tree (recursion stack depth)
- Best case (balanced): O(log N)
- Worst case (skewed): O(N)
- Result list space: O(H) or O(number of levels)

## Approach 2: BFS (Level-Order Traversal) - Alternative Solution

### Algorithm Overview Alt

Use a queue to perform level-order traversal:

1. Process nodes level by level
2. For each level, the last node processed is the rightmost one
3. Add that node's value to the result

### Code Walkthrough Alt

```java
public List<Integer> rightSideView(TreeNode root) {
    if (root == null) return new ArrayList();
    
    Queue<TreeNode> queue = new LinkedList();
    queue.offer(root);
    List<Integer> res = new ArrayList();
    
    while (!queue.isEmpty()) {
        int size = queue.size();  // Number of nodes at current level
        
        while (size-- > 0) {
            TreeNode cur = queue.poll();
            
            // Last node at this level is the rightmost
            if (size == 0) {
                res.add(cur.val);
            }
            
            // Add children for next level (left to right)
            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }
    }
    
    return res;
}
```

### Complexity Analysis Alt

**Time Complexity**: O(N)

- Visit each node exactly once

**Space Complexity**: O(W)

- W = maximum width of tree (max nodes at any level)
- Queue can hold at most W nodes
- Worst case: O(N) for a complete binary tree's last level

## Comparison: DFS vs BFS

| Aspect | DFS (Right-First) | BFS (Level-Order) |
| -------- | ------------------- | ------------------- |
| **Intuition** | Visit right first, first at each depth | Process levels, take last node |
| **Code Simplicity** | Very clean, concise | Slightly more code |
| **Space** | O(H) - better for wide trees | O(W) - better for tall trees |
| **Order** | Pre-order traversal | Level-order traversal |
| **Best Use** | When tree is balanced or right-heavy | When explicit level info needed |

## Key Insights

1. **DFS Right-First**: The elegant trick is visiting right before left, ensuring the first node at each depth is the visible one.

2. **Size Check**: Using `currDepth == result.size()` is a clean way to detect "first time at this level" without additional data structures.

3. **Left Subtree Matters**: Even though we want the right view, left subtree nodes can be visible if they extend deeper than the right subtree.

4. **Both Work**: Both approaches are correct - DFS is typically more elegant and uses less space for wide trees.

## Edge Cases

- **Empty tree**: Return empty list
- **Single node**: Return [root.val]
- **Left-skewed tree**: All left nodes visible

  ```c
      1
     /
    2
   /
  3
  Output: [1, 2, 3]
  ```

- **Right-skewed tree**: All right nodes visible
- **Mixed depths**: Left subtree deeper than right in some places
