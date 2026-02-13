# How Why Explanation - 3721. Longest Balanced Subarray II

## Problem

Given an integer array `nums`, find the length of the longest subarray in which the number of **distinct even values** equals the number of **distinct odd values**.

## Intuition

Balance depends on distinctness, not frequency. For any fixed left boundary `l`, if we mark only the **first occurrence** of each value in `nums[l..]`, then the balance of evens vs odds in a window `[l, r]` is just the sum of (+1 for even, -1 for odd) over those first-occurrence markers inside `[l, r]`. We can slide `l` from right to left, maintaining where each value first appears, and quickly find the farthest `r` where the prefix balance is zero.

## Brute Force (Not Used)

- For each `l`, track distinct evens/odds to the right and update answer; $O(n^2)$.
- Too slow for larger `n`.

## Approach (Right-to-left with segment tree)

1. Maintain a map `first[val]` storing the leftmost index (from the current `l` perspective) where `val` appears.
2. Use a segment tree over indices that stores for each position either +1 (first occurrence of an even), -1 (first occurrence of an odd), or 0 (not active). Each node keeps prefix min/max and total sum to support prefix queries.
3. Iterate `l` from `n-1` down to `0`:
   - If `val` already had a first occurrence to the right, clear that old position in the tree (set 0) because `l` becomes the new first occurrence.
   - Set position `l` to +1 or -1 depending on parity.
   - Query the tree for the **rightmost prefix** whose cumulative sum is 0. That index `r` gives a balanced window `[l, r]`; update answer with `r - l + 1` if valid.
4. Return the maximum length found.

Why it works: Distinctness is captured by marking only the first occurrence of each value in the current suffix. The balance of distinct evens/odds in `[l, r]` equals the prefix sum of these markers. A zero-sum prefix means the counts match.

## Complexity

- Time: $O(n \log n)$ — one update + one query per index.
- Space: $O(n)$ for the segment tree and the hash map.

## Optimality

The segment tree gives logarithmic updates/queries while sliding `l`. Given the need to relocate first-occurrence markers on duplicates, a balanced tree/Fenwick is appropriate; this is near-optimal for dynamic prefix queries.

## Edge Cases

- All even or all odd → best length 0 (no balance).
- Single element → unbalanced unless empty subarray counted (here answer 0 or 1? Implementation yields 0).
- Repeated values: only the earliest in the current suffix contributes; later duplicates are ignored until `l` passes them.

## Comparison Table

| Aspect | Segment tree first-occurrence (Solution) | (Not provided) naive O(n²) |
| --- | --- | --- |
| Distinctness tracking | Map of first indices | Recompute sets per window |
| Balance check | Prefix sum == 0 via tree | Count evens/odds each time |
| Time | $O(n \log n)$ | $O(n^2)$ |
| Space | $O(n)$ | $O(n)$ |

## Key Snippet (Java)

```java
for (int l = n - 1; l >= 0; --l) {
	int v = nums[l];
	Integer old = first.get(v);
	if (old != null) st.update(old, 0); // clear old first
	first.put(v, l);
	st.update(l, (v % 2 == 0) ? 1 : -1);

	int r = st.find_rightmost_prefix(0);
	if (r >= l) ans = Math.max(ans, r - l + 1);
}
```

## Example Walkthrough

`nums = [2, 4, 1, 3]`

- Start `l=3`: mark 3 as -1, prefix zero at r=2? no; answer 0.
- `l=2`: clear nothing; mark 1 as -1 → balance -2; zero not found.
- `l=1`: mark 4 as +1, balance from 1..3 is 0 → length 3 (`[4,1,3]`).
- `l=0`: mark 2 as +1, balance 0..3 is 0 → length 4 (best).

## Insights

- “First occurrence in suffix” is the right abstraction for distinct counting while sliding the left boundary.
- Prefix-sum zeros directly encode equal distinct even/odd counts.

## References

- Solution implementation in [3721. Longest Balanced Subarray II/Solution.java](3721.%20Longest%20Balanced%20Subarray%20II/Solution.java)
