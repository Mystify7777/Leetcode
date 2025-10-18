// 3397. Maximum Number of Distinct Elements After Operations
// https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/
class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0, prev = (int)-1e9;

        for (int x : nums) {
            int l = Math.max(x - k, prev + 1);
            if (l <= x + k) {
                prev = l;
                ans++;
            }
        }
        return ans;
    }
}
//alternate approach

/**
class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        if (nums.length <= (k << 1) + 1) return nums.length;
        Arrays.sort(nums);
        int distinct = 0;
        int l = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int m = Math.max(l+1, nums[i]-k);
            if (m <= nums[i]+k) {
                distinct++;
                l = m;
            }
        }
        return distinct;
    }
} */