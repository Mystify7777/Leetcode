// 3418. Maximum Amount of Money Robot Can Earn
// https://leetcode.com/problems/maximum-amount-of-money-robot-can-earn/
class Solution {
    public int maximumAmount(int[][] coins) {
        int n = coins.length;
        int m = coins[0].length;
        int[][][] dp = new int[n][m][3];

        for (int[][] row : dp) {
            for (int[] col : row) {
                java.util.Arrays.fill(col, (int)-1e9);
            }
        }

        dp[0][0][1] = 0;
        dp[0][0][2] = 0;
        dp[0][0][0] = coins[0][0];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < 3; k++) {
                    if (i > 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j][k] + coins[i][j]);
                    if (i > 0 && k > 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j][k - 1]);
                    if (j > 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j - 1][k] + coins[i][j]);
                    if (j > 0 && k > 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j - 1][k - 1]);
                }

        return Math.max(dp[n - 1][m - 1][0], Math.max(dp[n - 1][m - 1][1], dp[n - 1][m - 1][2]));
    }
}

class Solution2 {

    public int maximumAmount(int[][] coins) {
        int n = coins[0].length;
        int[][] dp = new int[n + 1][3];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        }

        for (int i = 0; i < 3; i++) {
            dp[1][i] = 0;
        }
        for (int[] row : coins) {
            for (int j = 1; j <= n; j++) {
                int x = row[j - 1];
                dp[j][2] = Math.max(
                    Math.max(dp[j - 1][2] + x, dp[j][2] + x),
                    Math.max(dp[j - 1][1], dp[j][1])
                );
                dp[j][1] = Math.max(
                    Math.max(dp[j - 1][1] + x, dp[j][1] + x),
                    Math.max(dp[j - 1][0], dp[j][0])
                );
                dp[j][0] = Math.max(dp[j - 1][0], dp[j][0]) + x;
            }
        }

        return dp[n][2];
    }
}