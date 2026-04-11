# How Why Explanation - 3741. Minimum Distance Between Three Equal Elements II

## Goal

Find the minimum distance value over triples of equal elements.
For indices `i < j < k` with `nums[i] == nums[j] == nums[k]`, the distance expression used is:
`|k-j| + |j-i| + |i-k|`.
Return the minimum such value, or `-1` if no value appears at least 3 times.

## Key simplification

For sorted indices `i < j < k`:

`|k-j| + |j-i| + |i-k| = (k-j) + (j-i) + (k-i) = 2 * (k-i)`.

So we only need to minimize the gap between the first and third occurrence in a triple.

## Algorithm (matches `Solution2`)

1. Keep two last positions per value:
	- `prev1[v]` = most recent index of value `v`
	- `prev2[v]` = second most recent index of value `v`
2. Scan array left to right at index `i` with value `v`:
	- If `prev2[v] != -1`, we have three occurrences ending at `i`.
	- Candidate span is `i - prev2[v]` (first-to-third in the latest triple).
	- Minimize `ans` with this span.
	- Shift history: `prev2[v] = prev1[v]`, `prev1[v] = i`.
3. If no candidate found, return `-1`; otherwise return `2 * ans`.

## Why it works

- Any valid triple contributes `2 * (k-i)`, so minimizing distance is equivalent to minimizing `k-i`.
- For a fixed value, the best `k-i` with `k` at current index is achieved by taking the closest valid first index among recent occurrences, which `prev2` provides.
- Scanning once and updating per value checks all triples implicitly in O(n).

## Complexity

- Time: O(n)
- Space: O(U), where `U` is value range used by the arrays in this implementation (here `n+1`).

## Note on `Solution`

`Solution` groups all indices by value and checks each consecutive triple in each list; it is correct but uses lists/maps and extra iteration. `Solution2` is the optimized one-pass variant.
