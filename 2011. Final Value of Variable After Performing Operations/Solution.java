// 2011. Final Value of Variable After Performing Operations
//https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
class Solution {
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for(String o : operations) x += (44 - o.charAt(1));
        return x;
    }
}