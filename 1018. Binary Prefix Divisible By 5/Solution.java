// 1018. Binary Prefix Divisible By 5
//https://leetcode.com/problems/binary-prefix-divisible-by-5/
class Solution {
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> res = new ArrayList<>();
        int val = 0;

        for (int n : nums) {
            val = ((val << 1) + n) % 5;
            res.add(val == 0);
        }

        return res;
    }
}
