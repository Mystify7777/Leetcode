// 3689. Maximum Total Subarray Value I
// https://leetcode.com/problems/maximum-total-subarray-value-i/

class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int num : nums) {
            max = Math.max(num, max);
            min = Math.min(num, min);
        }
        return (long) k * (max - min);
    }
}