// 696. Count Binary Substrings
// https://leetcode.com/problems/count-binary-substrings/
class Solution {
    public int countBinarySubstrings(String s) {
        int curr = 1, prev = 0, ans = 0;
        for (int i = 1; i < s.length(); i++)
            if (s.charAt(i) == s.charAt(i-1)) curr++;
            else {
                ans += Math.min(curr, prev);
                prev = curr;
                curr = 1;
            }
        return ans + Math.min(curr, prev);
    }
}

class Solution2 {
    public int countBinarySubstrings(String s) {
        int start = 0, ans = 0;
        char[] arr = s.toCharArray();
        for (int i = 1; i < arr.length; i++){
            if (arr[i] != arr[i - 1]){ans++; start = i - 1;}
            else if (start > 0 && arr[--start] != arr[i]) ans++; //if start isn't 0, we may still have a valid substring
            else start = 0; // if not, then reset start to 0
        }

        return ans;
    }
}