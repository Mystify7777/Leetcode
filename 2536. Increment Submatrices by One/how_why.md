# 2536. Increment Submatrices by One

## Recap

You are given an integer `n` (the size of an `n × n` matrix initially filled with zeros) and a list of `queries` where each query `[row1, col1, row2, col2]` specifies a submatrix. For each query, increment all cells in the rectangle from `(row1, col1)` to `(row2, col2)` (inclusive) by 1. Return the final matrix after processing all queries.

## Intuition

Naively incrementing every cell in each rectangle is `O(queries * n^2)` in the worst case (if each query covers the entire matrix). A smarter approach uses a **2D difference array** to record the "edges" of each increment operation. After marking all edges, we reconstruct the final matrix via 2D prefix sums in `O(n^2)` time regardless of the number of queries.

The 2D difference array technique works by:

1. Marking the **top-left corner** `(r1, c1)` with `+1` (the increment begins here).
2. Marking just **below the bottom edge** `(r2+1, c1)` with `-1` (undo the increment for rows beyond).
3. Marking just **right of the right edge** `(r1, c2+1)` with `-1` (undo the increment for columns beyond).
4. Marking the **diagonal corner** `(r2+1, c2+1)` with `+1` (correct for double-subtraction).

When we compute the 2D prefix sum, these markers ensure exactly the rectangle `[r1..r2][c1..c2]` accumulates `+1`.

## Approach

```text
1. Initialize diff[n][n] with zeros.
2. For each query [r1, c1, r2, c2]:
     diff[r1][c1] += 1
     if r2+1 < n: diff[r2+1][c1] -= 1
     if c2+1 < n: diff[r1][c2+1] -= 1
     if r2+1 < n and c2+1 < n: diff[r2+1][c2+1] += 1
3. Compute 2D prefix sum over diff:
     diff[i][j] += diff[i-1][j] + diff[i][j-1] - diff[i-1][j-1]
     (with boundary checks for i=0 or j=0)
4. Return diff (now the result matrix).
```

## Code (Java)

```java
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
```

## Correctness

The 2D difference array mirrors the 1D technique:

- In 1D, marking `diff[L] += 1` and `diff[R+1] -= 1` ensures the range `[L..R]` receives `+1` after prefix sum.
- In 2D, we apply this independently along rows and columns. The four corner markers implement inclusion-exclusion:
  - `+1` at `(r1, c1)`: starts the increment region.
  - `-1` at `(r2+1, c1)` and `(r1, c2+1)`: cancel increments beyond the rectangle's boundaries.
  - `+1` at `(r2+1, c2+1)`: restores correctness at the overlap of the two cancel regions.

When we compute the 2D prefix sum using `diff[i][j] += above + left - diag`, each cell accumulates exactly the net effect from all query markers, yielding the correct increment count.

## Complexity

- Let `Q = queries.length`.
- Time: `O(Q + n^2)` — process `Q` queries in `O(4Q) = O(Q)`, then compute prefix sum over `n^2` cells.
- Space: `O(n^2)` for the difference array (the output itself).

## Edge Cases

- Single cell query `[r, c, r, c]`: only `diff[r][c] += 1` if within bounds; the ±1 markers outside the cell (if any) correctly isolate it.
- Query covering entire matrix `[0, 0, n-1, n-1]`: only `diff[0][0] += 1` is applied (no out-of-bound markers), so the entire matrix increments by 1.
- Overlapping queries: difference array naturally accumulates multiple `+1` markers at the same corners, and the prefix sum correctly sums them.
- `n = 1`: matrix is a single cell; boundary checks prevent out-of-bounds writes.

## Alternative Implementation (Separate Row/Column Passes)

The commented alternate solution applies the same idea but computes row-wise prefix sums first, then column-wise prefix sums:

```java
// After marking corners:
// 1. Row-wise prefix: res[i][j] += res[i][j-1] for all i
// 2. Column-wise prefix: res[i][j] += res[i-1][j] for all j
```

This separates the 2D prefix sum into two 1D passes, which can be slightly faster due to cache locality, but both approaches are `O(n^2)` and yield identical results.

## Takeaways

- 2D difference arrays extend 1D range updates to matrices efficiently.
- Four corner markers (`+1, -1, -1, +1`) implement inclusion-exclusion for rectangles.
- Computing 2D prefix sums reconstructs the final matrix in linear time relative to its size.
- This technique is essential for batch range-update problems on grids.
