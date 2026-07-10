// 3559. Number of Ways to Assign Edge Weights II
// https://leetcode.com/problems/number-of-ways-to-assign-edge-weights-ii/
class LCA{
    private int n;
    private int m;
    private int[]d;
    private List<Integer>[] e;
    private int[][]f;

    public LCA(int[][] edges, int root){
        n = edges.length +1;
        m = (int)(Math.log(n)/Math.log(2)) + 1;
        e = new ArrayList[n+1];
        d = new int[n +1];
        f = new int[n+1][m];

        for(int i = 0; i<= n; i++){
            e[i] = new ArrayList<Integer>();
        }

        for(int[] edge: edges){
            int u = edge[0], v = edge[1];
            e[u].add(v);
            e[v].add(u);
        }

        dfs(root, 0);

        for(int i = 1; i< m; i++){
            for(int x = 1; x<=n; x++){
                f[x][i] = f[f[x][i-1]][i-1];
            }
        }
    }

    private void dfs(int x, int fa){
        f[x][0] = fa;
        for(int y: e[x]){
            if(y == fa) continue;
            d[y] = d[x]+1;
            dfs(y, x);
        }
    }

    public int lca(int x, int y){
        if(d[x] > d[y]){
            int temp = x;
            x = y;
            y = temp;
        }

        for(int i = m -1; i>=0; i--){
            if(d[x] <= d[f[y][i]]){
                y = f[y][i];
            }
        }
        if(x == y) return x;

        for(int i = m-1; i>=0; i--){
            if(f[y][i] != f[x][i]){
                x = f[x][i];
                y = f[y][i];
            }
        }

        return f[x][0];
    }

    public int dis(int x, int y){
        return d[x] + d[y] - d[lca(x, y)]*2;
    }
}

class Solution2 {
    private static final int MOD = 1_000_000_007;
    private static final int N = 100010;
    private static int[]p2 = new int[N];
    static {
        p2[0] = 1;
        for(int i = 1; i< N; i++){
            p2[i] = (int)(((long)p2[i-1] * 2) % MOD);
        }
    }
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        LCA lca = new LCA(edges, 1);
        int m = queries.length;
        int[]res = new int[m];

        for(int i = 0; i< m; i++){
            int x = queries[i][0], y = queries[i][1];
            if(x != y){
                res[i] = p2[lca.dis(x, y) - 1];
            }
        }
        return res;
    }
}

class Solution {
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        int m = edges.length;
        int q = queries.length;
        
        int[] head = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            head[i] = -1;
        }
        
        int[] to = new int[m * 2];
        int[] next = new int[m * 2];
        int edgeCount = 0;
        
        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            to[edgeCount] = v;
            next[edgeCount] = head[u];
            head[u] = edgeCount++;
            
            to[edgeCount] = u;
            next[edgeCount] = head[v];
            head[v] = edgeCount++;
        }
        
        int[] ans = new int[q];
        int[] qHead = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            qHead[i] = -1;
        }
        
        int[] qTo = new int[q * 2];
        int[] qNext = new int[q * 2];
        int[] qIndex = new int[q * 2];
        int qCount = 0;
        
        for (int i = 0; i < q; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            if (u == v) {
                ans[i] = 0;
                continue;
            }
            qTo[qCount] = v;
            qIndex[qCount] = i;
            qNext[qCount] = qHead[u];
            qHead[u] = qCount++;
            
            qTo[qCount] = u;
            qIndex[qCount] = i;
            qNext[qCount] = qHead[v];
            qHead[v] = qCount++;
        }
        
        int MOD = 1000000007;
        int[] p2 = new int[n + 1];
        p2[0] = 1;
        for (int i = 1; i <= n; i++) {
            p2[i] = (p2[i - 1] * 2) % MOD;
        }
        
        int[] stack = new int[n + 1];
        int[] edgeStack = new int[n + 1];
        int[] depth = new int[n + 1];
        int[] dsuParent = new int[n + 1];
        int[] color = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            dsuParent[i] = i;
        }
        
        int top = 0;
        stack[0] = 1;
        edgeStack[0] = head[1];
        color[1] = 1;
        depth[1] = 0;
        
        while (top >= 0) {
            int u = stack[top];
            int e = edgeStack[top];
            
            if (e != -1) {
                int v = to[e];
                edgeStack[top] = next[e];
                
                if (color[v] == 0) {
                    color[v] = 1;
                    depth[v] = depth[u] + 1;
                    
                    top++;
                    stack[top] = v;
                    edgeStack[top] = head[v];
                }
            } else {
                for (int qe = qHead[u]; qe != -1; qe = qNext[qe]) {
                    int v = qTo[qe];
                    if (color[v] != 0) {
                        int qIdx = qIndex[qe];
                        if (ans[qIdx] == 0) {
                            int curr = v;
                            while (curr != dsuParent[curr]) {
                                dsuParent[curr] = dsuParent[dsuParent[curr]];
                                curr = dsuParent[curr];
                            }
                            int lca = curr;
                            
                            int len = depth[u] + depth[v] - 2 * depth[lca];
                            ans[qIdx] = p2[len - 1];
                        }
                    }
                }
                
                color[u] = 2;
                top--;
                if (top >= 0) {
                    dsuParent[u] = stack[top];
                }
            }
        }
        
        return ans;
    }
}