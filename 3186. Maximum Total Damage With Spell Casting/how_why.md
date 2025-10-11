# How_Why.md — Maximum Total Damage With Spell Casting (LeetCode #3186)

## 🧩 Problem Statement

You are a sorcerer casting destructive spells.  
Each spell has a **damage power value** `power[i]`.  

If you cast a spell with power `x`, you **cannot cast** any spell with power:

```java

x - 2, x - 1, x + 1, or x + 2

```

as they interfere magically.

Your goal is to **maximize total damage** dealt by choosing a subset of spells to cast (possibly with repetition, based on their frequency).

Return the **maximum total damage** possible.

---

## 🧠 Brute Force Approach

### 💡 Idea

Try all subsets of spells, skipping the ones that are within ±2 difference of already-chosen ones.

### ⚙️ Steps

1. Generate all unique spell powers.
2. Recursively try to include/exclude each spell:
   - Include it → add its total contribution and skip conflicting ones.
   - Exclude it → move to next.
3. Return the max sum obtained.

### ⚠️ Why It’s Bad

- Exponential recursion (`O(2^n)`).
- Even small inputs will time out because conflicts span ±2 range.
- Not feasible for large arrays.

---

## ⚡ Optimized Approach — Dynamic Programming on Sorted Unique Powers

### 💡 Core Insight

This is a **variant of the “Delete and Earn” problem** (LeetCode #740),  
but the conflict range extends from `±1` to `±2`.

If we sort all unique damage values and compute the total contribution for each value:

```java

total_damage(value) = value * frequency(value)

```

Then, for each value:

- Either **skip** it (take previous best),
- Or **take** it, and add it to the best total **that doesn’t conflict** (any damage ≤ current_value - 3).

---

### 🧮 Example Walkthrough

```java

power = [3, 4, 2, 8, 10, 10, 9]

```

### Step 1 — Count Frequencies

| Power | Frequency | Total Damage |
|--------|------------|--------------|
| 2 | 1 | 2 |
| 3 | 1 | 3 |
| 4 | 1 | 4 |
| 8 | 1 | 8 |
| 9 | 1 | 9 |
| 10 | 2 | 20 |

#### Step 2 — Sorted Unique List

`[2, 3, 4, 8, 9, 10]`

#### Step 3 — Build DP Table

| i | Value | TotalDamage | Non-conflicting prev | DP[i] |
|---|--------|--------------|----------------------|--------|
| 0 | 2 | 2 | — | 2 |
| 1 | 3 | 3 | — (conflicts) | 3 |
| 2 | 4 | 4 | — (conflicts) | 4 |
| 3 | 8 | 8 | DP[2] = 4 | 12 |
| 4 | 9 | 9 | DP[2] = 4 | 13 |
| 5 | 10 | 20 | DP[2] = 4 | 24 |

✅ **Result:** `24`

Chosen powers → `[4, 10, 10]`  
Total damage = `4 + 10 + 10 = 24`

---

## 🧩 Final Code

```java
class Solution {
    public long maximumTotalDamage(int[] power) {
        // Step 1: Count frequency of each power
        Map<Integer, Long> freq = new HashMap<>();
        for (int val : power)
            freq.put(val, freq.getOrDefault(val, 0L) + 1);

        // Step 2: Sort unique values
        List<Integer> keys = new ArrayList<>(freq.keySet());
        Collections.sort(keys);

        int n = keys.size();
        long[] dp = new long[n];
        dp[0] = keys.get(0) * freq.get(keys.get(0));

        // Step 3: DP to find maximum non-conflicting total damage
        for (int i = 1; i < n; i++) {
            long val = keys.get(i) * freq.get(keys.get(i));
            dp[i] = dp[i - 1]; // Option 1: skip current value

            // Option 2: include current value (find previous non-conflicting)
            int j = i - 1;
            while (j >= 0 && Math.abs(keys.get(j) - keys.get(i)) <= 2)
                j--;

            if (j >= 0)
                dp[i] = Math.max(dp[i], dp[j] + val);
            else
                dp[i] = Math.max(dp[i], val);
        }

        return dp[n - 1];
    }
}
````

---

## ⏱️ Complexity Analysis

| Operation           | Time                | Space    | Explanation                      |
| ------------------- | ------------------- | -------- | -------------------------------- |
| Counting frequency  | O(n)                | O(n)     | Single pass                      |
| Sorting             | O(u log u)          | O(u)     | `u` = unique power values        |
| DP traversal        | O(u²) (worst)       | O(u)     | Nested scan for conflict search  |
| **Optimized Total** | **O(u log u + u²)** | **O(u)** | Acceptable for small unique sets |

Can be further optimized to **O(u log u)** using binary search to find `previousIndex` (instead of a while loop).

---

## 🔍 Why It Works

* By sorting unique powers, we ensure conflict checking only moves backward.
* DP ensures we always store **maximum achievable damage** up to each value.
* Conflicts (within ±2) are excluded by finding the nearest safe index.
* The method merges **frequency counting + DP on sorted values** — classic dynamic programming with dependency gap logic.

---

## ✅ Final Verdict

| Aspect            | Description                                 |
| ----------------- | ------------------------------------------- |
| 🔧 Approach       | Dynamic Programming on Sorted Unique Powers |
| ⚔️ Core Trick     | Handle ±2 conflict window                   |
| ⏱️ Time           | O(u log u + u²)                             |
| 💾 Space          | O(u)                                        |
| 🧙‍♂️ Key Concept | “Delete and Earn” with extended range       |
| ⚡ Efficiency      | Great for competitive constraints           |

✅ **Result:** Maximum total damage while respecting spell interference.

---
