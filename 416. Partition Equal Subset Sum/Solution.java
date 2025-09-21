//416. Partition Equal Subset Sum
// class Solution {
//     public boolean canPartition(int[] nums) {
//         int totalSum = 0;
//         for (int num : nums) totalSum += num;
//         if (totalSum % 2 != 0) return false;
//         int targetSum = totalSum / 2;
//         boolean[] dp = new boolean[targetSum + 1];
//         dp[0] = true;
//         for (int num : nums) {
//             for (int currSum = targetSum; currSum >= num; currSum--) {
//                 dp[currSum] = dp[currSum] || dp[currSum - num];
//                 if (dp[targetSum]) return true;
//             }
//         }
//         return dp[targetSum];
//     }
// }

class Solution {
    
public boolean canPartition(int[] nums) {
        int sum =0;
        for(int num : nums)
            sum += num;
        if(sum % 2 == 1)
            return false;
        sum /= 2;
        BitSet ans = new BitSet(sum + 1);
        ans.set(sum);
        for(int num : nums){
            if(num > sum)
                continue;
            ans.or(ans.get(num , sum+1));
            if(ans.get(0))
                return true;  
        }
        return false;
    }

}