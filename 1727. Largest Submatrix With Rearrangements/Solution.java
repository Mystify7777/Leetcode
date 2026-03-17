// 1727. Largest Submatrix With Rearrangements
// https://leetcode.com/problems/largest-submatrix-with-rearrangements/
class Solution {
    public int largestSubmatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        // Calculate heights for each column
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 1) {
                    matrix[i][j] += matrix[i - 1][j];
                }
            }
        }

        int res = 0;
        for (int i = 0; i < row; i++) {
            // Sort the heights in ascending order
            Arrays.sort(matrix[i]);

            // Iterate through the sorted heights
            for (int j = 0; j < col; j++) {
                int height = matrix[i][j];
                int width = col - j;
                res = Math.max(res, height * width);
            }
        }

        return res;        
    }
}

class Solution2 {
    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        for(int i =1; i< m; i++) {
            for(int j=0; j<n; j++) {
                if(matrix[i][j]==0) continue;
                matrix[i][j] += matrix[i-1][j];
            }
        }
        int maxArea = 0;
        for(int i=0; i<m; i++) {
            Arrays.sort(matrix[i]);
            for(int j=0; j<n; j++) {
                int width = n-j;
                maxArea = Math.max(maxArea, width * matrix[i][j]);
                
            }
        }
        return maxArea;
    }
}