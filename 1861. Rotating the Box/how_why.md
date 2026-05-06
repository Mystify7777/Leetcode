# How Why - Explanation 1861. Rotating the Box

[1861. Rotating the Box](https://leetcode.com/problems/rotating-the-box/)

## Problem

You are given a box represented by a grid of characters:

- `'#'` = stone
- `'*'` = obstacle
- `'.'` = empty space

First, every stone falls to the right as far as it can, stopping at obstacles or the edge. Then the entire box is rotated 90 degrees clockwise.

## Intuition

The problem is easier if we separate it into two phases:

1. Simulate gravity row by row so stones settle to the right.
2. Rotate the resulting grid clockwise.

Because stones only move right within a row, each row can be processed independently using a pointer that tracks the rightmost empty slot available before the next obstacle.

## Approach (Gravity First, Then Rotate)

1. For each row, scan from right to left.
2. Keep `empty` as the rightmost position where a stone can land.
3. When you see:
   - `'#'`: move the stone to `empty` and mark the old position as `'.'`.
   - `'*'`: reset `empty` to just left of the obstacle, because stones cannot pass it.
   - `'.'`: do nothing.
4. After all rows have settled, rotate the box clockwise into a new array of size `cols x rows`.
5. Copy `box[rows - 1 - j][i]` into `rotatedBox[i][j]`.

This is implemented in [1861. Rotating the Box/Solution.java](1861.%20Rotating%20the%20Box/Solution.java).

## Why This Works

Stones fall only within their original row, and obstacles divide the row into independent segments. Within each segment, scanning from right to left ensures each stone moves to the farthest valid empty cell.

After gravity has been applied, rotating the box is just a standard matrix rotation: the element at `(r, c)` moves to `(c, rows - 1 - r)`.

## Complexity

- Time: `O(rows * cols)`.
- Space: `O(rows * cols)` for the rotated output; the gravity simulation is in-place.

## Edge Cases

- Rows with no stones: unchanged except for rotation.
- Rows with multiple obstacles: each segment is handled separately.
- Boxes with one row or one column: the same logic still works.

## Alternate Implementation

`Solution2` builds the rotated grid directly while processing each row:

- It places obstacles immediately in the rotated output.
- It tracks the lowest free slot in each segment for stones.
- It fills empty cells as needed so the rotated result matches the gravity-simulated version.

Both implementations produce the same final rotated box.
