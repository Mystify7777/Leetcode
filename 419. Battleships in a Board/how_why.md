# 419. Battleships in a Board - Solution Explanation

## Problem Understanding

Given a 2D board containing `'X'` (battleship) and `'.'` (empty), count the number of battleships on the board.

**Rules:**

- Battleships are either horizontal or vertical (never diagonal)
- Battleships are separated by at least one cell (no adjacent battleships)
- Battleships are made of contiguous `'X'` cells

**Challenge:** Solve in **one pass** with **O(1) extra space** without modifying the board.

## Optimal Approach: Count "Tail" Cells

### Core Insight

Instead of traversing each battleship completely, we only count **one representative cell** per battleship. We choose the **bottom-right cell** (tail) of each battleship.

A cell is a "tail" if:

1. It's a battleship cell (`'X'`)
2. No battleship cell exists to its **right** (either end of row OR next cell is `'.'`)
3. No battleship cell exists **below** it (either end of column OR next cell is `'.'`)

This ensures we count each battleship exactly once.

### Code Walkthrough

```java
public int countBattleships(char[][] board) {
    if (board == null) {
        throw new IllegalArgumentException("Input is null");
    }
    if (board.length == 0 || board[0].length == 0) {
        return 0;
    }

    int rows = board.length;
    int cols = board[0].length;
    int count = 0;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            // Check if this is a "tail" cell
            if (board[i][j] == 'X'
                    && (j == cols - 1 || board[i][j + 1] == '.')
                    && (i == rows - 1 || board[i + 1][j] == '.')) {
                count++;
            }
        }
    }

    return count;
}
```

### Why This Works

**Key Properties:**

- Every battleship has exactly **one tail cell** (its bottom-right corner)
- No two battleships share the same tail cell (due to separation constraint)
- We traverse each cell exactly once → O(M × N) time
- We use only a counter variable → O(1) space

**Complexity:**

- **Time:** O(M × N) where M = rows, N = cols
- **Space:** O(1) - only using a counter

## Complete Example Walkthrough

### Input Board

```bash
X . . X
X . . X
X . . X
```

Let's trace through each cell:

#### Row 0

- **(0,0):** `'X'` → Check right: `board[0][1] = '.'` ✓, Check below: `board[1][0] = 'X'` ✗ → **Not a tail**
- **(0,1):** `'.'` → Skip
- **(0,2):** `'.'` → Skip
- **(0,3):** `'X'` → Check right: `j == 3` (last col) ✓, Check below: `board[1][3] = 'X'` ✗ → **Not a tail**

#### Row 1

- **(1,0):** `'X'` → Check right: `board[1][1] = '.'` ✓, Check below: `board[2][0] = 'X'` ✗ → **Not a tail**
- **(1,1):** `'.'` → Skip
- **(1,2):** `'.'` → Skip
- **(1,3):** `'X'` → Check right: `j == 3` (last col) ✓, Check below: `board[2][3] = 'X'` ✗ → **Not a tail**

#### Row 2 (last row)

- **(2,0):** `'X'` → Check right: `board[2][1] = '.'` ✓, Check below: `i == 2` (last row) ✓ → **This is a tail! Count = 1**
- **(2,1):** `'.'` → Skip
- **(2,2):** `'.'` → Skip
- **(2,3):** `'X'` → Check right: `j == 3` (last col) ✓, Check below: `i == 2` (last row) ✓ → **This is a tail! Count = 2**

**Result:** 2 battleships ✓

### Visual Representation with Tails Marked

```bash
X . . X
X . . X
T . . T    ← T = tail cells (bottom-right of each battleship)
```

## Another Example

### Input

```t
X X X X
```

#### Tracing

- **(0,0):** `'X'` → right: `board[0][1] = 'X'` ✗ → **Not a tail**
- **(0,1):** `'X'` → right: `board[0][2] = 'X'` ✗ → **Not a tail**
- **(0,2):** `'X'` → right: `board[0][3] = 'X'` ✗ → **Not a tail**
- **(0,3):** `'X'` → right: `j == 3` (last col) ✓, below: `i == 0` (last row) ✓ → **This is a tail! Count = 1**

**Result:** 1 battleship (horizontal) ✓

```t
X X X T    ← Only the rightmost cell is the tail
```

## Alternative Approach: Count "Head" Cells

We could instead count **top-left cells** (heads) - cells that are `'X'` with no `'X'` to their left or above.

```java
// Alternative: Count heads instead of tails
if (board[i][j] == 'X'
        && (j == 0 || board[i][j - 1] == '.')
        && (i == 0 || board[i - 1][j] == '.')) {
    count++;
}
```

This is equally valid! The choice between head/tail counting is arbitrary - both work perfectly.

## Why Not DFS/BFS?

**Traditional Approach (DFS/BFS):**

- For each unvisited `'X'`, run DFS/BFS to mark the entire battleship
- Space: O(M × N) for visited array or modifying the board
- Less elegant and uses extra space

**Our Approach:**

- Single pass, no marking needed
- Leverages the problem's constraint (valid board with separated battleships)
- More efficient and cleaner

## Edge Cases Handled

1. **Empty board:** Returns 0
2. **Single cell battleship:** `[['X']]` → Returns 1
3. **All horizontal:** Counts correctly
4. **All vertical:** Counts correctly
5. **Mixed orientations:** Works for any valid configuration

## Key Takeaway

This problem demonstrates **smart counting** - instead of processing entire objects, we identify a unique representative for each object and count those. This pattern appears in many grid problems where objects have specific shapes or constraints.
