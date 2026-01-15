# How & Why: LeetCode 812 - Largest Triangle Area

## Problem

Given `n` points in 2D, return the maximum area of any triangle formed by three distinct points.

## Intuition

- Use the shoelace (cross-product) formula for triangle area; no need for trigonometry.
- Input size is small (`n <= 50`), so checking all triplets is fine.

## Brute Force Approach

- **Idea:** Enumerate all triples, compute area with the shoelace formula, take the max.
- **Complexity:** Time $O(n^3)$, Space $O(1)$; acceptable for constraints.

## My Approach (Shoelace over all triplets) — from Solution.java

- **Idea:** For each `(i,j,k)`, area = $0.5 * |x_1(y_2-y_3) + x_2(y_3-y_1) + x_3(y_1-y_2)|$; track the maximum.
- **Complexity:** Time $O(n^3)$, Space $O(1)$.
- **Core snippet:**

```java
double best = 0;
for (int i=0;i<n;i++) for (int j=i+1;j<n;j++) for (int k=j+1;k<n;k++) {
    int x1=pts[i][0], y1=pts[i][1];
    int x2=pts[j][0], y2=pts[j][1];
    int x3=pts[k][0], y3=pts[k][1];
    double area = 0.5 * Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2));
    best = Math.max(best, area);
}
```

## Most Optimal Approach

- Asymptotically the $O(n^3)$ brute force is optimal under given limits; no faster method needed. Use shoelace for correctness and simplicity.

## Edge Cases

- All points collinear → maximum area is 0.
- Duplicate points → still handled; triangles with coincident vertices have area 0.
- Very large coordinates → use `double`/`long` in cross-product to avoid overflow.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Shoelace over all triplets (used) | Enumerate triples, cross-product area | O(n^3) | O(1) | Correct, simple, fine for n<=50 |
| Trapezoid sum (discarded) | Approx via trapezoids on edges | Wrong | O(n^3) | Not geometrically correct |

## Example Walkthrough

`points = [[0,0],[0,2],[2,0],[0,1]]`

- Triangle (0,0),(0,2),(2,0): area = 2.0
- Other triangles are smaller or equal → maximum = 2.0.

## Insights

- Shoelace/cross-product is the canonical way to compute polygon/triangle area robustly with integers.

## References to Similar Problems

- 149. Max Points on a Line (collinearity checks use cross products)
- 963. Minimum Area Rectangle II (geometry on point sets)
