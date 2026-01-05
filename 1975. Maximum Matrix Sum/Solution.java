// 1975. Maximum Matrix Sum
// https://leetcode.com/problems/maximum-matrix-sum/
class Solution {

    public long maxMatrixSum(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        long sum = 0;
        int minAbs = Integer.MAX_VALUE, negativeCount = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int val = matrix[i][j];
                sum += Math.abs(val);
                minAbs = Math.min(minAbs, Math.abs(val));
                if (val < 0) negativeCount++;
            }
        }
        
        if (negativeCount % 2 != 0) {
            sum -= 2 * minAbs;
        }
        
        return sum;  
    }
}
