# How_Why.md — Maximum Number of Distinct Elements After Operations (LeetCode #3397)

## 🧩 Problem Statement

You are given:

- an integer array `nums`
- and an integer `k`.

You may **add or subtract any integer from each element**,  
as long as the **magnitude of the operation ≤ k** for that element.

Formally, for each `nums[i]`, you can transform it into **any value within the range**:

```java

[nums[i] - k, nums[i] + k]

```

Your goal:  
Find the **maximum possible number of distinct elements** you can achieve after performing these operations.

---

## 🧠 Brute Force Approach

### 💡 Idea

- For every number `nums[i]`, we can choose **any integer** in `[nums[i]-k, nums[i]+k]`.
- We need to choose values such that **no two numbers are equal**,  
  maximizing the count of unique numbers.

### ⚙️ Steps

1. Generate all possible values for each number (range length = `2k + 1`).
2. Try all combinations to find the configuration with most unique values.

### ⏱️ Complexity

| Type | Cost |
|------|------|
| Time | O((2k + 1)^n) — exponential |
| Space | O(n) |

❌ Clearly infeasible — even small inputs explode combinatorially.

---

## ⚙️ Optimized Greedy Approach — **Sorted Sweep**

### 🔑 Core Insight

Each number can “occupy” any integer interval `[x - k, x + k]`.  
We want to **place each number at the earliest possible distinct position**  
that doesn’t overlap with previously placed values.

By always choosing the smallest available valid number,
we **greedily maximize** how many distinct integers can fit.

---

### 🧮 Step-by-step Logic

1. **Sort** the array `nums` to handle smaller ranges first.
2. Maintain a variable `prev` for the **last chosen distinct number**.
3. For each number `x`:
   - The earliest valid number we can assign is `l = max(x - k, prev + 1)`  
     → ensures `l` is within `x`’s range **and** greater than previous chosen.
   - If `l ≤ x + k` (i.e. still within valid range),  
     we can assign `l` as a distinct new number.
   - Update `prev = l`, increment distinct count.

---

### 💻 Implementation

```java
class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0, prev = (int)-1e9;

        for (int x : nums) {
            int l = Math.max(x - k, prev + 1);
            if (l <= x + k) {
                prev = l;
                ans++;
            }
        }
        return ans;
    }
}
```

---

### 🧮 Example Walkthrough

#### Input

```java
nums = [4, 6, 2, 1], k = 2
```

#### Sorted

```java
[1, 2, 4, 6]
```

#### Step-by-step

| x | Range [x−k, x+k] | Earliest valid l   | Accepted? | prev | Distinct count |
| - | ---------------- | ------------------ | --------- | ---- | -------------- |
| 1 | [−1, 3]          | max(−1, −1e9+1)=−1 | ✅         | −1   | 1              |
| 2 | [0, 4]           | max(0, −1+1)=0     | ✅         | 0    | 2              |
| 4 | [2, 6]           | max(2, 0+1)=2      | ✅         | 2    | 3              |
| 6 | [4, 8]           | max(4, 2+1)=3      | ✅         | 3    | 4              |

✅ **Maximum distinct = 4**

---

### 🧮 Alternate Approach

```java
class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        if (nums.length <= (k << 1) + 1) return nums.length;
        Arrays.sort(nums);
        int distinct = 0;
        int l = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int m = Math.max(l+1, nums[i]-k);
            if (m <= nums[i]+k) {
                distinct++;
                l = m;
            }
        }
        return distinct;
    }
}
```

Slightly different style, but same greedy logic.

---

## 📊 Complexity Analysis

| Aspect        | Complexity                                  | Reason                   |
| ------------- | ------------------------------------------- | ------------------------ |
| ⏱️ Time       | O(n log n)                                  | Sorting dominates        |
| 💾 Space      | O(1)                                        | Constant auxiliary space |
| 🧠 Optimality | Greedy placement ensures max unique numbers |                          |

---

## ✅ Summary

| Concept         | Description                                                          |
| --------------- | -------------------------------------------------------------------- |
| 🎯 Goal         | Maximize number of distinct integers possible after ±k adjustments   |
| ⚙️ Method       | Greedy interval placement                                            |
| 🧮 Key Trick    | Assign smallest valid number after previous                          |
| 🧠 Why it works | Earlier numbers get smallest free slots, leaving room for later ones |
| ⏱️ Time         | O(n log n)                                                           |
| 💾 Space        | O(1)                                                                 |

---

### TL;DR

By sorting and always taking the **smallest available valid position**,
we ensure no two elements collide —
greedily achieving the **maximum distinct count possible**.

---
