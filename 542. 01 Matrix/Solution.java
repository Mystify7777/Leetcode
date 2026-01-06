// 542. 01 Matrix
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0)
            return new int[0][0];

        int m = mat.length, n = mat[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int MAX_VALUE = m * n;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    mat[i][j] = MAX_VALUE;
                }
            }
        }
        
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] dir : directions) {
                int r = cell[0] + dir[0], c = cell[1] + dir[1];
                if (r >= 0 && r < m && c >= 0 && c < n && mat[r][c] > mat[cell[0]][cell[1]] + 1) {
                    queue.offer(new int[]{r, c});
                    mat[r][c] = mat[cell[0]][cell[1]] + 1;
                }
            }
        }
        
        return mat;
    }
}

//Alt Approach
/**explain this working ->
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
       final int m = matrix.length;
        final int n = matrix[0].length;
        final int rowLast = m - 1, colLast = n - 1;
        final int INF = m + n + 1;

        int[] row = matrix[0];
        int[] prevRow;

        if (row[0] == 1) row[0] = INF;

        for (int c = 1; c <= colLast; c++) {
            if (row[c] == 1) row[c] = row[c - 1] + 1;
        }

        for (int r = 1; r <= rowLast; r++) {
            prevRow = row;
            row = matrix[r];
            if (row[0] == 1) row[0] = prevRow[0] + 1;
            for (int c = 1; c <= colLast; c++) {
                if (row[c] == 1) {
                    int minPrev = prevRow[c] < row[c - 1] ? prevRow[c] : row[c - 1];
                    row[c] = minPrev + 1;
                }
            }
        }

        row = matrix[rowLast];
        for (int c = colLast - 1; c >= 0; c--) {
            if (row[c] > 1 && row[c + 1] + 1 < row[c]) {
                row[c] = row[c + 1] + 1;
            }
        }
        for (int r = rowLast - 1; r >= 0; r--) {
            prevRow = row;
            row = matrix[r];
            if (row[colLast] > 1 && prevRow[colLast] + 1 < row[colLast]) {
                row[colLast] = prevRow[colLast] + 1;
            }
            for (int c = colLast - 1; c >= 0; c--) {
                if (row[c] > 1) {
                    int minPrev = prevRow[c] < row[c + 1] ? prevRow[c] : row[c + 1];
                    int newVal = minPrev + 1;
                    if (newVal < row[c]) row[c] = newVal;
                }
            }
        }

        return matrix;

    }
}
 */