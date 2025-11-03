// 1578. Minimum Time to Make Rope Colorful
//https://leetcode.com/problems/minimum-time-to-make-rope-colorful/description/
class Solution {
    public int minCost(String s, int[] cost) {
        int n = s.length();
        int result = 0;
        for (int i=1; i<n; i++) {
            if (s.charAt(i) == s.charAt(i-1)) {
                result = result + Math.min(cost[i], cost[i-1]);
                cost[i] = Math.max(cost[i], cost[i-1]);
            }
        }
        return result;
    }
}

//why is this approach fast?

/**
class Solution {
    public int minCost(String colors, int[] neededTime) {
        char[] arr = colors.toCharArray();
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                int total = neededTime[i - 1];
                int max = neededTime[i - 1];
                while (i < arr.length && arr[i] == arr[i - 1]) {
                    total += neededTime[i];
                    max = Math.max(max, neededTime[i]);
                    i++;
                }
                res += total - max;
            }
        }
        return res;
    }
} */