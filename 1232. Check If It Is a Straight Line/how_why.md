
# How & Why: LeetCode 1232 - Check If It Is a Straight Line

## Problem

Given `n` coordinates in 2D, determine if all points lie on a single straight line.

## Intuition

- Points are collinear if every point shares the same slope with respect to a reference segment. Cross products avoid division and floating error.

## Brute Force Approach

- **Idea:** Compute slope between every pair of points and compare.
- **Complexity:** Time $O(n^2)$, Space $O(1)$; redundant work.

## My Approach (Cross-Product Slope Check) — from Solution.java

- **Idea:** Take the first two points as the base direction `(dx, dy)`. For each point `(x, y)`, ensure `dx*(y - y1) == dy*(x - x1)` (zero cross product → same line).
- **Complexity:** Time $O(n)$, Space $O(1)$.
- **Core snippet:**

```java
int dx = x1 - x0, dy = y1 - y0;
for (int[] p : coords) {
	if (dx * (p[1]-y1) != dy * (p[0]-x1)) return false;
}
return true;
```

## Most Optimal Approach

- The $O(n)$ cross-product scan is optimal; no faster asymptotic method exists.

## Edge Cases

- Exactly 2 points → always collinear.
- Duplicate points still satisfy the equation.
- Large coordinates: use `long` if overflow is a concern.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Pairwise slopes | Compare every pair | O(n^2) | O(1) | Overkill |
| Cross-product scan (used) | Fix base segment; check all points | O(n) | O(1) | Simple and robust |

## Example Walkthrough

Coords: `[[1,2],[2,3],[3,4],[4,5]]`

- Base `(dx,dy) = (1,1)` from first two points.
- For each point, `dx*(y - y1) == dy*(x - x1)` holds → return true.

## Insights

- Cross product zero test generalizes collinearity and avoids division.

## References to Similar Problems

- 149. Max Points on a Line (uses slope/cross-product collinearity)
