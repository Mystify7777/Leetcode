// Added using AI
// 3567. Minimum Absolute Difference in Sliding Submatrix
// https://leetcode.com/problems/minimum-absolute-difference-in-sliding-submatrix/
class Solution {
    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][] ans = new int[m - k + 1][n - k + 1];

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                List<Integer> v = new ArrayList<>();
                for (int x = i; x < i + k; x++)
                    for (int y = j; y < j + k; y++)
                        v.add(grid[x][y]);

                Collections.sort(v);
                int mn = Integer.MAX_VALUE;
                int prev = v.get(0);
                for (int p = 1; p < v.size(); p++) {
                    if (v.get(p) != prev)
                        mn = Math.min(mn, v.get(p) - prev);
                    prev = v.get(p);
                }
                ans[i][j] = (mn == Integer.MAX_VALUE) ? 0 : mn;
            }
        }
        return ans;
    }
}
class Solution2 {
    public int getmn(int[] arr){
        Arrays.sort(arr);
        int mn = Integer.MAX_VALUE;
        for(int i = 1;i< arr.length;i++){
            if(arr[i - 1] != arr[i]){
                mn = Math.min(mn, Math.abs(arr[i] - arr[i - 1]));
            }
            
        }
        return mn == Integer.MAX_VALUE? 0:mn;
    }
    public int[][] minAbsDiff(int[][] grid, int k) {
        // 1 1 1
        // 1 1 1
        // 1 1 1
        int m = grid.length, n = grid[0].length;
        int[][] answ = new int[m - k + 1][n - k + 1];
        for(int row = 0; row < m - k + 1;row++){
            for(int col = 0; col < n - k + 1;col++){
                int[] arr = new int[k * k];
                int idx = 0;
                for(int i = row;i < row + k;i++){
                    for(int j = col;j < col + k;j++){
                        arr[idx] = grid[i][j];
                        idx++;
                    }
                }
                answ[row][col] = getmn(arr);
                

            }
        }
        return answ;
    }
}