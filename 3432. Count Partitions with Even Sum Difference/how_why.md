# 3432. Count Partitions with Even Sum Difference — how/why

## Recap

Given an integer array `nums`, partition it into two non-empty subarrays by choosing an index `i` (where `0 ≤ i < n-1`). The left partition is `nums[0..i]` and the right partition is `nums[i+1..n-1]`. Count how many valid partitions result in `leftSum % 2 == rightSum % 2` (i.e., both sums are even or both are odd).

## Intuition

Key insight: Only the **parity** (even/odd) of the sums matters. Let `L` = sum of left partition, `R` = sum of right partition, and `total` = `L + R`.

For `L % 2 == R % 2`:

- If `total` is even: `L` even ⟹ `R` even, `L` odd ⟹ `R` odd. Both cases satisfy the condition.
- If `total` is odd: `L` even ⟹ `R` odd, `L` odd ⟹ `R` even. No case satisfies the condition.

**Therefore**: If `total` is even, *all* partitions are valid. Otherwise, *no* partitions are valid.

## Approach

**Optimized Solution (O(n))**:

1. Compute `total = sum(nums)`.
2. Check if `total` is even (using bitwise AND: `total & 1 == 0`).
   - If even: return `n - 1` (all `n-1` possible partitions are valid).
   - If odd: return `0` (no valid partitions).

**Alternative Solution (Naive, for clarity)**:

1. Compute `total = sum(nums)`.
2. Iterate through each possible partition index `i` from `0` to `n-2`:
   - Compute `leftSum` incrementally.
   - Compute `rightSum = total - leftSum`.
   - Check if `leftSum % 2 == rightSum % 2`; if yes, increment count.
3. Return count.

## Code (Java)

```java
class Solution {
    public int countPartitions(int[] A) {
        int total = Arrays.stream(A).sum();
        return (total & 1) == 0 ? A.length - 1 : 0;
    }
}
```

## Correctness

- **Parity preservation**: `L + R = total`. If `total` is even, `L` and `R` have the same parity (both even or both odd). If `total` is odd, `L` and `R` have opposite parities.

- **Bitwise check**: `(total & 1) == 0` efficiently checks if `total` is even (equivalent to `total % 2 == 0`).

- **No partition constraint**: We cannot partition into empty subarrays, so valid indices are `0` to `n-2`, giving `n-1` possible partitions.

- **Completeness**: All `n-1` partitions satisfy the parity condition if and only if `total` is even.

## Complexity

- **Time**: `O(n)` for computing the sum.
- **Space**: `O(1)` auxiliary space.

## Edge Cases

- Array of size 1: return 0 (cannot partition into two non-empty parts).
- Array of size 2: return 1 if sum is even, else 0.
- All elements even: sum is even → return `n-1`.
- All elements odd:
  - If `n` is even: sum is even → return `n-1`.
  - If `n` is odd: sum is odd → return 0.
- Mix of even and odd: depends on total parity.
- Negative numbers: parity rules still apply (e.g., `-3` is odd).

## Takeaways

- **Parity analysis**: Often leads to dramatic simplifications (reduces O(n) iteration to O(1) check).
- **Mathematical insight over simulation**: Understanding that all partitions behave identically regarding parity eliminates need for iteration.
- **Bitwise operations**: `x & 1` is a fast way to check odd/even.
- **Constraint exploitation**: The constraint "both sums must have same parity" directly relates to total parity, not individual elements.
- This pattern applies to other parity-based partition problems (e.g., sum differences, mod k sums).

## Alternative (Naive, O(n) with Iteration)

```java
class Solution {
    public int countPartitions(int[] nums) {
        int total = 0;
        for (int num : nums) total += num;
        
        int leftSum = 0;
        int count = 0;
        
        for (int i = 0; i < nums.length - 1; i++) {
            leftSum += nums[i];
            int rightSum = total - leftSum;
            
            if ((leftSum % 2) == (rightSum % 2)) {
                count++;
            }
        }
        
        return count;
    }
}
```

**Trade-off**: More explicit and easier to understand, but `O(n)` time with loop overhead. The optimized solution is mathematically superior but requires recognizing the parity pattern. Use optimized version in production; use alternative for clarity during development or interviews.
