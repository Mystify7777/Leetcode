// 1871. Jump Game VII
// https://leetcode.com/problems/jump-game-vii/

class Solution {
    public boolean canReach(String s, int minJ, int maxJ) {
        int n = s.length();

        if (s.charAt(n - 1) == '1')
            return false;

        int[] dp = new int[n];
        dp[0] = 1;
        int reach = 0, maxR = maxJ;

        for (int i = minJ; i < n; i++) {
            if (i > maxR) return false;

            reach += dp[i - minJ];
            if (i > maxJ) reach -= dp[i - maxJ - 1];

            if (reach > 0 && s.charAt(i) == '0') {
                dp[i] = 1;
                maxR = i + maxJ;
            }
        }

        return reach > 0;
    }
}

class Solution2 {
    public boolean canReach(String s, int minJump, int maxJump) {
        int start = 0, end = 0, len = s.length();
        if(len == 0 || s.charAt(0) == '1' || s.charAt(len-1) == '1') {
            return false;
        }
        boolean[] dp = new boolean[len];
        dp[0] = true;

        for(int i = 0; i < len; i++) {
            if(!dp[i]) {
                continue;
            }

            start = Math.max(end + 1, i + minJump);
            end = Math.min(len-1, i + maxJump);

            for(int j = start; j <= end; j++) {
                if(s.charAt(j) == '0') {
                    dp[j] = true;
                }
            }
            if(dp[len-1]) {
                return true;
            }
        }
        return dp[len-1];
    }
}
