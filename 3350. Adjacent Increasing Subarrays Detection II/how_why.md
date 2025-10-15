# How_Why.md — Adjacent Increasing Subarrays Detection II (LeetCode #3350)

## 🧩 Problem Statement

You are given an integer list `nums`.  
We must find the **maximum possible `k`** such that there exist **two adjacent strictly increasing subarrays** of length **at least `k`**.

Formally:

- A subarray `[a₁, a₂, …, aₖ]` is *strictly increasing* if `aᵢ < aᵢ₊₁` for all valid `i`.
- Two subarrays are *adjacent* if one ends exactly where the next begins.

Return the **maximum `k`** that satisfies this property.

---

## 🧠 Brute Force Approach

### 💡 Idea

1. Find all possible increasing subarrays.
2. Check pairs of adjacent subarrays.
3. For each pair, find the longest possible `k` such that both subarrays have length ≥ `k`.

### ⚙️ Steps

- Use two nested loops to find all increasing sequences.
- Compare consecutive ones.
- Track the maximum possible shared length `k`.

### ⏱️ Complexity

- **O(n²)** time — expensive for large arrays.
- **O(1)** space.

---

## ⚡ Optimized Approach — Single Pass (O(n))

### 💡 Core Idea

Instead of recomputing every subarray, we can **track increasing streaks** while iterating.

We only need to know:

- The **length of the current increasing segment (`up`)**.
- The **length of the previous increasing segment (`pre_max_up`)**.
- The **maximum result (`res`)** computed dynamically.

Whenever we encounter a “break” (i.e., not increasing), we **compare the two most recent streaks**.

---

### ⚙️ Algorithm Walkthrough

#### Step 1: Initialize

```java
int n = A.size();
int up = 1, pre_max_up = 0, res = 0;
```

* `up`: current increasing subarray length.
* `pre_max_up`: previous increasing subarray length.
* `res`: result (maximum `k` found).

---

#### Step 2: Traverse the array

For every element:

```java
if (A.get(i) > A.get(i - 1)) {
    up++; // still increasing
} else {
    pre_max_up = up; // store previous segment
    up = 1; // reset
}
```

Whenever the sequence breaks, we’ve identified two consecutive increasing blocks —
so we can compare them.

---

#### Step 3: Compute adjacency

At each step, update result:

```java
res = Math.max(res, Math.max(up / 2, Math.min(pre_max_up, up)));
```

### Explanation

* `Math.min(pre_max_up, up)` = maximum `k` if both previous and current streaks are long enough.
* `up / 2` = handles edge cases where the streak extends across the junction (symmetric pattern).

---

#### Step 4: Return result

```java
return res;
```

---

### 🧮 Example Walkthrough

#### Example:

```c
A = [1, 2, 3, 2, 3, 4, 5]
```

| i                | A[i-1] | A[i] | Relation | up | pre_max_up | res | Explanation                              |
| ---------------- | ------ | ---- | -------- | -- | ---------- | --- | ---------------------------------------- |
| 1                | 1      | 2    | ↑        | 2  | 0          | 0   | start increasing                         |
| 2                | 2      | 3    | ↑        | 3  | 0          | 0   | continue                                 |
| 3                | 3      | 2    | ↓        | 1  | 3          | 1   | break, store prev=3                      |
| 4                | 2      | 3    | ↑        | 2  | 3          | 2   | adjacent sequences [1,2,3] & [2,3] found |
| 5                | 3      | 4    | ↑        | 3  | 3          | 3   | larger adjacency                         |
| 6                | 4      | 5    | ↑        | 4  | 3          | 3   | continues                                |
| → **Result = 3** |        |      |          |    |            |     |                                          |

---

### 💻 Code Implementation

```java
class Solution {
    public int maxIncreasingSubarrays(List<Integer> A) {
        int n = A.size(), up = 1, pre_max_up = 0, res = 0;
        for (int i = 1; i < n; ++i) {
            if (A.get(i) > A.get(i - 1)) {
                up++;
            } else {
                pre_max_up = up;
                up = 1;
            }
            res = Math.max(res, Math.max(up / 2, Math.min(pre_max_up, up)));
        }
        return res;
    }
}
```

---

## 🧾 Complexity Analysis

| Type        | Complexity                        | Explanation                  |
| ----------- | --------------------------------- | ---------------------------- |
| ⏱️ Time     | **O(n)**                          | One-pass traversal           |
| 💾 Space    | **O(1)**                          | Only constant variables used |
| ⚙️ Approach | Dynamic window of increasing runs |                              |

---

## 🔍 Comparison with 3349 (Part I)

| Version       | Output  | Meaning                      |
| ------------- | ------- | ---------------------------- |
| **3349 (I)**  | Boolean | Whether such `k` exists      |
| **3350 (II)** | Integer | The **maximum** possible `k` |

Both share the **same logic**, but problem II extends it to compute the **maximum length**.

---

## ✅ Summary

| Aspect          | Description                                             |
| --------------- | ------------------------------------------------------- |
| 🔧 Method       | Track consecutive increasing streaks                    |
| 🧩 Core Concept | Compare consecutive runs                                |
| ⏱️ Time         | O(n)                                                    |
| 💾 Space        | O(1)                                                    |
| 🧠 Result       | Finds the largest `k` for adjacent increasing subarrays |

---

### TL;DR

Instead of scanning all subarrays,
**track increasing streaks** in one pass.
When a break occurs, use the lengths of the two latest streaks
to update the **maximum adjacent increasing length `k`** efficiently.

---
