# 1337. The K Weakest Rows in a Matrix

## Problem

Given an `m x n` binary matrix where each row has 1s (soldiers) followed by 0s (civilians), return the indices of the `k` weakest rows ordered from weakest to strongest. A row is weaker if it has fewer soldiers; ties are broken by smaller row index.

## Approach (count + encoded sort)

- Count soldiers per row by scanning until the first 0 (rows are sorted), giving `soldiers = j`.
- Encode sort key in a single integer: `score = soldiers * rows + rowIndex` so that fewer soldiers sort first and ties keep smaller index ahead without a custom comparator.
- Sort the `score` array; then recover row indices with `score % rows` and take the first `k` entries.

## Why the encoding works

- Primary key: `soldiers` (multiplying by constant `rows` preserves ordering).
- Secondary key: `rowIndex` is added, so within equal soldier counts smaller indices stay smaller.
- Using the product avoids building pairs or a comparator; plain `Arrays.sort` suffices.

## Example

`mat = [[1,1,0,0],[1,1,1,1],[1,0,0,0],[1,1,0,0]], k = 2`

| row | soldiers | score (rows=4) |
| --- | -------- | -------------- |
| 0   | 2        | 2*4 + 0 = 8    |
| 1   | 4        | 4*4 + 1 = 17   |
| 2   | 1        | 1*4 + 2 = 6    |
| 3   | 2        | 2*4 + 3 = 11   |

Sorted scores: `[6, 8, 11, 17]` → indices `[2, 0, 3, 1]` → take first `k`: `[2, 0]`.

## Complexity

- Time: O(m * n) to count + O(m log m) to sort.
- Space: O(m) for the score array.

## Edge considerations

- Rows may be full of 1s or 0s; the counting loop naturally handles both.
- `k` is guaranteed ≤ rows, so slicing the first `k` scores is safe.
