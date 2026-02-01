# How Why Explanation - 3010. Divide an Array Into Subarrays With Minimum Cost I

## Problem

Given an integer array, you must split it into exactly three contiguous subarrays. The cost is the sum of the minimum element of each subarray. Return the minimum possible total cost.

## Intuition

With exactly three parts, the first element must belong to the first subarray, and the split points only affect which two other elements become the minima of the remaining subarrays. It turns out the optimal strategy is simply: take the first element as the first subarray's minimum, then take the two smallest elements among the remaining positions as the minima of the second and third subarrays.

## Brute Force (Not Used)

- Enumerate all two cut positions to form three segments, compute each segment minimum, take the best.
- Complexity: $O(n^2)$ time, $O(1)$ space; too slow for large $n$.

## Approach (Pick two smallest after the first)

1. The first subarray must include index 0; its minimum is at least `A[0]`. Choosing any earlier cut cannot reduce `A[0]` being counted.
2. For the other two subarrays, you need two minima from indices `1..n-1`. The best is to pick the two smallest values in that range; any partition can place each of them as the minimum of one subarray.
3. Scan once to find the smallest (`a`) and second smallest (`b`) elements after the first position.
4. Answer is `A[0] + a + b`.

Why it works: You can always place the globally smallest element (after index 0) alone in a subarray to make it that subarray's minimum, and similarly for the second smallest. No partition can produce a lower minimum than the global minima of those ranges.

## Complexity

- Time: $O(n)$ single pass.
- Space: $O(1)$.

## Optimality

Any solution must inspect elements to know minima; a single pass is optimal. Using the two global minima is provably minimal because minima are monotone with respect to set inclusion.

## Edge Cases

- Array length exactly 3: the only split yields each element as a minimum; result is the sum of all elements.
- Duplicate smallest values: both minima can be the same value if it appears twice.
- Very small/large numbers: use int arithmetic; bounds in code cap at 51 in first variant, `Integer.MAX_VALUE` in second.

## Comparison Table

| Aspect | Single-pass minima (Solution) | Single-pass minima clear init (Solution2) |
| --- | --- | --- |
| Initialization | `a = b = 51` (problem constraints) | `Integer.MAX_VALUE` | 
| Logic | Track two mins over suffix | Same | 
| Complexity | $O(n)$ / $O(1)$ | $O(n)$ / $O(1)$ |

## Key Snippet (Java)

```java
int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
for (int i = 1; i < nums.length; i++) {
	if (nums[i] < min1) {
		min2 = min1;
		min1 = nums[i];
	} else if (nums[i] < min2) {
		min2 = nums[i];
	}
}
return nums[0] + min1 + min2;
```

## Example Walkthrough

`A = [1, 2, 3, 12]`

- Smallest two after first: 2 and 3.
- Cost = 1 (first) + 2 + 3 = 6.
- One valid partition achieving this: `[1] | [2] | [3, 12]`.

## Insights

- The structure (exactly three subarrays) simplifies to picking two global minima after the first position; no detailed partitioning DP is needed.
- If constraints bound values (as in Solution), initialization can use that bound; otherwise use `Integer.MAX_VALUE`.

## References

- Solution implementation in [3010. Divide an Array Into Subarrays With Minimum Cost I/Solution.java](3010.%20Divide%20an%20Array%20Into%20Subarrays%20With%20Minimum%20Cost%20I/Solution.java)
