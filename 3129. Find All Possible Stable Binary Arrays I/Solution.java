// 3129. Find All Possible Stable Binary Arrays I
// https://leetcode.com/problems/find-all-possible-stable-binary-arrays-i/
class Solution {
    public int numberOfStableArrays(int zero, int one, int limit) {
        final int mod = 1_000_000_007;
        int L = limit + 1;

        // Swapping 0 and 1 doesn't change the count, but can reduce memory (O(one*L))
        if (one > zero) {
            int tmp = zero; zero = one; one = tmp;
        }

        // First row
        int[] dp0_prev = new int[one + 1];
        int[] dp1_prev = new int[one + 1];
        for (int j = 1; j <= Math.min(one, limit); ++j) dp1_prev[j] = 1;

        // Store last L = limit + 1 dp1 rows to access dp1[i-L][j]
        ArrayDeque<int[]> dp1q = new ArrayDeque<>();
        dp1q.addLast(dp1_prev.clone());

        for (int i = 1; i <= zero; ++i) {
            int[] dp0 = new int[one + 1];
            int[] dp1 = new int[one + 1];
            if (i <= limit) dp0[0] = 1;

            int[] old1 = (i >= L) ? dp1q.peekFirst() : null;

            for (int j = 1; j <= one; ++j) {
                dp0[j] = (dp0_prev[j] + dp1_prev[j] - (i >= L ? dp1q.peekFirst()[j] : 0)) % mod;
                dp1[j] = (dp1[j - 1] + dp0[j - 1] - (j >= L ? dp0[j - L] : 0)) % mod;

                // Fix negatives
                dp0[j] = (dp0[j] + mod) % mod;
                dp1[j] = (dp1[j] + mod) % mod;
            }

            dp1q.addLast(dp1.clone());
            if (dp1q.size() > L) dp1q.removeFirst();

            dp0_prev = dp0;
            dp1_prev = dp1;
        }

        return (dp0_prev[one] + dp1_prev[one]) % mod;
    }
}

class Solution2 {
    public int numberOfStableArrays(int zero, int one, int limit) {
        final int mod = 1_000_000_007;
        int L = limit + 1;

        int[][] dp0 = new int[zero + 1][one + 1]; // i 0s + j 1s ending with 0
        int[][] dp1 = new int[zero + 1][one + 1]; // i 0s + j 1s ending with 1

        // Base cases: only zeros or only ones => only one string if len <= min(limit, zero/one)
        for (int i = 1; i <= Math.min(zero, limit); ++i) dp0[i][0] = 1;
        for (int j = 1; j <= Math.min(one, limit); ++j) dp1[0][j] = 1;

        // DP iterations
        for (int i = 1; i <= zero; ++i) {
            for (int j = 1; j <= one; ++j) {
                dp0[i][j] = (dp0[i - 1][j] + dp1[i - 1][j] - (i >= L ? dp1[i - L][j] : 0)) % mod;
                dp1[i][j] = (dp1[i][j - 1] + dp0[i][j - 1] - (j >= L ? dp0[i][j - L] : 0)) % mod;

                // Fix negatives
                dp0[i][j] = (dp0[i][j] + mod) % mod;
                dp1[i][j] = (dp1[i][j] + mod) % mod;
            }
        }

        return (dp0[zero][one] + dp1[zero][one]) % mod;
    }
}