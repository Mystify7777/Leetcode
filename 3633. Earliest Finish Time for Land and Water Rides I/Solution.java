// 3633. Earliest Finish Time for Land and Water Rides I
// https://leetcode.com/problems/earliest-finish-time-for-land-and-water-rides-i/
class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int n = landStartTime.length;
        int m = waterStartTime.length;
        int min=3000,minW=min,res=minW;
        for(int i=0;i<n;i++){
            min=Math.min(min,landStartTime[i]+landDuration[i]);
        }
        for(int i=0;i<m;i++){
            minW=Math.min(minW,waterStartTime[i]+waterDuration[i]);
            res=Math.min(res,Math.max(min,waterStartTime[i])+waterDuration[i]);
        }
        for(int i=0;i<n;i++){
            res=Math.min(res,Math.max(minW,landStartTime[i])+landDuration[i]);
        }
        return res;
    }
}