# How_Why.md — Find Sum of Array Product of Magical Sequences (LeetCode #3539)

## 🧩 Problem Statement

You are given three inputs:

- `m`: the sequence length,
- `k`: the required number of set bits (1s),
- `nums`: an integer array.

A **magical sequence** is defined such that:

- Each element in `nums` can be used from 0 to `m` times.
- We compute combinations of counts, and every combination contributes to the total “magical sum” based on binary representations.

Your task is to find the **sum of all valid products of magical sequences** that have exactly `k` set bits when their counts are added in binary.

Since the answer may be very large, return it modulo **1e9 + 7**.

---

## 🧠 Brute Force Approach

### 💡 Idea

Enumerate **every possible way** of distributing counts (0–m) across `nums`.

1. For each configuration of counts, calculate:
   - The total number of times each number is used.
   - The binary addition of all counts.
2. If the final count has exactly `k` set bits → include its product contribution.

### ⚙️ Steps

- Use nested loops (or recursion) to generate all possible `(cnt₁, cnt₂, ..., cntₙ)` combinations.
- Check binary popcount after addition.
- Multiply corresponding powers and sum up.

### 🚨 Limitation

This explodes combinatorially:

```java

O((m+1)^n)

```

which is completely infeasible when both `m` and `n` are large (up to 50 or 100).

---

## ⚡ Optimized Dynamic Programming Approach

### 💡 Key Insight

We can use **multi-dimensional dynamic programming (DP)** to track:

1. The number of set bits accumulated so far (`bits`),
2. The carry from binary addition (`carry`),
3. The number of elements already chosen (`chosen`).

Each DP transition simulates binary addition bit by bit.

---

## 🧮 Step-by-Step Breakdown

### 🧩 Step 1 — Precompute Combinations (`C[n][r]`)

We need combinations to determine how many ways we can pick certain counts:

```java

C[i][j] = C[i-1][j-1] + C[i-1][j]

```

This helps handle multinomial choices for count assignments efficiently.

---

### 🧩 Step 2 — Precompute Powers

For every number `nums[i]`, compute:

```java

pow[i][cnt] = (nums[i])^cnt % MOD

```

This lets us quickly compute each element’s contribution for any repetition count.

---

### 🧩 Step 3 — DP Definition

We use a **4D DP**:

```java

dp[pos][bits][carry][chosen]

```

- `pos` → current index in `nums`
- `bits` → number of set bits accumulated so far
- `carry` → current carryover from binary addition
- `chosen` → total number of elements used so far

**Base Case:**

```java

dp[0][0][0][0] = 1

```

---

### 🧩 Step 4 — Transition

For each possible repetition count `cnt` for `nums[pos]`, we update transitions:

```java
int total = carry + cnt;
int new_bits = bits + (total & 1);
int new_carry = total >> 1;
````

We add contributions as:

```java
dp[pos+1][new_bits][new_carry][chosen+cnt] +=
    dp[pos][bits][carry][chosen] *
    C[remaining][cnt] *
    pow[pos][cnt]
```

All mod `1e9 + 7`.

---

### 🧩 Step 5 — Final Accumulation

At the end (`pos == n`):

* For all valid carries, compute their **remaining set bits** (`Integer.bitCount(carry)`).
* If total equals `k`, add to the final answer:

```java
res = (res + dp[n][k - carryBits][carry][m]) % MOD
```

---

## 🧮 Example Walkthrough (Simplified)

```java
m = 2, k = 1, nums = [2, 3]
```

* Combinations possible:

  * Choose counts for `2` and `3` such that total bits = 1
* Each combination contributes via:

  * Count-based binomial coefficient
  * Power term (`2^cnt₁ * 3^cnt₂`)
* DP efficiently computes valid combinations respecting carry and bit constraints.

---

## 💻 Final Code

```java
class Solution {
    public int magicalSum(int m, int k, int[] nums) {
        int MOD = (int) (1e9 + 7);
        int n = nums.length;

        int[][] C = new int[m + 1][m + 1];
        for (int i = 0; i <= m; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                C[i][j] = (C[i-1][j-1] + C[i-1][j]) % MOD;
            }
        }

        int[][] pow = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            pow[i][0] = 1;
            for (int cnt = 1; cnt <= m; cnt++) {
                pow[i][cnt] = (int)((long)pow[i][cnt-1] * nums[i] % MOD);
            }
        }

        int[][][][] dp = new int[n + 1][k + 1][m + 1][m + 1];
        dp[0][0][0][0] = 1;

        for (int pos = 0; pos < n; pos++) {
            for (int bits = 0; bits <= k; bits++) {
                for (int carry = 0; carry <= m; carry++) {
                    for (int chosen = 0; chosen <= m; chosen++) {
                        if (dp[pos][bits][carry][chosen] == 0) continue;
                        int remaining = m - chosen;

                        for (int cnt = 0; cnt <= remaining; cnt++) {
                            int total = carry + cnt;
                            int new_bits = bits + (total & 1);
                            int new_carry = total >> 1;

                            if (new_bits <= k && new_carry <= m) {
                                long add = (long)dp[pos][bits][carry][chosen] *
                                           C[remaining][cnt] % MOD *
                                           pow[pos][cnt] % MOD;
                                dp[pos+1][new_bits][new_carry][chosen+cnt] =
                                    (dp[pos+1][new_bits][new_carry][chosen+cnt] + (int)add) % MOD;
                            }
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int carry = 0; carry <= m; carry++) {
            int carryBits = Integer.bitCount(carry);
            if (carryBits <= k) {
                res = (res + dp[n][k - carryBits][carry][m]) % MOD;
            }
        }

        return res;
    }
}
```

---

## 🧾 Complexity Analysis

| Type          | Complexity                 | Explanation                   |
| ------------- | -------------------------- | ----------------------------- |
| Time          | O(n × k × m² × m)          | Four-dimensional DP traversal |
| Space         | O(n × k × m²)              | Multi-layer DP array          |
| Optimizations | Precomputed C & pow tables | Avoids recomputation          |

---

## ✅ Summary

| Aspect       | Description                                                     |
| ------------ | --------------------------------------------------------------- |
| 🔧 Approach  | 4D DP simulating binary addition                                |
| 💡 Key Trick | Carry + bit tracking per step                                   |
| 🧮 Core Idea | Combine combinatorics + digit DP                                |
| ⏱️ Time      | O(n × k × m³)                                                   |
| 💾 Space     | O(n × k × m²)                                                   |
| 🧙‍♂️ Result | Elegant high-dimensional DP handling complex binary constraints |

---

### TL;DR

This problem is a **fusion of combinatorics and digit DP**.
We track binary carries and bit counts simultaneously using a multi-dimensional DP table, efficiently building the total magical sum that satisfies the `k`-bit condition.

---
