# How & Why: LeetCode 283 - Move Zeroes

-----

## Problem Restatement

Given an integer array `nums`, your task is to move all the `0`'s to the end of it. The key constraints are:

1.  You must maintain the **relative order** of the non-zero elements.
2.  You must do this **in-place** without making a copy of the array.

-----

## How to Solve

The problem can be solved efficiently using a **two-pointer** approach. This technique is sometimes called the "snowball" method.

The idea is to partition the array into two sections. A `left` pointer will keep track of the position where the next non-zero element should be placed.

  - A `right` pointer iterates through the entire array.
  - When the `right` pointer finds a non-zero number, that number is swapped with the element at the `left` pointer's position.
  - The `left` pointer is then incremented to mark the new boundary.

### Implementation

```java
class Solution {
    public void moveZeroes(int[] nums) {
        // 'left' tracks the position for the next non-zero element.
        int left = 0;

        // 'right' iterates through the array to find non-zero elements.
        for (int right = 0; right < nums.length; right++) {
            // When a non-zero element is found...
            if (nums[right] != 0) {
                // ...swap it with the element at the 'left' position.
                int temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;
                
                // Move the 'left' pointer to the next position.
                left++;
            }
        }        
    }
}
```

-----

## Why This Works

1.  **Partitioning**: The `left` pointer effectively divides the array into two parts: the processed non-zero elements are to the left of `left`, and the unprocessed elements (a mix of zeroes and non-zeroes) are at and to the right of `left`.
2.  **Order Preservation**: Since the `right` pointer scans the array from left to right, it encounters the non-zero elements in their original relative order. By swapping them into the `left` position (which also only moves from left to right), that original relative order is preserved.
3.  **In-Place Operation**: All swaps occur within the `nums` array itself, satisfying the in-place requirement. After the `right` pointer has scanned the entire array, all non-zero elements have been moved to the front, and all the elements they were swapped with (which were all zeroes) have been moved to the back.

-----

## Complexity Analysis

  - **Time Complexity**: $O(n)$, where `n` is the number of elements in `nums`. The `right` pointer iterates through the array exactly once.
  - **Space Complexity**: $O(1)$. No extra data structures are used; the modification is done in-place.

-----

## Example Walkthrough

**Input:**

```
nums = [0, 1, 0, 3, 12]
```

**Process:**

  - **Initial**: `left = 0`, `right = 0`. `nums[0]` is 0. Do nothing.
  - `right = 1`: `nums[1]` is 1 (non-zero).
      - Swap `nums[1]` and `nums[0]`. `nums` becomes `[1, 0, 0, 3, 12]`.
      - Increment `left` to 1.
  - `right = 2`: `nums[2]` is 0. Do nothing.
  - `right = 3`: `nums[3]` is 3 (non-zero).
      - Swap `nums[3]` and `nums[1]`. `nums` becomes `[1, 3, 0, 0, 12]`.
      - Increment `left` to 2.
  - `right = 4`: `nums[4]` is 12 (non-zero).
      - Swap `nums[4]` and `nums[2]`. `nums` becomes `[1, 3, 12, 0, 0]`.
      - Increment `left` to 3.
  - The loop finishes.

**Output:**

```
[1, 3, 12, 0, 0]
```

-----

## Alternate Approaches

1.  **Two-Pass Write and Fill**:
      - Use a pointer (`lastNonZeroFoundAt`) to keep track of where to write the next non-zero number.
      - **First Pass**: Iterate through the array. If you see a non-zero number, copy it to `nums[lastNonZeroFoundAt]` and increment the pointer.
      - **Second Pass**: After the first pass, all non-zero elements are at the beginning. Iterate from `lastNonZeroFoundAt` to the end of the array, filling the remaining spots with zeroes.
      - This approach is also $O(n)$ time and $O(1)$ space but can result in fewer total write operations.

### Optimal Choice

The provided **single-pass two-pointer (swapping) solution** is considered optimal and very elegant. It solves the problem in one pass while perfectly maintaining the constraints. The two-pass method is also a valid and optimal solution.

-----

## Key Insight

The core of the problem is to separate the non-zero elements from the zero elements. The two-pointer strategy provides an efficient way to partition the array in-place, using one pointer to track the "clean" (non-zero) section and another to scan for elements to add to it.