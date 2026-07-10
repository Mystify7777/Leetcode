// 3558. Number of Ways to Assign Edge Weights I
// https://leetcode.com/problems/number-of-ways-to-assign-edge-weights-i/
class Solution {

    long mod = (long)1e9 + 7;

    long power(long x, long n){

        if(n==0) return 1;
        if(n==1) return x;

        long half = power(x, n/2);

        half = (half*half)%mod;

        if(n%2==1){
            half = (half * x)%mod;
        }

        return half;

    }

    private int depth(int node, int parent, List<Integer> adj[]){

        int ret = 0;

        for(int x: adj[node]){

            if(x!=parent){
                ret = Math.max(ret, 1+ depth(x, node, adj));
            }

        }

        return ret;
    }





    public int assignEdgeWeights(int[][] edges) {

        int n = edges.length;

        List<Integer> adj[] = new ArrayList[n+2];

        for(int i=0;i<n+2;i++){
            adj[i] = new ArrayList<>();
        }

        for(int i=0;i<n;i++){

            int node1 = edges[i][0];
            int node2 = edges[i][1];

            adj[node1].add(node2);
            adj[node2].add(node1);

        }

        int dep = depth(1, -1, adj);

        return (int)power(2, dep-1);

    }
}

class Solution2 {
    public int assignEdgeWeights(int[][] edges) {
        int n = 0;
        for (int[] e : edges) {
            if (e[0] > n) n = e[0];
            if (e[1] > n) n = e[1];
        }

        int[] tree = new int[n + 1];
        for (int[] e : edges) 
            if (e[0] > e[1]) tree[e[0]] = e[1];
            else tree[e[1]] = e[0];
        
        boolean[] vis = new boolean[n + 1];

        int max = 0;

        for (int i = 1; i <= n; i++) {
            int idx = i, depth = 0;
            while (tree[idx] != 0 && !vis[idx]) {
                vis[idx] = true;
                idx = tree[idx];
                depth++;
            }

            depth += tree[idx];
            tree[i] = depth;

            if (depth > max) max = depth;
        }

        return (int) modPow(2, max - 1);
    }

    static final int MOD = 1_000_000_007;

    private long modPow(long a, long b) {
        long res = 1;

        while (b > 0) {
            if ((b & 1) == 1)
                res = (res * a) % MOD;

            a = (a * a) % MOD;
            b >>= 1;
        }

        return res;
    }
}