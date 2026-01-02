# How_Why.md: Evaluate Division

## Problem

You are given equations in the form of `A / B = k`, where `A` and `B` are strings representing variables, and `k` is a real number (the result of the division).

Given a list of equations and a list of queries, return the answers to each query. If a single answer cannot be determined, return `-1.0`.

**Example:**
```
Input: 
  equations = [["a","b"],["b","c"]], 
  values = [2.0,3.0], 
  queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]

Output: [6.0, 0.5, -1.0, 1.0, -1.0]

Explanation:
  a / b = 2.0, b / c = 3.0
  a / c = a / b * b / c = 2.0 * 3.0 = 6.0
  b / a = 1 / (a / b) = 0.5
  a / e = not defined = -1.0
  a / a = 1.0
  x / x = not defined = -1.0
```

---

## Approach: Graph DFS

### Idea

* **Build a graph:** Each equation `A / B = k` creates edges in both directions
  - Edge from A to B with weight `k` (A / B = k)
  - Edge from B to A with weight `1 / k` (B / A = 1 / k)

* **Query using DFS:** For each query, perform DFS from the dividend to the divisor, multiplying edge weights along the path

* **Handle edge cases:** If variables don't exist or no path exists, return -1.0

### Code

```java
class Solution {
    public void dfs(String node, String dest, HashMap<String, HashMap<String, Double>> gr, 
                    HashSet<String> vis, double[] ans, double temp) {
        if (vis.contains(node)) return;
        
        vis.add(node);
        if (node.equals(dest)) {
            ans[0] = temp;
            return;
        }
        
        for (Map.Entry<String, Double> entry : gr.get(node).entrySet()) {
            String ne = entry.getKey();
            double val = entry.getValue();
            dfs(ne, dest, gr, vis, ans, temp * val);
        }
    }
    
    public HashMap<String, HashMap<String, Double>> buildGraph(List<List<String>> equations, 
                                                                double[] values) {
        HashMap<String, HashMap<String, Double>> gr = new HashMap<>();
        
        for (int i = 0; i < equations.size(); i++) {
            String dividend = equations.get(i).get(0);
            String divisor = equations.get(i).get(1);
            double value = values[i];
            
            // Create nodes if they don't exist
            gr.putIfAbsent(dividend, new HashMap<>());
            gr.putIfAbsent(divisor, new HashMap<>());
            
            // Add edges in both directions
            gr.get(dividend).put(divisor, value);
            gr.get(divisor).put(dividend, 1.0 / value);
        }
        
        return gr;
    }
    
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String, HashMap<String, Double>> gr = buildGraph(equations, values);
        double[] finalAns = new double[queries.size()];
        
        for (int i = 0; i < queries.size(); i++) {
            String dividend = queries.get(i).get(0);
            String divisor = queries.get(i).get(1);
            
            if (!gr.containsKey(dividend) || !gr.containsKey(divisor)) {
                finalAns[i] = -1.0;
            } else {
                HashSet<String> vis = new HashSet<>();
                double[] ans = {-1.0};
                dfs(dividend, divisor, gr, vis, ans, 1.0);
                finalAns[i] = ans[0];
            }
        }
        
        return finalAns;
    }
}
```

### Why This Works

* **Graph Representation:** Each equation creates bidirectional edges with appropriate weights
  - `a / b = 2.0` → Edge: a → b (2.0) and b → a (0.5)

* **DFS Traversal:** Finds a path from dividend to divisor by multiplying edge weights

* **Example:** a/c when a/b=2.0, b/c=3.0
  - Start at 'a' with value 1.0
  - Move to 'b': 1.0 × 2.0 = 2.0
  - Move to 'c': 2.0 × 3.0 = 6.0
  - Result: 6.0

* Time Complexity: **O(N + Q × (V + E))** 
  - N = number of equations, Q = number of queries
  - V, E = vertices, edges in graph
  
* Space Complexity: **O(V + E)** - graph storage

### Why This Approach

* **Intuitive:** Models the problem as a weighted graph
* **Flexible:** Easily handles complex division chains
* **Handles edge cases:** Checks for non-existent variables
* **DFS is efficient:** For sparse graphs with few queries
