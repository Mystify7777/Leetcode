# How_Why.md – Word Pattern (LeetCode 290)

## ❌ Brute Force Idea

The problem:

* Given a `pattern` string (like `"abba"`) and a sentence `"dog cat cat dog"`.
* We must check if there’s a **one-to-one mapping** between characters in the pattern and words in the sentence.

Brute force attempt:

* Compare each character with corresponding word.
* Check all pairings against each other (nested loops).
* If any mismatch is found, return false.

Problems:

* O(n²) comparisons.
* Doesn’t elegantly enforce bijection (both directions).

---

## ✅ Better Approach (Two-Way Mapping)

Key observation:

* Each character in `pattern` should map to **exactly one word**.
* Each word should map to **exactly one character**.

So we need **bijection** (one-to-one and onto mapping).

### Implementation Strategy:

1. Split sentence into words. If counts mismatch → false.
2. Use a map `charToWord` for `pattern → word`.
3. Use a set `seenWords` to prevent multiple characters mapping to the same word.
4. Iterate:

   * If char already mapped → check consistency.
   * Else, check word not already used, then assign mapping.

---

### Java Implementation

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        
        if (pattern.length() != words.length) {
            return false;
        }

        HashMap<Character, String> charToWord = new HashMap<>();
        HashSet<String> seenWords = new HashSet<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];

            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(w)) {
                    return false;
                }
            } else {
                if (seenWords.contains(w)) {
                    return false; // word already mapped to another char
                }
                charToWord.put(c, w);
                seenWords.add(w);
            }
        }
        return true;
    }
}
```

---

### Example Walkthrough

Input:
`pattern = "abba", s = "dog cat cat dog"`

* Step 1: `a → dog`, seen = {dog}
* Step 2: `b → cat`, seen = {dog, cat}
* Step 3: `b` again → must match `cat` ✅
* Step 4: `a` again → must match `dog` ✅

Output → **true** ✅

---

Input:
`pattern = "abba", s = "dog cat cat fish"`

* Step 1: `a → dog`
* Step 2: `b → cat`
* Step 3: `b` again → matches `cat` ✅
* Step 4: `a` again → expected `dog`, but word is `fish` ❌

Output → **false**.

---

## 📊 Complexity

* **Time:** O(n), where n = number of words.
* **Space:** O(k), where k = distinct chars/words.

---

## ✅ Key Takeaways

* Always check **length mismatch** first.
* Maintain **two-way uniqueness** → char-to-word mapping + used-word check.
* Works like an **isomorphism check** between two sequences.

---
