// 2598. Smallest Missing Non-negative Integer After Operations
//https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/
class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int n = nums.length, res = 0;
        int[] rem = new int[value];
        for (int x : nums) {
            int r = ((x % value) + value) % value;
            rem[r]++;
        }
        while (rem[res % value]-- > 0) res++;
        return res;
    }
}
/**APPROACH2
class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] modularDivisionRes = new int[value];
        for (int n : nums) {
            int modDivValue = n % value;
            if (modDivValue < 0) {
                modDivValue += value;
            }
            modularDivisionRes[modDivValue]++;
        }

        int min = modularDivisionRes[0];
        int position = 0;
        for (int i = 0; i < value; i++) {
            if (modularDivisionRes[i] < min) {
                position = i;
                min = modularDivisionRes[i];
            }
        }
        return value * min + position;
    }
} */