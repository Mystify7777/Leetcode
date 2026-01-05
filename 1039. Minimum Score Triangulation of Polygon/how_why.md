# How_Why.md – Minimum Score Triangulation of Polygon (1039)

## ✅ Problem Recap

We’re given a convex polygon with vertices labeled `values[i]`.
We want to triangulate it (divide into triangles) such that the **sum of triangle scores** is minimized.

**Triangle Score**
If a triangle is formed by vertices `(i, j, k)` → score = `values[i] * values[j] * values[k]`.

---

## Brute Force (Recursive Enumeration)

* Try every way to pick a "third vertex" `k` between `(i, j)`.
* Recursively compute cost of triangulating `(i, k)` and `(k, j)`.
* Take minimum across all partitions.

### Pseudocode

```java
int minScore(int[] values, int i, int j) {
    if (j - i < 2) return 0; // less than 2 edges → no triangle
    
    int ans = Integer.MAX_VALUE;
    for (int k = i+1; k < j; k++) {
        int cost = values[i]*values[k]*values[j]
                 + minScore(values, i, k)
                 + minScore(values, k, j);
        ans = Math.min(ans, cost);
    }
    return ans;
}
```

* Very **slow** (exponential time).
* Many overlapping subproblems.

---

## Recursive + Memoization (Top-Down DP)

We add memoization with `dp[i][j] = min score between i and j`.

### Code

```java
int[][] dp;
int helper(int[] values, int i, int j) {
    if (j - i < 2) return 0;
    if (dp[i][j] != 0) return dp[i][j];

    int min = Integer.MAX_VALUE;
    for (int k = i+1; k < j; k++) {
        int cost = values[i]*values[k]*values[j]
                 + helper(values, i, k)
                 + helper(values, k, j);
        min = Math.min(min, cost);
    }
    return dp[i][j] = min;
}
```

* **Time Complexity:** O(n³)
* **Space Complexity:** O(n²)
* Works for `n ≤ 50`.

---

## Iterative DP (Bottom-Up)

We fill a `dp[i][j]` table where each entry means:
**minimum triangulation cost of polygon vertices from i to j.**

### Code (your first version)

```java
for (int i = n-1; i >= 0; --i) {
    for (int j = i+1; j < n; ++j) {
        for (int k = i+1; k < j; ++k) {
            dp[i][j] = Math.min(
                dp[i][j] == 0 ? Integer.MAX_VALUE : dp[i][j],
                dp[i][k] + values[i]*values[k]*values[j] + dp[k][j]
            );
        }
    }
}
```

* Build table iteratively.
* Final answer is `dp[0][n-1]`.

---

## Example Walkthrough

### Input

```c
values = [1,3,1,4,1,5]
```

### Key DP fills (bottom-up)

- Length 3 (base triangles):
  - dp[0][2] = 1*3*1 = 3
  - dp[1][3] = 3*1*4 = 12
  - dp[2][4] = 1*4*1 = 4
  - dp[3][5] = 4*1*5 = 20
- Length 4:
  - dp[0][3] = min(
    k=1: 1*3*4 + 0 + 12 = 24,
    k=2: 1*1*4 + 3 + 0 = 7
    ) = 7
  - dp[1][4] = min(
    k=2: 3*1*1 + 0 + 4 = 7,
    k=3: 3*4*1 + 12 + 0 = 24
    ) = 7
  - dp[2][5] = min(
    k=3: 1*4*5 + 0 + 20 = 40,
    k=4: 1*1*5 + 4 + 0 = 9
    ) = 9
- Length 5:
  - dp[0][4] = min(
    k=1: 1*3*1 + 0 + 4 = 7,
    k=2: 1*1*1 + 3 + 4 = 8,
    k=3: 1*4*1 + 7 + 0 = 11
    ) = 7
  - dp[1][5] = min(
    k=2: 3*1*5 + 0 + 20 = 35,
    k=3: 3*4*5 + 12 + 0 = 72,
    k=4: 3*1*5 + 7 + 0 = 22
    ) = 22
- Length 6 (full polygon):
  - dp[0][5] = min(
    k=1: 1*3*5 + 0 + 22 = 37,
    k=2: 1*1*5 + 3 + 9  = 17,
    k=3: 1*4*5 + 7 + 0  = 27,
    k=4: 1*1*5 + 7 + 20 = 32
    ) = 17

Result: **17** is the minimum triangulation score for this polygon (matching the DP recurrence on this input).

---

## Complexity

| Method            | Time   | Space |
| ----------------- | ------ | ----- |
| Brute Force       | O(3^n) | O(1)  |
| Rec + Memoization | O(n³)  | O(n²) |
| Iterative DP      | O(n³)  | O(n²) |

---

✅ **Best choice:** Iterative DP (`O(n³)`), since `n ≤ 50`.
Recursive + memoization is easier to write but same complexity.

---

## Related Problems

- [120. Triangle/how_why.md](120.%20Triangle/how_why.md)
- [91. Decode Ways/how_why.md](91.%20Decode%20Ways/how_why.md)
- [139. Word Break/how_why.md](139.%20Word%20Break/how_why.md)
- [140. Word Break II/how_why.md](140.%20Word%20Break%20II/how_why.md)

---
