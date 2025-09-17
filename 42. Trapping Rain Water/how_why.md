# How_Why.md

## Problem

Given `n` non-negative integers representing the elevation map where the width of each bar is 1, compute how much water it can trap after raining.

---

## How (Step-by-step Solution)

### Approach: Two-Pointer Method

1. Initialize two pointers:
   - `left = 0`
   - `right = n - 1`
   - `leftMax = height[left]`
   - `rightMax = height[right]`

2. While `left < right`:
   - Compare `leftMax` and `rightMax`.
   - If `leftMax < rightMax`:
     - Move `left` pointer one step right.
     - Update `leftMax = max(leftMax, height[left])`.
     - Water trapped at `left` = `leftMax - height[left]`.
   - Else:
     - Move `right` pointer one step left.
     - Update `rightMax = max(rightMax, height[right])`.
     - Water trapped at `right` = `rightMax - height[right]`.

3. Continue until pointers meet.  
4. Return total water trapped.

---

## Why (Reasoning)

- At each step, the **side with the smaller max boundary** determines the water trapping capacity.
- Water above a bar depends on the **minimum of the tallest bars to its left and right**.
- By keeping track of `leftMax` and `rightMax`, and always moving the smaller side, we ensure correctness:
  - If `leftMax < rightMax`, then water trapped depends only on `leftMax`, because `rightMax` is guaranteed to be taller.

---

## Complexity Analysis

- **Time Complexity**: O(n), each bar is visited once.
- **Space Complexity**: O(1), only a few variables are used.

---

## Example Walkthrough

### Input

`height = [0,1,0,2,1,0,1,3,2,1,2,1]`

### Steps

- Start: left=0, right=11, leftMax=0, rightMax=1  
- Move left/right pointers based on comparisons...  
- Accumulate trapped water at each step.  

### Total water trapped = 6

---

## Alternate Approaches

1. **Brute Force**:
   - For each bar, compute max height to its left and right, then calculate trapped water.
   - Time: O(n²), Space: O(1).

2. **Precomputed Arrays**:
   - Precompute `leftMax[i]` and `rightMax[i]` for every index.
   - Then water at index `i` = `min(leftMax[i], rightMax[i]) - height[i]`.
   - Time: O(n), Space: O(n).

3. **Stack-based Approach**:
   - Use a monotonic stack to compute water trapped when encountering a higher bar.
   - Time: O(n), Space: O(n).

✅ **Optimal Method**: Two-pointer approach (O(n) time, O(1) space).
