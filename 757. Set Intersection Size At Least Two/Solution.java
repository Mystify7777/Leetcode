// 757. Set Intersection Size At Least Two
// https://leetcode.com/problems/set-intersection-size-at-least-two/
class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> 
            a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]
        );

        int ans = 0;
        int a = -1, b = -1;

        for (int[] it : intervals) {
            int l = it[0], r = it[1];

            if (l > b) {
                a = r - 1;
                b = r;
                ans += 2;
            } else if (l > a) {
                a = b;
                b = r;
                ans += 1;
            }
        }

        return ans;
    }
}

//wtf is this 
/**
import java.util.Arrays;

class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        int n = 0;
        long[] endStartPairs = new long[intervals.length];
        for (int[] interval : intervals) {
            endStartPairs[n] = -interval[0] & 0xFFFFFFFFL;
            endStartPairs[n++] |= (long) (interval[1]) << 32;
        }
        Arrays.sort(endStartPairs);
        int min = -2;
        int max = -1;
        int curStart;
        int curEnd;
        int res = 0;
        for (long endStartPair : endStartPairs) {
            curStart = -(int) endStartPair;
            curEnd = (int) (endStartPair >> 32);
            if (curStart <= min) {
                continue;
            }
            if (curStart <= max) {
                res += 1;
                min = max;
            } else {
                res += 2;
                min = curEnd - 1;
            }
            max = curEnd;
        }
        return res;
    }
}
 */