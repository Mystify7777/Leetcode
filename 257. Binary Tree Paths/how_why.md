# Recap

Given the `root` of a binary tree, return all root-to-leaf paths in any order. A **leaf** is a node with no children.

**Example:** For tree `[1,2,3,null,5]`, the paths are `["1->2->5", "1->3"]`.

## Intuition

We need to find all paths from root to every leaf node. This is a classic DFS (Depth-First Search) problem where we build the path as we traverse down the tree. When we reach a leaf node (no left or right child), we add the complete path to our result.

The key challenge is efficiently building and managing the path strings. We have two main approaches:

1. **String concatenation** - build new strings at each recursive call
2. **StringBuilder with backtracking** - use a mutable buffer and undo changes

## Approach 1: String Concatenation (Simpler)

1. Start with empty result list and empty path string.
2. Recursively traverse the tree:
   - If current node is a leaf, add `path + node.val` to result.
   - If left child exists, recurse with `path + node.val + "->"`
   - If right child exists, recurse with `path + node.val + "->"`
3. Each recursive call passes a new string, so no backtracking needed.

**Key insight:** String immutability in Java means each concatenation creates a new string, naturally providing the "undo" mechanism.

## Code 1: String Concatenation

```java
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> answer = new ArrayList<String>();
        if (root != null) searchBT(root, "", answer);
        return answer;
    }
    
    private void searchBT(TreeNode root, String path, List<String> answer) {
        if (root.left == null && root.right == null) 
            answer.add(path + root.val);
        if (root.left != null) 
            searchBT(root.left, path + root.val + "->", answer);
        if (root.right != null) 
            searchBT(root.right, path + root.val + "->", answer);
    }
}
```

## Approach 2: StringBuilder with Backtracking (More Efficient)

1. Start with empty result list and empty StringBuilder.
2. Recursively traverse the tree:
   - Save current StringBuilder length (checkpoint).
   - Append current node's value.
   - If it's a leaf, add StringBuilder content to result.
   - If not a leaf, append `"->"` and recurse on children.
   - **Backtrack:** restore StringBuilder to saved length (undo appends).
3. This reuses the same StringBuilder, avoiding string creation overhead.

**Key insight:** Backtracking (resetting StringBuilder length) allows us to reuse one mutable buffer throughout the entire traversal, significantly reducing memory allocations.

## Code 2: StringBuilder with Backtracking

```java
class Solution2 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if(root == null) {
            return res;
        }
        dsf(root, new StringBuilder(), res);
        return res;
    }

    private static void dsf(TreeNode node, StringBuilder sb, List<String> res) {
        if(node == null) {
            return;
        }
        int l = sb.length();  // Checkpoint
        
        sb.append(node.val);
        if(node.left == null && node.right == null) {
            res.add(sb.toString());
        } else {
            sb.append("->");
            dsf(node.left, sb, res);
            dsf(node.right, sb, res);
        }
        sb.setLength(l);  // Backtrack to checkpoint
    }
}
```

## Comparison: Solution 1 vs Solution 2

### **Solution 1: String Concatenation**

**Pros:**

- ✅ **Simpler and more intuitive** - easier to understand and code
- ✅ **No backtracking logic** - strings are immutable, each call gets its own
- ✅ **Cleaner structure** - separation between leaf and non-leaf cases is obvious

**Cons:**

- ❌ **Higher memory overhead** - creates many intermediate string objects
- ❌ **Slower** - string concatenation is O(n) for each append
- ❌ **More GC pressure** - lots of temporary strings need garbage collection

### **Solution 2: StringBuilder with Backtracking**

**Pros:**

- ✅ **More efficient** - single StringBuilder reused throughout
- ✅ **Better performance** - O(1) appends, O(1) backtracking
- ✅ **Lower memory** - fewer object allocations

**Cons:**

- ❌ **More complex** - requires understanding backtracking pattern
- ❌ **Manual state management** - need to save/restore StringBuilder length
- ❌ **Less intuitive** - the backtracking logic can be confusing

### **Performance Analysis**

For a tree with `n` nodes and `k` leaf nodes with average path length `h`:

| Metric | Solution 1 | Solution 2 |
|--------|-----------|-----------|
| **Time** | O(n × h) | O(n) |
| **Space (aux)** | O(n × h) | O(h) |
| **String objects** | O(n × h) | O(k) |
| **Code complexity** | Low | Medium |

### **When to Use Each**

**Use Solution 1 (String)** when:

- Code clarity is more important than performance
- Tree is small (< 1000 nodes)
- You're in an interview and want to code quickly
- Team prefers readable code over micro-optimizations

**Use Solution 2 (StringBuilder)** when:

- Performance is critical
- Dealing with large trees
- Minimizing GC pressure matters
- You're comfortable with backtracking patterns

## Correctness

Both solutions are correct and follow the same DFS pattern:

- **Base case:** Leaf nodes (no children) - add complete path to result
- **Recursive case:** Non-leaf nodes - recurse on children with updated path
- **Path building:** Solution 1 builds paths via concatenation; Solution 2 via append/backtrack
- **Termination:** Both visit each node exactly once; recursion ends at leaves

**Solution 2's null check:** The explicit `if(node == null) return` at the start of `dsf` is redundant given the parent's null checks before recursion, but it makes the code safer if called incorrectly.

## Complexity

### Solution 1: String Concatenation

- **Time:** O(n × h) where n is nodes, h is average path length (each concat is O(h))
- **Space:** O(n × h) for intermediate strings + O(h) recursion stack

### Solution 2: StringBuilder

- **Time:** O(n) - each node visited once, O(1) operations per node
- **Space:** O(h) for StringBuilder and recursion stack

Both produce O(k × h) space in the result list where k is leaf count.

## Edge Cases

- **Empty tree (`root = null`):** Return empty list. Solution 1 checks before calling helper; Solution 2 checks in main function.
- **Single node (root is leaf):** Return `["root_val"]`. Both handle correctly.
- **Left-skewed tree:** Path becomes very long. Solution 2's efficiency advantage increases.
- **Full binary tree:** Many paths. Solution 2 reuses StringBuilder across all paths efficiently.
- **Large node values:** Longer strings; Solution 2's append is still O(1) per digit.

## Takeaways

- **String immutability:** In Java, strings are immutable, making concatenation create new objects. StringBuilder is mutable and efficient for building strings.
- **Backtracking pattern:** Saving state (length), making changes, then restoring state is a powerful technique for reusing mutable structures.
- **Trade-off:** Simplicity vs efficiency. Solution 1 is clearer but less efficient; Solution 2 is optimal but requires more thought.
- **DFS for paths:** Root-to-leaf problems naturally fit DFS traversal.
- **When optimization matters:** For small inputs, readability wins. For large inputs or tight constraints, optimization wins.

## Insights & Recommendations

**For interviews:** Start with Solution 1. If interviewer asks for optimization, explain Solution 2's approach and the trade-offs. This shows you can code quickly AND understand performance optimization.

**For production:** Use Solution 2 if performance matters (large trees, high frequency). Otherwise, Solution 1's clarity may reduce bugs and maintenance cost.

**General principle:** "Premature optimization is the root of all evil" - Donald Knuth. Write clear code first, optimize when profiling shows it's needed.

**The backtracking pattern** in Solution 2 generalizes to many problems: combinations, permutations, subsets, Sudoku solver, etc. Master this pattern—it's a fundamental technique in competitive programming and interviews.

## Alternative: Iterative BFS Approach

```java
class Solution3 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) return paths;
        
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<String> pathQueue = new LinkedList<>();
        
        nodeQueue.offer(root);
        pathQueue.offer(String.valueOf(root.val));
        
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            String path = pathQueue.poll();
            
            if (node.left == null && node.right == null) {
                paths.add(path);
            }
            
            if (node.left != null) {
                nodeQueue.offer(node.left);
                pathQueue.offer(path + "->" + node.left.val);
            }
            
            if (node.right != null) {
                nodeQueue.offer(node.right);
                pathQueue.offer(path + "->" + node.right.val);
            }
        }
        
        return paths;
    }
}
```

**Pros:** Iterative (no recursion stack), level-order traversal.
**Cons:** Uses two queues, still has string concatenation overhead.
