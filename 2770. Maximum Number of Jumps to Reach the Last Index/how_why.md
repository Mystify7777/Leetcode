# How Why - Explanation 2770. Maximum Number of Jumps to Reach the Last Index

[2770. Maximum Number of Jumps to Reach the Last Index](https://leetcode.com/problems/maximum-number-of-jumps-to-reach-the-last-index/)

## Problem

Given an array `nums` and an integer `target`, you start at index `0` and want to reach index `n - 1`.

You may jump from index `i` to index `j` if `j > i` and `|nums[j] - nums[i]| <= target`.

Return the maximum number of jumps you can make to reach the last index, or `-1` if it is impossible.

## Intuition

This is a longest-path-in-DAG problem, because jumps only move forward. Since every valid jump goes from a smaller index to a larger index, dynamic programming works naturally.

For each index, we want the best number of jumps needed to reach it. Then we try to extend that answer to future indices that are reachable under the `target` constraint.

## Approach (Bottom-Up DP)

1. Let `dp[i]` be the maximum number of jumps needed to reach index `i`.
2. Initialize all entries to unreachable, except `dp[0] = 0`.
3. For each `i`, check all earlier indices `j < i`.
4. If `|nums[i] - nums[j]| <= target` and `dp[j]` is reachable, update:
   `dp[i] = max(dp[i], dp[j] + 1)`.
5. Return `dp[n - 1]`.

This is implemented in [2770. Maximum Number of Jumps to Reach the Last Index/Solution.java](2770.%20Maximum%20Number%20of%20Jumps%20to%20Reach%20the%20Last%20Index/Solution.java).

## Approach (Top-Down DP + Memoization)

`Solution2` uses recursion:

1. Define `solve(i)` as the maximum number of jumps from index `i` to the end.
2. If `i == n - 1`, return `0`.
3. For every future index `j > i` that satisfies the jump condition, try `1 + solve(j)`.
4. Memoize the result for each index so each subproblem is solved once.

This computes the same answer as the bottom-up version, but in a recursive style.

## Why This Works

Because jumps only move to the right, the graph has no cycles. That means we can safely compute answers in increasing index order or recursively with memoization.

The DP transition is correct because any optimal path to index `i` must come from some earlier valid index `j`, and the best such path is the maximum over all valid predecessors.

## Complexity

- Bottom-up solution: `O(n^2)` time, `O(n)` space.
- Top-down solution: `O(n^2)` time in the worst case, `O(n)` space for memoization plus recursion stack.

## Edge Cases

- If index `0` cannot reach any later index, the result is `-1`.
- If `nums.length == 1`, the answer is `0` because we are already at the end.
- If `nums.length == 2`, the answer is `1` only when the jump is valid.

## Notes on the Three Implementations

- `Solution1` is the classic bottom-up DP.
- `Solution2` is the top-down memoized version.
- `Solution` is another bottom-up implementation using `Integer.MIN_VALUE` as the unreachable sentinel.
