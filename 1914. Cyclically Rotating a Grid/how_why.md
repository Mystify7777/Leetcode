# How Why - Explanation 1914. Cyclically Rotating a Grid

[1914. Cyclically Rotating a Grid](https://leetcode.com/problems/cyclically-rotating-a-grid/)

## Problem

Given an `m x n` grid and an integer `k`, rotate each rectangular layer of the grid counterclockwise by `k` positions and return the resulting grid.

Each layer is the border of a sub-rectangle. Inner layers rotate independently from outer layers.

## Intuition

A grid layer is just a cycle of cells along its perimeter. If we read the border values in order, rotating the layer by `k` positions is equivalent to shifting that cycle by `k % perimeter_length`.

So the problem becomes:

1. Identify each layer.
2. Extract its border values in traversal order.
3. Rotate the values by `k`.
4. Write them back to the same border positions.

## Approach (Layer-by-Layer Rotation)

### Solution

1. Use four pointers `T`, `L`, `B`, `R` for the current top, left, bottom, and right boundaries.
2. While the current layer has height and width greater than 1:
   - Compute its perimeter length.
   - Reduce `k` by modulo that perimeter.
   - Rotate the border one step at a time `k % perimeter` times by shifting the top row, right column, bottom row, and left column.
3. Move inward to the next layer.
4. Return the updated grid.

### Solution2

1. For each layer, traverse its border and store values into a temporary array.
2. Compute the effective rotation `k % length`.
3. Traverse the same border again and write values back with the shifted offset.
4. Continue for all layers.

This is implemented in [1914. Cyclically Rotating a Grid/Solution.java](1914.%20Cyclically%20Rotating%20a%20Grid/Solution.java).

## Why This Works

Each layer forms a closed loop. Rotating the layer does not affect any other layer, so processing layers independently is valid.

The order of cells along a border is fixed. A cyclic shift of that order exactly matches the problem's rotation. Using `k % perimeter` removes unnecessary full cycles, since rotating by the perimeter length returns the border to the same configuration.

## Complexity

- `Solution`: Time is `O(k * perimeter)` per layer in the worst case because it applies the shift one step at a time. Space is `O(1)` extra.
- `Solution2`: Time is `O(m * n)` overall for extracting and rewriting each layer once. Space is `O(perimeter)` for the temporary array.

## Edge Cases

- Single row or single column: no complete layer exists, so the grid remains unchanged.
- Small rectangles: each layer is still handled independently.
- Large `k`: always reduced with modulo before rotating.

## Key Insight

A layer is a perimeter cycle. Once you treat it as a circular sequence, rotating the grid becomes simple index shifting on each border.
