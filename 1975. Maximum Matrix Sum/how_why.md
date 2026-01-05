# 1975. Maximum Matrix Sum

## Problem

Given an n × m integer matrix, you can perform operations to multiply any number of entire rows or columns by -1. Your goal is to maximize the sum of the matrix's elements.

## Approach

The key insight is that we want to **maximize the sum by making all numbers positive** if possible.

### Why This Works

1. **Multiply entire rows/columns by -1**: When we multiply a row or column by -1, all its elements flip sign
2. **We can make pairs of negative numbers positive**: If two negative numbers are positioned such that we can flip them both to positive with row/column operations, they can both become positive
3. **Checkerboard pattern**: The matrix can be thought of like a checkerboard - if we flip certain rows/columns, we control which elements get multiplied by -1

### Key Observations

- We want the sum of **absolute values** of all elements
- If the count of negative numbers is **even**, we can pair them all up and make all positive
- If the count is **odd**, one negative will remain - we want that to be the **smallest absolute value**

## Algorithm

1. **Sum all absolute values**: This gives us the best possible sum if all numbers were positive
2. **Count negative numbers**: Track how many negative values exist
3. **Find minimum absolute value**: This is needed if we can't make all numbers positive
4. **Check parity**:
   - If `negativeCount` is **even**: Return the sum of all absolute values (we can make all positive)
   - If `negativeCount` is **odd**: Subtract `2 * minAbs` from the sum (we must keep the smallest value negative)

## Time & Space Complexity

- **Time**: O(n × m) - single pass through the matrix
- **Space**: O(1) - only using constant extra space

## Example

```s
Input: matrix = [[2,-2,-1],[0,2,1],[1,-2,0]]

Absolute values: [2, 2, 1, 0, 2, 1, 1, 2, 0]
Sum of absolutes: 11
Negative count: 1 (odd)
Minimum absolute value: 0

Result: 11 - 2*0 = 11
```

We have 1 negative number (odd), so we must keep one negative. But since the minimum absolute value is 0, keeping it negative doesn't reduce our sum!
