# 3546. Equal Sum Grid Partition I - How Why Explanation

## Goal

Decide if the grid can be split into two parts of equal sum by a single cut that slices the grid either horizontally between rows or vertically between columns.

## Idea in 3 lines

- Total sum must be even; target half = total/2.
- For a horizontal cut after row `i`, the top sum is the prefix sum of rows up to `i`; similarly, a vertical cut uses column prefixes.
- Scan possible cut positions, accumulating prefix sums; if any prefix equals target, partition is possible.

## Algorithm (matches `Solution`)

1. Compute total sum; if odd, return false.
2. Let `target = total/2`.
3. Horizontal check: accumulate row sums top-down until the row before the last; if prefix == target, return true.
4. Vertical check: accumulate column sums left-to-right until the column before the last; if prefix == target, return true.
5. Otherwise return false.

## Why it works

- A valid single straight cut must be either horizontal or vertical; diagonals are disallowed.
- The portion on one side of the cut is exactly a row or column prefix; checking all prefixes exhausts possibilities.
- Equality to `target` guarantees both sides have the same sum because total = 2 * target.

## Complexity

- Time: O(m * n) to compute total and scan prefixes.
- Space: O(1) extra.
