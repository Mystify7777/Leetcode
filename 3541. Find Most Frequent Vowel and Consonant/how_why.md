# How & Why: LeetCode 3541 - Find Most Frequent Vowel and Consonant

This solution finds the sum of the highest vowel and consonant frequencies in a single pass by using a frequency array and tracking the maximums as it iterates.

---

## Problem Restatement

Given a string `s` containing only lowercase English letters, your task is to find two values:

1. The frequency of the most frequent vowel (`a`, `e`, `i`, `o`, `u`).
2. The frequency of the most frequent consonant.

Finally, return the sum of these two frequencies.

---

## How to Solve

The problem can be solved efficiently in a single pass using a frequency-counting method:

1. **Frequency Map:** Create an integer array of size 26 to act as a frequency map for each letter of the alphabet.
2. **Tracking Variables:** Initialize two variables, `maxVowel` and `maxConso`, to zero. These will keep a running record of the highest frequencies seen for vowels and consonants, respectively.
3. **Iterate and Count:** Loop through each character of the input string:
  - Increment the count for that character in your frequency map.
  - Check if the character is a vowel or a consonant.
  - If it's a vowel, update `maxVowel` if the character's new count is greater than the current `maxVowel`.
  - If it's a consonant, do the same for `maxConso`.
4. **Return the Sum:** After the loop finishes, simply return `maxVowel + maxConso`.

### Implementation

```java
class Solution {
  public int maxFreqSum(String s) {
    // Frequency map for 'a' through 'z'
    int[] freq = new int[26];
    // Trackers for the max frequencies
    int maxVowel = 0, maxConso = 0;

    for (char c : s.toCharArray()) {
      // Get the 0-25 index for the character
      int i = c - 'a';
      freq[i]++;

      // Check if it's a vowel
      if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
        // Update max vowel frequency if needed
        maxVowel = Math.max(maxVowel, freq[i]);
      } else {
        // Otherwise, it's a consonant. Update max consonant frequency.
        maxConso = Math.max(maxConso, freq[i]);
      }
    }
    // Return the sum of the two maximums
    return maxVowel + maxConso;
  }
}
```

---

## Why This Works

1. **Efficiency:** The single-pass approach is optimal because it gathers all the required information in one go. There's no need to iterate through the string multiple times.
2. **Real-Time Updates:** By updating the `maxVowel` and `maxConso` variables inside the loop, immediately after a character's count changes, the algorithm ensures these trackers are always up-to-date. By the time the loop finishes, they are guaranteed to hold the highest frequencies found anywhere in the string.
3. **Constant Space:** Using an array of size 26 for the frequency map is highly effective. Since the alphabet size is fixed, the memory usage doesn't grow with the input string's length, making it a constant-space solution.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$, where $n$ is the length of the string $s$. We perform a single pass through the string.
- **Space Complexity:** $O(1)$. The `freq` array's size is fixed at 26, which is constant and does not depend on the input size.

---

## Example Walkthrough

**Input:** `s = "abbaceei"`

**Process:**

- Initial: `freq = [0,...,0]`, `maxVowel = 0`, `maxConso = 0`.
- `c = 'a'`: `freq['a']` becomes 1. It's a vowel. `maxVowel` becomes 1.
- `c = 'b'`: `freq['b']` becomes 1. It's a consonant. `maxConso` becomes 1.
- `c = 'b'`: `freq['b']` becomes 2. It's a consonant. `maxConso` becomes 2.
- `c = 'a'`: `freq['a']` becomes 2. It's a vowel. `maxVowel` becomes 2.
- `c = 'c'`: `freq['c']` becomes 1. It's a consonant. `maxConso` remains 2.
- `c = 'e'`: `freq['e']` becomes 1. It's a vowel. `maxVowel` remains 2.
- `c = 'e'`: `freq['e']` becomes 2. It's a vowel. `maxVowel` remains 2.
- `c = 'i'`: `freq['i']` becomes 1. It's a vowel. `maxVowel` remains 2.
- Loop finishes. The most frequent vowel ('a' or 'e') appeared 2 times. The most frequent consonant ('b') appeared 2 times.

**Output:**
Return `maxVowel + maxConso = 2 + 2 = 4`.

---

## Alternate Approaches

### 1. Two-Pass Method
   - **How:** In the first pass, populate the entire frequency map. In a second pass, iterate through the 26 letters of the alphabet, check if each is a vowel or consonant, and find the maximum frequencies from the now-complete map.
   - **Complexity:** Still $O(n)$ time and $O(1)$ space. The performance is virtually identical, but the single-pass solution is slightly more streamlined.

---

## Optimal Choice

The single-pass solution is optimal. It's clean, efficient, and solves the problem with the best possible time and space complexity without any unnecessary steps.

---

## Key Insight

The main takeaway is that you don't need to separate the counting process from the "finding the maximum" process. Both can be done simultaneously. By keeping a running tally of the maximums as you build your frequency map, you can solve the problem in a single, efficient iteration.