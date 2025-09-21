# How\_Why.md: Remove Duplicates from Sorted Array

## Problem

Given a **sorted array** `nums`, remove the duplicates **in-place** such that each element appears only **once** and return the **new length**.
Do not allocate extra space for another array; you must do this by **modifying the input array in-place**.

**Example:**

```java
Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,...]
```

---

## Brute-force Approach

### Idea

* Create a **new array** to store unique elements.
* Iterate through the original array:

  * If the element is not in the new array → append.
* Copy back to original array if required.

### Example Walkthrough

* Input: `[0,0,1,1,2]`

  1. New array = `[]`
  2. `0` → not in new array → `[0]`
  3. `0` → already exists → skip
  4. `1` → not in new array → `[0,1]`
  5. `1` → skip
  6. `2` → `[0,1,2]`
* Copy back → `[0,1,2,...]`
* Return `3`

### Limitation

* **Space complexity:** O(n) (not allowed by problem constraints)
* **Time complexity:** O(n²) if using `contains()` check each time

---

## Optimized Approach (Two Pointers)

### Idea_

* Use **two pointers**:

  1. **Slow pointer `i`** tracks the position of the last unique element.
  2. **Fast pointer `j`** scans the array.
* When `nums[j] != nums[i]`, increment `i` and set `nums[i] = nums[j]`.
* At the end, `i+1` gives the number of unique elements.

### Code

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;

    int i = 0; // slow pointer

    for (int j = 1; j < nums.length; j++) {
        if (nums[j] != nums[i]) {
            i++;
            nums[i] = nums[j]; // place unique element at next position
        }
    }

    return i + 1; // number of unique elements
}
```

### Example Walkthrough_

* Input: `[0,0,1,1,2]`

  1. `i=0`, `j=1` → `nums[j]=0`, same as `nums[i]` → skip
  2. `j=2` → `nums[j]=1` ≠ `nums[i]=0` → `i=1`, `nums[1]=1` → `[0,1,...]`
  3. `j=3` → `nums[j]=1` = `nums[i]` → skip
  4. `j=4` → `nums[j]=2` ≠ `nums[i]=1` → `i=2`, `nums[2]=2` → `[0,1,2,...]`
* Return `3` → unique elements `[0,1,2]`

### Advantages

* **Time complexity:** O(n)
* **Space complexity:** O(1) (in-place)
* Efficient for **sorted arrays** without extra memory.

---
