// 1611. Minimum One Bit Operations to Make Integers Zero
// https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero
class Solution {
    public int minimumOneBitOperations(int n) {
        int multiplier = 1;
        int res = 0;
        while (n > 0) {
            res += n ^ (n - 1) * multiplier;
            multiplier = -1 * multiplier;
            n &= n - 1;
        }
        return Math.abs(res);
    }
}