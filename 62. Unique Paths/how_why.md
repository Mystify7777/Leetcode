# How\_Why.md: Unique Paths (LeetCode 62)

## Problem

Given an `m x n` grid, a robot starts at the top-left corner and can only move **right** or **down**. Find the number of unique paths to reach the bottom-right corner.

**Example:**

```java
Input: m = 3, n = 2
Output: 3
Paths: 
1. Right → Right → Down
2. Right → Down → Right
3. Down → Right → Right
```

---

## Brute-force Approach

### Idea

* Recursively explore all paths from `(0,0)` to `(m-1,n-1)`:

  1. Move **down**.
  2. Move **right**.
* Base cases:

  * Out-of-bounds → return 0.
  * Reached target → return 1.

### Code

```java
public int uniquePathsBF(int m, int n) {
    return dfs(0, 0, m, n);
}

private int dfs(int row, int col, int m, int n) {
    if (row >= m || col >= n) return 0;
    if (row == m - 1 && col == n - 1) return 1;
    return dfs(row + 1, col, m, n) + dfs(row, col + 1, m, n);
}
```

### Example Walkthrough

```java
Grid 3x2:
(0,0) → (1,0) → (2,0) → (2,1) → target (1 path)
(0,0) → (1,0) → (1,1) → (2,1) → target (1 path)
(0,0) → (0,1) → (1,1) → (2,1) → target (1 path)
Total paths = 3
```

**Limitations:**

* **Time Complexity:** O(2^(m+n)) — exponential.
* Explores overlapping subproblems multiple times → inefficient.

---

## User Approach (Dynamic Programming with 2 Rows)

### Idea_

* Use **DP** to store number of paths to each cell.
* Transition formula:

  ```java
  dp[row][col] = dp[row-1][col] + dp[row][col-1]
  ```
  
* Only store **current row** and **above row** to save space.

### Code_

```java
public int uniquePaths(int m, int n) {
    int[] aboveRow = new int[n];
    Arrays.fill(aboveRow, 1);

    for (int row = 1; row < m; row++) {
        int[] currentRow = new int[n];
        Arrays.fill(currentRow, 1);
        for (int col = 1; col < n; col++) {
            currentRow[col] = currentRow[col - 1] + aboveRow[col];
        }
        aboveRow = currentRow;
    }

    return aboveRow[n - 1];
}
```

### Example Walkthrough_

```java
3x2 Grid:
Initialize first row: [1,1]

Row 1:
col 1 → currentRow[1] = currentRow[0] + aboveRow[1] = 1 + 1 = 2
Row 1 array: [1,2]

Row 2:
col 1 → currentRow[1] = 1 + 2 = 3
Row 2 array: [1,3]

Result: 3 paths
```

**Advantages:**

* **Time Complexity:** O(m\*n)
* **Space Complexity:** O(n) — only two rows needed.

---

## Optimized Approach (Combinatorics)

### Idea__

* Moving from top-left to bottom-right: need **m-1 downs** and **n-1 rights** in any order.
* Total moves = `(m-1 + n-1)`
* Number of paths = `C(m+n-2, m-1)` (binomial coefficient).

### Formula

```java
paths = (m+n-2)! / ((m-1)! * (n-1)!)
```

* Can compute iteratively to avoid overflow.

### Example Walkthrough__

```java
3x2 Grid:
Total moves = 3+2-2 = 3
Choose down moves = 3 choose 2 = 3
Matches DP result
```

**Advantages:**

* **Time Complexity:** O(min(m,n)) using iterative multiplication/division
* **Space Complexity:** O(1)

---

### Key Takeaways

1. Brute-force is simple but inefficient due to overlapping subproblems.
2. DP reduces time complexity and can be optimized to use only two rows.
3. Combinatorial approach is **mathematically optimal** if factorials can be computed safely.

---
