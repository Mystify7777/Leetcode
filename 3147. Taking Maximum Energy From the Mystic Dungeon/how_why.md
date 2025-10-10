# How_Why.md — Taking Maximum Energy From the Mystic Dungeon (LeetCode #3147)

## 🧩 Problem Statement

You are in a **mystic dungeon** with `n` rooms.  
Each room `i` contains an energy value `energy[i]` (can be positive or negative).  
You can **start at any room**, and each time you move, you jump **exactly `k` rooms ahead**.

Your goal is to **maximize the total energy collected** from all visited rooms.

Return the **maximum energy** you can collect.

---

## 🧠 Brute Force Approach

### 💡 Idea

Start from every possible index and keep jumping `k` steps ahead, summing the energies.

**Steps:**

1. Loop over each starting index `i`.
2. Keep summing `energy[i] + energy[i+k] + energy[i+2k] + ...` until you go out of bounds.
3. Track the maximum sum obtained.

**Code Sketch:**

```java
int max = Integer.MIN_VALUE;
for (int start = 0; start < n; start++) {
    int sum = 0;
    for (int j = start; j < n; j += k) {
        sum += energy[j];
        max = Math.max(max, sum);
    }
}
return max;
```

### ⚠️ Why It’s Bad

* For every starting point, we traverse multiple elements with step `k`.
* Time complexity = `O(n² / k)` in the worst case (almost `O(n²)` when `k` is small).
* Extremely inefficient for large arrays.

---

## ⚡ Optimized Approach — Dynamic Programming (Bottom-Up)

### 💡 Core Insight

Each position’s best total energy **depends on future reachable positions**:

```java
maxEnergy[i] = energy[i] + max(0, maxEnergy[i + k])
```

* If we know the max energy starting from future rooms, we can compute the current one in O(1).
* Process from **right to left** (since jumps go forward).

---

### 🧮 Example Walkthrough

```java
energy = [2, -1, 3, -4, 2]
k = 2
```

| Index (i) | energy[i] | i + k   | dp[i + k] | dp[i] = energy[i] + dp[i+k] | result |
| --------- | --------- | ------- | --------- | --------------------------- | ------ |
| 4         | 2         | 6 (OOB) | 0         | 2                           | 2      |
| 3         | -4        | 5 (OOB) | 0         | -4                          | 2      |
| 2         | 3         | 4       | 2         | 5                           | 5      |
| 1         | -1        | 3       | -4        | -5                          | 5      |
| 0         | 2         | 2       | 5         | 7                           | 7      |

✅ Final result = **7**

Explanation:

* Best path starts at index `0 → 2 → 4`
  Energy = `2 + 3 + 2 = 7`

---

## 🧩 Code Implementation

```java
class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int[] dp = new int[n];
        int max = Integer.MIN_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = energy[i] + (i + k < n ? dp[i + k] : 0);
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
```

---

## ⏱️ Complexity Analysis

| Operation                    | Time     | Space    | Explanation           |
| ---------------------------- | -------- | -------- | --------------------- |
| Iterating from right to left | O(n)     | O(n)     | One pass DP fill      |
| Final max computation        | —        | —        | Done during same pass |
| **Total**                    | **O(n)** | **O(n)** | Efficient             |

If desired, we can even optimize space to **O(1)** by only keeping track of reachable segments, but readability drops slightly.

---

## 🔍 Why It Works

* Every position depends **only** on the position `i + k` (future reachable room).
* By iterating backwards, we ensure all needed values are computed when required.
* The DP array captures **maximum cumulative energy** starting from each position.
* Taking the global max over `dp[i]` gives the optimal starting room.

---

## ✅ Final Verdict

| Aspect               | Description                               |
| -------------------- | ----------------------------------------- |
| 🔧 Approach          | Bottom-Up Dynamic Programming             |
| ⏱️ Time              | O(n)                                      |
| 💾 Space             | O(n)                                      |
| 💡 Trick             | Backward traversal to reuse future states |
| ⚡ Efficiency         | Excellent — Handles large input easily    |
| 🧙‍♂️ Core Principle | “Future-dependent jump DP”                |

✅ **Result:** Elegant O(n) dynamic programming solution with minimal overhead.

---
