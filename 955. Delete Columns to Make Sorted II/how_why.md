# How_Why 955. Delete Columns to Make Sorted II

## Problem

Given an array of equal-length strings, delete the minimum number of column indices so that the rows are lexicographically non-decreasing from top to bottom.

## Intuition

Scan columns left-to-right. Keep track of which adjacent row pairs are already confirmed to be in increasing order. Only when a column would violate the order for any still-unconfirmed pair do we have to delete it. If the column is kept, update which pairs are now confirmed.

## Approach

1) Maintain a boolean array `locked` (size `n-1`) where `locked[i]` is true if `rows[i] < rows[i+1]` is already decided by a previous kept column.
2) For each column `c` from 0..m-1:
    - Check all pairs with `!locked[i]`. If any `rows[i][c] > rows[i+1][c]`, the column must be deleted; increment answer and continue to next column.
    - Otherwise, keep the column and mark `locked[i] = true` wherever `rows[i][c] < rows[i+1][c]`.
3) The process ends when all columns are processed; deletions count is minimal by greedy choice.

## Correctness Proof

We only delete a column when keeping it would create a decreasing pair among the still-unlocked adjacencies, which would make a valid ordering impossible. If a column is safe, keeping it can only help: it may lock pairs and never harms existing order. Greedy works because the decision for a column depends solely on previously unlocked pairs, and deleting a safe column cannot reduce future deletions. Thus the algorithm yields the minimal deletions ensuring lexicographic order.

## Complexity

- Time: O(n * m) where n = rows, m = cols (each pair checked per column once).
- Space: O(n) for the `locked` array.

## Key Takeaways

- Greedy with incremental constraints: decide per column using only still-undecided pairs.
- Locking adjacent pairs captures exactly the information needed; no full sort simulation required.
