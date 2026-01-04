# How\_Why.md: Populating Next Right Pointers in Each Node

## Problem

You are given a **perfect binary tree** where all leaves are on the same level, and every parent has two children. Populate each `next` pointer to point to its next right node. If there is no next right node, the `next` pointer should be set to `NULL`.

**Constraint:** You may only use constant extra space.

**Example:**

```s
Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]

     1 -> NULL
   /   \
  2  -> 3 -> NULL
 / \   / \
4->5->6->7 -> NULL
```

---

## Brute-force Approach

### Idea

* Use **BFS (level-order traversal)** with a queue.
* At each level, connect all nodes in the queue from left to right.
* Enqueue child nodes for the next level.

### Code

```java
public Node connectBF(Node root) {
    if (root == null) return null;
    
    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        Node prev = null;
        
        for (int i = 0; i < size; i++) {
            Node curr = queue.poll();
            
            if (prev != null) {
                prev.next = curr;
            }
            prev = curr;
            
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
    }
    
    return root;
}
```

### Limitation

* **Space complexity:** O(n) due to queue storage.
* Problem asks for constant O(1) space solution.

---

## Optimized Approach (DFS with Recursion - O(1) Space)

### Idea*

1. Process the tree recursively using DFS.
2. **Key observation:** When we're at a parent node:
   - Connect the parent's **left child's next pointer** to the parent's **right child**.
   - Connect the parent's **right child's next pointer** to the **left child of the parent's next node**.
3. Use the **already-established `next` pointers** of parent nodes to find siblings at the next level.
4. Recurse on left and right subtrees.

### Why It Works

In a perfect binary tree, once we've connected pointers at the parent level, we can use those connections to find the correct nodes for child level connections.

**Connection Rules:**

- `node.left.next = node.right` (siblings under same parent)
- `node.right.next = node.next.left` (connect to next parent's left child)

### Code*

```java
public Node connect(Node root) {
    if (root == null) return null;
    
    if (root.left != null) root.left.next = root.right;
    if (root.right != null && root.next != null) root.right.next = root.next.left;
    
    connect(root.left);
    connect(root.right);
    
    return root;
}
```

### Example Walkthrough

**Initial Tree:**

```s
       1
     /   \
    2     3
   / \   / \
  4   5 6   7
```

**Step 1:** At node `1`:

- `1.left.next = 1.right` → `2.next = 3`
- `1.right.next = 1.next.left` → `3.next = NULL` (since `1.next == NULL`)

```s
       1 -> NULL
     /   \
    2 --> 3 -> NULL
   / \   / \
  4   5 6   7
```

**Step 2:** Recurse on `1.left` (node `2`):

- `2.left.next = 2.right` → `4.next = 5`
- `2.right.next = 2.next.left` → `5.next = 3.left` → `5.next = 6`

```s
       1 -> NULL
     /   \
    2 --> 3 -> NULL
   / \   / \
  4->5->6   7
```

**Step 3:** Recurse on `2.left` (node `4`): No children, return.

**Step 4:** Recurse on `2.right` (node `5`): No children, return.

**Step 5:** Recurse on `1.right` (node `3`):

- `3.left.next = 3.right` → `6.next = 7`
- `3.right.next = 3.next.left` → `7.next = NULL`

```s
       1 -> NULL
     /   \
    2 --> 3 -> NULL
   / \   / \
  4->5->6->7 -> NULL
```

### Time and Space Complexity

* **Time complexity:** O(n) - visit each node once
* **Space complexity:** O(h) - recursion call stack where h = log(n) for perfect binary tree
* **Achieves O(1) auxiliary space** (excluding recursion stack)

---

## Optimal Approach (Iterative BFS using Next Pointers - O(1) Space)

### Idea**

* Use already-connected `next` pointers to traverse and connect the next level.
* No queue or recursion needed.
* Start with the root as the first level.

### Code**

```java
public Node connectOptimal(Node root) {
    Node level = root;
    
    while (level != null) {
        Node curr = level;
        
        while (curr != null) {
            if (curr.left != null) curr.left.next = curr.right;
            if (curr.right != null && curr.next != null) curr.right.next = curr.next.left;
            curr = curr.next;
        }
        
        level = level.left;
    }
    
    return root;
}
```

### How It Works

1. **Outer loop:** Process each level of the tree.
2. **Inner loop:** Traverse the current level using `next` pointers (already established).
3. For each node in the current level, establish connections for its children.
4. Move to the next level: `level = level.left` (leftmost node of next level).

### Complexity

* **Time complexity:** O(n)
* **Space complexity:** O(1) - truly constant space!

---

## Key Insights

1. **Use `next` pointers:** Leverage already-connected pointers to find siblings at the next level.
2. **Connection pattern:**
   - Left child → Right sibling: `node.left.next = node.right`
   - Right child → Next parent's left child: `node.right.next = node.next.left`
3. **Perfect binary tree advantage:** Every non-leaf parent has exactly two children, simplifying connections.
4. **Trade-off:** Recursive solution is clean (O(log n) space), iterative solution is optimal (O(1) space).
