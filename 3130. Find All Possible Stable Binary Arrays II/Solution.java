// 3130. Find All Possible Stable Binary Arrays II
// https://leetcode.com/problems/find-all-possible-stable-binary-arrays-ii/
class Solution {

    public int numberOfStableArrays(int zero, int one, int limit) {

        int MOD = 1_000_000_007;

        long[][][] dp = new long[zero+1][one+1][2];

        for(int i=1;i<=Math.min(zero,limit);i++)
            dp[i][0][0]=1;

        for(int j=1;j<=Math.min(one,limit);j++)
            dp[0][j][1]=1;

        for(int i=1;i<=zero;i++)
        for(int j=1;j<=one;j++){

            long over0 = (i-limit>=1)?dp[i-limit-1][j][1]:0;
            long over1 = (j-limit>=1)?dp[i][j-limit-1][0]:0;

            dp[i][j][0] =
            (dp[i-1][j][0]+dp[i-1][j][1]-over0+MOD)%MOD;

            dp[i][j][1] =
            (dp[i][j-1][0]+dp[i][j-1][1]-over1+MOD)%MOD;
        }

        return (int)((dp[zero][one][0]+dp[zero][one][1])%MOD);
    }
}

class Solution2 {
	private static final int MODULUS = (int) 1e9 + 7;
	private static final long MOD_LONG = MODULUS;

	private static int add(int x, int y) {
		return (x + y) % MODULUS;
	}

	private static int subtract(int x, int y) {
		return (x + MODULUS - y) % MODULUS;
	}

	private static int multiply(int x, int y) {
		return (int) ((long) x * y % MOD_LONG);
	}

	public static int numberOfStableArrays(int zero, int one, int limit) {
		int m = Math.max(zero, one);
		int[][] sumlim = new int[m + 1][m + 1]; // [count][sum]
		int[] ps = new int[m + 1];
		int[] sl = sumlim[0];
		sl[0] = 1;
		for (int c = 1; c <= m; c++) {
			int e = Math.min(c * limit, m);
			int s = 0;
			for (int i = 0; i <= e; i++) {
				ps[i] = s;
				s = add(s, sl[i]);
			}
			sl = sumlim[c];
			for (int i = c; i <= e; i++)
				sl[i] = i < limit ? ps[i] : subtract(ps[i], ps[i - limit]);
		}
		int result = m <= limit ? 2 : 0;
		int c0 = sumlim[1][zero];
		int c1 = sumlim[1][one];
		for (int i = 2; i <= m; i++) {
			int c0p = c0;
			int c1p = c1;
			c0 = sumlim[i][zero];
			c1 = sumlim[i][one];
			result = add(result, multiply(c0, c1p));
			result = add(result, multiply(c1, c0p));
			int t = multiply(c0, c1);
			result = add(result, add(t, t));
		}
		return result;
	}
}