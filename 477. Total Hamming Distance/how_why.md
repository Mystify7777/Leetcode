# 477. Total Hamming Distance - Solution Explanation

## Problem Understanding

**Hamming Distance** between two integers is the number of positions where their binary representations differ.

**Example:**

- `4 = 100` (binary)
- `14 = 1110` (binary)
- Hamming distance = 2 (differ at positions 1 and 3)

**Task:** Given an array of integers, find the sum of Hamming distances between **all pairs** of numbers.

### Naive Approach (Too Slow)

```java
// O(n²) - Times out on large inputs
int total = 0;
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        total += hammingDistance(nums[i], nums[j]);
    }
}
```

For `n = 10,000`, this requires ~50 million operations. We need something better!

## The Brilliant Insight: Think Bit by Bit! 🧠

Instead of comparing pairs of numbers, **analyze each bit position independently across all numbers**.

### Key Observation

For any specific bit position:

- If a bit is **1** in `count_ones` numbers
- Then it's **0** in `count_zeros = n - count_ones` numbers
- **Total contributions** from this bit = `count_ones × count_zeros`

Why? Because each `1` forms a differing pair with each `0` at this position!

## Visual Explanation

### Example: `nums = [4, 14, 2]`

Let's write these in **32-bit binary** (showing only relevant bits):

```c
Number   Decimal    Binary Representation
                    Bit:  4  3  2  1  0
─────────────────────────────────────────
nums[0]     4            0  0  1  0  0
nums[1]    14            0  1  1  1  0
nums[2]     2            0  0  0  1  0
```

### Step-by-Step: Analyze Each Bit Position

#### **Bit Position 0 (Rightmost):**

```c
nums[0] = 4  →  ...00100  →  bit 0 = 0
nums[1] = 14 →  ...01110  →  bit 0 = 0
nums[2] = 2  →  ...00010  →  bit 0 = 0
```

- **Ones:** 0
- **Zeros:** 3
- **Contribution:** `0 × 3 = 0` (no differing pairs)

#### **Bit Position 1:**

```c
nums[0] = 4  →  ...00100  →  bit 1 = 0
nums[1] = 14 →  ...01110  →  bit 1 = 1  ← One!
nums[2] = 2  →  ...00010  →  bit 1 = 1  ← One!
```

- **Ones:** 2 (nums[1], nums[2])
- **Zeros:** 1 (nums[0])
- **Contribution:** `2 × 1 = 2`
  - Pairs: (nums[0], nums[1]) and (nums[0], nums[2])

#### **Bit Position 2:**

```c
nums[0] = 4  →  ...00100  →  bit 2 = 1  ← One!
nums[1] = 14 →  ...01110  →  bit 2 = 1  ← One!
nums[2] = 2  →  ...00010  →  bit 2 = 0
```

- **Ones:** 2 (nums[0], nums[1])
- **Zeros:** 1 (nums[2])
- **Contribution:** `2 × 1 = 2`
  - Pairs: (nums[0], nums[2]) and (nums[1], nums[2])

#### **Bit Position 3:**

```c
nums[0] = 4  →  ...00100  →  bit 3 = 0
nums[1] = 14 →  ...01110  →  bit 3 = 1  ← One!
nums[2] = 2  →  ...00010  →  bit 3 = 0
```

- **Ones:** 1 (nums[1])
- **Zeros:** 2 (nums[0], nums[2])
- **Contribution:** `1 × 2 = 2`
  - Pairs: (nums[1], nums[0]) and (nums[1], nums[2])

#### **Bit Positions 4-31:**

All bits are 0 for these small numbers → contribution = 0

### **Total Hamming Distance:**

```c
Sum = 0 + 2 + 2 + 2 + 0 + ... + 0 = 6
```

## Verification: Let's Check Manually

**All pairs:**

1. `hamming(4, 14)`:

   ```c
   4  = 00100
   14 = 01110
   XOR = 01010  →  2 differing bits
   ```

2. `hamming(4, 2)`:

   ```c
   4 = 00100
   2 = 00010
   XOR = 00110  →  2 differing bits
   ```

3. `hamming(14, 2)`:

   ```c
   14 = 01110
   2  = 00010
   XOR = 01100  →  2 differing bits
   ```

**Total:** `2 + 2 + 2 = 6` ✓ Matches!

## Code Walkthrough

```java
public int totalHammingDistance(int[] nums) {
    int result = 0;
    
    // Check all 32 bit positions
    for (int i = 0; i < 32; i++) {
        int count_ones = 0;
        
        // Count how many numbers have bit i set to 1
        for (int j = 0; j < nums.length; j++) {
            count_ones += (nums[j] >> i) & 1;
        }
        
        // Calculate contribution from this bit position
        result += count_ones * (nums.length - count_ones);
    }
    
    return result;
}
```

### Breaking Down the Bit Magic

**`(nums[j] >> i) & 1`** - What does this do?

1. **`nums[j] >> i`**: Right shift by `i` positions
   - Moves bit `i` to the rightmost position
   - Example: `14 >> 2` → `1110 >> 2` → `0011` (decimal 3)

2. **`& 1`**: Bitwise AND with 1
   - Extracts only the rightmost bit
   - Example: `0011 & 0001` → `0001` (result = 1)

**Visual Example for `nums[j] = 14`, `i = 2`:**

```c
14 in binary:      0 0 0 0 1 1 1 0
After >> 2:        0 0 0 0 0 0 1 1
After & 1:         0 0 0 0 0 0 0 1  → Returns 1
```

## Why This Formula Works: The Combinatorics

For a specific bit position with:

- `m` numbers having bit = 1
- `n - m` numbers having bit = 0

**How many differing pairs?**

- Each `1` can pair with any `0`
- Total pairs = `m × (n - m)`

**Example:** 5 numbers, 3 have bit=1, 2 have bit=0

```c
Group A (bit=1): [a, b, c]
Group B (bit=0): [d, e]

Differing pairs:
a-d, a-e  (2 pairs from a)
b-d, b-e  (2 pairs from b)
c-d, c-e  (2 pairs from c)
Total: 3 × 2 = 6 pairs
```

## Complexity Analysis

**Time Complexity:** O(32 × n) = **O(n)**

- Outer loop: 32 iterations (constant)
- Inner loop: n iterations
- Much better than O(n²) naive approach!

**Space Complexity:** O(1)

- Only using a few variables

## Why This is So Much Faster

| Approach | Time Complexity | For n=10,000 |
| ---------- | ---------------- | -------------- |
| Naive (all pairs) | O(n²) | ~100,000,000 ops |
| Bit-by-bit | O(32n) | ~320,000 ops |

**Speed-up:** ~312x faster! 🚀

## Alternative Code Styles

### Style 1: Helper Function (Cleaner)

```java
public int totalHammingDistance(int[] nums) {
    int res = 0;
    for (int bit = 0; bit < 32; bit++) {
        res += distance(nums, bit);
    }
    return res;
}

private int distance(int[] nums, int bit) {
    int ones = 0;
    for (int num : nums) {
        ones += ((num >> bit) & 1);
    }
    return ones * (nums.length - ones);
}
```

### Style 2: Enhanced Loop (More Functional)

```java
public int totalHammingDistance(int[] nums) {
    int distance = 0;
    for (int i = 0; i < 32; i++) {
        int ones = 0;
        for (int num : nums) {
            ones += (num >> i) & 1;
        }
        distance += ones * (nums.length - ones);
    }
    return distance;
}
```

All three styles are equivalent - choose based on readability preference!

## Key Takeaways

1. **Transform the problem:** Instead of comparing pairs, analyze bits independently
2. **Use combinatorics:** Count ones and zeros, then multiply
3. **Bit manipulation:** `(num >> i) & 1` extracts bit `i`
4. **Optimize aggressively:** O(n²) → O(n) makes a huge difference

This pattern of "analyzing each dimension independently" appears in many problems. Once you see it, it becomes a powerful tool in your arsenal!
