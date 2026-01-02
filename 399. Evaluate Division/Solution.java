// 399. Evaluate Division
class Solution {
    public void dfs(String node, String dest, HashMap<String, HashMap<String, Double>> gr, HashSet<String> vis, double[] ans, double temp) {
        if (vis.contains(node))
            return;

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

    public HashMap<String, HashMap<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
        HashMap<String, HashMap<String, Double>> gr = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String dividend = equations.get(i).get(0);
            String divisor = equations.get(i).get(1);
            double value = values[i];

            gr.putIfAbsent(dividend, new HashMap<>());
            gr.putIfAbsent(divisor, new HashMap<>());

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
                double temp = 1.0;
                dfs(dividend, divisor, gr, vis, ans, temp);
                finalAns[i] = ans[0];
            }
        }

        return finalAns;
    }
}


//compare

/**
import java.util.*;

class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Map string names to integer IDs for faster processing
        Map<String, Integer> map = new HashMap<>();
        int id = 0;
        for (List<String> eq : equations) {
            for (String s : eq) {
                if (!map.containsKey(s)) map.put(s, id++);
            }
        }

        // Use primitive arrays instead of Maps for the Union-Find structure
        int[] parent = new int[id];
        double[] weight = new double[id];
        for (int i = 0; i < id; i++) {
            parent[i] = i;
            weight[i] = 1.0;
        }

        // 1. Build the Union-Find structure
        for (int i = 0; i < equations.size(); i++) {
            int u = map.get(equations.get(i).get(0));
            int v = map.get(equations.get(i).get(1));
            union(u, v, values[i], parent, weight);
        }

        // 2. Process Queries
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String s1 = queries.get(i).get(0);
            String s2 = queries.get(i).get(1);
            
            if (!map.containsKey(s1) || !map.containsKey(s2)) {
                results[i] = -1.0;
            } else {
                int u = map.get(s1);
                int v = map.get(s2);
                int rootU = find(u, parent, weight);
                int rootV = find(v, parent, weight);
                
                if (rootU != rootV) {
                    results[i] = -1.0;
                } else {
                    results[i] = weight[u] / weight[v];
                }
            }
        }
        return results;
    }

    private int find(int i, int[] parent, double[] weight) {
        if (parent[i] == i) return i;
        
        int oldParent = parent[i];
        int root = find(oldParent, parent, weight);
        
        parent[i] = root;
        // Update weight relative to the root: i / root = (i / oldParent) * (oldParent / root)
        weight[i] *= weight[oldParent];
        
        return root;
    }

    private void union(int u, int v, double val, int[] parent, double[] weight) {
        int rootU = find(u, parent, weight);
        int rootV = find(v, parent, weight);
        
        if (rootU != rootV) {
            parent[rootU] = rootV;
            // Calculate ratio rootU / rootV = (u / rootV) / (u / rootU)
            // = ( (u/v) * (v/rootV) ) / (u/rootU)
            weight[rootU] = (val * weight[v]) / weight[u];
        }
    }
}
 */