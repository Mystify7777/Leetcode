# 1306. Jump Game III

Given an array `arr` and a starting index, you can jump either forward or backward by `arr[i]` steps. The goal is to reach an index where `arr[i] == 0`.

## Approach: BFS (Breadth-First Search)

Use BFS to explore all reachable indices:
- Start from the given index and add it to a queue.
- For each index popped from the queue, check if the value is 0 (goal reached).
- Otherwise, add the two possible next indices (`i + arr[i]` and `i - arr[i]`) to the queue.
- Track visited indices to avoid cycles and redundant exploration.
- If the queue becomes empty without finding 0, return false.

## Why BFS works

BFS explores all reachable positions level by level. It guarantees that we will find a path to 0 if one exists.

## Complexity

- Time: `O(n)` — each index is visited at most once.
- Space: `O(n)` — for the visited array and the queue.
