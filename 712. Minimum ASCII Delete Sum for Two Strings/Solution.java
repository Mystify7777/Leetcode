// 712. Minimum ASCII Delete Sum for Two Strings
// https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[] dp = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            dp[j] = dp[j - 1] + s2.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] += s1.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                int temp = dp[j];

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = Math.min(dp[j] + s1.charAt(i - 1), dp[j - 1] + s2.charAt(j - 1));
                }

                prev = temp;
            }
        }

        return dp[n];        
    }
}
class Solution2 {
    public int minimumDeleteSum(String s1, String s2) {
        char[] a = s1.toCharArray(); char[] b = s2.toCharArray();
        int n = a.length; int m = b.length;
        int[][] dp = new int[n + 1][m + 1];
        int total = 0;
        for(char c: a) total += (int) c;
        for(char c: b) total += (int) c;
        for(int i =  n - 1; i >= 0; i--){
            for(int j = m - 1; j >= 0; j--){
                if(a[i] == b[j]){
                    dp[i][j] += dp[i + 1][j + 1] + (int) a[i];
                } else {
                    dp[i][j] = Math.max(dp[i +1][j], dp[i][j + 1]);
                }

            }
        }
        return -dp[0][0] * 2 + total;
    }
}