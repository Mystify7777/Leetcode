# How & Why: LeetCode 3047 - Find the Largest Area of Square Inside Two Rectangles

## Problem

Given `n` axis-aligned rectangles (bottom-left `bl[i]`, top-right `tr[i]`), find the largest possible area of a square that lies inside the intersection of any two rectangles. If no positive-area square fits in any pair’s intersection, the answer is 0.

## Intuition

- A square that is inside both rectangles must be inside their intersection. The largest square that fits in a rectangle is limited by its smaller side. So for each pair, the best square side is `min(overlapWidth, overlapHeight)`.

## Brute Force Approach

- **Idea:** Enumerate all pairs of rectangles (there are $O(n^2)$), compute their intersection, take the max possible square side.
- **Complexity:** $O(n^2)$ time, $O(1)$ space; acceptable for constraints.

## My Approach (Pairwise Intersection) — from Solution.java

- **Idea:** For each pair `(i,j)`, overlap width = `min(tr[i].x, tr[j].x) - max(bl[i].x, bl[j].x)`, overlap height similarly. If both > 0, candidate side = `min(width, height)`. Track the max side; area = side².
- **Complexity:** Time $O(n^2)$, Space $O(1)$.
- **Core snippet:**

```java
long best = 0;
for (int i=0;i<n;i++) for (int j=i+1;j<n;j++) {
	int w = Math.min(tr[i][0], tr[j][0]) - Math.max(bl[i][0], bl[j][0]);
	int h = Math.min(tr[i][1], tr[j][1]) - Math.max(bl[i][1], bl[j][1]);
	if (w > 0 && h > 0) best = Math.max(best, Math.min(w, h));
}
return best * best;
```

## Most Optimal Approach

- The pairwise $O(n^2)$ scan is optimal given we must consider every pair to guarantee correctness. No extra data structures needed.

## Edge Cases

- No overlap in a pair (w <= 0 or h <= 0) → skip.
- Degenerate overlap (line/point) yields side ≤ 0 → ignored.
- Large coordinates: use `long` for area (`side * side`).

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Pairwise intersection (used) | Check each pair’s overlap, take min side | O(n^2) | O(1) | Simple, sufficient |
| Sweep line by x/y | Would still need pair info | ≥ O(n^2) | Higher | Unnecessary overhead |

## Example Walkthrough

Rectangles: `[(0,0)-(3,4)]`, `[(1,1)-(4,5)]`, `[(5,5)-(6,6)]`

- Pair 0,1: overlap w=2, h=3 → side=2.
- Pair 0,2: no overlap.
- Pair 1,2: no overlap.
- Best side=2 → area=4.

## Insights

- Intersection of two axis-aligned rectangles is axis-aligned; the limiting factor for a square is the smaller side of that intersection.

## References to Similar Problems

- 223. Rectangle Area (overlap computation)
- 3047’s companion problems on grid/fence squares (2943, 2975)
