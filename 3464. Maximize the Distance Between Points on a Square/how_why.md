# How Why - Explanation 3464. Maximize the Distance Between Points on a Square

[3464. Maximize the Distance Between Points on a Square](https://leetcode.com/problems/maximize-the-distance-between-points-on-a-square/)

## Problem

You are given a square with side length `side`, and a list of boundary points on that square. You must choose `k` points so that the minimum Manhattan distance between any two chosen points is as large as possible.

Return that maximum possible minimum distance.

## Intuition

The key idea is to turn each boundary point into a single number: its position along the square perimeter.

Once points are ordered around the boundary, the problem becomes a feasibility question:

- If we want every chosen pair to be at least `d` apart,
- can we pick `k` points whose perimeter coordinates are spaced far enough apart?

That lets us binary search the answer `d`.

## Perimeter Mapping

The square is traversed clockwise starting from `(0, 0)`:

- left edge: `(0, y)` maps to `y`
- top edge: `(x, side)` maps to `side + x`
- right edge: `(side, y)` maps to `3 * side - y`
- bottom edge: `(x, 0)` maps to `4 * side - x`

This produces a one-dimensional coordinate in `[0, 4 * side)` for each point. The implementation builds these values in [3464. Maximize the Distance Between Points on a Square/Solution.java](3464.%20Maximize%20the%20Distance%20Between%20Points%20on%20a%20Square/Solution.java#L3-L20).

## Approach (Binary Search + Greedy Check)

1. Convert all boundary points to perimeter coordinates.
2. Sort those coordinates.
3. Binary search the answer `d`.
4. For each candidate `d`, check whether we can greedily pick `k` points so that consecutive chosen coordinates are at least `d` apart, including the wrap-around gap on the perimeter.

### Why binary search works

If a distance `d` is feasible, then every smaller distance is also feasible. That monotonicity makes binary search valid.

### Why the greedy check works

For a fixed `d`, once we choose a starting point, the best chance to fit `k` points is to always take the earliest next point that is at least `d` ahead. That preserves as much room as possible for the remaining picks.

The code tries the first greedy chain, then shifts the starting point forward until it finds a valid circular arrangement.

## Complexity

- Time: `O(n log n log P)`, where `n = points.length` and `P = 4 * side`.
- Space: `O(n)` for the perimeter array.

## Edge Cases

- `k = 4`: the answer is determined by the four selected points on the boundary.
- Points on different sides: the perimeter transform keeps them in the correct clockwise order.
- Very large `side`: use `long` for perimeter coordinates to avoid overflow.

## Key Insight

The solution does not try every subset. It turns a 2D boundary problem into a sorted 1D circular ordering, then searches the largest distance that still allows `k` greedy picks.
