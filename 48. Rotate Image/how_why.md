# How Why - Explanation 48. Rotate Image

[48. Rotate Image](https://leetcode.com/problems/rotate-image/)

## Problem

Given an `n x n` matrix, rotate it 90 degrees clockwise in-place.

## Intuition

A clockwise rotation can be broken into two simpler in-place operations:

1. Transpose the matrix.
2. Reverse each row.

After the transpose, rows become columns. Reversing each row fixes the orientation and produces the final rotated matrix.

## Approach (Transpose + Reverse)

1. Traverse the upper triangle of the matrix, including the diagonal.
2. Swap `matrix[i][j]` with `matrix[j][i]` to transpose the matrix.
3. For each row, swap the leftmost and rightmost elements, moving inward.
4. The matrix is now rotated 90 degrees clockwise.

This matches [48. Rotate Image/Solution.java](48.%20Rotate%20Image/Solution.java).

## Why This Works

Transpose converts the matrix from row-major orientation to column-major orientation. For a clockwise rotation, the left-to-right order inside each row must also be reversed. Combining these two operations is exactly equivalent to rotating the matrix 90 degrees clockwise.

Because all swaps are done directly inside the original matrix, no extra matrix is needed.

## Complexity

- Time: `O(n^2)`.
- Space: `O(1)`.

## Example Walkthrough

Input:

```text
[1,2,3]
[4,5,6]
[7,8,9]
```

After transpose:

```text
[1,4,7]
[2,5,8]
[3,6,9]
```

After reversing each row:

```text
[7,4,1]
[8,5,2]
[9,6,3]
```

## Edge Cases

- `n = 1`: the matrix stays the same.
- Even/odd sizes: the same transpose + row-reverse logic applies.

## Alternate View

You can also think of this as moving element `(i, j)` to `(j, n - 1 - i)`, but the transpose + reverse method is the cleanest in-place implementation.
