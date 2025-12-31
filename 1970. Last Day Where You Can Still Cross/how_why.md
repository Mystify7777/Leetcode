# 1970. Last Day Where You Can Still Cross

## Problem Statement

You are given a 2D grid of size `row x col` representing water cells. Initially, all cells contain water. Given a 2D array `cells` where `cells[i] = [r, c]` represents that on day `i`, the cell at position `(r, c)` becomes land (water cell becomes an obstacle).

You need to find the **last day** on which you can still walk from the **top** of the grid to the **bottom** of the grid. A path exists if you can move through water cells (cells with value 0) from row 0 to row `row - 1` using 4-directional movement (up, down, left, right).

If it's not possible to cross at any day, return -1.

## Approach: Disjoint Set Union (DSU) with Reverse Time

Instead of simulating day by day moving forward, we simulate **backward in time**:

1. **Reverse the process**: Start with all cells as land (all cells filled), then reverse the order of `cells` to "unfill" them one by one.

2. **Connectivity Check**: Use DSU to track when the top and bottom become connected through water cells.

3. **Virtual Nodes**:
   - Use node `0` to represent the "top" virtual node
   - Use node `row * col + 1` to represent the "bottom" virtual node
   - Use node `r * col + c + 1` to represent cell `(r, c)` in the grid

4. **Algorithm**:
   - Iterate through cells in reverse order
   - For each cell becoming water (unfilled):
     - Connect it to all adjacent water cells (using DSU union)
     - If it's on the leftmost column (c == 0), connect it to the top virtual node
     - If it's on the rightmost column (c == col - 1), connect it to the bottom virtual node
     - Check if top and bottom are now connected

5. **Result**: The first day (in reverse) when top and bottom become connected is the answer.

## Why This Works

- **Time Reversal Advantage**: Instead of BFS/DFS to check connectivity each day (O(n²m²)), we use DSU which is nearly O(1) per operation.

- **Connectivity Through Water**: We only care about connectivity through water cells. By checking when the top and bottom virtual nodes become connected, we know a path exists.

- **8-Directional Check**: The solution uses all 8 directions (including diagonals) to connect adjacent cells for a more realistic walking path.

## Complexity Analysis

- **Time Complexity**: `O(n * m * α(n * m)) ≈ O(n * m)` where α is the inverse Ackermann function (practically constant)
- **Space Complexity**: O(n * m) for the grid and DSU arrays

## Key Insights

1. **Reverse Time Processing**: Backward simulation is often simpler for connectivity problems
2. **Virtual Nodes**: Using sentinel/virtual nodes simplifies edge case handling
3. **8-Directional Movement**: In this problem, diagonal movement is allowed, making the grid more connected
4. **DSU Efficiency**: Union-Find is ideal for dynamic connectivity queries
