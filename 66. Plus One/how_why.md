# 66. Plus One - Solution Explanation

## Problem Summary

Given a non-empty array of digits representing a non-negative integer, add one to the number and return the resulting array of digits.

**Example:**

- Input: `[1,2,3]` → Output: `[1,2,4]`
- Input: `[4,3,2,1]` → Output: `[4,3,2,2]`
- Input: `[9]` → Output: `[1,0]`

## How the Solution Works

### Algorithm Overview

The solution uses a **right-to-left traversal** approach with carry handling:

1. **Start from the rightmost digit** (least significant)
2. **Add 1 to the last digit**
3. **Handle carry propagation**: If a digit becomes 10, set it to 0 and continue to the next digit
4. **Early exit**: If we encounter a digit < 9, we can increment it and return immediately (no further carry needed)
5. **Special case**: If all digits were 9 (e.g., `[9,9,9]`), create a new array with one extra digit

### Time & Space Complexity

- **Time Complexity**: O(n) where n is the number of digits (worst case: iterate through all digits)
- **Space Complexity**: O(1) in average case, O(n) in worst case (when all digits are 9 and we need a new array)

## Why This Approach?

### Advantages

✅ **Efficient**: Single pass from right to left - no need to convert to integer and back  
✅ **Avoids overflow**: Works with numbers that exceed integer/long limits  
✅ **In-place modification**: Modifies the input array directly (space efficient for most cases)  
✅ **Early termination**: Stops as soon as carry is absorbed (average case is O(1) space)  

### Key Insight

By iterating from right to left and handling carry as we go:

- We don't need to reverse the array
- We can stop immediately when carry is absorbed
- Only in the edge case (all 9s) do we create a new array

### Example Walkthrough

**Input: `[1,2,9]`**

```c
i=2: digits[2]=9 → digits[2]=0 (carry forward)
i=1: digits[1]=2 < 9 → digits[1]=3, return [1,3,0]
```

**Input: `[9,9,9]`**

```c
i=2: digits[2]=9 → digits[2]=0
i=1: digits[1]=9 → digits[1]=0
i=0: digits[0]=9 → digits[0]=0
Loop ends, create new array [1,0,0,0]
```

## Edge Cases Handled

- Single digit 9: `[9]` → `[1,0]`
- All 9s: `[9,9,9]` → `[1,0,0,0]`
- No carry needed: `[1,2,3]` → `[1,2,4]`
- Mix with zeros: `[9,0,9]` → `[9,1,0]`
