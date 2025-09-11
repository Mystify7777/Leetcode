# How & Why: LeetCode 2785 - Sort Vowels in a String

---

## Problem Restatement
You are given a string `s`. The task is to sort all the **vowels** in the string in **ascending order**, while leaving consonants and non-alphabetic characters in their original position.

Return the new string.

---

## How to Solve

### Step 1: Identify Vowels
We define vowels as:
```
A, E, I, O, U, a, e, i, o, u
```

These are the only characters we will sort, all others remain in place.

### Step 2: Count Frequency of Characters
We create a frequency table (`stringCharCount`) for all ASCII characters:
```java
int[] stringCharCount = new int[128];
for (char ch : s.toCharArray()) {
    stringCharCount[ch]++;
}
```

This allows us to know how many times each vowel appears.

### Step 3: Mark Vowels Present
We also track whether a character is a vowel:
```java
boolean[] isVowels = new boolean[128];
for (char v : vowels) {
    if (stringCharCount[v] > 0) isVowels[v] = true;
}
```

### Step 4: Place Sorted Vowels Back
We iterate through vowels in lexicographical order (`A, E, I, O, U, a, e, i, o, u`) and rebuild the string:
1. Keep a pointer `left` scanning the original string.
2. Whenever we hit a vowel position, we replace it with the next available vowel from sorted order.
3. We reduce the frequency count as we place vowels.

```java
int left = 0;
for (char v : vowels) {
    while (stringCharCount[v] > 0) {
        char ch = sChars[left];
        stringCharCount[v] -= isVowels[ch] ? 1 : 0;
        sChars[left] = isVowels[ch] ? v : ch;
        left++;
    }
}
```

Finally, return the rebuilt string.

---

## Why This Works
1. **Sorting by Frequency Table**: Instead of extracting and sorting vowels separately, we use the frequency table to process vowels directly in sorted order.
2. **In-Place Rebuild**: We traverse the string and only replace vowel positions, preserving consonants.
3. **Efficiency**: Since we only loop through the string and the fixed vowel list, the solution avoids expensive sorting operations.

---

## Complexity Analysis
- **Time Complexity**: O(n), where `n` = length of the string. Each character is processed a constant number of times.
- **Space Complexity**: O(1), since we use fixed-size arrays (128 for ASCII).

---

## Example Walkthrough
Input:
```
s = "leetcode"
```

1. Extract vowels: `e, e, o, e`.
2. Sort them: `e, e, e, o`.
3. Place back into string:
   - Original: `l e e t c o d e`
   - After replacement: `l e e t c e d o`

Output:
```
"leetcedo"
```

---

## Key Insight
The main idea is to **separate vowels from consonants conceptually** and place them back in sorted order using a frequency-based approach. This ensures O(n) efficiency without needing additional data structures like priority queues or lists.

