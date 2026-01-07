// 994. Rotting Oranges
class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = grid;
        Queue<int[]> q = new LinkedList<>();
        int countFreshOrange = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == 2) {
                    q.offer(new int[] {i, j});
                }
                if (visited[i][j] == 1) {
                    countFreshOrange++;
                }
            }
        }
        if (countFreshOrange == 0)
            return 0;
        if (q.isEmpty())
            return -1;
        
        int minutes = -1;
        int[][] dirs = {{1, 0},{-1, 0},{0, -1},{0, 1}};
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] cell = q.poll();
                int x = cell[0];
                int y = cell[1];
                for (int[] dir : dirs) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i >= 0 && i < m && j >= 0 && j < n && visited[i][j] == 1) {
                        visited[i][j] = 2;
                        countFreshOrange--;
                        q.offer(new int[] {i, j});
                    }
                }
            }
            minutes++;
        }
        
        if (countFreshOrange == 0)
            return minutes;
        return -1;
    }
}

class Solution2 {
    public int orangesRotting(int[][] grid) {
        if(grid==null || grid.length==0 || grid[0].length ==0 ) return -1;
        int n = grid.length; 
        int m = grid[0].length;
        int time[][] = new int [n][m];
        for(int i=0;i<n;i++){
            Arrays.fill(time[i],Integer.MAX_VALUE);
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==2){
                    dfs(grid,time,i,j,0);
                }
            }
        }
        int timeReq = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    if(time[i][j]==Integer.MAX_VALUE) return -1;
                    timeReq = Math.max(timeReq,time[i][j]);
                }
            }
        }
        return timeReq; 
    }
    void dfs(int grid[][],int time[][],int i , int j , int currTime){
        if(i<0 || j<0 || i>=grid.length || j>=grid[0].length 
        || grid[i][j]==0 || currTime>=time[i][j]) return ;
        time[i][j] = currTime;
        dfs(grid,time,i+1,j,currTime+1);
        dfs(grid,time,i-1,j,currTime+1);
        dfs(grid,time,i,j+1,currTime+1);
        dfs(grid,time,i,j-1,currTime+1);
    }
}