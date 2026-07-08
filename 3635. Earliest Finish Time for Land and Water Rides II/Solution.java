// 3635. Earliest Finish Time for Land and Water Rides II
// https://leetcode.com/problems/earliest-finish-time-for-land-and-water-rides-ii/
class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {

        return Math.min(calculate(landStartTime, landDuration, waterStartTime, waterDuration), calculate(waterStartTime, waterDuration, landStartTime, landDuration));
    }

    private int calculate(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int plan=Integer.MAX_VALUE, minTime=Integer.MAX_VALUE, waterMinTime=Integer.MAX_VALUE;

        for(int i=0;i<landStartTime.length;i++) {
            minTime = Math.min(minTime, landStartTime[i]+landDuration[i]);
        }
        for(int i=0;i<waterStartTime.length;i++) {
            plan = Math.min(plan, Math.max(minTime, waterStartTime[i])+waterDuration[i]);
        }
        return plan;
    }
}