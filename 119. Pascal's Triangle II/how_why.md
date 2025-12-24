# 119. Pascal's Triangle II

## Problem Understanding

Return the `rowIndex`-th row of Pascal's Triangle, where the first row is row 0.

In Pascal's Triangle:

- Each number is the sum of the two numbers directly above it
- Row 0: [1]
- Row 1: [1,1]
- Row 2: [1,2,1]
- Row 3: [1,3,3,1]
- Row 4: [1,4,6,4,1]

## Approach

### Mathematical Formula (Combinatorial)

This solution uses the **binomial coefficient formula** to calculate each element directly:

The k-th element in row n is: **C(n, k) = n! / (k! × (n-k)!)**

Rather than calculating factorials (which can cause overflow), we use an iterative formula:

- C(n, 0) = 1
- C(n, k) = C(n, k-1) × (n - k + 1) / k

### Algorithm Steps

1. **Initialize**: Start with the first element (always 1)
2. **Iterative Calculation**: For each position k from 1 to r:
   - Multiply previous result by `(r - k + 1)` (numerator)
   - Divide by `k` (denominator)
   - Add the result to the answer list
3. **Return** the complete row

## Why This Works

### The Mathematical Foundation

Each element in Pascal's Triangle represents a binomial coefficient C(n,k), which counts combinations.

The recurrence relation is:

- C(n, k) = C(n, k-1) × (n - k + 1) / k

**Example for row 4:**

- C(4,0) = 1
- C(4,1) = C(4,0) × 4/1 = 1 × 4 = 4
- C(4,2) = C(4,1) × 3/2 = 4 × 3/2 = 6
- C(4,3) = C(4,2) × 2/3 = 6 × 2/3 = 4
- C(4,4) = C(4,3) × 1/4 = 4 × 1/4 = 1

Result: [1, 4, 6, 4, 1] ✓

### Key Implementation Details

1. **Long for temp**: Uses `long` to prevent overflow during intermediate calculations
2. **Order of operations**: Multiply first, then divide to maintain accuracy and avoid premature rounding
3. **up and down counters**:
   - `up` starts at r and decrements (numerator factors)
   - `down` starts at 1 and increments (denominator factors)

## Complexity Analysis

- **Time Complexity**: O(r)
  - Single loop iterating r times
  - Each iteration does O(1) arithmetic operations

- **Space Complexity**: O(r)
  - Only the output list which stores r+1 elements
  - No extra data structures used

## Example Walkthrough

**Input**: `rowIndex = 4`

```c
Initialize: ans = [1], temp = 1

i=1: up=4, down=1
  temp = 1 × 4 / 1 = 4
  ans = [1, 4]

i=2: up=3, down=2
  temp = 4 × 3 / 2 = 6
  ans = [1, 4, 6]

i=3: up=2, down=3
  temp = 6 × 2 / 3 = 4
  ans = [1, 4, 6, 4]

i=4: up=1, down=4
  temp = 4 × 1 / 4 = 1
  ans = [1, 4, 6, 4, 1]

Output: [1, 4, 6, 4, 1]
```

## Alternative Approaches

1. **Dynamic Programming (Space O(r²))**: Build all rows from 0 to r
2. **In-place DP (Space O(r))**: Update a single array by iterating backwards
3. **Recursive Formula**: Use the additive property (element = left + right from previous row)

This solution is optimal because it directly computes the target row in O(r) time with O(r) space.

## Edge Cases

- rowIndex = 0 → [1]
- rowIndex = 1 → [1, 1]
- Large values of rowIndex (using `long` prevents overflow)
