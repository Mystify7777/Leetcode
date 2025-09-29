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

#### Steps

1. Consider sub-polygons of length 3 → direct triangles.
2. Expand to length 4, 5, … until full polygon.
3. Use recurrence to compute min triangulation.

### Output

```c
13
```

Visit [Example.md](.\Example.md) for complete demonstration.

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
