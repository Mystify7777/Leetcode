# How_Why.md — Random Flip Matrix (LeetCode #519)

## 🧩 Problem Statement

You’re given a matrix with dimensions `m x n`, initially filled with **0**.  
You need to **flip** one random cell to **1** each time `flip()` is called, ensuring **no cell is flipped twice** until `reset()` is called.

After a reset, all cells return to 0, and the process restarts.

---

## ⚙️ Brute-Force Approach

### Idea

- Store all available positions (as a list of `[row, col]` pairs).
- On each `flip()`, randomly pick one position, mark it as flipped, and remove it from the list.
- On `reset()`, refill the list.

### Example Code (Conceptual)

```java
class Solution {
    List<int[]> cells = new ArrayList<>();
    Random rand = new Random();
    int m, n;

    public Solution(int m, int n) {
        this.m = m;
        this.n = n;
        reset();
    }

    public int[] flip() {
        int idx = rand.nextInt(cells.size());
        int[] res = cells.remove(idx);
        return res;
    }

    public void reset() {
        cells.clear();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cells.add(new int[] {i, j});
    }
}
```

---

### ⚠️ Limitations

* **Time Complexity:**

  * `flip()` → `O(n)` (list removal is linear)
  * `reset()` → `O(m * n)`
* **Space Complexity:** `O(m * n)`
* Very **inefficient** for large matrices (up to 10⁸ elements).

---

## 💡 Optimized Approach — HashMap + Randomized Index Mapping

### Core Idea

Instead of keeping track of all remaining positions directly,
treat the matrix as a **flattened 1D array of size m×n**.

Each index corresponds to:

```java
index = row * n + col
```

Use a **HashMap** to simulate the removal of elements without physically resizing any list.

---

### Step-by-Step Logic

1. **Initial Setup**

   * Total cells = `m * n`
   * Initially, all indices from `0` to `total-1` are unflipped.

2. **Flip Operation**

   * Randomly choose an integer `r` in `[0, total)`.
   * Use `map` to track previously swapped indices.
   * “Remove” the chosen index by swapping it with the **last available index (`total - 1`)**.
   * Decrease `total`.

   ```java
   int r = random.nextInt(total);
   int x = map.getOrDefault(r, r);            // actual index
   total--;
   map.put(r, map.getOrDefault(total, total)); // swap logic
   ```

3. **Reset Operation**

   * Clear the map.
   * Reset `total = m * n`.

---

### Example Walkthrough

#### Suppose

```java
m = 2, n = 3 → 2x3 matrix (6 cells)
Indexes: 0, 1, 2, 3, 4, 5
```

#### Step 1 — flip()

* Randomly pick `r = 4`
* Return cell `(4 / 3, 4 % 3) = (1, 1)`
* Map: `{4 -> 5}`
* Total becomes 5.

#### Step 2 — flip() again

* Suppose `r = 2`
* Return `(2 / 3, 2 % 3) = (0, 2)`
* Map: `{4 -> 5, 2 -> 4}`
* Total becomes 4.

---

### Your Code

```java
class Solution {
    HashMap<Integer, Integer> map;
    int row, col;
    Random ran;
    int total;

    public Solution(int m, int n) {
        map = new HashMap<>();
        ran = new Random();
        row = m;
        col = n;
        total = row * col;
    }

    public int[] flip() {
        int r = ran.nextInt(total);
        int x = map.getOrDefault(r, r);
        total--;
        map.put(r, map.getOrDefault(total, total));
        return new int[] { x / col, x % col };
    }

    public void reset() {
        map.clear();
        total = row * col;
    }
}
```

---

## 🧮 Complexity Analysis

| Operation       | Time Complexity     | Space Complexity            | Explanation                            |
| :-------------- | :------------------ | :-------------------------- | :------------------------------------- |
| **flip()**      | `O(1)`              | `O(k)`                      | Each flip inserts one entry into `map` |
| **reset()**     | `O(1)`              | `O(1)` (after clear)        | Resets the state instantly             |
| **Total Space** | `O(m*n)` worst-case | Only when all cells flipped |                                        |

---

## 🧠 Example Trace

For `m=3`, `n=1`:

| Step | total | random r | chosen index | map after flip    | Returned Cell |
| ---- | ----- | -------- | ------------ | ----------------- | ------------- |
| 1    | 3     | 2        | 2            | `{2→2}`           | (2,0)         |
| 2    | 2     | 1        | 1            | `{2→2, 1→1}`      | (1,0)         |
| 3    | 1     | 0        | 0            | `{2→2, 1→1, 0→0}` | (0,0)         |

Then `reset()` clears all.

---

## ✅ Summary

| Method                   | Approach                          | Time   | Space    | Remarks                      |
| :----------------------- | :-------------------------------- | :----- | :------- | :--------------------------- |
| Brute-force              | Store & remove coordinates        | `O(n)` | `O(m*n)` | Simple but slow              |
| Optimized (your version) | Random index mapping with HashMap | `O(1)` | `O(k)`   | Elegant, scalable, efficient |

---

### 🔹 Final Verdict

Your **HashMap + Random Index Swap** solution is the most efficient possible design for this problem.
It achieves **constant-time flips**, avoids memory-heavy structures, and resets in **O(1)** — perfect for large matrices.

---
