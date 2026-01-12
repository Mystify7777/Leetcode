
# How & Why: LeetCode 96 - Unique Binary Search Trees

## Problem

Count how many structurally unique BSTs store values 1..n.

## Intuition

- Choosing `i` as root splits the set into left values `< i` and right values `> i`.
- The count for size `k` equals the sum over all root choices of (ways on left) × (ways on right): the Catalan recurrence.

## Brute Force Approach

- **Idea:** Recursive enumeration: for every root `i`, recursively count left/right sizes and multiply.
- **Complexity:** Exponential due to recomputing the same subtree sizes many times.

## My Approach (DP over size) — from Solution.java

- **Idea:** Bottom-up DP `G[k]` = number of unique BSTs with `k` nodes. For each size `i`, iterate roots `j` and add `G[j-1] * G[i-j]`.
- **Complexity:** Time $O(n^2)$, Space $O(n)$.
- **Core snippet:**

```java
int[] G = new int[n+1];
G[0] = G[1] = 1;
for (int i = 2; i <= n; i++) {
	for (int j = 1; j <= i; j++) {
		G[i] += G[j-1] * G[i-j];
	}
}
return G[n];
```

## Most Optimal Approach

- The $O(n^2)$ DP is standard. A closed-form Catalan formula exists but requires big integer arithmetic; for programming constraints, the DP is optimal and simple.

## Edge Cases

- `n = 0` → 1 empty tree by definition.
- `n = 1` → 1 tree.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute recursive | Recompute counts for sub-sizes repeatedly | Exponential | O(n) stack | Not feasible for larger n |
| DP over size (used) | Catalan recurrence with table | O(n^2) | O(n) | Simple and fast enough |
| Catalan closed form | Use $C_n = \frac{1}{n+1}\binom{2n}{n}$ | O(n) if precomputing factorials | O(1) | Needs big ints; easy overflow |

## Example Walkthrough (n = 3)

- Roots tried: 1, 2, 3.
- `i=3`: `G[3] = G[0]G[2] + G[1]G[1] + G[2]G[0] = 1·2 + 1·1 + 2·1 = 5`.
- Matches Catalan `C_3 = 5`.

## Insights

- The sequence is Catalan: $C_n = \frac{1}{n+1}\binom{2n}{n}$.
- Root choice partitions sizes; independence of left/right subtrees makes the product term.

## References to Similar Problems

- Unique Binary Search Trees II (construct all trees)
- Different Ways to Add Parentheses (range split with cartesian product)
- Full Binary Trees generation (Catalan-like counting)
