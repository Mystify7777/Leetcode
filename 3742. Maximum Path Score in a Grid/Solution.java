// 3742. Maximum Path Score in a Grid
// https://leetcode.com/problems/maximum-path-score-in-a-grid/
class AltSolution {
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        final int NEG = -1_000_000_000;

        int[][] prev = new int[n][k + 1];
        for (int j = 0; j < n; j++) {
            Arrays.fill(prev[j], NEG);
        }

        for (int i = 0; i < m; i++) {
            int[][] curr = new int[n][k + 1];
            for (int j = 0; j < n; j++) {
                Arrays.fill(curr[j], NEG);
            }

            for (int j = 0; j < n; j++) {
                int gain = grid[i][j];
                int need = gain > 0 ? 1 : 0;

                int limit = Math.min(k, i + j);

                if (i == 0 && j == 0) {
                    curr[0][0] = 0;
                    continue;
                }

                for (int c = need; c <= limit; c++) {
                    int best = NEG;

                    if (i > 0 && prev[j][c - need] != NEG) {
                        best = Math.max(best, prev[j][c - need] + gain);
                    }

                    if (j > 0 && curr[j - 1][c - need] != NEG) {
                        best = Math.max(best, curr[j - 1][c - need] + gain);
                    }

                    curr[j][c] = best;
                }
            }

            prev = curr;
        }

        int ans = NEG;
        for (int c = 0; c <= k; c++) {
            ans = Math.max(ans, prev[n - 1][c]);
        }

        return ans < 0 ? -1 : ans;
    }
}
// https://www.youtube.com/@0x3f
class Solution {
    public int maxPathScore(int[][] grid, int K) {
        if (minPathSum(grid) > K) {
            return -1;
        }

        int m = grid.length;
        int n = grid[0].length;
        K = Math.min(K, m + n - 2); // 至多花费 m+n-2
        int[][] f = new int[n + 1][K + 2];
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        f[1][1] = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = grid[i][j];
                for (int k = Math.min(K, i + j); k >= 0; k--) { // 从 (0,0) 到 (i,j) 至多花费 i+j
                    int newK = x > 0 ? k - 1 : k;
                    f[j + 1][k + 1] = Math.max(f[j + 1][newK + 1], f[j][newK + 1]) + x;
                }
            }
        }

        int ans = 0;
        for (int x : f[n]) {
            ans = Math.max(ans, x);
        }
        return ans;
    }

    // 64. 最小路径和
    private int minPathSum(int[][] grid) {
        int n = grid[0].length;
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[1] = 0;
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                f[j + 1] = Math.min(f[j], f[j + 1]) + Math.min(row[j], 1); // 值大于 0 的单元格花费 1
            }
        }
        return f[n];
    }
}
