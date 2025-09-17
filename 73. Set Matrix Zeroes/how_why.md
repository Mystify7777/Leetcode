# How_Why.md

## Problem

Given an `m x n` integer matrix, if an element is `0`, set its entire row and column to `0`.  
You must do it **in place** (without using extra space for another matrix).

---

## How (Step-by-step Solution)

### Approach: Using First Row & First Column as Markers

1. **Check the first column separately**  
   - Use a boolean flag `zeroinFirstCol` to track if the first column needs to be zeroed.  
   - We separate it because the first cell `(0,0)` would otherwise overlap between row and column markers.

2. **Mark zero rows and columns**  
   - For each cell `(row, col)` starting from column `1`:  
     - If `matrix[row][col] == 0`:  
       - Mark its row → set `matrix[row][0] = 0`  
       - Mark its column → set `matrix[0][col] = 0`

3. **Apply zeroes in reverse order**  
   - Traverse the matrix **backwards** (bottom-right to top-left).  
   - For each cell `(row, col)` (col ≥ 1):  
     - If `matrix[row][0] == 0` **or** `matrix[0][col] == 0`, set `matrix[row][col] = 0`.  
   - If `zeroinFirstCol` is true, set the entire first column to `0`.

---

## Why (Reasoning)

- Normally, we’d need two extra arrays to track zero rows and zero columns (O(m+n) space).  
- Instead, we **reuse the first row and first column as markers**, avoiding extra space.  
- Traversing **in reverse order** ensures we don’t overwrite markers before using them.

---

## Complexity Analysis

- **Time Complexity**: O(m × n) → Each cell is processed at most twice.  
- **Space Complexity**: O(1) → No extra space except the boolean flag.  

---

## Example Walkthrough

### Input

```text
[
 [1, 1, 1],
 [1, 0, 1],
 [1, 1, 1]
]
```

### Step 1: Mark zeros

- Encounter `matrix[1][1] = 0`
    → mark `matrix[1][0] = 0`, `matrix[0][1] = 0`

**Matrix now:**

```text
[
 [1, 0, 1],
 [0, 0, 1],
 [1, 1, 1]
]
```

### Step 2: Apply zeros in reverse

- Row 1 and Column 1 → set entire row and column to 0.

**Final Matrix:**

```text
[
 [1, 0, 1],
 [0, 0, 0],
 [1, 0, 1]
]
```

---

## Alternate Approaches

1. Brute Force (O(m×n) space)

   - Use two arrays to track zero rows and zero columns.

   - Simpler but not in-place.

2. Set-based Approach

   - Store rows and columns in hash sets, then iterate again to zero them.

   - Uses O(m+n) extra space.

3. In-place with markers (optimal, current solution)

   - Reuses first row and first column.

   - Achieves O(1) extra space.

   ---
