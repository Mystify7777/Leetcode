// 3228. Maximum Number of Operations to Move Ones to the End
// https://leetcode.com/problems/maximum-number-of-operations-to-move-ones-to-the-end
class Solution {
    public int maxOperations(String s) {
        int ones = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1')
                ones++;
            else if (i > 0 && s.charAt(i - 1) == '1')
                res += ones;
        }
        return res;
    }
}

//alternate but faster approach.
/**
class Solution {
    public int maxOperations(String s) {
        int index = s.length() - 1, res = 0, running = 0;
        while(index >= 0){
            while(s.charAt(index) == '1'){
                index--;
                res += running;
                if(index < 0) return res;
            }
            while(s.charAt(index) == '0'){
                index--;
                if(index < 0) return res;
            } 
            running++;
        }
        return res;
    }
} */