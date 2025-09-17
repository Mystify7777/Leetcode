# How_Why.md

## Problem

You are given an array `nums` containing `n + 1` integers where each integer is between `1` and `n` (inclusive).  
There is **only one repeated number**, but it may appear more than once.  
Return this duplicate number.

---

## How (Step-by-step Solution)

### Approach: Floyd’s Tortoise and Hare (Cycle Detection)

1. **Think of the array as a linked list**:
   - Each index points to `nums[i]`.
   - Because there are `n+1` elements in range `[1, n]`, by the pigeonhole principle, there must be a cycle (caused by the duplicate).
2. **Phase 1: Detect cycle**  
   - Use two pointers:  
     - `slow = nums[slow]` (moves 1 step).  
     - `fast = nums[nums[fast]]` (moves 2 steps).  
   - Eventually, `slow` and `fast` will meet inside the cycle.
3. **Phase 2: Find the entrance of the cycle**  
   - Reset one pointer (`slow2`) to the start (`nums[0]`).  
   - Move both `slow` and `slow2` one step at a time:  
     - `slow = nums[slow]`  
     - `slow2 = nums[slow2]`  
   - The point where they meet is the duplicate number.

---

## Why (Reasoning)

- The duplicate creates a **cycle in the linked list mapping**.  
- Floyd’s algorithm guarantees cycle detection in O(n) time with O(1) space.  
- Resetting one pointer to the start and moving both at the same pace ensures they meet exactly at the cycle entrance (the duplicate value).

---

## Complexity Analysis

- **Time Complexity**: O(n) → Each pointer traverses at most `2n` steps.  
- **Space Complexity**: O(1) → Only a few variables used.

---

## Example Walkthrough

### Input

```java
nums = [1, 3, 4, 2, 2]
```

### Step 1: Cycle detection

- `slow = nums[0] = 1`  
- `fast = nums[0] = 1`  

Iterations:

- Move once: `slow=3`, `fast=2`
- Move again: `slow=2`, `fast=4`
- Move again: `slow=4`, `fast=2`
- Move again: `slow=2`, `fast=2` → **they meet**

### Step 2: Find cycle entrance

- Reset `slow2 = nums[0] = 1`  
- Move step by step:
  - `slow= nums[2] = 4`, `slow2= nums[1] = 3`  
  - `slow= nums[4] = 2`, `slow2= nums[3] = 2` → **they meet at 2**

### Output

`2`

---

## Alternate Approaches

1. **Sorting** → O(n log n), but destroys order.  
2. **HashSet** → Detect first repeat, O(n) time but O(n) extra space.  
3. **Binary Search on Value Range** → Count elements ≤ mid, narrow range. O(n log n) time, O(1) space.  

✅ **Best choice**: Floyd’s Cycle Detection (O(n) time, O(1) space).
