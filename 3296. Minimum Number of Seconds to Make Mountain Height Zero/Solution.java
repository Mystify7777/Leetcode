// 3296. Minimum Number of Seconds to Make Mountain Height Zero
// https://leetcode.com/problems/minimum-number-of-seconds-to-make-mountain-height-zero/
class Solution {
    public long minNumberOfSeconds(int height, int[] times) {
        long lo = 1, hi = 10000000000000000L;

        while (lo < hi) {
            long mid = (lo + hi) >> 1;
            long tot = 0;
            for (int i = 0; i < times.length && tot < height; i++)
                tot += (long) (Math.sqrt((double) mid / times[i] * 2 + 0.25) - 0.5);
            if (tot >= height)
                hi = mid;
            else
                lo = mid + 1;
        }

        return lo;
    }
}

class Solution2 {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {

        int max = 0;
        for (int x : workerTimes) 
            max = Math.max(max, x);

        int h = (mountainHeight-1) / workerTimes.length + 1;
        long left = 1, right = (long) max * h * (h + 1) / 2;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (check(mountainHeight, workerTimes, mid)) right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    boolean check(int mountainHeight, int[] workerTimes, long time) {
        for (int x : workerTimes) {
            mountainHeight -= (int)(-1 + Math.sqrt(1 + 8 * time / x)) / 2;
            if (mountainHeight <= 0) return true;
        }
        return false;
    }
}