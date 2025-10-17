# How_Why.md — Maximize the Number of Partitions After Operations (LeetCode #3003)

## 🧩 Problem Statement

You’re given:

- a string `s` of lowercase English letters,
- and an integer `k`.

You can **change at most one character** in `s` to any other letter.

You want to **split `s` into the maximum number of substrings**,  
such that **each substring contains at most `k` distinct letters**.

Return the **maximum number of partitions** possible after the operation.

---

## 🧠 Brute Force Approach

### 💡 Idea

- Try **all possible character changes** (including no change).
- For each modified string:
  - Scan left-to-right and split whenever the current substring has > `k` distinct characters.

### ⚙️ Steps

1. Loop over each position in `s`.
2. Try changing `s[i]` to each of 26 letters.
3. Count partitions for that version.
4. Keep the maximum.

### ⏱️ Complexity

| Type | Cost |
|------|------|
| Time | O(26 × n²) — checking partitions per possible change |
| Space | O(1) |

❌ Not practical — too slow for `|s| > 1000`.

---

## ⚙️ Optimized Approach — **DP + Bitmask + Memoization**

### 🔑 Core Intuition

We can treat this as a **dynamic programming** problem, where at each index:

- we track which letters exist in the current partition (as a **bitmask**),
- and whether we’ve already **used our one allowed change**.

---

### 🧩 State Definition

```java

dp(index, currentMask, canChange)
→ maximum number of additional partitions possible

```

**Where:**

- `index`: current position in string `s`
- `currentMask`: bitmask of letters seen in the current partition
- `canChange`: boolean (true if we still have the one-change opportunity)

---

### 🧮 State Transitions

1. **Without changing the current character:**
   - Add `s[index]` to the current partition (update bitmask).
   - If number of distinct letters ≤ `k`: continue current partition.
   - Else: start a new partition.

2. **With one character change (if allowed):**
   - Try replacing `s[index]` with every letter `'a'..'z'`.
   - For each possible replacement:
     - Update bitmask accordingly.
     - Apply same logic (continue or start new partition).
   - Mark `canChange = false` after using it once.

---

### 🧰 Memoization Key

Each DP call is uniquely identified by `(index, currentMask, canChange)`.

To store efficiently in a hash map:

```java
long key = ((long) index << 27) | ((long) currentMask << 1) | (canChange ? 1 : 0);
````

This fits all states into a single `long` key for fast lookups.

---

### 💻 Implementation

```java
class Solution {
    private HashMap<Long, Integer> cache;
    private String s;
    private int k;

    public int maxPartitionsAfterOperations(String s, int k) {
        this.cache = new HashMap<>();
        this.s = s;
        this.k = k;
        return dp(0, 0, true) + 1; // +1 for the first partition
    }

    private int dp(int index, int currentSet, boolean canChange) {
        long key = ((long) index << 27) | ((long) currentSet << 1) | (canChange ? 1 : 0);
        if (cache.containsKey(key)) return cache.get(key);
        if (index == s.length()) return 0;

        int charIndex = s.charAt(index) - 'a';
        int updatedSet = currentSet | (1 << charIndex);
        int distinct = Integer.bitCount(updatedSet);
        int res;

        // Case 1: Without changing the character
        if (distinct > k)
            res = 1 + dp(index + 1, 1 << charIndex, canChange);
        else
            res = dp(index + 1, updatedSet, canChange);

        // Case 2: Try changing character (if available)
        if (canChange) {
            for (int newChar = 0; newChar < 26; newChar++) {
                int newSet = currentSet | (1 << newChar);
                int newDistinct = Integer.bitCount(newSet);
                if (newDistinct > k)
                    res = Math.max(res, 1 + dp(index + 1, 1 << newChar, false));
                else
                    res = Math.max(res, dp(index + 1, newSet, false));
            }
        }

        cache.put(key, res);
        return res;
    }
}
```

---

### 🧮 Example Walkthrough

#### Input

```java
s = "accca", k = 2
```

#### Process

* Start with empty mask, index = 0, canChange = true
* Try taking `'a'`, `'c'`, etc., updating mask.
* When distinct letters exceed `k`, start a new partition.
* Try changing `'a'` to `'c'` to merge more letters under same partition count.

Result:

```java
maxPartitionsAfterOperations("accca", 2) = 2
```

---

## 📊 Complexity Analysis

| Aspect          | Complexity                                           | Reason                                       |
| --------------- | ---------------------------------------------------- | -------------------------------------------- |
| ⏱️ Time         | `O(n × 2^k × 26)` (pruned heavily)                   | Each index can trigger up to 26 replacements |
| 💾 Space        | `O(n × 2^k)`                                         | DP cache for states                          |
| 🧠 Optimization | Bitmask reduces distinct letter tracking to integers |                                              |

---

## 🧾 Alternate Approach (Iterative Mask Sliding)

A non-DP alternative uses bitmasks and prefix-partitions:

* Precompute left-to-right and right-to-left partitions.
* Try inserting a single change point to maximize partitions.
  This version avoids recursion but is more complex to implement correctly.

---

## ✅ Summary

| Concept        | Description                                             |
| -------------- | ------------------------------------------------------- |
| 🎯 Goal        | Maximize substring partitions with ≤ k distinct letters |
| 🔁 Allowed     | One character modification                              |
| ⚙️ Method      | Bitmask DP with memoization                             |
| 🧮 State       | (index, currentMask, canChange)                         |
| 💡 Key Insight | Adding a character only changes a bitmask of 26 bits    |
| ⏱️ Time        | Optimized recursive search with pruning                 |
| 🧾 Space       | Compact hash-based memoization                          |

---

### TL;DR

You recursively decide whether to:

* continue current substring,
* start a new one,
* or use your one allowed character change.

Each path is memoized via a bitmask that tracks seen letters —
making this problem a **classic bitmask-DP with a limited “joker” operation**.

---
