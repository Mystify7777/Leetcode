# How Why - Explanation 3629. Minimum Jumps to Reach End via Prime Teleportation

[3629. Minimum Jumps to Reach End via Prime Teleportation](https://leetcode.com/problems/minimum-jumps-to-reach-end-via-prime-teleportation/)

## Problem

Given an array `nums`, start at index `0` and reach index `n - 1` using the fewest jumps.

From index `i`, you can always move to `i - 1` or `i + 1` when those indices are valid. If `nums[i]` is non-prime, you may also teleport to any index whose value is a multiple of `nums[i]`.

Return the minimum number of jumps, or `-1` if the end is unreachable.

## Intuition

This is a shortest-path problem on an implicit graph, so BFS is the right tool.

The challenge is the teleportation rule: from a value `x`, jumping to every index with value divisible by `x` could be expensive if done naively. To make it efficient, group indices by their value and only scan multiples of the current value once.

## Approach (BFS + Value Buckets)

1. Precompute a prime table using a sieve.
2. Build a linked-list style bucket structure:
   - `head[v]` stores the first index whose value is `v`.
   - `next[i]` links indices with the same value.
3. Run BFS from index `0`.
4. For each popped index `dq`:
   - Try `dq - 1` and `dq + 1`.
   - If `nums[dq]` is non-prime and has not been expanded before, teleport:
     - iterate through all multiples of `nums[dq]` up to the maximum value,
     - visit every index stored in the bucket for that multiple,
     - clear each bucket after processing so it is never scanned again.
5. The first time we reach `n - 1`, return its distance.

This is implemented in [3629. Minimum Jumps to Reach End via Prime Teleportation/Solution.java](3629.%20Minimum%20Jumps%20to%20Reach%20End%20via%20Prime%20Teleportation/Solution.java).

## Why This Works

BFS explores states in increasing jump count, so the first time we reach the last index is guaranteed to be optimal.

The teleportation expansion is safe because all indices reachable from a value `x` are discovered when `x` is first processed. Marking values as seen and clearing processed buckets prevents repeated work without missing any reachable nodes.

Prime values cannot teleport, so they only contribute neighbor moves.

## Complexity

- Time: roughly `O(n + maxValue log log maxValue + expansions over multiples)`.
- Space: `O(n + maxValue)` for the queue, buckets, and prime table.

## Edge Cases

- Array of length `1`: already at the end, answer is `0`.
- Prime value at the current index: only left/right moves are allowed.
- Repeated values: bucketed index lists ensure all matching indices are found efficiently.
- Unreachable end: BFS exhausts the queue and returns `-1`.

## Key Insight

The problem becomes efficient once you separate it into two parts:

- BFS for minimum jumps.
- Bucketed value lookup for teleportation targets.
