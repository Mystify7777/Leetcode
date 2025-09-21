# How\_Why.md: Spiral Matrix II (LeetCode 59)

## Problem

Given an integer `n`, generate an `n x n` matrix filled with elements from `1` to `n^2` in **spiral order**.

**Example:**

```java
Input: n = 3
Output:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]
```

---

## Brute-force Approach

### Idea

* Fill the matrix in **layers** starting from top-left.
* Use nested loops to fill one layer completely before moving to the next.
* Use multiple conditions and separate counters for each direction.

### Code

```java
public int[][] generateMatrixBF(int n) {
    int[][] matrix = new int[n][n];
    int num = 1;
    for (int layer = 0; layer < (n+1)/2; layer++) {
        // top row
        for (int i = layer; i < n-layer; i++) matrix[layer][i] = num++;
        // right column
        for (int i = layer+1; i < n-layer; i++) matrix[i][n-layer-1] = num++;
        // bottom row
        for (int i = n-layer-2; i >= layer; i--) matrix[n-layer-1][i] = num++;
        // left column
        for (int i = n-layer-2; i > layer; i--) matrix[i][layer] = num++;
    }
    return matrix;
}
```

### Example Walkthrough

`n = 3`:

1. Layer 0:

   * Top row → 1, 2, 3
   * Right column → 4
   * Bottom row → 5, 6
   * Left column → 7
2. Layer 1 (center) → 8 → 9

**Limitation:**

* Brute-force is essentially fine, but messy edge conditions can lead to off-by-one errors.
* Needs careful handling when `n` is odd.

---

## User Approach (Layer by Layer with Pointers)

### Idea_

* Maintain **4 pointers**: `rowStart`, `rowEnd`, `colStart`, `colEnd`.
* Fill the top, right, bottom, and left edges of the current rectangle.
* Increment or decrement pointers after filling each side.
* Continue until `rowStart > rowEnd` or `colStart > colEnd`.

### Code_

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    int rowStart = 0, rowEnd = n-1;
    int colStart = 0, colEnd = n-1;
    int num = 1;
    
    while (rowStart <= rowEnd && colStart <= colEnd) {
        for (int i = colStart; i <= colEnd; i++) matrix[rowStart][i] = num++;
        rowStart++;
        
        for (int i = rowStart; i <= rowEnd; i++) matrix[i][colEnd] = num++;
        colEnd--;
        
        for (int i = colEnd; i >= colStart; i--) 
            if (rowStart <= rowEnd) matrix[rowEnd][i] = num++;
        rowEnd--;
        
        for (int i = rowEnd; i >= rowStart; i--) 
            if (colStart <= colEnd) matrix[i][colStart] = num++;
        colStart++;
    }
    
    return matrix;
}
```

### Example Walkthrough_

`n = 3`:

```java
Initial pointers: rowStart=0, rowEnd=2, colStart=0, colEnd=2
Step 1: Fill top → 1 2 3
Step 2: Fill right → 4
Step 3: Fill bottom → 5 6
Step 4: Fill left → 7
Update pointers → rowStart=1, rowEnd=1, colStart=1, colEnd=1
Step 5: Fill top (center) → 8
Matrix completed with 9
```

**Advantages:**

* Clean handling of odd and even `n`.
* Easy to visualize.
* Linear time complexity `O(n^2)` (must fill all elements).

---

## Optimized Approach

* The user approach is **already optimal**:

  * **Time Complexity:** `O(n^2)` (minimum required to fill `n^2` elements)
  * **Space Complexity:** `O(1)` extra (apart from output matrix)
* Any alternative would still have `O(n^2)` time because we need to assign all elements.

---

### Key Takeaways

1. Spiral filling is best handled using **4 pointers** for boundaries.
2. Always check `rowStart <= rowEnd` and `colStart <= colEnd` before filling bottom/left.
3. Complexity is inherently `O(n^2)`; cannot improve asymptotically.

---
