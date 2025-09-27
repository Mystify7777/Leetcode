# How\_Why.md — Largest Rectangle in Histogram (LeetCode 84)

## ❌ Brute Force (not shown in your code, but baseline idea)

### Idea

* For each bar, expand left and right while the bars are ≥ current height.
* Compute the area as `height[i] * (right - left - 1)`.
* Keep max.

### Limitation

* Time complexity `O(n^2)` in worst case (e.g., increasing/decreasing heights).
* Too slow for large inputs.

---

## ⚡ Your Approach 1 (Precomputing nearest smaller elements)

### Idea

* For each index `i`, precompute:

  * `lessFromLeft[i]`: index of first bar to the left that’s lower than `heights[i]`.
  * `lessFromRight[i]`: index of first bar to the right that’s lower than `heights[i]`.
* Then the max rectangle with `heights[i]` as smallest height is:

  ```
  heights[i] * (lessFromRight[i] - lessFromLeft[i] - 1)
  ```

### Example Walkthrough

For `heights = [2,1,5,6,2,3]`:

* lessFromLeft = \[-1, -1, 1, 2, 1, 4]
* lessFromRight = \[1, 6, 4, 4, 6, 6]
* Compute areas → max = 10.

### Complexity

* **Time:** `O(n)` (each bar is popped once).
* **Space:** `O(n)` (for two arrays).

---

## ✅ Approach 2 (Monotonic Stack)

### Idea

* Use a stack that keeps indices of bars in increasing height order.
* While the current bar is shorter than the top of stack:

  * Pop the bar and calculate area with it as the smallest height.
  * Width is determined by the distance to the new stack top.
* This ensures each bar is pushed/popped at most once.

### Example Walkthrough

For `heights = [2,1,5,6,2,3]`:

* Push indices until `heights[i]` < stack top.
* At `i=4`, pop `6` and `5`, calculate area `10`.
* At the end, pop remaining bars.

### Complexity

* **Time:** `O(n)` (each index is pushed and popped once).
* **Space:** `O(n)` (stack).

---

## 🏆 Summary

* **Brute Force:** `O(n^2)`, too slow.
* **Precompute arrays (your 1st code):** `O(n)`, clear but uses extra space for `lessFromLeft` and `lessFromRight`.
* **Stack (your 2nd code):** `O(n)`, elegant, widely considered the canonical solution.

---
