# 2110. Number of Smooth Descent Periods of a Stock — how/why

## Recap

Given `prices`, a smooth descent period is a contiguous subarray where every adjacent pair decreases by exactly 1 (i.e., `prices[i] == prices[i+1] + 1`). Single days always count as descent periods. Return the total number of smooth descent periods.

## Intuition

Descent periods form runs where each next price is exactly previous minus one. If a run has length `L`, then the number of descent subarrays inside it is the sum of 1..L, which equals `L * (L + 1) / 2`. Rather than precomputing all run lengths, we can accumulate on the fly:

- Maintain `count` = length of current descent run ending at `i`.
- If `prices[i] == prices[i+1] + 1`, extend the run: `count++`; else reset `count = 1` for the next start.
- Add `count` to the answer each step, starting with `ans = 1` for the first day.

This works because every time we extend a run by one, we add exactly the number of new subarrays ending at the current position.

## Approach

- Initialize `ans = 1`, `count = 1`.
- Iterate `i = 0..n-2`:
  - If `prices[i] == prices[i+1] + 1`, `count++`; else `count = 1`.
  - `ans += count`.
- Return `ans`.

## Code (Java)

```java
class Solution {
    public long getDescentPeriods(int[] prices) {
        long ans = 1, count = 1;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] == prices[i + 1] + 1) count++;
            else count = 1;
            ans += count;
        }
        return ans;
    }
}
```

## Correctness

- Each single day counts: initial `ans = 1`, `count = 1` ensures that.
- When `prices[i] == prices[i+1] + 1`, extending the run increases the number of subarrays ending at `i+1` by exactly `count`.
- When the chain breaks, resetting `count = 1` starts a new run, correctly counting the single-day period.
- Equivalent to summing `L * (L + 1) / 2` over all maximal descent runs, but done online in O(n).

## Complexity

- Time: O(n)
- Space: O(1)

## Edge Cases

- Empty `prices`: Typically unspecified; if allowed, total is 0 (adjust by guarding `n == 0`).
- All equal: Only single-day periods count → `n`.
- Strictly decreasing by 1: One long run of length `n` → `n * (n + 1) / 2`.
- Any break (difference not equal to 1): Run resets and counts restart.

## Takeaways

- Run-length accumulation is a powerful linear-time technique for counting subarrays with local constraints.
- Online accumulation avoids extra passes and formulas by adding the number of new subarrays at each step.
