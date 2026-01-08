// 279. Perfect Squares
public class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        int count = 1;
        while (count * count <= n) {
            int sq = count * count;
            for (int i = sq; i <= n; i++) {
                dp[i] = Math.min(dp[i - sq] + 1, dp[i]);
            }
            count++;
        }
        return dp[n];
    }
}

class Solution2 {
    private boolean validSquare(int n) {
        int root = (int) Math.sqrt(n);
        return root * root == n;
    }
     public int numSquares(int n) {
     if (n < 4) {
            return n;
        }
        if (validSquare(n)) {
            return 1;
        }
        while ((n & 3) == 0) {
            n >>= 2;
        }
        if ((n & 7) == 7) {
            return 4;
        }
        int x = (int) Math.sqrt(n);
        for (int i = 1; i <= x; i++) {
            if (validSquare(n - i * i)) {
                return 2;
            }
        }
        return 3;
    }
}