# 3661. Maximum Walls Destroyed by Robots - How Why Explanation

## Goal

Place robots at given positions; each shoots either left or right within its distance range to destroy walls. Maximize total walls destroyed, accounting for overlap when robots destroy the same wall.

## Idea in 3 lines

- Use DP: track the best wall count if robot `i` shoots left vs. right.
- For adjacent robots, one may shoot toward the other; their ranges may overlap—count common walls once.
- Recurrence: adding robot `i` shooting left/right = walls it destroys leftward/rightward + best prior state, adjusting for overlaps.

## Algorithm (Solution 2 – clearer structure)

1. Sort robots and walls by position; pair each robot with its distance.
2. For each robot `i`, precompute:
   - `leftWall[i]` = walls in range `[max(robot[i-1]+1, robot[i]−distance[i]), robot[i]]`
   - `rightWall[i]` = walls in range `[robot[i], min(robot[i+1]−1, robot[i]+distance[i])]`
   - `common[i]` = walls in overlap of robot `i−1` right range and robot `i` left range (if both shoot toward each other)
3. Use DP arrays `dpLeft[i]`, `dpRight[i]`:
   - `dpLeft[i]` = max walls using robots 0..i with robot i shooting left
   - `dpRight[i]` = max walls using robots 0..i with robot i shooting right
4. Recurrence:
   - When robot `i` shoots left: gain `leftWall[i]`; best prior is `max(dpLeft[i−1], dpRight[i−1] − common[i])`
     - (subtract `common[i]` if previous shot right, to avoid double-counting overlap)
   - When robot `i` shoots right: gain `rightWall[i]`; best prior is `max(dpLeft[i−1], dpRight[i−1])` (no overlap with leftward shots)
5. Answer is `max(dpLeft[n−1], dpRight[n−1])`.

## Why it works

- Sorting allows binary search / sliding-window range counting.
- Each robot independently chooses direction; adjacent robots' interference is modeled via the `common[i]` overlap detection.
- The recurrence correctly credits walls each robot won't double-count by subtracting known overlaps.
- DP optimal substructure: the best decision for robot `i` depends only on the best decision of robot `i−1`.

## Complexity

- Time: O(n log n + n log m) for sort and binary searches; O(m + n) for wall/range scans = O((n+m) log m).
- Space: O(n + m) for robots, walls, and DP arrays.

## Note on Solution 1

Solution 1 uses a similar DP approach but inline—it directly computes interference on the fly without explicit precomputed arrays, trading some clarity for slightly less preprocessing overhead.
