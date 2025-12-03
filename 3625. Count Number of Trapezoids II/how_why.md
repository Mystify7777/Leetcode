# 3625. Count Number of Trapezoids II — how/why

## Recap

Given an array of points `points[i] = [x_i, y_i]` on a 2D plane, count the number of trapezoids that can be formed by selecting 4 points. A trapezoid has **exactly one pair of parallel sides**. Return the count.

Note: Unlike problem 3623 (Trapezoids I) which only counts trapezoids with vertical parallel sides, this problem counts **all** trapezoids regardless of orientation.

## Intuition

A trapezoid is formed by 4 points where exactly one pair of opposite sides is parallel. To count:

1. **Count all 4-tuples with at least one pair of parallel sides** (includes trapezoids and parallelograms).
2. **Subtract parallelograms** (which have two pairs of parallel sides).

Two line segments are parallel if they have the same slope. We group line segments by:
- **Normalized slope** (using GCD to reduce fractions).
- **Parallel line identifier** (intercept or distance from origin).

For segments on the same parallel line with the same slope, pick 2 to form parallel sides: `C(count, 2)`.

## Approach

1. **Generate all possible line segments** from pairs of points.

2. **Normalize each segment**:
   - Compute `dx = x2 - x1`, `dy = y2 - y1`.
   - Normalize direction: ensure `dx > 0` or (`dx == 0` and `dy > 0`).
   - Reduce slope to simplest form: `(sx, sy) = (dx/gcd, dy/gcd)`.
   - Compute line identifier: `des = sx * y1 - sy * x1` (perpendicular distance scaled).

3. **Build two maps**:
   - **Map `t`**: Groups segments by *normalized slope* `(sx, sy)` and line `des`. Counts segments on parallel lines with the same normalized slope.
   - **Map `v`**: Groups segments by *exact vector* `(dx, dy)` and line `des`. Identifies collinear segments (same length, direction, and line).

4. **Count trapezoids**:
   - `count(t)`: Counts all 4-tuples with at least one pair of parallel sides (includes trapezoids + parallelograms).
   - `count(v)`: Counts parallelograms (segments with identical vectors on the same line form parallelograms).
   - Result: `count(t) - count(v) / 2`.

5. **Helper `count(map)`**:
   - For each slope group, for each parallel line:
     - If `k` segments exist, compute `C(k, 2) * C(remaining, 2)` for all pairs of parallel lines.
   - Accumulate using the formula: `sum -= val; ans += val * sum;` to count pairs.

## Code (Java)

```java
class Solution {
    public int countTrapezoids(int[][] points) {
        HashMap<Integer, HashMap<Integer, Integer>> t = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> v = new HashMap<>();

        int n = points.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                // Normalize direction
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }

                int g = gcd(dx, Math.abs(dy));
                int sx = dx / g;
                int sy = dy / g;

                // Line identifier
                int des = sx * points[i][1] - sy * points[i][0];

                // Keys for maps
                int key1 = (sx << 12) | (sy + 2000);
                int key2 = (dx << 12) | (dy + 2000);

                t.computeIfAbsent(key1, k -> new HashMap<>()).merge(des, 1, Integer::sum);
                v.computeIfAbsent(key2, k -> new HashMap<>()).merge(des, 1, Integer::sum);
            }
        }

        return count(t) - count(v) / 2;
    }

    private int count(HashMap<Integer, HashMap<Integer, Integer>> map) {
        long ans = 0;

        for (HashMap<Integer, Integer> inner : map.values()) {
            long sum = 0;
            for (int val : inner.values()) sum += val;

            for (int val : inner.values()) {
                sum -= val;
                ans += (long) val * sum;
            }
        }

        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }
}
```

## Correctness

- **Normalized slope**: Using GCD ensures that segments with slopes 2/4 and 1/2 are treated as parallel (both reduce to 1/2).

- **Line identifier**: The formula `sx * y - sy * x` gives a unique value for each parallel line with slope `(sx, sy)`, allowing us to group segments on the same line.

- **Direction normalization**: Ensures that segment AB and BA are treated identically.

- **Inclusion-Exclusion**: 
  - `count(t)` counts all quadrilaterals with at least one pair of parallel sides.
  - `count(v)` counts parallelograms (where both pairs are parallel and segments have identical vectors).
  - Dividing by 2 corrects for double counting in the parallelogram calculation.

- **Trapezoid = (at least one parallel pair) - (both pairs parallel)**: This gives exactly one parallel pair.

## Complexity

- **Time**: `O(n²)` to generate all pairs of points, plus `O(n²)` for counting = `O(n²)`.
- **Space**: `O(n²)` to store all segment information in hashmaps.

## Edge Cases

- Fewer than 4 points: return 0.
- All points collinear: no trapezoids (need two distinct parallel lines).
- Square or rectangle: excluded (these are parallelograms, not trapezoids).
- Three points on a line + one off: can form trapezoids if the parallel sides are chosen correctly.
- Large coordinates: bit-packing keys requires careful range management; adjust shift values if coordinates exceed bounds.
- Degenerate cases (duplicate points): typically assume distinct points or handle separately.

## Takeaways

- **Slope normalization with GCD**: Essential for comparing line segments with rational slopes.
- **Line identification**: Perpendicular distance formula `ax - by = c` uniquely identifies parallel lines.
- **Inclusion-Exclusion principle**: Count broader category, then subtract overcounted cases.
- **Bit-packing for keys**: Combining two integers into one key using bit shifts enables efficient hashmap usage.
- **Parallelogram detection**: Segments with identical normalized vectors on the same line form parallelograms.
- This approach generalizes to counting other geometric structures (e.g., rectangles, rhombuses) by varying the parallel side constraints.

## Alternative (Slope-Intercept Form)

```java
class Solution {
    public int countTrapezoids(int[][] points) {
        int n = points.length;
        Map<Double, Map<Double, Integer>> slopeToIntercept = new HashMap<>();
        Map<String, Map<Double, Integer>> midToSlope = new HashMap<>();
        int ans = 0;

        // Generate all segments
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                
                double slope, intercept;
                if (x1 == x2) {
                    slope = Double.POSITIVE_INFINITY;
                    intercept = x1;
                } else {
                    slope = (double)(y2 - y1) / (x2 - x1);
                    intercept = y1 - slope * x1;
                }
                
                String mid = (x1 + x2) + "," + (y1 + y2);
                
                slopeToIntercept.computeIfAbsent(slope, k -> new HashMap<>())
                    .merge(intercept, 1, Integer::sum);
                midToSlope.computeIfAbsent(mid, k -> new HashMap<>())
                    .merge(slope, 1, Integer::sum);
            }
        }

        // Count trapezoids (at least one parallel pair)
        for (Map<Double, Integer> lines : slopeToIntercept.values()) {
            int sum = 0;
            for (int count : lines.values()) {
                ans += sum * count;
                sum += count;
            }
        }

        // Subtract parallelograms
        for (Map<Double, Integer> slopes : midToSlope.values()) {
            int sum = 0;
            for (int count : slopes.values()) {
                ans -= sum * count;
                sum += count;
            }
        }

        return ans;
    }
}
```

**Trade-off**: Uses floating-point arithmetic (simpler logic but potential precision issues). The GCD-based approach with integer arithmetic is more robust for exact computation.
