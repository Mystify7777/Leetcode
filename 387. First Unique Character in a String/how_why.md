# How\_Why.md: First Unique Character in a String

## Problem

Given a string `s`, return the **index of the first non-repeating character**.
Return `-1` if every character repeats.

**Example:**

```java
Input: s = "leetcode"
Output: 0  // 'l' is the first unique character
```

---

## Brute-force Approach

### Idea

* For each character, check if it occurs only once in the string.
* Can use `indexOf()` and `lastIndexOf()`:

  * If `indexOf(c) == lastIndexOf(c)` → character appears only once

### Example Walkthrough

* Input: `"loveleetcode"`
* Iterate `'a'` to `'z'`:

  1. `'a'`: not in string → skip
  2. `'c'`: indexOf=4, lastIndexOf=4 → unique → ans = min(ans, 4)
  3. `'l'`: indexOf=0, lastIndexOf=0 → unique → ans = min(4,0) → ans=0

### Limitation

* **Time complexity:** O(26 × n) → O(n) but with large constants
* Uses **string scanning repeatedly** for each character → inefficient for long strings

---

## Better Approach (Two-pass Array)

### Idea_

* Count frequency of all characters in a first pass using an array of size 26.
* In a second pass, iterate through string to find the **first character with count = 1**.

### Steps

1. Convert string to `char[]` for fast access.
2. Initialize `int[26]` array for counting letters.
3. First pass: count occurrences.
4. Second pass: return index of first character with count = 1.
5. If none found, return -1.

### Code

```java
class Solution {
    public int firstUniqChar(String s) {
        char[] let = s.toCharArray();
        int[] seen = new int[26];

        // Count each character
        for(int i = 0; i < let.length; i++)
            seen[let[i] - 'a']++;

        // Find first unique
        for(int i = 0; i < let.length; i++){
            if(seen[let[i] - 'a'] == 1)
                return i;
        }

        return -1;
    }
}
```

### Example Walkthrough_

* Input: `"loveleetcode"`

  1. Count array after first pass:

     ```java
     l:2, o:2, v:1, e:4, t:1, c:1, d:1
     ```

  2. Second pass:

     * `i=0`: 'l' → count=2 → skip
     * `i=1`: 'o' → count=2 → skip
     * `i=2`: 'v' → count=1 → return 2 ✅

### Advantages

* **Time complexity:** O(n) — two passes only
* **Space complexity:** O(26) → O(1)
* Simple and efficient for large strings

---
