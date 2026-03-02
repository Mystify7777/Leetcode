// 1536. Minimum Swaps to Arrange a Binary Grid
// https://leetcode.com/problems/minimum-swaps-to-arrange-a-binary-grid/
class Solution {
    public int minSwaps(int[][] grid) {
        int n = grid.length;
        int[] zeros = new int[n];
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = n - 1; j >= 0 && grid[i][j] == 0; j--) {
                count++;
            }
            zeros[i] = count;
        }

        int swaps = 0;

        for (int i = 0; i < n; i++) {
            int needed = n - i - 1;
            int j = i;
            while (j < n && zeros[j] < needed) j++;

            if (j == n) return -1;
            while (j > i) {
                int temp = zeros[j];
                zeros[j] = zeros[j - 1];
                zeros[j - 1] = temp;
                j--;
                swaps++;
            }
        }

        return swaps;
    }
}

class Solution2 {
  public int minSwaps(int[][] grid) {
    final int n = grid.length;
    int ans = 0;
    // suffixZeros[i] := the number of suffix zeros in the i-th row
    int[] suffixZeros = new int[n];

    for (int i = 0; i < grid.length; ++i)
      suffixZeros[i] = getSuffixZeroCount(grid[i]);

    for (int i = 0; i < n; ++i) {
      final int neededZeros = n - 1 - i;
      // Get the first row with suffix zeros >= `neededZeros` in suffixZeros[i:..n).
      final int j = getFirstRowWithEnoughZeros(suffixZeros, i, neededZeros);
      if (j == -1)
        return -1;
      // Move the rows[j] to the rows[i].
      for (int k = j; k > i; --k)
        suffixZeros[k] = suffixZeros[k - 1];
      ans += j - i;
    }

    return ans;
  }

  private int getSuffixZeroCount(int[] row) {
    for (int i = row.length - 1; i >= 0; --i)
      if (row[i] == 1)
        return row.length - i - 1;
    return row.length;
  }

  
  private int getFirstRowWithEnoughZeros(int[] suffixZeros, int i, int neededZeros) {
    for (int j = i; j < suffixZeros.length; ++j)
      if (suffixZeros[j] >= neededZeros)
        return j;
    return -1;
  }
}

