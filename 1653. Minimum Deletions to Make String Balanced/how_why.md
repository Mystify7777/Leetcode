# How Why Explanation - 1653. Minimum Deletions to Make String Balanced

## Problem

Given a string `s` of `'a'` and `'b'`, remove the fewest characters so that the resulting string is **balanced**: no `'b'` appears after an `'a'` (i.e., all `'a'`s come before all `'b'`s). Return the minimum deletions.

## Intuition

Scan left to right tracking how many `'b'`s we keep. When we see an `'a'` after some `'b'`s, we must fix the order: either delete this `'a'` or delete a previous `'b'`. The greedy choice: delete whichever costs 1 now while minimizing future issues. If we delete the current `'a'`, we pay +1 deletion; if instead we delete one earlier `'b'`, we reduce the kept `'b'` count by 1. Keeping the minimum of these as we go yields an optimal linear solution.

## Brute Force (Not Used)

- Try every split `prefix as b's, suffix as a's`, or every subsequence; exponential or $O(n^2)$ with DP—unnecessary.

## Approach (One-Pass Greedy)

Maintain two counters:
1. `countB`: number of `'b'`s kept so far.
2. `res`: minimum deletions up to current position.

For each character:

- If `'b'`: increment `countB` (it is fine to keep).
- If `'a'` and `countB > 0`: either delete this `'a'` (`res+1`) or delete one prior `'b'` (`countB-1` net by decrementing `countB`). The optimal update is `res = res + 1` then `countB--` to reflect removing one stored `'b'`. This matches the code's operations.

Why it works: At any point, an out-of-order `'a'` can be fixed by removing it or one earlier `'b'`; both cost 1 deletion. Choosing to “remove a previous `'b'`” via `countB--` preserves the current `'a'` and keeps `res` minimal. This local decision is globally optimal because future characters only care about counts, not positions.

## Complexity

- Time: $O(n)$ one pass.
- Space: $O(1)$.

## Optimality

Any solution must read all chars; one pass with constant state is optimal. The greedy choice mirrors the minimal DP recurrence `dp[i] = min(dp[i-1] + (s[i]=='a'), countB)` simplified into counters.

## Edge Cases

- All `'a'` or all `'b'`: already balanced, deletions = 0.
- Alternating like `"abab..."`: deletions accumulate; the greedy still yields min.
- Empty string: deletions = 0.

## Comparison Table

| Aspect | Greedy counters (Solution) | DP over prefix splits (not shown) |
| --- | --- | --- |
| Time | $O(n)$ | $O(n)$ |
| Space | $O(1)$ | $O(n)$ |
| Implementation | Simple counters | Prefix arrays / transitions |

## Key Snippet (Java)

```java
int res = 0, count = 0;
for (char c : s.toCharArray()) {
	if (c == 'b') count++;
	else if (count != 0) { // c == 'a' after some b's
		res++;    // delete this 'a'
		count--;  // or equivalently delete one prior 'b'
	}
}
return res;
```

## Example Walkthrough

`s = "aababbab"`

- Processed greedily yields `res = 2`: delete the first out-of-order `'a'` after `bb` and one more later conflict, resulting in `aaabbb` (balanced).

## Insights

- The counter decrement on seeing an out-of-order `'a'` represents “undoing” a prior `'b'` to keep the string balanced.
- Equivalent DP: `delA[i]` (min deletions to make prefix end with `'a'`) and `delB[i]` (end with `'b'`), but it collapses to this two-counter greedy.

## References

- Solution implementation in [1653. Minimum Deletions to Make String Balanced/Solution.java](1653.%20Minimum%20Deletions%20to%20Make%20String%20Balanced/Solution.java)
