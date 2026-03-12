// 3600. Maximize Spanning Tree Stability with Upgrades
// https://leetcode.com/problems/maximize-spanning-tree-stability-with-upgrades/
class DSU {
    int[] parent;
    int[] rank;
    int components;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean unite(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa == pb) return false;

        if (rank[pa] < rank[pb]) {
            int temp = pa;
            pa = pb;
            pb = temp;
        }

        parent[pb] = pa;

        if (rank[pa] == rank[pb]) {
            rank[pa]++;
        }

        components--;
        return true;
    }
}

class Solution {

    public boolean canAchieve(int n, int[][] edges, int k, int x) {
        DSU dsu = new DSU(n);

        // Mandatory edges
        for (int[] e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 1) {
                if (s < x) return false;
                if (!dsu.unite(u, v)) return false;
            }
        }

        // Free optional edges
        for (int[] e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 0 && s >= x) {
                dsu.unite(u, v);
            }
        }

        // Upgrade edges
        int usedUpgrades = 0;

        for (int[] e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 0 && s < x && 2 * s >= x) {
                if (dsu.unite(u, v)) {
                    usedUpgrades++;
                    if (usedUpgrades > k) return false;
                }
            }
        }

        return dsu.components == 1;
    }

    public int maxStability(int n, int[][] edges, int k) {

        // Check mandatory cycle
        DSU dsu = new DSU(n);

        for (int[] e : edges) {
            if (e[3] == 1) {
                if (!dsu.unite(e[0], e[1])) {
                    return -1;
                }
            }
        }

        int low = 1, high = 200000;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canAchieve(n, edges, k, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }
}

class Dsu{
    private int parent[];
    private int siz[];
    private int n;
    public Dsu(int n){
        this.n=n;
        parent=new int[n];
        siz=new int[n];
        for(int i=0;i<n;i++){
            parent[i]=i;
        }
    }
    public int findParent(int p){
        if(parent[p]==p){
            return p;
        }
        parent[p]=findParent(parent[p]);
        return parent[p];
    }
    public void union(int a,int b){
        int pa=findParent(a);
        int pb=findParent(b);
        if(pa!=pb){
            if(siz[pa]>siz[pb]){
                int temp=pa;
                pa=pb;
                pb=pa;
            }
            parent[pb]=pa;
            siz[pa]+=siz[pb];
        }
    }
}

class Edge implements Comparable<Edge>{
    private int src;
    private int tgt;
    private int strength;
    public Edge(int s,int t,int st){
        src=s;
        tgt=t;
        strength=st;
    }
    public int getSrc(){
       return src; 
    }
    public int getTgt(){
       return tgt; 
    }
    public int getStrength(){
       return strength; 
    }
    public int compareTo(Edge e){
        return e.strength-this.strength;
    }
    public String toString(){
        return this.src+" "+this.tgt+" "+this.strength;
    }
}

class Solution2 {
    public int maxStability(int n, int[][] edges, int k) {
        int minStren=(int)1e6;
        Dsu dsu=new Dsu(n);
        boolean foundOne=false;
        List<Edge> edgs=new ArrayList<>();
        for(int i=0;i<edges.length;i++){
            if(edges[i][3]==1){
                int paSrc=dsu.findParent(edges[i][0]);
                int paTgt=dsu.findParent(edges[i][1]);
                if(paSrc==paTgt){
                    return -1;
                }
                dsu.union(edges[i][0],edges[i][1]);
                minStren=Math.min(minStren,edges[i][2]);
                foundOne=true;
            }else{
                edgs.add(new Edge(edges[i][0],edges[i][1],edges[i][2]));
            }
        }
        
        Collections.sort(edgs);
        int e;
        List<Integer> zero=new ArrayList<>();
        for(e=0;e<edgs.size();e++){
            //System.out.println(edges);
            int cs=edgs.get(e).getStrength();
            int paSrc=dsu.findParent(edgs.get(e).getSrc());
            int paTgt=dsu.findParent(edgs.get(e).getTgt());
            if(paSrc!=paTgt){
                zero.add(cs);
                dsu.union(paSrc,paTgt);
            }
        }

        for(int i=0;i<n;i++){
            if(dsu.findParent(i)!=dsu.findParent(0)){
                return -1;
            }
        }
        int va=(int)1e6;
        if(zero.size()!=0){
            va=0;
            if(k==0){
                va=zero.get(zero.size()-1);
            }else if(k>=zero.size()){
                va=zero.get(zero.size()-1)*2;
            }else{
                va=Math.min(zero.get(zero.size()-1)*2,zero.get(zero.size()-k-1));
            }
        }
        return Math.min(minStren,va);
    }
}