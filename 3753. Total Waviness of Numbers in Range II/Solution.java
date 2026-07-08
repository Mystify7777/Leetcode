// 3753. Total Waviness of Numbers in Range II
// https://leetcode.com/problems/total-waviness-of-numbers-in-range-ii/
class Solution {
    public long solve(long x) {
        long ans = 0;
        for (long left = x / 10, right = x % 10, p10 = 1; left >= 10; ) {
            int d = (int)(left % 10);
            int dl = (int)(left / 10 % 10);
            int dr = (int)(right / p10);
            left /= 10;

            // stage 1: cycle right digit only
            boolean peak = d > dl && d > dr;
            boolean valley = d < dl && d < dr;
            if (valley) {
                ans += right - p10 * (d + 1);
            }
            if (peak) {
                ans += right;
            }
            if (d > dl && d <= dr) {
                ans += p10 * d;
            }

            // stage 2: cycle right digit with center digit in [0, d-1]
            int up = 9 - Math.min(d, dl);
            ans += (45 - up * (up + 1) / 2) * p10;
            if (d > dl)
                ans += (d * (d - 1) / 2 - dl * (dl + 1) / 2) * p10;

            // stage 3: cycle right digit and center digit with left digit in
            // [0, dl-1]
            int upl = 9 - dl;
            ans += (dl * 90 - dl * (dl + 1) * (dl - 1) / 6 - 9 * 10 * 11 / 6 +
                    upl * (upl + 1) * (upl + 2) / 6) *
                   p10;
            if (left < 10)
                ans -= 45 * p10;

            // stage 4: cycle all three digits
            ans += (900 - 9 * 10 * 11 / 3) * (left / 10) * p10;
            if (left >= 10)
                ans -= 45 * p10;

            p10 *= 10;
            right += d * p10;
        }
        return ans;
    }

    public long totalWaviness(long num1, long num2) {
        return solve(num2 + 1) - solve(num1);
    }
}


class Solution2 {
    private Long[][][][][] memoCount;
    private Long[][][][][] memoWaviness;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long num) {
        if (num < 100) {
            return 0;
        }
        String s = String.valueOf(num);
        int n = s.length();
        memoCount = new Long[n][2][2][11][11];
        memoWaviness = new Long[n][2][2][11][11];
        return dp(0, 1, 0, 10, 10, s)[1];
    }

    private long[] dp(int idx, int tight, int isStarted, int lastDigit, int secLastDigit, String s) {
        if (idx == s.length()) {
            return new long[]{isStarted == 1 ? 1 : 0, 0};
        }
        if (memoCount[idx][tight][isStarted][lastDigit][secLastDigit] != null) {
            return new long[]{
                memoCount[idx][tight][isStarted][lastDigit][secLastDigit],
                memoWaviness[idx][tight][isStarted][lastDigit][secLastDigit]
            };
        }

        long totalCount = 0;
        long totalWaviness = 0;
        int limit = tight == 1 ? s.charAt(idx) - '0' : 9;

        for (int d = 0; d <= limit; d++) {
            int nextTight = (tight == 1 && d == limit) ? 1 : 0;
            int nextStarted = (isStarted == 1 || d > 0) ? 1 : 0;
            
            int nextLast = 10;
            int nextSecLast = 10;
            int wavinessContribution = 0;

            if (nextStarted == 1) {
                nextLast = d;
                nextSecLast = lastDigit;
                if (secLastDigit != 10 && lastDigit != 10) {
                    if ((lastDigit > secLastDigit && lastDigit > d) || (lastDigit < secLastDigit && lastDigit < d)) {
                        wavinessContribution = 1;
                    }
                }
            }

            long[] res = dp(idx + 1, nextTight, nextStarted, nextLast, nextSecLast, s);
            totalCount += res[0];
            totalWaviness += res[1] + wavinessContribution * res[0];
        }

        memoCount[idx][tight][isStarted][lastDigit][secLastDigit] = totalCount;
        memoWaviness[idx][tight][isStarted][lastDigit][secLastDigit] = totalWaviness;
        return new long[]{totalCount, totalWaviness};
    }
}