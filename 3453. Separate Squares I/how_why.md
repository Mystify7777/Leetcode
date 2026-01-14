# How & Why: LeetCode 3453 - Separate Squares I

## Problem

Find the minimum y-coordinate of a horizontal line that splits the total area of given axis-aligned squares into equal top and bottom parts (squares can overlap).

## Intuition

- As the horizontal line moves upward, area above decreases monotonically and area below increases. The balance point can be found via binary search on y.

## Brute Force Approach

- **Idea:** Sample many y-levels and compute areas each time.
- **Complexity:** Very high and imprecise; not practical.

## My Approach (Binary Search on y) — from Solution.java

- **Idea:** Binary search over y; for a candidate y, compute area_above and area_below by intersecting the line with each square. Move search window based on which side has more area.
- **Complexity:** Time $O(n \cdot \text{iters})$ (≈60 iters), Space $O(1)$.
- **Core snippet:**

```java
double diff(double y){
	double above=0, below=0;
	for (int[] s: squares){
		double top=s[1]+s[2], bot=s[1], side=s[2];
		if (y<=bot) above+=side*side;
		else if (y>=top) below+=side*side;
		else { double ah=top-y, bh=y-bot; above+=side*ah; below+=side*bh; }
	}
	return above-below;
}
double lo=0, hi=2e9;
for(int i=0;i<60;i++){ double mid=(lo+hi)/2; if(diff(mid)>0) lo=mid; else hi=mid; }
return hi;
```

## Most Optimal Approach

- Same binary search, but tighten bounds to [minY, maxY] and reuse total area (Solution2). Complexity unchanged; numerically cleaner.

## Edge Cases

- Single square → answer is its bottom y plus half its side.
- All squares strictly above/below initial bounds: ensure bounds cover all squares (use minY/maxY).
- Overlapping squares: count full area for each; overlaps accumulate as required.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute sampling | Try many y samples | High | O(1) | Inaccurate/slow |
| Binary search (used) | Monotone area diff, wide bounds | O(n·iters) | O(1) | Simple; uses loose [0,2e9] |
| Binary search with tight bounds (Solution2) | Same, but [minY,maxY] | O(n·iters) | O(1) | Better numeric stability |

## Example Walkthrough

Squares: `[(0,0,2)]`

- Total area = 4. Binary search finds y where above=below=2 → y = 0 + 1 = 1.

## Insights

- Defining a monotone function (area_above - area_below) enables root-finding via binary search without explicit integration.

## References to Similar Problems

- 3454. Separate Squares II (sweep-line exact split)
- 410. Split Array Largest Sum (binary search on monotone predicate pattern)
