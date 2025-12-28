# How & Why: LeetCode 1351 — Count Negative Numbers in a Sorted Matrix

This note explains a two-pointer solution that efficiently counts negative numbers in a sorted matrix by leveraging the sorted structure to avoid unnecessary comparisons.

---

## Problem

Given an `m × n` matrix `grid` where:

- Each row is sorted in non-increasing order (descending).
- Each column is sorted in non-increasing order (descending).

Goal: Count how many negative numbers appear in the matrix.

---

### Key Idea

Start from the **top-right corner** of the matrix. Use the sorted properties:

- If the current element is **non-negative**, move down (to a potentially smaller value).
- If the current element is **negative**, all elements to the left in that row are also negative. Count them all and move left.

This approach ensures each cell is visited at most once, giving us O(m + n) time complexity.

---

### Algorithm (pseudocode)

- Start at position `(row=0, col=cols-1)` (top-right corner).
- Initialize `count = 0`.

While `row < rows` AND `col >= 0`:

- If `grid[row][col] >= 0`:
  - Move down: `row++` (we need smaller values)
- Else (grid element is negative):
  - All elements from index `0` to `col` in this row are negative.
  - Add `col + 1` to `count` (or equivalently, `rows - row` in this specific implementation).
  - Move left: `col--`

Return `count`.

---

### Why This Works

1. **Sorted Properties**: Both rows and columns are sorted in descending order. This means:
   - All elements to the left of a negative number in the same row are also negative.
   - All elements below a non-negative number in the same column are also non-negative or smaller.

2. **Optimal Path**: By starting at the top-right and moving either down or left, we traverse the matrix in a way that:
   - Eliminates entire rows or columns from consideration.
   - Never revisits a cell.

3. **Efficient Counting**: When we find a negative number, we immediately know how many negatives are in that row (from column 0 to the current column).

---

### Implementation (Java)

```java
class Solution {
    public int countNegatives(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int row = 0;                      // Start at top
        int col = grid[0].length - 1;     // Start at rightmost column
        int count = 0;

        while (row < rows && col >= 0) {
            if (grid[row][col] >= 0) {
                // Non-negative, move down to find negatives
                row++;
            } else {
                // Negative found; all elements to the left in this row are negative
                count += rows - row;  // Count this and all rows below
                col--;                // Move left to check next column
            }
        }
        return count;
    }
}
```

---

### Complexity Analysis

- **Time Complexity**: O(m + n)
  - We start at one corner and move either down or left, traversing at most `m + n` cells.

- **Space Complexity**: O(1)
  - Only using a constant amount of extra space for pointers and counter.

---

### Example Walkthrough

Given matrix:

```m
[[ 4,  3,  2, -1],
 [ 3,  2,  1, -1],
 [ 1,  1, -1, -2],
 [-1, -1, -2, -3]]
```

Execution:

1. Start at `(0, 3)`: `grid[0][3] = -1` (negative) → count += 4 - 0 = 4, col = 2
2. At `(0, 2)`: `grid[0][2] = 2` (non-negative) → row = 1
3. At `(1, 2)`: `grid[1][2] = 1` (non-negative) → row = 2
4. At `(2, 2)`: `grid[2][2] = -1` (negative) → count += 4 - 2 = 2 (total: 6), col = 1
5. At `(2, 1)`: `grid[2][1] = 1` (non-negative) → row = 3
6. At `(3, 1)`: `grid[3][1] = -1` (negative) → count += 4 - 3 = 1 (total: 7), col = 0
7. At `(3, 0)`: `grid[3][0] = -1` (negative) → count += 4 - 3 = 1 (total: 8), col = -1
8. Exit loop

Result: 8 negatives
