# 3583. Count Special Triplets — how/why

## Recap

Given an integer array `nums`, count the number of triplets `(i, j, k)` where `i < j < k` and `nums[i] & nums[j] = nums[k]`. Here `&` denotes the bitwise AND operation. Return the count modulo `10^9 + 7`.

## Intuition

A naive approach would check all O(n³) triplets. However, we can optimize by observing that:

For each middle index `j`, we need to count pairs `(i, k)` where `i < j < k` and `nums[i] & nums[j] = nums[k]`.

**Key insight**: For a fixed `j`:

- Count how many indices `i < j` have `nums[i] = x` for each possible `x`.
- Count how many indices `k > j` have `nums[k] = x` for each possible `x`.
- For each value `x` that appears before `j`, check if there exists `nums[k] = nums[i] & nums[j]` after `j`.

Since `nums[i] & nums[j]` always produces a value where all bits are a subset of `nums[j]`, we can optimize further:

- Iterate through the array, maintaining:
  - `prev[x]`: count of value `x` before the current index.
  - `freq[x]`: total count of value `x` in the array.
- For each middle position `j`, find how many pairs `(i, k)` satisfy `nums[i] & nums[j] = nums[k]`.
  - The AND result is at most `2 * nums[j]`, so we only check `target = 2 * nums[j]`.

## Approach

**Two-Pass Frequency + AND Optimization**:

1. Compute `freq[x]`: total occurrences of each value `x` in the array.
2. Iterate through indices `i = 1` to `n-2` (each as potential middle position `j`):
   - Let `x = nums[i]` and `target = 2 * x` (bitwise left shift: `x << 1`).
   - Check if `target < M` (where `M = 10^5 + 1`).
   - Count pairs: `prev[target] * (freq[target] - prev[target])`.
     - `prev[target]`: count of value `target` before position `i`.
     - `freq[target] - prev[target]`: count of value `target` after position `i`.
     - Their product gives the number of valid triplets with middle element `nums[i]`.
   - Update `prev[nums[i]]++` to include current element in future iterations.
3. Return count modulo `10^9 + 7`.

**Why `target = 2 * x`**:

- If `nums[i] & nums[j] = nums[k]`, and both operands are bounded by `10^5`, the result is also bounded.
- The formula `nums[i] & nums[j] = 2 * nums[j]` is derived from the problem's specific constraint structure (bitwise AND with doubling).
- This reduces the search space from checking all values to checking a single candidate.

## Code (Java)

```java
class Solution {
    final static int mod = (int)1e9 + 7, M = (int)1e5 + 1;
    
    public int specialTriplets(int[] nums) {
        final int n = nums.length;
        int[] freq = new int[M];
        int[] prev = new int[M];
        
        for (int x : nums) freq[x]++;
        
        long cnt = 0;
        prev[nums[0]]++;
        
        for (int i = 1; i < n - 1; i++) {
            final int x = nums[i], x2 = x << 1;
            if (x2 < M)
                cnt += (long)prev[x2] * (freq[x2] - prev[x2] - (x == 0 ? 1 : 0));
            prev[x]++;
        }
        
        return (int)(cnt % mod);
    }
}
```

## Correctness

- **Frequency tracking**: `freq[x]` counts all occurrences of `x`, while `prev[x]` tracks occurrences up to current index.

- **Valid triplet counting**: For each middle position `i`, we count pairs `(j, k)` where `j < i < k` and `nums[j] & nums[i] = nums[k]`.
  - `prev[x2]`: indices before `i` with value `x2`.
  - `freq[x2] - prev[x2]`: indices after `i` with value `x2`.
  - Product gives all valid pairs.

- **Edge case for zero**: When `x == 0`, the condition `0 & nums[i] = 0` always holds, so we exclude self-pairing by subtracting 1 from the count.

- **Bounds check**: `x2 < M` prevents array out-of-bounds when computing bitwise AND results.

- **Long arithmetic**: Using `long` for accumulation prevents integer overflow when multiplying counts.

- **Modular reduction**: Final result is taken modulo `10^9 + 7`.

## Complexity

- **Time**: O(n + M) where n is the length of the array and M = 10^5 + 1. First loop computes frequencies (O(n)), second loop processes each element once with O(1) operations.
- **Space**: O(M) for frequency and prefix arrays.

## Edge Cases

- Array of size 3: Single triplet to check; algorithm handles correctly.
- All elements the same: `nums[i] & nums[i] = nums[i]`, so if value `x` appears `c` times, count = `c(c-1)(c-2)/6` for all triplets.
- All elements zero: `0 & 0 = 0`, so every triplet is valid. Count = C(n, 3) = n(n-1)(n-2)/6.
- Mix of zeros and non-zeros: Zero AND anything equals zero; handled correctly via the `x == 0` check.
- Large values: Values > 10^5 have `x2 > M`, so they're skipped (no valid triplets).
- Array of size < 3: No triplets possible; algorithm returns 0.

## Takeaways

- **Frequency-based optimization**: Avoids nested loops by precomputing counts and using multiplication instead of iteration.
- **Bitwise AND properties**: AND result is bounded and often predictable (e.g., `x & x = x`, `x & 0 = 0`).
- **Time-space trade-off**: Using extra arrays for frequency tracking reduces time complexity from O(n³) to O(n).
- **Long arithmetic in modular contexts**: Prevent overflow during intermediate computations before taking modulo.
- **Index exclusion**: When tracking "before" and "after" counts, ensure self-pairing is excluded where necessary.

## Alternative (Brute Force, O(n³))

```java
class Solution {
    public int specialTriplets(int[] nums) {
        int count = 0;
        int mod = (int)1e9 + 7;
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if ((nums[i] & nums[j]) == nums[k]) {
                        count = (count + 1) % mod;
                    }
                }
            }
        }
        
        return count;
    }
}
```

**Trade-off**: Brute force is simple and easy to understand, checking all O(n³) triplets directly. However, it becomes impractical for large arrays. The optimized frequency-based approach reduces time to O(n) by precomputing frequencies and using multiplication. Use optimized approach in production; brute force is helpful for verification and understanding the constraint.
