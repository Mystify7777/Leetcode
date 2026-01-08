// 1458. Max Dot Product of Two Subsequences


class Solution {
    int[] nums1, nums2;
    int[][] memo;
    int n, m;
    final int NEG_INF = (int) -1e9;

    int dp(int i, int j) {
        if (i == n || j == m)
            return NEG_INF;

        if (memo[i][j] != Integer.MIN_VALUE)
            return memo[i][j];

        int take = nums1[i] * nums2[j];

        int res = Math.max(
            Math.max(
                take + dp(i + 1, j + 1), // take both and continue
                take                    // take and end here
            ),
            Math.max(
                dp(i + 1, j),           // skip nums1[i]
                dp(i, j + 1)            // skip nums2[j]
            )
        );

        return memo[i][j] = res;
    }

    public int maxDotProduct(int[] a, int[] b) {
        nums1 = a;
        nums2 = b;
        n = nums1.length;
        m = nums2.length;

        memo = new int[n][m];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], Integer.MIN_VALUE);

        return dp(0, 0);
    }
}

class Solution2 {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int NEG = -1_000_000_000;

        int[] dp = new int[m + 1];
        for (int j = 0; j <= m; j++) dp[j] = NEG;

        for (int i = 1; i <= n; i++) {
            int[] ndp = new int[m + 1];
            for (int j = 0; j <= m; j++) ndp[j] = NEG;

            for (int j = 1; j <= m; j++) {
                int prod = nums1[i - 1] * nums2[j - 1];
                int take = dp[j - 1] == NEG ? prod : Math.max(prod, dp[j - 1] + prod);

                int best = take;
                best = Math.max(best, dp[j]);
                best = Math.max(best, ndp[j - 1]);

                ndp[j] = best;
            }
            dp = ndp;
        }
        return dp[m];
    }
}

//why is the alternate approach fast?