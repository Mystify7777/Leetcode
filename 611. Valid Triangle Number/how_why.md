# How & Why – 611. Valid Triangle Number

## Problem

Given an integer array `nums`, return the number of triplets `(i, j, k)` such that:

* `nums[i] + nums[j] > nums[k]`,
* `i < j < k`.

This checks if 3 numbers can form the sides of a valid triangle.

---

## 1. Brute Force

**Idea:**
Try every triplet `(i, j, k)` and check if it forms a triangle.

**Check:**
For sorted `a ≤ b ≤ c`, the only condition needed is `a + b > c`.

**Pseudocode:**

```java
for i=0..n-1:
  for j=i+1..n-1:
    for k=j+1..n-1:
      if nums[i] + nums[j] > nums[k]:
        count++
```

**Complexity:**

* Time: O(n³) → too slow (n ≤ 1000).

---

## 2. Sorting + Binary Search

**Idea:**

* Sort the array.
* For each pair `(i, j)`, binary search the largest `k` such that `nums[i] + nums[j] > nums[k]`.

**Complexity:**

* O(n² log n).

---

## 3. Sorting + Two Pointers (Optimal)

**Your code:**

* Sort `nums`.
* Fix the largest side `nums[i]`.
* Use two pointers `left` and `right` to find pairs `(left, right)` such that:

  * If `nums[left] + nums[right] > nums[i]`, then **all pairs from left..(right-1) are valid**, because the array is sorted.
  * Otherwise, move `left++`.

**Complexity:**

* Time: O(n²)
* Space: O(1)

---

## Example Walkthrough

Input:

```
nums = [2, 2, 3, 4]
```

Step 1: Sort → `[2, 2, 3, 4]`

### i = 3 (nums[i] = 4)

* left = 0, right = 2 → nums[0]+nums[2] = 2+3=5 > 4 ✅
  → count += (2-0) = 2 (pairs: (2,3,4), (2,3,4))
  → right-- → right=1
* left=0, right=1 → nums[0]+nums[1]=4 > 4 ❌ (not strictly greater)
  → left++

Total so far = 2

### i = 2 (nums[i] = 3)

* left = 0, right = 1 → nums[0]+nums[1] = 2+2=4 > 3 ✅
  → count += (1-0) = 1 (pair: (2,2,3))
  → right--

Total = 3

### i = 1 → skip (need at least 2 smaller indices)

---

✅ Final Answer = 3
Triplets are:

* (2,3,4)
* (2,3,4)  (the other 2)
* (2,2,3)

---

📌 **Takeaway:**

* Sorting ensures only one inequality check.
* Two-pointers reduce complexity to **O(n²)**, which is optimal for this problem.

---

