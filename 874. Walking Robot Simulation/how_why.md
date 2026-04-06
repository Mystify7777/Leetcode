# 874. Walking Robot Simulation - How Why Explanation

## Goal

Simulate a robot starting at `(0,0)` facing north. Commands include turns and forward steps, and the robot must stop before any obstacle. Return the maximum squared distance from the origin reached at any point.

## Idea in 3 lines

- Maintain the robot’s current position and facing direction.
- Represent obstacles in a hash set so each next cell can be checked in O(1).
- For forward commands, move step by step until the command finishes or an obstacle blocks the next cell; update the maximum distance after each successful move.

## Algorithm (matches `Solution`)

1. Store every obstacle in a hash set.
2. Keep `x`, `y`, and a direction index `dir` where `0=north`, `1=east`, `2=south`, `3=west`.
3. For each command:
	- `-1`: turn right, so `dir = (dir + 1) % 4`.
	- `-2`: turn left, so `dir = (dir + 3) % 4`.
	- Positive value `k`: try to move forward one step at a time `k` times.
4. Before each step, compute the next cell. If it is blocked, stop moving for that command.
5. After each successful move, update `maxDist = max(maxDist, x*x + y*y)`.
6. Return `maxDist`.

## Why it works

- The robot’s state is fully determined by its position and facing direction.
- Obstacles only matter for the next cell being entered, so checking each step is enough to simulate the movement exactly.
- Since the question asks for the farthest squared distance reached at any time, updating the answer after every successful step guarantees the maximum is captured.

## Complexity

- Time: O(total steps across all forward commands).
- Space: O(number of obstacles).

## Note on Solution2

- `Solution2` is logically the same simulation.
- It stores obstacles as encoded `long` values instead of strings, which is often faster and uses less overhead.
