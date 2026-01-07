# How_Why.md — Lowest Common Ancestor of a Binary Tree (LeetCode 236)

## Recap

Given a binary tree and two nodes `p` and `q`, find the **Lowest Common Ancestor (LCA)** — the deepest node that has both `p` and `q` as descendants (a node is a descendant of itself).

**Key Constraint:** Both `p` and `q` exist in the tree.

---

## Intuition

The LCA is the first node we encounter while traversing down the tree where we see both `p` and `q` as descendants (or the node itself is one of them).

**Core insight:** If at any node:

- Both `p` and `q` are found in left and right subtrees → this node is the LCA.
- Only one of them is found → the LCA is in that subtree.
- Neither is found → LCA is not in this subtree.

---

## Approach: Postorder DFS (Your Implementation)

### How It Works

Recursively search the tree bottom-up:

1. **Base case:** If the current node is `null`, or if it equals `p` or `q`, return it.
   - This handles:
     - Empty subtrees (return `null`).
     - Finding either target node (return that node).

2. **Recursive case:** Search left and right subtrees.

3. **Decision logic:**
   - If both left and right return non-null → the current node is the LCA (both targets found in different subtrees).
   - If only left is non-null → the LCA is in the left subtree (or left itself).
   - If only right is non-null → the LCA is in the right subtree (or right itself).
   - If both are null → neither target exists in this subtree (shouldn't happen given constraints).

### Example Walkthrough

Tree:

```s
        3
       / \
      5   1
     / \  / \
    6  2 0  8
      / \
     7  4
```

Finding LCA(5, 1):

- At node 3: search left (finds 5) and right (finds 1).
- Both found in different subtrees → **return 3** ✓

Finding LCA(5, 4):

- At node 3: left subtree contains both 5 and 4.
- Right subtree returns null.
- Only left is non-null → **LCA is in left subtree**.
- Continue recursion until we reach node 5 (which is the LCA).

---

## Code Analysis

### Solution 1 (Concise)

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if(root == null || root == p || root == q)  
        return root;
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if(left != null && right != null)   
        return root;
    
    return left != null ? left : right;
}
```

- Direct equality check: `root == p` (identity comparison, O(1)).
- Clean ternary operator for the final return.

### Solution 2 (Defensive Null Checks)

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if(Objects.isNull(root) || p == root || q == root) 
        return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if(!Objects.isNull(left) && !Objects.isNull(right)) 
        return root;

    return !Objects.isNull(left) ? left : right;
}
```

- Uses `Objects.isNull()` for explicit null-checking (idiomatic Java).
- Functionally identical to Solution 1.

---

## Correctness

**Why it works:**

1. **Base case correctness:** If `root == p` or `root == q`, we return immediately. This correctly handles cases where one of the targets is an ancestor of the other.

2. **Recursive correctness:**
   - If both left and right subtrees return non-null nodes, the LCA must be the current node (both targets are in different subtrees).
   - If only one subtree returns non-null, the LCA must be in that subtree (both targets are on the same side).

3. **Termination:** The recursion terminates when reaching null nodes or finding a target.

---

## Complexity

| Metric | Complexity | Notes |
|--------|-----------|-------|
| **Time** | O(n) | Visit every node in the tree in worst case (unbalanced tree where LCA is a deep node). |
| **Space** | O(h) | Recursion call stack depth = tree height. O(log n) for balanced trees, O(n) for skewed trees. |

---

## Edge Cases

1. **One node is ancestor of other:** Return the ancestor node.
   - Example: LCA(3, 5) in the above tree returns 3.

2. **p and q are the same:** Return that node.
   - Example: LCA(5, 5) returns 5.

3. **One node is the root:** Return the root if one of p/q is the root.
   - Example: LCA(3, 5) returns 3.

4. **Deep tree:** Function still works; uses O(h) stack space.

---

## Takeaways

✅ **Postorder DFS** is natural for LCA problems — process children before deciding about parent.

✅ **Identity comparison** (`==`) works here because we're comparing TreeNode references, not values.

✅ **Early termination** when we find either target optimizes the search.

✅ No parent pointers needed; the recursive structure implicitly tracks ancestry.

---

## Alternative Approaches

### 1. Path Storage (Less Elegant)

Store paths from root to both p and q, then find the first divergence point.

**Complexity:** O(n) time, O(h) space for paths.

**Trade-off:** More code, easier to understand for some.

---

### 2. Parent Pointers (If Available)

If each node has a parent pointer, we can move upward from both nodes until they meet.

**Complexity:** O(h) time, O(1) space.

**Trade-off:** Requires additional structure; not applicable here.

---

### 3. Iterative with Stack (If Recursion Causes Issues)

Manually manage the call stack using an explicit stack data structure.

**Complexity:** Same as recursive (O(n) time, O(h) space).

**Trade-off:** More boilerplate code; useful if recursion limit is exceeded.

---

## Why Your Solution is Optimal

For the constraints of this problem (both nodes exist, binary tree), the postorder DFS approach is:

- ✅ **Optimal:** O(n) time is necessary in worst case.
- ✅ **Clean:** Leverages recursion naturally.
- ✅ **No preprocessing:** Works immediately without building auxiliary structures.

The choice between `==` and `Objects.isNull()` is stylistic; both are correct.
