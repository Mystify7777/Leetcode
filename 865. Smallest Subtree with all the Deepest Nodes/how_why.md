# 865. Smallest Subtree with all the Deepest Nodes

## Problem Understanding

Given the root of a binary tree, return the smallest subtree such that it contains all the deepest nodes in the original tree. A node is called the deepest if it has the largest depth possible among any node in the entire tree.

## Approach 1: BFS + Parent Tracking + LCA-like Convergence

### How it works:

1. **BFS Traversal**: Use BFS to traverse the tree level by level while maintaining a parent map
2. **Track Last Level**: Keep updating the last level nodes - these will be the deepest nodes
3. **Convergence**: Starting from all deepest nodes, move upward (using parent pointers) until all paths converge to a single node - this is the LCA of all deepest nodes

### Why it works:

- BFS guarantees we find all nodes at the maximum depth
- The smallest subtree containing all deepest nodes is their Lowest Common Ancestor (LCA)
- Moving parents upward simultaneously ensures we find the convergence point

### Code Flow:

```s
Tree:        3
           /   \
          5     1
         / \   / \
        6   2 0   8
           / \
          7   4

Step 1: BFS finds deepest nodes: [7, 4]
Step 2: Move up: [2]
Step 3: Only one node left, return 2
```

### Complexity:

- **Time**: O(n) - BFS traversal O(n) + convergence O(h) where h ≤ n
- **Space**: O(n) - parent map + queue + deepest set

## Approach 2: DFS with Depth Tracking (Optimal)

### How it works

1. **DFS Recursion**: Recursively calculate depth of left and right subtrees
2. **Compare Depths**: At each node, compare left and right subtree depths
3. **Decision Making**:
   - If left depth > right depth: deepest nodes are on the left
   - If right depth > left depth: deepest nodes are on the right
   - If equal: current node is the LCA of all deepest nodes (they're in both subtrees)

### Why it works

- If both subtrees have the same depth, all deepest nodes must be at equal distances in both subtrees
- This means the current node is the smallest subtree containing all deepest nodes
- If depths differ, all deepest nodes are in the deeper subtree

### Code Flow

```s
Tree:        3
           /   \
          5     1
         / \   / \
        6   2 0   8
           / \
          7   4

DFS(3):
  DFS(5):
    DFS(6): depth=1
    DFS(2):
      DFS(7): depth=1
      DFS(4): depth=1
      Equal depths! Return (node=2, depth=3)
    Return (node=2, depth=4)
  DFS(1):
    DFS(0): depth=1
    DFS(8): depth=1
    Equal depths! Return (node=1, depth=2)
  
  Compare: left.depth(4) > right.depth(2)
  Return (node=2, depth=5)
```

### Complexity

- **Time**: O(n) - visit each node once
- **Space**: O(h) - recursion stack where h is tree height

## Comparison of Approaches

| Aspect | Approach 1 (BFS + LCA) | Approach 2 (DFS + Depth) |
|--------|------------------------|--------------------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(n) | O(h) |
| **Traversal Type** | Level-order (BFS) | Post-order (DFS) |
| **Data Structures** | HashMap, Queue, Set, List | Result object, recursion stack |
| **Memory Usage** | Higher (stores parent map for all nodes) | Lower (only recursion stack) |
| **Code Complexity** | More complex (multiple steps) | Cleaner and more elegant |
| **Intuition** | Easier to visualize (find deepest, then LCA) | Requires understanding of depth comparison |
| **Number of Passes** | 2 passes (find deepest + converge) | 1 pass (DFS traversal) |
| **Best For** | When you need parent relationships | When you want optimal space usage |

## Key Insights

1. **Problem Transformation**: This is essentially finding the LCA of all deepest nodes

2. **Depth Equality Property**: When left and right subtrees have equal depth, the current node must be the answer because:
   - All deepest nodes are at the same level
   - Some are in the left subtree and some are in the right
   - This node is their LCA

3. **Space Optimization**: Approach 2 is superior because:
   - No need to store parent relationships
   - No need to track all deepest nodes explicitly
   - Uses only O(h) space vs O(n)

4. **Single Pass vs Multiple Steps**: Approach 2 solves the problem in one DFS pass, while Approach 1 requires:
   - BFS to find deepest nodes
   - Iterative convergence to find LCA

## Recommendation

**Use Approach 2 (DFS + Depth)** because:

- ✅ More space efficient: O(h) vs O(n)
- ✅ Cleaner, more elegant code
- ✅ Single-pass solution
- ✅ Better performance in practice
- ✅ Easier to extend for similar problems

**Use Approach 1** only when:

- You specifically need parent relationships for other operations
- You're more comfortable with iterative solutions
- You need to visualize the deepest nodes explicitly

## Related Problems

- 1123. Lowest Common Ancestor of Deepest Leaves (identical problem)
- 236. Lowest Common Ancestor of a Binary Tree
- 104. Maximum Depth of Binary Tree
- 110. Balanced Binary Tree
