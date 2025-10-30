// 1526. Minimum Number of Increments on Subarrays to Form a Target Array
//https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
class Solution {
       public int minNumberOperations(int[] A) {
        int res = A[0];
        for (int i = 1; i < A.length; ++i)
            res += Math.max(A[i] - A[i - 1], 0);
        return res;
    }
}