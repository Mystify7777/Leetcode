# How_Why.md – Longest Increasing Subsequence (300)

## ✅ Approach: Patience Sorting / Binary Search

### Idea

* Maintain an array `tails` where:

  * `tails[i]` is the **smallest possible tail** of an increasing subsequence of length `i+1`.

* For each number `x` in `nums`:

  1. Use **binary search** on `tails[0..size)` to find the first element ≥ `x`.
  2. Replace it with `x` (smaller tail → better chance to extend subsequences).
  3. If `x` is larger than all tails, append it (`size++`).

* At the end, `size` = length of LIS.

### Key Insight

* You don’t actually maintain the subsequence, just **the smallest tails** for each length.
* This allows **O(n log n)** time complexity instead of O(n²).

---

### Example Walkthrough

#### Input

```
nums = [10, 9, 2, 5, 3, 7, 101, 18]
```

#### Steps

1. **x = 10**

   * `tails = []`
   * Binary search → i = 0
   * `tails[0] = 10`
   * `size = 1`
   * `tails = [10]`

2. **x = 9**

   * Binary search → first element ≥ 9 → i = 0
   * Replace `tails[0] = 9`
   * `tails = [9]`
   * `size = 1`

3. **x = 2**

   * Binary search → i = 0
   * Replace `tails[0] = 2`
   * `tails = [2]`
   * `size = 1`

4. **x = 5**

   * Binary search → first ≥ 5 → i = 1
   * Append `tails[1] = 5`
   * `tails = [2, 5]`
   * `size = 2`

5. **x = 3**

   * Binary search → first ≥ 3 → i = 1
   * Replace `tails[1] = 3`
   * `tails = [2, 3]`
   * `size = 2`

6. **x = 7**

   * Binary search → first ≥ 7 → i = 2
   * Append `tails[2] = 7`
   * `tails = [2, 3, 7]`
   * `size = 3`

7. **x = 101**

   * Binary search → i = 3
   * Append `tails[3] = 101`
   * `tails = [2, 3, 7, 101]`
   * `size = 4`

8. **x = 18**

   * Binary search → first ≥ 18 → i = 3
   * Replace `tails[3] = 18`
   * `tails = [2, 3, 7, 18]`
   * `size = 4`

---

#### Output

```
Length of LIS = 4
```

* One possible subsequence: `[2, 3, 7, 18]`.

---

### Complexity

* **Time**: O(n log n) → binary search for each of n elements.
* **Space**: O(n) → `tails` array.

---
