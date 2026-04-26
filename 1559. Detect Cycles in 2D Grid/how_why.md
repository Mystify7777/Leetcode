# How Why - Explanation 1559. Detect Cycles in 2D Grid

[1559. Detect Cycles in 2D Grid](https://leetcode.com/problems/detect-cycles-in-2d-grid/)

## Problem

Given an `m x n` grid of lowercase letters, determine whether there exists a cycle made of cells with the same character.

A valid cycle:

- uses 4-directional moves (up/down/left/right),
- has length at least 4,
- does not immediately go back through the same edge you just came from.

Return `true` if such a cycle exists, otherwise `false`.

## Intuition

Treat each cell as a node in an undirected graph. There is an edge between adjacent cells only when they have the same character.

In an undirected graph, during DFS, if you reach an already visited node that is not your parent, you found a cycle.

So for each unvisited cell, run DFS and carry the parent cell `(pr, pc)` to avoid counting the direct back-edge.

## Approach (DFS + Parent Tracking)

1. Keep a visited array.
2. For each cell not visited yet, start DFS.
3. In DFS from `(r, c)`:
	- mark visited,
	- try 4 neighbors,
	- skip the parent `(pr, pc)`,
	- only continue to neighbors with the same character,
	- if such a neighbor is already visited, cycle exists,
	- otherwise recurse.
4. If any DFS returns `true`, return `true`; otherwise return `false`.

This matches [1559. Detect Cycles in 2D Grid/Solution.java](1559.%20Detect%20Cycles%20in%202D%20Grid/Solution.java#L4-L30).

## Why This Is Correct

Consider one connected component of same-character cells.

- DFS explores exactly all reachable nodes in that component.
- In an undirected graph, seeing a visited neighbor that is not the parent means there is another path back to that node.
- That creates a closed walk, i.e., a cycle.

If the algorithm returns `true`, it found such a non-parent visited neighbor, so a cycle exists.
If a cycle exists in any component, DFS on that component must eventually traverse an edge to a previously visited non-parent node, so the algorithm returns `true`.

Therefore the algorithm is correct.

## Complexity

- Time: `O(m * n)`.
- Space: `O(m * n)` for visited + recursion stack in the worst case.

## Edge Cases

- Single row/column: cannot form a valid cycle of length at least 4.
- Isolated equal cells with no loop: returns `false`.
- Multiple components: outer loops ensure every component is checked.

## Alternate Approach In File

The file also includes `SolutionAlt`, a Union-Find variant in [1559. Detect Cycles in 2D Grid/Solution.java](1559.%20Detect%20Cycles%20in%202D%20Grid/Solution.java#L31-L84):

- merge adjacent equal-character cells,
- if two neighbors are already in the same set before merging, a cycle is found.

Both approaches are valid; the primary `Solution` uses DFS.
