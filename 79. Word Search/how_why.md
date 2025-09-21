
# How\_Why.md – Word Search (LeetCode 79)

## Problem

Given an `m x n` board and a word, return **true** if the word exists in the grid.

* Letters must be **adjacent horizontally or vertically**.
* The same cell **cannot be used more than once**.

---

## Brute Force Approach

### Idea

* For every cell in the board, start a DFS/Backtracking search.
* Try to match the word character-by-character in 4 directions (up, down, left, right).
* Mark cells as visited during the path, then unmark when backtracking.

### Complexity

* Worst case: O(m \* n \* 4^L), where L = length of word.
* Space = O(L) recursion stack + O(m\*n) visited array.

### Example

```java
board = [
  [A, B, C, E],
  [S, F, C, S],
  [A, D, E, E]
]
word = "ABCCED"

Start at (0,0) A → B → C → C → E → D
Found full match → return true.
```

✅ Straightforward, guarantees correctness.

---

## Your First Approach (Simple Backtracking)

```java
private boolean backtrack(char[][] board, String word, boolean[][] visited, int i, int j, int index)
```

* Starts DFS from each cell matching `word[0]`.
* Explores all 4 neighbors.
* Uses `visited` array to avoid reuse.
* Backtracks by resetting `visited[i][j] = false` if path fails.

### Complexity_

* Same as brute force O(m*n*4^L).
* Clean, minimal, easy to follow.

---

## Optimized Approach (Pruning + Heuristic)

In the second solution, a few smart **optimizations** are applied:

1. **Character frequency pruning**:

   * Count frequency of all letters in board.
   * If any letter in `word` is more frequent than in `board`, return false early.

2. **Reversing word if beneficial**:

   * If the starting character is rarer than the ending character, reverse the word.
   * Why? → reduces branching by starting from a rarer letter.

3. **Compact DFS with direction array**:

   ```java
   private static final int[] dirs = {0, -1, 0, 1, 0};
   ```

   * Avoids writing 4 DFS calls, iterates directions cleanly.

### Complexity__

* Still worst-case exponential, but with **strong pruning**.
* In practice, runs much faster on large boards and long words.

---

## Example Walkthrough with Optimization

```java
board = [
  [A, B],
  [C, D]
]
word = "ABCD"

Step 1: Count letters → all exist.  
Step 2: Compare freq of A vs D → maybe reverse.  
Step 3: Start DFS → fails since adjacency broken.  
Return false.
```

---

## Conclusion

* **Naive backtracking** → correct, simpler, but may TLE on big inputs.
* **Optimized version** → adds smart pruning (frequency + reversing + compact DFS).
* Both are acceptable in interview; if time is tight, go with the simple one. If interviewer hints at optimization → mention frequency pruning.

👉 Optimal doesn’t mean faster in worst-case (still exponential), but **pruning** makes a huge difference in real test cases.

---
