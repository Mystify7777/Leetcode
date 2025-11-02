// 2257. Count Unguarded Cells in the Grid
// https://leetcode.com/problems/count-unguarded-cells-in-the-grid/
class Solution {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        char[][] grid = new char[m][n];

        for (int[] guard : guards) {
            grid[guard[0]][guard[1]] = 'G';
        }
        for (int[] wall : walls) {
            grid[wall[0]][wall[1]] = 'W';
        }

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] guard : guards) {
            for (int[] dir : directions) {
                int x = guard[0] + dir[0];
                int y = guard[1] + dir[1];
                while (x >= 0 && x < m && y >= 0 && y < n) {
                   
                    if (grid[x][y] == 'W' || grid[x][y] == 'G') break;
                   
                    if (grid[x][y] == '\0') {
                        grid[x][y] = 'A'; 
                    }
                    x += dir[0];
                    y += dir[1];
                }
            }
        }

        int unguarded = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '\0') { 
                    unguarded++;
                }
            }
        }

        return unguarded;
    }
}
/**Alternate fast approach
class Solution {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
          int visit[][]=new int[m][n];
          int count=0;
          int gr=guards.length;
          int wr=walls.length;
          for(int[] wall:walls)
          {
            visit[wall[0]][wall[1]]=2;
          }
          for(int gu[]:guards)
        {
            visit[gu[0]][gu[1]]=2;
        }
          int[][] dxdy={{-1,0},{1,0},{0,-1},{0,1}};
          for(int row=0;row<gr;row++){
                int dx=guards[row][0];
                int dy=guards[row][1];
                for(int i=dx+1;i<m;i++)
                {
                    int newdx=i;
                    int newdy=dy;
                    if(visit[newdx][newdy]==2)
                    {
                        break;
                    } 
                    if(visit[newdx][newdy]==1)
                    {
                        continue;
                    }
                    visit[newdx][newdy]=1;
                    count++;
                }
                for(int i=dy+1;i<n;i++)
                {
                    int newdx=dx;
                    int newdy=i;
                    if(visit[newdx][newdy]==2)
                    {
                        break;
                    }
                    if(visit[newdx][newdy]==1){
                        continue;
                    }
                    visit[newdx][newdy]=1;
                    count++;

                }
                for(int i=dy-1;i>=0;i--)
                {
                    int newdx=dx;
                    int newdy=i;
                    if(visit[newdx][newdy]==2)
                    {
                        break;
                    }
                        
                    if(visit[newdx][newdy]==1){
                        continue;
                    }
                    visit[newdx][newdy]=1;
                    count++;

                }
                for(int i=dx-1;i>=0;i--)
                {
                    int newdx=i;
                    int newdy=dy;
                    if(visit[newdx][newdy]==2)
                    {
                        break;
                    }
                        
                    if(visit[newdx][newdy]==1){
                        continue;
                    }
                    visit[newdx][newdy]=1;
                    count++;
                }
            
          }
          
          int sum=(m*n)-(gr+wr+count);
          return sum;

    }
   

}
 */