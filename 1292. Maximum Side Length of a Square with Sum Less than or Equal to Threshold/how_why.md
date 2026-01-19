
# How & Why: LeetCode 1292 - Maximum Side Length of a Square with Sum ≤ Threshold

## Problem

Given an `m x n` matrix of positive ints and an integer `threshold`, find the largest side length `k` of a square submatrix whose element sum is ≤ `threshold`.

## Intuition

- Sums of arbitrary submatrices can be answered fast with 2D prefix sums. Then we can test any square in O(1). We can search for the maximum `k` either by binary search on side length or by incremental growth.

## Brute Force Approach

- **Idea:** For each cell as top-left, for each possible `k`, sum the `k x k` square directly.
- **Complexity:** $O(m n k^2)$ in worst case → too slow.

## My Approach (2D Prefix Sum + Binary Search) — from Solution.java

- **Idea:**
	1) Build 2D prefix sums `pref` (using in-place row+col accumulation).
	2) Binary search `k` in [1, min(m,n)].
	3) `isValid(k)`: scan all `k x k` squares; get sum in O(1) via prefix; if any ≤ threshold, return true.
- **Complexity:** Prefix build $O(mn)$; `isValid` $O(mn)$; binary search adds `log(min(m,n))` factor → overall $O(mn \, log S)`. Space $O(1)$ extra if done in-place, else $O(mn)` for a separate prefix.

- **Core snippet:**

```java
// sum of square ending at (i,j) of size k
int sum = pref[i][j]
				- (x1>0 ? pref[x1-1][j] : 0)
				- (y1>0 ? pref[i][y1-1] : 0)
				+ (x1>0 && y1>0 ? pref[x1-1][y1-1] : 0);
```

## Most Optimal Approach

- Binary search on k + prefix sums is optimal and clean. Alternatively, sweep k upward while maintaining current best (Solution2) in $O(mn \cdot K)$ where K is answer but without log factor; both are fine.

## Edge Cases

- No square fits (all entries > threshold) → answer 0.
- Single cell ≤ threshold → answer at least 1.
- Large sums: use `int` for prefix if constraints allow; otherwise `long` to avoid overflow.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force summing | Sum each square directly | O(mn k^2) | O(1) | Too slow |
| Prefix + grow (Solution2) | In-place prefix, increment k | O(mn · K) | O(1) extra | No log factor, depends on answer |
| Prefix + binary search (used) | O(1) square sum, search k | O(mn log S) | O(1) extra | Deterministic bounds |

## Example Walkthrough

`mat = [[1,1,3],[1,1,3],[1,1,3]], threshold=4`

- Prefix built. Binary search k=2 valid (sums 4). Try k=3 → sum 12 > 4, so max k=2.

## Insights

- 2D prefix sums reduce any submatrix sum to 4 lookups; pairing with a search over size is a standard pattern for bounded-sum square/rectangle queries.

## References to Similar Problems

- 1444. Number of Ways of Cutting a Pizza (2D prefix for sub-rect sums)
- 3047/2975/2943 (grid spans and size optimization)
