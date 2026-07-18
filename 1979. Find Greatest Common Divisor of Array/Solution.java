// 1979. Find Greatest Common Divisor of Array
// https://leetcode.com/problems/find-greatest-common-divisor-of-array/
class Solution2 {
    public int findGCD(int[] nums) {
        int mn = 1001, mx = 0;

        for (int num : nums) {
            if (num < mn)
                mn = num;
            if (num > mx)
                mx = num;
        }

        return gcd(mn, mx);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int rem = a % b;
            a = b;
            b = rem;
        }
        return a;
    }
}

class Solution {

    public int findGCD(int[] nums) {

        int min = nums[0];
        int max = nums[0];

        
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < min) {
                min = nums[i];
            }

            if (nums[i] > max) {
                max = nums[i];
            }
        }

        return gcd(min, max);
    }

    public int gcd(int a, int b) {

        while (b != 0) {

            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}