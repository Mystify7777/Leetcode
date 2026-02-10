# How Why Explanation - 3719. Longest Balanced Subarray I

## Problem

Given an integer array `nums`, find the length of the longest subarray where the number of distinct even values equals the number of distinct odd values.

## Intuition

We need distinct counts of evens vs odds in a subarray. A brute-force double loop can expand subarrays and maintain sets of seen evens and odds; whenever the two set sizes match, update the answer. Constraints in this variant are small enough for $O(n^2)$.

## Brute Force (Used)

- For each start `i`, expand `j` to the right, insert `nums[j]` into even/odd set, and check size equality.
- Complexity: $O(n^2)$ time, $O(n)$ space for sets per start (can be optimized to reused arrays as in Solution2).

## Approach (Nested loop with distinct tracking)

1. Loop start index `i` from 0..n-1.
2. Maintain two containers for distinct evens/odds in the current window (`i..j`).
3. Extend `j`; add `nums[j]` to the appropriate container if new.
4. If `|even| == |odd|`, update `ans` with window length.
5. Return max length found.

Why it works: Distinctness requires tracking membership. Scanning all subarrays ensures the longest qualifying window is found; set size equality is checked incrementally.

## Complexity

- Time: $O(n^2)$.
- Space: $O(n)$ for distinct tracking within a window (optimized to $O(U)$ with timestamp arrays in Solution2, where $U$ is value bound).

## Optimality

For the I variant with small limits, $O(n^2)$ is acceptable. The timestamp-array optimization avoids set allocations per window while keeping the same asymptotic time.

## Edge Cases

- Single element: distinct counts are (1,0) or (0,1); answer 0 or 1? Needs both sides equal → length 0 (unless both zero, which can't happen), so result 0.
- All even or all odd: no balanced subarray, answer 0.
- Repeated values: do not increase distinct count after first occurrence in window.

## Comparison Table

| Aspect | Set-based nested loops (Solution) | Timestamp array reuse (Solution2) |
| --- | --- | --- |
| Distinct tracking | HashSet per parity | Global arrays with per-iteration timestamps |
| Time | $O(n^2)$ | $O(n^2)$ (less overhead) |
| Space | $O(n)$ dynamic | $O(U)$ fixed (value-bounded) |
| Micro-opts | None | Break early if remaining length ≤ best |

## Key Snippet (Java)

```java
int ans = 0;
for (int i = 0; i < n; i++) {
	Set<Integer> e = new HashSet<>();
	Set<Integer> o = new HashSet<>();
	for (int j = i; j < n; j++) {
		if (nums[j] % 2 == 0) e.add(nums[j]); else o.add(nums[j]);
		if (e.size() == o.size()) ans = Math.max(ans, j - i + 1);
	}
}
return ans;
```

## Example Walkthrough

`nums = [1, 2, 2, 1, 3]`

- From `i=0`, window to `j=3` has evens `{2}`, odds `{1}` → not equal; to `j=4` odds `{1,3}` evens `{2}` → sizes equal (2 vs 1? actually 1 vs 2) nope. From `i=1`, window `1..4`: evens `{2}`, odds `{1,3}` sizes 1 vs 2 not equal. Best balanced window is `i=0..3`? Check: odds `{1}`, evens `{2}` sizes 1 vs 1 length 4 after pruning duplicates? Actually window `0..3` distinct odds `{1}`, evens `{2}` sizes equal → length 4 (subarray `[1,2,2,1]`). Answer = 4.

## Insights

- Distinct counting differs from frequency; duplicates after first do not change parity 
counts.
- Timestamp arrays are a useful micro-optimization to avoid per-window allocations when values are bounded.

## References

- Solution implementation in [3719. Longest Balanced Subarray I/Solution.java](3719.%20Longest%20Balanced%20Subarray%20I/Solution.java)
