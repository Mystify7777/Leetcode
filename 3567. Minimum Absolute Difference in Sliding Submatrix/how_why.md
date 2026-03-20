# 3567. Minimum Absolute Difference in Sliding Submatrix - How Why Explanation

## Goal

For every `k x k` submatrix in a grid, find the minimum absolute difference between any two distinct elements in that submatrix (0 if all elements are equal). Return the matrix of these minima for all top-left positions.

## Idea in 3 lines

- Each window is independent: collect its `k^2` elements, sort them, and the minimum nonzero gap between adjacent sorted values is the answer for that window.
- Sliding optimizations exist, but a direct per-window sort is simple and accepted for the given constraints.
- Handle the all-equal case by returning 0 when no differing adjacent pair exists.

## Algorithm (matches `Solution`/`Solution2`)

1. For every top-left `(i, j)` where a `k x k` fits:
	- Copy the `k^2` elements into a temporary list/array.
	- Sort the collection.
	- Scan adjacent pairs; track the smallest positive difference. If none, answer is 0.
2. Store that value in the output at `(i, j)`.
3. Return the filled output matrix of size `(m - k + 1) x (n - k + 1)`.

## Why it works

- Sorting the window orders elements so the minimum absolute difference must appear between some adjacent pair in that order.
- Evaluating every `k x k` window covers all required positions exactly once.
- The explicit check for differing neighbors correctly yields 0 when all elements are equal.

## Complexity

- Time: O((m - k + 1)(n - k + 1) * k^2 log(k^2)) for the per-window sort.
- Space: O(k^2) for the temporary buffer (reused per window).
