# How_Why.md — Maximal Rectangle (LeetCode #85)

## 🧩 Problem Statement

Given a 2D binary matrix filled with `'0'`s and `'1'`s, find the **largest rectangle containing only '1's**, and return its area.

---

## ⚙️ Brute-Force Approach

### 💡 Idea

Treat every cell as the potential **bottom-right corner** of a rectangle.  
For each `'1'`, expand **leftward** and **upward** to determine the maximum area possible.

### 🧠 Logic

- Iterate through each cell `(i, j)`.
- If it’s `'1'`, then:
  - Look backward (leftward) in the row.
  - Track the **minimum height** of columns within that range.
  - Compute area as `(width * minHeight)` for all possible widths ending at `j`.

### 🧩 Example

For:

```java

matrix = [
['1','0','1','1'],
['1','1','1','1']
]

```

- Row 0:
  - Heights = [1, 0, 1, 1]
  - Max area = 2
- Row 1:
  - Heights = [2, 1, 2, 2]
  - Max area = 4 (rectangle of size 2×2)

✅ Final Answer = 4

---

### ⏱️ Time Complexity

For every cell, we look back across its row → **O(m × n²)**  
Space: **O(n)** (height array only)

---

## ⚡ Optimized Approach — “Histogram per Row” Method

### 💡 Intuition

Each row can be visualized as the **base of a histogram**,  
where column heights represent **consecutive '1's** up to that row.

Then, the problem reduces to finding the **largest rectangle in a histogram** for each row —  
just like **LeetCode #84 — Largest Rectangle in Histogram**.

---

### 🔍 Core Steps

1. Maintain arrays:
   - `height[j]` → height of column `j` (consecutive '1's so far)
   - `left[j]` → leftmost boundary of rectangle including column `j`
   - `right[j]` → rightmost boundary of rectangle including column `j`

2. For each row:
   - **Update heights** — if `matrix[i][j] == '1'` → increment height; else reset to 0.
   - **Update left boundaries** — track how far left a rectangle can extend.
   - **Update right boundaries** — track how far right a rectangle can extend.
   - Compute area for each column:

     ```java
     area = height[j] * (right[j] - left[j])
     ```

---

### 🧩 Example Walkthrough

#### Input

```java

matrix = [
['1','0','1','0','0'],
['1','0','1','1','1'],
['1','1','1','1','1'],
['1','0','0','1','0']
]

```

#### Step-by-Step

| Row | Heights (h) | Max Rectangle Area |
|-----|--------------|--------------------|
| 0 | [1,0,1,0,0] | 1 |
| 1 | [2,0,2,1,1] | 3 |
| 2 | [3,1,3,2,2] | 6 |
| 3 | [4,0,0,3,0] | 6 |

✅ Final Answer = **6**

---

### 🧮 Complexity Analysis

| Operation | Time | Space | Description |
|------------|------|-------|-------------|
| Height Update | O(n) per row | O(n) | Tracks heights |
| Boundary Updates | O(n) per row | O(n) | For left/right |
| Total | **O(m × n)** | **O(n)** | Very efficient |

---

## 🔧 Your Submitted Code (Simplified Variant)

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int ans = 0, m = matrix.length, n = matrix[0].length;
        int[] height = new int[n]; // histogram heights

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    height[j] = 0;
                    continue;
                }
                height[j]++;
                for (int cur = j - 1, pre = height[j]; cur >= 0; cur--) {
                    if (height[cur] == 0) break;
                    pre = Math.min(pre, height[cur]);
                    ans = Math.max(ans, (j - cur + 1) * pre);
                }
                ans = Math.max(ans, height[j]);
            }
        }
        return ans;
    }
}
````

### 🧩 How It Works

* Maintains a running `height` array.
* For each `'1'`, it:

  * Expands leftward (`cur = j-1`) to compute max possible rectangle width.
  * Takes `pre = min(pre, height[cur])` → ensures we only count consecutive 1s vertically.
  * Updates `ans` with `area = width * minHeight`.

---

## ✅ Summary

| Method       | Idea                                              | Time         | Space    | Comment               |
| :----------- | :------------------------------------------------ | :----------- | :------- | :-------------------- |
| Brute-force  | For each cell, expand leftward/upward             | O(m × n²)    | O(n)     | Simple but slow       |
| Your version | Incremental height scan with local left expansion | O(m × n²)    | O(n)     | Compact but quadratic |
| Optimized    | Histogram + Stack or boundaries                   | **O(m × n)** | **O(n)** | Fastest possible      |

---

### 🔹 Final Verdict

Your version elegantly balances clarity and correctness —
though not as fast as the histogram-stack approach, it’s clean and performs well for moderate matrix sizes.

✅ **Result:** Works correctly, well-structured, and easy to visualize.

---
