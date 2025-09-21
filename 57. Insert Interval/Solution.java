// 57. Insert Interval
/*
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
       List<int[]> intervalList = new ArrayList<>(Arrays.asList(intervals));
        intervalList.add(newInterval);
        Collections.sort(intervalList, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> res = new ArrayList<>();
        int[] current = intervalList.get(0);

        for (int i = 1; i < intervalList.size(); i++) {
            int[] interval = intervalList.get(i);
            
            if (current[1] >= interval[0]) {
                current[1] = Math.max(current[1], interval[1]);
            } else {
                res.add(current);
                current = interval;
            }
        }

        res.add(current);
        return res.toArray(new int[res.size()][]);
    }
}
*/
import java.util.*;
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {

        int n = intervals.length , i = 0;
        List<int[]> store = new ArrayList<>();

        while(i < intervals.length && intervals[i][1] < newInterval[0])
        {
            store.add(intervals[i++]);
        }

        while(i<n && newInterval[1] >= intervals[i][0]){

            newInterval[0] = Math.min(newInterval[0] , intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1] , intervals[i][1]); 
            i++;
        }

        store.add(newInterval);

        while(i<n)
        {
            store.add(intervals[i++]);
        }

        int[][] merged = new int[store.size()][2];

        for(int j = 0 ; j<store.size() ; j++)
        {
            merged[j] = store.get(j);
        }

        return merged;
        
    }
}