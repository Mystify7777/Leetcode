# How_Why.md

## Problem

Given an `m x n` matrix, return all elements of the matrix in **spiral order**.

---

## How (Step-by-step Solution)

### Approach: Layer-by-Layer Traversal

We simulate the spiral traversal using **four boundaries**:

- `rowBegin` → starting row
- `rowEnd` → ending row
- `colBegin` → starting column
- `colEnd` → ending column

Steps:

1. Traverse **right** across the top row (`rowBegin`), then move the boundary down.
2. Traverse **down** the last column (`colEnd`), then move the boundary left.
3. Traverse **left** across the bottom row (`rowEnd`), then move the boundary up (only if `rowBegin <= rowEnd`).
4. Traverse **up** the first column (`colBegin`), then move the boundary right (only if `colBegin <= colEnd`).
5. Repeat until all boundaries collapse.

---

## Why (Reasoning)

- By shrinking the boundaries after each traversal:
  - The outer layer gets covered first.
  - The next iteration covers the inner spiral.
- This avoids revisiting cells and ensures all are covered exactly once.

---

## Complexity Analysis

- **Time Complexity**: O(m × n), where `m` = number of rows and `n` = number of columns.  
  Every element is visited exactly once.  
- **Space Complexity**: O(1) (excluding the output list).  
  Uses only pointers for row and column boundaries.

---

## Example Walkthrough

### Input

```text
[
 [1, 2, 3],
 [4, 5, 6],
 [7, 8, 9]
]
```

### Spiral Traversal

   1. Traverse right → `1, 2, 3`

   2. Traverse down → `6, 9`

   3. Traverse left → `8, 7`

   4. Traverse up →`4`

   5. Traverse right (inner layer) → `5`

### Output

```text
[1, 2, 3, 6, 9, 8, 7, 4, 5]
```

---

## Alternate Approaches

1. Direction Vectors + Visited Matrix

   - Use direction vectors (right, down, left, up) and simulate movement.

   - Keep track of visited cells.

   - Simpler logic but requires O(m × n) extra space.

2. Recursive Approach

   - Peel the matrix layer by layer recursively.

   - Elegant but less efficient due to recursion overhead.

---
