
# How & Why: LeetCode 383 - Ransom Note

This solution efficiently determines if a ransom note can be constructed by using a frequency map (an array) to track the available characters from a magazine.

---

## Problem Restatement

You are given two strings, `ransomNote` and `magazine`. Your goal is to determine if the `ransomNote` can be constructed by using the letters from the `magazine`.

The key constraint is that each letter from the magazine can only be used once in your ransom note.

### Example

**Input:**
```
ransomNote = "aa", magazine = "aab"
```
**Output:**
```
true (You can take two 'a's from the magazine)
```

**Input:**
```
ransomNote = "aa", magazine = "ab"
```
**Output:**
```
false (The magazine only has one 'a')
```

---

## How to Solve

The most effective way to solve this is to count the available characters and then check if we have enough for the note. This is a classic "inventory" problem.

1. **Create an Inventory:** Create a frequency map (an integer array of size 26 is perfect for lowercase English letters) to count all the characters available in the magazine.
2. **First Pass (Stocking):** Iterate through the magazine string. For each character, increment its corresponding count in the frequency map. This tells us exactly how many of each letter we have to work with.
3. **Second Pass (Spending):** Iterate through the ransomNote string. For each character needed for the note, decrement its count in the frequency map.
4. **Check for Shortage:** After decrementing, immediately check if the count for that character has dropped below zero. If it has, it means we tried to use a letter that we didn't have enough of in our inventory. In this case, construction is impossible, and we can immediately return false.
5. **Success:** If you get through the entire ransomNote without any character count ever dropping below zero, it means you had enough of every required letter. You can successfully construct the note, so you return true.

### Implementation

```java
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        // Frequency map for 'a' through 'z' acts as our inventory
        int[] count = new int[26];

        // 1. Stock the inventory by counting letters in the magazine
        for (char c : magazine.toCharArray()) {
            count[c - 'a']++;
        }

        // 2. "Spend" letters to create the ransom note
        for (char c : ransomNote.toCharArray()) {
            count[c - 'a']--;

            // 3. Check if we ran out of a required letter
            if (count[c - 'a'] < 0) {
                return false; // Not enough of this character
            }
        }

        // If we get here, we had enough of every letter
        return true;
    }
}
```

---

## Why This Works

- **Resource Management:** The solution perfectly models a resource problem. The magazine provides the resources (letters), and the ransomNote represents the demand. The frequency map is the inventory system that tracks supply.
- **Efficiency:** By pre-counting all the letters in the magazine, the check for each letter in the ransomNote becomes an extremely fast $O(1)$ operation (a simple array lookup and decrement).
- **Early Exit:** The `if (count < 0)` check is crucial. It allows the function to stop and return false the moment it discovers an impossible requirement, without wasting time checking the rest of the ransomNote.

---

## Complexity Analysis

- **Time Complexity:** $O(m+n)$, where $m$ is the length of magazine and $n$ is the length of ransomNote. We perform one pass over the magazine and one pass over the ransom note.
- **Space Complexity:** $O(1)$. The count array has a fixed size of 26, which is constant and does not depend on the input string lengths.

---

## Example Walkthrough

**Input:**
```
ransomNote = "aab", magazine = "baa"
```

**Process:**

- Stocking Pass (magazine="baa"):
    - After this loop, the count array will be: `count['a'] = 2`, `count['b'] = 1`.
- Spending Pass (ransomNote="aab"):
    - char = 'a': Decrement `count['a']`. It becomes 1. 1 is not less than 0. Continue.
    - char = 'a': Decrement `count['a']`. It becomes 0. 0 is not less than 0. Continue.
    - char = 'b': Decrement `count['b']`. It becomes 0. 0 is not less than 0. Continue.
- The loop finishes without ever finding a negative count.

**Output:**
```
Return true.
```

---

## Alternate Approaches

### 1. Using a HashMap
   - **How:** A `HashMap<Character, Integer>` can be used instead of an array. The logic is identical: populate the map with counts from the magazine, then decrement counts for characters from the ransomNote.
   - **Complexity:** Same time and space complexity. An array is slightly more performant for a fixed, small character set like the lowercase alphabet due to less overhead.

---

## Optimal Choice

The frequency array approach is optimal. It's the most direct and performant way to handle character counting for a known and limited character set (a-z).

---

## Key Insight

This problem can be simplified to "Do we have enough of every character we need?". The most effective way to answer this is to first count everything you have (magazine) and then subtract everything you need (ransomNote), checking for a deficit at each step.