// 3225. Maximum Score From Grid Operations
// https://leetcode.com/problems/maximum-score-from-grid-operations/
class altSolution {
    public long maximumScore(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;
        if (m == 1) return 0;

        long[][] col = new long[m][n + 1];

        // prefix sum per column
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                col[j][i + 1] = col[j][i] + grid[i][j];
            }
        }

        long[][] dp = new long[n + 1][n + 1];
        long[][] prefMax = new long[n + 1][n + 1];
        long[][] suffMax = new long[n + 1][n + 1];

        for (int c = 1; c < m; c++) {

            long[][] newdp = new long[n + 1][n + 1];

            for (int curr = 0; curr <= n; curr++) {
                for (int prev = 0; prev <= n; prev++) {

                    if (curr <= prev) {
                        long gain = col[c][prev] - col[c][curr];

                        newdp[curr][prev] = Math.max(
                                newdp[curr][prev],
                                suffMax[prev][0] + gain
                        );
                    } else {
                        long gain = col[c - 1][curr] - col[c - 1][prev];

                        newdp[curr][prev] = Math.max(
                                newdp[curr][prev],
                                Math.max(
                                        suffMax[prev][curr],
                                        prefMax[prev][curr] + gain
                                )
                        );
                    }
                }
            }

            // build prefix & suffix
            for (int curr = 0; curr <= n; curr++) {

                prefMax[curr][0] = newdp[curr][0];

                for (int prev = 1; prev <= n; prev++) {

                    long penalty = 0;
                    if (prev > curr)
                        penalty = col[c][prev] - col[c][curr];

                    prefMax[curr][prev] = Math.max(
                            prefMax[curr][prev - 1],
                            newdp[curr][prev] - penalty
                    );
                }

                suffMax[curr][n] = newdp[curr][n];

                for (int prev = n - 1; prev >= 0; prev--) {
                    suffMax[curr][prev] = Math.max(
                            suffMax[curr][prev + 1],
                            newdp[curr][prev]
                    );
                }
            }

            dp = newdp;
        }

        long ans = 0;
        for (int k = 0; k <= n; k++) {
            ans = Math.max(ans, Math.max(dp[0][k], dp[n][k]));
        }

        return ans;
    }
}

class Solution {
	public long maximumScore(int[][] grid) {
		int n = grid.length;
		long[] dp1 = new long[n];
		long[] dp2 = new long[n];
		long res = 0, prev1 = 0, prev2 = 0;
        int i = 0;
		while (i < n - 1) {
			long curr = score(grid, dp1, i, prev1, 0, 1, n);
			prev1 = Math.max(res, prev2);
			prev2 = score(grid, dp2, i + 1, res, n - 1, -1, -1);
	    	res = Math.max(prev1, curr);
            i++;
		}
		return Math.max(res, prev2);
	}
    long score(int[][] grid, long[] dp, int col, long prev, int row, int dir, int stop) {
		long max = 0;
		while (row != stop) {
            max = Math.max(max, prev);
			prev = dp[row];
            max += grid[row][col];
			dp[row] = max;
            row += dir;
		}
		return max;
	}
}
