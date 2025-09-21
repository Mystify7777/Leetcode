
# How & Why — Problem 33: Search in Rotated Sorted Array

## Problem Restatement

We are given a **rotated sorted array** `nums` (originally sorted in ascending order, then rotated at some pivot). We need to search for a target value and return its index. If not found, return `-1`.

* Example:

  * Input: `nums = [4,5,6,7,0,1,2], target = 0`
  * Output: `4`

---

## 1. Brute Force Approach

### Idea

Simply check each element in the array until we find the target.

### Steps

1. Loop through `nums`.
2. If `nums[i] == target`, return `i`.
3. If not found, return `-1`.

### Complexity

* **Time:** $O(n)$
* **Space:** $O(1)$

### Example

Input: `nums = [4,5,6,7,0,1,2], target = 0`

* Check 4 → no
* Check 5 → no
* Check 6 → no
* Check 7 → no
* Check 0 → found at index 4

Answer = `4`.

**Limitation:** Linear scan is too slow for large arrays (up to 10^5 elements).

---

## 2. Binary Search in Rotated Array (Your Code)

### Idea_

Even though the array is rotated, **one half is always sorted**.

* If target lies in the sorted half → move into that half.
* Otherwise, search the other half.

This allows us to still use binary search.

### Steps_

1. Initialize `low=0`, `high=n-1`.
2. While `low <= high`:

   * Find `mid = (low+high)/2`.
   * If `nums[mid] == target`, return `mid`.
   * If `nums[low] <= nums[mid]`, then **left half is sorted**:

     * If target lies between `nums[low]` and `nums[mid]`, search left.
     * Else search right.
   * Otherwise, **right half is sorted**:

     * If target lies between `nums[mid]` and `nums[high]`, search right.
     * Else search left.
3. If not found, return `-1`.

### Complexity_

* **Time:** $O(\log n)$
* **Space:** $O(1)$

---

## 3. Walkthrough Example

Input: `nums = [4,5,6,7,0,1,2], target = 0`

```java
Initial: low=0, high=6, mid=3 → nums[3]=7
nums[low]=4 <= nums[mid]=7 → Left half [4,5,6,7] is sorted
Target=0 not in [4..7] → search right → low=4
```

```java
Now: low=4, high=6, mid=5 → nums[5]=1
nums[low]=0 <= nums[mid]=1 → Left half [0,1] is sorted
Target=0 is in [0..1] → search left → high=4
```

```java
Now: low=4, high=4, mid=4 → nums[4]=0 → Found!
```

Answer = **index 4**.

---

## 4. Optimized Version

This binary search variation is already **optimal**:

* Brute force takes $O(n)$.
* Regular binary search doesn’t work directly due to rotation.
* This modified binary search reduces to $O(\log n)$, which is the best possible for searching in a sorted/rotated array.

✅ **Best Method:** Modified Binary Search
✅ **Time Complexity:** $O(\log n)$
✅ **Space Complexity:** $O(1)$

---
