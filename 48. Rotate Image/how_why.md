# How_Why.md

## Problem

You are given an `n x n` 2D matrix representing an image. Rotate the image by **90 degrees clockwise**, in-place (without using extra space for another matrix).

---

## How (Step-by-step Solution)

### Approach: Transpose + Reverse

1. **Transpose the matrix**  
   - Swap elements across the diagonal.  
   - i.e., for all `i < j`: swap `matrix[i][j]` and `matrix[j][i]`.

   Example (after transpose):

    ```text
    1 2 3       1 4 7
    4 5 6  -->  2 5 8
    7 8 9       3 6 9
    ```

2. **Reverse each row**  

    - Swap left and right elements in each row.  
    - This gives the 90° clockwise rotation.

    Example (after row reversal):

    ```text
    1 4 7       7 4 1
    2 5 8  -->  8 5 2
    3 6 9       9 6 3
    ```

3. Done — matrix is rotated **in-place**.

---

## Why (Reasoning)

- A **clockwise rotation** can be decomposed into two steps:

1. **Transpose** (rows → columns).
2. **Reverse rows** (to fix orientation).

- This avoids needing extra space and works directly within the matrix.

---

## Complexity Analysis

- **Time Complexity**: O(n²) (every element visited during transpose and reverse).  
- **Space Complexity**: O(1) (in-place swaps only).

---

## Example Walkthrough

### Input

```text
[
[1,2,3],
[4,5,6],
[7,8,9]
]
```

### Step 1: Transpose

```text
[
[1,4,7],
[2,5,8],
[3,6,9]
]
```

### Step 2: Reverse each row

```text
[
[7,4,1],
[8,5,2],
[9,6,3]
]
```

### Output

```text
[
[7,4,1],
[8,5,2],
[9,6,3]
]
```

---

## Alternate Approaches

1. **Rotate layer by layer (4-way swap)**  
   - Rotate elements on the boundary first, then inner layers.  
   - More complex but avoids two passes.

2. **Using extra matrix** (not in-place)  
   - Place element `matrix[i][j]` into `newMatrix[j][n-1-i]`.  
   - Requires O(n²) extra space.
