# 3381. Maximum Subarray Sum With Length Divisible by K — how/why

## Recap

Given an integer array `nums` and an integer `k`, find the maximum subarray sum among all subarrays whose length is divisible by `k`. A subarray is a contiguous slice `nums[i..j]`.

## Intuition

Let `P[t]` be the prefix sum up to index `t-1` (so `P[0] = 0`, `P[j+1] = nums[0]+...+nums[j]`). The sum of a subarray `i..j` is `P[j+1] - P[i]`. Its length is `j - i + 1`. The condition “length divisible by `k`” means `(j - i + 1) % k == 0`, which is equivalent to `(j+1) % k == i % k`. So for each `j`, we want to subtract the smallest `P[i]` among all `i` with `i % k == (j+1) % k`.

## Approach

1. Compute running prefix sum `pref` while iterating `j` from `0` to `n-1`.
2. Maintain an array `minPref[0..k-1]` where `minPref[r]` is the minimum prefix sum seen at any index `i` with `i % k == r`.
3. For each position `j`, let `pref = P[j+1]` and `r = (j+1) % k`.
   - Candidate best sum ending at `j` is `pref - minPref[r]`.
   - Update the answer with this candidate.
   - Update `minPref[r] = min(minPref[r], pref)`.
4. Initialize `minPref[0] = 0` (since `P[0]=0` and index `0 % k == 0`), and all others to `+∞`.

This ensures that for any end index `j`, we subtract the smallest valid starting-prefix with matching modulo, maximizing the subarray sum subject to the length constraint.

## Code (Java)

```java
class Solution {
    public long maxSubarraySumLengthDivisibleByK(int[] nums, int k) {
        int n = nums.length;
        long[] minPref = new long[k];
        for (int r = 0; r < k; r++) minPref[r] = Long.MAX_VALUE / 4;
        minPref[0] = 0; // P[0] at index 0 (0 % k == 0)

        long ans = Long.MIN_VALUE / 4;
        long pref = 0;
        for (int j = 0; j < n; j++) {
            pref += nums[j];
            int r = (int) ((j + 1L) % k);
            // Candidate using the minimal prefix of same modulo class
            if (minPref[r] != Long.MAX_VALUE / 4) {
                ans = Math.max(ans, pref - minPref[r]);
            }
            // Update minimal prefix for this modulo class
            if (pref < minPref[r]) minPref[r] = pref;
        }

        return ans == Long.MIN_VALUE / 4 ? 0 : ans;
    }
}
```

## Correctness

- Length condition: `(j+1) % k == i % k` ensures `(j - i + 1) % k == 0`. By grouping prefix indices by `i % k`, we only consider valid starts for a given end.
- Optimality: For a fixed end `j` and modulo class `r`, the maximum `P[j+1] - P[i]` is achieved by the minimum possible `P[i]` among indices `i` with `i % k == r`. Tracking `minPref[r]` maintains exactly that.
- Coverage: Iterating all `j` and all modulo classes implicitly via `r = (j+1) % k` examines all subarrays whose length is divisible by `k`.

## Complexity

- Time: `O(n)` — single pass with `O(1)` work per element.
- Space: `O(k)` — to keep one minimum per modulo class.

## Edge Cases

- All negative numbers: The algorithm still returns the best (least negative) valid subarray; if no valid subarray exists (which cannot happen since any length multiple of `k` exists for sufficiently large ranges), fallback returns 0 in the code above; you can remove that fallback if a valid subarray must be chosen.
- `k = 1`: Any length is divisible by 1; this reduces to the classic maximum subarray sum: `minPref[0]` is the global minimum prefix.
- Short arrays: If `n < k`, valid subarrays may still exist (e.g., length 0 modulo `k` can be 0, k, 2k, ...); our modulo-based grouping handles this uniformly.
- Large values: Use `long` to avoid overflow in prefix sums.

## Takeaways

- Divisibility constraints on subarray length often translate to constraints on prefix-index modulo classes.
- Maximizing `P[j+1] - P[i]` with a constraint on `i` reduces to keeping the minimum valid `P[i]` seen so far.
- Prefix sums and remainder bucketing give an elegant `O(n)` solution with `O(k)` space.
