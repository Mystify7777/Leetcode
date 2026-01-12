public class Solution {
    
}
// 1129. Shortest Path with Alternating Colors
class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        int[][] g = new int[n][n];
        buildGraph(g, n, red_edges, blue_edges);
        
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 1});
        q.offer(new int[]{0, -1});
        int len = 0;
        int[] res = new int[n];
        Arrays.fill(res, Integer.MAX_VALUE);
        res[0] = 0;
        
        Set<String> visited = new HashSet<>();
        while (!q.isEmpty()) {
            int size = q.size();
            len++;
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int node = cur[0];
                int color = cur[1];
                int oppoColor = -color;
                
                for (int j = 1; j < n; j++) {
                    if (g[node][j] == oppoColor ||
                       g[node][j] == 0) {
                        if (!visited.add(j + "" + oppoColor)) continue;
                        q.offer(new int[]{j, oppoColor});
                        res[j] = Math.min(res[j], len);
                    }
                }
            }
        }
        
        for (int i = 1; i < n; i++) {
            if (res[i] == Integer.MAX_VALUE) {
                res[i] = -1;
            }
        }
        
        return res;
    }
    
    private void buildGraph(int[][] g, int n, int[][] red_edges, int[][] blue_edges) {
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], -n);
        }
        
        for (int[] e : red_edges) {
            int from = e[0];
            int to = e[1];
            g[from][to] = 1;
        }
        
        for (int[] e : blue_edges) {
            int from = e[0];
            int to = e[1];
            if (g[from][to] == 1) {
                g[from][to] = 0;
            } else {
                g[from][to] = -1;
            }
        }
    }
}

class Solution2 {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        int rqi = 0, rqj = 0, bqi = 0, bqj = 0, nrq = 0, nbq = 0, rql = 10, bql = 10;
        int pathLen = 0;
        int nvis = 0;
        int[] redRes = new int[n], blueRes = new int[n];
        int[] redQueue = new int[rql], blueQueue = new int[bql];
        int[][] redVerts = new int[n][redEdges.length/n + 2];
        int[][] blueVerts = new int[n][blueEdges.length/n + 2];
        for (int i = 0; i < n; i++) {
            redRes[i] = 0x7fffffff;
            blueRes[i] = 0x7fffffff;
        }
        fillVerts(redVerts, redEdges);
        fillVerts(blueVerts, blueEdges);
        rqj = (rqj + 1) % rql; nrq++;
        bqj = (bqj + 1) % bql; nbq++;
        
        
        while(nrq > 0 || nbq > 0) {
            int[] varr;
            int vl, nrEls = nrq, nbEls = nbq;
            pathLen++;
            for(int i = 0; i < nrEls; i++) {
                varr = redVerts[redQueue[rqi]];
                rqi = (rqi + 1) % rql;
                nrq--;
                vl = varr[0];
                for (int j = 1; j <= vl; j++) {
                    if (pathLen < redRes[varr[j]]) {
                        if (nbq == bql) {
                            blueQueue = dblSize(blueQueue, bqi);
                            bqi = 0;
                            bqj = bql;
                            bql = 2 * bql;
                        }
                        redRes[varr[j]] = pathLen;  
                        blueQueue[bqj] = varr[j];
                        bqj = (bqj + 1) % bql;
                        nbq++;
                    }
                } 
            }
            
            for(int i = 0; i < nbEls; i++) {
                varr = blueVerts[blueQueue[bqi]];
                bqi = (bqi + 1) % bql;
                nbq--;
                vl = varr[0];
                for (int j = 1; j <= vl; j++) {
                    if (pathLen < blueRes[varr[j]]) {
                        //System.out.println("nrq= " + nrq + " rql= " + rql);
                        if (nrq == rql) {
                            redQueue = dblSize(redQueue, rqi);
                            rqi = 0;
                            rqj = rql;
                            rql = 2 * rql;
                        }
                        blueRes[varr[j]] = pathLen;
                        redQueue[rqj] = varr[j];
                        rqj = (rqj + 1) % rql;
                        nrq++;
                    }
                } 
            }
        }
        int tmp;
        redRes[0] = 0;
        for (int i = 0; i < n; i++) {
            tmp = redRes[i] <= blueRes[i] ? redRes[i] : blueRes[i];
            redRes[i] = tmp == 0x7fffffff ? -1 : tmp;
        }
        //System.out.println(Arrays.toString(res));
        
        return redRes;
    }
    
    void fillVerts(int[][] verts, int[][] edges) {
        int v1, v2, el = edges.length;
        int[] varr;
        for (int i = 0; i < el; i++) {
            v1 = edges[i][0];
            v2 = edges[i][1];
            varr = verts[v1];
            verts[v1] = varr.length == ++varr[0] ? dblSize(varr, 0) : varr;
            verts[v1][varr[0]] = v2;
        }
    }
    
    int[] dblSize(int[] arr, int s) {
        int l = arr.length;
        int[] narr = new int[2 * l];
        for (int i = 0; i < l; i++, s++) narr[i] = arr[s % l];
        return narr;
    }
}

class Solution3 {
    List<Integer>[] gr;
    List<Integer>[] gb;
    int[] res;
    public int[] shortestAlternatingPaths(int n, int[][] re, int[][] be) {
        gr = new ArrayList[n];
        gb = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            gr[i] = new ArrayList<>();
            gb[i] = new ArrayList<>();
        }
        for(int[] e : re) {
            gr[e[0]].add(e[1]);
        }
        for(int[] e : be) {
            gb[e[0]].add(e[1]);
        }
        res = new int[n];
        Arrays.fill(res, Integer.MAX_VALUE);
        parse(n, 0);
        parse(n, 1);
        for(int i = 0; i < n; i++) {
            res[i] = res[i] == Integer.MAX_VALUE ? -1 : res[i];
        }
        return res;
    }
    
    void parse(int n, int s) {
        Queue<Integer> q = new LinkedList<>();
        boolean[][] seen = new boolean[n][2];
        q.add(0);
        int step = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- > 0) {
                int v = q.poll();
                res[v] = Math.min(res[v], step);
                for(Integer u : (s == 0 ? gr[v] : gb[v])) {
                    if(!seen[u][s]) {
                        q.add(u);
                        seen[u][s] = true;
                    }
                }
            }
            step++;
            s = 1 - s;
        }
    }
}