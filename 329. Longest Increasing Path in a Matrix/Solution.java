// 329. Longest Increasing Path in a Matrix
class Solution2 {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] memo = new int[matrix.length][matrix[0].length];
        int result = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                result = Math.max(result,findLongest(matrix,row,col,Integer.MIN_VALUE,memo));
            }
        }
        return result;
    }
    public int findLongest(int[][] matrix, int row, int col, int pre, int[][] memo){
        if(row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length || matrix[row][col] <= pre)
            return 0;
    
        if(memo[row][col] > 0){
            return memo[row][col];
        }
        else{
            int curr = matrix[row][col];
            int tempMax = 0;
            tempMax = Math.max(tempMax, findLongest(matrix, row - 1, col, curr, memo));
            tempMax = Math.max(tempMax, findLongest(matrix, row + 1, col, curr, memo));
            tempMax = Math.max(tempMax, findLongest(matrix, row, col - 1, curr, memo));
            tempMax = Math.max(tempMax, findLongest(matrix, row, col + 1, curr, memo));
            memo[row][col] = tempMax + 1;
            return memo[row][col];
        }
    }
}
class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        int maxLen = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (memo[i][j] == -1) {
                    int len = dfs(matrix, i, j, memo);
                    if (maxLen < len) maxLen = len;
                }
            }
        }
        return maxLen;
    }

    public int dfs(int[][] matrix, int row, int col, int[][] memo) {
        if (memo[row][col] != -1) {
            return memo[row][col];
        }

        // we need to calculate the max length starting at row, col 
        // 1 + max(dfs(row+1, col), dfs(row-1, col), dfs(row)(col+1), dfs(row)(col-1))
        int max = 1;

        if (row+1 < matrix.length && matrix[row+1][col] > matrix[row][col]) {
            max = Math.max(max, 1 + dfs(matrix, row+1, col, memo));
        } 

        if (row-1 >= 0 && matrix[row-1][col] > matrix[row][col]) {
            max = Math.max(max, 1 + dfs(matrix, row-1, col, memo));
        } 

        if (col+1 < matrix[0].length && matrix[row][col+1] > matrix[row][col]) {
            max = Math.max(max, 1 + dfs(matrix, row, col+1, memo));
        }
        
        if (col-1 >= 0 && matrix[row][col-1] > matrix[row][col]) {
            max = Math.max(max, 1 + dfs(matrix, row, col-1, memo));
        }
        
        memo[row][col] = max;
        return memo[row][col];
    }
}