# 3074. Apple Redistribution into Boxes

## Problem Understanding

We need to redistribute apples from multiple packs into boxes with different capacities. The goal is to find the minimum number of boxes needed to store all apples.

## Approach

### Key Insight

To minimize the number of boxes, we should use boxes with the **largest capacities first**. This greedy approach ensures we pack as many apples as possible into each box.

### Algorithm Steps

1. **Calculate Total Apples**: Sum up all apples from all packs
   - We need to know how many apples we're distributing in total

2. **Sort Capacities in Descending Order**:
   - Sort the capacity array
   - Iterate from the end (largest capacity first)

3. **Fill Boxes Greedily**:
   - Start with the largest capacity box
   - Subtract the box capacity from the total apples
   - Increment the box count
   - Continue until all apples are distributed (sum ≤ 0)

## Why This Works

The greedy approach is optimal because:

- Using larger boxes first reduces the number of boxes needed
- There's no benefit to using smaller boxes before larger ones
- Each box can hold any apples (no restrictions on which apples go where)

## Complexity Analysis

- **Time Complexity**: O(n log n + m)
  - O(n log n) for sorting the capacity array (n = length of capacity)
  - O(m) for summing apples (m = length of apple)
  - O(n) for iterating through capacities in worst case
  
- **Space Complexity**: O(1)
  - Only using constant extra space (variables for sum and count)
  - Sorting is done in-place

## Example Walkthrough

**Input**: `apple = [1,3,2]`, `capacity = [4,3,1,5,2]`

1. Total apples = 1 + 3 + 2 = 6
2. Sort capacity: [1,2,3,4,5]
3. Fill boxes (from largest):
   - Take box with capacity 5: 6 - 5 = 1 apple left, boxes used = 1
   - Take box with capacity 4: 1 - 4 = -3 (all apples stored), boxes used = 2
4. Return 2

## Edge Cases

- All apples fit in one box (return 1)
- Need all boxes to store apples (return capacity.length)
- Single apple pack or single box
