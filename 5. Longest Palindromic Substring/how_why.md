# How_Why.md

## Problem

Given a string `s`, return the **longest palindromic substring** in `s`.  
A palindrome is a string that reads the same forward and backward.

---

## How (Step-by-step Solution)

### Approach: Expand Around Center

1. **Iterate through each character** in the string:
   - Treat it as the **center of a palindrome**.
   - Palindromes can be:
     - **Odd-length**: expand around a single center (e.g., `"aba"`).
     - **Even-length**: expand around two adjacent characters (e.g., `"abba"`).
2. For each index `i`:
   - Expand around `(i, i)` for odd length.
   - Expand around `(i, i+1)` for even length.
3. Keep track of the **longest palindrome found** during expansions.
4. Return the longest substring at the end.

---

## Why (Reasoning)

- A brute force method would check all substrings (O(n³)), which is too slow.
- Expanding around centers avoids redundant checks:
  - There are `2n - 1` possible centers (`n` odd + `n-1` even).
  - Each expansion takes O(n) in the worst case.
- Overall complexity reduces to **O(n²)**, which is acceptable for this problem.

---

## Complexity Analysis

- **Time Complexity**: O(n²) → each expansion may traverse the whole string.  
- **Space Complexity**: O(1) → only a few variables are used.

---

## Example Walkthrough

### Input

`s = "babad"`

### Step-by-step

- i = 0 → expand around `'b'` → "bab" (odd)  
- i = 1 → expand around `'a'` → "aba" (odd)  
- i = 2 → expand around `'b'` → "bab" again  
- i = 3 → expand around `'a'` → "ada" (odd)  
- i = 4 → expand around `'d'` → "d"  

### Output

`"bab" or "aba" (both valid)`

---

## Alternate Approaches

1. **Dynamic Programming**
   - Use a DP table `dp[i][j]` to check if substring `s[i...j]` is a palindrome.
   - Time: O(n²), Space: O(n²).
   - More memory-heavy than expand-around-center.

2. **Manacher’s Algorithm**
   - Solves in O(n) time.
   - Complex to implement, less intuitive.
   - Usually unnecessary unless strict time limits.

3. ✅ **Optimal for interviews** → Expand Around Center (clean + efficient).
