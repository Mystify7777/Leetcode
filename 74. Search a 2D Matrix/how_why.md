# How\_Why.md: Search a 2D Matrix (LeetCode 74)

## Problem

Given an `m x n` matrix with the following properties:

1. Integers in each row are sorted from left to right.
2. The first integer of each row is greater than the last integer of the previous row.

Write a function to determine if a given target exists in the matrix.

**Example:**

```java
Input:
matrix = [
  [1, 3, 5, 7],
  [10, 11, 16, 20],
  [23, 30, 34, 60]
]
target = 3
Output: true
```

---

## Brute-force Approach

### Idea

* Iterate through each cell of the matrix.
* Check if the current cell equals the target.

### Code

```java
public boolean searchMatrixBF(int[][] matrix, int target) {
    for (int[] row : matrix) {
        for (int val : row) {
            if (val == target) return true;
        }
    }
    return false;
}
```

### Example Walkthrough

* Target = 3
* Check: 1 → 3 ✅ found.

### Limitations

* **Time Complexity:** O(m\*n) → inefficient for large matrices.
* **Space Complexity:** O(1) → no extra space, but slow.

---

## Optimized Approach (Binary Search by Treating Matrix as 1D)

### Idea_

* Treat the `m x n` matrix as a **flattened sorted array** of length `m*n`.
* Index mapping:

  ```java
  1D index = mid
  row = mid / n
  col = mid % n
  ```

* Perform **binary search** on the virtual 1D array.

### Code_

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length;
    int n = matrix[0].length;
    int left = 0, right = m * n - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int mid_val = matrix[mid / n][mid % n];

        if (mid_val == target) return true;
        else if (mid_val < target) left = mid + 1;
        else right = mid - 1;
    }
    return false;
}
```

### Example Walkthrough_

Matrix as 1D: `[1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]`

* Target = 3
* left=0, right=11 → mid=5 → matrix\[5/4]\[5%4]=matrix\[1]\[1]=11 → target < mid → right=4
* left=0, right=4 → mid=2 → matrix\[2/4]\[2%4]=matrix\[0]\[2]=5 → target < mid → right=1
* left=0, right=1 → mid=0 → matrix\[0]\[0]=1 → target > mid → left=1
* left=1, right=1 → mid=1 → matrix\[0]\[1]=3 ✅ found.

### Complexity

* **Time Complexity:** O(log(m\*n)) → logarithmic search.
* **Space Complexity:** O(1) → no extra space used.

---

### Key Takeaways

1. Brute-force works but is linear in matrix size.
2. Using binary search on a **flattened view** leverages the sorted property efficiently.
3. Index mapping (`row = mid/n, col = mid%n`) is the key trick.

---
