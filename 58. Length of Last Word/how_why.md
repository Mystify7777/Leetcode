
# How & Why: LeetCode 58 - Length of Last Word

This solution efficiently finds the length of the last word by scanning the string backward, which avoids unnecessary processing of the beginning of the string.

---

## Problem Restatement

You are given a string `s` that consists of words separated by spaces. Your task is to find and return the length of the last word in the string. A "word" is defined as a maximal sequence of non-space characters.

### Example

**Input:**
```
s = "Hello World"
```
**Output:**
```
5 (The last word is "World")
```

**Input:**
```
s = "   fly me   to   the moon  "
```
**Output:**
```
4 (The last word is "moon")
```

---

## How to Solve

The most direct and memory-efficient way to solve this is to scan the string from right to left. This approach immediately hones in on the last word without needing to process the rest of the string first.

1. **Find the End of the Last Word:** Start a pointer at the very end of the string. Move this pointer to the left, skipping any trailing spaces. When you hit the first non-space character, you've found the end of the last word.
2. **Find the Start of the Last Word:** Now that you know where the last word ends, continue moving another pointer to the left from that position. Keep moving as long as you are on a non-space character.
3. **Count the Characters:** When this second pointer stops (either by hitting a space or the beginning of the string), you have found the start and end boundaries of the last word. The difference between the end and start positions is its length.

### Implementation

```java
class Solution {
    public int lengthOfLastWord(String s) {
        // 1. Start from the end of the string
        int end = s.length() - 1;

        // 2. Skip all trailing spaces to find the last character of the last word
        while (end >= 0 && s.charAt(end) == ' ') {
            end--;
        }

        // 3. Mark this position as the start of our counting
        int start = end;

        // 4. Move left until we find a space or the beginning of the string
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }

        // 5. The length is the difference between the end and start pointers
        return end - start;
    }
}
```

---

## Why This Works

- **Efficiency:** By starting from the end, the algorithm's work is proportional to the length of the last word plus any trailing spaces. For a long string with a short last word, this is much faster than processing from the beginning.
- **Correct Boundary Detection:** The first loop correctly handles all cases of trailing spaces, ensuring the end pointer is accurately positioned on the final character of the last word. The second loop then correctly finds the boundary just before the start of that word.
- **In-Place Calculation:** The solution uses only two integer variables (`start`, `end`) to keep track of positions. It does not create any new strings or data structures, making it extremely memory-efficient with $O(1)$ space.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$, where $n$ is the length of the string. In the worst-case scenario (a string with no spaces or only leading spaces), the algorithm has to scan the entire string.
- **Space Complexity:** $O(1)$. No extra space proportional to the input size is used.

---

## Example Walkthrough

**Input:**
```
s = "Hello World  "
```

**Process:**

- Initial: `end` starts at index 12 (the last space).
- First Loop (Skip Trailing Spaces):
    - `s.charAt(12)` is ' '. `end` becomes 11.
    - `s.charAt(11)` is ' '. `end` becomes 10.
    - `s.charAt(10)` is 'd'. This is not a space. The loop stops. `end` is now 10.
- Find Start: `start` is set to `end` (10).
- Second Loop (Count Word Characters):
    - `start` is 10 ('d'). Not a space. `start` becomes 9.
    - `start` is 9 ('l'). Not a space. `start` becomes 8.
    - `start` is 8 ('r'). Not a space. `start` becomes 7.
    - `start` is 7 ('o'). Not a space. `start` becomes 6.
    - `start` is 6 ('W'). Not a space. `start` becomes 5.
    - `start` is 5 (' '). This is a space. The loop stops. `start` is now 5.
- Calculate Length: The result is `end - start = 10 - 5 = 5`.

**Output:**
```
Return 5.
```

---

## Alternate Approaches

### 1. Built-in String Functions
   - **How:** One could use `s.trim()` to remove leading/trailing spaces, then `s.split(" ")` to create an array of words, and finally get the length of the last element in that array.
   - **Complexity:** This is often less efficient. `trim()` and `split()` create new strings and a new array, leading to a space complexity of $O(n)$ and potentially slower performance due to the overhead of these operations.

---

## Optimal Choice

The manual backward scan is the optimal solution in an interview context because it demonstrates an understanding of string manipulation and adheres to the best possible space complexity of $O(1)$.

---

## Key Insight

The key to this problem is realizing that since you only care about the last word, starting your search from the end of the string is the most logical and direct approach. It simplifies the logic by letting you find the word first, then its beginning, without needing to iterate over irrelevant data.