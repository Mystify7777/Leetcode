// https://leetcode.com/problems/find-the-number-of-subsequences-with-equal-gcd/
// 3336. Find the Number of Subsequences With Equal GCD
class Solution {
    private static final int MOD = 1_000_000_007;
    private int n;
    private int[][][] dp;

    private int solve(int idx, int g1, int g2, int[] nums) {
        if (idx == n) {
            return (g1 != 0 && g1 == g2) ? 1 : 0;
        }

        if (dp[idx][g1][g2] != -1)
            return dp[idx][g1][g2];

        long ans = 0;

        // Ignore current element
        ans = solve(idx + 1, g1, g2, nums);

        // Put in seq1
        int ng1 = (g1 == 0) ? nums[idx] : gcd(g1, nums[idx]);
        ans = (ans + solve(idx + 1, ng1, g2, nums)) % MOD;

        // Put in seq2
        int ng2 = (g2 == 0) ? nums[idx] : gcd(g2, nums[idx]);
        ans = (ans + solve(idx + 1, g1, ng2, nums)) % MOD;

        return dp[idx][g1][g2] = (int) ans;
    }

    public int subsequencePairCount(int[] nums) {
        n = nums.length;
        dp = new int[n + 1][201][201];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 200; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        return solve(0, 0, 0, nums);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}

// https://www.youtube.com/@0x3f
class Solution2 {
    private static final int MOD = 1_000_000_007;
    private static final int MX = 201;

    private static final int[][] lcms = new int[MX][MX];
    private static final int[] pow2 = new int[MX];
    private static final int[] pow3 = new int[MX];
    private static final int[] mu = new int[MX];
    private static boolean initialized = false;

    // 这样写比 static block 快
    public Solution() {
        if (initialized) {
            return;
        }
        initialized = true;

        for (int i = 1; i < MX; i++) {
            for (int j = 1; j < MX; j++) {
                lcms[i][j] = lcm(i, j);
            }
        }

        pow2[0] = pow3[0] = 1;
        for (int i = 1; i < MX; i++) {
            pow2[i] = pow2[i - 1] * 2 % MOD;
            pow3[i] = (int) ((long) pow3[i - 1] * 3 % MOD);
        }

        mu[1] = 1;
        for (int i = 1; i < MX; i++) {
            for (int j = i * 2; j < MX; j += i) {
                mu[j] -= mu[i];
            }
        }
    }

    public int subsequencePairCount(int[] nums) {
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }

        // cnt[i] 表示 nums 中的 i 的倍数的个数
        int[] cnt = new int[m + 1];
        for (int x : nums) {
            cnt[x]++;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = i * 2; j <= m; j += i) {
                cnt[i] += cnt[j]; // 统计 i 的倍数的个数
            }
        }

        int[][] f = new int[m + 1][m + 1];
        for (int g1 = 1; g1 <= m; g1++) {
            for (int g2 = 1; g2 <= m; g2++) {
                int l = lcms[g1][g2];
                int c = l <= m ? cnt[l] : 0;
                int c1 = cnt[g1];
                int c2 = cnt[g2];
                f[g1][g2] = (int) (((long) pow3[c] * pow2[c1 + c2 - c * 2] - pow2[c1] - pow2[c2] + 1) % MOD);
            }
        }

        // 倍数容斥
        long ans = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m / i; j++) {
                for (int k = 1; k <= m / i; k++) {
                    ans += mu[j] * mu[k] * f[j * i][k * i];
                }
            }
        }
        return (int) ((ans % MOD + MOD) % MOD); // 保证 ans 非负
    }

    private static int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    private static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }
}