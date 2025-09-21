# How\_Why.md: Search in Rotated Sorted Array

## Problem

Given a **rotated sorted array** `nums` (ascending order, rotated at some pivot), find the **index** of a given `target`.
Return `-1` if the target does not exist.

**Example:**

```java
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
```

---

## Brute-force Approach

### Idea

* Iterate through the array from left to right.
* Compare each element with `target`.
* If found, return index; otherwise return `-1`.

### Example Walkthrough

* Input: `[4,5,6,7,0,1,2]`, target `0`

  1. Check `4` → not 0
  2. Check `5` → not 0
  3. Check `6` → not 0
  4. Check `7` → not 0
  5. Check `0` → found → index `4`

### Limitation

* **Time complexity:** O(n) — inefficient for large arrays
* Does not utilize **sorted/rotated property** of array

---

## Optimized Approach (Binary Search on Rotated Array)

### Idea_

* Use **modified binary search**:

  1. Identify which half (left or right) of `mid` is **sorted**.
  2. Check if the target lies in that half:

     * If yes → search within that half
     * If no → search the other half
* Repeat until `low > high`.

### Steps

1. `mid = (low + high)/2`
2. If `nums[mid] == target` → return `mid`
3. Check which half is sorted:

   * **Left sorted:** `nums[low] <= nums[mid]`
   * **Right sorted:** else
4. Adjust `low` or `high` depending on where target can lie.

### Code

```java
public int search(int[] nums, int target) {
    int low = 0, high = nums.length - 1;

    while (low <= high) {
        int mid = (low + high) / 2;

        if (nums[mid] == target) return mid;

        if (nums[low] <= nums[mid]) { // Left half sorted
            if (nums[low] <= target && target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        } else { // Right half sorted
            if (nums[mid] < target && target <= nums[high]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
    }

    return -1;
}
```

### Example Walkthrough_

* Input: `[4,5,6,7,0,1,2]`, target `0`

  1. `low=0, high=6, mid=3` → `nums[3]=7`

     * Left `[4,5,6,7]` sorted
     * Target `0` not in `[4,7]` → search right half → `low=4`
  2. `low=4, high=6, mid=5` → `nums[5]=1`

     * Left `[0,1]` sorted
     * Target `0` in `[0,1]` → search left half → `high=4`
  3. `low=4, high=4, mid=4` → `nums[4]=0` → found → return `4`

### Advantages

* **Time complexity:** O(log n)
* **Space complexity:** O(1)
* Efficiently uses the **rotated sorted property** of the array

---
