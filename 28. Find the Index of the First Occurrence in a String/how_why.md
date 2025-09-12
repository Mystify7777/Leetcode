# How & Why: LeetCode 28 - Find the Index of the First Occurrence in a String

-----

This solution finds a substring by sliding a window, the size of the "needle," across the "haystack" and checking for a match at each position.

-----

## Problem Restatement

Given two strings, a `haystack` and a `needle`, your goal is to find the starting index of the **first time** `needle` appears in `haystack`. If it's not found, return `-1`.

-----

## How to Solve

The approach is a straightforward **sliding window**. 🪟

Think of it like using a magnifying glass that's the exact width of your `needle`. You place this glass at the very beginning of the `haystack`.

1.  **Check:** Is the text under the glass the same as the `needle`?
2.  **Match:** If yes, you've found it\! The starting position of the glass is your answer.
3.  **Slide:** If no, slide the glass one character to the right and check again.
4.  **Finish:** If you reach the end of the `haystack` without a match, the `needle` isn't there.

### Implementation

```java
class Solution {
    public int strStr(String haystack, String needle) {
        // The window's start is 'i' and end is 'j'
        for (int i = 0, j = needle.length(); j <= haystack.length(); i++, j++) {
            
            // Get the text inside the current window
            String sub = haystack.substring(i, j);

            // If the window's text matches the needle, we're done!
            if (sub.equals(needle)) {
                return i;
            }
        }
        
        // If the loop finishes, no match was found
        return -1;
    }
}
```

-----

## Why This Works

This method works because it's an **exhaustive, left-to-right search**. It systematically checks every single possible starting point in the `haystack`. Since it starts from the beginning (`i=0`), the very first match it finds is guaranteed to be the first occurrence, satisfying the problem's requirement.

-----

## Complexity Analysis

  * **Time Complexity**: $O(n \\cdot m)$
    This is because for each of the `n` possible starting spots in the `haystack`, the `.equals()` method might have to compare up to `m` characters in the worst case. (`n` is `haystack.length()`, `m` is `needle.length()`).
  * **Space Complexity**: $O(m)$
    In each step of the loop, `haystack.substring(i, j)` typically creates a new temporary string of length `m`, which uses extra memory.

-----

## Example Walkthrough

**Input:**

```
haystack = "sadbutsad", needle = "sad"
```

**Process:**

1.  **Window 1 (index 0):** The substring is `"sad"`. Does it match the `needle` `"sad"`? **Yes**.
2.  A match is found at the window's starting index, `i = 0`.
3.  Return `0`. The search stops immediately.

-----

## Alternate Approaches

While the sliding window is easy to grasp, it's not the fastest.

1.  **Knuth-Morris-Pratt (KMP) ✨**: A famous, highly efficient algorithm that preprocesses the `needle` to understand its own structure. This allows it to intelligently skip ahead after a mismatch, avoiding redundant checks. Its time complexity is an optimal $O(n + m)$.
2.  **Rabin-Karp**: This method uses hashing. It calculates a hash value for the `needle` and for each window in the `haystack`. If the hashes match, it performs a full comparison to be certain. It's also very fast on average, with a time complexity of $O(n + m)$.

### Optimal Choice

For an interview, the **sliding window** is a perfectly fine first answer. However, the **KMP algorithm** is the classic, optimal solution known for its guaranteed linear-time performance.

-----

## Key Insight

The core idea of substring searching is that a simple brute-force check works but is slow. Advanced algorithms gain a massive speed boost by being "smart" about how they shift the search pattern after a partial mismatch, using information from the `needle` itself to avoid re-checking things they already know.