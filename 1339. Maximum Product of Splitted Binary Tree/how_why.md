# How_Why.md — Maximum Product of Splitted Binary Tree (LeetCode 1339)

## Recap

Given a binary tree with node values, you can remove exactly one edge to split the tree into two separate trees. Find the maximum product of the sums of the two resulting trees, modulo 10⁹ + 7.

**Goal:** Split the tree once and maximize `(sum1) × (sum2)`.

---

## Intuition

When you remove an edge between a parent and a subtree:

- One component is the subtree being removed.
- The other component is the remaining tree.

**Key insight:** For any subtree with sum `S`, when removed:

- Subtree sum = `S`
- Remaining tree sum = `total - S`
- Product = `(total - S) × S`

To maximize the product, try all possible subtrees and pick the one giving the maximum product.

**Optimization:** For fixed `total`, the product `(total - S) × S` is maximized when `S` is close to `total / 2` (roughly equal split).

---

## Approach 1: Two DFS Passes + BFS Enumeration (Your Implementation)

### How It Works

**Step 1: Compute all subtree sums**

- Use DFS to traverse the tree and compute cumulative sums.
- Store sum information in the tree nodes (destructively modify `node.val`).

**Step 2: Enumerate all cuts**

- Use BFS to visit every node in the tree.
- For each node, treat it as the root of a subtree being cut off.
- Calculate product: `(total - node.val) × node.val`.
- Track the maximum product.

### Code Analysis

```java
private long dfs(TreeNode node) {
    if (node == null) return 0;
    
    // Accumulate: node.val = original value + left sum + right sum
    node.val += dfs(node.left) + dfs(node.right);
    return node.val;
}

public int maxProduct(TreeNode root) {
    long total = dfs(root);  // First pass: compute all subtree sums
    
    // Second pass: BFS through all nodes to find max product
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    
    while (!q.isEmpty()) {
        TreeNode node = q.poll();
        if (node == null) continue;
        
        // If we cut the edge above this node:
        // - Subtree sum = node.val
        // - Remaining sum = total - node.val
        // - Product = (total - node.val) * node.val
        long cur = (total - node.val) * node.val;
        ans = Math.max(ans, cur);
        
        if (node.left != null) q.add(node.left);
        if (node.right != null) q.add(node.right);
    }
    
    return (int)(ans % MOD);
}
```

**Key observations:**

- The `dfs` function modifies `node.val` in-place (destructive update).
- After the first DFS, each `node.val` contains the subtree sum rooted at that node.
- BFS ensures we check all possible cuts (every subtree).

### Example Walkthrough

Tree:

```s
      1
     / \
    2   3
   / \
  4   5
```

**Step 1 - DFS (Post-order, computing subtree sums):**

```s
dfs(4): return 4
dfs(5): return 5
dfs(2): 2 + 4 + 5 = 11
dfs(3): return 3
dfs(1): 1 + 11 + 3 = 15 (total sum)
```

After dfs, `node.val` becomes:

```s
      15
     /  \
    11   3
   / \
  4   5
```

**Step 2 - BFS (Checking all cuts):**

- Cut above node(15): product = (15 - 15) × 15 = 0
- Cut above node(11): product = (15 - 11) × 11 = 4 × 11 = 44 ✓ (max so far)
- Cut above node(3): product = (15 - 3) × 3 = 12 × 3 = 36
- Cut above node(4): product = (15 - 4) × 4 = 11 × 4 = 44 ✓ (tied)
- Cut above node(5): product = (15 - 5) × 5 = 10 × 5 = 50 ✓ (new max)

**Answer:** 50 % (10⁹ + 7) = 50

---

## Approach 2: Single DFS Postorder (Alternative Implementation)

```java
private static long getSum(TreeNode root) {
    if (root == null) return 0;
    return root.val + getSum(root.left) + getSum(root.right);
}

private static long getMaxProduct(TreeNode root) {
    if (root == null) return 0;
    
    long left = getMaxProduct(root.left);
    long right = getMaxProduct(root.right);
    
    // Current subtree sum
    long t1 = left + right + root.val;
    
    // Try cutting here
    long temp = (sum - t1) * t1;
    max = Math.max(max, temp);
    
    return t1;
}

public static int maxProduct(TreeNode root) {
    max = 0;
    sum = getSum(root);
    getMaxProduct(root);
    return (int)(max % MODULO);
}
```

**Differences from Solution 1:**

- Uses two separate DFS functions: `getSum()` and `getMaxProduct()`.
- Computes subtree sums on-the-fly in `getMaxProduct()` without modifying nodes.
- Cleaner separation of concerns.

**Why is Solution 2 faster?** (Your note in the code)

Despite appearing the same, Solution 2 is typically faster because:

1. **Function locality:** `getMaxProduct()` and `getSum()` calls are inline-friendly; compiler can optimize better.
2. **Stack efficiency:** Less queue allocation (BFS queue in Solution 1).
3. **Cache locality:** Recursive calls use the call stack efficiently; BFS queue adds indirection.
4. **No null checks in loop:** The BFS loop in Solution 1 does `if (node == null) continue`, adding overhead.
5. **Static variables:** `static` fields are slightly faster than instance variables in some JVMs.

**Trade-off:** Solution 2 is faster but requires two passes over the tree. Solution 1 is clearer in intent but less optimized.

---

## Correctness

**Why this works:**

1. Every possible cut corresponds to removing the edge above some node (except the root, which would leave one tree empty).
2. For each cut, one component is a subtree, the other is the rest of the tree.
3. By checking all nodes, we ensure we try all possible cuts.
4. The modulo operation is applied at the end (not during computation) to avoid overflow issues.

**Edge case handling:**

- Single-node tree: The product is always 0 (cut between root and null).
- Skewed tree: Still works; we check every node.

---

## Complexity

| Metric | Complexity | Notes |
|--------|-----------|-------|
| **Time** | O(n) | Two passes: DFS to compute sums (O(n)) + BFS/DFS to check all nodes (O(n)). |
| **Space** | O(h) | Recursion depth or queue size proportional to tree height. O(h) for balanced trees, O(n) for skewed. |

Both solutions have identical asymptotic complexity; the difference is in constant factors and cache behavior.

---

## Edge Cases

1. **Single node:** Product = 0 (can't make a meaningful split).
2. **All positive values:** Product is maximized when trees are balanced.
3. **Negative values:** The product calculation still works; modulo handles negative intermediate results.
4. **Large sums:** Modulo 10⁹ + 7 is applied at the end to prevent overflow.

---

## Takeaways

✅ **Tree DP pattern:** Compute aggregate values (subtree sums) in a single pass.

✅ **Post-order traversal:** Natural for problems where child values affect parent decisions.

✅ **Enumerate all candidates:** Try cutting at every node to find the optimal cut.

✅ **In-place modification:** Reusing `node.val` saves space (though Solution 2's approach is cleaner).

✅ **Modulo arithmetic:** Apply modulo only at the end to avoid precision loss during computation.

---

## Alternative Approaches

### 1. Store subtree sums separately (More Memory)

Create a map/array to store subtree sums without modifying the original tree.

**Complexity:** O(n) time, O(n) space.

**Trade-off:** Cleaner (doesn't modify tree), but uses extra space.

---

### 2. Single DFS with Inline Computation (Most Elegant)

Combine sum calculation and product calculation in one traversal.

**Complexity:** O(n) time, O(h) space.

**Example:**

```java
private long dfs(TreeNode node, long total) {
    if (node == null) return 0;
    
    long left = dfs(node.left, total);
    long right = dfs(node.right, total);
    
    long subtreeSum = node.val + left + right;
    ans = Math.max(ans, (total - subtreeSum) * subtreeSum);
    
    return subtreeSum;
}
```

This eliminates the need for a second pass.

---

## Why Your Solution is Good

- ✅ Straightforward: Compute sums, then try all cuts.
- ✅ Correct: Handles all cases and edge cases.
- ✅ Efficient: O(n) time is optimal (must visit every node).

**Minor note:** Solution 2's approach (separate sum computation) is slightly faster in practice and is more maintainable than in-place modification.
