// 2536. Increment Submatrices by One
// https://leetcode.com/problems/increment-submatrices-by-one/
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] diff = new int[n][n];
        
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            diff[r1][c1]++;
            if (r2 + 1 < n) diff[r2 + 1][c1]--;
            if (c2 + 1 < n) diff[r1][c2 + 1]--;
            if (r2 + 1 < n && c2 + 1 < n) diff[r2 + 1][c2 + 1]++;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int above = i > 0 ? diff[i - 1][j] : 0;
                int left = j > 0 ? diff[i][j - 1] : 0;
                int diag = (i > 0 && j > 0) ? diff[i - 1][j - 1] : 0;
                diff[i][j] += above + left - diag;
            }
        }
        
        return diff;
    }
}

//alternate faster approach
/**
class Solution {
    public int[][] rangeAddQueries(int n, int[][] Q) {
        int[][] res = new int[n][n];
        for (var q : Q) {
            int r0 = q[0], c0 = q[1], r1 = q[2]+1, c1 = q[3]+1;
            res[r0][c0]++;
            if (c1 < n) res[r0][c1]--;
            if (r1 < n) {
                res[r1][c0]--;
                if (c1 < n) res[r1][c1]++;
            }
        }

        for (int i = 0; i < n; i++) for (int j = 1; j < n; j++)
            res[i][j] += res[i][j-1];

        for (int i = 1; i < n; i++) for (int j = 0; j < n; j++)
            res[i][j] += res[i-1][j];

        return res;
    }
} */