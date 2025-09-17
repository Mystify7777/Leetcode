
# How_Why.md

## Problem

Given an integer array `nums` and an integer `k`, return the total number of subarrays whose sum equals `k`.

---

## How (Step-by-step Solution)

### Approach 1: Prefix Sum + HashMap (Standard)

1. Maintain a **running prefix sum** (`sum`) while iterating over `nums`.
2. At each step, check if `(sum - k)` has been seen before:

   * If yes, then there exists a subarray ending at current index with sum `k`.
   * Add the frequency of `(sum - k)` to the result.
3. Store/update the frequency of the current `sum` in the HashMap.

   * Initialize with `{0: 1}` to handle subarrays starting from index `0`.

**Code:**

```java
public int subarraySum(int[] nums, int k) {
    int count = 0, sum = 0;
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    for (int num : nums) {
        sum += num;
        if (map.containsKey(sum - k)) {
            count += map.get(sum - k);
        }
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
}
```

---

### Approach 2: Hand-Rolled Hash Table (Obfuscated Version)

This second code is doing essentially the same logic **without using Java’s HashMap**.

* **Custom Hashing:**

  * Uses constants `MIXER = 0x9E3779BA` (a known "golden ratio" hash mixer for integers).
  * The `mask()` function ensures the array size is a power-of-two minus 1, so it can bitmask instead of using `%`.

* **Hashtable:**

  * `int[] hashtable` stores keys and values in pairs: `[key, count, key, count, ...]`.
  * Probes linearly (open addressing) when collisions happen.

* **Zeros handling:**

  * Instead of storing `{0: 1}` like in HashMap, it maintains a `zeros` counter separately.

In short, it’s **reinventing HashMap**, but much more cryptic and error-prone.

---

## Why (Reasoning)

* The **clean HashMap solution** is straightforward, readable, and interview-ready.
* The **obfuscated version**:

  * Eliminates Java’s built-in HashMap overhead.
  * Likely designed for performance (competitive programming, LeetCode speedruns).
  * But **way harder to understand and maintain**.

Both do the same thing:

* Track **prefix sums**.
* Count how many times a given prefix sum has been seen before.
* Use that to calculate number of subarrays with sum `k`.

---

## Complexity Analysis

* **Time Complexity**: O(n) (both approaches, each element processed once).
* **Space Complexity**: O(n) for storing prefix sums in the map/hashtable.

---

## Example Walkthrough

### Input

```text
nums = [1, 1, 1], k = 2
```

### Execution

* Prefix sums:

  * sum = 1 → map = {0:1, 1:1}
  * sum = 2 → sum - k = 0 → count = 1 → map = {0:1, 1:1, 2:1}
  * sum = 3 → sum - k = 1 → count = 2 → map = {0:1, 1:1, 2:1, 3:1}

### Output

```text
2
```

(two subarrays `[1,1]`, `[1,1]`).

---

## Alternate Approaches

1. **Brute Force (O(n²))**

   * For each subarray, compute sum and check if equals `k`.

2. **Prefix Sum Array (O(n²))**

   * Precompute prefix sums, check all pairs `(i, j)`.

3. **Optimized Prefix Sum + HashMap (O(n))** ✅ Best.

---

✨ **Conclusion**: Stick with the **HashMap solution** — it’s clear and optimal.
The second version is just a custom low-level hash table that obfuscates the idea.

---
