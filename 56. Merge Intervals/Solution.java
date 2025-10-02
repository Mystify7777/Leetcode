// 56. Merge Intervals
import java.util.*;
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        int[] prev = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] <= prev[1]) {
                prev[1] = Math.max(prev[1], interval[1]);
            } else {
                merged.add(prev);
                prev = interval;
            }
        }

        merged.add(prev);

        return merged.toArray(new int[merged.size()][]);         
    }
}
/**
// class Solution {
//     public int[][] merge(int[][] intervals) {
//         int n = intervals.length; // size of the array

//         // Step 1: sort the given intervals by starting time
//         Arrays.sort(intervals, new Comparator<int[]>() {
//             public int compare(int[] a, int[] b) {
//                 return a[0] - b[0];
//             }
//         });

//         // Step 2: store result in a list
//         List<int[]> ans = new ArrayList<>();

//         for (int i = 0; i < n; i++) {
//             // If ans is empty OR current interval does not overlap
//             if (ans.isEmpty() || intervals[i][0] > ans.get(ans.size() - 1)[1]) {
//                 ans.add(new int[]{intervals[i][0], intervals[i][1]});
//             }
//             // If current interval overlaps with the last one in ans
//             else {
//                 ans.get(ans.size() - 1)[1] = Math.max(ans.get(ans.size() - 1)[1], intervals[i][1]);
//             }
//         }

//         // Step 3: convert List<int[]> → int[][]
//         return ans.toArray(new int[ans.size()][]);
//     }
// }
class Solution {
public int[][] merge(int[][] intervals) {
        int max = 0;
        for (int i = 0; i < intervals.length; i++) {
            max = Math.max(intervals[i][0], max);
        }

        int[] mp = new int[max + 1];
        for (int i = 0; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            mp[start] = Math.max(end + 1, mp[start]);
        }

        int r = 0;
        int have = -1;
        int intervalStart = -1;
        for (int i = 0; i < mp.length; i++) {
            if (mp[i] != 0) {
                if (intervalStart == -1) intervalStart = i;
                have = Math.max(mp[i] - 1, have);
            }
            if (have == i) {
                intervals[r++] = new int[] { intervalStart, have };
                have = -1;
                intervalStart = -1;
            }
        }

        if (intervalStart != -1) {
            intervals[r++] = new int[] { intervalStart, have };
        }
        if (intervals.length == r) {
            return intervals;
        }

        int[][] res = new int[r][];
        for (int i = 0; i < r; i++) {
            res[i] = intervals[i];
        }

        return res;
    }
}
 */