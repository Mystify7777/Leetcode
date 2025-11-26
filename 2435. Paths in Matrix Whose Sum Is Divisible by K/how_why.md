# Recap

You are given a 0-indexed `m x n` integer matrix `grid` and an integer `k`. You start at the top-left cell `(0, 0)` and want to reach the bottom-right cell `(m-1, n-1)`. You can only move **right** or **down** at each step. Return the number of paths where the sum of elements along the path is divisible by `k`. Since the answer may be very large, return it modulo `10^9 + 7`.

## Intuition

This is a dynamic programming problem with an extra dimension for tracking remainders. Instead of just counting paths to each cell, we need to count paths grouped by their sum modulo `k`. For any cell `(i, j)`, we track how many paths reach it with each possible remainder `r` (where `0 ≤ r < k`). The answer is the number of paths reaching `(m-1, n-1)` with remainder `0`.

## Approach

1. **State definition:** `dp[i][j][r]` = number of paths to cell `(i, j)` with sum ≡ `r` (mod `k`).

2. **Initialization:**
   - For the first row: paths can only come from the left. Accumulate sum and set `dp[0][j][sum % k] = 1`.
   - For the first column: paths can only come from above. Accumulate sum and set `dp[i][0][sum % k] = 1`.

3. **Transition:** For cell `(i, j)` with value `val`:
   - A path with remainder `r` at `(i-1, j)` becomes remainder `(r + val) % k` at `(i, j)`.
   - Same for paths from `(i, j-1)`.
   - Formula: `dp[i][j][(r + val) % k] += dp[i-1][j][r] + dp[i][j-1][r]`.

4. **Space optimization:** Use two 2D arrays `prev[n][k]` and `curr[n][k]` to store only the previous and current rows, reducing space from `O(m*n*k)` to `O(n*k)`.

5. **Result:** Return `dp[m-1][n-1][0]` (paths with remainder 0).

## Code (Java)

```java
class Solution {
    private static final int MOD = (int) 1e9 + 7;

    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][] prev = new int[n][k];
        int[][] curr = new int[n][k];

        // Initialize first row
        int sum = 0;
        for (int j = 0; j < n; j++) {
            sum = (sum + grid[0][j]) % k;
            prev[j][sum] = 1;
        }

        // Process remaining rows
        sum = grid[0][0] % k;
        for (int i = 1; i < m; i++) {
            sum = (sum + grid[i][0]) % k;
            Arrays.fill(curr[0], 0);
            curr[0][sum] = 1;

            for (int j = 1; j < n; j++) {
                Arrays.fill(curr[j], 0);
                int val = grid[i][j];

                for (int r = 0; r < k; r++) {
                    int nr = (r + val) % k;
                    curr[j][nr] = (prev[j][r] + curr[j - 1][r]) % MOD;
                }
            }

            // Swap prev and curr
            int[][] tmp = prev;
            prev = curr;
            curr = tmp;
        }

        return prev[n - 1][0];
    }
}
```

## Correctness

- **Base cases:** First row and first column are correctly initialized with cumulative sums modulo `k`, ensuring exactly one path per cell with the appropriate remainder.

- **Recurrence validity:** The transition `(r + val) % k` correctly computes the new remainder when adding the current cell's value to a path with remainder `r`.

- **Path counting:** By summing contributions from both possible predecessors (top and left), we count all valid paths without duplication or omission.

- **Modular arithmetic:** Taking modulo at each step prevents overflow and ensures the result stays within bounds.

- **Space optimization correctness:** Only the previous row's state is needed to compute the current row, so rolling arrays work without losing information.

## Complexity

- **Time:** `O(m * n * k)` — for each of `m * n` cells, we iterate through `k` remainder states.
- **Space:** `O(n * k)` — two arrays of size `n * k` for rolling DP.

## Edge Cases

- Single cell `(1x1)`: Path sum is `grid[0][0]`. Return `1` if divisible by `k`, else `0`.
- `k = 1`: All sums divisible by 1, return total number of paths = `C(m+n-2, m-1)` mod `10^9+7`.
- Large grid with small `k`: Efficient due to `O(n*k)` space.
- All zeros in grid: Sum is always 0, divisible by any `k`, return total paths.
- `k > 50` (constraint limit): Still efficient since `k` is small relative to grid size.
- Path sum exactly divisible: Correctly captured by remainder 0.

## Takeaways

- **DP with modular constraint:** When divisibility is involved, add a dimension to track remainders modulo the divisor.
- **Space optimization:** Rolling arrays reduce space complexity when only the previous state is needed.
- **Modular arithmetic in transitions:** `(r + val) % k` updates remainders correctly for path extensions.
- **Initialization matters:** Properly setting up boundary conditions (first row/column) is crucial for correctness.
- This pattern generalizes to other path-counting problems with sum constraints (e.g., paths with sum in a range, paths with specific parity).

## Alternative (3D DP - More Intuitive)

```java
class Solution {
    private static final int MOD = (int) 1e9 + 7;

    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m][n][k];
        
        dp[0][0][grid[0][0] % k] = 1;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                
                int val = grid[i][j];
                for (int r = 0; r < k; r++) {
                    int nr = (r + val) % k;
                    if (i > 0) dp[i][j][nr] = (dp[i][j][nr] + dp[i-1][j][r]) % MOD;
                    if (j > 0) dp[i][j][nr] = (dp[i][j][nr] + dp[i][j-1][r]) % MOD;
                }
            }
        }
        
        return dp[m-1][n-1][0];
    }
}
```

**Trade-off:** More intuitive to understand, but uses `O(m * n * k)` space. The rolling array version is more space-efficient for large grids.
