# 1340. Jump Game V

The goal is to maximize how many indices can be visited starting from any position, while only jumping to a lower value and never crossing a higher or equal value within distance `d`.

## Approach 1: DFS + Memoization (`Solution`)

- Try every index as a starting point.
- From an index `i`, scan left and right up to distance `d`.
- Stop scanning in a direction when a value `>= arr[i]` blocks further movement.
- Use memoization so each index is solved once.

Why it works:
- The problem forms a directed acyclic structure because jumps only go to strictly smaller values.
- Memoization prevents recomputing the best path from the same index.

## Approach 2: Monotonic Stack + DFS (`SolutionAsian`)

- Precompute the nearest greater element on the left and right within distance `d`.
- These boundary indices tell us how far jumps can propagate.
- Then use DFS over those derived transitions to compute the best result.

This is more structural and avoids repeated scanning of blocked positions.

## Complexity

- DFS scan version: `O(n * d)` in the worst case.
- Stack-assisted version: `O(n)` for preprocessing, plus DFS memoization over derived transitions.
- Space: `O(n)` for memoization and helper arrays.
