# How_Why.md

## Problem

Given an array `temperatures` where `temperatures[i]` represents the daily temperature on day `i`,  
return an array `answer` such that `answer[i]` is the number of days you have to wait after day `i`  
to get a warmer temperature. If there is no future day for which this is possible, put `0` instead.

---

## How (Step-by-step Solution)

### Approach: Monotonic Decreasing Stack

1. Create a **stack** to store indices of temperatures.
   - The stack will keep indices in such a way that the temperatures are in decreasing order.
2. Traverse the array:
   - While the current day’s temperature is **greater** than the temperature at the top of the stack:
     - Pop the index from the stack → this means we found a warmer day for that earlier day.
     - Update the result for that popped index with `(current index - popped index)`.
   - Push the current index onto the stack.
3. Any indices left in the stack at the end don’t have a warmer day, so their results stay as `0`.

---

## Why (Reasoning)

- A naive approach would compare each day with all future days → O(n²).
- The **stack** helps us efficiently find the "next greater temperature":
  - It only stores indices of unresolved days (waiting for a warmer day).
  - Each index is pushed and popped at most once → O(n) overall.
- This transforms the problem into a **monotonic stack problem** (a common pattern for "next greater element").

---

## Complexity Analysis

- **Time Complexity**: O(n) → each element pushed and popped at most once.  
- **Space Complexity**: O(n) → stack stores up to n indices in the worst case.

---

## Example Walkthrough

### Input

```java temperatures = [73,74,75,71,69,72,76,73]```

### Step-by-step

- Day 0 (73): stack = [0]
- Day 1 (74): warmer than 73 → result[0] = 1 → stack = [1]
- Day 2 (75): warmer than 74 → result[1] = 1 → stack = [2]
- Day 3 (71): cooler, push → stack = [2,3]
- Day 4 (69): cooler, push → stack = [2,3,4]
- Day 5 (72): warmer than 69 → result[4] = 1  
  warmer than 71 → result[3] = 2 → stack = [2,5]
- Day 6 (76): warmer than 75 → result[2] = 4  
  warmer than 72 → result[5] = 1 → stack = [6]
- Day 7 (73): cooler, push → stack = [6,7]

### Final result

`[1,1,4,2,1,1,0,0]`

---

## Alternate Approaches

1. **Brute Force**
   - For each day, look ahead until a warmer day is found.
   - Time: O(n²), Space: O(1).

2. **Backward Traversal with "Next Index" Jump**
   - Traverse from the end, skipping days using previously computed answers.
   - More complex to implement, but O(n).

3. ✅ **Optimal** → Monotonic stack (used here). Clean and efficient.
