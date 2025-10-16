# How_Why.md — Smallest Missing Non-negative Integer After Operations (LeetCode #2598)

## 🧩 Problem Statement

You are given an integer array `nums` and an integer `value`.

In one operation, you can **add or subtract `value`** to any element of `nums`.  
After performing any number of operations, your goal is to find the **smallest non-negative integer** that **cannot** appear in the array.

Return that smallest missing integer.

---

## 🧠 Brute Force Approach

### 💡 Idea

1. Generate all possible values for each element by repeatedly adding or subtracting `value`.
2. Track all achievable integers in a `set`.
3. Start from `0` upwards and return the smallest integer not present.

### ⚙️ Example

```java

nums = [1, -10, 7, 13, 6, 8], value = 5

```

Each element can take on multiple forms like:

```java

1 ± 5k → {..., -9, -4, 1, 6, 11, ...}

```

Keep marking numbers as “reachable,” then find the smallest unmarked one.

### ⏱️ Complexity

- **Time:** Exponential → infeasible for large inputs.
- **Space:** Huge, due to storing infinite reachable values.

---

## ⚡ Optimized Approach — Modular Remainders (O(n))

### 💡 Insight

Each element’s value is determined **modulo `value`**, since adding or subtracting `value` doesn’t change its remainder class.

So instead of considering infinite additions/subtractions,  
we only need to count **how many numbers** fall into each remainder bucket.

---

### ⚙️ Algorithm Steps

#### Step 1. Compute normalized remainders

For each number `x`, we calculate:

```java
r = ((x % value) + value) % value

```

This ensures that `r` is always in `[0, value-1]`, even for negative numbers.

Store the frequency of each remainder in an array `rem[]`.

#### Step 2. Determine the smallest missing integer

Now, imagine you are trying to “fill up” numbers starting from `0`:

- The number `0` corresponds to `rem[0]`.
- The number `1` corresponds to `rem[1 % value]`.
- The number `2` corresponds to `rem[2 % value]`.
- …

Each time you can use one element from that remainder class.

Continue until a remainder class runs out — that’s where the smallest missing number lies.

---

### 💻 Implementation

```java
class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int n = nums.length, res = 0;
        int[] rem = new int[value];
        
        for (int x : nums) {
            int r = ((x % value) + value) % value; // normalize negative remainders
            rem[r]++;
        }
        
        while (rem[res % value]-- > 0) res++;
        return res;
    }
}
```

---

### 🧮 Example Walkthrough

#### Input

```java
nums = [1, -10, 7, 13, 6, 8]
value = 5
```

#### Step 1: Compute remainders

| Element | x % 5 | Normalized | Count    |
| ------- | ----- | ---------- | -------- |
| 1       | 1     | 1          | rem[1]++ |
| -10     | 0     | 0          | rem[0]++ |
| 7       | 2     | 2          | rem[2]++ |
| 13      | 3     | 3          | rem[3]++ |
| 6       | 1     | 1          | rem[1]++ |
| 8       | 3     | 3          | rem[3]++ |

`rem = [1, 2, 1, 2, 0]`

#### Step 2: Find smallest missing integer

| i | i % 5 | rem[i % 5] | Result       |
| - | ----- | ---------- | ------------ |
| 0 | 0     | 1 → use    | res = 1      |
| 1 | 1     | 2 → use    | res = 2      |
| 2 | 2     | 1 → use    | res = 3      |
| 3 | 3     | 2 → use    | res = 4      |
| 4 | 4     | 0 → stop   | ✅ answer = 4 |

➡️ **Output: 4**

---

## 🧾 Complexity Analysis

| Type     | Complexity   | Explanation                                   |
| -------- | ------------ | --------------------------------------------- |
| ⏱️ Time  | **O(n)**     | One pass for counting + one pass for scanning |
| 💾 Space | **O(value)** | Array to store remainder frequencies          |

---

## 🔁 Alternative Approach (More Mathematical)

### Code

```java
class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] freq = new int[value];
        for (int n : nums) {
            int mod = n % value;
            if (mod < 0) mod += value;
            freq[mod]++;
        }

        int min = freq[0], pos = 0;
        for (int i = 0; i < value; i++) {
            if (freq[i] < min) {
                pos = i;
                min = freq[i];
            }
        }

        return value * min + pos;
    }
}
```

### Explanation

* Each full “round” of all remainders contributes `value` to the final number.
* The missing integer lies in the first remainder bucket that runs short.
* Result formula:
  **`result = value * minCount + remainderIndex`**

This method avoids the `while` loop and directly computes the answer.

---

## ✅ Summary

| Aspect          | Description                                                        |
| --------------- | ------------------------------------------------------------------ |
| 🧩 Core Concept | Remainder counting under modulo `value`                            |
| 🔧 Key Step     | Normalize negatives, then count each remainder                     |
| ⏱️ Time         | O(n)                                                               |
| 💾 Space        | O(value)                                                           |
| 💡 Insight      | Each number class repeats every `value`, so just track frequencies |

---

### TL;DR

Adding or subtracting multiples of `value` only affects the **remainder class**.
Track how many numbers exist for each remainder, then the **first empty slot** in the sequence determines the smallest missing integer.

---
