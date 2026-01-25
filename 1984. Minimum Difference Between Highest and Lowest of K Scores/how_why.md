# How Why Explanation - 1984. Minimum Difference Between Highest and Lowest of K Scores

## Problem

Given an integer array `nums` and an integer `k`, pick any `k` scores and return the minimum possible difference between the highest and lowest selected scores.

## Intuition

After sorting, any set of `k` elements with the minimal high-low gap must appear as a contiguous window. So we just need the smallest window spread over the sorted array.

## Brute Force (Not Used)

- Enumerate all size-`k` subsets, compute `max - min` for each, track the minimum.
- Complexity: \(O(\binom{n}{k} \cdot k)\), infeasible beyond tiny `n`.

## Approach (Sorted Sliding Window)

1. Sort `nums` ascending.
2. For every window `[i, i + k - 1]`, compute `nums[i + k - 1] - nums[i]` and keep the minimum.
3. Return the minimum spread found.

Why it works: sorting orders candidates so that the tightest `k`-element range is always contiguous, eliminating the need to consider non-contiguous picks.

## Complexity

- Time: \(O(n \log n)\) for sort + \(O(n)\) scan.
- Space: \(O(1)\) extra (sorting in place assumed).

## Optimality

The scanning after sort is linear; the \(O(n \log n)\) sort dominates. You cannot beat \(O(n \log n)\) without counting/radix sort assumptions. The quicksort alternative has the same expected complexity.

## Edge Cases

- `k == 1` or `n == 1` -> difference is 0.
- `k == n` -> difference is `max(nums) - min(nums)` (first and last after sort).
- Arrays with duplicates are handled naturally by the window diff.

## Comparison Table

| Aspect | Sorted window | Quicksort + window |
| --- | --- | --- |
| Sort step | `Arrays.sort` \(O(n \log n)\) | In-place quicksort expected \(O(n \log n)\), worst \(O(n^2)\) |
| Window scan | \(O(n)\) | \(O(n)\) |
| Extra space | \(O(1)\) | \(O(1)\) |
| Stability | Stable impl, not required | Unstable, not required |

## Key Snippet (Java)

```java
Arrays.sort(nums);
int ans = nums[k - 1] - nums[0];
for (int i = 0; i + k <= nums.length; i++) {
    ans = Math.min(ans, nums[i + k - 1] - nums[i]);
}
return ans;
```

## Example Walkthrough

Input: `nums = [9, 4, 1, 7]`, `k = 2`

1. Sort -> `[1, 4, 7, 9]`
2. Windows of size 2:
   - `[1, 4]` diff = 3
   - `[4, 7]` diff = 3
   - `[7, 9]` diff = 2 (best)
3. Answer = 2

## Insights

- Contiguity after sorting collapses subset search to a single pass.
- When element range is small, counting sort can reduce to \(O(n + R)\), but the window scan remains the same.

## References

- Solution implementation in [1984. Minimum Difference Between Highest and Lowest of K Scores/Solution.java](1984.%20Minimum%20Difference%20Between%20Highest%20and%20Lowest%20of%20K%20Scores/Solution.java)
