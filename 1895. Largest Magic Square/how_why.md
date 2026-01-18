
# How & Why: LeetCode 1895 - Largest Magic Square

## Problem

Given an `m x n` integer grid, a magic square is a `k x k` submatrix whose all rows, columns, and both diagonals sum to the same value. Find the largest `k` possible; return its size.

## Intuition

- For any candidate top-left `(r,c)` and size `k`, we need all row sums, col sums, and the two diagonals equal. Checking naively per cell is costly; prefix sums can make row/col sums O(1) per query.

## Brute Force Approach

- **Idea:** Try every square size and position; for each, sum rows, cols, diags directly.
- **Complexity:** Time $O(m n k^2)$ per size → up to $O(m n \cdot \min(m,n)^2)$, too slow.

## My Approach (Direct Sum per Square) — from Solution.java

- **Idea:** Iterate sizes from largest down. For each top-left, compute each row sum, then col sums, then diagonals directly. First valid square of current size ends search.
- **Complexity:** Time $O(m n k)$ per size → in worst case $O(m n \cdot \min(m,n))^2$; Space $O(1)$ extra.
- **Core snippet:**

```java
for (int k=minSide; k>0; k--) {
	for (int r=0; r+k<=m; r++) for (int c=0; c+k<=n; c++)
		if (isValid(grid,r,c,k)) return k;
}

// isValid sums rows, cols, both diags and compares to first row sum
```

## Most Optimal Approach

- Use row/col prefix sums to query any row or column segment in O(1). Then each square check is $O(k)$ (rows+cols+2 diagonals), reducing total to ~$O(m n \min(m,n))$. See Solution2’s prefix-sum version.

## Edge Cases

- Size 1 is always magic; answer at least 1.
- Non-square grid handled by scanning up to `min(m,n)`.
- Negative numbers are fine; equality of sums still works.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Naive sums | Sum all cells per row/col/diag each time | O(mn k^2) | O(1) | Too slow |
| Direct per-line sums (used) | Recompute lines per square | O(mn k) | O(1) | Simpler but still heavy |
| Prefix sums (Solution2) | O(1) row/col queries, O(k) per square | O(mn k) with smaller constant | O(mn) | Faster in practice |

## Example Walkthrough

Grid:

```m
7 1 4 5 6
2 5 1 6 4
1 5 4 3 2
1 2 7 3 4
4 3 2 2 5
```

- Start k=5 fails. For k=3, square at (1,1) sums rows= cols= diags=12 → valid; answer 3.

## Insights

- Checking sizes from largest down allows early exit.
- Prefix sums turn repeated line-sum work into O(1) queries, a common grid-optimization pattern.

## References to Similar Problems

- 3047 / 2975 / 2943 (area/rectangle checks with prefix optimizations)
- 1444. Number of Ways of Cutting a Pizza (2D prefix sums for sub-rect queries)
