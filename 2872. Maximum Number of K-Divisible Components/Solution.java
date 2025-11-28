// 2872. Maximum Number of K-Divisible Components
// https://leetcode.com/problems/maximum-number-of-k-divisible-components/
class Solution {

    public int maxKDivisibleComponents(
        int n,
        int[][] edges,
        int[] values,
        int k
    ) {
        // Base case: if there are less than 2 nodes, return 1
        if (n < 2) return 1;

        int componentCount = 0;
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        // Step 1: Build the graph
        for (int[] edge : edges) {
            int node1 = edge[0], node2 = edge[1];
            graph.computeIfAbsent(node1, key -> new HashSet<>()).add(node2);
            graph.computeIfAbsent(node2, key -> new HashSet<>()).add(node1);
        }

        // Convert values to long to prevent overflow
        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = values[i];
        }

        // Step 2: Initialize the BFS queue with leaf nodes (nodes with only one connection)
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
            if (entry.getValue().size() == 1) {
                queue.add(entry.getKey());
            }
        }

        // Step 3: Process nodes in BFS order
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            // Find the neighbor node
            int neighborNode = -1;
            if (
                graph.get(currentNode) != null &&
                !graph.get(currentNode).isEmpty()
            ) {
                neighborNode = graph.get(currentNode).iterator().next();
            }

            if (neighborNode >= 0) {
                // Remove the edge between current and neighbor
                graph.get(neighborNode).remove(currentNode);
                graph.get(currentNode).remove(neighborNode);
            }

            // Check divisibility of the current node's value
            if (longValues[currentNode] % k == 0) {
                componentCount++;
            } else if (neighborNode >= 0) {
                // Add current node's value to the neighbor
                longValues[neighborNode] += longValues[currentNode];
            }

            // If the neighbor becomes a leaf node, add it to the queue
            if (
                neighborNode >= 0 &&
                graph.get(neighborNode) != null &&
                graph.get(neighborNode).size() == 1
            ) {
                queue.add(neighborNode);
            }
        }

        return componentCount;
    }
}

/**
class Solution {

    int ans;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] adj = new ArrayList[n];

        for (int i=0 ; i<n ; i++) {
            adj[i] = new ArrayList<>();
        }
        for (var e: edges) {
            int n0 = e[0], n1 = e[1];
            adj[n0].add(n1);
            adj[n1].add(n0);
        }

        ans = 0;

        dfs(values, k, adj, 0, -1);

        return ans;
    }

    private void dfs(int[] values, int k,
        List<Integer>[] adj, int node, int parent) {
        for (int child: adj[node]) {
            if (child != parent) {
                dfs(values, k, adj, child, node);
                // keep digging

                values[node] += values[child];
                values[node] %= k;
            }
        }

        values[node] %= k;
        if (values[node] == 0) {
            ans++;
        }
    }
}
 */