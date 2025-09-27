# How\_Why.md — Word Ladder (LeetCode 127)

---

## ❌ Brute Force (Baseline)

### Idea

* Try all possible transformation paths from `beginWord` to `endWord`.
* At each step, replace one character with `a–z`, check if it’s in `wordList`, and recurse until reaching the end.

### Example

`hit → hot → dot → dog → cog`
Try replacing every character at every step → exponential growth.

### Limitation

* Time complexity **O(26^L \* N)** in worst case (L = word length, N = wordList size).
* Too slow for large lists.

---

## ⚡ Approach 1: Single-direction BFS ✅

### Idea

* Model as a graph where words are nodes, edges exist between words differing by 1 character.
* Run BFS from `beginWord`:

  * Each step generates all neighbors (valid transformations in `wordList`).
  * Track levels (distance from start).
  * First time we see `endWord`, that’s the shortest path.

### Example

Queue = \[`hit`] → expand neighbors (`hot`) → queue = \[`hot`] → expand → (`dot`, `lot`) → … until `cog`.

### Complexity

* O(N × L × 26), where N = number of words, L = word length.
* Works but can still be slow if the search space is wide.

---

## ⚡ Approach 2: Bidirectional BFS (your code) 🚀

### Idea

* Run BFS **from both ends** (`beginWord` and `endWord`).
* At each step, expand the smaller frontier to keep branching factor low.
* If the two search frontiers meet, return the total path length.

### Key details from your code:

* `beginSet` and `endSet`: words to explore from each side.
* Always expand the smaller set (`if (beginSet.size() > endSet.size()) swap`).
* `visited` ensures we don’t revisit words.
* `dict` is the wordList for O(1) lookups.

### Example Trace

```
beginSet = {hit}, endSet = {cog}, step = 1
→ expand "hit": generate "hot", beginSet = {hot}, step = 2
→ expand "hot": generate "dot", "lot", ...
→ expand until meeting endSet {cog}
```

Shortest transformation found when sets meet.

### Complexity

* O(N × L × 26) in theory.
* In practice **\~O((N × L) / 2)** because searching from both sides cuts work nearly in half.

---

## Example Walkthrough

Let’s walk through the **bidirectional BFS** step by step with

```java
beginWord = "hit"
endWord   = "cog"
wordList  = ["hot","dot","dog","lot","log","cog"]
```

---

## 🔎 Step-by-Step Walkthrough (Bidirectional BFS)

---

### Initialization

```java
beginSet = {"hit"}
endSet   = {"cog"}
visited  = {}
step     = 1
```

---

### Step 1: Expand from `beginSet = {"hit"}`

* Take `"hit"`.

* Try replacing each character with `a..z`:

  * Replace 1st char: `ait, bit, ..., zit` → none in dict.
  * Replace 2nd char: `hat, hbt, hct, ... hot ✅` (in dict).
  * Replace 3rd char: `hia, hib, ... hiz` → none in dict.

* New words: `{"hot"}`.

```java
beginSet = {"hot"}
endSet   = {"cog"}
visited  = {"hot"}
step     = 2
```

---

### Step 2: Expand from `beginSet = {"hot"}`

* Take `"hot"`.

Neighbors:

* `"dot"` ✅
* `"lot"` ✅

```java
beginSet = {"dot","lot"}
endSet   = {"cog"}
visited  = {"hot","dot","lot"}
step     = 3
```

---

### Step 3: Expand from `beginSet = {"dot","lot"}`

#### Expand `"dot"`:

* `"dog"` ✅

#### Expand `"lot"`:

* `"log"` ✅

```java
beginSet = {"dog","log"}
endSet   = {"cog"}
visited  = {"hot","dot","lot","dog","log"}
step     = 4
```

---

### Step 4: Expand from `beginSet = {"dog","log"}`

#### Expand `"dog"`:

* `"cog"` ✅ → found in `endSet` 🎉

At this point, BFS frontiers meet.

```text
step = 4 + 1 = 5
```

---

## ✅ Final Result

Shortest transformation sequence length = **5**

Example valid sequence:

```text
hit → hot → dot → dog → cog
```

---

## 🏆 Summary

* **Brute Force:** exponential → infeasible.
* **Single-direction BFS:** correct, polynomial time, but still heavy.
* **Bidirectional BFS (your code):** optimal for shortest path in unweighted graph; drastically reduces search space.

👉 In interviews and real-world use, **bidirectional BFS** is the best choice.

---
