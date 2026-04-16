# 3488. Closest Equal Element Queries - How Why Explanation

 [3488. Closest Equal Element Queries](https://leetcode.com/problems/closest-equal-element-queries/)

## Goal

For each query index `q`, find the minimum circular distance from `q` to another index `j` such that `nums[j] == nums[q]`. If no such index exists, return `-1`.

## Idea in 3 lines

- Group all indices by value.
- For query index `q`, only the nearest same-value neighbors in sorted index order can be optimal.
- Because the array is circular, distance between two indices is `min(|a-b|, n-|a-b|)`.

## Algorithm (matches `Solution`)

1. Build a map: `value -> sorted list of indices where value appears`.
2. For each query index `q`:
	 - Let `v = map.get(nums[q])`.
	 - If `v.size() == 1`, answer is `-1`.
	 - Find position `pos` of `q` inside `v` using binary search.
	 - Take two cyclic neighbors in `v`:
		 - left: `v[(pos - 1 + m) % m]`
		 - right: `v[(pos + 1) % m]`
	 - Compute circular distance to both neighbors and return the smaller one.
3. Collect results in order.

## Why it works

- Indices for the same value are sorted; the nearest same-value index around `q` must be adjacent in this sorted circular list.
- Any non-adjacent same-value index has at least one adjacent index on the path that is no farther.
- Taking `min(d, n-d)` correctly converts linear gap to circular shortest path.

## Complexity

- Preprocessing map: `O(n)`.
- Each query: `O(log f)` for binary search, where `f` is frequency of `nums[q]`.
- Total: `O(n + Q log f)` (worst-case `O(n + Q log n)`).
- Space: `O(n)`.

## Note on `Solution2`

`Solution2` computes best distances for all indices in one pass per value-tracking strategy, then answers queries in O(1) each. It is a different but valid optimization route.
