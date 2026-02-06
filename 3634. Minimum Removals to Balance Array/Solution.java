// https://leetcode.com/problems/minimum-removals-to-balance-array/
// 3634. Minimum Removals to Balance Array
class Solution2 {
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums); // Sort array to maintain order
        int n = nums.length;
        int maxSize = 0;
        int left = 0;

        // Use sliding window to find longest valid subarray
        for (int right = 0; right < n; right++) {
            // While condition is violated, shrink window from left
            while (nums[right] > (long) k * nums[left]) left++;
            maxSize = Math.max(maxSize, right - left + 1);
        }
        // Return number of elements to remove
        return n - maxSize;
    }
}

class Solution {
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums);
        int i = 0;
        int maxLen = 0;

        for (int j = 0; j < nums.length; j++) {
            while ((long) nums[j] > (long) nums[i] * k) {
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }

        return nums.length - maxLen;
    }
}