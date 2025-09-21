# How\_Why.md: Find Peak Element

## Problem

Given an array `nums` where `nums[i] != nums[i+1]`, find **any peak element**.
A peak element is an element strictly greater than its neighbors. Return **its index**.

**Example:**

```java
Input: nums = [1,2,3,1]
Output: 2  // nums[2] = 3 is a peak
```

---

## Brute-force Approach

### Idea

* Iterate through the array from left to right.
* For each element `nums[i]`, check:

  ```java
  if nums[i] > nums[i-1] && nums[i] > nums[i+1] → return i
  ```
  
* Handle boundaries separately:

  * `nums[0] > nums[1]` → return 0
  * `nums[n-1] > nums[n-2]` → return n-1

### Example Walkthrough

* Input: `[1,2,3,1]`

  1. `i=0`: 1 > ? (boundary) → no
  2. `i=1`: 2 > 1 && 2 > 3 → no
  3. `i=2`: 3 > 2 && 3 > 1 → yes → return 2

### Limitation

* **Time complexity:** O(n) — linear scan
* **Inefficient** for very large arrays

---

## Optimized Approach (Binary Search)

### Idea_

* Use **binary search** to exploit the property:

  * If `nums[mid] < nums[mid+1]` → there is a peak on the **right**
  * If `nums[mid] < nums[mid-1]` → there is a peak on the **left**
  * If `nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1]` → peak found

* This works because every array **must have a peak** due to boundary elements or local increases/decreases.

### Steps

1. Check boundaries (`0` and `n-1`) as peaks.
2. Initialize `start = 1`, `end = n-2` (skip boundaries).
3. While `start <= end`:

   * `mid = start + (end - start)/2`
   * If `nums[mid]` is a peak → return `mid`
   * Else if `nums[mid] < nums[mid-1]` → search **left** (`end = mid-1`)
   * Else if `nums[mid] < nums[mid+1]` → search **right** (`start = mid+1`)

### Code

```java
public int findPeakElement(int[] nums) {
    if(nums.length == 1) return 0;

    int n = nums.length;
    if(nums[0] > nums[1]) return 0;
    if(nums[n-1] > nums[n-2]) return n-1;

    int start = 1, end = n-2;
    while(start <= end) {
        int mid = start + (end - start)/2;
        if(nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1]) return mid;
        else if(nums[mid] < nums[mid-1]) end = mid - 1;
        else start = mid + 1;
    }
    return -1; // never reached
}
```

### Example Walkthrough_

* Input: `[1,2,3,1]`

  1. Check boundaries: 1 < 2, 1 < 3 → continue
  2. `start=1, end=2, mid=1` → nums\[1]=2

     * nums\[1] > 1? yes
     * nums\[1] > 3? no → nums\[1] < nums\[2] → move right
     * `start=2`
  3. `mid=2` → nums\[2]=3, check neighbors: 3 > 2 && 3 > 1 → peak found → return 2

### Advantages

* **Time complexity:** O(log n) — binary search
* **Space complexity:** O(1) — no extra storage
* Efficient even for very large arrays

---
