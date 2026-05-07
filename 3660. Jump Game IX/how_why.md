# How Why - Explanation 3660. Jump Game IX

[3660. Jump Game IX](https://leetcode.com/problems/jump-game-ix/)

## Problem

Given an array `nums`, build an answer array where each position reflects the maximum value reachable under the problem's segment rule. The provided solutions compute the result by scanning prefix maxima and suffix minima and merging segments when necessary.

## Intuition

The key observation is that the array can be partitioned into regions where the prefix maximum is still greater than some future suffix minimum. In those regions, the current answer should carry forward from the right, because the segment is merged with a future stronger segment.

If the prefix maximum is not greater than the suffix minimum ahead of it, then the current prefix stands on its own and its answer is just the prefix maximum.

## Approach (Prefix Max + Suffix Min)

1. Compute `pre[i]` as the maximum value in `nums[0..i]`.
2. Compute `suf[i]` as the minimum value in `nums[i..n-1]`.
3. Set the last answer to the last prefix maximum.
4. Traverse from right to left:
   - If `pre[i] > suf[i + 1]`, the current segment merges with the next one, so `res[i] = res[i + 1]`.
   - Otherwise, the current segment is independent, so `res[i] = pre[i]`.
5. Return the result array.

This is implemented in [3660. Jump Game IX/Solution.java](3660.%20Jump%20Game%20IX/Solution.java).

## Why This Works

`pre[i]` tells us the best value available up to index `i`. `suf[i + 1]` tells us the smallest value available after `i`. If the prefix maximum is greater than a later suffix minimum, then the current segment cannot be finalized independently: it must merge with a later segment that can influence the result.

If that condition is false, the prefix already forms a stable segment and its best value is simply the prefix maximum.

## Complexity

- Time: `O(n)`.
- Space: `O(n)` for the helper arrays.

## Edge Cases

- Empty array: `Solution3` explicitly returns an empty array.
- Strictly increasing input: every prefix becomes its own segment.
- Strictly decreasing input: many positions collapse to the rightmost merged answer.

## Notes on the Three Implementations

- `Solution` uses explicit `pre`, `suf`, and `res` arrays.
- `Solution2` compresses the same idea into fewer arrays by tracking the running suffix minimum.
- `Solution3` is the clearest version of the same prefix/suffix merge logic.
