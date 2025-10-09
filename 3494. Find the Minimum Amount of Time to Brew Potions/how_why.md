# How_Why.md — Find the Minimum Amount of Time to Brew Potions (LeetCode #3494)

## 🧩 Problem Statement

You are given:

- Two integer arrays:  
  - `skill[i]` → the skill multiplier of the i-th potion brewer  
  - `mana[j]` → the mana cost of the j-th ingredient

You need to find the **minimum total amount of time** required to brew all the potions using all ingredients in sequence, such that:

- Each potion’s brewing time depends on the brewer’s skill and the ingredient’s mana.
- After each ingredient is used, you may adjust the brewing order to minimize future total time.

Return the **minimum total brewing time** possible.

---

## 🧠 Brute Force Idea

### 💡 Naive Thought

Try every possible order of assigning brewers (`skill[i]`) to ingredients (`mana[j]`).

For each ingredient:

1. Choose the brewer order.
2. Compute total brewing time:

    ```java

    totalTime = Σ (skill[i] * mana[j])

    ```

3. Keep the minimum.

### 🚨 Problem

- There are `n!` possible assignments for `n` brewers.
- If both `n` and `m` are large (up to 1000), the approach explodes exponentially.

**Time Complexity:** `O(m × n!)` — **impossible** to compute.

---

## ⚡ Optimized Approach — Dynamic Programming with Running Max

### 💡 Key Insight

We can reuse previous computations:

- Maintain `done[i]` = minimum time after brewing `i` potions.
- Iterate over all ingredients, updating the brewing times efficiently.

At every step, we use the relationship:

```java

done[i+1] = max(done[i+1], done[i]) + mana[j] * skill[i]

```

This ensures:

- We accumulate the time contribution of the current ingredient.
- We propagate the best possible prior brewing order.

Then, the *reverse loop*:

```java

for (i = n-1; i > 0; --i)
done[i] = done[i+1] - mana[j] * skill[i];

```

is used to “adjust” the brewing order to minimize upcoming steps — effectively balancing accumulated skill and mana contributions.

---

### 🧮 Step-by-Step Example

Let:

```java

skill = [1, 3]
mana = [2, 5]

```

#### Initial

```java

done = [0, 0, 0]   // index 0..n

```

#### First ingredient (mana = 2)

- For i=0 → done[1] = max(done[1], done[0]) + 2*1 = 2
- For i=1 → done[2] = max(done[2], done[1]) + 2*3 = 2 + 6 = 8  
→ done = [0, 2, 8]

Reverse pass:

- i=1 → done[1] = done[2] - 2*3 = 8 - 6 = 2

✅ after first round: `[0, 2, 8]`

#### Second ingredient (mana = 5)

- For i=0 → done[1] = max(done[1], done[0]) + 5*1 = 5
- For i=1 → done[2] = max(done[2], done[1]) + 5*3 = 8 + 15 = 23  
→ done = [0, 5, 23]

Reverse pass:

- i=1 → done[1] = done[2] - 5*3 = 23 - 15 = 8

✅ Final `done = [0, 8, 23]`

**Result:** `done[n] = done[2] = 23`

---

## 🧩 Code Implementation

```java
class Solution {
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length, m = mana.length;
        long[] done = new long[n + 1];
        
        for (int j = 0; j < m; ++j) {
            for (int i = 0; i < n; ++i) {
                done[i + 1] = Math.max(done[i + 1], done[i]) + (long) mana[j] * skill[i];
            }
            for (int i = n - 1; i > 0; --i) {
                done[i] = done[i + 1] - (long) mana[j] * skill[i];
            }
        }
        return done[n];
    }
}
````

---

## ⏱️ Complexity Analysis

| Operation                | Time         | Space    | Explanation            |
| ------------------------ | ------------ | -------- | ---------------------- |
| Outer loop (ingredients) | O(m)         | —        | One per ingredient     |
| Inner forward loop       | O(n)         | —        | Update brewing time    |
| Inner reverse loop       | O(n)         | —        | Adjust for next step   |
| **Total**                | **O(n × m)** | **O(n)** | Efficient and scalable |

---

## 🔍 Why It Works

* `done[i]` tracks the *best possible brewing time* after i potions.
* The **forward pass** accumulates time, ensuring no overlapping brews.
* The **reverse pass** rebalances contributions to prepare for future ingredients.
* By always considering the maximum so far (`max(done[i+1], done[i])`), we guarantee non-decreasing brew time order — a key to minimizing total.

This is essentially a **1D DP simulation** of optimal brewing progression.

---

## ✅ Final Verdict

| Aspect       | Description                                    |
| ------------ | ---------------------------------------------- |
| 🔧 Approach  | Dynamic Programming (with order propagation)   |
| ⏱️ Time      | O(n × m)                                       |
| 💾 Space     | O(n)                                           |
| ⚡ Efficiency | Excellent for large inputs                    |
| 💡 Trick     | Reverse pass equalizes brewing sequence impact |

✅ **Result:** Accepted — elegant dynamic programming that balances cumulative effects efficiently.

---
