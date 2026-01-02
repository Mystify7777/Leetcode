# How_Why.md: To Lower Case

## Problem

Given a string `s`, return the string converted to lowercase.

**Example:**

```shell
Input: s = "Hello"
Output: "hello"

Input: s = "LOVELY"
Output: "lovely"
```

---

## Approach: Character-by-Character Conversion

### Idea

* Iterate through each character in the string
* For uppercase letters (A-Z), convert them to lowercase
* Leave all other characters unchanged
* Use **ASCII arithmetic:** Uppercase letters (65-90) + 32 = Lowercase (97-122)

### Code

```java
class Solution {
    public String toLowerCase(String string) {
        char[] array = new char[string.length()];
        
        int index = 0;
        for (char ch : string.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                // Convert uppercase to lowercase by adding 32
                array[index++] = (char) ((ch - 'A') + 'a');
            } else {
                // Keep other characters as-is
                array[index++] = ch;
            }
        }
        
        return new String(array);
    }
}
```

### Why This Works

* **ASCII Values:**
  - 'A' = 65, 'Z' = 90 (uppercase letters)
  - 'a' = 97, 'z' = 122 (lowercase letters)
  - Difference: 97 - 65 = 32

* **Conversion Formula:**
  - Uppercase to Lowercase: `ch - 'A' + 'a'`
  - This is equivalent to: `ch + 32`

* **Example:** 'H' = 72
  - 'H' - 'A' = 72 - 65 = 7
  - 7 + 'a' = 7 + 97 = 104 = 'h' ✓

* Time Complexity: **O(n)** - single pass through string
* Space Complexity: **O(n)** - output array

---

## Alternative Approach 1: Using Character Addition

### Code*

```java
class Solution {
    public String toLowerCase(String str) {
        char[] result = str.toCharArray();
        
        for (int i = 0; i < result.length; i++) {
            if (result[i] >= 'A' && result[i] <= 'Z') {
                result[i] = (char) (result[i] + 32);
            }
        }
        
        return new String(result);
    }
}
```

**Difference:** Directly adds 32 instead of computing `ch - 'A' + 'a'`

---

## Alternative Approach 2: Using Built-in Method

### Code**

```java
class Solution {
    public String toLowerCase(String str) {
        return str.toLowerCase();
    }
}
```

**Pros:**

- Simplest and cleanest code
- Handles Unicode properly
- Built-in optimization

**Cons:**

- Doesn't demonstrate manual character conversion
- Less educational for interviews

---

## Alternative Approach 3: StringBuilder

### Code

```java
class Solution {
    public String toLowerCase(String str) {
        StringBuilder sb = new StringBuilder();
        
        for (char ch : str.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                sb.append((char) (ch + 32));
            } else {
                sb.append(ch);
            }
        }
        
        return sb.toString();
    }
}
```

---

## Comparison

| Approach | Time | Space | Notes |
| ---------- | ------ | ------- | ------- |
| Char Array | O(n) | O(n) | Direct, efficient |
| Built-in | O(n) | O(n) | Simplest |
| StringBuilder | O(n) | O(n) | Good for frequent modifications |

---

## Why This Approach

* **Efficient:** Single pass, minimal overhead
* **Educational:** Demonstrates ASCII/character arithmetic
* **Practical:** Works for interview questions
* **No Dependencies:** Doesn't rely on built-in methods (good for learning)
