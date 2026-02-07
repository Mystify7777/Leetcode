// 1653. Minimum Deletions to Make String Balanced
// https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/
class Solution {
    public int minimumDeletions(String s) {
        int res = 0, count = 0;
        for (char c : s.toCharArray()){
            if (c == 'b')
                count++;
            else if (count != 0){
                res++;
                count--;
            }
        }
        return res;
    }
}