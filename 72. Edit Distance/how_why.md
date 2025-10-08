# How_Why.md — Edit Distance (LeetCode #72)

## 🧩 Problem Statement

Given two strings `word1` and `word2`, return the **minimum number of operations** required to convert `word1` into `word2`.  
You are allowed to perform **three operations**:

1. **Insert** a character  
2. **Delete** a character  
3. **Replace** a character

---

## ⚙️ Brute-Force Method

### Idea:
Use recursion to explore all possible edit sequences between `word1` and `word2`.

For each mismatch between characters, try all three possible operations:
- **Insert** → move in `word2`
- **Delete** → move in `word1`
- **Replace** → move both forward

### Code (Conceptual):
```java
int editDistance(String s1, String s2, int i, int j) {
    if (i == s1.length()) return s2.length() - j; // insert remaining
    if (j == s2.length()) return s1.length() - i; // delete remaining

    if (s1.charAt(i) == s2.charAt(j))
        return editDistance(s1, s2, i + 1, j + 1);

    // try replace, delete, insert
    int replace = editDistance(s1, s2, i + 1, j + 1);
    int delete = editDistance(s1, s2, i + 1, j);
    int insert = editDistance(s1, s2, i, j + 1);

    return 1 + Math.min(replace, Math.min(delete, insert));
}
```

### ⚠️ Limitations

* **Time Complexity:** `O(3^(m+n))` → exponential.
* **Overlapping subproblems** lead to massive recomputation.
* Quickly TLEs for medium/large strings.

---

## 💡 Your Approach — Bottom-Up Dynamic Programming (Tabulation)

### Core Idea

Use **Dynamic Programming (DP)** to store intermediate edit distances for all substrings of `word1` and `word2`.

We build a DP table `dp[i][j]`:

> `dp[i][j]` = minimum operations to convert `word1[0..i)` → `word2[0..j)`

---

### Step-by-Step Logic

1. **Initialization**

   * `dp[i][0] = i` → delete all characters from `word1`
   * `dp[0][j] = j` → insert all characters into `word1`

2. **Transition**
   For every pair `(i, j)`:

   * If characters match:
     `dp[i][j] = dp[i-1][j-1]`
   * Otherwise:

     ```java
     dp[i][j] = 1 + min(
         dp[i-1][j-1], // replace
         dp[i-1][j],   // delete
         dp[i][j-1]    // insert
     )
     ```

3. **Final Answer**
   → `dp[m][n]` gives the minimum edit distance.

---

### Code (Your Version)

```java
class Solution {
  public int minDistance(String word1, String word2) {
    final int m = word1.length();
    final int n = word2.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; ++i)
      dp[i][0] = i;

    for (int j = 1; j <= n; ++j)
      dp[0][j] = j;

    for (int i = 1; i <= m; ++i)
      for (int j = 1; j <= n; ++j)
        if (word1.charAt(i - 1) == word2.charAt(j - 1))
          dp[i][j] = dp[i - 1][j - 1];
        else
          dp[i][j] = Math.min(
              dp[i - 1][j - 1],
              Math.min(dp[i - 1][j], dp[i][j - 1])
          ) + 1;

    return dp[m][n];
  }
}
```

---

## 🧠 Example Walkthrough

### Input

```c
word1 = "horse"
word2 = "ros"
```

### DP Table Construction:-

| i\j |  "" |  r  |  o  |  s  |
| :-: | :-: | :-: | :-: | :-: |
|  "" |  0  |  1  |  2  |  3  |
|  h  |  1  |  1  |  2  |  3  |
|  o  |  2  |  2  |  1  |  2  |
|  r  |  3  |  2  |  2  |  2  |
|  s  |  4  |  3  |  3  |  2  |
|  e  |  5  |  4  |  4  |  3  |

✅ Minimum Edit Distance = **3**
(Operations: `horse → rorse → rose → ros`)

---

## 🧩 Alternate Version — Top-Down (Memoized Recursion)

```java
class Solution {
    int[][] dp;

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        return solve(word1, word2, m - 1, n - 1);
    }

    private int solve(String s1, String s2, int i, int j) {
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;
        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j))
            return dp[i][j] = solve(s1, s2, i - 1, j - 1);

        int insert = solve(s1, s2, i, j - 1);
        int delete = solve(s1, s2, i - 1, j);
        int replace = solve(s1, s2, i - 1, j - 1);

        return dp[i][j] = 1 + Math.min(replace, Math.min(insert, delete));
    }
}
```

---

## 🧮 Complexity Analysis

| Aspect              | Complexity | Explanation                               |
| :------------------ | :--------- | :---------------------------------------- |
| **Time**            | `O(m * n)` | Each subproblem computed once             |
| **Space**           | `O(m * n)` | DP table stores results for substrings    |
| **Optimized Space** | `O(n)`     | Possible using rolling array optimization |

---

## ✅ Summary

| Method                       | Approach       | Time         | Space    | Remarks                |
| :--------------------------- | :------------- | :----------- | :------- | :--------------------- |
| Brute-force                  | Pure recursion | `O(3^(m+n))` | `O(m+n)` | Exponential            |
| Top-Down DP                  | Memoization    | `O(m*n)`     | `O(m*n)` | Recursive, clear logic |
| Bottom-Up DP (your approach) | Tabulation     | `O(m*n)`     | `O(m*n)` | Clean & efficient      |

---

🔹 **Final Verdict:**
Your **bottom-up DP** solution is the standard optimal implementation for Edit Distance — concise, efficient, and guaranteed to run in polynomial time.

---
