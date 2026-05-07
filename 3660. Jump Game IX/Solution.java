// 3660. Jump Game IX
// https://leetcode.com/problems/jump-game-ix/
class Solution {
    public int[] maxValue(int[] nums) {
        int n = nums.length;

        int[] pre = new int[n];
        int[] suf = new int[n];
        int[] res = new int[n];

        // prefix max
        pre[0] = nums[0];
        for (int i = 1; i < n; i++) {
            pre[i] = Math.max(pre[i - 1], nums[i]);
        }

        // suffix min
        suf[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = Math.min(suf[i + 1], nums[i]);
        }

        res[n - 1] = pre[n - 1];

        // build answer
        for (int i = n - 2; i >= 0; i--) {

            // merge segment
            if (pre[i] > suf[i + 1]) {
                res[i] = res[i + 1];
            }

            // new segment
            else {
                res[i] = pre[i];
            }
        }

        return res;
    }
}
class Solution2 {
    public int[] maxValue(int[] nums) {
        int n = nums.length, min = Integer.MAX_VALUE;
        int[] max = new int[n], ans = new int[n];
        max[0] = nums[0];
        for(int i = 1; i < n; i++) max[i] = Math.max(max[i - 1], nums[i]);

        for(int i = n - 2; i >= 0; i--) {
            min = Math.min(min, nums[i + 1]);
            if(min < max[i]) max[i] = max[i + 1];
        }
        return max;
    }
}

class Solution3 {
    public int[] maxValue(int[] nums) {
        int n = nums.length;
        if (n == 0) 
            return new int[0];

        int[] pre = new int[n];
        int[] suff = new int[n];
        int[] res = new int[n];

        pre[0] = nums[0];
        for (int i = 1; i < n; i++) 
            pre[i] = Math.max(nums[i], pre[i - 1]);

        suff[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) 
            suff[i] = Math.min(nums[i], suff[i + 1]);

        res[n - 1] = pre[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            res[i] = pre[i];
            if (pre[i] > suff[i + 1]) 
                res[i] = res[i + 1];
        }
        return res;
    }
}
