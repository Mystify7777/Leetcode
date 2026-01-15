
# How & Why: LeetCode 2943 - Maximize Area of Square Hole in Grid

## Problem

You have an `n x m` wooden grid with horizontal bars removed at indices `hBars` and vertical bars removed at indices `vBars`. A square hole is formed by choosing consecutive missing bars. Find the maximum possible area of such a square hole.

## Intuition

- The side length of the biggest square hole is governed by the longest streak of consecutive missing horizontal bars and vertical bars. The usable side = `1 + longestConsecutiveMissing` in each direction; the square side is the smaller of the two.

## Brute Force Approach

- **Idea:** Try every pair of missing horizontal bars and vertical bars to form holes; compute sizes.
- **Complexity:** $O(|h|^2 + |v|^2)$; unnecessary.

## My Approach (Streaks after Sorting) — from Solution.java

- **Idea:** Sort `hBars` and `vBars`. Scan to find longest run where indices differ by 1. Side length = `1 + maxHRun` and `1 + maxVRun`; max square side = `min( sideH, sideV )`; area = side².
- **Complexity:** Time $O(|h| \log |h| + |v| \log |v|)$ for sorting, $O(|h|+|v|)$ scan; Space $O(1)$ extra.
- **Core snippet:**

```java
Arrays.sort(h); Arrays.sort(v);
int streakH = longestRun(h), streakV = longestRun(v);
int side = Math.min(streakH, streakV);
return side * side;

int longestRun(int[] a){
	int best=1, cur=1;
	for (int i=1;i<a.length;i++) {
		cur = (a[i]-a[i-1]==1) ? cur+1 : 1;
		best = Math.max(best, cur);
	}
	return best+1; // bars count -> cell side
}
```

## Most Optimal Approach

- Sorting plus single pass is optimal for arbitrary bar order. With pre-sorted input, it’s linear.

## Edge Cases

- Only one missing bar in a direction → longest run 1 → side contribution 2.
- Unequal runs: side is limited by the smaller direction.
- Duplicate bar indices: treat as gap break; make sure the streak logic resets when diff ≠ 1.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Pairwise try gaps | Enumerate bar pairs | O(h^2 + v^2) | O(1) | Overkill |
| Sort + streak scan (used) | Longest consecutive missing bars | O(h.log h + v.log v) | O(1) | Simple and fast |

## Example Walkthrough

`hBars = [1,2,4]`, `vBars = [3,4]`

- Horizontal streaks: 1,2 (run 2) ⇒ sideH = 3; vertical streak: singletons ⇒ sideV = 2.
- Max square side = min(3,2) = 2 → area = 4.

## Insights

- Consecutive missing bars dictate continuous gap length; side length is streak+1 because bars define boundaries between cells.

## References to Similar Problems

- 1138. Alphabet Board Path (grid indexing logic)
- 850. Rectangle Area II (grid gaps and spans reasoning)
