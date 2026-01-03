# How_Why.md: Binary Tree Cameras

## Problem

You are given the `root` of a binary tree. We install cameras on the tree nodes where each camera at a node can monitor its parent, itself, and its immediate children.

Return the **minimum number of cameras** needed to monitor all nodes of the tree.

**Example:**

```c
Input: root = [0,0,null,0,0]
Output: 1

       0*
      /
     0
    / \
   0   0

Input: root = [0,0,null,0,null,0,null,null,0]
Output: 2
```

---

## Approach: Greedy DFS with State Tracking

### Idea

* **Greedy Strategy:** Place cameras as **low as possible** (leaves' parents)
* Use **DFS** to traverse bottom-up (post-order)
* Track **3 states** for each node:
  - **-1:** NOT MONITORED (needs coverage)
  - **0:** MONITORED (covered by child/parent camera)
  - **1:** HAS CAMERA (monitoring others)

* **Key Rules:**
  1. If any child is NOT MONITORED → place camera at current node
  2. If any child HAS CAMERA → current node is MONITORED
  3. Otherwise → current node is NOT MONITORED (parent will handle)

### Code

```java
class Solution {
    private int numOfCameras = 0;
    
    public int minCameraCover(TreeNode root) {
        // If root is not monitored, place camera at root
        return dfs(root) == -1 ? numOfCameras + 1 : numOfCameras;
    }
    
    // Returns state: -1 = NOT MONITORED, 0 = MONITORED, 1 = HAS CAMERA
    private int dfs(TreeNode root) {
        if (root == null) return 0;  // null nodes are "monitored"
        
        int left = dfs(root.left);
        int right = dfs(root.right);
        
        // Rule 1: If any child is NOT MONITORED, place camera here
        if (left == -1 || right == -1) {
            numOfCameras++;
            return 1;  // This node now has camera
        }
        
        // Rule 2: If any child HAS CAMERA, this node is MONITORED
        if (left == 1 || right == 1) {
            return 0;  // Monitored by child
        }
        
        // Rule 3: Both children are MONITORED but no camera nearby
        return -1;  // Not monitored, parent will handle
    }
}
```

### Why This Works

* **Bottom-Up Greedy Strategy:**
  - Start from leaves
  - Place cameras at **leaf parents** to cover 3 nodes at once
  - Propagate monitoring status upward

* **Example Walkthrough:**

  ```c
         0
       /   \
      0     0
     / \   /
    0   0 0
  
  DFS Order (post-order): 0→0→0→0→0→0
  
  Step 1: Leftmost leaf (0)
    - null children → return -1 (NOT MONITORED)
  
  Step 2: Its sibling (0)
    - null children → return -1 (NOT MONITORED)
  
  Step 3: Their parent (0)
    - left=-1, right=-1 → PLACE CAMERA*
    - numOfCameras=1, return 1
  
  Step 4: Rightmost leaf (0)
    - null children → return -1 (NOT MONITORED)
  
  Step 5: Its parent (0)
    - left=-1 → PLACE CAMERA*
    - numOfCameras=2, return 1
  
  Step 6: Root (0)
    - left=1, right=1 → MONITORED
    - return 0
  
  Answer: 2 cameras ✓
  ```

* **State Transition Example:**

  ```c
       P (parent)
      /
     N (current)
    / \
   L   R (children)
  
  Case 1: L=-1 or R=-1 (child not monitored)
    → Place camera at N
    → N returns 1
  
  Case 2: L=1 or R=1 (child has camera)
    → N is monitored by child
    → N returns 0
  
  Case 3: L=0 and R=0 (both monitored but no camera)
    → N is not monitored
    → N returns -1 (parent will place camera)
  ```

* **Why Greedy Works:**

  ```text
  Bad Strategy: Place camera at leaves
    Each camera covers only 1-2 nodes
  
  Good Strategy: Place at leaf parents
    Each camera covers 3 nodes (parent, left child, right child)
    Optimal coverage!
  ```

* **Time Complexity:** **O(n)** - visit each node once
* **Space Complexity:** **O(h)** - recursion stack (h = height)

---

## Approach 2: Greedy DFS with Value Encoding

### Idea*

* Similar greedy strategy but uses **numeric encoding** for states
* Sum of children states determines current node's state

### Code*

```java
class Solution {
    private int ans = 0;
    
    public int minCameraCover(TreeNode root) {
        return dfs(root) > 2 ? ans + 1 : ans;
    }
    
    // Returns: 0=null/monitored, 3=not monitored, 1=has camera
    public int dfs(TreeNode node) {
        if (node == null) return 0;
        
        int val = dfs(node.left) + dfs(node.right);
        
        if (val == 0) return 3;  // Both children null/monitored → not monitored
        if (val < 3) return 0;   // At least one camera nearby → monitored
        
        ans++;
        return 1;  // Place camera here
    }
}
```

**Logic:**

```c
val = left + right

val=0: Both children null/monitored → return 3 (not monitored)
val=1: One child has camera → return 0 (monitored)
val=2: Two children have cameras → return 0 (monitored)
val≥3: At least one child not monitored → place camera, return 1
```

---

## Comparison

| Approach | Time | Space | Notes |
| -------- | ---- | ----- | ----- |
| State Tracking (-1,0,1) | O(n) | O(h) | **Clear logic**, easy to understand |
| Value Encoding | O(n) | O(h) | **More concise**, harder to read |

---

## Visual Example (root = [0,0,null,0,0])

```c
       0
      /
     0*        (* = camera)
    / \
   0   0

DFS Traversal:
1. Visit leaf 0 (left)
   → null children
   → return -1 (not monitored)

2. Visit leaf 0 (right)
   → null children
   → return -1 (not monitored)

3. Visit parent 0
   → left=-1, right=-1
   → PLACE CAMERA
   → numOfCameras=1
   → return 1 (has camera)

4. Visit root 0
   → left=1 (child has camera)
   → return 0 (monitored)

Final: 1 camera ✓

Coverage:
Camera at node 2 covers:
- Itself (node 2)
- Parent (root)
- Left child (leaf 1)
- Right child (leaf 2)
All nodes monitored!
```

---

## Why This Approach

* **Optimal:** Greedy strategy proven to give minimum cameras
* **Efficient:** Single DFS pass, O(n) time
* **Clever State Design:** 3 states capture all necessary information
* **Bottom-Up:** Leverages post-order traversal naturally
* **Interview Gold:** Demonstrates advanced DFS and greedy algorithm skills

**Key Insight:**

```md
Why place cameras at leaf parents?
- Camera at leaf: covers 1-2 nodes
- Camera at leaf parent: covers 3 nodes (parent + 2 children)
- Optimal coverage = fewer cameras!
```

**Key Takeaway:** For tree coverage problems, think bottom-up and place resources (cameras, guards, etc.) at strategic positions that maximize coverage!
