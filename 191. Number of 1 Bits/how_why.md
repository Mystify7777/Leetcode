# 191. Number of 1 Bits - How & Why

## Problem Overview

Write a function that takes an unsigned integer and returns the number of '1' bits it has (also known as the **Hamming weight**).

**Example:**

- Input: `n = 11` (binary: `00000000000000000000000000001011`)
- Output: `3` (three '1' bits)
- Input: `n = 128` (binary: `00000000000000000000000010000000`)
- Output: `1` (one '1' bit)

## Algorithm Explanation

### Key Insight

Use **bit manipulation** to check each bit of the number:

1. Use `n & 1` to check if the least significant bit (rightmost bit) is 1
2. Use **unsigned right shift** (`>>>`) to move to the next bit
3. Repeat until all bits are processed

### Step-by-Step Logic

1. **Initialize counter** `ones = 0`
2. **While n is not 0:**
   - Check the rightmost bit using `n & 1`
   - Add result (0 or 1) to the counter
   - Right shift n by 1 position using `>>>`
3. **Return the count**

### Why This Works

- `n & 1` performs a bitwise AND with 1 (binary: `...0001`)
  - If rightmost bit is 1: `1 & 1 = 1`
  - If rightmost bit is 0: `0 & 1 = 0`
- `>>>` is **unsigned right shift** - shifts bits right and fills leftmost bits with 0
- Each iteration processes one bit until n becomes 0

### Time & Space Complexity

- **Time:** O(log n) or O(32) for 32-bit integers (essentially O(1) for fixed-size integers)
- **Space:** O(1) - Only using a counter variable

## Bit Manipulation Deep Dive

### The `&` (AND) Operator

```bash
  1011  (n = 11)
& 0001  (mask = 1)
------
  0001  (result = 1, rightmost bit is 1)

  1010  (n = 10)
& 0001  (mask = 1)
------
  0000  (result = 0, rightmost bit is 0)
```

### The `>>>` (Unsigned Right Shift) Operator

**Why `>>>` instead of `>>`?**

- `>>` is **signed right shift** - preserves sign bit (leftmost bit)
- `>>>` is **unsigned right shift** - always fills with 0 from left

**Example:**

```java
int n = -1;  // All bits are 1: 11111111111111111111111111111111

// Signed right shift >> (infinite loop risk!)
n >> 1  →  11111111111111111111111111111111  (still -1, sign preserved)

// Unsigned right shift >>>
n >>> 1  →  01111111111111111111111111111111  (positive number)
```

**For positive numbers, both work the same:**

```shell
  1011  (11)
>>> 1
------
  0101  (5)
```

## Code Walkthrough

```java
public static int hammingWeight(int n) {
    int ones = 0;  // Counter for 1 bits
    
    while(n != 0) {
        ones = ones + (n & 1);  // Add 1 if rightmost bit is 1, else add 0
        n = n >>> 1;             // Shift right to process next bit
    }
    
    return ones;
}
```

### Why `ones + (n & 1)` instead of `if` statement?

- **Compact:** Avoids conditional branching
- **Efficient:** Direct addition is faster than if-else
- **Equivalent to:**

  ```java
  if ((n & 1) == 1) {
      ones++;
  }
  ```

## Example Walkthrough

**Input:** `n = 11` (binary: `1011`)

| Iteration | n (binary) | n (decimal) | n & 1 | ones | n >>> 1 |
| ----------- | ------------ | ------------- | ------- | ------ | --------- |
| Initial | 1011 | 11 | - | 0 | - |
| 1 | 1011 | 11 | 1 | 1 | 0101 (5) |
| 2 | 0101 | 5 | 1 | 2 | 0010 (2) |
| 3 | 0010 | 2 | 0 | 2 | 0001 (1) |
| 4 | 0001 | 1 | 1 | 3 | 0000 (0) |
| End | 0000 | 0 | - | 3 | Loop exits |

**Result:** `3` (three 1-bits)

**Input:** `n = 128` (binary: `10000000`)

| Iteration | n (binary) | n & 1 | ones | n >>> 1 |
| ----------- | ------------ | ------- | ------ | --------- |
| Initial | 10000000 | - | 0 | - |
| 1 | 10000000 | 0 | 0 | 01000000 |
| 2 | 01000000 | 0 | 0 | 00100000 |
| 3 | 00100000 | 0 | 0 | 00010000 |
| 4 | 00010000 | 0 | 0 | 00001000 |
| 5 | 00001000 | 0 | 0 | 00000100 |
| 6 | 00000100 | 0 | 0 | 00000010 |
| 7 | 00000010 | 0 | 0 | 00000001 |
| 8 | 00000001 | 1 | 1 | 00000000 |
| End | 00000000 | - | 1 | Loop exits |

**Result:** `1` (one 1-bit)

## Alternative Approaches

### 1. Brian Kernighan's Algorithm (More Efficient)

```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n = n & (n - 1);  // Removes rightmost 1-bit
        count++;
    }
    return count;
}
```

**How it works:**

- `n & (n - 1)` removes the rightmost 1-bit
- Only iterates once per 1-bit (not once per total bit)
- **Time:** O(k) where k = number of 1-bits

**Example:** `n = 12` (binary: `1100`)

```shell
Iteration 1: 1100 & 1011 = 1000 (removed rightmost 1)
Iteration 2: 1000 & 0111 = 0000 (removed last 1)
Result: 2 iterations for 2 ones
```

**Comparison:**

- **Current solution:** Checks all 32 bits (or log n bits)
- **Kernighan's:** Only checks bits that are 1
- **Better when:** Few 1-bits in a large number

### 2. Built-in Java Method

```java
public int hammingWeight(int n) {
    return Integer.bitCount(n);
}
```

- **Pros:** One line, optimized
- **Cons:** Not showing understanding of bit manipulation

### 3. Convert to Binary String

```java
public int hammingWeight(int n) {
    return Long.bitCount(Integer.toUnsignedLong(n));
}
```

- Less efficient due to string conversion
- Not recommended for this problem

## Edge Cases

1. **n = 0** - Zero has no 1-bits → Return 0
   - Binary: `00000000000000000000000000000000`
   - Result: 0

2. **n = 1** - Single 1-bit → Return 1
   - Binary: `00000000000000000000000000000001`
   - Result: 1

3. **n = -1** - All 32 bits are 1 (two's complement) → Return 32
   - Binary: `11111111111111111111111111111111`
   - Result: 32

4. **n = 2147483647** (Integer.MAX_VALUE) - 31 ones → Return 31
   - Binary: `01111111111111111111111111111111`
   - Result: 31

5. **n = -2147483648** (Integer.MIN_VALUE) - Only leftmost bit is 1 → Return 1
   - Binary: `10000000000000000000000000000000`
   - Result: 1

## Why Unsigned Right Shift Matters

### Problem with Signed Right Shift (`>>`)

```java
int n = -5;  // Binary: 11111111111111111111111111111011

// Using >> (signed)
n >> 1  →  11111111111111111111111111111101  (still negative, -3)
n >> 2  →  11111111111111111111111111111110  (still negative, -2)
// Will never reach 0, infinite loop!

// Using >>> (unsigned)
n >>> 1  →  01111111111111111111111111111101  (positive)
n >>> 2  →  00111111111111111111111111111110  (positive)
// Eventually reaches 0
```

### Visual Comparison

```r
Original:     10000000000000000000000000000001 (-2147483647)

Right shift (>>):
Step 1:       11000000000000000000000000000000 (negative)
Step 2:       11100000000000000000000000000000 (negative)
...           (never reaches 0)

Unsigned right shift (>>>):
Step 1:       01000000000000000000000000000000 (positive)
Step 2:       00100000000000000000000000000000 (positive)
...           (eventually reaches 0)
```

## Performance Analysis

### Current Solution

- **Best case:** O(1) for n = 0
- **Average case:** O(32) = O(1) for 32-bit integers
- **Worst case:** O(32) = O(1)

### Memory

- **Space:** O(1) - only two integer variables

### Optimizations Applied

1. **No division/modulo** - Uses bit operations (faster)
2. **No conditionals** - Direct addition of `(n & 1)`
3. **Unsigned shift** - Ensures termination for all inputs

## Key Takeaways

1. **Bit masking** (`n & 1`) checks individual bits efficiently
2. **Unsigned right shift** (`>>>`) is crucial for negative numbers
3. **Bit manipulation** is faster than arithmetic operations
4. **Brian Kernighan's algorithm** is more efficient for sparse 1-bits
5. **Understanding two's complement** is essential for negative numbers
6. **Simple addition** `ones + (n & 1)` avoids branching overhead

## When to Use Each Approach

| Approach | Use When | Pros | Cons |
| ---------- | ---------- | ------ | ------ |
| **Current (shift all bits)** | General purpose, interviews | Simple, easy to explain | Checks all bits |
| **Kernighan's** | Many zeros, few ones | Faster for sparse 1s | Slightly harder to understand |
| **Built-in bitCount()** | Production code | Most optimized | Doesn't show understanding |

## Interview Tips

1. **Start with the simple shift approach** - Shows bit manipulation understanding
2. **Mention Kernighan's algorithm** - Shows advanced knowledge
3. **Explain why `>>>` not `>>`** - Shows deep understanding of Java
4. **Discuss trade-offs** - Shows analytical thinking
5. **Test with negative numbers** - Shows attention to edge cases

The current solution is **elegant, correct, and interview-friendly**! 🎯
