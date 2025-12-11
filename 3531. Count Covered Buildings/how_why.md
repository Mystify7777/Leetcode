# 3531. Count Covered Buildings — how/why

## Recap

Given an integer `n` and a 2D array `buildings` where `buildings[i] = [x_i, y_i]` represents a building at coordinates `(x_i, y_i)` on an `n × n` grid, count how many buildings are "covered." A building at position `(x, y)` is covered if:

- There exists a building with the same `x` coordinate but both smaller and larger `y` coordinates.
- There exists a building with the same `y` coordinate but both smaller and larger `x` coordinates.

In other words, a building is covered if it's not on the boundary of its row or column when considering only the buildings in the grid.

## Intuition

A building at `(x, y)` is covered if:

- It's not the topmost or bottommost building in its column (same `x`).
- It's not the leftmost or rightmost building in its row (same `y`).

This translates to finding, for each building:

- The min and max `y` values among all buildings sharing the same `x` coordinate.
- The min and max `x` values among all buildings sharing the same `y` coordinate.

If the building's coordinates fall strictly between these min/max values (not at the boundaries), it's covered.

## Approach

**Range Tracking with HashMaps**:

1. Initialize two maps:
   - `yRangeGivenX`: Maps each `x` coordinate to `[min_y, max_y]` for buildings at that `x`.
   - `xRangeGivenY`: Maps each `y` coordinate to `[min_x, max_x]` for buildings at that `y`.

2. First pass: Populate the maps by iterating through all buildings:
   - For each building `(x, y)`, update the min/max ranges in both maps.

3. Second pass: Check each building for coverage:
   - A building at `(x, y)` is covered if:
     - `xRangeGivenY[y][0] < x < xRangeGivenY[y][1]` (not at row boundaries).
     - `yRangeGivenX[x][0] < y < yRangeGivenX[x][1]` (not at column boundaries).
   - Increment count for each covered building.

4. Return the count.

## Code (Java)

```java
class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, int[]> yRangeGivenX = new HashMap<>();
        Map<Integer, int[]> xRangeGivenY = new HashMap<>();
        
        for (int[] b : buildings) {
            int x = b[0], y = b[1];
            yRangeGivenX.putIfAbsent(x, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
            yRangeGivenX.get(x)[0] = Math.min(yRangeGivenX.get(x)[0], y);
            yRangeGivenX.get(x)[1] = Math.max(yRangeGivenX.get(x)[1], y);

            xRangeGivenY.putIfAbsent(y, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
            xRangeGivenY.get(y)[0] = Math.min(xRangeGivenY.get(y)[0], x);
            xRangeGivenY.get(y)[1] = Math.max(xRangeGivenY.get(y)[1], x);
        }
        
        int count = 0;
        for (int[] b : buildings) {
            int x = b[0], y = b[1];
            if (xRangeGivenY.get(y)[0] < x && x < xRangeGivenY.get(y)[1] &&
                yRangeGivenX.get(x)[0] < y && y < yRangeGivenX.get(x)[1]) {
                count++;
            }
        }
        
        return count;
    }
}
```

## Correctness

- **Range initialization**: Using `Integer.MAX_VALUE` for min and `Integer.MIN_VALUE` for max ensures the first comparison updates the range correctly.

- **Min/max updates**: For each building at `(x, y)`:
  - `yRangeGivenX[x]` tracks the vertical span of buildings in column `x`.
  - `xRangeGivenY[y]` tracks the horizontal span of buildings in row `y`.

- **Coverage check**: A building is covered if it's strictly inside the ranges (not at boundaries):
  - `xRangeGivenY[y][0] < x < xRangeGivenY[y][1]`: Building is not leftmost or rightmost in its row.
  - `yRangeGivenX[x][0] < y < yRangeGivenX[x][1]`: Building is not topmost or bottommost in its column.

- **Strict inequality**: Using `<` (not `≤`) ensures buildings at the boundaries are not counted as covered.

- **All buildings checked**: The second loop examines each building exactly once, ensuring completeness.

## Complexity

- **Time**: O(m) where m is the number of buildings. First pass iterates through all buildings once (O(m)), second pass also iterates once (O(m)). HashMap operations (get/put) are O(1) average.
- **Space**: O(n) in the worst case where buildings are distributed across all rows and columns. Both maps can have up to n entries each.

## Edge Cases

- Single building: No other buildings to form coverage → return `0`.
- All buildings in one row: Only buildings in same column with multiple y-values can be covered.
- All buildings in one column: Only buildings in same row with multiple x-values can be covered.
- Grid with buildings only at corners: All buildings are at boundaries → return `0`.
- Three buildings in L-shape: The corner building might be covered if there are buildings extending in both directions.
- Duplicate coordinates: Problem likely assumes unique positions, but if duplicates exist, they would share the same ranges.
- Buildings forming a cross (+): Center building is covered if there are buildings in all four directions.

## Takeaways

- **Range queries**: Tracking min/max values for grouped data enables efficient boundary detection.
- **Two-dimensional constraints**: Decomposing 2D constraints into independent 1D checks (row and column) simplifies the logic.
- **HashMap for sparse data**: Using maps instead of arrays is efficient when buildings don't cover the entire grid.
- **Two-pass algorithms**: First pass gathers global information (ranges), second pass uses it for local decisions (coverage).
- **Strict vs non-strict inequalities**: Using strict `<` correctly identifies interior points versus boundary points.

## Alternative (Array-Based Approach, O(m + n))

```java
class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        int[] maxRow = new int[n + 1];
        int[] minRow = new int[n + 1];
        int[] maxCol = new int[n + 1];
        int[] minCol = new int[n + 1];

        Arrays.fill(minRow, n + 1);
        Arrays.fill(minCol, n + 1);

        for (int[] p : buildings) {
            int x = p[0];
            int y = p[1];
            maxRow[y] = Math.max(maxRow[y], x);
            minRow[y] = Math.min(minRow[y], x);
            maxCol[x] = Math.max(maxCol[x], y);
            minCol[x] = Math.min(minCol[x], y);
        }

        int res = 0;
        for (int[] p : buildings) {
            int x = p[0];
            int y = p[1];
            if (x > minRow[y] && x < maxRow[y] && y > minCol[x] && y < maxCol[x]) {
                res++;
            }
        }

        return res;
    }
}
```

**Trade-off**: Array-based approach uses fixed-size arrays indexed by coordinates (O(n) space), making lookups O(1) with no hash overhead. This is faster when `n` is small or when buildings are densely distributed. However, it requires O(n) space regardless of building count. HashMap approach uses O(m) space (only occupied coordinates), making it more space-efficient for sparse grids. Use arrays when `n` is small and performance is critical; use hashmaps when `n` is large or buildings are sparse.
