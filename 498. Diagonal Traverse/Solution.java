// 498. Diagonal Traverse
public class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0];

        int m = matrix.length, n = matrix[0].length;
        int[] result = new int[m * n];
        int row = 0, col = 0;

        for (int i = 0; i < m * n; i++) {
            result[i] = matrix[row][col];

            if ((row + col) % 2 == 0) {
                if (col == n - 1) row++;
                else if (row == 0) col++;
                else { row--; col++; }
            } else {
                if (row == m - 1) col++;
                else if (col == 0) row++;
                else { row++; col--; }
            }
        }

        return result;
    }
}


//what's the difference in this approach?
/**

class Solution {
    private int[] res;
    private int itr, dir;

    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        res = new int[m * n];
        itr = 0; 
        dir = 1;
        traverse(0, 0, mat, m, n);
        return res;
    }

    private void traverse(int i, int j, int[][] grid, int m, int n) {
        if (itr == m * n) return;

        res[itr++] = grid[i][j];

        int newI = i, newJ = j;

        if (dir == 1) {
            newI = i - 1;
            newJ = j + 1;
        } else {
            newI = i + 1;
            newJ = j - 1;
        }

        if (newI < 0 && newJ < n) {
            newI = 0;
            dir = -1;
        } else if (newJ == n) {
            newI = i + 1;
            newJ = n - 1;
            dir = -1;
        } else if (newJ < 0 && newI < m) {
            newJ = 0;
            dir = 1;
        } else if (newI == m) {
            newI = m - 1;
            newJ = j + 1;
            dir = 1;
        }

        traverse(newI, newJ, grid, m, n);
    }
}
 */