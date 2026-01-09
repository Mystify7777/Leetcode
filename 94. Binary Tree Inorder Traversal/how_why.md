# Recap

Given the `root` of a binary tree, return the inorder traversal of its nodes' values.

Inorder traversal visits nodes in the order: **Left → Root → Right**.

## Intuition

Inorder traversal naturally follows a recursive pattern: process left subtree, then current node, then right subtree. However, we can also implement it iteratively using a stack to simulate the recursion call stack.

The iterative approach explicitly maintains a stack of nodes. We traverse as far left as possible, pushing nodes onto the stack. When we can't go left anymore, we pop a node, process it (add its value to result), and then move to its right child.

## Approach

**Iterative Solution (Using Stack):**

1. Initialize an empty result list and a stack.
2. Start with `cur = root`.
3. While `cur` is not null OR stack is not empty:
   - **Phase 1 (Go Left):** While `cur` is not null, push `cur` onto stack and move to `cur.left`.
   - **Phase 2 (Process & Go Right):** Pop from stack, add the node's value to result, and move to its right child (`cur = popped.right`).
4. Return the result list.

**Key insight:** The inner while loop goes as far left as possible, stacking nodes. The outer loop processes each stacked node and explores its right subtree.

**Why this works:** The stack holds nodes whose left subtrees haven't been fully explored yet. When we pop, we've finished the left subtree, so we process the node and then explore its right subtree.

## Code (Java)

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root;
        
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.add(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            list.add(cur.val);
            cur = cur.right;
        }
        
        return list;
    }
}
```

## Correctness

- **Left subtree first:** The inner while loop ensures we go all the way left before processing any node, guaranteeing left subtrees are processed first.
  
- **Root processing:** After exhausting the left path, we pop a node, which means its left subtree is complete. We process it (add to result).

- **Right subtree last:** After processing a node, we move to its right child. If it exists, we repeat the process (go left, process, go right).

- **Stack behavior:** The stack mimics the recursion call stack. Nodes are pushed when we "call" into their left subtree and popped when we "return" to process them.

- **Termination:** The loop continues while there are nodes to process (either current node or nodes in stack). Eventually, all nodes are processed and the stack becomes empty.

## Complexity

- **Time:** `O(n)` — each node is pushed and popped from stack exactly once, where `n` is the number of nodes.
- **Space:** `O(h)` — stack space, where `h` is the height of the tree. In worst case (skewed tree), `h = n`. In balanced tree, `h = log(n)`.

## Edge Cases

- **Empty tree (`root = null`):** Return empty list. The outer while condition is false immediately.
- **Single node:** Push and pop the node, add its value, return `[val]`.
- **Left-skewed tree:** Stack grows to depth `n`, processes nodes bottom-up.
- **Right-skewed tree:** Each node is pushed, immediately popped (no left child), and we move to right child.
- **Balanced tree:** Stack size stays around `log(n)`.

## Takeaways

- **Stack simulates recursion:** Iterative tree traversal can replace recursion by explicitly managing a stack.
- **Inorder pattern:** Left → Root → Right. This is useful for BSTs where inorder gives sorted order.
- **Two-phase loop:** The nested while loops separate "going left" from "processing and going right."
- **Space-time tradeoff:** Iterative uses explicit stack space; recursive uses implicit call stack. Both are `O(h)` space.
- This pattern extends to preorder and postorder with slight modifications.

## Alternative: Recursive Solution

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    
    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        inorder(node.left, result);   // Left
        result.add(node.val);          // Root
        inorder(node.right, result);   // Right
    }
}
```

**Pros:** More concise and intuitive.
**Cons:** Uses implicit call stack (same `O(h)` space complexity).

## Alternative: Morris Traversal (O(1) Space)

For advanced optimization, Morris Traversal modifies the tree temporarily to achieve `O(1)` space complexity by creating threaded binary tree links, avoiding stack/recursion entirely.

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode cur = root;
        
        while (cur != null) {
            if (cur.left == null) {
                result.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode pred = cur.left;
                while (pred.right != null && pred.right != cur) {
                    pred = pred.right;
                }
                
                if (pred.right == null) {
                    pred.right = cur;  // Create thread
                    cur = cur.left;
                } else {
                    pred.right = null;  // Remove thread
                    result.add(cur.val);
                    cur = cur.right;
                }
            }
        }
        
        return result;
    }
}
```

**Pros:** `O(1)` space complexity.
**Cons:** More complex logic, temporarily modifies tree structure (though it restores it).
