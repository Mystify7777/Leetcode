# How Why - Explanation 1391. Check if There is a Valid Path in a Grid

[1391. Check if There is a Valid Path in a Grid](https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/)

## Problem

Each cell in the grid is a street type (`1..6`) with specific allowed connections. Starting at `(0, 0)`, determine whether there is a valid connected path to `(m - 1, n - 1)`.

You can move only through matching street connections (the road must connect in both cells).

## Intuition

Every street type has exactly 2 open sides.

So once you enter a cell from some direction, there is only one valid direction to leave that cell (or none if connection is invalid). That makes movement deterministic.

From the start cell, there are at most 2 possible initial directions. If either deterministic walk reaches the destination, answer is `true`.

## Transition-Table Approach

The implementation uses three precomputed tables in [1391. Check if There is a Valid Path in a Grid/Solution.java](1391.%20Check%20if%20There%20is%20a%20Valid%20Path%20in%20a%20Grid/Solution.java#L4-L12):

- `DIRS`: direction index to `(dr, dc)`.
- `START`: for each street type, the two possible outgoing directions from `(0,0)`.
- `TRANS[type][incomingDir]`: next direction after entering a tile of `type` with `incomingDir`; `-1` means invalid entry.

Then:

1. Handle `1x1` grid directly (`true`).
2. Get two candidate initial directions from `START` for the start tile.
3. Run `check(...)` for each direction.
4. In `check(...)`, walk cell by cell:
	- use `TRANS` to validate and turn,
	- fail if transition is invalid (`-1`),
	- fail if we come back to start,
	- succeed if we reach destination.

If either walk succeeds, return `true`.

## Why This Works

For a fixed initial direction, the path is uniquely determined:

- at each visited tile, the incoming direction determines the only valid outgoing direction,
- so there is no branching to explore.

Therefore checking one initial direction is just simulating the unique path implied by that choice. Since the start tile has only two exits, checking both covers all possible valid paths from the source.

Hence the algorithm is correct: it returns `true` exactly when at least one of those two deterministic paths reaches `(m - 1, n - 1)`.

## Complexity

- Time: `O(m * n)` in the worst case (a walk can traverse many cells, done for at most 2 starts).
- Space: `O(1)` extra space.

## Edge Cases

- `m == 1 && n == 1`: already at destination.
- Start tile has no valid continuation for one/both start directions: that branch returns `false`.
- Paths that loop back to `(0,0)` are rejected.

## Key Insight

This is not a graph search over many branches. Because street tiles are degree-2 pieces, the path is deterministic once the first move is chosen. So the full problem reduces to testing only two simulations.
