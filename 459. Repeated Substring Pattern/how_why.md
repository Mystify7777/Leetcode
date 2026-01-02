# How_Why.md: Repeated Substring Pattern

## Problem

Given a string `s`, check if it can be constructed by repeating a pattern string. In other words, check if there exists a substring `p` such that `s = p + p + ... + p` (some number of times).

**Example:**

```java
Input: s = "abab"
Output: true
Explanation: s = "ab" repeated 2 times

Input: s = "aba"
Output: false

Input: s = "abcabcabcabc"
Output: true
Explanation: s = "abc" repeated 4 times
```

---

## Approach 1: Pattern Checking (Your Solution)

### Idea

* The repeating pattern must have length that **divides the total length**
* Try all divisors of the length in **descending order** (longest patterns first)
* For each potential pattern length, check if that pattern repeated forms the original string

### Code

```java
class Solution {
    public boolean repeatedSubstringPattern(String str) {
        int len = str.length();
        
        // Try all possible pattern lengths from len/2 down to 1
        for (int i = len / 2; i >= 1; i--) {
            // Pattern length must divide total length
            if (len % i == 0) {
                int m = len / i;  // Number of repetitions
                String subS = str.substring(0, i);  // Candidate pattern
                
                // Check if pattern repeated m times equals original string
                int j;
                for (j = 1; j < m; j++) {
                    if (!subS.equals(str.substring(j * i, i + j * i))) {
                        break;
                    }
                }
                
                // If we completed all comparisons, we found the pattern
                if (j == m) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
```

### Why This Works

* **Key Insight:** If length `n` has a repeating pattern of length `p`, then `p` must be a **divisor of n**

* **Example:** s = "abab" (length 4)
  * Try pattern length 2: "ab" × 2 = "abab" ✓
  * Return true

* **Example:** s = "abcabcabcabc" (length 12)
  * Try pattern length 6: fails
  * Try pattern length 4: fails
  * Try pattern length 3: "abc" × 4 = "abcabcabcabc" ✓
  * Return true

* Time Complexity: **O(n²)** worst case (checking all divisors and substring comparisons)
* Space Complexity: **O(n)** for substring storage

---

## Approach 2: String Concatenation (Elegant Alternative)

### Idea*

* If `s` is formed by repeating pattern `p`, then `s` will always appear in `(s + s)` after removing the first and last character

* **Why?** If we concatenate the string with itself and remove boundaries, the middle contains at least one full copy of the pattern

### Code*

```java
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).substring(1, s.length() * 2 - 1).contains(s);
    }
}
```

### Example

* s = "abab"
* (s + s) = "abababab"
* Remove first and last: "bababa"
* "bababa" contains "abab"? Yes! ✓

* s = "aba"
* (s + s) = "abaaba"
* Remove first and last: "baab"
* "baab" contains "aba"? No! ✗

### Comparison

| Approach | Pros | Cons |
|---------- | ------ | ------ |
| Pattern Checking | Explicit, easy to understand | O(n²) time |
| Concatenation | Elegant, O(n) average case | Less intuitive, relies on string operations |

---

## Approach 3: StringBuilder (Clean Middle Ground)

### Code**

```java
class Solution {
    public boolean repeatedSubstringPattern(String str) {
        int len = str.length();
        for (int i = len / 2; i >= 1; i--) {
            if (len % i == 0) {
                int m = len / i;
                String subS = str.substring(0, i);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < m; j++) {
                    sb.append(subS);
                }
                if (sb.toString().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

### Why This Approach

* **Balance:** Between clarity and efficiency
* **Readable:** Easy to follow the logic
* **Practical:** Good performance for typical inputs
