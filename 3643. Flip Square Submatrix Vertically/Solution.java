// 3643. Flip Square Submatrix Vertically
// https://leetcode.com/problems/flip-square-submatrix-vertically/
class Solution {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k >> 1; j++) {
                int temp = grid[x + j][y + i];
                grid[x + j][y + i] = grid[x + k - j - 1][y + i];
                grid[x + k - j - 1][y + i] = temp;
            }
        }

        return grid;
    }
}
class Solution2 {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        for (int i = 0; i < k >> 1; i++) {
            for (int j = 0; j < k; j++) {
                int temp = grid[x + i][y + j];
                grid[x + i][y + j] = grid[x + k - 1 - i][y + j];
                grid[x + k - 1 - i][y + j] = temp;
            }
        }
        return grid;
    }
}
class Solution3 {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {

        int top = x;
        int bottom = x+k-1;
        while(top < bottom){


            for(int j=y;j<y+k;j++){


                int temp = grid[top][j];
                grid[top][j] = grid[bottom][j];
                grid[bottom][j] = temp;
            }
            top++;
            bottom--;
        }
        return grid;
    }
}