# How Why Explanation - 1722. Minimize Hamming Distance After Swap Operations

## Problem

You are given arrays `source` and `target` of equal length, and a list of index pairs `allowedSwaps`.

You may swap values in `source` any number of times, but only between indices connected by allowed swaps (directly or indirectly). Return the minimum possible Hamming distance between final `source` and `target`.

## Intuition

Allowed swaps create connected components of indices. Inside one component, values can be permuted arbitrarily, so only the multiset of values in that component matters.

So for each component:

- count how many times each value appears in `source`
- try to match each `target[i]` using that component's available counts
- unmatched targets contribute to Hamming distance

This turns the problem from positional swapping to multiset matching per DSU component.

## Approach (DSU + frequency maps)

1. Build DSU over indices `0..n-1`.
2. Union all pairs in `allowedSwaps`.
3. Group `source` values by component root: `root -> (value -> count)`.
4. Scan indices again:
	- find component root for index `i`
	- if `target[i]` exists in that component count map, consume one occurrence
	- otherwise increment answer
5. Return total unmatched count.

This is exactly the logic in [1722. Minimize Hamming Distance After Swap Operations/Solution.java](1722.%20Minimize%20Hamming%20Distance%20After%20Swap%20Operations/Solution.java#L4-L45).

## Why It Works

- DSU correctly partitions indices into maximal swap-connected components.
- Any permutation is achievable inside a component, so only counts matter.
- Greedily consuming a matching value for each target index is optimal because all matches are interchangeable by value count.
- Every target that cannot be matched in its component must remain mismatched in all valid swap sequences.

Therefore, the unmatched total is the minimum Hamming distance.

## Complexity

- Let `n` be array length, `m` be number of allowed swaps.
- DSU operations: near O((n + m) * alpha(n)).
- Map operations: O(n) average.
- Total: O((n + m) * alpha(n)) average.
- Space: O(n) for DSU + component frequency maps.

## Edge Cases

- No allowed swaps: only exact index matches count, so answer is ordinary Hamming distance.
- Fully connected graph: we can globally permute `source`; answer equals multiset mismatch between full arrays.
- Duplicate values: handled naturally by frequency counts.

## Alternate Approaches

- The alternate implementation in [1722. Minimize Hamming Distance After Swap Operations/Solution.java](1722.%20Minimize%20Hamming%20Distance%20After%20Swap%20Operations/Solution.java#L47-L105) tracks per-component net differences (`source +1`, `target -1`) and sums absolute imbalances.
- BFS/DFS to build connected components also works instead of DSU, but DSU is concise and efficient for repeated unions.
