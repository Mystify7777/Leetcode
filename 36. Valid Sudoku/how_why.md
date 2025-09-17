# How_Why.md

## Problem

Determine if a given 9×9 Sudoku board is valid.  
The board is partially filled, and only the following rules must hold:

1. Each row must contain the digits `1–9` without repetition.
2. Each column must contain the digits `1–9` without repetition.
3. Each of the nine 3×3 sub-boxes must contain the digits `1–9` without repetition.

---

## How (Step-by-step Solution)

### Approach: HashSet Tracking

1. Create 3 arrays of **HashSets**:
   - `rows[9]` → track numbers in each row.
   - `cols[9]` → track numbers in each column.
   - `boxes[9]` → track numbers in each 3×3 sub-grid.
2. Iterate through every cell `(r, c)` in the 9×9 board:
   - If the cell contains `'.'`, skip it.
   - Otherwise:
     - Compute the box index as:

       ```java
       boxIndex = (r / 3) * 3 + (c / 3)
       ```

     - Check if the digit already exists in:
       - `rows[r]`
       - `cols[c]`
       - `boxes[boxIndex]`
       - If yes → return `false`.
     - Otherwise, insert the digit into all three sets.
3. If no rule is violated → return `true`.

---

## Why (Reasoning)

- Each number must be **unique within its row, column, and 3×3 sub-box**.
- HashSets make it efficient to check duplicates (O(1) per lookup).
- The box index formula ensures each of the 9 boxes is uniquely identified.

---

## Complexity Analysis

- **Time Complexity**: O(81) = O(1) (since Sudoku is always 9×9).  
- **Space Complexity**: O(27 × 9) = O(1) (fixed-size sets for rows, columns, and boxes).

---

## Example Walkthrough

### Input

```java
[
["5","3",".",".","7",".",".",".","."],
["6",".",".","1","9","5",".",".","."],
[".","9","8",".",".",".",".","6","."],
["8",".",".",".","6",".",".",".","3"],
["4",".",".","8",".","3",".",".","1"],
["7",".",".",".","2",".",".",".","6"],
[".","6",".",".",".",".","2","8","."],
[".",".",".","4","1","9",".",".","5"],
[".",".",".",".","8",".",".","7","9"]
]
```

### Step-by-step

- At (0,0): value = `5`. Add to row 0, col 0, box 0.  
- At (0,1): value = `3`. Add to row 0, col 1, box 0.  
- … continue until all cells are checked.  
- No conflicts found → board is valid.

### Output

`true`

---

## Alternate Approaches

1. **Boolean arrays instead of HashSets**
   - Use `boolean[9][9]` for rows, columns, and boxes.
   - Faster and memory-efficient.
   - More code-heavy but avoids HashSet overhead.

2. ✅ **Recommended for interviews**: HashSet version (cleaner, more readable).
