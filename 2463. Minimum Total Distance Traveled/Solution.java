// 2463. Minimum Total Distance Traveled
// https://leetcode.com/problems/minimum-total-distance-traveled/
class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);

        int n = robot.size();
        int m = factory.length;

        long INF = (long)1e18;
        long[][] dp = new long[n + 1][m + 1];

        for (int i = 0; i <= n; i++)
            Arrays.fill(dp[i], INF);

        for (int j = 0; j <= m; j++)
            dp[0][j] = 0;

        for (int j = 1; j <= m; j++) {
            int pos = factory[j - 1][0];
            int limit = factory[j - 1][1];

            for (int i = 0; i <= n; i++) {
                dp[i][j] = dp[i][j - 1];

                long dist = 0;
                for (int k = 1; k <= limit && i - k >= 0; k++) {
                    dist += Math.abs(robot.get(i - k) - pos);
                    dp[i][j] = Math.min(dp[i][j], dp[i - k][j - 1] + dist);
                }
            }
        }

        return dp[n][m];
    }
}
class Solution2 {
    public long minimumTotalDistance(List<Integer> r, int[][] f) {
        Collections.sort(r);
        Arrays.sort(f, (x, y) -> x[0] - y[0]);
        int R = r.size();
        int F = f.length;
        long[][] dp = new long[R + 1][F + 1];
        for (int i = 0; i < R; i++) dp[i][F] = Long.MAX_VALUE / 4;
        for (int j = F - 1; j >= 0; j--) {
            long distSum = 0;
            ArrayDeque<long[]> dq = new ArrayDeque<>();
            dq.addLast(new long[]{R, 0});
            for (int i = R - 1; i >= 0; i--) {
                distSum += Math.abs(r.get(i) - f[j][0]);
                while (!dq.isEmpty() && dq.peekFirst()[0] > i + f[j][1])
                    dq.pollFirst();
                long val = dp[i][j + 1] - distSum;
                while (!dq.isEmpty() && dq.peekLast()[1] >= val)
                    dq.pollLast();
                dq.addLast(new long[]{i, val});
                dp[i][j] = dq.peekFirst()[1] + distSum;
            }
        }
        return dp[0][0];
    }
}