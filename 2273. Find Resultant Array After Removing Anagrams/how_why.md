
# How_Why.md — Find Resultant Array After Removing Anagrams (LeetCode #2273)

## 🧩 Problem Statement

You are given a string array `words`.  
You need to **remove all adjacent anagrams** from the list.  

Return the array that remains after performing this operation repeatedly until no two adjacent words are anagrams.

### 🔹 Example

**Input:**

```java

words = ["abba", "baba", "bbaa", "cd", "cd"]

```

**Output:**

```java

["abba", "cd"]

```

**Explanation:**

- `"abba"`, `"baba"`, and `"bbaa"` are anagrams → keep only the first `"abba"`.
- `"cd"` and `"cd"` are identical → second `"cd"` removed.
- Final result: `["abba", "cd"]`.

---

## 🧠 Brute Force Approach

### 💡 Idea

Compare each word with the next one:

1. Check if they are anagrams (by sorting or using frequency maps).
2. If yes → remove the next one.
3. Repeat this process until no adjacent anagrams remain.

### ⚙️ Steps

- Start from the beginning.
- At each step:
  - If `sorted(words[i]) == sorted(words[i-1])`, skip `words[i]`.
  - Otherwise, keep it.
- Use a new list to store results.

### ⚠️ Why It’s Inefficient

If done naïvely with repeated list removals, time can go up to:

```java

O(n² * k log k)

```

(where `k` is average word length, because sorting happens every time).

---

## ⚡ Optimized Approach — One-Pass with Previous Sorted Tracker

### 💡 Key Insight

Instead of checking for all previous anagrams,  
we only need to compare **with the immediately previous** word.

If we keep track of the **sorted version of the last kept word**,  
we can decide in O(k log k) per step whether the current one is an anagram.

---

### 🧮 Example Walkthrough

```java

Input: ["abba", "baba", "bbaa", "cd", "cd"]

```

| Word | Sorted Form | Previous Sorted | Action | Result List |
|------|--------------|----------------|---------|--------------|
| "abba" | "aabb" | "" | Add | ["abba"] |
| "baba" | "aabb" | "aabb" | Skip | ["abba"] |
| "bbaa" | "aabb" | "aabb" | Skip | ["abba"] |
| "cd" | "cd" | "aabb" | Add | ["abba", "cd"] |
| "cd" | "cd" | "cd" | Skip | ["abba", "cd"] |

✅ Final Output: `["abba", "cd"]`

---

## 🧩 Final Code

```java
class Solution {
    public List<String> removeAnagrams(String[] words) {
        String prev = "";
        List<String> result = new ArrayList<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String curr = new String(chars);

            if (!curr.equals(prev)) {
                result.add(word);
                prev = curr;
            }
        }

        return result;
    }
}
```

---

## ⏱️ Complexity Analysis

| Operation                   | Time               | Space        | Explanation                        |
| --------------------------- | ------------------ | ------------ | ---------------------------------- |
| Sorting each word           | O(k log k)         | O(k)         | Sorting per word                   |
| Iterating through all words | O(n)               | O(1)         | Single pass                        |
| **Total**                   | **O(n × k log k)** | **O(n × k)** | Reasonable for typical constraints |

---

## 🔍 Why It Works

* Only adjacent anagrams matter.
* By keeping `prev` as the **sorted signature** of the last accepted word,
  we can easily detect if the next one is an anagram.
* Sorting ensures all anagrams map to the same canonical form (e.g. `"baba" → "aabb"`).
* Only unique adjacent sorted patterns are kept.

---

## ✅ Final Verdict

| Aspect          | Description                                        |
| --------------- | -------------------------------------------------- |
| 🔧 Approach     | One-pass comparison using sorted signatures        |
| 💡 Key Trick    | Track previous sorted version                      |
| ⏱️ Time         | O(n × k log k)                                     |
| 💾 Space        | O(n × k)                                           |
| ⚙️ Complexity   | Simple & efficient                                 |
| 🧙‍♂️ Core Idea | Reduce repeated work by caching previous signature |

✅ **Result:** Efficient removal of adjacent anagrams in a single pass.

---
