// 1877. Minimize Maximum Pair Sum in Array
// https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
class Solution {
    public int minPairSum(int[] nums) {
       // Step 1: Sort the array in ascending order
        Arrays.sort(nums);

        // Step 2: Initialize pointers at the start and end of the sorted array
        int left = 0, right = nums.length - 1;

        // Step 3: Initialize a variable to store the minimum of the maximum pair sum
        int minMaxPairSum = Integer.MIN_VALUE;

        // Step 4: Iterate until the pointers meet
        while (left < right) {
            // Step 5: Calculate the current pair sum
            int currentPairSum = nums[left] + nums[right];

            // Step 6: Update the minimum of the maximum pair sum
            minMaxPairSum = Math.max(minMaxPairSum, currentPairSum);

            // Step 7: Move the pointers towards the center
            left++;
            right--;
        }

        // Step 8: Return the minimum of the maximum pair sum
        return minMaxPairSum;
    }
}

class Solution2 {
    public int minPairSum(int[] nums) {
        int minMax = 0;

        for(int n:nums){
            minMax = Math.max(n,minMax);
        }

        int[] arr = new int[minMax+1];
        for(int n:nums){
            arr[n]++;
        }

        int l = 0, r = minMax, ans = 0;
        while (l < r) {
            while (arr[l] == 0)
                l++;
            while (arr[r] == 0)
                r--;
            ans = Math.max(ans, l + r);
            if (arr[r] > arr[l])
                arr[r] -= arr[l++];
            else if (arr[l] > arr[r])
                arr[l] -= arr[r--];
            else {
                l++;
                r--;
            }
        }

        return ans;
    }
}