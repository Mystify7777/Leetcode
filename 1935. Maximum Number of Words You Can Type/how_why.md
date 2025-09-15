# How & Why: LeetCode 1935 - Maximum Number of Words You Can Type

---

## Problem Restatement
We are given:
- A string `text` consisting of words separated by spaces.
- A string `brokenLetters`, where each character represents a broken key on the keyboard.

We need to determine how many words in `text` can still be typed without using any broken letters.

---

## How to Solve

### Step 1: Split Text into Words
We split the input string `text` into words:
```java
String[] words = text.split(" ");
```

### Step 2: Check Each Word
For each word:
- Assume it can be typed (`canType = true`).
- Check if it contains any character from `brokenLetters`.
- If it contains a broken letter, mark it as untypable (`canType = false`).

```java
for (String word : words) {
    boolean canType = true;
    for (char c : brokenLetters.toCharArray()) {
        if (word.indexOf(c) != -1) {
            canType = false;
            break;
        }
    }
    if (canType) count++;
}
```

### Step 3: Return Result
Finally, return the count of words that can still be typed.

---

## Why This Works
- Splitting ensures we process each word individually.
- Checking with `indexOf` finds whether a word contains any broken letter.
- By breaking early when a broken letter is found, we avoid unnecessary checks.

This method respects the definition of a typable word: **a word is valid only if none of its letters are broken**.

---

## Complexity Analysis
- **Time Complexity**: O(W × L × B), where:
  - W = number of words
  - L = average word length
  - B = number of broken letters

  Each word is checked against all broken letters.

- **Space Complexity**: O(W) due to splitting the text into words.

---

## Example Walkthrough
Input:
```
text = "hello world"
brokenLetters = "ad"
```

Process:
- Word "hello": no `a` or `d` → count++.
- Word "world": contains no `a` or `d` → count++.

Output:
```
2
```

---

## Alternate Approaches

1. **Use a HashSet for Broken Letters**
   - Convert `brokenLetters` into a `Set<Character>` for O(1) lookups.
   - Then check each character in the word directly instead of calling `indexOf` repeatedly.
   - Improves performance when `brokenLetters` is large.

2. **Bitmasking Optimization**
   - Represent broken letters as a bitmask (26-bit integer).
   - For each word, check if any character overlaps with broken mask.
   - Faster for large-scale inputs.

### Optimal Choice
The **HashSet-based approach** is usually optimal:
- Simpler than bitmasking.
- Reduces redundant scanning from `indexOf`.
- Gives near O(W × L) performance.

---

## Key Insight
The problem boils down to checking **set disjointness** between a word’s characters and the broken letters. If they overlap, the word cannot be typed; if not, it is valid.

