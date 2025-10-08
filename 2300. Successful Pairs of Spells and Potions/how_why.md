# How_Why.md — Successful Pairs of Spells and Potions (LeetCode #2300)

## 🧩 Problem Statement

You are given two integer arrays:

- `spells[]`: power of spells
- `potions[]`: strength of potions  
and an integer `success`, representing the minimum product for a spell-potion pair to be **successful**.

A pair `(spell, potion)` is successful if:

```java

spell * potion >= success

```

Return an array `pairs[]` where `pairs[i]` = number of successful potions for `spells[i]`.

---

## 🧠 Brute Force Approach

### 💡 Idea

For each spell, check all potions and count how many satisfy:

```java

spell * potion >= success

```

### 🧩 Steps

1. Loop over each spell.
2. For every spell, loop over all potions.
3. Count the number of potions that make the product ≥ success.

### 🧮 Example

```java

spells = [3, 1, 2]
potions = [8, 5, 8]
success = 16

```

- Spell 3 → [8,5,8] → products [24,15,24] → 2 successful  
- Spell 1 → [8,5,8] → [8,5,8] → 0 successful  
- Spell 2 → [8,5,8] → [16,10,16] → 2 successful  

✅ Output: `[2, 0, 2]`

### 🚨 Problem

- Time Complexity: **O(n × m)** → too slow for 10⁵ inputs.
- Recomputes results unnecessarily.

---

## ⚡ Optimized Approach — Sorting + Binary Search

### 💡 Key Idea

If `potions` is sorted, we can quickly find the **first potion** strong enough for each spell using **binary search**.

### 🧩 Steps

1. Sort `potions` in ascending order.
2. For each `spell`, perform a binary search on `potions` to find:

    ```java

    smallest index where (spell * potions[index]) >= success

    ```

3. If such an index = `left`, then all potions from `left` to `end` are valid.  
So, number of successful pairs = `m - left`.

---

### 🔍 Example Walkthrough

```java

spells = [3, 1, 2]
potions = [8, 5, 8]
success = 16

```

**Step 1:** Sort potions → `[5, 8, 8]`

**Step 2:** For each spell:

| Spell | Binary Search Target | Found Index | Successful Count |
|--------|----------------------|--------------|------------------|
| 3 | 16/3 ≈ 5.34 | 0 → potion 5 fails → move right → index=1 | 3 - 1 = 2 |
| 1 | 16/1 = 16 | no potion qualifies → index=3 | 0 |
| 2 | 16/2 = 8 | first potion ≥ 8 at index=1 | 3 - 1 = 2 |

✅ Output → `[2, 0, 2]`

---

### 🧮 Code Implementation

```java
class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length;
        int m = potions.length;
        int[] pairs = new int[n];
        Arrays.sort(potions);

        for (int i = 0; i < n; i++) {
            int spell = spells[i];
            int left = 0, right = m - 1;

            // Binary Search for smallest potion that succeeds
            while (left <= right) {
                int mid = left + (right - left) / 2;
                long product = (long) spell * potions[mid];
                if (product >= success)
                    right = mid - 1;
                else
                    left = mid + 1;
            }
            pairs[i] = m - left; // remaining potions succeed
        }
        return pairs;
    }
}
```

---

### ⏱️ Complexity Analysis

| Operation                    | Time                 | Space    | Explanation              |
| ---------------------------- | -------------------- | -------- | ------------------------ |
| Sorting potions              | O(m log m)           | O(1)     | Sort once                |
| Binary search for each spell | O(n log m)           | O(1)     | Per spell                |
| **Total**                    | **O((n + m) log m)** | **O(1)** | Efficient for 10⁵ inputs |

---

## 🔧 Alternative Optimization — Prefix Counting (Bucket Trick)

The alternative approach (in your commented code) avoids binary search:

### 💡 Idea

* Precompute how many potions have strength ≥ `x` using prefix sums.
* For each spell, compute the **minimum potion strength needed**:

  ```java
  required = ceil(success / spell)
  ```

* Directly look up how many potions meet this requirement.

### 🧩 Example

If `potions = [2, 3, 5, 10]`, then:

```java
map[i] = count of potions >= i
```

So `map[5] = 2`, `map[10] = 1`.

Then for each spell, just check `map[required]`.

### 🧮 Complexity

| Step              | Time               | Space                                |
| ----------------- | ------------------ | ------------------------------------ |
| Preprocessing     | O(maxPotion)       | O(maxPotion)                         |
| Each spell lookup | O(1)               | —                                    |
| **Total**         | O(n + maxPotion)** | Large space but constant-time lookup |

⚠️ Drawback: Space can explode if `max(potions)` is huge (up to 10⁵).

---

## ✅ Comparison Summary

| Approach        | Technique      | Time               | Space | Pros            | Cons          |
| --------------- | -------------- | ------------------ | ----- | --------------- | ------------- |
| Brute Force     | Double Loop    | O(n·m)             | O(1)  | Simple          | Too slow      |
| Binary Search ✅ | Sort + BS      | **O((n+m) log m)** | O(1)  | Fast, reliable  | Needs sorting |
| Prefix Counting | Precomputation | O(n + maxPotion)   | High  | Constant lookup | High memory   |

---

## 🧩 Why Your Version Works

* Sorting ensures potions are in increasing order.
* Binary search efficiently pinpoints the **first successful potion** for each spell.
* Avoids overflow with `(long) spell * potion`.
* Final count derived as `m - left` elegantly captures all succeeding pairs.

---

## 🎯 Final Verdict

Your binary search solution is:

* **Elegant**
* **Optimal**
* **Cleanly structured**
* **Time-efficient (O((n+m)logm))**

✅ **Result:** Accepted — 100% correct and efficient for large inputs.

---
