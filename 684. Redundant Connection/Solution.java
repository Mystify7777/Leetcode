// 684. Redundant Connection
class Solution {
    private int[] parent;
    private int[] rank;

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n + 1];
        rank = new int[n + 1];

        // Initialize each node as its own parent
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (find(u) == find(v)) {
                return edge; // Cycle detected
            }
            join(u, v); // Merge sets
        }

        return new int[0]; // Unreachable for valid inputs
    }

    private int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // Path compression
        }
        return parent[i];
    }

    private void join(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            // Union by rank
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

class Solution2 {
    private int find(int i,int[] parent){
        if(parent[i]==-1){
            return i;
        }
        parent[i] = find(parent[i],parent);
        return parent[i];
    }
    private boolean union(int i,int j,int[] parent,int[] rank){
        int a = find(i,parent);
        int b = find(j,parent);
        if(a==b){
            return false;
        }
        if(rank[a]<=rank[b]){
            parent[a] = b;
            rank[b] += rank[a];
        }else{
            parent[b] = a;
            rank[a] += rank[b];
        }
        return true;
    }
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n+1];
        int[] rank = new int[n+1];
        for(int i=1;i<=n;i++){
            parent[i] = -1;
            rank[i] = 1;
        }
        for(int[] e: edges){
            if(!union(e[0],e[1],parent,rank)){
                return e;
            }
        }
        return new int[]{};
    }
}