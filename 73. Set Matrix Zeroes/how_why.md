# How\_Why.md: Set Matrix Zeroes (LeetCode 73)

## Problem

Given an `m x n` matrix, if an element is 0, set its **entire row and column** to 0. Do it **in-place**.

**Example:**

```java
Input:
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output:
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]
```

---

## Brute-force Approach

### Idea

* Iterate over the matrix.
* For each 0 found, mark its row and column (e.g., with a placeholder like `-1`) to indicate it will become 0.
* Then iterate again to set all marked cells to 0.

### Code

```java
public void setZeroesBF(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;

    boolean[][] mark = new boolean[m][n];

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] == 0) mark[i][j] = true;
        }
    }

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mark[i][j]) {
                for (int k = 0; k < n; k++) matrix[i][k] = 0;
                for (int k = 0; k < m; k++) matrix[k][j] = 0;
            }
        }
    }
}
```

### Limitations

* **Time Complexity:** O((m*n)*(m+n)) → inefficient for large matrices.
* **Space Complexity:** O(m\*n) for the extra mark array.

---

## User Approach (Optimized In-place Using First Row/Column as Markers)

### Idea_

* Use **first row** and **first column** as markers for rows/columns that should be zeroed.
* Keep a boolean to track if the **first column** itself has any zero.
* Traverse matrix top-down, left-right:

  * For each zero at `(i,j)`, set `matrix[i][0] = 0` and `matrix[0][j] = 0`.
* Traverse matrix bottom-up, right-left:

  * If `matrix[i][0] == 0 || matrix[0][j] == 0`, set `matrix[i][j] = 0`.

### Code_

```java
public void setZeroes(int[][] matrix) {
    boolean zeroinFirstCol = false;

    for (int row = 0; row < matrix.length; row++) {
        if (matrix[row][0] == 0) zeroinFirstCol = true;
        for (int col = 1; col < matrix[0].length; col++) {
            if (matrix[row][col] == 0) {
                matrix[row][0] = 0;
                matrix[0][col] = 0;
            }
        }
    }

    for (int row = matrix.length - 1; row >= 0; row--) {
        for (int col = matrix[0].length - 1; col >= 1; col--) {
            if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                matrix[row][col] = 0;
            }
        }
        if (zeroinFirstCol) matrix[row][0] = 0;
    }
}
```

### Example Walkthrough

```java
Input:
1 1 1
1 0 1
1 1 1

Step 1: Mark zeros in first row/col:
1 0 1
0 0 1
1 1 1

Step 2: Set zeros using markers (traverse bottom-up):
1 0 1
0 0 0
1 0 1

Result matches expected output.
```

### Complexity

* **Time Complexity:** O(m\*n) — each cell visited twice.
* **Space Complexity:** O(1) — in-place, only one extra boolean for first column.

---

### Key Takeaways

1. Brute-force works but wastes both space and time.
2. Using the first row and column as markers is a **clever trick** to reduce space.
3. Always handle the **first row and first column carefully**, since they’re both markers and part of the matrix.

---
