# How_Why.md – Find Largest Value in Each Tree Row (LeetCode 515)

## ❌ Brute Force Idea

We want the largest node value at each level of a binary tree.

**Naïve idea**:

* Traverse the tree level by level.
* For each level, collect all values in a list, sort it, and take the last element as the max.
* Append to result.

⚠️ Problem: Sorting each level is wasteful. If a level has `k` nodes, sorting is `O(k log k)`. Across all levels, that adds extra complexity.

---

## ✅ Approach 1 – BFS Level Order (O(n))

A clean solution uses **level-order traversal (BFS)**:

1. Use a queue to traverse level by level.
2. For each level:

   * Track the maximum value among nodes at that depth.
   * Add the max to result.
3. Return the result list.

### Example

```
    1
   / \
  3   2
 / \   \
5   3   9
```

* Level 0 → [1] → max=1
* Level 1 → [3,2] → max=3
* Level 2 → [5,3,9] → max=9

Result = `[1, 3, 9]`

### Code

```java
class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                max = Math.max(max, node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            res.add(max);
        }

        return res;
    }
}
```

---

## ✅ Approach 2 – DFS with Depth Tracking (O(n), Optimized Space)

Instead of BFS, we can do **DFS** and keep track of depth:

* If this is the **first node** we see at depth `d`, add it to the result list.
* Otherwise, update result[d] with the larger value.
* Recurse into left and right subtrees with depth+1.

This avoids an explicit queue and is elegant.

### Code (DFS)

```java
class Solution {
    List<Integer> ans;
    
    public List<Integer> largestValues(TreeNode root) {
        ans = new ArrayList<>();
        dfs(root, 0);
        return ans;
    }
    
    private void dfs(TreeNode node, int depth) {
        if (node == null) return;

        if (depth == ans.size()) {
            ans.add(node.val);
        } else {
            ans.set(depth, Math.max(ans.get(depth), node.val));
        }

        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }
}
```

---

## 📊 Complexity

* **Time:** O(n), every node visited once.
* **Space:**

  * BFS → O(width of tree), worst O(n).
  * DFS → O(height of tree), O(log n) for balanced, O(n) for skewed.

---

## ✅ Key Takeaways

* This problem is a **level-based aggregation** problem.
* BFS is more intuitive, DFS is more compact.
* Both guarantee `O(n)` performance.

---
