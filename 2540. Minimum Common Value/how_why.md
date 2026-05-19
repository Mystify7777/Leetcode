# 2540. Minimum Common Value

Both input arrays are sorted in non-decreasing order, so the minimum common value can be found with a two-pointer scan.

## Approach

- Start one pointer at the beginning of each array.
- If the values are equal, that value is the smallest common value, so return it immediately.
- If `nums1[i] < nums2[j]`, advance `i`; otherwise advance `j`.
- If either pointer reaches the end, no common value exists.

`Solution2` adds a quick boundary check before scanning:
- If the largest value in one array is smaller than the smallest value in the other, the arrays cannot intersect.

## Why it works

Because both arrays are sorted, moving the pointer that points to the smaller value never skips a possible common value. This keeps the scan linear while preserving correctness.

## Complexity

- Time: `O(n + m)`
- Space: `O(1)`
