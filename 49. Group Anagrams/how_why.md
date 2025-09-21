
# How\_Why.md — Problem 49: Group Anagrams

## Problem Restatement

We are given an array of strings `strs`. We need to **group the anagrams** together and return them as a list of lists.

* **An anagram**: two strings that contain the same characters with the same frequency but possibly in a different order.

Example:
Input: `["eat","tea","tan","ate","nat","bat"]`
Output: `[["eat","tea","ate"],["tan","nat"],["bat"]]`

---

## 1. Brute Force Approach

### Idea

Check every string against every other string to see if they are anagrams.

### Steps

1. Initialize an empty result list.
2. For each string:

   * Compare it with each group already formed.
   * To check if two words are anagrams: sort both strings and compare, OR count character frequencies.
   * If it matches, place it in that group; otherwise start a new group.

### Example Walkthrough

Input: `["bat","tab","eat"]`

* Compare `"bat"` with `"tab"` → both sorted = `"abt"` → same → group together.
* `"eat"` sorted = `"aet"` → new group.

Result: `[["bat","tab"],["eat"]]`

### Complexity

* **Sorting-based comparison:** $O(m \log m)$ per comparison, where `m` = word length.
* With nested comparisons, total is $O(n^2 \cdot m \log m)$.
* Very slow for large input.

**Limitation:** Too slow because every string is compared multiple times.

---

## 2. Sorting Key Approach (Your First Version)

### Idea_

Instead of comparing each string to others, create a **signature key** by sorting its characters. All anagrams share the same sorted string.

### Steps_

1. For each string, sort its characters.
2. Use the sorted string as the key in a HashMap.
3. Append the string to the list corresponding to that key.
4. Return all grouped lists.

### Example Walkthrough_

Input: `["eat","tea","tan","ate","nat","bat"]`

* `"eat"` → sorted `"aet"` → group `"aet":[eat]`
* `"tea"` → sorted `"aet"` → group `"aet":[eat,tea]`
* `"tan"` → sorted `"ant"` → group `"ant":[tan]`
* `"ate"` → sorted `"aet"` → group `"aet":[eat,tea,ate]`
* `"nat"` → sorted `"ant"` → group `"ant":[tan,nat]`
* `"bat"` → sorted `"abt"` → group `"abt":[bat]`

Result: `[["eat","tea","ate"],["tan","nat"],["bat"]]`

### Complexity_

* Sorting each string: $O(m \log m)$.
* Doing this for all `n` strings: $O(n \cdot m \log m)$.

**Limitation:** Sorting is unnecessary overhead.

---

## 3. Frequency-Count Key Approach (Your Current Code)

### Idea__

Instead of sorting, use **character frequency** as the signature.

* For each word, count occurrences of letters `a-z`.
* Convert the frequency array into a string key.
* Anagrams will have identical frequency signatures.

### Steps__

1. Initialize ```java
HashMap\<String, List<String>>```.
2. For each word:

   * Count its character frequencies into `int[26]`.
   * Convert the array into a unique string (like `"1#0#0#...#1#"`).
   * Use that as the map key.
   * Append the word to the correct list.
3. Return all lists.

### Example Walkthrough__

Input: `["bat","tab","eat"]`

* `"bat"` → freq: {a=1, b=1, t=1} → key `"1#1#0#...#1#"` → group → \[bat]
* `"tab"` → freq: same key → group → \[bat,tab]
* `"eat"` → freq: {a=1,e=1,t=1} → different key → group → \[eat]

Result: `[["bat","tab"],["eat"]]`

### Complexity__

* Counting characters: $O(m)$.
* Doing this for all `n` strings: $O(n \cdot m)$.
* Faster than sorting approach.

---

## Best Approach

✅ **Frequency-Count Key (Approach 3)** is best.

* Eliminates sorting overhead.
* Works in **linear time per string**.
* Scales better for large inputs.

**Final Complexity:**

* **Time:** $O(n \cdot m)$
* **Space:** $O(n \cdot m)$ (for storing keys and groups)

---
