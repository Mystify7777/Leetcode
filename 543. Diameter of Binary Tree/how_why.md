# 543. Diameter of Binary Tree - Explanation

## Problem Understanding

The **diameter** of a binary tree is the **length of the longest path** between any two nodes in the tree. This path may or may not pass through the root.

The **length of a path** is measured by the number of edges between nodes.

**Key Points:**

- The diameter is the longest path, measured in edges (not nodes)
- The path can be anywhere in the tree (doesn't have to go through root)
- We need to consider paths through every possible node as a potential "turning point"

**Example:**

```shell
Input: root = [1,2,3,4,5]

          1
         / \
        2   3
       / \
      4   5

Longest path: 4 → 2 → 1 → 3 (3 edges)
Or equivalently: 5 → 2 → 1 → 3 (3 edges)

Output: 3
```

## Key Insight

The diameter passing through a node equals:

- **Left height** + **Right height** at that node

The overall diameter is the **maximum** of these values across all nodes.

```java
For node N:
diameter_through_N = height(left_subtree) + height(right_subtree)

Global diameter = max(diameter_through_every_node)
```

## Approach: DFS with Global Maximum

### Algorithm Overview

1. **Calculate height** of each subtree recursively (DFS)
2. **At each node**, calculate the diameter passing through it: `left_height + right_height`
3. **Track maximum** diameter seen across all nodes using a global variable
4. **Return height** upward for parent calculations

### Why This Approach Works

- **Bottom-up calculation**: We calculate heights from leaves up to root
- **Local vs Global**:
  - Each node returns its height (local contribution to parent)
  - Each node updates the global max with its diameter (left + right)
- **Single pass**: We only visit each node once (O(N))
- **Optimal substructure**: The height of a node depends on the heights of its children

### Visualization

```m
          1
         / \
        2   3
       / \
      4   5

At node 4: left=0, right=0, diameter=0, return height=1
At node 5: left=0, right=0, diameter=0, return height=1
At node 2: left=1, right=1, diameter=2, return height=2
At node 3: left=0, right=0, diameter=0, return height=1
At node 1: left=2, right=1, diameter=3, return height=3

Maximum diameter = 3
```

## Code Walkthrough

```java
public class Solution {
    int max = 0;  // Global variable to track maximum diameter
    
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);  // Trigger DFS traversal
        return max;       // Return the maximum diameter found
    }
    
    private int maxDepth(TreeNode root) {
        // Base case: null node has height 0
        if (root == null) return 0;
        
        // Recursively get heights of left and right subtrees
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        // Update global maximum with diameter through current node
        // Diameter = left_height + right_height (number of edges)
        max = Math.max(max, left + right);
        
        // Return height of current node to parent
        // Height = max(left, right) + 1 (including current node)
        return Math.max(left, right) + 1;
    }
}
```

### Step-by-Step Breakdown

1. **Global Variable**: `max` stores the maximum diameter found so far

2. **maxDepth Function**: Serves dual purpose:
   - Returns the height of the subtree rooted at current node
   - Updates global max with the diameter passing through current node

3. **Key Calculation**:
   - `left + right`: Diameter through current node (edges on both sides)
   - `Math.max(left, right) + 1`: Height of current node (tallest side + current node)

4. **Why it works**:
   - Every possible path is considered (we check every node as a potential "turning point")
   - The longest path must pass through some node - we find it by checking all

## Example Trace

```c
Tree:       1
           / \
          2   3
         /
        4

Step-by-step execution:

maxDepth(1):
  maxDepth(2):
    maxDepth(4):
      maxDepth(null) → return 0
      maxDepth(null) → return 0
      left=0, right=0
      max = max(0, 0+0) = 0
      return max(0,0)+1 = 1
    
    maxDepth(null) → return 0
    left=1, right=0
    max = max(0, 1+0) = 1
    return max(1,0)+1 = 2
  
  maxDepth(3):
    maxDepth(null) → return 0
    maxDepth(null) → return 0
    left=0, right=0
    max = max(1, 0+0) = 1
    return max(0,0)+1 = 1
  
  left=2, right=1
  max = max(1, 2+1) = 3
  return max(2,1)+1 = 3

Final result: max = 3
```

## Complexity Analysis

**Time Complexity**: O(N)

- Visit each node exactly once
- At each node: O(1) operations
- N = total number of nodes

**Space Complexity**: O(H)

- H = height of tree (recursion stack depth)
- Best case (balanced tree): O(log N)
- Worst case (skewed tree): O(N)

## Important Distinctions

### Height vs Diameter

- **Height**: Number of edges from node to deepest leaf (or number of nodes on longest path to leaf)
- **Diameter**: Number of edges in longest path between any two nodes

```m
Example:
    1
   /
  2
 /
3

Height of tree: 2 edges (from 1 to 3)
Diameter of tree: 2 edges (from 3 to 2 to 1, or just 3→2→1)
```

### Why Return Height and Update Diameter?

- **Return height**: Parent needs to know how tall the subtree is to calculate its own diameter
- **Update diameter**: Track the best diameter seen anywhere in tree

This is a classic example of **return value ≠ final answer**:

- We return local information (height) for recursive calculation
- We maintain global information (max diameter) as a side effect

## Common Pitfalls

1. **Confusing edges with nodes**: Diameter is measured in edges, not nodes
   - Path with 4 nodes = 3 edges

2. **Assuming diameter goes through root**: The longest path might be entirely in a subtree

   ```m
        1
       /
      2
     / \
    3   4
   
   Diameter is 2 (from 3→2→4), doesn't touch root 1
   ```

3. **Only checking one subtree**: Must consider diameter through every node

4. **Not using global variable**: Hard to track maximum across all recursive calls without it

## Key Insights

1. **Dual-purpose function**: `maxDepth` both calculates height and tracks diameter - elegant solution

2. **Global state**: Using a class variable to track max across recursive calls is clean and efficient

3. **Single traversal**: We solve the problem in one DFS pass - no need for multiple traversals

4. **Bottom-up approach**: Calculate from leaves up, building on subproblem solutions

## Alternative Approaches

While the shown solution is optimal, you could also:

1. **Return pair**: Return both height and diameter from each call (more complex)
2. **Two passes**: First calculate all heights, then calculate diameter (less efficient)
3. **Use array**: Pass `int[] max = {0}` instead of instance variable (if avoiding class state)

The given solution strikes the best balance of simplicity and efficiency.
