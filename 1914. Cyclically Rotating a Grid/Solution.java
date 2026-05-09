// 1914. Cyclically Rotating a Grid
// https://leetcode.com/problems/cyclically-rotating-a-grid/
class Solution {
    public int[][] rotateGrid(int[][] grid, int k) {
        int T = 0, L = 0;
        int B = grid.length - 1, R = grid[0].length - 1;

        while (T < B && L < R) {
            int len = B - T, wid = R - L;
            int perimeter = 2 * len + 2 * wid;
            int r = k % perimeter;

            while (r-- > 0) {
                int tmp = grid[T][L];

                for (int i = L; i < R; i++)
                    grid[T][i] = grid[T][i + 1];

                for (int i = T; i < B; i++)
                    grid[i][R] = grid[i + 1][R];

                for (int i = R; i > L; i--)
                    grid[B][i] = grid[B][i - 1];

                for (int i = B; i > T; i--)
                    grid[i][L] = grid[i - 1][L];

                grid[T + 1][L] = tmp;
            }

            T++; L++;
            B--; R--;
        }

        return grid;
    }
}

class Solution2 {
    private int traverse(int[][] g, int[] la, int m, int n, int i, boolean f, int v, int l) {
        int r1 = i, c1 = i;
        int r2 = i, c2 = n - i - 1;
        int r3 = m - i - 1, c3 = n - i - 1;
        int r4 = m - i - 1, c4 = i;
        /*System.out.println(r1 + " " + c1);
        System.out.println(r2 + " " + c2);
        System.out.println(r3 + " " + c3);
        System.out.println(r4 + " " + c4);*/
        int j = 0;
        for (int k = r1; k <= r4; k++)
            if (f)
                g[k][c1] = la[(j++ + v) % l];
            else
                la[j++] = g[k][c1];
        for (int k = c4 + 1; k <= c3; k++)
            if (f)
                g[r4][k] = la[(j++ + v) % l];
            else
                la[j++] = g[r4][k];
        for (int k = r3 - 1; k >= r2; k--)
            if (f)
                g[k][c2] = la[(j++ + v) % l];
            else
                la[j++] = g[k][c2];
        for (int k = c2 - 1; k > c1; k--)
            if (f)
                g[r1][k] = la[(j++ + v) % l];
            else
                la[j++] = g[r1][k];
        return j;
    }

    public int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[] la = new int[(m + n - 2) << 1];
        int rc = Math.min(m, n) >> 1;
        for (int i = 0; i < rc; i++) {
            int l = traverse(grid, la, m, n, i, false, k, 0);
            //System.out.println(l);
            traverse(grid, la, m, n, i, true, l - (k % l), l);
        }
        return grid;
    }
}
