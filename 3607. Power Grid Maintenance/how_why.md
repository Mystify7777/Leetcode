# 3607. Power Grid Maintenance — how and why

## Problem recap

You have a power grid with `c` stations numbered from 1 to c, connected by bidirectional connections. Some connections form connected components. You need to process two types of queries:

- **Type 1 (query station x):** Find the smallest-numbered station in x's connected component that is currently online. If station x itself is online, return x. If all stations in x's component are offline, return -1.
- **Type 2 (take station x offline):** Mark station x as offline for all future queries.

Return an array containing the results of all Type 1 queries.

## Core intuition

This is a **union-find (disjoint set union)** problem combined with **online/offline state tracking**:

1. Use DSU to group stations into connected components
2. For each component, maintain a min-heap of station IDs
3. When querying:
   - If the station is online, return it directly
   - Otherwise, find the smallest online station in its component using the heap
   - Lazily remove offline stations from the heap top

The key insight: we don't need to maintain perfect heaps. We can lazily clean offline stations when we peek, making updates O(1) and queries amortized efficient.

## Approach 1 — DSU with lazy heap cleanup (your solution)

### Data structures

- **DSU (Disjoint Set Union):** Groups stations into connected components with path compression
- **online array:** Tracks which stations are currently online
- **componentHeap map:** For each component root, maintains a min-heap of all station IDs in that component

### Algorithm

**Initialization:**

1. Build DSU from connections
2. Mark all stations as online
3. Group stations by component root, create a min-heap for each component

**Query processing:**

For Type 1 queries:

- If station x is online → return x immediately
- If station x is offline:
  - Find x's component root
  - Get the heap for that component
  - Lazily remove offline stations from heap top
  - Return the first online station found (or -1 if heap becomes empty)

For Type 2 queries:

- Mark station x as offline (heap cleanup happens lazily during Type 1 queries)

### Implementation (matches `Solution.java`)

```java
import java.util.*;

class DSU {
    int[] parent;

    public DSU(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++)
            parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    public boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py)
            return false;
        parent[py] = px;
        return true;
    }
}

class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        DSU dsu = new DSU(c);
        boolean[] online = new boolean[c + 1];
        Arrays.fill(online, true);

        // Build connected components
        for (int[] conn : connections)
            dsu.union(conn[0], conn[1]);

        // Create min-heap for each component
        Map<Integer, PriorityQueue<Integer>> componentHeap = new HashMap<>();
        for (int station = 1; station <= c; station++) {
            int root = dsu.find(station);
            componentHeap.putIfAbsent(root, new PriorityQueue<>());
            componentHeap.get(root).offer(station);
        }

        List<Integer> result = new ArrayList<>();

        for (int[] query : queries) {
            int type = query[0], x = query[1];

            if (type == 2) {
                // Take station x offline
                online[x] = false;
            } else {
                // Query station x
                if (online[x]) {
                    result.add(x);
                } else {
                    int root = dsu.find(x);
                    PriorityQueue<Integer> heap = componentHeap.get(root);

                    // Lazy cleanup: remove offline stations from heap top
                    while (heap != null && !heap.isEmpty() && !online[heap.peek()]) {
                        heap.poll();
                    }

                    result.add((heap == null || heap.isEmpty()) ? -1 : heap.peek());
                }
            }
        }

        // Convert list to array
        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }
}
```

## Approach 2 — optimized DSU with sorted stations (alternate)

The alternate approach uses a more space-efficient method:

- Stores stations sorted by component
- Uses indices to track ranges for each component
- Advances start pointer when encountering offline stations

```java
class Solution {
    public int[] processQueries(int n, int[][] connections, int[][] queries) {
        n++;
        
        // Build DSU using array-based labels
        final int[] labels = new int[n];
        for (int i = 1; i < n; i++) {
            labels[i] = i;
        }
        
        // Union stations
        for (int[] conn : connections) {
            labels[getLabel(labels, conn[0])] = labels[getLabel(labels, conn[1])];
        }
        
        // Count stations per component
        final int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            counts[getLabel(labels, i)]++;
        }
        
        // Convert counts to start indices
        updateCounts(counts);
        final int[] starts = counts.clone();
        
        // Sort stations by component
        final int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[counts[labels[i]]++] = i;
        }
        
        // Process queries
        final int[] result = new int[queries.length];
        int len = 0;
        final boolean[] offline = new boolean[n];
        
        for (var q : queries) {
            final int x = q[1];
            if (q[0] == 1) {
                if (offline[x]) {
                    final int label = labels[x];
                    final int end = counts[label];
                    int start = starts[label];
                    
                    // Skip offline stations
                    while (start < end && offline[sorted[start]]) {
                        start++;
                    }
                    starts[label] = start;
                    result[len++] = start == end ? -1 : sorted[start];
                } else {
                    result[len++] = x;
                }
            } else {
                offline[x] = true;
            }
        }
        return Arrays.copyOf(result, len);
    }

    private static int getLabel(final int[] labels, int idx) {
        final int current = labels[idx];
        return (current == idx || current < 0) ? idx : 
               (labels[idx] = getLabel(labels, current));
    }

    private static void updateCounts(int[] count) {
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            final int newSum = sum + count[i];
            count[i] = sum;
            sum = newSum;
        }
    }
}
```

## Comparison of approaches

| Aspect | Approach 1 (Heap-based) | Approach 2 (Array-based) |
|--------|-------------------------|--------------------------|
| **Component tracking** | DSU class with parent array | Label array with path compression |
| **Station ordering** | Min-heap per component | Sorted array with range indices |
| **Online/offline** | boolean array | boolean array |
| **Space complexity** | O(c) for heaps | O(c) for sorted array |
| **Query complexity** | O(log c) worst case per query | O(k) where k = offline stations skipped |
| **Implementation** | More intuitive with heaps | More compact, harder to understand |
| **Best when** | General case, readable code | Memory-constrained environments |

Both approaches are efficient with similar performance characteristics.

## Why this works

**DSU correctness:**

Union-find with path compression correctly groups stations into connected components in nearly O(1) amortized time per operation.

**Lazy cleanup optimization:**

Instead of eagerly removing offline stations from heaps when they go offline:

- Mark them as offline in the boolean array (O(1))
- Only clean them from heap during queries when they appear at the top
- This amortizes the cleanup cost across queries

**Smallest station guarantee:**

Min-heaps naturally maintain the smallest element at the top. After removing offline stations, the first online station we see is the smallest online station in the component.

## Complexity

### Approach 1 (your solution)

- **Time:**
  - Initialization: O(c α(c)) for DSU + O(c log c) for heap insertions
  - Per Type 1 query: O(1) if station is online, O(k log c) for k offline stations cleaned
  - Per Type 2 query: O(1)
  - Total: O(c log c + q · k log c) where q = queries, k = avg offline stations cleaned
- **Space:** O(c) for DSU, heaps, and tracking arrays

### Approach 2 (alternate)

- **Time:**
  - Initialization: O(c α(c)) for DSU + O(c log c) for sorting
  - Per query: O(k) for skipping k offline stations
  - Total: O(c log c + q · k)
- **Space:** O(c)

## Example walkthrough

Suppose `c = 5`, `connections = [[1,2], [3,4]]`, `queries = [[1,2], [2,2], [1,2], [1,1]]`.

**Initialization:**

- DSU groups: {1,2} and {3,4} and {5}
- All stations online
- Component heaps:
  - root 1: [1, 2]
  - root 3: [3, 4]
  - root 5: [5]

**Query [1, 2]:** Station 2 is online → return 2

**Query [2, 2]:** Take station 2 offline → `online[2] = false`

**Query [1, 2]:** Station 2 is offline

- Find component root: 1
- Heap for root 1: [1, 2]
- Clean heap: remove 2 (offline), peek 1 (online)
- Return 1

**Query [1, 1]:** Station 1 is online → return 1

Result: [2, 1, 1]

## Edge cases to consider

- Single station in component: return that station or -1 if offline
- All stations in a component offline: return -1
- No connections: each station is its own component
- Query offline station in singleton component: return -1
- Multiple Type 2 queries on same station: no issue, already marked offline

## Takeaways

- Union-find is the natural choice for grouping stations into connected components
- Lazy cleanup is more efficient than eager updates for offline tracking
- Min-heaps provide efficient access to the smallest element with lazy removal
- Path compression in DSU ensures nearly constant-time find operations
- Trading eager updates for lazy cleanup can significantly improve performance
- Array-based approaches can be more cache-friendly than heap-based for small ranges
