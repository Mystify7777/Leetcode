// 1984. Minimum Difference Between Highest and Lowest of K Scores
//https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
class Solution {
    public int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = nums[k - 1] - nums[0];
        for (int i = 0; i + k <= n; i++) {
            ans = Math.min(ans, nums[i + k - 1] - nums[i]);
        }
        return ans;
    }
}

class Solution2 {
    public int minimumDifference(int[] nums, int k) {
        if (nums.length == 1 || k == 1) return 0;
        quickSort(nums, 0, nums.length - 1);
        int minDifference = Integer.MAX_VALUE;
        for (int i = k - 1; i < nums.length; i++) {
            minDifference = Math.min(minDifference, nums[i] - nums[i - k + 1]);
        }
        return minDifference;
    }

    public void quickSort(int[] nums, int start, int end) {
        int left = start;
        int right = end;
        int pivot = nums[start];

        while (left <= right) {
            while (left <= right && nums[left] < pivot) {
                left++;
            }

            while (left <= right && nums[right] > pivot) {
                right--;
            }

            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }

        if (right > start) {
            quickSort(nums, start, right);
        }

        if (left < end) {
            quickSort(nums, left, end);
        }
    }
}