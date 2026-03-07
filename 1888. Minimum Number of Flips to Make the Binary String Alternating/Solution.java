// 1888. Minimum Number of Flips to Make the Binary String Alternating
// https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
class Solution {
    public int minFlips(String s) {
        int n = s.length();
        int res = n;
        int[] op = {0, 0};

        for (int i = 0; i < n; i++)
            op[(s.charAt(i) ^ i) & 1]++;

        for (int i = 0; i < n; i++) {
            op[(s.charAt(i) ^ i) & 1]--;
            op[(s.charAt(i) ^ (n + i)) & 1]++;
            res = Math.min(res, Math.min(op[0], op[1]));
        }

        return res;
    }
}