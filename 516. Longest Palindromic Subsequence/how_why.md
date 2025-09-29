# How_Why.md – Longest Palindromic Subsequence (LeetCode 516)

## ❌ Brute Force Idea

The most straightforward idea is recursion:

* Check both ends of the string.
* If `s[left] == s[right]`, then include them and recurse inward.
* Otherwise, try both `(left+1, right)` and `(left, right-1)` and take the max.

**Why it’s bad:**

* This explores *all subsequences* (exponential time, ~O(2^n)).
* Will cause TLE for input lengths up to 1000.

---

## ✅ Your Approach (Bottom-Up Dynamic Programming)

You used **2D DP** (`dp[i][j]`) where:

* `dp[i][j]` = length of longest palindromic subsequence in substring `s[i..j]`.

Steps:

1. Fill diagonals (`dp[i][i] = 1`) since single characters are palindromes.
2. Expand window length from 2 → n:

   * If `s[i] == s[j]` → `dp[i][j] = 2 + dp[i+1][j-1]`.
   * Else → `dp[i][j] = max(dp[i+1][j], dp[i][j-1])`.
3. Final answer = `dp[0][n-1]`.

**Time Complexity:** O(n²)
**Space Complexity:** O(n²)

---

## 🚀 Other Optimized Approaches

1. **Top-Down DP (Memoization)**

   * Recursively explore substrings with caching.
   * Same complexity but easier to write.

2. **LCS Trick (Longest Common Subsequence)**

   * Longest palindromic subsequence in `s` = **LCS(s, reverse(s))**.
   * Time Complexity: O(n²), Space: O(n²).
   * Very intuitive if you already know LCS DP.

3. **Space Optimization**

   * Since only previous row/column is needed, space can be reduced from O(n²) → O(n).

---

## 🔎 Example Walkthrough

Input:

```java
s = "bbbab"
```

### Step 1 – Initialization

`dp[i][i] = 1`
So dp table diag = `[1,1,1,1,1]`.

### Step 2 – Substrings length 2+

* For `"bb"` → match → `dp[0][1] = 2`.
* For `"bbb"` → match outer b’s → `dp[0][2] = 3`.
* For `"bbba"` → last chars differ → `dp[0][3] = max(dp[1][3], dp[0][2]) = 3`.
* For `"bbbab"` → outer b’s match → `dp[0][4] = 2 + dp[1][3] = 4`.

### Step 3 – Answer

Final `dp[0][n-1] = 4`.
Longest palindromic subsequence = `"bbbb"`.

---

## ✅ Key Takeaways

* Brute force recursion is **exponential**.
* DP (bottom-up or top-down) brings it to **O(n²)**, which is optimal.
* Alternate LCS formulation is a neat trick for interviews.

---
