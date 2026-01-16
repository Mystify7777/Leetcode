
# How & Why: LeetCode 2975 - Maximum Square Area by Removing Fences From a Field

## Problem

You have an `m x n` field with horizontal fence positions `hFences` and vertical fence positions `vFences` (1-indexed). By removing some fences, you can form rectangular gaps. Find the maximum possible area of a square gap you can form; return `-1` if no square is possible. Answer is mod $1e9+7$.

## Intuition

- A square requires equal height and width. Possible heights are all pairwise differences between horizontal fence lines (including boundaries 1 and m). Possible widths are pairwise differences between vertical fence lines (including 1 and n). The largest common value between these two difference sets is the side of the biggest square.

## Brute Force Approach

- **Idea:** Enumerate every horizontal pair and every vertical pair, compute intersection area if equal.
- **Complexity:** $O(|h|^2 * |v|^2)$ — too slow.

## My Approach (Pairwise Differences + Set) — from Solution.java

- **Idea:**
	1) Add boundaries 1 and m to `hFences`; 1 and n to `vFences`.
	2) Compute all pairwise diffs among horizontals; store in a set.
	3) Compute pairwise diffs among verticals; if a diff exists in the horizontal set, update best side.
	4) Return `best*best % MOD` or `-1` if none.
- **Complexity:** Time $O(h^2 + v^2)$, Space $O(h^2)` for the set (fits constraints).
- **Core snippet:**

```java
List<Integer> H = withBounds(hFences, 1, m);
List<Integer> V = withBounds(vFences, 1, n);
Set<Integer> diffs = new HashSet<>();
for (int i=0;i<H.size();i++) for (int j=i+1;j<H.size();j++) diffs.add(abs(H.get(j)-H.get(i)));
long best=0;
for (int i=0;i<V.size();i++) for (int j=i+1;j<V.size();j++) {
		int d = Math.abs(V.get(j)-V.get(i));
		if (diffs.contains(d)) best = Math.max(best, d);
}
return best==0 ? -1 : (int)((best*best)%1_000_000_007L);
```

## Most Optimal Approach

- With current constraints, $O(h^2 + v^2)$ is sufficient. If much larger, you could hash differences from the smaller side to reduce memory or use frequency arrays after sorting and two-pointer on diffs.

## Edge Cases

- No common diff between horizontal and vertical → return -1.
- Only boundaries (no fences): side = min(m-1+1, n-1+1) = min(m, n), so square of full field.
- Duplicated fence positions: treat as same line; diff 0 should not be considered (skip or it won’t match positive diffs).

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Full pairwise cross | Try all horizontal pairs × vertical pairs | O(h^2 v^2) | O(1) | Too slow |
| Pairwise diff + set (used) | Common side length via diff sets | O(h^2 + v^2) | O(h^2) | Simple, passes constraints |
| Diff hashing smaller side | Store diffs of smaller dimension only | O(h^2 + v^2) | min(O(h^2),O(v^2)) | Memory tweak if needed |

## Example Walkthrough

`m=4, n=3, h=[2,3], v=[2]`

- H with bounds: [1,2,3,4] → diffs include 1,2,3.
- V with bounds: [1,2,3] → diffs include 1,2.
- Common max diff = 2 → area = 4.

## Insights

- Side lengths are exactly the set of pairwise distances between fence lines including boundaries; the problem reduces to max common distance.

## References to Similar Problems

- 2943. Maximize Area of Square Hole in Grid (consecutive-bar streak variant)
- 850. Rectangle Area II (interval spans reasoning)
