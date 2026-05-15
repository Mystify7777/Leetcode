# 153. Find Minimum in Rotated Sorted Array

The array is a sorted array that has been rotated. The minimum element is the point where the order breaks.

## Idea

Use binary search against the last element:
- If `nums[mid] > last`, then the minimum must be to the right of `mid`.
- Otherwise, the minimum is at `mid` or to the left of it.

This works because all elements in the left rotated section are greater than the last element, while the minimum and everything after it are less than or equal to the last element.

## Why it is correct

The search space always keeps the minimum inside `[left, right]`.
Each comparison removes half of the range, so the algorithm converges to the first position whose value is not greater than the last element, which is the minimum.

## Complexity

- Time: `O(log n)`
- Space: `O(1)`
