# How_Why.md – Maximum Subarray (53)

---

## 🔹 Problem

Given an integer array `nums`, find the **contiguous subarray** (containing at least one number) which has the largest sum, and return that sum.

---

## 🔸 Brute Force (Naive)

### Idea

* Generate all possible subarrays.
* Compute their sums, and track the maximum.

### Complexity

* **Time:** `O(n²)` (or `O(n³)` if sum recomputed each time).
* **Space:** `O(1)`.

### Example Walkthrough (`nums = [-2,1,-3,4,-1,2,1,-5,4]`)

* Check `[4,-1,2,1]` → sum = `6`.
* That’s the maximum.
* Works, but **too slow** for large arrays.

---

## 🔸 Kadane’s Algorithm (Your Approach)

### Idea

* Keep a running sum `sum`.
* If `sum` drops below `0`, reset it (start fresh subarray).
* Track the maximum sum found so far.

### Complexity

* **Time:** `O(n)` → single pass.
* **Space:** `O(1)`.

### Example Walkthrough

`nums = [-2,1,-3,4,-1,2,1,-5,4]`

* Start: `sum=0, max=-∞`.
* `-2 → sum=-2 → reset to 0, max=-2`.
* `1 → sum=1, max=1`.
* `-3 → sum=-2 → reset, max=1`.
* `4 → sum=4, max=4`.
* `-1 → sum=3, max=4`.
* `2 → sum=5, max=5`.
* `1 → sum=6, max=6`.
* `-5 → sum=1, max=6`.
* `4 → sum=5, max=6`.

Answer = **6**.

---

## 🔸 DP Variation (Alternative View)

### Idea

* Define `dp[i] = max subarray sum ending at index i`.
* Transition:

  $$
  dp[i] = \max(nums[i], nums[i] + dp[i-1])
  $$
* Answer = `max(dp[i])` over all `i`.

### Complexity

* **Time:** `O(n)`
* **Space:** `O(n)` (can be optimized to `O(1)` by rolling variables).

---

## 🔹 Final Takeaways

* **Brute force** is intuitive but slow.
* **Kadane’s Algorithm (your code)** is the cleanest & fastest solution.
* **DP formulation** is just a rephrasing of Kadane’s.

---
