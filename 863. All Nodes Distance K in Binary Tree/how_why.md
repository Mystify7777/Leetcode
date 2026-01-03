# How_Why.md: All Nodes Distance K in Binary Tree

## Problem

Given the `root` of a binary tree, the value of a target node `target`, and an integer `k`, return an array of the values of all nodes that have a distance `k` from the `target` node.

You can return the answer in **any order**.

**Example:**

```c
Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]

         3
       /   \
      5     1
     / \   / \
    6   2 0   8
       / \
      7   4

Nodes at distance 2 from 5: [7, 4, 1]
```

---

## Approach: BFS with Parent Mapping

### Idea

* **Problem:** Trees only have parent → child references, but we need to go **upward** too
* **Solution:**
  1. Build a **parent map** using BFS/DFS
  2. Treat tree as an **undirected graph**
  3. Use **BFS from target node** to find all nodes at distance k
* Track **visited nodes** to avoid cycles

### Code

```java
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        
        // Step 1: Build parent map using BFS
        Map<Integer, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode top = queue.poll();

                if (top.left != null) {
                    parent.put(top.left.val, top);
                    queue.offer(top.left);
                }

                if (top.right != null) {
                    parent.put(top.right.val, top);
                    queue.offer(top.right);
                }
            }
        }

        // Step 2: BFS from target to find nodes at distance k
        Map<Integer, Integer> visited = new HashMap<>();
        queue.offer(target);
        
        while (k > 0 && !queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode top = queue.poll();
                visited.put(top.val, 1);

                // Explore left child
                if (top.left != null && !visited.containsKey(top.left.val)) {
                    queue.offer(top.left);
                }

                // Explore right child
                if (top.right != null && !visited.containsKey(top.right.val)) {
                    queue.offer(top.right);
                }

                // Explore parent (upward)
                if (parent.containsKey(top.val) && !visited.containsKey(parent.get(top.val).val)) {
                    queue.offer(parent.get(top.val));
                }
            }

            k--;
        }

        // Collect all nodes at distance k
        while (!queue.isEmpty()) {
            ans.add(queue.poll().val);
        }
        
        return ans;
    }
}
```

### Why This Works

* **Step 1: Building Parent Map**

  ```c
  Tree:       3
            /   \
           5     1
          / \
         6   2
  
  Parent Map:
  {5: 3, 1: 3, 6: 5, 2: 5}
  (root 3 has no parent)
  ```

* **Step 2: BFS from Target (target=5, k=2)**

  ```c
  Distance 0: [5] (target)
  ↓
  Distance 1: [3, 6, 2] (parent, left child, right child)
  ↓
  Distance 2: [1, 7, 4] ← Answer!
  
  Detailed:
  - From 5: go to parent(3), left(6), right(2)
  - From 3: go to right(1) - already visited 5
  - From 6: no children
  - From 2: go to left(7), right(4)
  ```

* **Example Walkthrough (target=5, k=2):**

  ```c
           3
         /   \
        5     1
       / \   / \
      6   2 0   8
         / \
        7   4
  
  Level 0 (k=2): queue = [5]
  Level 1 (k=1): queue = [3, 6, 2] (from 5: parent, left, right)
  Level 2 (k=0): queue = [1, 7, 4]
    - From 3: add 1 (right child of 3)
    - From 2: add 7, 4 (children of 2)
  
  Answer: [1, 7, 4] ✓
  ```

* **Time Complexity:** **O(n)** - visit each node twice (build map + BFS)
* **Space Complexity:** **O(n)** - parent map, visited set, queue

---

## Approach 2: DFS with Distance Tracking

### Idea*

* Use **DFS** to find target and track distance from target
* When going **down** from target: find children at distance k
* When going **up** through parent: track remaining distance

### Code*

```java
class Solution {
    List<Integer> ans = new LinkedList<>();
    
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        foundAtDistance(root, target, k);
        return ans;
    }

    // Find children at distance k below given node
    public void findChildrenAtKDistance(TreeNode root, int distance) {
        if (root == null) return;
        if (distance < 0) return;
        
        if (distance == 0) {
            ans.add(root.val);
            return;
        }
        
        findChildrenAtKDistance(root.left, distance - 1);
        findChildrenAtKDistance(root.right, distance - 1);
    }

    // Returns distance from this node to target (-1 if not found)
    public int foundAtDistance(TreeNode root, TreeNode target, int k) {
        if (root == null) {
            return -1;
        }

        // Found target
        if (root.val == target.val) {
            findChildrenAtKDistance(root, k);
            return 0;
        }

        // Search in left subtree
        int leftDist = foundAtDistance(root.left, target, k);
        if (leftDist != -1) {
            // Target found in left subtree
            if (leftDist + 1 == k) {
                ans.add(root.val);
            } else {
                // Look in right subtree with remaining distance
                findChildrenAtKDistance(root.right, k - leftDist - 2);
            }
            return leftDist + 1;
        }

        // Search in right subtree
        int rightDist = foundAtDistance(root.right, target, k);
        if (rightDist != -1) {
            // Target found in right subtree
            if (rightDist + 1 == k) {
                ans.add(root.val);
            } else {
                // Look in left subtree with remaining distance
                findChildrenAtKDistance(root.left, k - rightDist - 2);
            }
            return rightDist + 1;
        }

        return -1; // Target not found in this subtree
    }
}
```

### Why This Works*

* **DFS Logic:**

  ```steps
  1. Find target node while tracking path
  2. At target: collect all nodes k distance below
  3. Going up: at each ancestor
     - If ancestor is k distance from target → add it
     - Otherwise: search opposite subtree with remaining distance
  ```

* **Example (target=5, k=2):**

  ```c
           3 (dist from 5 = 1)
         /   \
        5*    1 (dist = 2) ← Add!
       / \
      6   2 (dist = 1)
         / \
        7   4 (dist = 2) ← Add!
  
  Step 1: Found target 5, find children at k=2
    → Go down 2 levels: find 7, 4 ✓
  
  Step 2: Return to parent 3 (distance = 1)
    → Need k-1-1 = 0 more from 3
    → Search right subtree with distance 2-1-1=0
    → Find 1 at distance 0 ✓
  ```

* **Time Complexity:** **O(n)** - visit each node once
* **Space Complexity:** **O(h)** - recursion stack (h = height)

---

## Comparison

| Approach | Time | Space | Notes |
| -------- | ---- | ----- | ----- |
| BFS + Parent Map | O(n) | O(n) | **Easier to understand**, iterative |
| DFS + Distance Tracking | O(n) | O(h) | **More complex**, better space for balanced trees |

---

## Visual Example (target=5, k=2)

```c
         3
       /   \
      5     1
     / \   / \
    6   2 0   8
       / \
      7   4

BFS Approach:
Distance 0: [5]
Distance 1: [3, 6, 2]
Distance 2: [1, 7, 4] ✓

DFS Approach:
1. Find 5
2. Go down from 5: k=2 → find 7, 4
3. Return to 3 (dist=1): check right child
   → Find 1 at remaining distance 0
Result: [7, 4, 1] ✓
```

---

## Why This Approach

* **Clever Graph Transformation:** Converts tree to undirected graph
* **BFS Strength:** Natural for level-based distance queries
* **DFS Alternative:** More space-efficient for balanced trees
* **Versatile:** Parent mapping technique useful for many tree problems
* **Interview Ready:** Shows understanding of both BFS and DFS

**Key Takeaway:** When you need bidirectional traversal in a tree, build a parent map or use DFS with distance tracking!
