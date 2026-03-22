# How Why Explanation - 1886. Determine Whether Matrix Can Be Obtained By Rotation

## Goal

Check whether rotating matrix `a` by 0°, 90°, 180°, or 270° can produce matrix `b`.

## Idea in 3 lines

- A 90° clockwise rotation maps `a[i][j]` to `a[n-1-j][i]`; 180° and 270° follow similarly.
- Compare `b` against `a` under each of the four rotation mappings; if any match for every cell, return true.
- Track counts (or booleans) for each rotation as you scan; early elimination possible when mismatches occur.

## Algorithm (matches `Solution`)

1. Let `n = a.length`. Initialize counters for 0°, 90°, 180°, 270° matches.
2. For each cell `(i, j)` in `b`, compare to the corresponding rotated coordinates in `a`:
   - 0° → `a[i][j]`
   - 90° → `a[n-1-j][i]`
   - 180° → `a[n-1-i][n-1-j]`
   - 270° → `a[j][n-1-i]`
Increment a counter when values match.
3. After scanning all cells, if any counter equals `n * n`, `b` is obtainable; else not.

## Why it works

- The coordinate transforms are the exact definitions of the four allowed rotations.
- A full match count of `n*n` for a rotation means every cell aligns, so the matrices are identical under that rotation.
- Checking all four rotations exhausts the allowed operations.

## Complexity

- Time: O(n^2) for a single pass checking all four mappings.
- Space: O(1).

## Example (2x2)

Let `a = [[1,2],[3,4]]`, `b = [[3,1],[4,2]]`.

- 0° check: `b[0][0]=3` vs `a[0][0]=1` ⇒ mismatch, so 0° fails.
- 90° mapping: `b[i][j]` should equal `a[n-1-j][i]`:
  - `b[0][0]=3` vs `a[1][0]=3` ✔
  - `b[0][1]=1` vs `a[0][0]=1` ✔
  - `b[1][0]=4` vs `a[1][1]=4` ✔
  - `b[1][1]=2` vs `a[0][1]=2` ✔ → all cells match, so rotation is possible.
