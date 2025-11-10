// 3542. Minimum Operations to Convert All Elements to Zero
//https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/
class Solution {
    public static int minOperations(int[] nums) {
        int n = nums.length;
        int ans = 0;
        boolean[] isNumEncountered = new boolean[100001];
        int[] monoStack = new int[n];
        int size = 0;

        for (int i = 0; i < n; i++) {
            int curr = nums[i];

            if (curr == 0) {
                while (size > 0) {
                    isNumEncountered[monoStack[--size]] = false;
                }
                continue;
            }

            while (size > 0 && monoStack[size - 1] > curr) {
                isNumEncountered[monoStack[--size]] = false;
            }

            if (!isNumEncountered[curr]) {
                ans++;
                isNumEncountered[curr] = true;
            }

            monoStack[size++] = curr;
        }

        return ans;
    }
}

//alternate approach
/**
class Solution {
    public int minOperations(int[] nums) {
        var stack = new int[nums.length + 1];
        var top = 0;
        var ans = 0;
        for (var i = 0; i < nums.length; i++) {
            while (stack[top] > nums[i]) {
                top--;
                ans++;
            }
            if (stack[top] != nums[i])
                stack[++top] = nums[i];
        }
        return ans + top;
    }
} */