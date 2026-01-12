
# How & Why: LeetCode 1266 - Minimum Time Visiting All Points

## Problem

Given an array of points `points[i] = [x_i, y_i]`, starting at the first point you must visit each subsequent point in order. Each move can go horizontally, vertically, or diagonally, and every move takes 1 second. Return the minimum total time to finish all visits.

---

## How (Step-by-step)

### Approach: Sum Chebyshev Distances

1. Initialize `total = 0`.
2. For every consecutive pair `(points[i-1], points[i])`, compute `dx = |x_i - x_{i-1}|` and `dy = |y_i - y_{i-1}|`.
3. Add `max(dx, dy)` to `total` because a diagonal step reduces both axes simultaneously until one axis is aligned.
4. After processing all pairs, `total` is the minimum time.

**Formula:** $\text{time} = \sum_{i=1}^{n-1} \max\big(|x_i - x_{i-1}|,\ |y_i - y_{i-1}|\big)$.

---

## Why (Reasoning)

- One diagonal move fixes one unit on both axes; once one axis is aligned, only straight moves remain. Thus the segment cost is the larger gap between axes: $\max(dx, dy)$.
- Segments are independent—optimizing each hop optimizes the whole path—so summing per-pair costs yields the global minimum.
- This matches the Chebyshev distance for grid movement with 8-directional steps.

---

## Complexity

- **Time:** $O(n)$ — single pass over the points.
- **Space:** $O(1)$ — constant extra variables.

---

## Example Walkthrough

Input: `[[1,1],[3,4],[-1,0]]`

- From `[1,1]` to `[3,4]`: `dx = 2`, `dy = 3` → cost `max(2,3) = 3`.
- From `[3,4]` to `[-1,0]`: `dx = 4`, `dy = 4` → cost `4`.
- Total time = `3 + 4 = 7` seconds.

---

## Reference Implementation (Java)

```java
class Solution {
	public int minTimeToVisitAllPoints(int[][] points) {
		int total = 0;
		for (int i = 1; i < points.length; i++) {
			int dx = Math.abs(points[i][0] - points[i - 1][0]);
			int dy = Math.abs(points[i][1] - points[i - 1][1]);
			total += Math.max(dx, dy);
		}
		return total;
	}
}
```
