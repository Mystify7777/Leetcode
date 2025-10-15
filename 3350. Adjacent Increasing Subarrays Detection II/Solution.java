// 3350. Adjacent Increasing Subarrays Detection II
//https://leetcode.com/problems/adjacent-increasing-subarrays-detection-ii/
class Solution {
      public int maxIncreasingSubarrays(List<Integer> A) {
        int n = A.size(), up = 1, pre_max_up = 0, res = 0;
        for (int i = 1; i < n; ++i) {
            if (A.get(i) > A.get(i - 1)) {
                up++;
            } else {
                pre_max_up = up;
                up = 1;
            }
            res = Math.max(res, Math.max(up / 2, Math.min(pre_max_up, up)));
        }
        return res;
    }
}
/**
class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int ans = 0, pre = 0, cur = 0;
        int n = nums.size();
        for (int i = 0; i < n; ++i) {
            ++cur;
            if (i == n - 1 || nums.get(i) >= nums.get(i + 1)) {
                ans = Math.max(ans, Math.max(cur / 2, Math.min(pre, cur)));
                pre = cur;
                cur = 0;
            }
        }
        return ans;
    }
} */

/**
class Solution {
    public int maxIncreasingSubarrays(List<Integer> list) {
        Integer[] nums = list.toArray(Integer[]::new);
        int n = nums.length, i = 0, res = 0;
        int prev = 0;

        while (i < n) {
            int start = i;

            while (i + 1 < n && nums[i] < nums[i + 1]) {
                i++;
            }

            int curr = i - start + 1;
            res = Math.max(res, Math.max(curr / 2, Math.min(curr, prev)));
            
            prev = curr;
            i++;
        }
        
        return res;
    }
}
 */