# 3578. Count Partitions With Max-Min Difference at Most K — how/why

## Recap

Given an integer array `nums` and an integer `k`, count the number of ways to partition the array into two non-empty contiguous subarrays such that the difference between the maximum and minimum values in the entire array is at most `k`. Return the result modulo `10^9 + 7`.

## Intuition

A partition at index `i` is valid if `max(nums) - min(nums) ≤ k` across the entire array. Since the max-min comparison spans the whole array (not just each part), we need to track which partitions keep the global max-min difference within `k`.

Key observation: As we extend the array from left to right, we maintain a sliding window of valid starting positions using a two-pointer technique. For each ending position `j`, we find the rightmost starting position `i` such that `max(A[i..j]) - min(A[i..j]) ≤ k`. All valid partitions ending at `j` must start somewhere in `[0, i]`.

We use deques to efficiently track the maximum and minimum values in the current window, enabling us to compute valid partition counts via prefix sums.

## Approach

**Sliding Window with Deque + Dynamic Programming**:

1. Initialize `dp[i]` = count of valid partitions at position `i`, `acc` = running sum of dp values.
2. Maintain two deques: `minq` and `maxq` storing indices of minimums and maximums in the current window.
3. For each position `j`:
   - Add `A[j]` to both deques, removing elements that violate monotonicity:
     - For `maxq`: remove indices where `A[index] ≤ A[j]` (keep decreasing order).
     - For `minq`: remove indices where `A[index] ≥ A[j]` (keep increasing order).
   - Shrink the window from left (increase `i`) while `max - min > k`:
     - Subtract `dp[i]` from `acc` (those partitions are no longer valid).
     - Remove outdated indices from front of deques.
   - Set `dp[j+1] = acc` (count of valid partitions ending at `j`).
   - Update `acc += dp[j+1]` (prepares for next iteration).
4. Return `dp[n]`.

**Why it works**:

- `dp[j+1]` = count of valid starting positions `[0, i]` for partitions ending at `j`.
- `acc` maintains the sum of valid counts: `dp[0] + dp[1] + ... + dp[j]`.
- When the window becomes invalid, we remove contributions from positions that can no longer form valid partitions.
- Final answer `dp[n]` represents all valid partitions across the entire array.

## Code (Java)

```java
class Solution {
    public int countPartitions(int[] A, int k) {
        int n = A.length, mod = 1_000_000_007, acc = 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;

        Deque<Integer> minq = new ArrayDeque<>();
        Deque<Integer> maxq = new ArrayDeque<>();
        for (int i = 0, j = 0; j < n; ++j) {
            while (!maxq.isEmpty() && A[j] > A[maxq.peekLast()])
                maxq.pollLast();
            maxq.addLast(j);

            while (!minq.isEmpty() && A[j] < A[minq.peekLast()])
                minq.pollLast();
            minq.addLast(j);

            while (A[maxq.peekFirst()] - A[minq.peekFirst()] > k) {
                acc = (acc - dp[i++] + mod) % mod;
                if (minq.peekFirst() < i)
                    minq.pollFirst();
                if (maxq.peekFirst() < i)
                    maxq.pollFirst();
            }

            dp[j + 1] = acc;
            acc = (acc + dp[j + 1]) % mod;
        }

        return dp[n];
    }
}
```

## Correctness

- **Deque maintenance**: Deques maintain indices in order of element values (decreasing for max, increasing for min), allowing O(1) retrieval of current max/min.
- **Sliding window validity**: When `max - min > k`, we move left pointer `i` forward, removing invalid starting positions.
- **DP accumulation**: `acc` accumulates all valid starting positions. When a position becomes invalid, we subtract its contribution.
- **Modular arithmetic**: All operations are performed modulo `10^9 + 7`, including subtraction (add `mod` before taking modulo to handle negative values).
- **Partition counting**: Each `dp[j+1]` counts how many valid partitions end at position `j`, derived from the sliding window state.

## Complexity

- **Time**: `O(n)` each element is added and removed from deques at most once. DP updates are amortized O(1).
- **Space**: `O(n)` for `dp` array and deques.

## Edge Cases

- Array of size 2: Only one partition possible; check if it's valid.
- All elements equal: `max - min = 0 ≤ k` for all partitions → answer is `n-1`.
- `k = 0`: Only partitions where all elements are the same are valid.
- Large differences: If `max - min > k` everywhere, answer is 0.
- Negative numbers: Min/max comparisons work correctly; modular arithmetic handles them.
- `k` very large: All `n-1` partitions are valid.
- Duplicate elements: Deques handle equal values; removes don't occur until strictly greater/less is found.

## Takeaways

- **Deque for sliding window max/min**: Efficient O(1) retrieval vs O(log n) heap or O(n) naive scan.
- **Combining sliding window with DP**: Tracks valid counts incrementally rather than checking each partition independently.
- **Prefix sum optimization**: `acc` maintains running sum, avoiding recalculation for each window state.
- **Modular arithmetic care**: Subtraction requires adding `mod` to prevent negative results before taking modulo.
- **Amortized analysis**: Total operations are O(n) because each element is processed at most twice (once added, once removed).
- This pattern extends to other "max/min difference" problems and sliding window + DP combinations.

## Alternative (Brute Force, O(n²))

```java
class Solution {
    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        int mod = 1_000_000_007;
        int count = 0;

        for (int i = 0; i < n - 1; i++) {
            int minVal = Integer.MAX_VALUE;
            int maxVal = Integer.MIN_VALUE;
            
            for (int j = 0; j < n; j++) {
                minVal = Math.min(minVal, nums[j]);
                maxVal = Math.max(maxVal, nums[j]);
            }
            
            if (maxVal - minVal <= k) {
                count = (count + 1) % mod;
            }
        }

        return count;
    }
}
```

**Trade-off**: Brute force is simpler to understand but recalculates min/max for the entire array at each partition point, achieving O(n²) time complexity. The optimized deque solution maintains sliding window state, reducing time to O(n) and enabling processing of large arrays efficiently. Use deque approach in production; brute force is helpful for understanding the problem.
