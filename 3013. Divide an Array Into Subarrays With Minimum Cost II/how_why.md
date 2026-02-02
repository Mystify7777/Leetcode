# How Why Explanation - 3013. Divide an Array Into Subarrays With Minimum Cost II

## Problem

Given an array `nums`, integer `k`, and integer `dist`, divide the array into exactly `k` contiguous subarrays such that:

- The first subarray must include `nums[0]`.
- For each subarray (except the first), its leftmost index is at most `dist` positions away from the previous subarray's leftmost index.
Return the minimum possible sum of the minimum elements from each of the `k` subarrays.

## Intuition

The first element `nums[0]` is fixed as one minimum. For the remaining `k-1` subarrays, we need to select `k-1` starting positions from indices `1..n-1`, and the distance constraint means each choice must be within `dist` of the previous. To minimize the sum, we want the `k-1` smallest values among valid choices. This becomes a sliding window problem: maintain a window of size up to `dist+1` and track the sum of the `k-1` smallest elements in it.

## Brute Force (Not Used)

- Enumerate all valid ways to pick `k-1` starting positions respecting the `dist` constraint, compute the sum of minima.
- Exponential complexity; infeasible.

## Approach (Sliding Window with Two-Heap Partition)

1. **Setup**: We need to pick `k-1` minima from positions `1..n-1`. The first choice is within `[1, 1+dist]`, then subsequent choices slide the window.
2. **Smart Window**: Maintain a data structure (two TreeMaps or heaps) that:
   - Splits elements into "low" (the `k-1` smallest) and "high" (the rest).
   - Tracks `sumLow` = sum of the `k-1` smallest.
   - Supports efficient add/remove with rebalancing.
3. **Sliding**:
   - Initially add elements `nums[1..1+dist]` to the window; query `sumLow`.
   - For each next position `i`, remove the oldest element `nums[i-1]`, add the new element `nums[i+dist]`, and update the answer with `sumLow`.
4. **Answer**: `nums[0] + min(sumLow over all valid windows)`.

Why it works: the `dist` constraint defines a sliding window of valid candidates. Maintaining the `k-1` smallest in the window gives the optimal choice for each window position.

## Complexity

- Time: $O(n \log n)$ for each add/remove operation in TreeMap; $O(n \log n)$ total with $O(n)$ operations.
- Space: $O(dist)$ for elements in the window.

## Optimality

Maintaining sorted structure with dynamic min-k tracking is necessary; TreeMap or balanced heap structure provides the log factor per operation, which is near-optimal for dynamic order statistics without additional preprocessing.

## Edge Cases

- `k == 2`: only one minimum to pick from the suffix; simplifies to finding the min in a sliding window.
- `dist >= n-2`: all positions are valid; pick the `k-1` global smallest after the first.
- Duplicates: the structure handles them via counts in TreeMap.
- `k == n`: each element forms its own subarray; sum is the total.

## Comparison Table

| Aspect | Two TreeMaps (Solution) | Two Heaps with lazy deletion (Solution2) |
| --- | --- | --- |
| Low partition | TreeMap tracking counts | Max-heap with validity counter |
| High partition | TreeMap tracking counts | Min-heap |
| Rebalance | Move between maps | Adjust valid count + lazy cleanup |
| Complexity | $O(n \log n)$ | $O(n \log n)$ |
| Space | $O(dist)$ | $O(dist)$ + lazy delete map |

## Key Snippet (Java)

```java
SmartWindow window = new SmartWindow(k - 1);
for (int i = 1; i <= 1 + dist; i++) {
    window.add(nums[i]);
}
long ans = window.query();

for (int i = 2; i + dist < n; i++) {
    window.remove(nums[i - 1]);
    window.add(nums[i + dist]);
    ans = Math.min(ans, window.query());
}
return ans + nums[0];
```

## Example Walkthrough

`nums = [1, 3, 2, 6, 4, 2]`, `k = 3`, `dist = 3`

- Fixed: `nums[0] = 1`.
- Need `k-1 = 2` minima from indices `1..5` with distance constraint.
- Initial window `[1..4]` = `[3,2,6,4]`; two smallest: 2,3; sum = 5.
- Slide: remove 3, add 2 (index 5); window `[2..5]` = `[2,6,4,2]`; two smallest: 2,2; sum = 4.
- Answer: `1 + 4 = 5`.

## Insights

- The distance constraint creates a sliding window; tracking the min-k in the window avoids recomputing from scratch.
- Two-heap or two-TreeMap partition is a classic pattern for dynamic median/top-k queries.
- Lazy deletion (Solution2) defers cleanup until heap tops are accessed, trading bookkeeping overhead for simpler balance logic.

## References

- Solution implementation in [3013. Divide an Array Into Subarrays With Minimum Cost II/Solution.java](3013.%20Divide%20an%20Array%20Into%20Subarrays%20With%20Minimum%20Cost%20II/Solution.java)
