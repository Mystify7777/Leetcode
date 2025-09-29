# 212. Word Search II — How & Why

## Problem Restated
We are given a 2D board of characters and a list of words.  
We need to find **all words** from the list that can be formed in the board by connecting adjacent characters (up, down, left, right). Each cell can only be used once per word.

**Example:**

```java

Board =
[o a a n]
[e t a e]
[i h k r]
[i f l v]

Words = ["oath","pea","eat","rain"]

Output = ["oath","eat"]

```

---

## 1. Brute Force Approach

**Idea**: For each word in the list:

- Start DFS/BFS from every cell in the board.
- Try to match the word letter by letter.

**Example (word = "oath")**:

- Start at `o(0,0)`.
- Explore neighbors → `a(0,1)` → `t(1,1)` → `h(2,1)` → full match.

**Complexity**:

- For each word (W), check every cell (MN), exploring up to length L.
- Time = **O(W · M · N · L)**
- Space = **O(L)** recursion depth.

**Limitations**:

- Very slow if `words` is large, since each search restarts from scratch.

---

## 2. Optimized Trie + DFS (Your Solution)

**Key Insight**:  
Instead of searching for each word independently, we **build a Trie** (prefix tree) of all words, then perform a DFS search **once per cell**, pruning invalid paths early.

**Steps**:

1. **Build Trie** from all words.  
   - Each path represents a prefix, and nodes can store `word` when a complete word ends.
2. **DFS from each cell**:
   - Skip if character not in current Trie node.
   - Move into the next Trie node.
   - If that node has `word`, add it to results.
   - Mark current cell as visited (`'#'`) and explore neighbors.
   - Restore cell afterwards (backtracking).
3. **Avoid duplicates**:
   - Use a `Set` (or mark `p.word=null` once found).

**Example Walkthrough**:

```java

Board =
[o a a n]
[e t a e]
[i h k r]
[i f l v]

Words = ["oath","pea","eat","rain"]

```

- Trie built → root → "oath", "pea", "eat", "rain".
- Start DFS at (0,0) = 'o':
  - Matches trie path "o".
  - Explore → (0,1) = 'a' → path "oa".
  - Explore → (1,1) = 't' → path "oat".
  - Explore → (2,1) = 'h' → complete word "oath".
  - Add "oath".
- Later DFS finds "eat" similarly.
- Final result = ["oath", "eat"].

**Complexity**:

- Building Trie: **O(sum of all word lengths)**
- DFS search: Each board cell visited at most once per path → **O(M·N·4^Lmax)** worst-case.
- In practice, much faster since Trie prunes invalid branches early.

---

## 3. Alternative Optimized Approach — Backtracking + Prefix Hashing

Instead of a Trie, we can:

- Store all words in a **hash set**.
- Store all prefixes in another set.
- While DFS exploring, prune if the current path is not a valid prefix.

**Tradeoff**:

- Easier to implement than Trie.
- But prefix hash set consumes more memory and may be slower for huge input.

---

## ✨ Conclusion

- **Brute Force**: Easy but very slow (check every word separately).  
- **Trie + DFS (Your Code)**: Efficient — uses prefix pruning, avoids repeated searches, and finds all words in one traversal.  
- **Prefix Hashing Alternative**: Works, but less memory-efficient than Trie.

✅ Your Trie + DFS implementation is the **standard optimal solution** for Word Search II.

---
