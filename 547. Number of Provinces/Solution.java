// 547. Number of Provinces
/*
class Solution {
    public int findCircleNum(int[][] M) {
        int N = M.length;
        boolean[]visited = new boolean[N];
        int count = 0;
        
        for(int i = 0; i < N ;i++){
            if(!visited[i]){
                count++;
                dfs(M,i,visited);
            }
        }
      return count;  
    }
    
    
    private void dfs(int[][]M,int i,boolean[]visited){
        for(int j = 0 ; j < M[i].length ; j++){
            if(!visited[j] && M[i][j] != 0){
                visited[j] = true;
                dfs(M,j,visited);
            }
        }
    }
    
}
*/
class Solution {
    public int findCircleNum(int[][] isConnected){
        int n=isConnected.length;

        boolean[] vis= new boolean[n];
        int count=0;
        for(int i=0;i<n;i++){
            if(!vis[i]){
                dfs(isConnected,vis,i);

                count++;
            }
        }

        return count;
    }

    private void dfs(int[][] isConnected,boolean[] visited,int city){
        visited[city]=true;
        for(int j=0;j<isConnected.length;j++){
            if(isConnected[city][j]==1 && !visited[j]){
                dfs(isConnected,visited,j);
            }
        }
    }
} 

/**
class Solution {
    private void solveDFS(int n, int[][] isConnected, int idx, boolean[] vis) {
        vis[idx] = true;
        for (int i = 0; i < n; i++) {
            if (isConnected[idx][i] == 1 && !vis[i]) {
                solveDFS(n, isConnected, i, vis);
            }
        }
    }

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] vis = new boolean[n + 1];
        int findConnection = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                solveDFS(n, isConnected, i, vis);
                findConnection++;
            }
        }
        return findConnection;
    }
}
 */