# How_Why.md – Coin Change (322)

---

## 🔹 Problem

You are given an integer array `coins` representing coin denominations, and an integer `amount` representing a total.
Return the **minimum number of coins** that make up the amount. If it’s not possible, return `-1`.

---

## 🔸 Brute Force (Naive Recursion)

### Idea

* For each coin, subtract it from the amount and recurse.
* Base cases:

  * If `amount == 0` → 0 coins needed.
  * If `amount < 0` → invalid path.
* Try all paths, take the minimum.

### Complexity

* **Time:** Exponential (`O(n^amount)`) — tries every combination.
* **Space:** `O(amount)` recursion depth.

### Example Walkthrough (`coins = [1,2,5], amount = 11`)

* Try `11-1 = 10`, then recurse.
* Try `11-2 = 9`, recurse.
* Try `11-5 = 6`, recurse.
* This branches explosively.

This works but is **too slow** for large amounts.

---

## 🔸 Your Math + GCD + Pruning Approach

### Idea

* Sort coins and use the smallest one (`minCoin`) to set bounds.
* Check feasibility via **GCD**: if `amount % gcd(coins) != 0`, no solution exists.
* Use **backtracking with pruning**:

  * Bound search between `minVal` and `maxVal`.
  * Use recursive `findCombination` to try limited combinations.

### Complexity

* Depends heavily on input — much faster with pruning, but **worst case is still exponential**.
* Strong **mathematical pruning** makes it competitive on tricky inputs.

### Example Walkthrough (`coins = [1,3,4], amount = 6`)

* GCD check → valid.
* Try combinations bounded between min/max possible coins.
* Quickly finds `3+3` or `4+1+1`.
* Returns `2`.

This is a **clever competitive-programming style optimization**, but not the standard LC solution.

---

## 🔸 Optimized DP (Tabulation)

### Idea

* Classic **bottom-up dynamic programming**.
* `dp[i] = min coins needed for amount i`.
* Transition:

  $$
  dp[i] = \min(dp[i], dp[i - c] + 1) \quad \text{for each coin } c
  $$

### Complexity

* **Time:** `O(n * amount)` (where `n = coins.length`).
* **Space:** `O(amount)`.

### Example Walkthrough (`coins = [1,2,5], amount = 11`)

* Start: `dp[0] = 0`.
* Fill table:

  * `dp[1] = 1` (one `1`).
  * `dp[2] = 1` (one `2`).
  * `dp[3] = 2` (either `1+2` or `1+1+1`).
  * …
  * `dp[11] = 3` (`5+5+1`).
* Answer = `3`.

This is the **most reliable and accepted solution**.

---

## 🔹 Final Takeaways

* **Naive recursion**: Good for explaining the problem, but too slow.
* **Your math-based pruning solution**: Very smart, uses number theory + pruning; competitive but not the typical approach.
* **DP (tabulation)**: Best balance of efficiency and simplicity → go-to solution for interviews and LeetCode.

---
