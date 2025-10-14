# How_Why.md — Adjacent Increasing Subarrays Detection I (LeetCode #3349)

## 🧩 Problem Statement

You are given an integer list `nums` and an integer `k`.

We need to determine **whether there exist two adjacent strictly increasing subarrays**,  
each of length at least `k`.

Formally:

- A subarray `[a₁, a₂, …, aₖ]` is **strictly increasing** if every `aᵢ < aᵢ₊₁`.
- Two subarrays are **adjacent** if the first ends exactly where the second begins.

Return `true` if such two adjacent increasing subarrays of length ≥ `k` exist, else return `false`.

---

## 🧠 Brute Force Approach

### 💡 Idea

- Enumerate **all possible subarrays**.
- For each subarray, check if it’s increasing and track lengths.
- Try to find two consecutive segments each of length ≥ `k`.

### ⚙️ Steps

1. Iterate through all possible start indices.
2. Expand forward to find increasing streaks.
3. Track consecutive segments and verify adjacency.
4. Return `true` if any two adjacent increasing segments both ≥ `k`.

### 🚨 Limitation

- Checking every subarray takes **O(n²)** time.
- Not efficient for large inputs.

---

## ⚡ Optimized Approach — Single Pass (O(n))

### 💡 Key Insight

We can detect adjacent increasing subarrays **on the fly** using **two counters**:

- `inc`: length of current increasing segment,
- `prevInc`: length of previous increasing segment.

As we traverse:

- When the current pair `nums[i] > nums[i-1]`, we extend the current streak (`inc++`).
- When it breaks (not increasing), the **previous streak** ends:
  - Assign `prevInc = inc`.
  - Reset `inc = 1`.

Then check if the **junction** between the previous and current streaks
forms two increasing subarrays of length ≥ `k`.

---

### ⚙️ Step-by-Step Breakdown

```java
int inc = 1, prevInc = 0, maxLen = 0;
```

* `inc` = current increasing segment length.
* `prevInc` = previous increasing segment length.
* `maxLen` = the largest valid combined pattern found.

---

#### 🔹 Case 1: `nums[i] > nums[i-1]`

→ Continue increasing streak:

```java
inc++;
```

#### 🔹 Case 2: `nums[i] <= nums[i-1]`

→ Increasing streak broken:

```java
prevInc = inc;
inc = 1;
```

Then, compute possible combined adjacency:

```java
maxLen = Math.max(maxLen, Math.max(inc >> 1, Math.min(prevInc, inc)));
```

Here:

* `Math.min(prevInc, inc)` ensures **both sides are long enough**.
* `inc >> 1` (same as `inc / 2`) catches smaller adjacent patterns (optimization heuristic).

If at any point `maxLen >= k`, return `true`.

---

### 🧮 Example Walkthrough

#### Example:

```java
nums = [1, 2, 3, 2, 3, 4, 5]
k = 2
```

| i | nums[i-1] | nums[i] | Relation | inc | prevInc | maxLen | Comment                    |
| - | --------- | ------- | -------- | --- | ------- | ------ | -------------------------- |
| 1 | 1         | 2       | ↑        | 2   | 0       | 0      | Start increasing           |
| 2 | 2         | 3       | ↑        | 3   | 0       | 0      | Continue                   |
| 3 | 3         | 2       | ↓        | 1   | 3       | 1      | Break, new sequence        |
| 4 | 2         | 3       | ↑        | 2   | 3       | 2      | Adjacent 3↑ and 2↑ found ✅ |

Output → **true**

---

## 💻 Code

```java
public class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        int inc = 1, prevInc = 0, maxLen = 0;

        for (int i = 1; i < n; i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                inc++;
            } else {
                prevInc = inc;
                inc = 1;
            }

            maxLen = Math.max(maxLen, Math.max(inc >> 1, Math.min(prevInc, inc)));
            if (maxLen >= k) return true;
        }

        return false;
    }
}
```

---

## 🧾 Complexity Analysis

| Type         | Complexity                         | Explanation               |
| ------------ | ---------------------------------- | ------------------------- |
| Time         | **O(n)**                           | Single pass through array |
| Space        | **O(1)**                           | Constant extra variables  |
| Optimized By | Tracking adjacent streaks directly |                           |

---

## ✅ Summary

| Aspect       | Description                                                |
| ------------ | ---------------------------------------------------------- |
| 🔧 Approach  | Single-pass two-counter technique                          |
| 💡 Key Trick | Maintain current and previous streaks                      |
| ⏱️ Time      | O(n)                                                       |
| 💾 Space     | O(1)                                                       |
| 🧩 Result    | Efficient adjacency detection without subarray enumeration |

---

### TL;DR

Instead of checking all possible subarrays,
we **track increasing streaks continuously** and detect adjacency on-the-fly —
making the algorithm **linear**, elegant, and efficient.

---
