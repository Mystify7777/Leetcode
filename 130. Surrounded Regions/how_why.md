
# How_Why.md — Surrounded Regions (LeetCode #130)

## 🧩 Problem Statement

You are given an `m x n` matrix `board` containing `'X'` and `'O'`.  
Capture all regions surrounded by `'X'`.  

A region is **captured** by flipping all `'O'`s into `'X'`s in that surrounded region.

**Rule:**  

- `'O'` on the border (or connected to a border `'O'`) **cannot** be flipped.

---

## ⚙️ Brute-Force Method

### Idea

- For every `'O'`, check if it’s surrounded by `'X'` on all sides (up, down, left, right).
- If yes, flip it to `'X'`.

### Code (Conceptual)

```java
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        if (board[i][j] == 'O' && isSurrounded(board, i, j))
            board[i][j] = 'X';
    }
}
```

Where `isSurrounded()` does a DFS/BFS for every `'O'` to check boundary reachability.

### ⚠️ Limitations

* **Highly inefficient** — each `'O'` triggers a DFS/BFS.
* **Repeated work**: revisiting same cells multiple times.
* **TLE (Time Limit Exceeded)** on large grids.

---

## 💡 Your Approach (Optimized DFS from Boundary)

### Core Idea

Instead of checking every `'O'`, **protect** the ones that should **not** be flipped.

1. Any `'O'` **connected to a boundary `'O'`** must **remain `'O'`**.
   → Mark these safe `'O'`s temporarily (e.g., as `'*'`).
2. After marking, **flip**:

   * All remaining `'O'` → `'X'`
   * All `'*'` → `'O'`

### Steps

1. Start DFS from all boundary `'O'`s.
2. In DFS, mark connected `'O'`s as `'*'`.
3. Do a final pass to flip everything accordingly.

---

### Code (Your Version)

```java
class Solution {
   public void solve(char[][] board) {
       if (board.length < 2 || board[0].length < 2)
           return;

       int m = board.length, n = board[0].length;

       // Step 1: Mark boundary-connected 'O's as '*'
       for (int i = 0; i < m; i++) {
           if (board[i][0] == 'O')
               boundaryDFS(board, i, 0);
           if (board[i][n-1] == 'O')
               boundaryDFS(board, i, n-1);
       }

       for (int j = 0; j < n; j++) {
           if (board[0][j] == 'O')
               boundaryDFS(board, 0, j);
           if (board[m-1][j] == 'O')
               boundaryDFS(board, m-1, j);
       }

       // Step 2: Flip remaining 'O' → 'X', and '*' → 'O'
       for (int i = 0; i < m; i++) {
           for (int j = 0; j < n; j++) {
               if (board[i][j] == 'O')
                   board[i][j] = 'X';
               else if (board[i][j] == '*')
                   board[i][j] = 'O';
           }
       }
   }

   private void boundaryDFS(char[][] board, int i, int j) {
       if (i < 0 || i >= board.length || j < 0 || j >= board[0].length)
           return;
       if (board[i][j] != 'O')
           return;

       board[i][j] = '*';
       boundaryDFS(board, i-1, j);
       boundaryDFS(board, i+1, j);
       boundaryDFS(board, i, j-1);
       boundaryDFS(board, i, j+1);
   }
}
```

---

## 🧠 Example Walkthrough

### Input

```java
[
  ['X','X','X','X'],
  ['X','O','O','X'],
  ['X','X','O','X'],
  ['X','O','X','X']
]
```

### Step 1 — Boundary DFS:

* Start DFS from boundary `'O'` → only `(3,1)` found.
* Mark it and all reachable `'O'`s as `'*'`.

Board after marking:

```java
[
  ['X','X','X','X'],
  ['X','O','O','X'],
  ['X','X','O','X'],
  ['X','*','X','X']
]
```

### Step 2 — Flip phase:

* All `'O'` → `'X'`
* All `'*'` → `'O'`

✅ Final Output:

```java
[
  ['X','X','X','X'],
  ['X','X','X','X'],
  ['X','X','X','X'],
  ['X','O','X','X']
]
```

---

## 🚀 Optimized Variant (Cleaner DFS)

An alternate version uses `swap()` helper and avoids redundant boundary conditions:

```java
void DFS(int i, int j, char[][] grid) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 'O')
        return;
    grid[i][j] = 'p';
    DFS(i+1, j, grid);
    DFS(i-1, j, grid);
    DFS(i, j+1, grid);
    DFS(i, j-1, grid);
}
```

This approach is cleaner and avoids the index bound checks inside each direction.

---

## 🧮 Complexity Analysis

| Aspect | Complexity   | Explanation                       |
| :----- | :----------- | :-------------------------------- |
| Time   | **O(m × n)** | Each cell visited at most once    |
| Space  | **O(m × n)** | Recursive DFS stack in worst case |

---

## ✅ Summary

| Method                         | Pros                                | Cons                                                           |
| :----------------------------- | :---------------------------------- | :------------------------------------------------------------- |
| **Brute-force surround check** | Straightforward                     | Inefficient; revisits same regions                             |
| **Your DFS boundary marking**  | Optimal; elegant; avoids redundancy | Recursive depth could cause stack overflow on very large grids |
| **BFS version**                | Prevents stack overflow             | Slightly more code                                             |

🔹 **Final Verdict:**
The boundary-DFS marking method (your version) is **the optimal and most widely accepted approach** for this problem.

---
