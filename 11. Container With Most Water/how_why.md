
# How & Why — Problem 11: Container With Most Water

## Problem Restatement

You are given an array `height[]` where each element represents the height of a vertical line drawn at index `i`. The task is to find two lines such that together with the x-axis, they form a container that can hold the maximum amount of water.

Formally:

$$
\text{Area} = (j - i) \times \min(height[i], height[j])
$$

where `0 <= i < j < n`.

---

## 1. Brute Force Approach

### Idea

Check **all possible pairs** `(i, j)` of lines, compute the area, and track the maximum.

### Steps

1. Loop `i` from `0` to `n-1`.
2. Loop `j` from `i+1` to `n-1`.
3. Calculate area = `(j - i) * min(height[i], height[j])`.
4. Keep track of the maximum area found.

### Complexity

* **Time:** $O(n^2)$ — double loop over all pairs.
* **Space:** $O(1)$.

### Example

Input: `height = [1,8,6,2,5,4,8,3,7]`

* Pair `(0,8)` → Area = `(8-0) * min(1,7) = 8`.
* Pair `(1,8)` → Area = `(8-1) * min(8,7) = 49`.
* Pair `(2,8)` → Area = `(8-2) * min(6,7) = 36`.
* … check all pairs.

**Answer:** 49.

**Limitation:** Too slow for large `n` (up to 10^5).

---

## 2. Two-Pointer Approach (Your Code)

### Idea_

Use **two pointers** — one at the start, one at the end.

* At each step, compute the area.
* Move the pointer pointing to the **shorter line**, since it limits the area.
* Continue until the two pointers meet.

### Why It Works

* Moving the taller line doesn’t increase area (width decreases, height can’t improve beyond the shorter one).
* So only moving the shorter line can potentially improve area.

### Steps_

1. Initialize `left = 0`, `right = n-1`.
2. While `left < right`:

   * Calculate area.
   * Update max area.
   * Move `left++` if `height[left] < height[right]`, else `right--`.
3. Return max area.

### Complexity_

* **Time:** $O(n)$ — each index visited at most once.
* **Space:** $O(1)$.

---

## 3. Step-by-Step Walkthrough with Diagram

Input: `height = [1,8,6,2,5,4,8,3,7]`

```text
Index:    0  1  2  3  4  5  6  7  8
Height:   1  8  6  2  5  4  8  3  7
```

* **Step 1**: `left=0`, `right=8`

```text
1 |                         | 7
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
  |                         |
----------------------------------
```

Area = (8-0) \* min(1,7) = 8 → Move `left` (since 1 < 7).

---

* **Step 2**: `left=1`, `right=8`

```text
   8 |                       | 7
     |                       |
     |                       |
     |                       |
     |                       |
     |                       |
----------------------------------
```

Area = (8-1) \* min(8,7) = 49 → Max updated. Move `right` (since 8 > 7).

---

* **Step 3**: `left=1`, `right=7`

```text
   8 |                 | 3
     |                 |
     |                 |
----------------------------------
```

Area = (7-1) \* min(8,3) = 18 → Max still 49. Move `right`.

---

* Continue moving pointers inward…
  Final max area = **49**.

---

## 4. Optimized Version

The two-pointer method **is already optimal**:

* Any solution must examine pairs in some systematic way.
* We reduce complexity from $O(n^2)$ → $O(n)$ with no extra space.

✅ **Best Method:** Two-Pointer Approach
✅ **Time Complexity:** $O(n)$
✅ **Space Complexity:** $O(1)$

---
