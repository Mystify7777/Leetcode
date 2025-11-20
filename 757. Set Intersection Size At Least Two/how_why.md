# Recap

You are given a 2D integer array `intervals` where `intervals[i] = [start_i, end_i]` represents all integers from `start_i` to `end_i` (inclusive). You need to find the minimum size of a set `S` such that for every interval, the intersection of `S` with that interval has at least 2 elements. Return the minimum size of such a set.

## Intuition

This is a greedy interval covering problem. The key insight is to sort intervals by their end points and process them in order. For each interval, we maintain the two rightmost elements we've added to our set. If the current interval doesn't contain at least two of these elements, we add the necessary elements as far right as possible (at the end of the current interval) to maximize coverage of future intervals.

## Approach

1. **Sort intervals** by end point ascending. If end points are equal, sort by start point descending (larger intervals first).
   - Sorting by end ensures we process intervals that end earlier first.
   - Among intervals with the same end, processing larger ones first helps cover smaller ones.

2. **Track two rightmost elements** in our set: `a` (second rightmost) and `b` (rightmost), initialized to `-1`.

3. **For each interval `[l, r]`**:
   - **Case 1:** If `l > b` (interval doesn't contain either tracked element):
     - Add two new elements: `r - 1` and `r` (rightmost two points).
     - Update `a = r - 1`, `b = r`.
     - Increment answer by 2.

   - **Case 2:** If `l > a` but `l <= b` (interval contains only `b`, needs one more):
     - Add one new element: `r` (rightmost point).
     - Update `a = b` (shift tracked elements), `b = r`.
     - Increment answer by 1.

   - **Case 3:** If `l <= a` (interval contains both `a` and `b`):
     - Do nothing, interval already satisfied.

4. Return the total count.

## Code (Java)

```java
class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> 
            a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]
        );

        int ans = 0;
        int a = -1, b = -1;

        for (int[] it : intervals) {
            int l = it[0], r = it[1];

            if (l > b) {
                a = r - 1;
                b = r;
                ans += 2;
            } else if (l > a) {
                a = b;
                b = r;
                ans += 1;
            }
        }

        return ans;
    }
}
```

## Correctness

- **Greedy choice is optimal:** By always placing new elements as far right as possible (at the end of the current interval), we maximize the chance of covering future intervals that start before or at these positions.

- **Sorting guarantees correctness:** Processing intervals by end point ensures that once we place elements for an interval, those elements are optimally positioned for all later intervals (which end at the same or later positions).

- **Two-element tracking:** Since we need exactly 2 elements per interval, tracking the two rightmost suffices. Any interval that starts at or before `a` is automatically satisfied by both `a` and `b`.

- **Tie-breaking by start (descending):** When intervals have the same end, processing larger ones first ensures that if we add elements for the larger interval, they'll also cover any smaller intervals with the same end.

## Complexity

- **Time:** `O(n log n)` for sorting, plus `O(n)` for the greedy pass = `O(n log n)`.
- **Space:** `O(1)` auxiliary (excluding sorting space).

## Edge Cases

- Single interval `[a, b]`: Need 2 elements, choose `b-1` and `b`, return `2`.
- Non-overlapping intervals: Each needs 2 new elements, return `2 * n`.
- Fully overlapping intervals (same `[a, b]`): All satisfied by the same 2 elements, return `2`.
- Nested intervals: Larger intervals processed first when ends match, ensuring proper coverage.
- Intervals with gap of 1: Example `[1, 2]` and `[2, 3]` share element `2`, so we need `{1, 2, 3}` (3 elements total).

## Takeaways

- **Interval scheduling problems** often benefit from sorting by end points and greedy selection.
- **Placing elements as late as possible** maximizes future coverage (rightmost positioning).
- **Tracking the minimum necessary state** (here, two rightmost elements) keeps the algorithm efficient.
- **Tie-breaking rules** in sorting can be crucial for correctness (process larger intervals first when ends match).
- This problem generalizes interval point coverage to requiring `k` points per interval (here `k = 2`).
