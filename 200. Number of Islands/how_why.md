# How_Why.md

## Problem

You are given a 2D grid map of `'1'`s (land) and `'0'`s (water).  
Count the number of islands.

- An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
- Assume all four edges of the grid are surrounded by water.

---

## How (Step-by-step Solution)

### Approach: Depth-First Search (DFS)

1. Iterate over each cell in the grid.
2. If a cell is land (`'1'`), that means we’ve found a new island:
   - Increase island count.
   - Perform DFS to "sink" the island (mark all connected land as `'0'`).
3. DFS will:
   - Check bounds (stop if out of grid).
   - Stop if the current cell is not land.
   - Mark the current cell as visited (`'0'`).
   - Recursively explore neighbors (up, down, left, right).
4. Continue until the whole grid is processed.

---

## Why (Reasoning)

- Each time we find an unvisited `'1'`, it represents a new island.  
- DFS ensures we traverse all cells belonging to that island and mark them so they are not double-counted.  
- This way, each island contributes exactly **1** to the count.

---

## Complexity Analysis

- **Time Complexity**: O(n × m) → every cell visited at most once.  
- **Space Complexity**:  
  - O(n × m) in worst case due to recursion stack (all land).  
  - Can be optimized with BFS (queue) or Union-Find.

---

## Example Walkthrough

### Input

```text
[
 ['1','1','0','0','0'],
 ['1','1','0','0','0'],
 ['0','0','1','0','0'],
 ['0','0','0','1','1']
]
```

### Execution

- (0,0) → '1' → start DFS → sink whole top-left island → count = 1.

- (2,2) → '1' → start DFS → sink → count = 2.

- (3,3) → '1' → start DFS → sink connected cell (3,4) → count = 3.

### Output

`3`

---

## Alternate Approaches

1. BFS (Breadth-First Search)

    - Instead of recursion, use a queue to traverse neighbors.

    - Avoids deep recursion issues for large grids.

2. Union-Find (Disjoint Set Union)

    - Treat each land cell as a node.

    - Union adjacent land cells.

    - Count distinct sets.

    - More complex but useful for dynamic island problems.

## Notes

Modifying grid in-place (marking '1' → '0') avoids extra visited array.

Works for any n × m grid, including rectangular (not just square).

---
