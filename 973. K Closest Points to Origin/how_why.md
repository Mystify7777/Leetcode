# 973. K Closest Points to Origin

## Problem Understanding

Given `points[i] = [xi, yi]`, return the `k` points with the smallest Euclidean distance to the origin `(0, 0)`. The relative order of the `k` points does not matter.

## Approach

Use a greedy sort by squared distance (no need for square roots) and pick the first `k` points.

### Algorithm

1. Compute each point's squared distance: `d = x*x + y*y`.
2. Sort `points` ascending by `d` using a comparator.
3. Return the first `k` elements (`Arrays.copyOfRange`).

### Why This Works

- Squared distance preserves ordering vs. actual distance, avoids `Math.sqrt`.
- Sorting globally orders points by closeness; first `k` are the closest.
- Stable with duplicates; any valid order is acceptable.

## Complexity

- Time: `O(n log n)` for sorting `n` points.
- Space: `O(log n)` stack for sort (in-place array sort) plus `O(k)` for the returned slice.

## Example

Input: `points = [[1,3],[-2,2]]`, `k = 1`

- Distances: `[10, 8]`
- Sorted: `[[-2,2],[1,3]]`
- Output: `[[-2,2]]`

## Alternatives

- Quickselect (average `O(n)`, worst `O(n^2)`) to partition around the k-th distance.
- Max-heap of size `k` (time `O(n log k)`, space `O(k)`).
