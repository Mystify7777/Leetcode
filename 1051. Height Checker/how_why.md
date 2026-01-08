# 1051. Height Checker — How & Why

Problem

- Given an array `heights` where `heights[i]` is the height of the ith student in line, return the number of indices where the student is not in the position they would be if the array were sorted in non-decreasing order.

Key Idea

- We are counting mismatches between the current order and the sorted order. The simplest way is to compare `heights` to a sorted copy. Since heights are bounded (typically 1..100), we can do this in linear time with counting sort.

Approach 1: Sort-and-Compare (Simple)

1. Make a copy of `heights` and sort it.
2. Scan both arrays in parallel and count positions where values differ.

Complexity

- Time: O(n log n) for the sort, O(n) to compare → O(n log n)
- Space: O(n) for the copy (or O(1) extra if in-place sort is allowed and you keep another reference, but typically you need the original order to compare).

Approach 2: Counting Sort-on-the-Fly (Optimal for bounded values)

Intuition

- When values fall within a small fixed range (here heights are usually 1..100), counting sort is ideal. Instead of materializing the sorted array, we can produce the expected sorted value for each position `i` on-the-fly using frequency counts.

Steps

1. Count frequencies: `freq[h]++` for each `h` in `heights`.
2. Let `cur = 0` (or the minimum possible height). For each index `i` from 0 to `n-1`:
   - Move `cur` up until `freq[cur] > 0`.
   - The expected value at position `i` is `cur`.
   - If `heights[i] != cur`, increment the mismatch count.
   - Decrement `freq[cur]` to consume one occurrence.

Complexity

- Time: O(n + R), where R is the range of heights (≈ 100) → effectively O(n)
- Space: O(R) for the frequency array

Why It Works

- Sorting conceptually arranges values from smallest to largest, respecting multiplicities. A frequency array is just a compressed representation of the multiset; iterating `cur` while consuming counts enumerates the sorted sequence. Comparing each enumerated expected value with the actual value at the same index counts exactly the positions that differ from the sorted order.

Edge Cases

- Already sorted input → result 0
- All equal values → result 0
- Reverse-sorted or mixed duplicates → handled naturally
- Empty input (if allowed) → result 0

Pseudocode: Counting Sort-on-the-Fly

```js
function heightChecker(heights):
	n = len(heights)
	if n == 0:
		return 0

	MAX_H = 100
	freq = array of size MAX_H + 1 filled with 0
	for h in heights:
		freq[h] += 1

	mismatches = 0
	cur = 0
	for i in range(0, n):
		while cur <= MAX_H and freq[cur] == 0:
			cur += 1
		expected = cur
		if heights[i] != expected:
			mismatches += 1
		freq[cur] -= 1

	return mismatches
```

Notes

- If heights can include 0 or have a different bound, adjust `MAX_H` and starting `cur` accordingly. For LeetCode 1051 constraints (1..100), the above is appropriate and fast.

Reference

- [LeetCode 1051](https://leetcode.com/problems/height-checker/)
