# How_Why.md

## Problem

Given an integer array `nums` and an integer `k`, return the **k-th largest element** in the array.

---

## How (Step-by-step Solution)

### Approach: Min-Heap of Size k

1. Create a **min-heap** (PriorityQueue in Java).
2. Insert the first **k elements** of `nums` into the heap.
3. For each remaining element:
   - If the element is **greater than the heap’s root (smallest)**:
     - Remove the root (`poll()`).
     - Insert the current element.
4. After processing all elements, the root of the heap is the **k-th largest element**.

---

## Why (Reasoning)

- A **min-heap** of size `k` always keeps the `k` largest elements seen so far.  
- The smallest among them (heap root) is exactly the **k-th largest overall**.  
- Efficient because we don’t need to sort the whole array.

---

## Complexity Analysis

- **Time Complexity**:  
  - Heap insertion/removal = O(log k).  
  - For `n` elements → O(n log k).  
- **Space Complexity**: O(k), for storing the heap.

---

## Example Walkthrough

### Input

`nums = [3,2,1,5,6,4]`
`k = 2`

### Steps

- Insert first `k=2` → heap = [2,3] (min-heap keeps smallest at root).  
- i=2 → nums[2]=1 → not greater than 2 → skip.  
- i=3 → nums[3]=5 > 2 → remove 2, add 5 → heap = [3,5].  
- i=4 → nums[4]=6 > 3 → remove 3, add 6 → heap = [5,6].  
- i=5 → nums[5]=4 not > 5 → skip.  

Result = **heap root = 5** → 2nd largest.

---

## Alternate Approaches

1. **Sorting** → Sort array in descending order → O(n log n).  
2. **Quickselect (Partition-based, like QuickSort)** → O(n) average, O(n²) worst case.  
3. **Heap (Used here)** → O(n log k), good for large `n` with small `k`.

✅ **Balanced choice**: Min-heap approach (efficient and simple).
