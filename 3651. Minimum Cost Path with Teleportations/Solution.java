// https://leetcode.com/problems/minimum-cost-path-with-teleportations/
// 3651. Minimum Cost Path with Teleportations
// cp
class Solution {
    public int minCost(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length;
        
        // 1. Find the Maximum Value in the grid to size our helper arrays
        int maxVal = 0;
        for(int[] row : grid) {
            for(int val : row) maxVal = Math.max(maxVal, val);
        }

        // dp[i][j] = Min cost to reach (n-1, m-1) from (i, j)
        int[][] dp = new int[n][m];
        
        // temp[v] = Min cost starting from ANY cell with value 'v'
        int[] temp = new int[maxVal + 1];
        int[] best = new int[maxVal + 1];
        
        Arrays.fill(temp, Integer.MAX_VALUE);
        
        // Base Case: Cost from target to target is 0. 
        // Note: The cost is incurred when ENTERING a cell. 
        // We consider the target reached, so starting at target has 0 *additional* cost.
        temp[grid[n - 1][m - 1]] = 0;

        // --- INITIALIZATION (K=0) ---
        // Fill DP table using standard walking rules (Right/Down)
        for(int i = n - 1; i >= 0; i--) {
            for(int j = m - 1; j >= 0; j--) {
                if(i == n - 1 && j == m - 1) continue; // Skip target
                
                int down = (i + 1 < n) ? dp[i + 1][j] + grid[i + 1][j] : Integer.MAX_VALUE;
                int right = (j + 1 < m) ? dp[i][j + 1] + grid[i][j + 1] : Integer.MAX_VALUE;
                
                dp[i][j] = Math.min(down, right);
                
                // Update the best known cost for this cell's value
                if (dp[i][j] != Integer.MAX_VALUE) {
                    temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
                }
            }
        }

        // --- LAYERS (K > 0) ---
        // For each allowed teleport, we try to relax the grid costs
        for(int x = 0; x < k; x++) {
            
            // 1. Build Prefix Minimum Array
            // best[v] = min cost obtainable from any cell with value <= v
            best[0] = temp[0];
            for(int v = 1; v <= maxVal; v++) {
                best[v] = Math.min(best[v - 1], temp[v]);
            }
            
            // 2. Update DP Table with Teleport Options
            for(int i = n - 1; i >= 0; i--) {
                for(int j = m - 1; j >= 0; j--) {
                    if(i == n - 1 && j == m - 1) continue;
                    
                    int down = (i + 1 < n) ? dp[i + 1][j] + grid[i + 1][j] : Integer.MAX_VALUE;
                    int right = (j + 1 < m) ? dp[i][j + 1] + grid[i][j + 1] : Integer.MAX_VALUE;
                    int walkCost = Math.min(down, right);
                    
                    // Teleport Option: Jump to the best state with value <= grid[i][j]
                    int teleportCost = best[grid[i][j]];
                    
                    dp[i][j] = Math.min(walkCost, teleportCost);
                    
                    // Update temp for the NEXT iteration
                    if (dp[i][j] != Integer.MAX_VALUE) {
                        temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
                    }
                }
            }
        }
        
        return dp[0][0];
    }
}

// https://www.youtube.com/@0x3f
class Solution2 {
    public int minCost(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        if (k > 0 && grid[0][0] >= grid[m - 1][n - 1]) {
            return 0;
        }

        int mx = 0;
        for (int[] row : grid) {
            for (int x : row) {
                mx = Math.max(mx, x);
            }
        }

        int[] sufMinF = new int[mx + 2];
        Arrays.fill(sufMinF, Integer.MAX_VALUE);
        int[] minF = new int[mx + 1];
        int[] f = new int[n + 1];

        for (int t = 0; t <= k; t++) {
            Arrays.fill(minF, Integer.MAX_VALUE);
            Arrays.fill(f, Integer.MAX_VALUE / 2);
            f[1] = -grid[0][0];
            for (int[] row : grid) {
                for (int j = 0; j < n; j++) {
                    int x = row[j];
                    f[j + 1] = Math.min(Math.min(f[j], f[j + 1]) + x, sufMinF[x]);
                    minF[x] = Math.min(minF[x], f[j + 1]);
                }
            }
            boolean done = true;
            for (int i = mx; i >= 0; i--) {
                int mn = Math.min(sufMinF[i + 1], minF[i]);
                if (mn < sufMinF[i]) {
                    sufMinF[i] = mn;
                    done = false;
                }
            }
            if (done) {
                break;
            }
        }

        return f[n];
    }
}