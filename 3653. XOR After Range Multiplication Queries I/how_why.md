# 3653. XOR After Range Multiplication Queries I - How Why Explanation

## Goal

Process `q` queries on an array. Each query specifies a range [l, r], a step k, and a multiplier v. For each query, multiply every element at positions l, l+k, l+2k, ... (while ≤ r) by v modulo 10^9+7. After all queries, return the XOR of all array values.

## Idea in 3 lines

- Each query applies arithmetic (multiply by v mod 10^9+7) to a subset of positions using an arithmetic progression.
- Process queries in order; each modifies the array in-place.
- After all queries complete, XOR-reduce the final array to a single result.

## Algorithm (matches both Solutions)

1. For each query [l, r, k, v]:
   - Start at index `idx = l`.
   - While `idx <= r`, multiply `nums[idx]` by v and take mod 10^9+7.
   - Advance `idx` by k, skipping by the given step.
2. After all queries, initialize `ans = 0` and XOR each array element: `ans ^= nums[i]`.
3. Return `ans`.

## Why it works

- Each query's step-wise multiplication affects only the specified indices, leaving others unchanged.
- Taking modulo after each multiply prevents overflow and keeps values bounded.
- XOR is associative and commutative; computing it after all modifications gives the final answer.

## Complexity

- Time: O(∑(queries) * elements-per-query), where each query visits roughly (r - l) / k + 1 positions.
- Space: O(1) if modifying in-place (including long conversions); O(n) for Solution2 which uses a separate long array.

## Note on Solutions

- Both solutions use the same logic; `Solution2` uses a `long[]` copy instead of casting in-place.
- The `MOD` constant is 10^9+7 (written as `10e8+7` in Solution2, which equals 1e9+7).