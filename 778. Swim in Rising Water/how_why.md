# Swim in Rising Water: Analysis and Solutions

This document breaks down the "Swim in Rising Water" problem, exploring solutions from a simple brute-force method to more optimized approaches like the one you provided.

## 🧐 Problem Explanation

Imagine you're standing on a landscape represented by an `n x n` grid. Each cell `grid[i][j]` has an elevation value. Rain starts to fall, and the water level rises uniformly over time. At time `t`, the water depth is `t`.

You can swim from your current square to an adjacent one (up, down, left, or right) only if the elevation of **both** squares is less than or equal to the current water level `t`.

**The Goal:** Find the **minimum possible time `t`** that allows you to swim from the top-left corner `(0, 0)` to the bottom-right corner `(n-1, n-1)`.

Essentially, this problem asks for a path from start to finish that minimizes the highest elevation encountered along that path. The answer will be that maximum elevation.



---

## 🐢 Brute-Force Method

The most straightforward way to solve this is to simulate the process step-by-step.

### How It Works

1. Start with time `t = 0`.
2. Check if a path exists from `(0, 0)` to `(n-1, n-1)` using only cells with an elevation less than or equal to `t`. You can check for a path using a standard graph traversal algorithm like **Depth First Search (DFS)** or **Breadth First Search (BFS)**.
3. If no path exists, increment the time `t` by 1.
4. Repeat from step 2 until you find the first `t` for which a path exists. This `t` is your answer.

### Why It Works

This method is guaranteed to find the minimum time because it checks every possible time value in increasing order. The very first time it finds a valid path must be the minimum time required.

### Complexity

- Let `N = n * n` be the total number of cells in the grid.
- The time `t` can range from `0` to `N - 1`. In the worst case, we might have to check every single time value, which is `O(N)` checks.
- For each time `t`, we perform a DFS/BFS on the grid, which takes `O(N)` time to visit all cells.
- **Total Time Complexity:** `O(N) * O(N) = O(N^2)` or `O(n^4)`. This is quite slow and may not pass for larger grids.

---

## 🧠 My Approach: Binary Search + DFS

The provided solution is much more efficient. It cleverly identifies that we don't need to check every single time value `t`. Instead, we can **binary search for the answer**.

### How It Works

The core idea is to search for the minimum viable time `t` within the possible range of answers, which is `[grid[0][0], n*n - 1]`.

1. **Define a Search Space:** The lowest possible answer is the elevation of the starting cell `grid[0][0]`, and the highest is `n*n - 1`. Let's call this range `[left, right]`.
2. **Binary Search:**
    * Pick a middle time `m = left + (right - left) / 2`.
    * This `m` is a *guess*. We need to verify if it's possible to reach the end at time `m`.
3. **Verification with DFS:**
    * We use a helper function `dfs(grid, i, j, T, visited)` that returns `true` if a path exists from `(i, j)` to the end, given that the water level is `T`.
    * This DFS can only explore cells whose elevation is less than or equal to `T`.
4. **Narrowing the Search:**
    * If `dfs(..., m, ...)` returns **true**, it means `m` is a possible time. But we want the *minimum* time, so we try to find an even smaller one. We update our search space to the lower half: `right = m`.
    * If `dfs(..., m, ...)` returns **false**, time `m` is not enough. We need more time. We update our search space to the upper half: `left = m + 1`.
5. **Termination:** The loop continues until `left` and `right` converge (`left == right`), giving us the smallest `t` for which the DFS returns `true`.

### Why It's Applicable

Binary search works on this problem because of a key **monotonic property**.

Let's define a function `can_reach(t)` which is `true` if a path exists at time `t`, and `false` otherwise.

> If you can reach the destination at time `t`, you can **definitely** reach it at any time `t' > t`.

This is because any path available at time `t` will also be available at the higher water level `t'`. This creates a sorted-like sequence of possibilities:

`[F, F, F, ..., F, T, T, ..., T]`
(F = False, T = True)

Binary search is the perfect algorithm to find the first `T` in such a sequence.

---

## 🚀 The Most Optimal Method: Dijkstra's Algorithm / Priority Queue

While the Binary Search + DFS approach is very efficient, another classic way to solve this is by framing it as a shortest path problem on a graph, solvable with Dijkstra's algorithm.

### How It Works

1. **Graph Analogy:** Treat each cell `(i, j)` as a node in a graph.
2. **Path "Cost":** The "cost" or "weight" of a path is not the sum of elevations, but the **maximum elevation** encountered along that path.
3. **Algorithm:**
    * Use a **min-priority queue** that stores tuples of `(max_elevation_so_far, row, col)`. The queue is sorted by `max_elevation_so_far`.
    * Start by pushing the starting cell `(grid[0][0], 0, 0)` into the queue.
    * Use a `visited` array to keep track of processed cells.
    * While the queue is not empty:
        * Pop the cell with the **smallest** `max_elevation_so_far`. Let it be `(max_elev, r, c)`.
        * If `(r, c)` is the destination, you've found the path with the minimum possible maximum elevation. Return `max_elev`.
        * For each unvisited neighbor `(nr, nc)`:
            * The new maximum elevation to reach this neighbor is `new_max_elev = max(max_elev, grid[nr][nc])`.
            * Push `(new_max_elev, nr, nc)` into the priority queue.

### Why It Works

This approach always expands the path with the minimum possible "cost" (i.e., the lowest water level required so far). By greedily exploring the "driest" available paths first, it guarantees that when we first reach the destination, we have done so via a path that required the minimum possible water level.

---

## 📊 Complexity Comparison

| Method                       | Time Complexity                  | Space Complexity | Notes                                                                   |
| ---------------------------- | -------------------------------- | ---------------- | ----------------------------------------------------------------------- |
| **Brute-Force (Linear Scan)**| $O(N^2)$ or $O(n^4)$            | $O(N)$           | Simple to understand, but too slow for typical constraints.             |
| **Binary Search + DFS** | $O(N \cdot \log N)$ or $O(n^2 \cdot \log n)$ | $O(N)$           | Very efficient. The `log N` factor comes from the binary search.          |
| **Dijkstra's (Priority Queue)**| $O(N \cdot \log N)$ or $O(n^2 \cdot \log n)$ | $O(N)$           | Same theoretical complexity as binary search, a classic graph algorithm. |

Both the **Binary Search + DFS** and the **Dijkstra's** approach are considered optimal for this problem. The binary search method can sometimes be slightly faster in practice due to lower overhead compared to managing a priority queue.
