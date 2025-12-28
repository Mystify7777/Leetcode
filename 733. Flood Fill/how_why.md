# @Mystify7777

## How & Why: LeetCode 733 — Flood Fill

Recursive DFS repaints all connected pixels (4-directional) that share the starting pixel’s original color. A base check avoids infinite recursion when the new color matches the original.

---

### Problem

Given an image grid `image` and a starting pixel `(sr, sc)`, repaint that pixel and all 4-directionally connected pixels with the same original color to a new `color`. Return the modified image.

---

### Key Idea

Run DFS from `(sr, sc)`, only traversing cells that are in-bounds and still equal to the starting color. Repaint as you go. If the new color equals the starting color, skip work to avoid infinite loops.

---

### Algorithm (DFS)

- Let `orig = image[sr][sc]`; if `orig == color`, return `image`.
- Define `dfs(r, c)`:
  - If out of bounds, return.
  - If `image[r][c] != orig`, return.
  - Set `image[r][c] = color`.
  - Recurse to `(r-1, c)`, `(r+1, c)`, `(r, c-1)`, `(r, c+1)`.
- Call `dfs(sr, sc)` and return `image`.

---

### Implementation (Java)

```java
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    if (image[sr][sc] == color) return image; // avoid infinite loop
        fill(image, sr, sc, color, image[sr][sc]);
        return image;
    }

    private void fill(int[][] image, int r, int c, int color, int orig) {
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) return;
        if (image[r][c] != orig) return;
        image[r][c] = color;
        fill(image, r - 1, c, color, orig);
        fill(image, r + 1, c, color, orig);
        fill(image, r, c - 1, color, orig);
        fill(image, r, c + 1, color, orig);
    }
}
```

---

### Complexity

- **Time**: O(m·n) worst case (visit each cell once).
- **Space**: O(m·n) worst case recursion stack (all cells same color); O(min(m,n)) average for narrower components.

---

### Pitfalls

- Forgetting the early return when `newColor == orig` leads to infinite recursion.
- Missing bounds checks before access.
- Using 8-directional neighbors instead of the required 4-directional.

---

### Alternatives

- **BFS** with a queue instead of recursion to avoid deep call stacks; logic is identical for neighbor checks and recoloring.
