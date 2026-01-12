
# How & Why: LeetCode 713 - Subarray Product Less Than K

## Problem

Given an array of positive integers `nums` and integer `k`, count the number of contiguous subarrays where the product of all elements is strictly less than `k`.

## Intuition

- All numbers are positive, so expanding a window only increases or keeps the product; shrinking decreases it. This monotonicity makes a sliding window work for product (similar to sum problems).

## Brute Force Approach

- **Idea:** Enumerate every subarray and compute product.
- **Complexity:** Time $O(n^2)$, Space $O(1)$; will TLE for large `n`.

## My Approach (Sliding Window) — from Solution.java

- **Idea:** Maintain a window `[left, right]` whose product is < k. Expand `right`, multiply in `nums[right]`; while product ≥ k, shrink from the left. Every time the window is valid, all subarrays ending at `right` and starting between `left..right` are valid: add `right - left + 1`.
- **Complexity:** Time $O(n)$ (each index enters/leaves window once), Space $O(1)$.
- **Core snippet:**

```java
int left = 0; long prod = 1; int ans = 0;
for (int right = 0; right < n; right++) {
	prod *= nums[right];
	while (prod >= k) prod /= nums[left++];
	ans += right - left + 1; // subarrays ending at right
}
```

## Most Optimal Approach

- The two-pointer sliding window is optimal for positive numbers. If zeros were allowed, they naturally reset the product and window.

## Edge Cases

- `k <= 1` → no subarray qualifies (product is at least 1); return 0.
- Single element >= k excludes itself; < k counts as 1.
- Large products: use `long` for safety in multiplication/division even though array values are positive.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force | Check every subarray product | O(n^2) | O(1) | Too slow |
| Sliding window (used) | Expand/shrink window maintaining product < k | O(n) | O(1) | Requires all nums > 0 |

## Example Walkthrough

`nums = [10,5,2,6], k = 100`

- right=0: prod=10 < 100 → add 1 (subarrays ending at 0) → total 1
- right=1: prod=50 < 100 → add 2 ([10,5],[5]) → total 3
- right=2: prod=100 → shrink: prod=10; add 2 ([5,2],[2]) → total 5
- right=3: prod=60 < 100 → add 4 ([5,2,6],[2,6],[6],[5,2,6]) but first one? check: Actually window [1,3] prod=60 valid → add 3 ([5,2,6],[2,6],[6]) → total 8
Result = 8.

## Insights

- Counting trick: when window is valid, all suffixes ending at `right` are valid; no need to enumerate them.
- Positivity of nums is crucial; negative or zero would break monotonicity and require different handling.

## References to Similar Problems

- 209. Minimum Size Subarray Sum (sliding window on sums)
- 1248. Count Number of Nice Subarrays (counting with window)
