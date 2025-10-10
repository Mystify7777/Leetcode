// 3147. Taking Maximum Energy From the Mystic Dungeon
// https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/
class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int l = energy.length;
        int[] dp = new int[l];
        int result = Integer.MIN_VALUE;
        for (int i = l - 1; i >= 0; i--) {
            dp[i] = energy[i] + (i + k < l ? dp[i + k] : 0);
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}
/**
class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length, m = n-k, max = -1000;
        for (int i = m; i < n; i++) {
            for (int j = i, tmp = 0; j > -1; j -= k) {
                tmp += energy[j];
                if (max < tmp) {
                    max = tmp;
                }
            }
        }
        
        return max;
    }
} */