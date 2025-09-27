# How_Why.md – Clone Graph (133)

## ❌ Brute Force Approach: Naïve Deep Copy

### Idea

* Traverse each node of the graph.
* For each node:

  * Create a new node with the same value.
  * Recursively clone its neighbors.

### Example Walkthrough

Graph:

```
1 -- 2
|    |
4 -- 3
```

* Start at node `1`.
* Create new `1'`, recursively clone neighbors:

  * Clone `2` → clone its neighbors...
  * Clone `3` → cycle leads back to `1`, causing infinite recursion.

👉 Without a **visited map**, cycles cause **stack overflow**.

### Complexity

* **Time**: Infinite recursion in cyclic graphs.
* **Space**: N/A (doesn’t work).

---

## ✅ Working Approach: DFS with HashMap (Your Code)

### Idea

* Use a `HashMap` to **map original nodes → cloned nodes**.
* If a node has already been cloned, return it (avoids infinite loops).
* Otherwise:

  1. Create a clone of the current node.
  2. Store it in the map.
  3. Recursively clone neighbors and attach them.

### Code (version using `HashMap<Node, Node>`)

```java
class Solution {
    HashMap<Node, Node> map = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node newNode = new Node(node.val);
        map.put(node, newNode);

        for (Node neigh : node.neighbors) {
            newNode.neighbors.add(cloneGraph(neigh));
        }

        return newNode;
    }
}
```

### Example Walkthrough

Graph:

```
1 -- 2
|    |
4 -- 3
```

1. Visit `1` → clone `1'`.
2. Visit `2` → clone `2'`.
3. Visit `3` → clone `3'`.
4. Visit `4` → clone `4'`.
5. When cycles appear (like `3 → 1`), return the already cloned node.

Final cloned graph is identical but disconnected from original.

### Complexity

* **Time**: `O(V + E)` → each node and edge processed once.
* **Space**: `O(V)` for HashMap + recursion stack.

---

## 🚀 BFS Alternative

You can also solve with **BFS** (iterative):

```java
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        
        HashMap<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        Node clone = new Node(node.val);
        map.put(node, clone);
        queue.add(node);
        
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (Node neigh : curr.neighbors) {
                if (!map.containsKey(neigh)) {
                    map.put(neigh, new Node(neigh.val));
                    queue.add(neigh);
                }
                map.get(curr).neighbors.add(map.get(neigh));
            }
        }
        return clone;
    }
}
```

---

## ⚖️ Comparison of Your Two Versions

1. **`HashMap<Integer, Node>` (first code)**

   * Works only if all node values are unique (which LeetCode guarantees).
   * Risky for custom graph inputs (if duplicate values exist).

2. **`HashMap<Node, Node>` (second code)** ✅

   * More robust.
   * Always safe because it directly maps object references.
   * Preferred in real-world scenarios.

---

✅ **Final Recommendation**: Use **DFS or BFS with `HashMap<Node, Node>`** for a safe, optimal solution.

---
