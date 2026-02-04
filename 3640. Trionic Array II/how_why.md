# How Why Explanation - 3640. Trionic Array II

## Problem

Find a contiguous subarray that is **strictly increasing**, then **strictly decreasing**, then **strictly increasing** (three non-empty phases) and has the maximum possible sum. Return that maximum sum.

## Intuition

A valid trionic subarray has a middle strictly-decreasing block. If we fix that middle block, the best way to grow the left phase is to take the maximum-sum strictly-increasing suffix immediately to its left; similarly, the best right phase is the maximum-sum strictly-increasing prefix immediately to its right. So we can precompute, for every index, the best increasing-sum segment ending at it and starting at it, then scan all decreasing blocks to combine these pieces.

## Brute Force (Not Used)

- Enumerate all triples of cut points `(L, M, R)` and check if the three segments satisfy inc/dec/inc while tracking sums.
- Complexity: $O(n^3)$ naive (or $O(n^2)$ with prefix sums), infeasible.

## Approach (Precompute inc-sums + scan dec blocks)

1. **Left inc DP (`maxEndingAt`)**: For each index `i`, keep the best sum of a strictly increasing subarray ending at `i`. If `nums[i-1] < nums[i]`, extend from `i-1` when beneficial (`>0`).
2. **Right inc DP (`maxStartingAt`)**: From right to left, best sum of a strictly increasing subarray starting at `i`. If `nums[i] < nums[i+1]`, extend to `i+1` when beneficial.
3. **Decompose decreasing blocks**: Split the array into maximal strictly decreasing segments `(p..q)` and store their sums.
4. **Combine**: For each decreasing block `(p..q)`, it can be the middle if it has a smaller neighbor on the left and a larger neighbor on the right (`nums[p-1] < nums[p]` and `nums[q] < nums[q+1]`). Candidate sum = `maxEndingAt[p-1] + sum(p..q) + maxStartingAt[q+1]`. Take the maximum over all candidates.

Why it works: Once the middle decreasing segment is fixed, the optimal flanking segments are independent and captured by the precomputed best increasing sums. Scanning all decreasing blocks ensures every possible middle is considered exactly once.

## Complexity

- Time: $O(n)$ — two passes for inc DP, one pass to decompose and evaluate.
- Space: $O(n)` for the two DP arrays and the list of blocks (can be $O(1)$ if blocks are streamed).

## Optimality

Linear time is optimal up to constants since every element must be inspected. Precomputing inc-sums avoids nested scans when evaluating each decreasing block.

## Edge Cases

- No valid trionic shape (e.g., purely monotone) → algorithm would find no qualifying block; ensure caller handles (here `ans` would stay very negative).
- Plateaus (equal neighbors) break strictness and split blocks.
- Small `n < 3` cannot form three non-empty phases.

## Comparison Table

| Aspect | Precompute inc-sums + dec blocks (Solution) | Two-pointer grow/shrink (Solution2) |
| --- | --- | --- |
| Left/right gain | `maxEndingAt`, `maxStartingAt` DPs | On-the-fly accumulation | 
| Middle handling | Decompose all decreasing blocks | Expand around a decreasing run | 
| Time/Space | $O(n)$ / $O(n)$ (DP arrays) | $O(n)$ / $O(1)$ extra |
| Strictness enforcement | By block decomposition | By pointer movement and checks |

## Key Snippet (Java)

```java
// Precompute best increasing sums ending/starting at each index
for (int i = 0; i < n; i++) {
	maxEndingAt[i] = nums[i];
	if (i > 0 && nums[i - 1] < nums[i] && maxEndingAt[i - 1] > 0) {
		maxEndingAt[i] += maxEndingAt[i - 1];
	}
}
for (int i = n - 1; i >= 0; i--) {
	maxStartingAt[i] = nums[i];
	if (i < n - 1 && nums[i] < nums[i + 1] && maxStartingAt[i + 1] > 0) {
		maxStartingAt[i] += maxStartingAt[i + 1];
	}
}

// Evaluate each decreasing block (p..q)
long cand = maxEndingAt[p - 1] + sumBlock + maxStartingAt[q + 1];
ans = Math.max(ans, cand);
```

## Example Walkthrough

`nums = [1, 3, 2, 1, 4, 6, 5]`

- Increasing sums ending: `[1, 4, 2, 1, 5, 11, 5]` (only extend on strict increase with positive gain).
- Increasing sums starting: `[4, 7, 6, 5, 15, 11, 5]` (right-to-left).
- Decreasing blocks: `[3,2,1]` (p=1,q=3,sum=6) and `[6,5]` (p=5,q=6,sum=11).
- Block `[3,2,1]` has left neighbor 1<3 and right neighbor 1<4, candidate = `maxEndingAt[0]=1 + 6 + maxStartingAt[4]=15 = 22`.
- Block `[6,5]` fails right-neighbor check (end of array) so ignored.
- Answer = 22.

## Insights

- Strict monotonicity means equal values split phases; handling `<=` and `>=` carefully is key.
- Precomputing only when an extension increases sum (>0) mirrors Kadane’s idea, keeping best gain into the middle block.
- Decomposing into maximal decreasing blocks reduces evaluation to a single linear sweep.

## References

- Solution implementation in [3640. Trionic Array II/Solution.java](3640.%20Trionic%20Array%20II/Solution.java)
