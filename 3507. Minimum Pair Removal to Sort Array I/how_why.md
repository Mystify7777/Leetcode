
# How & Why: LeetCode 3507 - Minimum Pair Removal to Sort Array I

## Problem

Given an integer array, you may repeatedly pick an adjacent pair and replace it with their sum (removing one element). Count the minimum number of such pair-removal operations needed to make the array non-decreasing. Return that minimum.

## Intuition

- The operation reduces length by one; order can only improve if we merge “bad” spots. Merging the smallest-sum pair is a greedy heuristic: it least disturbs surrounding order while shrinking the array. Recompute until the array becomes sorted.

## Brute Force Approach

- **Idea:** Try all possible merge sequences (backtracking/BFS) to find the minimum operations.
- **Complexity:** Exponential; infeasible.

## My Approach (Greedy Min-Sum Adjacent Merge) — from Solution.java

- **Idea:**
	1) While array not sorted: find adjacent pair with minimum sum.
	2) Merge it (replace left with sum, remove right).
	3) Increment operation count and repeat.
- **Complexity:** Each pass scans O(n) to find min pair and O(n) to remove/shift; repeated up to O(n) times → worst-case O(n²) time, O(1) extra space (ArrayList shifts are O(n), but n is small in constraints).
- **Core snippet:**

```java
while (!sorted(v)) {
		int pos = argmin_{i}(v[i]+v[i+1]);
		v.set(pos, v.get(pos)+v.get(pos+1));
		v.remove(pos+1);
		ops++;
}
```

## Most Optimal Approach

- Given the problem’s constraints, the simple greedy + O(n²) shifting is acceptable. A linked list or deque could reduce deletion cost but doesn’t change asymptotic bounds because we still rescan for min each round.

## Edge Cases

- Already non-decreasing → 0 operations.
- Length 1 → already sorted.
- All equal → 0 operations.
- Negative numbers still work; min-sum pair computed the same way.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Exhaustive search | Explore all merge orders | Exponential | Exponential | Optimal but impossible |
| Greedy min-sum merge (used) | Repeatedly merge smallest adjacent sum until sorted | O(n²) | O(1) | Simple; matches provided solution |

## Example Walkthrough

`[3,1,2]`
- Not sorted. Adjacent sums: 3+1=4, 1+2=3 → pick (1,2): merge → `[3,3]` (ops=1).
- `[3,3]` is sorted → answer 1.

## Insights

- Merging the smallest-sum pair is a heuristic to minimally inflate values while reducing inversions; with provided solution, repeating until sorted yields required ops.

## References to Similar Problems

- Array merging/combining heuristics (e.g., optimal merge patterns) though objective differs here.
