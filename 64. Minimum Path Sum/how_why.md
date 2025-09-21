# How\_Why.md – Minimum Path Sum (LeetCode 64)

## Problem

We are given an `m x n` grid filled with non-negative numbers.
The task: find a path from the **top-left** to the **bottom-right** which minimizes the sum of numbers along the path.
We can **only move right or down**.

---

## Brute Force Approach

### Idea

* From each cell `(i, j)`, recursively explore both options:

  1. Move **right** `(i, j+1)`
  2. Move **down** `(i+1, j)`
* At each step, keep adding the current cell’s value.
* The minimum of all possible path sums gives the answer.

### Complexity

* Time = **O(2^(m+n))** (exponential).
* Space = recursion depth **O(m+n)**.

### Example (2x2 grid)

```text
[1, 3]
[1, 5]
```

Paths:

* Right → Down = 1 → 3 → 5 = 9
* Down → Right = 1 → 1 → 5 = 7 ✅ (minimum)

❌ Limitation: Too slow for large grids.

---

## Top-Down DP (Memoization)

### Idea_

* Brute force revisits the same subproblems many times.
* Use **memoization** to cache results.
* Define `dfs(i, j)` = min path sum from `(i, j)` to bottom-right.
* Recursive formula:

  ```java
  dfs(i, j) = grid[i][j] + min(dfs(i+1, j), dfs(i, j+1))
  ```

* Base case: bottom-right cell returns its value.
* Use a `dp` array to store computed results.

### Complexity_

* Time = **O(m\*n)** (each cell computed once).
* Space = **O(m\*n)** for memo + recursion stack.

### Example Walkthrough_

```java
Grid = [ [1, 3, 1],
         [1, 5, 1],
         [4, 2, 1] ]
```

* Start at (0,0) = 1 + min(path down, path right).
* Cache results for each cell.
* Eventually return 7.

✅ Much faster than brute force, but uses extra space.

---

## Bottom-Up DP (Your Code – In-place)

### Idea:__

* Instead of recursion, fill the grid iteratively.
* First row = cumulative sum (only from left).
* First column = cumulative sum (only from above).
* Other cells = value + min(top, left).

### Complexity:__

* Time = **O(m\*n)**.
* Space = **O(1)** (in-place).

### Example Walkthrough:__

```text
Step 1: First column → [1,2,6]
Step 2: First row    → [1,4,5]
Step 3: Fill rest    → Answer = 7
```

---

## Best Method

👉 **In-place DP (Bottom-up)** is the best tradeoff:

* Time = O(m\*n)
* Space = O(1)
* Avoids recursion overhead and extra memory.

---

✅ Final Answer = **Use your in-place DP solution.**

---

Do you want me to actually **write out the Java code** for the memoized recursion version too, so you have all three implementations side by side?
