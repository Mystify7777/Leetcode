# 3289. The Two Sneaky Numbers of Digitville — how and why

## Problem recap

You're given an array `nums` of length `n + 2` containing numbers from `0` to `n - 1`, where exactly two numbers appear twice (the "sneaky" numbers) and the rest appear once. The task is to find and return these two duplicate numbers.

For example, if `n = 3`, the expected array would be `[0, 1, 2]`, but you're given something like `[0, 1, 2, 1, 2]` where 1 and 2 appear twice.

## Core intuition

This is a variant of the classic "find duplicates" problem that can be solved elegantly using XOR properties:

- `x ^ x = 0` (any number XORed with itself is zero)
- `x ^ 0 = x` (any number XORed with zero is itself)
- XOR is commutative and associative

If we XOR all elements in `nums` with all numbers from `0` to `n-1`, the numbers that appear exactly once will cancel out, leaving us with `a ^ b` where `a` and `b` are the two sneaky numbers.

To separate `a` and `b` from their XOR, we use bit manipulation: find any bit position where `a` and `b` differ (this is guaranteed since `a ≠ b`), then partition all numbers into two groups based on that bit. Each group will contain one of the sneaky numbers.

## Approach — XOR with bit partitioning

1. **XOR everything:** XOR all elements in `nums` and all numbers from `0` to `n-1`. The result is `a ^ b`.

2. **Find a differentiating bit:** Use `diffBit = xor & -xor` to isolate the rightmost set bit in `a ^ b`. This bit is 1 in exactly one of `a` or `b`, not both.

3. **Partition and XOR separately:** Split all numbers (from both `nums` and `0..n-1`) into two groups based on whether they have `diffBit` set:
   - Group 0: XOR all numbers where `(num & diffBit) == 0` → produces one sneaky number
   - Group 1: XOR all numbers where `(num & diffBit) != 0` → produces the other sneaky number

4. **Return the result:** The two XOR results are the two sneaky numbers.

## Implementation (matches `Solution.java`)

```java
class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        int xor = 0;
        int n = nums.length - 2;

        // XOR all elements in nums and all numbers from 0 to n-1
        for (int num : nums) xor ^= num;
        for (int i = 0; i < n; i++) xor ^= i;

        // Find the rightmost set bit in xor (differentiating bit)
        int diffBit = xor & -xor;

        // Partition into two groups and XOR separately
        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & diffBit) == 0) a ^= num;
            else b ^= num;
        }
        for (int i = 0; i < n; i++) {
            if ((i & diffBit) == 0) a ^= i;
            else b ^= i;
        }

        return new int[]{a, b};
    }
}
```

## Why this works

**Step 1 — XOR all numbers:**

After XORing all elements in `nums` and all numbers from `0` to `n-1`:

- Numbers appearing exactly once cancel out (appear twice total: once in `nums`, once in `0..n-1`)
- The two sneaky numbers appear three times total (twice in `nums`, once in `0..n-1`), which is equivalent to appearing once after cancellation
- Result: `xor = a ^ b`

**Step 2 — Find differentiating bit:**

`diffBit = xor & -xor` isolates the rightmost bit where `a` and `b` differ. This works because:

- `-xor` in two's complement flips all bits and adds 1
- The AND operation keeps only the rightmost set bit

**Step 3 — Partition by bit:**

Since `diffBit` is set in exactly one of `a` or `b`, partitioning all numbers by this bit separates them into two independent subproblems:

- One partition contains `a` (appearing twice in `nums`, once in `0..n-1`) and other numbers appearing once → XOR yields `a`
- Other partition contains `b` (appearing twice in `nums`, once in `0..n-1`) and other numbers appearing once → XOR yields `b`

## Complexity

- **Time:** O(n) — we scan `nums` three times and `0..n-1` twice, all linear operations
- **Space:** O(1) — only a few integer variables are used

## Example

Suppose `nums = [0, 1, 2, 1, 2]`, so `n = 3` and the sneaky numbers are 1 and 2.

**Step 1:**

XOR operations:

- XOR all in nums: `0 ^ 1 ^ 2 ^ 1 ^ 2 = 0`
- XOR with 0..2: `0 ^ 0 ^ 1 ^ 2 = 3` (which is `1 ^ 2` in binary: `011`)

**Step 2:**

Finding the differentiating bit:

- `diffBit = 3 & -3 = 3 & ...11111101 = 1` (rightmost set bit)

**Step 3:**

Partitioning:

- Partition by bit 0 (LSB):
  - Group 0 (bit 0 = 0): 0, 2, 2 from nums; 0, 2 from range → XOR = 2
  - Group 1 (bit 0 = 1): 1, 1 from nums; 1 from range → XOR = 1

**Result:** `[2, 1]` or `[1, 2]` (order may vary)

## Edge cases to consider

- Minimum case `n = 2`: Array has 4 elements with two duplicates from {0, 1}
- The two sneaky numbers could be any pair from the range `0..n-1`
- Order of the returned array doesn't matter (as long as both sneaky numbers are present)

## Takeaways

- XOR is a powerful tool for finding duplicates when combined with mathematical properties of the data
- Bit manipulation techniques (like isolating the rightmost set bit) enable elegant solutions to partitioning problems
- This approach uses O(1) space, making it superior to hash-based solutions for this specific problem
- The technique generalizes to "find two missing/duplicate numbers" in arrays with known ranges
