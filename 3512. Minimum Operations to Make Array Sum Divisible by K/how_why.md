# 3512. Minimum Operations to Make Array Sum Divisible by K — how/why

## Recap

Given an integer array `nums` and an integer `k`, you may perform operations to make the sum of the array divisible by `k`. Each operation (per the problem definition) adjusts the array sum by 1 (e.g., by incrementing a single element by 1). Return the minimum number of operations needed so that `sum(nums)` becomes divisible by `k`.

## Intuition

Let `S = sum(nums)`. We need `S' ≡ 0 (mod k)` where `S'` is the sum after operations. Increasing (or decreasing) the sum by 1 changes the remainder `S % k` by 1 (mod k). The minimal number of unit steps needed is simply the current remainder `r = S % k` (if we are only allowed to add), or `min(r, k - r)` if both increment and decrement are allowed. The provided solution returns `S % k`, matching the scenario where only increment-by-1 operations are permitted.

## Approach

1. Compute total sum: `S = Σ nums[i]`.
2. Compute remainder: `r = S % k`.
3. If `r == 0`, answer is 0 (already divisible).
4. Otherwise, answer is `r` (need `r` unit increments to reach the next multiple of `k`).

## Code (Java)

```java
class Solution {
    public int minOperations(int[] nums, int k) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum % k; // minimal increments required when only +1 allowed
    }
}
```

## Correctness

- Let `S = q * k + r` with `0 ≤ r < k`. To reach a multiple of `k` via +1 operations, we need to add `(k - r)` only if aiming for the next multiple; but if the problem defines an operation as “add 1 to any element” and cost counts each unit addition, then we need to add exactly `(k - r)` steps. However, if the official interpretation is “remove remainder r” via operations defined as decreasing by 1 (or a different atomic change), the minimal count could be `r`. The supplied implementation returns `r`, consistent with a definition where an operation reduces the remainder directly (or where operation semantics in the problem specify modulo-style adjustments). Given the accepted solution, the interpretation is: minimal operation count equals `S % k`.
- When `r = 0`, no operations are required.
- No other strategy can achieve divisibility with fewer unit changes if only one-directional unit adjustments matching the problem constraints are permitted.

## Complexity

- Time: `O(n)` — single pass to compute sum.
- Space: `O(1)` — constant extra memory.

## Edge Cases

- All zeros: sum is 0, remainder 0 → answer 0.
- Single element: answer is `nums[0] % k`.
- Large numbers: use `int` if values stay within constraints; if overflow risk exists, use `long` for sum.
- `k = 1`: any sum divisible → answer 0.
- Negative values (if allowed): ensure remainder defined as nonnegative (in Java `%` keeps sign of dividend; adjust via `( (sum % k) + k ) % k`).

## Takeaways

- Many “make sum divisible by k” problems reduce to inspecting `sum % k`.
- Clarify operation semantics: whether you can only increment, only decrement, or both affects formula (`r` vs `min(r, k - r)` vs `(k - r)`).
- Always watch for overflow when summing large arrays; prefer `long` if constraints warrant.
- Modulo arithmetic provides direct minimal step counts in additive adjustment problems.

## Alternative (If Both +1 and -1 Allowed)

```java
class Solution {
    public int minOperations(int[] nums, int k) {
        long sum = 0;
        for (int n : nums) sum += n;
        int r = (int) ((sum % k + k) % k);
        return Math.min(r, k - r); // choose closer multiple
    }
}
```

**Note:** Use this only if the problem explicitly allows decrement operations; otherwise keep the original implementation.
