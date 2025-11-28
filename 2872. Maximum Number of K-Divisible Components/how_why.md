# 2872. Maximum Number of K-Divisible Components — how/why

## Recap

You are given a tree with `n` nodes (0-indexed), represented by `edges`. Each node `i` has a value `values[i]`. You want to split the tree into the maximum number of connected components such that the sum of values in each component is divisible by `k`. Return the maximum number of components possible.

## Intuition

In a tree, removing an edge splits it into exactly two components. The key insight: if a subtree's sum is divisible by `k`, we can "cut" it off from the rest of the tree to form a separate component. We process the tree from leaves upward (either via BFS from leaves or DFS post-order), checking if each subtree can form a valid component. If a node's subtree sum is divisible by `k`, we count it as a component and don't propagate its value upward (effectively cutting that edge).

## Approach

### BFS (Leaf-Removal) Approach

1. **Build adjacency list** from edges.
2. **Find all leaf nodes** (degree 1) and add to queue.
3. **Process leaves in BFS order**:
   - For each leaf, check if `values[leaf] % k == 0`.
   - If yes: increment component count (this leaf forms its own component).
   - If no: add its value to its only neighbor (propagate upward).
   - Remove the edge between leaf and neighbor.
   - If neighbor becomes a new leaf, add to queue.
4. Continue until all nodes processed.

### DFS (Post-Order) Approach

1. **Build adjacency list** from edges.
2. **DFS from root** (any node, e.g., node 0):
   - Recursively process all children first (post-order).
   - Accumulate child values into current node: `values[node] += values[child] % k`.
   - After processing all children, check if `values[node] % k == 0`.
   - If yes: increment component count (this subtree forms a component).
3. Return total count.

Both approaches track which subtrees can be "split off" based on divisibility.

## Code (Java)

```java
class Solution {
    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        if (n < 2) return 1;

        int componentCount = 0;
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        // Build graph
        for (int[] edge : edges) {
            int node1 = edge[0], node2 = edge[1];
            graph.computeIfAbsent(node1, key -> new HashSet<>()).add(node2);
            graph.computeIfAbsent(node2, key -> new HashSet<>()).add(node1);
        }

        // Convert to long to prevent overflow
        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = values[i];
        }

        // Initialize queue with leaf nodes
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
            if (entry.getValue().size() == 1) {
                queue.add(entry.getKey());
            }
        }

        // Process nodes in BFS order
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            // Find neighbor
            int neighborNode = -1;
            if (graph.get(currentNode) != null && !graph.get(currentNode).isEmpty()) {
                neighborNode = graph.get(currentNode).iterator().next();
            }

            if (neighborNode >= 0) {
                // Remove edge
                graph.get(neighborNode).remove(currentNode);
                graph.get(currentNode).remove(neighborNode);
            }

            // Check divisibility
            if (longValues[currentNode] % k == 0) {
                componentCount++;
            } else if (neighborNode >= 0) {
                // Propagate value upward
                longValues[neighborNode] += longValues[currentNode];
            }

            // If neighbor becomes a leaf, add to queue
            if (neighborNode >= 0 && 
                graph.get(neighborNode) != null && 
                graph.get(neighborNode).size() == 1) {
                queue.add(neighborNode);
            }
        }

        return componentCount;
    }
}
```

## Correctness

- **Tree structure:** In a tree with `n` nodes, there are exactly `n-1` edges. We can split it by removing edges, creating disjoint components.

- **Leaf processing:** Starting from leaves and working inward ensures we process subtrees before their ancestors. A leaf's value either forms its own component (if divisible) or gets merged with its parent.

- **Greedy optimality:** Greedily cutting off subtrees as soon as their sum is divisible by `k` maximizes components. If we don't cut a divisible subtree, merging it with the parent can only decrease or maintain the component count (never increase).

- **Modular arithmetic:** Since we only care about divisibility, we can work with `values[node] % k`. Propagating remainders upward is equivalent to summing full values.

- **Base case:** Single node tree returns 1 (the whole tree is one component).

## Complexity

- **Time:** `O(n)` — each node and edge processed once (BFS visits each node; DFS visits each node and edge once).
- **Space:** `O(n)` — adjacency list and queue/recursion stack.

## Edge Cases

- Single node (`n = 1`): Return 1.
- All values divisible by `k`: Maximum components = `n` (each node is its own component).
- No valid splits: Return 1 (entire tree is one component).
- Linear tree (path graph): Process from one end to the other.
- Star graph (one center, all others leaves): Process all leaves first, then center.
- Large values: Use `long` to prevent overflow when summing values.
- Negative values: Modulo operation handles negatives; ensure consistent modulo behavior.

## Takeaways

- **Tree splitting via edge removal:** Cutting edges based on subtree properties (here, divisibility) is a common pattern.
- **Leaf-to-root processing:** BFS from leaves or DFS post-order ensures children are processed before parents.
- **Greedy is optimal:** For tree partitioning with independent subtree constraints, greedy local decisions yield global optimum.
- **Modular arithmetic:** Track remainders instead of full sums to simplify computation and prevent overflow.
- This pattern generalizes to other tree partitioning problems (e.g., minimize max component size, balance components).

## Alternative (DFS Post-Order)

```java
class Solution {
    int componentCount = 0;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        dfs(adj, values, k, 0, -1);
        return componentCount;
    }

    private long dfs(List<Integer>[] adj, int[] values, int k, int node, int parent) {
        long sum = values[node];
        
        for (int child : adj[node]) {
            if (child != parent) {
                sum += dfs(adj, values, k, child, node);
            }
        }

        if (sum % k == 0) {
            componentCount++;
            return 0; // Don't propagate this subtree's sum
        }
        return sum;
    }
}
```

**Trade-off:** DFS is more concise and intuitive (recursive post-order traversal). BFS is iterative and may be preferred for very deep trees (avoids stack overflow). Both have the same time/space complexity.
