
# How & Why: LeetCode 223 - Rectangle Area

## Problem

Given two axis-aligned rectangles A and B (each by bottom-left `(x1,y1)` and top-right `(x2,y2)`), return the total area covered by both rectangles (union area). Rectangles may overlap or be disjoint.

## Intuition

- Total union area = area(A) + area(B) - overlapArea.
- Overlap in x exists only if `left < right`; overlap in y exists only if `bottom < top`.

## Brute Force Approach

- **Idea:** Rasterize/grid-scan or use inclusion per unit cell. Not practical and overkill.
- **Complexity:** Proportional to coordinate span; unnecessary.

## My Approach (Analytic Overlap) — from Solution.java

- **Idea:**
	1) Compute each rectangle area.
	2) Overlap width = `min(x2A, x2B) - max(x1A, x1B)`, overlap height similarly.
	3) If both positive, overlap area = width * height; else 0.
	4) Union = areaA + areaB - overlap.
- **Complexity:** Time $O(1)$, Space $O(1)$.
- **Core snippet:**

```java
int areaA = (C-A) * (D-B);
int areaB = (G-E) * (H-F);
int w = Math.min(C, G) - Math.max(A, E);
int h = Math.min(D, H) - Math.max(B, F);
int overlap = (w > 0 && h > 0) ? w * h : 0;
return areaA + areaB - overlap;
```

## Most Optimal Approach

- The constant-time analytic computation is optimal. Guard overflow with `long` if coordinates are large.

## Edge Cases

- Non-overlapping rectangles → overlap 0.
- Touching edges/corners → width or height = 0 → overlap 0.
- Negative coordinates are fine; formula still holds.
- Large coordinates: use `long` to avoid overflow in area products.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Rasterize/grid | Mark covered cells | Very high | High | Impractical |
| Analytic overlap (used) | AreaA + AreaB - overlap | O(1) | O(1) | Simple and exact |

## Example Walkthrough

Rect A: `(0,0)-(2,2)`, Rect B: `(1,1)-(3,3)`

- areaA=4, areaB=4
- overlap w = min(2,3)-max(0,1)=1; h = min(2,3)-max(0,1)=1 → overlap=1
- union = 4 + 4 - 1 = 7

## Insights

- Overlap only if both width and height of intersection are positive; equality means just touching with zero overlap.

## References to Similar Problems

- 836. Rectangle Overlap (boolean overlap check)
- 850. Rectangle Area II (union area of many rectangles)
