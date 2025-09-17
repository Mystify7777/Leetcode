# How_Why.md

## Problem

You are given an integer array `nums` of length `n` where each element appears **once or twice**.  
Return all the elements that appear **twice** in any order.

---

## How (Step-by-step Solution)

### Approach: Index Marking (In-Place Sign Flipping)

1. Each number in `nums` is in the range `[1, n]`.  
   This means we can use the number as an **index reference**.
2. Traverse the array:
   - For each number `x = abs(nums[i])`:
     - Look at `nums[x - 1]` (the index corresponding to `x`).
     - If `nums[x - 1]` is already **negative**, it means we have seen `x` before → add `x` to the result.
     - Otherwise, flip the sign of `nums[x - 1]` to mark that we have visited this number.
3. Return the list of duplicates collected.

---

## Why (Reasoning)

- The array is guaranteed to only contain numbers from `1` to `n`.  
- By marking the presence of a number using sign flips, we avoid extra space.  
- If we ever encounter a number whose "index position" is already negative, it means it has appeared before → duplicate found.

---

## Complexity Analysis

- **Time Complexity**: O(n) → One pass through the array.  
- **Space Complexity**: O(1) → Only output list uses extra space (ignoring it as per problem statement).

---

## Example Walkthrough

### Input

```java nums = [4,3,2,7,8,2,3,1]```

### Step 1: Traverse

- i=0 → x=4 → flip `nums[3]` → nums = [4,3,2,-7,8,2,3,1]
- i=1 → x=3 → flip `nums[2]` → nums = [4,3,-2,-7,8,2,3,1]
- i=2 → x=2 → flip `nums[1]` → nums = [4,-3,-2,-7,8,2,3,1]
- i=3 → x=7 → flip `nums[6]` → nums = [4,-3,-2,-7,8,2,-3,1]
- i=4 → x=8 → flip `nums[7]` → nums = [4,-3,-2,-7,8,2,-3,-1]
- i=5 → x=2 → nums[1] is already negative → duplicate = 2
- i=6 → x=3 → nums[2] is already negative → duplicate = 3
- i=7 → x=1 → flip `nums[0]` → nums = [-4,-3,-2,-7,8,2,-3,-1]

### Step 2: Output

`[2,3]`

---

## Alternate Approaches

1. **Sorting**  
   - Sort the array and check adjacent numbers.  
   - Time: O(n log n), Space: O(1).  

2. **HashSet**  
   - Keep track of seen numbers in a set.  
   - Time: O(n), Space: O(n).  

3. **Optimal (Used Here): Index Marking**  
   - O(n) time, O(1) space (in-place).  
   - Best for large arrays.
