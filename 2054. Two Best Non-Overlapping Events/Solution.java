// 2054. Two Best Non-Overlapping Events
// https://leetcode.com/problems/two-best-non-overlapping-events/
class Solution {
    public int maxTwoEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        int maxSingleEvent = 0;
        int[] maxUpTo = new int[events.length];
        for (int i = 0; i < events.length; i++) {
            maxSingleEvent = Math.max(maxSingleEvent, events[i][2]);
            maxUpTo[i] = maxSingleEvent;
        }

        int maxTwoEvents = 0;
        for (int i = 0; i < events.length; i++) {
            int start = events[i][0];
            int left = 0, right = i - 1, bestIndex = -1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (events[mid][1] < start) {
                    bestIndex = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            int currentSum = events[i][2];
            if (bestIndex != -1) {
                currentSum += maxUpTo[bestIndex];
            }

            maxTwoEvents = Math.max(maxTwoEvents, currentSum);
        }

        return maxTwoEvents;
    }
}

//Optimized -->
/**
class Solution {
    public int maxTwoEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        int n = events.length;
        int[] f = new int[n + 1];
        for (int i = n - 1; i >= 0; --i) {
            f[i] = Math.max(f[i + 1], events[i][2]);
        }
        int ans = 0;
        for (int[] e : events) {
            int v = e[2];
            int left = 0, right = n;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (events[mid][0] > e[1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            if (left < n) {
                v += f[left];
            }
            ans = Math.max(ans, v);
        }
        return ans;
    }
}
 */
