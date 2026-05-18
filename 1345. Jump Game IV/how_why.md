# 1345. Jump Game IV

This problem asks for the minimum number of jumps to move from index `0` to index `n-1` when you can jump to adjacent indices or to any other index with the same value.

## Approach

- Build a map from value -> list of indices that contain that value.
- Run BFS from index `0` (or `n-1` in the second solution) tracking distance.
- For each popped index, try `i-1`, `i+1`, and all indices with the same value.
- After processing a value's list, clear or remove it from the map to avoid repeated O(n) work for that value.

## Complexity

- Time: `O(n)` amortized — each index and each value-list is processed at most once.
- Space: `O(n)` for the map, queue, and visited array.
