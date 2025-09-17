# How_Why.md

## Problem

Given an array of positive integers `nums` and a positive integer `target`,  
return the **minimal length of a subarray** whose sum is **greater than or equal to** `target`.  
If no such subarray exists, return `0`.

---

## How (Step-by-step Solution)

### Approach: Sliding Window

1. **Initialize Variables**
   - `minLen = Integer.MAX_VALUE` → stores smallest valid subarray length.
   - `left = 0` → left pointer of sliding window.
   - `curSum = 0` → sum of current window.

2. **Expand Window (Right Pointer `right`)**
   - Add `nums[right]` to `curSum`.

3. **Shrink Window (Left Pointer `left`)**
   - While `curSum >= target`:
     - Update `minLen = Math.min(minLen, right - left + 1)`.
     - Subtract `nums[left]` from `curSum`.
     - Move `left++` forward.

4. **Final Result**
   - If `minLen` was updated, return it.
   - Otherwise return `0`.

---

## Why (Reasoning)

- The subarray must be **contiguous** → use **sliding window**.
- Expanding the right pointer ensures we include enough sum.
- Shrinking from the left ensures the window is **minimal**.
- This guarantees the smallest valid subarray length.

---

## Complexity Analysis

- **Time Complexity**: O(n), where `n = nums.length`.  
  Each element is processed at most twice (once when added, once when removed).  
- **Space Complexity**: O(1), only variables used.

---

## Example Walkthrough

### Input

`target = 7`
`nums = [2,3,1,2,4,3]`

### Steps

- right=0 → curSum=2 → not valid  
- right=1 → curSum=5 → not valid  
- right=2 → curSum=6 → not valid  
- right=3 → curSum=8 → valid → minLen=4 (`[2,3,1,2]`)  
- shrink left → curSum=6 → stop  
- right=4 → curSum=10 → valid → minLen=3 (`[1,2,4]`)  
- shrink left → curSum=7 → valid → minLen=2 (`[4,3]`)  
- shrink left → curSum=3 → stop  
- right=5 → curSum=6 → not valid  

Final Answer = **2**

---

## Alternate Approaches

1. **Brute Force**: Try all subarrays → O(n²). Not efficient.  
2. **Sliding Window (Optimal)**: O(n) → efficient and clean.  
3. **Prefix Sum + Binary Search**: O(n log n), works but slower than sliding window.

✅ **Optimal Method**: Sliding Window with Two Pointers.
