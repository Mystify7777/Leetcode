# @Mystify7777

## How & Why: LeetCode 637 — Average of Levels in Binary Tree

This note explains two solutions for computing the average value of nodes at each level in a binary tree: BFS (level-order traversal) and DFS (recursive depth-first traversal).

---

### Problem

Given the `root` of a binary tree, return the average value of the nodes on each level in the form of an array.

**Example:**

```shell
Input: root = [3,9,20,null,null,15,7]
        3
       / \
      9  20
         / \
        15  7
Output: [3.00000, 14.50000, 11.00000]
Explanation:
- Level 0: average is 3
- Level 1: average is (9 + 20) / 2 = 14.5
- Level 2: average is (15 + 7) / 2 = 11
```

---

## Solution 1: BFS (Breadth-First Search) ⭐

### Key Idea

Use **level-order traversal** with a queue. Process all nodes at the current level, calculate their average, and add children to the queue for the next level.

### Algorithm

1. Initialize a queue with the root node
2. While the queue is not empty:
   - Record the current queue size (number of nodes at this level)
   - Sum all node values at this level while adding their children to the queue
   - Calculate average = sum / count
   - Add average to result list
3. Return the result

### Implementation (Java)

```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>(List.of(root));
        List<Double> ans = new ArrayList<>();
        
        while (q.size() > 0) {
            double qlen = q.size();  // Number of nodes at current level
            double row = 0;          // Sum of values at current level
            
            // Process all nodes at current level
            for (int i = 0; i < qlen; i++) {
                TreeNode curr = q.poll();
                row += curr.val;
                
                // Add children for next level
                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
            }
            
            // Calculate and store average
            ans.add(row / qlen);
        }
        
        return ans;
    }
}
```

### Alternative BFS Implementation

```java
class Solution {
    private List<Double> averageOfLevelsBfs(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<Double> answer = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            double total = 0;
            
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                    total += node.val;
                }
            }
            answer.add(total / size);
        }
        
        return answer;
    }
}
```

### Complexity

- **Time**: O(n) where n is the number of nodes
  - We visit each node exactly once
- **Space**: O(w) where w is the maximum width of the tree
  - Queue holds at most the maximum number of nodes at any level
  - In worst case (complete binary tree), w ≈ n/2 = O(n)

### Why This Works

- **Level-by-Level Processing**: The queue naturally maintains level boundaries. By processing exactly `qlen` nodes in each iteration, we process one complete level at a time.
- **Queue Size as Level Indicator**: The queue size at the start of each iteration tells us how many nodes are at the current level.
- **Children Preparation**: As we process the current level, we add children to the queue, preparing the next level.

---

## Solution 2: DFS (Depth-First Search)

### Key Idea Alternate

Use **recursive DFS** to traverse the tree, tracking the level of each node. Accumulate sums and counts for each level, then calculate averages at the end.

### Algorithm Alternate

1. Maintain two lists: `sum` (total sum at each level) and `count` (number of nodes at each level)
2. Recursively traverse the tree:
   - If current level exists in lists, update sum and count
   - If it's a new level, add new entries
   - Recursively visit left and right children with `level + 1`
3. After traversal, compute averages: `result[i] = sum[i] / count[i]`

### Implementation (Java) Alternate

```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        List<Integer> nodesAtLevel = new ArrayList<>();
        
        // DFS traversal to collect sums and counts
        average(root, 0, result, nodesAtLevel);
        
        // Calculate averages
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i) / nodesAtLevel.get(i));
        }
        
        return result;
    }
    
    private void average(TreeNode node, int level, List<Double> sum, List<Integer> count) {
        if (node == null) {
            return;
        }
        
        if (level < sum.size()) {
            // Level already exists, update sum and count
            sum.set(level, sum.get(level) + node.val);
            count.set(level, count.get(level) + 1);
        } else {
            // New level, add initial values
            sum.add(1.0 * node.val);
            count.add(1);
        }
        
        // Recursively process left and right subtrees
        average(node.left, level + 1, sum, count);
        average(node.right, level + 1, sum, count);
    }
}
```

### Complexity Alternate

- **Time**: O(n) where n is the number of nodes
  - We visit each node exactly once
- **Space**: O(h) where h is the height of the tree
  - Recursion stack depth = height of tree
  - Additional space for sum and count lists = O(h)
  - In worst case (skewed tree), h = n, so O(n)

### Why This Works Alternate

- **Level Tracking**: The `level` parameter acts as an index for organizing nodes by depth.
- **Pre-order Traversal**: We process the current node, then recursively process left and right subtrees, naturally visiting all nodes.
- **Deferred Calculation**: By collecting sums and counts first, we can calculate all averages at the end in a single pass.

---

## Comparison: BFS vs DFS

| Aspect | BFS | DFS |
| -------- | ----- | ----- |
| **Approach** | Iterative with queue | Recursive |
| **Time** | O(n) | O(n) |
| **Space** | O(w) - width | O(h) - height |
| **Code** | More intuitive for level problems | More concise |
| **Best For** | Level-by-level processing | When recursion is preferred |

### When to Use Which

1. **BFS** ⭐
   - More natural for level-order problems
   - Better space complexity for skewed trees (O(1) vs O(n))
   - Easier to understand the "level" concept
   - Preferred for this problem

2. **DFS**
   - When you prefer recursive solutions
   - Better space complexity for wide/balanced trees
   - When you need to process entire tree before calculating results

---

## Example Walkthrough (BFS)

Input:

```bsh
        3
       / \
      9  20
         / \
        15  7
```

**Execution:**

**Initial**: `queue = [3]`, `ans = []`

**Iteration 1** (Level 0):

- `qlen = 1`, `row = 0`
- Poll 3: `row = 3`, add children 9 and 20
- `queue = [9, 20]`
- Average = 3 / 1 = 3.0
- `ans = [3.0]`

**Iteration 2** (Level 1):

- `qlen = 2`, `row = 0`
- Poll 9: `row = 9`, no children
- Poll 20: `row = 29`, add children 15 and 7
- `queue = [15, 7]`
- Average = 29 / 2 = 14.5
- `ans = [3.0, 14.5]`

**Iteration 3** (Level 2):

- `qlen = 2`, `row = 0`
- Poll 15: `row = 15`, no children
- Poll 7: `row = 22`, no children
- `queue = []`
- Average = 22 / 2 = 11.0
- `ans = [3.0, 14.5, 11.0]`

**Result**: `[3.0, 14.5, 11.0]`

---

## Common Pitfalls

1. **Integer Division**: Use `double` for both sum and count to avoid integer division errors.

   ```java
   // Wrong: int sum, int count → ans.add(sum / count) truncates
   // Right: double row, double qlen → ans.add(row / qlen)
   ```

2. **Queue Size Changes**: Store queue size before the loop, as it changes when adding children.

   ```java
   // Wrong: for (int i = 0; i < q.size(); i++)  // size changes!
   // Right: double qlen = q.size(); for (int i = 0; i < qlen; i++)
   ```

3. **Null Children**: Always check if children are null before adding to queue.

   ```java
   if (curr.left != null) q.offer(curr.left);
   ```
