// 2770. Maximum Number of Jumps to Reach the Last Index
// https://leetcode.com/problems/maximum-number-of-jumps-to-reach-the-last-index/
class Solution1 {
    public int maximumJumps(int[] nums, int target) {

        int n = nums.length;

        // dp[i] stores maximum jumps to reach index i
        int[] dp = new int[n];

        // Mark all indices unreachable
        Arrays.fill(dp, -1);

        // Starting index needs 0 jumps
        dp[0] = 0;

        for(int i = 1; i < n; i++) {

            // Check all previous indices
            for(int j = 0; j < i; j++) {

                // Valid jump and previous index reachable
                if(Math.abs(nums[i] - nums[j]) <= target && dp[j] != -1) {

                    // Update maximum jumps
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }
}
// Top-Down DP + Memoization Approach

class Solution2 {

    int n;

    // Recursive function to find maximum jumps from index i
    public int solve(int i, int[] nums, int target, int[] dp) {

        // If we reach last index
        if(i == n - 1)
            return 0;

        // Return already computed result
        if(dp[i] != -2)
            return dp[i];

        int ans = -1;

        // Try all possible next jumps
        for(int j = i + 1; j < n; j++) {

            // Check jump condition
            if(Math.abs(nums[j] - nums[i]) <= target) {

                int temp = solve(j, nums, target, dp);

                // Update maximum jumps
                if(temp != -1) {
                    ans = Math.max(ans, 1 + temp);
                }
            }
        }

        // Store and return answer
        return dp[i] = ans;
    }

    public int maximumJumps(int[] nums, int target) {

        n = nums.length;

        // -2 means not calculated yet
        int[] dp = new int[n];
        Arrays.fill(dp, -2);

        return solve(0, nums, target, dp);
    }
}
class Solution {
    public int maximumJumps(int[] nums, int target) {
        if(nums.length==2){
            int dif = nums[1]-nums[0];
                if((-1*target)<=dif && dif<=target){
                    return 1;
                }
                else{
                    return -1;
                }
        }
        int dp[]= new int[nums.length];
        Arrays.fill(dp,Integer.MIN_VALUE);
        dp[0]=0;
        for(int i=0;i<nums.length-1;i++){
            if(dp[i]==Integer.MIN_VALUE) continue;
            for(int j=i+1;j<nums.length;j++){
                int dif = nums[j]-nums[i];
                if((-1*target)<=dif && dif<=target){
                    dp[j]=Math.max(1+dp[i],dp[j]);
                }
            }
        }
        return dp[nums.length-1]==Integer.MIN_VALUE?-1:dp[nums.length-1];
    }
}
