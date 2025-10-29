// 3370. Smallest Number With All Set Bits
//https://leetcode.com/problems/smallest-number-with-all-set-bits
class Solution {
       public int smallestNumber(int n) {
        int res = 1;
        while (res < n)
            res = res * 2 + 1;
        return res;
    }
}