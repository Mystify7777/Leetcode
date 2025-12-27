// 2402. Meeting Rooms III
class Solution {
    public int mostBooked(int n, int[][] meetings) {
        int[] count = new int[n];
        long[] busy = new long[n];        
        
        int res = 0;
        int max = 0;

        Arrays.sort(meetings, (a, b) -> (a[0] - b[0]));

        for (int i = 0; i < meetings.length; i++) {
            int start = meetings[i][0];
            int end = meetings[i][1];

            long earliest = Long.MAX_VALUE;
            int roomIndex = -1;
            boolean assigned = false;

            for (int j = 0; j < n; j++) {
                if (busy[j] < earliest) {
                    earliest = busy[j];
                    roomIndex = j;
                }

                if (busy[j] <= start) {
                    busy[j] = end;
                    count[j]++;
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                busy[roomIndex] += (end - start);
                count[roomIndex]++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (count[i] > max) {
                max = count[i];
                res = i;
            }
        }

        return res;
    }
}

//another implementation
/**
class Solution {
    public int mostBooked(int n, int[][] meetings) {
        int[] count = new int[n];       // Count of meetings per room
        long[] busy = new long[n];      // When each room becomes free

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        for (int[] meeting : meetings) {
            int start = meeting[0], end = meeting[1];
            long earliest = Long.MAX_VALUE;
            int roomIndex = -1;
            boolean assigned = false;

            for (int i = 0; i < n; i++) {
                if (busy[i] < earliest) {
                    earliest = busy[i];
                    roomIndex = i;
                }
                if (busy[i] <= start) {
                    busy[i] = end;
                    count[i]++;
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                busy[roomIndex] += (end - start);
                count[roomIndex]++;
            }
        }

        int max = 0, res = 0;
        for (int i = 0; i < n; i++) {
            if (count[i] > max) {
                max = count[i];
                res = i;
            }
        }
        return res;
    }
}
 */

//  arent both same?  Why is the 2nd one faster then?