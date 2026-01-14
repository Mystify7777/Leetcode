
# How & Why: LeetCode 3454 - Separate Squares II

## Problem

Given axis-aligned squares on the plane (bottom-left `(x, y)`, side `len`), find a horizontal line `y = h` that splits the total union area exactly in half. Return `h` as a double; if multiple, any is fine.

## Intuition

- The union area changes only at square boundaries. Sweeping horizontal lines from bottom to top, we can accumulate area strip by strip. The target `h` is where prefix area first reaches half the total.
- Each strip’s area = strip height × union width of active x-intervals. We need efficient union-width tracking while sweeping y.

## Brute Force Approach

- **Idea:** Rasterize or sample many horizontal lines, compute union area below each by merging intervals per line.
- **Complexity:** Very high (depends on resolution); inaccurate and infeasible.

## My Approach (Sweep Line + Interval Union) — from Solution

- **Idea:**

	1) Create events at each square’s bottom (`+1`) and top (`-1`) with its `[x, x+l]` interval.
	2) Sweep events by y. Between consecutive event y-levels, active x-intervals stay constant → union width is fixed.
	3) For each gap `(prevY, curY)`, add strip area = height × unionWidth; record strip info.
	4) After total area known, walk strips again to find `h` where prefix hits half: `h = y_bottom + remaining / width`.
- **Union width:** sort active intervals, merge overlaps each time we process a strip (simplicity over optimality).
- **Complexity:** Time $O(n^2)$ worst-case due to repeated interval sorts/merges; Space $O(n)$ for events/active list/strips.
- **Core snippet:**

```java
// process gap
double width = unionWidth(active); // merge sorted intervals
double height = eventY - prevY;
total += width * height; strips.add([prevY, height, width]);

// update active by event type
if (type == +1) active.add([x1, x2]); else active.remove([x1, x2]);

// second pass to find h
double need = total / 2, pref = 0;
for (strip : strips) {
		double area = strip.h * strip.w;
		if (pref + area >= need) return strip.y + (need - pref) / strip.w;
		pref += area;
}
```

## Most Optimal Approach

- Use sweep line with coordinate-compressed x and a segment tree (Solution2) to maintain union width in $O(\log n)$ per event, giving overall $O(n \log n)$ time and $O(n)$ space. Same two-pass idea for locating `h`.

## Edge Cases

- Single square → `h` is its bottom y plus half its side.
- Disjoint squares stacked vertically → `h` lands in the strip where cumulative area crosses half.
- Identical overlapping squares → treat as one union; events stack but union width remains the same.
- No squares (if allowed) → undefined; the provided code returns `-1`.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute sample lines | Sample many y-levels | High / inaccurate | High | Not viable |
| Sweep + merge intervals (used) | Sweep y, merge active x each strip | Worst $O(n^2)$ | O(n) | Simple to implement |
| Sweep + segment tree (Solution2) | Sweep y, union width via segtree | $O(n \log n)$ | O(n) | Optimal and scalable |

## Example Walkthrough

Squares: `[(0,0,2), (1,0,2)]`

- Events at y=0 start [0,2] and [1,3]; union width=3.
- At y=2 both end. Total area = 3 * 2 = 6 → target = 3.
- Single strip: need 3 / width 3 → `h = 0 + 1 = 1`. So `y=1` splits area evenly.

## Insights

- Area only changes at event y-levels; you never need to test arbitrary `y`.
- The problem reduces to finding the prefix-length along y where cumulative strip areas reach half.

## References to Similar Problems

- 850. Rectangle Area II (union area via sweep + segment tree)
- 391. Perfect Rectangle (sweep line / interval union patterns)
