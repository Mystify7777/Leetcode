# How_Why.md — Design Add and Search Words Data Structure (LeetCode 211)

---

## ❌ Brute Force Approach

### **Idea**

Store all words in a list and, when searching:

* Compare each stored word with the query word.
* For `'.'`, allow it to match any character.

### **Example**

If words = `["bad", "dad", "mad"]` and query = `"b.."`:

* Compare `"b.."` with `"bad"` → match ✅
* Compare `"b.."` with `"dad"` → mismatch ❌
* Compare `"b.."` with `"mad"` → mismatch ❌

### **Why It’s Bad**

* For each search, we must compare against all stored words.
* Matching each word character by character takes O(L) time (L = length of word).
* Total = **O(N × L)** per query — very slow when words grow large.

---

## ✅ Optimized Approach — Trie (Prefix Tree)

### **Core Idea**

Use a **Trie (prefix tree)** for:

* Fast word insertion.
* Flexible pattern search (handles `'.'` wildcard efficiently).

---

### **How It Works**

#### 1️⃣ Add Word

* Each node represents one character.
* Each node has 26 children (for 'a'–'z').
* Traverse character by character:

  * Create child nodes if missing.
  * Mark the final node as `end of word`.

#### 2️⃣ Search Word

* Traverse the Trie recursively.
* When encountering:

  * **Normal character (a–z)** → move to corresponding child.
  * **Dot (`'.'`)** → try **all non-null children** recursively.

---

### **Example Walkthrough**

#### Input:

```java
addWord("bad");
addWord("dad");
addWord("mad");
search("pad"); // false
search("bad"); // true
search(".ad"); // true
search("b.."); // true
```

#### Step-by-step (Trie view):

```
root
 ├─ b ─ a ─ d (end)
 ├─ d ─ a ─ d (end)
 └─ m ─ a ─ d (end)
```

#### When searching `"b.."`:

* `'b'` → moves to `'b'` branch.
* `'.'` → tries all children of `'a'`.
* `'.'` again → matches `'d'`. ✅

---

### **Code**

```java
class WordDictionary {
    private WordDictionary[] children;
    boolean isEndOfWord;

    public WordDictionary() {
        children = new WordDictionary[26];
        isEndOfWord = false;
    }

    public void addWord(String word) {
        WordDictionary curr = this;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null)
                curr.children[c - 'a'] = new WordDictionary();
            curr = curr.children[c - 'a'];
        }
        curr.isEndOfWord = true;
    }

    public boolean search(String word) {
        WordDictionary curr = this;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (c == '.') {
                for (WordDictionary child : curr.children)
                    if (child != null && child.search(word.substring(i + 1)))
                        return true;
                return false;
            }
            if (curr.children[c - 'a'] == null)
                return false;
            curr = curr.children[c - 'a'];
        }
        return curr != null && curr.isEndOfWord;
    }
}
```

---

### **Complexity Analysis**

| Operation          | Time Complexity    | Explanation                        |
| ------------------ | ------------------ | ---------------------------------- |
| Add Word           | O(L)               | One traversal per word             |
| Search (no dots)   | O(L)               | Straight path lookup               |
| Search (with dots) | O(26^d) worst-case | `d` = number of `'.'` wildcards    |
| Space              | O(N × L)           | N = words, L = average word length |

---

### **Key Insights**

* **Trie** makes prefix-based or pattern-based lookups efficient.
* `'.'` is handled naturally by recursive branching.
* Using `WordDictionary[]` instead of `HashMap<Character, Node>` improves speed and memory efficiency for lowercase letters.
* Elegant recursive handling makes wildcard search simple yet powerful.

---
ww