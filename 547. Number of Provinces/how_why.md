# 547. Number of Provinces - Solution Explanation

## Problem Understanding

Given an `n × n` adjacency matrix `isConnected` where:

- `isConnected[i][j] = 1` means city `i` and city `j` are directly connected
- `isConnected[i][j] = 0` means they are not directly connected
- Cities connected **directly or indirectly** form a province

**Goal:** Count the total number of provinces (connected components).

### Key Insight

This is a **graph connected components** problem:

- Each city is a node
- Each `1` in the matrix is an edge
- A province = a connected component in the graph

---

## Approach 1: Depth-First Search (DFS) - Optimal ✅

### Core Idea

1. **Track visited cities** to avoid counting the same province twice
2. **For each unvisited city**, start a DFS to explore its entire province
3. **Increment counter** each time we start a new DFS (= found a new province)

### Code Implementation

```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    boolean[] visited = new boolean[n];
    int count = 0;
    
    // Try starting DFS from each city
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(isConnected, visited, i);  // Explore entire province
            count++;                        // Found a new province!
        }
    }
    
    return count;
}

private void dfs(int[][] isConnected, boolean[] visited, int city) {
    visited[city] = true;  // Mark current city as visited
    
    // Explore all neighbors
    for (int j = 0; j < isConnected.length; j++) {
        if (isConnected[city][j] == 1 && !visited[j]) {
            dfs(isConnected, visited, j);  // Recursively visit neighbor
        }
    }
}
```

### How DFS Works Step-by-Step

**Think of DFS as "exploring a city and all cities you can reach from it":**

1. **Start at a city** (if unvisited)
2. **Mark it visited** so we don't count it again
3. **Check all its neighbors** (look at row `city` in the matrix)
4. **For each connected unvisited neighbor**, recursively explore it
5. **When DFS returns**, we've visited an entire province

### Complexity Analysis

- **Time:** O(n²)
  - Outer loop: O(n)
  - Each DFS call processes each city once: O(n)
  - Inside DFS, we check n neighbors: O(n)
  - Total: O(n) cities × O(n) checks = O(n²)
  
- **Space:** O(n)
  - `visited` array: O(n)
  - Recursion stack: O(n) in worst case (linear graph)

---

## Complete Visual Walkthrough

### Example 1: Two Provinces

**Input:**

```c
isConnected = [
  [1, 1, 0],
  [1, 1, 0],
  [0, 0, 1]
]
```

**Visual Graph:**

```c
    0 ←→ 1        2
    (Province 1)  (Province 2)
```

**Step-by-Step Execution:**

```c
Initial State:
visited = [false, false, false]
count = 0

─────────────────────────────────────────

Iteration i=0:
✓ City 0 not visited → Start DFS from city 0
  
  DFS(city=0):
    visited[0] = true
    visited = [true, false, false]
    
    Check neighbors:
    - j=0: isConnected[0][0]=1, but visited[0]=true ✗ skip
    - j=1: isConnected[0][1]=1, visited[1]=false ✓
      → Call DFS(city=1)
      
        DFS(city=1):
          visited[1] = true
          visited = [true, true, false]
          
          Check neighbors:
          - j=0: isConnected[1][0]=1, but visited[0]=true ✗ skip
          - j=1: isConnected[1][1]=1, but visited[1]=true ✗ skip
          - j=2: isConnected[1][2]=0 ✗ not connected
          
          Return (DFS for city 1 complete)
    
    - j=2: isConnected[0][2]=0 ✗ not connected
    
    Return (DFS for city 0 complete)
  
  count++ → count = 1
  Province 1 complete: {0, 1}

─────────────────────────────────────────

Iteration i=1:
✗ City 1 already visited → Skip

─────────────────────────────────────────

Iteration i=2:
✓ City 2 not visited → Start DFS from city 2
  
  DFS(city=2):
    visited[2] = true
    visited = [true, true, true]
    
    Check neighbors:
    - j=0: isConnected[2][0]=0 ✗ not connected
    - j=1: isConnected[2][1]=0 ✗ not connected
    - j=2: isConnected[2][2]=1, but visited[2]=true ✗ skip
    
    Return (DFS for city 2 complete)
  
  count++ → count = 2
  Province 2 complete: {2}

─────────────────────────────────────────

Final Answer: 2 provinces
```

### Example 2: One Large Province

**Input:**

```c
isConnected = [
  [1, 0, 0, 1],
  [0, 1, 1, 0],
  [0, 1, 1, 1],
  [1, 0, 1, 1]
]
```

**Visual Graph:**

```c
    0 ←───────→ 3
                ↑
                │
    1 ←→ 2 ←────┘
    
All cities connected = 1 province
```

**Execution:**

```c
i=0: Start DFS from city 0
  DFS visits: 0 → 3 → 2 → 1
  All cities now visited!
  count = 1

i=1,2,3: All already visited → Skip

Final Answer: 1 province
```

---

## Approach 2: Union-Find (Disjoint Set Union)

### Core Idea*

Instead of exploring with DFS, we **merge cities into sets** as we find connections.

### Implementation

```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        
        // Union all connected cities
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        
        return uf.getCount();
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int count;  // Number of disjoint sets
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;  // Initially, each city is its own province
        
        for (int i = 0; i < n; i++) {
            parent[i] = i;  // Each city is its own parent
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX != rootY) {
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            count--;  // Merged two provinces
        }
    }
    
    public int getCount() {
        return count;
    }
}
```

### Union-Find Visual Example

**Input:** `[[1,1,0], [1,1,0], [0,0,1]]`

```c
Initial: Each city is its own province
parent = [0, 1, 2]
count = 3

Process isConnected[0][1] = 1:
  Union(0, 1)
  parent = [0, 0, 2]
  count = 2
  
  Cities 0 and 1 now in same province

Process isConnected[0][2] = 0: Skip (not connected)
Process isConnected[1][2] = 0: Skip (not connected)

Final: count = 2 provinces
Province 1: {0, 1}
Province 2: {2}
```

### Complexity

- **Time:** O(n² α(n)) ≈ O(n²) where α(n) is inverse Ackermann (practically constant)
- **Space:** O(n) for parent and rank arrays

---

## Approach 3: Breadth-First Search (BFS)

### Implementation*

```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    boolean[] visited = new boolean[n];
    int count = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            bfs(isConnected, visited, i);
            count++;
        }
    }
    
    return count;
}

private void bfs(int[][] isConnected, boolean[] visited, int start) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    visited[start] = true;
    
    while (!queue.isEmpty()) {
        int city = queue.poll();
        
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[city][j] == 1 && !visited[j]) {
                queue.offer(j);
                visited[j] = true;
            }
        }
    }
}
```

**BFS vs DFS:** Same complexity, but BFS uses a queue and explores level-by-level instead of depth-first.

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
| ---------- | ------ | ------- | ------ | ------ |
| **DFS** | O(n²) | O(n) | Simple, intuitive | Recursion stack |
| **BFS** | O(n²) | O(n) | Iterative, no stack overflow | Needs queue |
| **Union-Find** | O(n²) | O(n) | Elegant, reusable structure | More code |

**Best Choice:** DFS or Union-Find (both excellent, DFS is simpler to code)

---

## Key Takeaways

1. **Think Graph:** Adjacency matrix = graph, provinces = connected components
2. **DFS Pattern:** Visit once per component, mark everything reachable
3. **Count Smart:** Increment counter only when starting new DFS (= new province found)
4. **Union-Find Alternative:** Great when you need to dynamically track components

This pattern appears in many problems: friend circles, network connectivity, island counting, etc.!
