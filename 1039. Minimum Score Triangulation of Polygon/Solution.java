// 1039. Minimum Score Triangulation of Polygon
// copypasted
class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = i + 1; k < j; ++k) {
                    dp[i][j] = Math.min(dp[i][j] == 0 ? Integer.MAX_VALUE : dp[i][j],
                        dp[i][k] + values[i] * values[k] * values[j] + dp[k][j]);
                }
            }
        }
        return dp[0][n - 1];
    }
}

/**
class Solution {
    int[][] dp = new int[50][50];
    
    public int minScoreTriangulation(int[] values, int i, int j, int res) {
        if (j == 0) j = values.length - 1;
        if (dp[i][j] != 0) return dp[i][j];
        for (int k = i + 1; k < j; k++) {
            res = Math.min(res == 0 ? Integer.MAX_VALUE : res,
                minScoreTriangulation(values, i, k, 0) +
                values[i] * values[k] * values[j] +
                minScoreTriangulation(values, k, j, 0));
        }
        return dp[i][j] = res;
    }
} */

/**
class Solution {
    int [][]dp;
    public int minScoreTriangulation(int[] values) {
        dp = new int[values.length][values.length];
        return helper(values, 0, values.length-1);
    }
    int helper(int []a, int i, int j){
        if(j - i < 2){
            return 0;
        }
        if(dp[i][j] != 0)
        return dp[i][j];
        int min = Integer.MAX_VALUE;
        for(int k = i+1 ; k < j ; k++){
            int cost = a[i]*a[k]*a[j] + helper(a,i,k)+helper(a,k,j);
            min = Math.min(min, cost);
        }
        return dp[i][j] = min;
    }
}
 */
/**

class Solution {
    int dp[][];
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        dp = new int[n][n];
        for (int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        // call the function with i = 1, j = n-1
        // because every time we have to take the ar[i-1] that's why we are starting from i = 1
        return helper_min_score(1,n-1,values);
    }
    
    private int helper_min_score(int i,int j,int values[]){
        if (i == j) return 0;
        
        if (dp[i][j] != -1) return dp[i][j];
        
        int mini = Integer.MAX_VALUE;
        // do partition
    
        for (int k=i;k<=j-1;k++){
            int steps = values[i-1] * values[k] * values[j]
                + helper_min_score(i,k,values)// partition 1
                + helper_min_score(k+1,j,values); // partition 2
            mini = Math.min(steps,mini);
        }
        
        return dp[i][j] = mini;
    }
}
 */
