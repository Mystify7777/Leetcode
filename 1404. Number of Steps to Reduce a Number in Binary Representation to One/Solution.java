// https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
// 1404. Number of Steps to Reduce a Number in Binary Representation to One
class Solution {
    public int numSteps(String s) {
        int l = s.length() - 1;
        int carry = 0;
        int count = 0;
        
        while (l > 0) {
            // even number with carry = 0, result even
            if (Character.getNumericValue(s.charAt(l)) + carry == 0) {
                carry = 0;
                count++;
            // odd number with carry = 1, result even
            } else if (Character.getNumericValue(s.charAt(l)) + carry == 2) {
                carry = 1;
                count++;
            // even number with carry = 1, result odd
            // odd number with carry = 0, result odd
            } else {
                carry = 1;
                count += 2;
            }
            l--;
        }
        
        // last digit 1 needs to add a carried over digit 1, which gives 10.
        // So need one more divide to make it 1 (one more step)
        if (carry == 1) {
            count++;
        }
        
        return count;
    }
}

class Solution2 {
    public int numSteps(String s) {
        int n = s.length();
        int carry = 0;
        int res = 0;
        for (int i = n - 1; i > 0; i--){
            if (s.charAt(i) - '0' + carry == 1){
                res += 2;
                carry = 1;
            }
            else{
                res++;
            }
        }
        return res + carry;
    }
}