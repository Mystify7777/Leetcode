
# How\_Why.md: Longest Substring Without Repeating Characters

## Problem

Given a string `s`, find the length of the **longest substring without repeating characters**.

**Example:**

```java
Input: s = "abcabcbb"
Output: 3
Explanation: The longest substring without repeating characters is "abc".
```

---

## Brute-force Approach

### Idea

* Check **all substrings** of `s` and determine if each has all unique characters.
* Keep track of the **maximum length** found.

### Code

```java
public int lengthOfLongestSubstringBF(String s) {
    int maxLen = 0;
    for (int i = 0; i < s.length(); i++) {
        Set<Character> set = new HashSet<>();
        int length = 0;
        for (int j = i; j < s.length(); j++) {
            if (set.contains(s.charAt(j))) break;
            set.add(s.charAt(j));
            length++;
        }
        maxLen = Math.max(maxLen, length);
    }
    return maxLen;
}
```

### Example Walkthrough

* Input: `"abcabcbb"`
* Substrings checked:

  * `"a"` → valid, maxLen = 1
  * `"ab"` → valid, maxLen = 2
  * `"abc"` → valid, maxLen = 3
  * `"abca"` → repeats `'a'` → stop
* Returns `3`

### Limitation

* **Time complexity:** O(n²)
* Inefficient for long strings (`n` up to 10⁵).

---

## Sliding Window Approach (Optimized)

### Idea_

* Use **two pointers** (`left` and `right`) to maintain a window of unique characters.
* Expand `right` to include new characters.
* Move `left` forward whenever a duplicate is found.
* Track the **maximum window size**.

### Code_

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left));
            left++;
        }
        set.add(s.charAt(right));
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### Example Walkthrough_

* Input: `"abcabcbb"`
* `left=0, right=0` → add `'a'` → window: `"a"`, maxLen=1
* `right=1` → add `'b'` → window: `"ab"`, maxLen=2
* `right=2` → add `'c'` → window: `"abc"`, maxLen=3
* `right=3` → `'a'` exists → remove `'a'`, move left=1 → window: `"bca"`, maxLen still 3
* Continue → final maxLen = 3

### Advantages

* **Time complexity:** O(n) → each character is added/removed at most once
* **Space complexity:** O(min(n, charset size)) → HashSet stores current window characters

---

## Key Takeaways

1. **Brute-force:** simple, easy to implement but O(n²) → too slow for large strings.
2. **Sliding window:** linear time, optimal for this problem.
3. Maintaining a **HashSet** ensures uniqueness, and moving the left pointer removes duplicates efficiently.

---
