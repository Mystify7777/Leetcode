// 120. Triangle
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int row = triangle.size();
        int[] memo = new int[row];

        for (int i = 0; i < row; i++) {
            memo[i] = triangle.get(row - 1).get(i);
        }

        for (int r = row-2; r >= 0; r--) {
            for (int c = 0; c <= r; c++) {
                memo[c] = Math.min(memo[c], memo[c+1]) + triangle.get(r).get(c);
            }
        }

        return memo[0];        
    }
}
//fastest one i found in submissions
/**
// class Solution {
//     public int minimumTotal(List<List<Integer>> triangle) {
//         int n = triangle.size();
        
//         // dp array initialized with -1
//         int[][] dp = new int[n][n];
//         for (int i = 0; i < n; i++) {
//             for (int j = 0; j < n; j++) {
//                 dp[i][j] = -1;
//             }
//         }
        
//         return solve(0, 0, triangle, dp);
//     }
    
//     private int solve(int i, int j, List<List<Integer>> triangle, int[][] dp) {
//         int n = triangle.size();
        
//         // Base case: last row → return value at that cell
//         if (i == n - 1) {
//             return triangle.get(i).get(j);
//         }
        
//         // If already computed, return stored value
//         if (dp[i][j] != -1) {
//             return dp[i][j];
//         }
        
//         // Recursive calls: go down and diagonal
//         int down = solve(i + 1, j, triangle, dp);
//         int diagonal = solve(i + 1, j + 1, triangle, dp);
        
//         // Store result in dp
//         dp[i][j] = triangle.get(i).get(j) + Math.min(down, diagonal);
        
//         return dp[i][j];
//     }
// }





class Solution {
    int m;
    Integer[][] dp;
    public int minimumTotal(List<List<Integer>> tran) {
        m = tran.size();
        dp = new Integer[m][m];
       
        return helper(tran,0,0);
    }
    int helper(List<List<Integer>> tran,int row, int col){
        if(row==m){
            return 0;
        }
        if(dp[row][col]!=null){
            return dp[row][col];
        }
        int left = helper(tran,row+1,col);
        int right = helper(tran,row+1,col+1);
        return dp[row][col]=tran.get(row).get(col) + Math.min(left,right);
    }
}
 */