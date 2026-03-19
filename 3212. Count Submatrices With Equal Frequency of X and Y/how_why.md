# 3212. Count Submatrices With Equal Frequency of X and Y - How Why Explanation

## Goal

Count submatrices whose top-left corner is `(0,0)` where the number of `X` cells equals the number of `Y` cells (and is non-zero).

## Idea in 3 lines

- The total `X` (or `Y`) in the `(0,0)->(i,j)` submatrix is its 2D prefix sum at `(i,j)`.
- Build these prefixes on the fly: keep running row counts, add them into column accumulators to get the full prefix.
- At each `(i,j)`, if prefixX > 0 and prefixX == prefixY, the anchored submatrix qualifies.

## Algorithm (matches `Solution`/`Solution2`)

1. Maintain column accumulators `sumX[j]`, `sumY[j]` for counts up to the current row.
2. For each row `i`:
	- Track row running counts `rx`, `ry` while scanning columns.
	- Update column accumulators: `sumX[j] += rx`, `sumY[j] += ry` (these are prefixes to `(i,j)`).
	- If `sumX[j] > 0` and `sumX[j] == sumY[j]`, increment answer.
3. Return the total count.

## Why it works

- Any submatrix anchored at `(0,0)` is uniquely identified by its bottom-right `(i,j)`; its `X`/`Y` counts are exactly the prefix sums there.
- Row prefix + column accumulation reconstructs 2D prefixes in O(1) per cell without extra passes.
- Equality check at every `(i,j)` covers all candidate anchored submatrices exactly once.

## Complexity

- Time: O(m * n).
- Space: O(n) for the column accumulators.

## Example

Grid = [[X, Y, X], [., X, Y]], `.` = other char, k not used

- Row 0: running rx/ry → [(1,0), (1,1), (2,1)]; prefixes (sumX,sumY) → [(1,0), (1,1), (2,1)] ⇒ count at (0,1).
- Row 1: update cols then row prefixes →
	- (1,0): (1,0) ⇒ no
	- (1,1): (2,2) ⇒ count
	- (1,2): (3,3) ⇒ count
- Total qualifying anchored submatrices: 3 with bottoms at (0,1), (1,1), (1,2).
