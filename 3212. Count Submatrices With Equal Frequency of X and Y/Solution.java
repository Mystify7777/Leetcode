// 3212. Count Submatrices With Equal Frequency of X and Y
// https://leetcode.com/problems/count-submatrices-with-equal-frequency-of-x-and-y/
class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int[] sumX = new int[cols];
        int[] sumY = new int[cols];
        int res = 0;

        for (int i = 0; i < rows; i++) {
            int rx = 0, ry = 0;

            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'X') rx++;
                else if (grid[i][j] == 'Y') ry++;

                sumX[j] += rx;
                sumY[j] += ry;

                if (sumX[j] > 0 && sumX[j] == sumY[j]) res++;
            }
        }

        return res;
    }
}
class Solution2 {
    public int numberOfSubmatrices(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;
        int[] colX = new int[n];
        int[] colY = new int[n];
        int ans = 0;


        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {

                if(grid[i][j] == 'X') colX[j]++;
                if(grid[i][j] == 'Y') colY[j]++;
            }
            
            int x = 0, y = 0;
            for(int j = 0; j < n; j++) {

                x += colX[j];
                y += colY[j];

                if(x == y && x > 0) ans++;
            }
        }

        return ans;
    }
}