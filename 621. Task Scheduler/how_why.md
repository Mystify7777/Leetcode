# How_Why.md – Task Scheduler (LeetCode 621)

## ❌ Brute Force Idea

We’re asked to schedule tasks (`A`–`Z`) with a cooldown of `n` units between the same task.

**Naive approach:**

* Simulate task scheduling step by step.
* At each unit of time, pick the highest-frequency available task.
* If no task is available, insert an idle.

Example (tasks = `AAABBB`, n=2):

```
A _ _ A _ _ A
B _ _ B _ _ B
```

We fill gaps with idle slots if needed.

* **Time complexity:** O(time × #tasks) → very slow when tasks are large.
* **Space complexity:** O(#tasks).

---

## ✅ Optimized Approach – Frequency Counting

The scheduling is driven by the **most frequent task**.

Key observations:

1. Let `maxFreq` = maximum frequency of any task.
2. The most frequent task creates `maxFreq - 1` full "chunks".

   * Each chunk has length `(n + 1)` (task + idle slots).
3. Other tasks can fill these idle slots.
4. If multiple tasks share `maxFreq`, we need to place them in the last chunk too.

Formula:

```
result = max(tasks.length, (maxFreq - 1) * (n + 1) + maxCount)
```

where `maxCount` = number of tasks having frequency `maxFreq`.

---

### Your Implementation #1 (Chunk + Idle Calculation)

```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        Arrays.sort(freq);
        
        int chunk = freq[25] - 1;   // number of full cycles
        int idle = chunk * n;       // total idle slots initially
        
        // Fill idle slots with other task frequencies
        for (int i = 24; i >= 0; i--) {
            idle -= Math.min(chunk, freq[i]);
        }
        
        return idle < 0 ? tasks.length : tasks.length + idle;
    }
}
```

* Counts task frequencies.
* Sorts to find the max.
* Computes idle slots and reduces them by filling with other tasks.
* Returns total length = tasks + idle.

---

### Alternative Implementation #2 (Formula-Based)

```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] frequencies = new int[26];
        for (char letter : tasks) {
            frequencies[letter - 'A']++;
        }
        int maxFreq = 0;
        for (int freq : frequencies) {
            maxFreq = Math.max(maxFreq, freq);
        }
        int maxCount = 0;
        for (int freq : frequencies) {
            if (freq == maxFreq) maxCount++;
        }
        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + maxCount);
    }
}
```

* Same idea, but directly applies the scheduling formula.

---

## 🔎 Example Walkthrough

Input:

```
tasks = [A, A, A, B, B, B], n = 2
```

1. Frequencies: A=3, B=3
   → maxFreq = 3, maxCount = 2
2. Formula: `(maxFreq - 1) * (n + 1) + maxCount`
   = `(3-1) * (2+1) + 2`
   = `2*3 + 2 = 8`
3. tasks.length = 6 → max(6, 8) = 8

Output: **8** ✅

Schedule example:

```
A B idle A B idle A B
```

---

## 📊 Complexity Analysis

* **Time:** O(26 log 26) = O(1) for sorting / counting.
* **Space:** O(26) = O(1).

---

## ✅ Key Takeaways

* Always think in terms of the **most frequent task**; it dictates the minimum schedule length.
* Formula is cleaner and direct.
* The idle-slot approach gives more intuition on *why* the formula works.

---
