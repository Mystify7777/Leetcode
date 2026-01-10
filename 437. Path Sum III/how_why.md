# Recap

Given the root of a binary tree and an integer `targetSum`, return the number of paths in the tree where the sum of the node values equals `targetSum`. A path does not need to start at the root or end at a leaf, but it must go downwards (only moving to child nodes).

## Problem Understanding

- Paths can start and end at any node (not just root to leaf)
- Paths must go downward (can't go to parent nodes)
- We need to count all paths with sum equal to `targetSum`

---

## Approach 1: Brute Force - DFS from Every Node

**Concept:** Start a DFS from every node in the tree and count paths going downward from that starting point.

```java
class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        if(root == null) return 0;
        return pathSumFromRoot(root, targetSum) + 
               pathSum(root.left, targetSum) + 
               pathSum(root.right, targetSum);
    }

    private static int pathSumFromRoot(TreeNode root, long sum) {
        if(root == null) return 0;
        
        int res = 0;
        if(root.val == sum) {
            res += 1;
        }
        
        res += pathSumFromRoot(root.left, sum - root.val);
        res += pathSumFromRoot(root.right, sum - root.val);
        
        return res;
    }
}
```

**How it works:**

1. For each node, call `pathSumFromRoot()` to count paths starting from that node
2. `pathSumFromRoot()` uses DFS, tracking the remaining sum needed
3. When remaining sum becomes 0, we found a valid path
4. Recursively check left and right children with adjusted sum
5. Also recursively call `pathSum()` on left and right subtrees to start from their nodes

**Time Complexity:** O(n²)

- For each of n nodes, we potentially visit all n nodes below it
- In a skewed tree: O(n²)
- In a balanced tree: O(n log n) average

**Space Complexity:** O(h) where h is tree height

- Recursion stack depth

---

## Approach 2: Prefix Sum with Backtracking (Solution2)

**Concept:** Use a prefix sum map to track path sums as we traverse. When we encounter a prefix sum, we check if there's a previous prefix sum that would create the target difference.

```java
class Solution2 {
    private void solve(TreeNode root, int targetSum, Long prefixSum, 
                      Map<Long,Integer> map, int[] ans) {
        if(root == null) return;

        prefixSum += root.val;
        Long remaining = prefixSum - targetSum;

        // If (prefixSum - targetSum) exists in map, 
        // it means there's a path from some ancestor to current node that sums to targetSum
        ans[0] += map.getOrDefault(remaining, 0);
        
        // Add current prefix sum to map
        map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        
        // Recurse left and right
        solve(root.left, targetSum, prefixSum, map, ans);
        solve(root.right, targetSum, prefixSum, map, ans);

        // Backtrack: remove current node's prefix sum before exploring other branches
        map.put(prefixSum, map.get(prefixSum) - 1);
    }

    public int pathSum(TreeNode root, int targetSum) {
        int[] ans = new int[1];
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, 1);  // Base case: empty path sum = 0
        solve(root, targetSum, 0L, map, ans);
        return ans[0];
    }
}
```

**How it works:**

1. Maintain a `prefixSum` map tracking all prefix sums from root to current node
2. For each node, compute `prefixSum = currentSum + node.val`
3. Calculate `remaining = prefixSum - targetSum`
4. If `remaining` exists in map, it means there's a valid path ending at current node
   - This path would start from when we had that previous prefix sum and end at current node
5. Add current `prefixSum` to map
6. Recurse to children
7. **Backtrack:** Remove current prefix sum from map before returning (important for exploring other branches)

**Time Complexity:** O(n)

- Each node visited once

**Space Complexity:** O(h)

- Map stores at most h different prefix sums (one for each level from root to node)

---

## Approach 3: Optimized Prefix Sum with Backtracking (Solution3)

**Concept:** Same as Approach 2 but with cleaner implementation and better backtracking logic.

```java
class Solution3 {
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1);  // Base case
        return dfs(root, 0L, targetSum, prefixSumMap);
    }
    
    private int dfs(TreeNode node, long currentSum, int target, 
                   Map<Long, Integer> prefixSumMap) {
        if (node == null) return 0;
        
        // Add current node's value to running sum
        currentSum += node.val;
        
        // Check if there's a prefix sum that would create target sum
        int count = prefixSumMap.getOrDefault(currentSum - target, 0);
        
        // Add current prefix sum to map
        prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        
        // Recurse on children
        count += dfs(node.left, currentSum, target, prefixSumMap);
        count += dfs(node.right, currentSum, target, prefixSumMap);
        
        // Backtrack: remove current prefix sum
        prefixSumMap.put(currentSum, prefixSumMap.get(currentSum) - 1);
        if (prefixSumMap.get(currentSum) == 0) {
            prefixSumMap.remove(currentSum);
        }
        
        return count;
    }
}
```

**How it works:**

- Same logic as Approach 2, but with cleaner implementation
- Passes `currentSum` directly in recursion instead of a separate variable
- Combines counting and addition in one step

**Time Complexity:** O(n)

- Each node visited once

**Space Complexity:** O(h)

- Map stores at most h prefix sums

---

## Key Insight: Prefix Sum Logic

The core insight in Approaches 2 & 3:

```c
If prefixSum[i] - prefixSum[j] = targetSum
Then sum(node[j+1] to node[i]) = targetSum
```

So when we find `(currentSum - targetSum)` in our map, it means there's a path from some ancestor to the current node that sums to targetSum.

---

## Comparison of Approaches

| Feature | Approach 1 (Brute Force) | Approach 2 (Prefix Sum v1) | Approach 3 (Prefix Sum v2) |
|---------|--------------------------|---------------------------|---------------------------|
| **Time Complexity** | O(n²) worst, O(n log n) avg | O(n) | O(n) |
| **Space Complexity** | O(h) | O(h) | O(h) |
| **Intuition** | Start DFS from every node | Use prefix sum + map | Optimized prefix sum |
| **Code Clarity** | Simple but inefficient | Good with backtracking | Best clarity |
| **Map Size** | N/A | At most h entries | At most h entries |
| **Backtracking** | Implicit (via recursion) | Explicit removal | Explicit removal with cleanup |
| **Edge Cases** | Handles naturally | Initializes map with 0→1 | Initializes map with 0→1 |
| **When to Use** | Small trees or interviews | Medium to large trees | Production code |

---

## Why Prefix Sum is Better

**Approach 1 Problem:**

- For a skewed tree (like a linked list), every node can have a path to every other node below it
- We recalculate sums multiple times
- Example: path from node 1→2→3→4 gets recalculated when starting from node 1, then node 2, then node 3

**Approach 3 Solution:**

- Visit each node exactly once
- Track all historical prefix sums
- Instantly check if a valid path exists
- Use backtracking to explore different branches correctly

---

## Example Walkthrough

Tree:
```s
      10
     /  \
    5   -3
   / \
  3   2
 /     \
3       -2
```

Finding paths where targetSum = 8:

**Approach 1:**

- Start from 10: 10+5-3? No... continue DFS
- Start from 5: 5+3=8 ✓, 5+2+(-2)... No...
- Start from -3: ...
- O(n²) calls

**Approach 3:**

- At node 5 (prefixSum=15):
  - Check if 15-8=7 is in map: No
  - Add 15 to map
  - Go left (node 3, prefixSum=18):
    - Check if 18-8=10 is in map: No
    - Continue...
  - Go right (node 2, prefixSum=17):
    - Check if 17-8=9 is in map: No
    - Continue...
- Only visit each node once!

---

## Edge Cases

- **Tree with negative numbers:** All approaches handle correctly
- **Large targetSum:** Use `long` to avoid overflow
- **Empty tree:** Return 0
- **Single node:** Works with all approaches
- **Multiple identical paths:** Map tracks frequencies

---

## Takeaways

1. **Prefix Sum Technique:** Powerful for "subarray/subpath sum" problems
2. **Backtracking in Maps:** Must remove entries when returning to explore other branches
3. **Avoid Redundant Computation:** Don't recalculate; use history
4. **Time-Space Tradeoff:** Use O(h) extra space to reduce time from O(n²) to O(n)
5. **Path Sum on Trees:** Common problem; prefix sum + DFS + backtracking is the optimal approach
