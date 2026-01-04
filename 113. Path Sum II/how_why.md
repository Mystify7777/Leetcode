# How\_Why.md: Path Sum II

## Problem

Given the `root` of a binary tree and an integer `targetSum`, return **all root-to-leaf paths** where the sum of the node values in the path equals `targetSum`.

A **leaf** is a node with no children.

**Example:**

```c
Input: root = [5,4,8,11,null,13,4,7,2,null,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
```

---

## Brute-force Approach

### Idea

* Explore every possible path from root to leaf.
* At each leaf node, check if the sum equals `targetSum`.
* Use linear search without optimization.

### Code

```java
public List<List<Integer>> pathSumBF(TreeNode root, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    findAllPaths(root, targetSum, 0, path, result);
    return result;
}

void findAllPaths(TreeNode node, int targetSum, int currentSum, 
                  List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;
    
    path.add(node.val);
    currentSum += node.val;
    
    // Check if it's a leaf node
    if (node.left == null && node.right == null) {
        if (currentSum == targetSum) {
            result.add(new ArrayList<>(path));
        }
    } else {
        findAllPaths(node.left, targetSum, currentSum, path, result);
        findAllPaths(node.right, targetSum, currentSum, path, result);
    }
    
    path.remove(path.size() - 1); // backtrack
}
```

### Limitation

* Creates new path for every node, even non-leaves.
* Redundant checks throughout traversal.

---

## Optimized Approach (DFS with Backtracking)

### Idea*

1. Use **Depth-First Search (DFS)** to traverse the tree.
2. Maintain a `currentPath` list and reduce the target sum as we go deeper.
3. At each leaf node, check if the remaining sum equals the node value.
4. **Backtrack** by removing the current node from the path after exploring both subtrees.
5. Only check for `targetSum` at leaf nodes to avoid unnecessary checks.

### Code*

```java
public List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> res = new ArrayList<List<Integer>>();
    pathSum(root, sum, new ArrayList<Integer>(), res);
    return res;
}

void pathSum(TreeNode root, int sum, List<Integer> sol, List<List<Integer>> res) {
    if (root == null) {
        return;
    }
    
    sol.add(root.val);
    
    // Check if it's a leaf node with matching sum
    if (root.left == null && root.right == null && sum == root.val) {
        res.add(new ArrayList<Integer>(sol));
    } else {
        pathSum(root.left, sum - root.val, sol, res);
        pathSum(root.right, sum - root.val, sol, res);
    }
    
    sol.remove(sol.size() - 1); // backtrack
}
```

### Example Walkthrough

**Tree:**

```s
      5
     / \
    4   8
   /   / \
  11  13  4
 / \      \
7   2      1
targetSum = 22
```

**Traversal:**

1. Start at `5`, path = `[5]`, sum = 22 - 5 = 17
2. Go left to `4`, path = `[5,4]`, sum = 17 - 4 = 13
3. Go left to `11`, path = `[5,4,11]`, sum = 13 - 11 = 2
   - Go left to `7` (leaf), sum = 2 - 7 = -5 ✗
   - Backtrack, go right to `2` (leaf), sum = 2 - 2 = 0 ✓ → Add `[5,4,11,2]`
4. Backtrack to `5`, go right to `8`, path = `[5,8]`, sum = 17 - 8 = 9
5. Go left to `13` (leaf), sum = 9 - 13 = -4 ✗
6. Go right to `4`, path = `[5,8,4]`, sum = 9 - 4 = 5
   - Go right to `1` (leaf), sum = 5 - 1 = 4 ✓ → Add `[5,8,4,1]`

**Result:** `[[5,4,11,2], [5,8,4,1]]`

### Why This Works

* **Backtracking:** Removing elements from the path ensures we only add valid root-to-leaf paths.
* **Early termination:** Only check at leaf nodes, avoiding unnecessary calculations.
* **Reduced target:** Passing `sum - root.val` makes the logic cleaner.
* **Time complexity:** O(n) where n is the number of nodes (each node visited once).
* **Space complexity:** O(h) where h is the height of the tree (call stack depth).

---

## Key Insights

1. **Backtracking pattern:** Add node → explore → remove node.
2. **Leaf check:** `node.left == null && node.right == null`
3. **Path sum check:** Only validate at leaves to avoid false positives.
4. **Efficiency:** DFS is optimal for this problem as we must explore all paths anyway.
