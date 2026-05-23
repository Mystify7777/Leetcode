# 1752. Check if Array Is Sorted and Rotated

The array is considered sorted and rotated if it contains at most one place where the order decreases when viewed circularly.

## Idea

Scan the array once and count how many times `nums[i] > nums[(i + 1) % n]` occurs.

- `0` breaks means the array is already sorted.
- `1` break means the array is sorted and rotated.
- More than `1` break means it cannot be a valid sorted-and-rotated array.

## Why it works

A sorted rotated array can only wrap around once. Each additional drop indicates a second rotation point, which breaks the required order.

## Complexity

- Time: `O(n)`
- Space: `O(1)`
