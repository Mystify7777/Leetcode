# How_Why.md – Network Delay Time (LeetCode 743)

## ❌ Brute Force Idea

We want to calculate how long it takes for all nodes in a network to receive a signal sent from a source `k`.

**Naïve approach**:

* From the source, try all possible paths to all nodes.
* Compute the travel time for each path, then take the minimum.

⚠️ This becomes exponential since there are too many possible paths (`O(n!)` in the worst case). Not feasible.

---

## ✅ Better Approach – Bellman-Ford

We can use **Bellman-Ford** since it relaxes edges repeatedly until no improvements can be made:

* Initialize all distances as `∞`, except `k = 0`.
* Repeat `n-1` times: update distances if a shorter path is found.
* Finally, the maximum distance among all nodes is the answer.

**Drawback**: `O(n * E)` time → too slow for large graphs.

---

## 🚀 Optimized Approach – Dijkstra’s Algorithm

The problem is a direct application of **single-source shortest path** with non-negative weights.

### Idea

1. Build a graph (adjacency list) from `times`.
2. Use **Dijkstra’s algorithm** (priority queue) to always expand the shortest known path first.
3. Keep track of distances in an array.
4. After processing all reachable nodes, the answer = maximum distance. If any node is still `∞`, return `-1`.

---

### Example Walkthrough

Input:

```
times = [[2,1,1],[2,3,1],[3,4,1]], n=4, k=2
```

Graph:

```
2 -> (1,1), (3,1)
3 -> (4,1)
```

Steps:

* Start at node `2` (dist[2]=0).
* Relax edges: dist[1]=1, dist[3]=1.
* Pick next smallest: node `1` (done).
* Then node `3` → update dist[4]=2.
* Final distances: `[∞,1,0,1,2]`.
  Max = 2 → answer = 2.

---

### Code (Dijkstra with PQ)

```java
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build graph
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] t : times) {
            graph.putIfAbsent(t[0], new HashMap<>());
            graph.get(t[0]).put(t[1], t[2]);
        }

        // Distance array
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // Min-heap {distance, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);
        pq.offer(new int[]{0, k});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], node = cur[1];
            if (d > dist[node]) continue;

            for (int nei : graph.getOrDefault(node, new HashMap<>()).keySet()) {
                int newDist = d + graph.get(node).get(nei);
                if (newDist < dist[nei]) {
                    dist[nei] = newDist;
                    pq.offer(new int[]{newDist, nei});
                }
            }
        }

        // Find max distance
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            res = Math.max(res, dist[i]);
        }
        return res;
    }
}
```

---

## 📊 Complexity

* **Building graph:** `O(E)`
* **Dijkstra’s traversal:** `O(E log V)`
* **Space:** `O(V + E)`

---

## ✅ Key Takeaways

* The problem is a **shortest path problem** with positive weights → Dijkstra is optimal.
* Bellman-Ford also works but is slower.
* The result is the **max shortest-path distance** from source `k`, or `-1` if any node is unreachable.

---
