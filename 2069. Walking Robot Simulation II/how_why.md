# 2069. Walking Robot Simulation II - How Why Explanation

## Goal

Simulate a robot moving on the border of a `width x height` grid. The robot starts at `(0,0)` facing East, walks along the perimeter, and each `step(num)` moves it forward by `num` cells with wrap-around on the perimeter. `getPos()` and `getDir()` should report the robot’s current state.

## Idea in 3 lines

- The robot never enters the interior, so its motion is a cycle on the grid perimeter.
- Reduce large step counts with modulo by the perimeter length: `2 * (width - 1) + 2 * (height - 1)`.
- Move segment by segment along the current direction until the remaining steps are exhausted, turning clockwise when a side ends.

## Algorithm (matches `Robot2`)

1. Store position `(x, y)` and direction as one of `East`, `North`, `West`, `South`.
2. In `step(num)`:
	- Compute `perim = 2 * (width - 1) + 2 * (height - 1)`.
	- Reduce `num %= perim`; if it becomes `0`, treat it as a full cycle.
	- While `num > 0`, move as far as possible in the current direction without leaving the grid.
	- If steps remain after reaching an edge, rotate clockwise and continue with the leftover amount.
3. `getPos()` returns the current coordinates; `getDir()` returns the current facing direction.

## Why it works

- Any path after the robot reaches the boundary is periodic with the perimeter length, so modulo reduction preserves the final state.
- Moving edge by edge is enough because each direction is a straight segment until a corner is reached.
- Turning clockwise at each corner matches the robot’s fixed perimeter traversal order.

## Complexity

- Time: O(1) per `step` in terms of grid size, since the robot only traverses at most four perimeter segments after modulo reduction.
- Space: O(1).

## Note on `Robot`

- The alternate implementation stores accumulated steps and performs the real movement lazily when `getPos()` or `getDir()` is called.
- It uses the same perimeter modulo idea, but defers work until observation time.
