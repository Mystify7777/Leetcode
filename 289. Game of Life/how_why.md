# 289. Game of Life - How & Why

## Problem Overview

According to Conway's Game of Life rules, update a 2D board in-place where:

- Each cell is either live (1) or dead (0)
- The next state depends on the current state and the number of live neighbors (8-directional)

**Rules:**

1. **Live cell** with < 2 live neighbors → dies (underpopulation)
2. **Live cell** with 2-3 live neighbors → lives (survival)
3. **Live cell** with > 3 live neighbors → dies (overpopulation)
4. **Dead cell** with exactly 3 live neighbors → becomes alive (reproduction)

**Challenge:** Update **in-place** (without using extra board space)

## Algorithm Explanation

### Key Insight

Use **bit manipulation** to store both the current and next state in the same integer:

- **Bit 0 (LSB):** Current state (original value)
- **Bit 1:** Next state (computed value)

This allows us to:

1. Read the current state during neighbor counting
2. Store the next state without losing current information
3. Update all cells simultaneously in the final pass

### State Encoding

| Current | Next | Binary | Decimal | Meaning |
| --------- | ------ | -------- | --------- | --------- |
| 0 | 0 | 00 | 0 | Dead → Dead |
| 1 | 0 | 01 | 1 | Live → Dead |
| 0 | 1 | 10 | 2 | Dead → Live |
| 1 | 1 | 11 | 3 | Live → Live |

### Step-by-Step Logic

1. **Count live neighbors** for each cell (using bit 0)
2. **Apply rules** and set bit 1 based on current state and neighbor count:
   - Live cell (1) with 2-3 neighbors → set bit 1 (value becomes 3)
   - Dead cell (0) with 3 neighbors → set bit 1 (value becomes 2)
3. **Right shift all cells** by 1 to extract the next state (bit 1 becomes bit 0)

### Time & Space Complexity

- **Time:** O(m × n) where m = rows, n = columns
- **Space:** O(1) - In-place update using bit manipulation

## Code Walkthrough

### Main Function

```java
public void gameOfLife(int[][] board) {
    if (board == null || board.length == 0) return;
    int m = board.length, n = board[0].length;

    // Phase 1: Encode next state in bit 1
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int lives = liveNeighbors(board, m, n, i, j);

            // Live cell survives with 2-3 neighbors
            if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
                board[i][j] = 3; // 01 → 11 (live → live)
            }
            // Dead cell becomes alive with exactly 3 neighbors
            if (board[i][j] == 0 && lives == 3) {
                board[i][j] = 2; // 00 → 10 (dead → live)
            }
        }
    }

    // Phase 2: Extract next state by right shifting
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            board[i][j] >>= 1;  // Get bit 1 (next state)
        }
    }
}
```

### Live Neighbors Counter

```java
public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
    int lives = 0;
    
    // Check all 8 neighbors (and self)
    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
            lives += board[x][y] & 1;  // Extract bit 0 (current state)
        }
    }
    
    lives -= board[i][j] & 1;  // Exclude self
    return lives;
}
```

### Key Operations Explained

**`board[x][y] & 1`** - Extracts current state (bit 0)

```shell
11 & 1 = 01 = 1  (currently live)
10 & 1 = 00 = 0  (currently dead)
01 & 1 = 01 = 1  (currently live)
00 & 1 = 00 = 0  (currently dead)
```

**`board[i][j] >>= 1`** - Right shift to get next state

```shell
11 >> 1 = 01 = 1  (becomes live)
10 >> 1 = 01 = 1  (becomes live)
01 >> 1 = 00 = 0  (becomes dead)
00 >> 1 = 00 = 0  (becomes dead)
```

## Example Walkthrough

**Input:**

```math
[0,1,0]
[0,0,1]
[1,1,1]
[0,0,0]
```

### Phase 1: Count Neighbors & Encode Next State

**Cell `[0][1]` (value = 1, has 1 live neighbor):**

- Current: Live (1)
- Neighbors: 1 (< 2)
- Rule: Dies (underpopulation)
- Stays: 1 (01 → 01, doesn't meet survival condition)

**Cell `[1][2]` (value = 1, has 1 live neighbor):**

- Current: Live (1)
- Neighbors: 1
- Rule: Dies
- Stays: 1

**Cell `[2][0]` (value = 1, has 1 live neighbor):**

- Current: Live (1)
- Neighbors: 1
- Rule: Dies
- Stays: 1

**Cell `[2][1]` (value = 1, has 3 live neighbors):**

- Current: Live (1)
- Neighbors: 3
- Rule: Survives
- Becomes: 3 (01 → 11)

**Cell `[2][2]` (value = 1, has 2 live neighbors):**

- Current: Live (1)
- Neighbors: 2
- Rule: Survives
- Becomes: 3 (01 → 11)

**Cell `[1][1]` (value = 0, has 3 live neighbors):**

- Current: Dead (0)
- Neighbors: 3
- Rule: Reproduction
- Becomes: 2 (00 → 10)

**Cell `[0][0]` (value = 0, has 1 live neighbor):**

- Current: Dead (0)
- Neighbors: 1
- Rule: Stays dead
- Stays: 0

**After Phase 1:**

```math
[0,1,0]
[0,2,1]
[1,3,3]
[0,0,0]
```

### Phase 2: Right Shift to Extract Next State

```shell
[0>>1, 1>>1, 0>>1]     [0,0,0]
[0>>1, 2>>1, 1>>1]  =  [0,1,0]
[1>>1, 3>>1, 3>>1]     [0,1,1]
[0>>1, 0>>1, 0>>1]     [0,0,0]
```

**Final Output:**

```math
[0,0,0]
[0,1,0]
[0,1,1]
[0,0,0]
```

## Detailed Neighbor Counting

### For Cell `[2][1]` (the middle bottom cell)

**8-directional neighbors:**

```math
     [1][0]  [1][1]  [1][2]
     [2][0]  [2][1]  [2][2]
     [3][0]  [3][1]  [3][2]
```

Original board values:

```math
     0    0    1
     1    ?    1
     0    0    0
```

**Counting:**

- Loop through x from max(2-1, 0) = 1 to min(2+1, 3) = 3
- Loop through y from max(1-1, 0) = 0 to min(1+1, 2) = 2
- Sum up all `board[x][y] & 1`
- Total: 0+0+1+1+1+1+0+0+0 = 4
- Subtract self: 4 - 1 = 3 live neighbors ✓

## Why This Approach Works

### Problem with Naive Approach

```java
// DON'T DO THIS - loses current state!
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        int lives = countNeighbors(board, i, j);
        // If we update board[i][j] here, later cells
        // will count the NEW state instead of CURRENT state!
        board[i][j] = applyRules(board[i][j], lives);
    }
}
```

### Bit Manipulation Solution

- **Current state preserved** in bit 0 during neighbor counting
- **Next state stored** in bit 1 without interfering
- **All updates happen simultaneously** in the final right shift

## Alternative Approaches

### 1. Extra Board (Space O(m×n))

```java
public void gameOfLife(int[][] board) {
    int m = board.length, n = board[0].length;
    int[][] copy = new int[m][n];
    
    // Copy original board
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            copy[i][j] = board[i][j];
        }
    }
    
    // Update based on copy
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int lives = countNeighbors(copy, i, j);
            board[i][j] = applyRules(copy[i][j], lives);
        }
    }
}
```

- **Pros:** Simpler logic, easier to understand
- **Cons:** Uses O(m×n) extra space

### 2. HashMap/Set (Infinite Board)

```java
// For infinite board problems
Set<String> alive = new HashSet<>();
// Store coordinates of live cells
// Process all relevant cells based on current alive set
```

- **Use case:** When board is conceptually infinite
- **Space:** Proportional to number of live cells

### 3. State Encoding with Different Values

```java
// Use negative numbers or large values
// -1: was alive, now dead
// -2: was dead, now alive
// Then convert back
```

- **Less elegant** than bit manipulation
- **Same space complexity** as bit solution

## Edge Cases

1. **Empty board** - `board = null` or `board.length = 0`
   - Handled by early return

2. **Single cell** - `board = [[1]]`
   - 0 neighbors → dies
   - Result: `[[0]]`

3. **All dead** - `board = [[0,0],[0,0]]`
   - No changes
   - Result: `[[0,0],[0,0]]`

4. **All alive** - `board = [[1,1],[1,1]]`
   - Each has 3 neighbors → survives
   - Result: `[[1,1],[1,1]]`

5. **Corner cells** - Only 3 neighbors
   - Correctly handled by `Math.max` and `Math.min`

6. **Edge cells** - Only 5 neighbors
   - Correctly handled by boundary checks

## Bit Manipulation Patterns

### Why Use Bit 0 for Current, Bit 1 for Next?

- **Natural ordering:** Right shift extracts result naturally
- **& 1 operation:** Clean way to get current state
- **No interference:** Setting bit 1 doesn't affect bit 0

### Alternative: Use Bit 1 for Current?

```java
// Would need left shift and more complex masking
board[i][j] = (nextState << 1) | currentState;
// Extract: board[i][j] >> 1
```

- **More complex** and less intuitive

## Performance Optimization

### Current Solution

- **Two passes:** One to encode, one to decode
- **Neighbor counting:** 9 cells max per cell (O(1))
- **Total:** O(m × n) time, O(1) space

### Possible Optimizations

1. **Cache-friendly access:** Already optimal (row-major order)
2. **Avoid repeated bounds checking:** Current solution is clean
3. **Parallel processing:** Could parallelize outer loop (not shown)

## Key Takeaways

1. **Bit manipulation enables in-place updates** without extra space
2. **Two-bit encoding** stores both current and next state simultaneously
3. **`& 1` extracts current state** during neighbor counting
4. **`>> 1` extracts next state** in final pass
5. **Phase separation** ensures all updates happen simultaneously
6. **Boundary handling** with `Math.max` and `Math.min` is elegant
7. **Space O(1)** is achieved through clever state encoding

## Interview Tips

1. **Start with clarifying questions:**
   - Is the board finite or infinite?
   - Can we use extra space?
   - Should we optimize for space or clarity?

2. **Discuss naive approach first:**
   - Mention the extra board solution
   - Explain why it's simpler but uses O(m×n) space

3. **Introduce bit manipulation:**
   - Explain the 2-bit encoding scheme
   - Show how it enables in-place updates

4. **Walk through a small example:**
   - Use 2×3 or 3×3 board
   - Show the intermediate state after phase 1

5. **Discuss trade-offs:**
   - Bit solution: O(1) space, more complex
   - Extra board: O(m×n) space, simpler
   - Choice depends on constraints

This solution is **optimal for space-constrained scenarios** and demonstrates **advanced bit manipulation skills**! 🎯
