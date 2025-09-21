# 1572. Matrix Diagonal Sum – How & Why

## Problem Statement

Given an `n x n` square matrix `mat`, calculate the sum of the **primary** and **secondary diagonals**.

* Primary diagonal: elements `mat[i][i]`
* Secondary diagonal: elements `mat[i][n-1-i]`
* **Important:** If `n` is odd, the center element belongs to both diagonals and should only be counted once.

---

## 1. Brute-force / Simple Approach

```java
int sum = 0;
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        if (i == j || i + j == n - 1) {
            sum += mat[i][j];
        }
    }
}
return sum;
```

### How it works

* Iterate through every element.
* Add it if it lies on either diagonal.
* Works correctly but checks **all n² elements** unnecessarily.

### Time complexity: O(n²)

### Space complexity: O(1)

---

## 2. Optimized Approach (Your Solution)

```java
int summation = 0;
for (int i = 0; i < n; i++) {
    summation += mat[i][i];           // primary diagonal
    summation += mat[n-1-i][i];       // secondary diagonal
}

// Remove center element if n is odd
if (n % 2 == 1) summation -= mat[n/2][n/2];
```

### How it works_

1. **Primary diagonal:** indices `(0,0), (1,1), …, (n-1,n-1)` → `mat[i][i]`.
2. **Secondary diagonal:** indices `(n-1,0), (n-2,1), …, (0,n-1)` → `mat[n-1-i][i]`.
3. If `n` is odd, the middle element `mat[n/2][n/2]` is counted twice → subtract it once.
4. Return `summation`.

### Time Complexity: O(n)

Only one loop is needed; avoids nested loops.

### Space Complexity: O(1)

No extra space used.

---

### Example Walkthrough

**Input:**

```java
mat = [
 [1, 2, 3],
 [4, 5, 6],
 [7, 8, 9]
]
```

* Primary diagonal: `1 + 5 + 9 = 15`
* Secondary diagonal: `7 + 5 + 3 = 15`
* Total sum: `15 + 15 = 30`
* `n` is odd → subtract center `5` → `30 - 5 = 25` ✅

---

### Why this approach is elegant

* Single loop iteration → O(n) instead of O(n²).
* Clear distinction between primary and secondary diagonals.
* Handles the **odd-length center element** edge case explicitly.

---

This method is ideal for **square matrices**, especially when `n` is large, because it avoids unnecessary element checks.

---
