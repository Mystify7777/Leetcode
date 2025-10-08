// 72. Edit Distance
//https://leetcode.com/problems/edit-distance/
class Solution {
  public int minDistance(String word1, String word2) {
    final int m = word1.length();//first word length
    final int n = word2.length();///second word length
    // dp[i][j] := min # of operations to convert word1[0..i) to word2[0..j)
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; ++i)
      dp[i][0] = i;

    for (int j = 1; j <= n; ++j)
      dp[0][j] = j;

    for (int i = 1; i <= m; ++i)
      for (int j = 1; j <= n; ++j)
        if (word1.charAt(i - 1) == word2.charAt(j - 1))//same characters
          dp[i][j] = dp[i - 1][j - 1];//no operation
        else
          dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;                      //replace               //delete        //insert

    return dp[m][n];
  }
}
/***
class Solution {
    int[][] dp;
    public int minDistance(String word1, String word2) {
        int s1Len = word1.length();
        int s2Len = word2.length();
        dp = new int[s1Len][s2Len];
        for (int i=0; i < s1Len; i++) {
            Arrays.fill(dp[i], -1);
        }
        return checkDistance(word1, word2, s1Len-1, s2Len-1);
    }

    private int checkDistance(String word1, String word2, int i, int j) {
        if (i == -1) return j+1;
        if (j == -1) return i+1;
        if (dp[i][j] != -1) return dp[i][j];

        if (word1.charAt(i) == word2.charAt(j))
            return dp[i][j] = checkDistance(word1, word2, i-1, j-1);
        else return dp[i][j] = 1 + Math.min(
            checkDistance(word1, word2, i, j - 1),
            Math.min(checkDistance(word1, word2, i-1, j),
            checkDistance(word1, word2, i-1, j-1)
        ));
    }
}
 */