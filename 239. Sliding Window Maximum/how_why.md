# How_Why.md

## Problem

You are given an array `nums` and an integer `k`, representing a sliding window of size `k` moving from left to right.  
Return the maximum value in each window.

---

## How (Step-by-step Solution)

### Approach: Monotonic Deque

1. Use a **deque (double-ended queue)** to store indices of useful elements.
   - The deque is maintained in **decreasing order of values**.
   - The front of the deque always represents the index of the **maximum** element in the current window.

2. Iterate through the array:
   - **Step 1: Remove out-of-window indices**  
     If the index at the front of deque is out of the current window (`i - k`), remove it.
   - **Step 2: Maintain decreasing order**  
     While the element at the back of the deque is less than the current element `nums[i]`, remove it (since it's useless).
   - **Step 3: Insert current index**  
     Push `i` at the back of the deque.
   - **Step 4: Record result**  
     If the current index `i >= k-1`, add `nums[deque.front()]` (the maximum) to the result array.

---

## Why (Reasoning)

- A brute-force approach would check each window in O(k) time → **O(nk)** total.  
- With a **deque**, each element is pushed and popped at most once.  
- This reduces the complexity to **O(n)**.

---

## Complexity Analysis

- **Time Complexity**: O(n), since each element is added/removed at most once from deque.  
- **Space Complexity**: O(k), deque can hold at most `k` indices.

---

## Example Walkthrough

### Input

```text
nums = [1,3,-1,-3,5,3,6,7], k = 3
```

### Execution

- Window [1,3,-1] → max = 3

- Window [3,-1,-3] → max = 3

- Window [-1,-3,5] → max = 5

- Window [-3,5,3] → max = 5

- Window [5,3,6] → max = 6

- Window [3,6,7] → max = 7

### Output

`[3,3,5,5,6,7]
`

---

### Alternate Approaches

1. Max Heap (Priority Queue)

    - Push elements into a max-heap with their indices.

    - Pop elements that are out of the window.

    - Slower: O(n log k).

2. Brute Force

    - Compute max for each window independently.

    - Very slow: O(nk).

### Notes

- The deque is the optimal solution: linear time with minimal extra memory.

- Common interview question → focus on deque logic.

---
