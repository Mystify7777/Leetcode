// 1437. Check If All 1's Are at Least Length K Places Away
//https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away
class Solution {
    public boolean kLengthApart(int[] nums, int k) {
        int lastIndex = -1;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 1) {
                if (lastIndex != -1 && i - lastIndex - 1 < k) {
                    return false;
                }
                lastIndex = i;
            }
        }

        return true;
    }
}