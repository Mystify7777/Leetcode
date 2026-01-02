# How_Why.md: Permutation in String

## Problem

Given two strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`, or `false` otherwise.

In other words, return `true` if one of `s1`'s permutations is a substring of `s2`.

**Example:**

```shell
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains "ba" which is a permutation of "ab"

Input: s1 = "ab", s2 = "ab"
Output: true

Input: s1 = "ab", s2 = "a"
Output: false
```

---

## Approach: Sliding Window with HashMap

### Idea

* A **permutation** of `s1` has the same character frequencies as `s1`, just in a different order
* Use a **sliding window** of size `|s1|` on `s2`
* For each window, check if the character frequency matches `s1`'s frequency

### Code

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        HashMap<Character, Integer> s1Count = new HashMap<>();
        HashMap<Character, Integer> s2Count = new HashMap<>();
        
        // Count characters in s1 and initial window of s2
        for (int i = 0; i < s1.length(); i++) {
            s1Count.put(s1.charAt(i), s1Count.getOrDefault(s1.charAt(i), 0) + 1);
            s2Count.put(s2.charAt(i), s2Count.getOrDefault(s2.charAt(i), 0) + 1);
        }
        
        // Check initial window
        if (s1Count.equals(s2Count)) {
            return true;
        }
        
        // Slide the window across s2
        int left = 0;
        for (int right = s1.length(); right < s2.length(); right++) {
            // Add new character to the right
            char charRight = s2.charAt(right);
            s2Count.put(charRight, s2Count.getOrDefault(charRight, 0) + 1);
            
            // Remove character from the left
            char charLeft = s2.charAt(left);
            s2Count.put(charLeft, s2Count.get(charLeft) - 1);
            if (s2Count.get(charLeft) == 0) {
                s2Count.remove(charLeft);
            }
            
            left++;
            
            // Check if current window matches
            if (s1Count.equals(s2Count)) {
                return true;
            }
        }
        
        return false;
    }
}
```

### Why This Works

* **Permutation Check:** Two strings are permutations if they have identical character frequencies

* **Sliding Window:** Maintains a window of exactly `|s1|` characters
  - Add one character to the right
  - Remove one character from the left
  - Keep the window size constant

* **Efficiency:** We update counts incrementally instead of recalculating for each window

* **Example:** s1 = "ab", s2 = "eidbaooo"

  ```java
  s1Count = {'a': 1, 'b': 1}
  
  Window "ei": {'e': 1, 'i': 1} ❌
  Window "id": {'i': 1, 'd': 1} ❌
  Window "db": {'d': 1, 'b': 1} ❌
  Window "ba": {'b': 1, 'a': 1} ✓ Found!
  ```

* Time Complexity: **O(|s2|)** - single pass through s2
* Space Complexity: **O(1)** - at most 26 lowercase letters

---

## Alternative Approach: Array of Frequencies

### Idea*

Use an array instead of HashMap for O(1) character counting

### Code*

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        if (n > m) return false;
        
        int[] s1Count = new int[26];
        int[] s2Count = new int[26];
        
        for (int i = 0; i < n; i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }
        
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] != s2Count[i]) return false;
        }
        
        int left = 0;
        for (int right = n; right < m; right++) {
            s2Count[s2.charAt(right) - 'a']++;
            s2Count[s2.charAt(left) - 'a']--;
            left++;
            
            boolean match = true;
            for (int i = 0; i < 26; i++) {
                if (s1Count[i] != s2Count[i]) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        
        return false;
    }
}
```

### Comparison

| Approach | Pros | Cons |
| ---------- | ------ | ------ |
| HashMap | Cleaner code, handles Unicode | Slightly slower for small alphabets |
| Array | Faster for fixed-size alphabet | Assumes lowercase letters only |

---

## Why This Approach

* **Optimal:** Linear time, constant space (for fixed alphabet)
* **Intuitive:** Sliding window is a classic pattern
* **Practical:** Works well for real-world strings
