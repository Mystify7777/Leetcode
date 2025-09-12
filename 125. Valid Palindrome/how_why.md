# How & Why: LeetCode 125 - Valid Palindrome

-----

## Problem Restatement

You are given a string `s`. The goal is to determine if it is a **palindrome**. A string is a palindrome if it reads the same forwards and backward.

For this problem, you must first:

1.  Convert all uppercase letters to lowercase.
2.  Remove all non-alphanumeric characters (punctuation, spaces, symbols).

-----

## How to Solve

The provided solution uses a straightforward, two-step approach:

1.  **Sanitize the String**: First, prepare the string for the palindrome check. The code uses `toLowerCase()` to handle case-insensitivity and `replaceAll("[^a-z0-9]", "")` to filter out everything that isn't a letter or a number.
2.  **Two-Pointer Check**: After cleaning the string, it uses the classic two-pointer technique. A `left` pointer starts at the beginning and a `right` pointer starts at the end. The pointers move towards each other, comparing characters at each step. If a mismatch is found, it's not a palindrome.

### Implementation

```java
class Solution {
    public boolean isPalindrome(String s) {
        // 1. Sanitize the string
        s = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        // 2. Use two pointers to check for palindrome property
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false; // Mismatch found
            }
            left++;
            right--;
        }
        return true; // Loop completed without mismatches      
    }
}
```

-----

## Why This Works

1.  **Simplification**: By pre-processing the string, the core logic becomes incredibly simple. The `while` loop only has to worry about comparing characters, as all filtering and case conversion has already been handled.
2.  **Symmetrical Comparison**: A palindrome is defined by its symmetry. The two-pointer approach is the most direct way to verify this property. It checks pairs of characters from the outside-in, and a single failed check is enough to disprove the palindrome property, allowing for an early exit (`return false`).
3.  **Efficiency**: The check itself only requires a single pass (or half a pass, technically) over the cleaned string, making it very efficient.

-----

## Complexity Analysis

  - **Time Complexity**: $O(n)$, where `n` is the length of the original string. The sanitization step (lowercase conversion and regex replacement) takes linear time, and the subsequent two-pointer scan also takes linear time.
  - **Space Complexity**: $O(n)$. This is an important detail. The `toLowerCase()` and `replaceAll()` methods in Java create a **new string**. Therefore, the space required is proportional to the length of the input string, which does not meet a strict in-place requirement.

-----

## Example Walkthrough

**Input:**

```
s = "A man, a plan, a canal: Panama"
```

**Process:**

1.  **Sanitize**: The string is converted to lowercase and non-alphanumeric characters are removed.
      - `s` becomes `"amanaplanacanalpanama"`.
2.  **Two-Pointer Check**:
      - **Initial**: `left = 0` ('a'), `right = 20` ('a'). They match.
      - `left` becomes 1, `right` becomes 19.
      - **Next**: `left = 1` ('m'), `right = 19` ('m'). They match.
      - ... this continues ...
      - **End**: The pointers eventually meet in the middle. The `while` loop condition `left < right` becomes false.
3.  Since the loop finished without returning `false`, the function returns `true`.

**Output:**

```
true
```

-----

## Alternate Approaches

1.  **In-Place Two-Pointer (Optimal) ✨**:
      - This is the preferred method in an interview setting as it uses $O(1)$ space.
      - Initialize `left` and `right` pointers on the **original string**.
      - In the `while` loop, use nested loops to advance `left` and `right` inward, skipping any non-alphanumeric characters.
      - Once both pointers are on valid characters, compare their lowercase versions. If they don't match, return `false`.
      - This avoids creating a new string, making it more memory-efficient.

### Optimal Choice

The **In-Place Two-Pointer** approach is technically superior because it achieves the result with $O(1)$ extra space. The provided solution is simpler to write but less optimal in terms of memory usage.

-----

## Key Insight

This problem is about recognizing that you can separate the "data cleaning" phase from the "algorithmic check." The core logic is a simple palindrome test, which is wrapped in a layer of string sanitization. The most elegant solution combines these two steps to optimize for space.