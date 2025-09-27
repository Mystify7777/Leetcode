# How & Why – 120. Triangle

## Problem

You are given a triangle (list of lists of integers). Starting from the top, move to adjacent numbers on the row below and find the minimum path sum from top to bottom.

---

## 1. Brute Force (Pure Recursion)

**Idea:**
At each element `(i, j)`:

* Move **down** to `(i+1, j)`
* Move **diagonal** to `(i+1, j+1)`
  Take the minimum of the two paths.

**Pseudocode:**

```java
int solve(i, j):
    if i == lastRow:
        return triangle[i][j]
    down = solve(i+1, j)
    diagonal = solve(i+1, j+1)
    return triangle[i][j] + min(down, diagonal)
```

**Walkthrough Example:**

```
Triangle = [
   [2],
   [3,4],
   [6,5,7],
   [4,1,8,3]
]

Paths:
2 → 3 → 5 → 1 = 11
2 → 3 → 5 → 8 = 18
2 → 3 → 6 → 4 = 15
2 → 4 → 5 → 1 = 12
...

Min = 11
```

**Complexity:**

* Time: O(2^n) (exponential, since every step branches).
* Space: O(n) (recursion depth).

---

## 2. Top-Down with Memoization (Recursive + DP)

**Idea:**
Same as brute force, but cache results in `dp[i][j]` to avoid recomputation.

**Code insight:**

```java
if (dp[i][j] != -1) return dp[i][j];
dp[i][j] = triangle[i][j] + min(solve(i+1, j), solve(i+1, j+1));
```

**Complexity:**

* Time: O(n²) (each state computed once).
* Space: O(n²) for DP + recursion stack.

---

## 3. Bottom-Up DP (2D array)

**Idea:**
Work from the second-last row upwards:

* For each cell `(r, c)`, update as:

```
triangle[r][c] = triangle[r][c] + min(triangle[r+1][c], triangle[r+1][c+1])
```

At the end, `triangle[0][0]` holds the minimum path sum.

**Complexity:**

* Time: O(n²)
* Space: O(1) (if modifying in place).

---

## 4. Optimized Bottom-Up DP (1D array)

**Your fastest code:**
Instead of keeping the whole triangle, use a 1D array (`memo`) initialized with the last row.
Then move upward, overwriting values with the min path cost.

**Example Walkthrough (triangle = same as above):**

```
Initial memo = [4,1,8,3]   // bottom row

Row 2: [6,5,7]
→ memo[0] = 6 + min(4,1) = 7
→ memo[1] = 5 + min(1,8) = 6
→ memo[2] = 7 + min(8,3) = 10
memo = [7,6,10,3]

Row 1: [3,4]
→ memo[0] = 3 + min(7,6) = 9
→ memo[1] = 4 + min(6,10) = 10
memo = [9,10,10,3]

Row 0: [2]
→ memo[0] = 2 + min(9,10) = 11
memo = [11,10,10,3]
```

Answer = `memo[0] = 11`.

**Complexity:**

* Time: O(n²)
* Space: O(n) (1D array only).

---

✅ **Final Takeaway:**

* Brute force is exponential.
* Top-down DP avoids recomputation but costs O(n²) space.
* Bottom-up DP is cleaner and efficient.
* The **1D rolling array** solution is optimal: **O(n²) time, O(n) space**.

---
