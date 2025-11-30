//1590. Make Sum Divisible by P
//https://leetcode.com/problems/make-sum-divisible-by-p/
class Solution {
    public int minSubarray(int[] nums, int p) {
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Find remainder when total sum is divided by p
        int rem = (int)(totalSum % p);
        if (rem == 0) return 0; // If remainder is 0, no subarray needs to be removed

        HashMap<Integer, Integer> prefixMod = new HashMap<>();
        prefixMod.put(0, -1);  // Initialize to handle full prefix
        long prefixSum = 0;
        int minLength = nums.length;

        for (int i = 0; i < nums.length; ++i) {
            prefixSum += nums[i];
            int currentMod = (int)(prefixSum % p);
            int targetMod = (currentMod - rem + p) % p;

            if (prefixMod.containsKey(targetMod)) {
                minLength = Math.min(minLength, i - prefixMod.get(targetMod));
            }

            prefixMod.put(currentMod, i);
        }

        return minLength == nums.length ? -1 : minLength;
    }
}
//alternate approach
/**

class Solution {
    public int minSubarray(int[] nums, int p) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < p)
            return -1;
        long target = sum % p;
        if (target == 0)
            return 0;
        HashMap<Integer, Integer> HM = new HashMap<>();
        HM.put(0, -1);
        sum = 0;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % p == target)
                return 1;
            sum += nums[i];
            int a = (int) (sum % p);
            int b = (int) ((sum - target) % p);
            if (HM.containsKey(b))
                res = Math.min(i - HM.get(b), res);
            HM.put(a, i);
        }
        if (res >= nums.length)
            return -1;
        return res;
    }
} */