# How Why Explanation - 3634. Minimum Removals to Balance Array

## Problem

Given an integer array `nums` and integer `k`, remove the fewest elements so that in the remaining array `max <= k * min`. Return the minimum number of removals.

## Intuition

After sorting, the condition `max <= k * min` must hold within any valid subarray. So the task reduces to finding the longest contiguous segment in the sorted array that satisfies `nums[r] <= k * nums[l]`. Keeping that segment and removing everything else minimizes deletions.

## Brute Force (Not Used)

- Check every subarray `(l, r)`, test if it satisfies the ratio, track the longest.
- Complexity: $O(n^3)$ naive or $O(n^2)$ with prefix mins/maxes; too slow.

## Approach (Sort + Sliding Window)

1. Sort `nums` ascending.
2. Use two pointers `l`, `r` to maintain a window with `nums[r] <= k * nums[l]`.
3. For each `r`, advance `l` while the condition fails. Update `maxLen` with `r - l + 1`.
4. Answer is `n - maxLen` removals.

Why it works: In sorted order, the minimum of a window is at `l` and maximum at `r`. Sliding preserves order and finds the longest feasible segment in linear time.

## Complexity

- Time: $O(n \log n)$ for sort + $O(n)$ scan.
- Space: $O(1)$ extra (excluding sort buffer if in-place).

## Optimality

Sorting is necessary to reason about global min/max per segment. The two-pointer scan is linear and optimal after sorting.

## Edge Cases

- `k == 1`: require all elements equal; window collapses to runs of identical values.
- Single element: already balanced, removals = 0.
- Large values: use `long` in the product comparison to avoid overflow.

## Comparison Table

| Aspect | Two-pointer (Solution) | Two-pointer verbose (Solution2) |
| --- | --- | --- |
| Sort | `Arrays.sort` | `Arrays.sort` |
| Window logic | `(long) nums[j] > (long) nums[i] * k` | Same condition | 
| Complexity | $O(n \log n)$ / $O(1)$ | $O(n \log n)$ / $O(1)$ |

## Key Snippet (Java)

```java
Arrays.sort(nums);
int i = 0, maxLen = 0;
for (int j = 0; j < nums.length; j++) {
		while ((long) nums[j] > (long) nums[i] * k) i++;
		maxLen = Math.max(maxLen, j - i + 1);
}
return nums.length - maxLen;
```

## Example Walkthrough

`nums = [1, 3, 6, 10], k = 3`

- Sorted already. Expand window:
	- `i=0,j=0`: ok (1 vs 1)
	- `j=1`: 3 <= 3*1 -> ok (len 2)
	- `j=2`: 6 <= 3*1 -> ok (len 3)
	- `j=3`: 10 > 3*1 -> move `i` to 1 (10 <= 3*3? yes). Window len = 3 (`[3,6,10]`).
- MaxLen = 3; removals = 4 - 3 = 1 (remove 1).

## Insights

- Sorting turns the global ratio constraint into a local window check.
- Using `long` prevents overflow when `k` and `nums[i]` are large.
- Longest valid window ↔ minimum removals; a standard complement trick.

## References

- Solution implementation in [3634. Minimum Removals to Balance Array/Solution.java](3634.%20Minimum%20Removals%20to%20Balance%20Array/Solution.java)
