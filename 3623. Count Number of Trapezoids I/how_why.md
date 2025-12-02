# 3623. Count Number of Trapezoids I — how/why

## Recap

Given an array of points `points[i] = [x_i, y_i]` on a 2D plane, count the number of trapezoids that can be formed by selecting 4 points. A trapezoid has exactly one pair of parallel sides. Return the count modulo `10^9 + 7`.

**Key insight**: For a trapezoid with vertical parallel sides, we need two points with the same y-coordinate forming one parallel side, and two other points with a different (but same between them) y-coordinate forming the other parallel side.

## Intuition

To form a trapezoid with vertical parallel sides (which is what this problem counts), we need:

- Two points at height `y1`: these form one parallel side.
- Two points at height `y2` (where `y1 ≠ y2`): these form the other parallel side.

The x-coordinates can be arbitrary (as long as the four points are distinct). So the problem reduces to:

1. Group points by their y-coordinate.
2. Count how many ways to pick 2 points from one group (forming one parallel side).
3. Count how many ways to pick 2 points from another group (forming the other parallel side).
4. Multiply combinations across different height groups.

## Approach

1. **Sort points by y-coordinate** (or use a hashmap to group by y).
2. **Group points with the same y-coordinate** together.
3. **For each group of size `k`**, the number of ways to choose 2 points is `C(k, 2) = k * (k - 1) / 2`.
4. **Accumulate contributions**:
   - For each new height group with `lines` ways to form a parallel side, multiply by `total` (sum of all previous groups' line counts).
   - Add this product to the result.
   - Update `total` by adding the current group's `lines`.
5. **Return result modulo 10^9 + 7**.

**Example**: If heights have groups: `[y1: 3 points, y2: 2 points, y3: 4 points]`

- Group y1: `C(3,2) = 3` lines
- Group y2: `C(2,2) = 1` line → trapezoids = `3 * 1 = 3`
- Group y3: `C(4,2) = 6` lines → trapezoids = `(3 + 1) * 6 = 24`
- Total trapezoids = `3 + 24 = 27`

## Code (Java)

```java
class Solution {
    public int countTrapezoids(int[][] points) {
        int n = points.length, MOD = 1000000007;
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        
        long res = 0, total = 0;
        
        for (int i = 0, j; i < n; i = j) {
            j = i + 1;
            // Group points with same y-coordinate
            while (j < n && points[i][1] == points[j][1]) {
                j++;
            }
            
            long count = j - i;
            long lines = count * (count - 1) / 2; // C(count, 2)
            
            // Trapezoids formed with this group and all previous groups
            res = (res + total * lines) % MOD;
            
            // Update total lines count
            total = (total + lines) % MOD;
        }
        
        return (int) res;
    }
}
```

## Correctness

- **Trapezoid definition**: A valid trapezoid has exactly one pair of parallel sides. With vertical parallel sides, we need two horizontal line segments (each connecting 2 points at the same height).

- **Counting pairs**: For `k` points at height `y`, there are `C(k, 2) = k(k-1)/2` ways to choose 2 points, each forming one potential parallel side.

- **Combining groups**: To form a trapezoid, we pick one line from group `i` and one line from group `j` (where `i ≠ j`). By accumulating `total` (sum of all previous groups' line counts) and multiplying by the current group's `lines`, we count all valid combinations.

- **No double counting**: Processing groups sequentially ensures each pair of groups is counted exactly once.

- **Modulo arithmetic**: Taking modulo at each step prevents overflow and satisfies the problem requirement.

## Complexity

- **Time**: `O(n log n)` for sorting, plus `O(n)` for grouping and counting = `O(n log n)`.
- **Space**: `O(1)` auxiliary space (excluding sorting space).

## Edge Cases

- Fewer than 4 points: return 0 (can't form a trapezoid).
- All points at the same height: no second parallel side, return 0.
- Only two distinct heights: only one possible pair of parallel sides, count combinations.
- Many points at the same height: large `C(k, 2)` values; use `long` to prevent overflow before taking modulo.
- Points with same (x, y): problem typically assumes distinct points or counts them separately.

## Takeaways

- **Grouping by property**: When geometric constraints involve parallel lines or shared coordinates, group elements by that property.
- **Combinatorics**: Count ways to form substructures (here, line segments) and combine them.
- **Sequential accumulation**: Process groups in order, maintaining a running total to avoid nested loops (reduces O(n²) to O(n)).
- **Modulo arithmetic**: Apply modulo consistently to intermediate results to handle large numbers.
- This pattern extends to other counting problems involving pairwise or group-wise combinations (e.g., rectangles, triangles with specific properties).

## Alternative (HashMap)

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> freq = new HashMap<>();
        
        // Count frequency of each y-coordinate
        for (int[] point : points) {
            freq.merge(point[1], 1, Integer::sum);
        }
        
        long sum = 0, ans = 0;
        
        for (int count : freq.values()) {
            long lines = count * (count - 1L) / 2L;
            ans = (ans + lines * sum) % MOD;
            sum = (sum + lines) % MOD;
        }
        
        return (int) ans;
    }
}
```

**Trade-off**: HashMap approach doesn't require sorting (O(n) time for grouping) but loses deterministic ordering (results are still correct since order of processing groups doesn't affect the count). Sorting-based approach is O(n log n) but more explicit. Both are acceptable; choose based on preference.
