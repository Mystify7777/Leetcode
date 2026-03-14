# How Why Explanation - 3296. Minimum Number of Seconds to Make Mountain Height Zero

## Problem

There are workers, where worker `i` removes 1 unit of mountain height every `times[i]` seconds, but their speed accumulates like a series: in `t` seconds one worker can remove `1 + 2 + ... + k` units if `k` jobs fit into `t` with durations `times[i], 2*times[i], ..., k*times[i]`. Given initial height `height`, find the minimum time to reduce height to 0.

## Intuition

Each worker is independent. Given a time budget `T`, worker with time `d` can complete the largest `k` such that `d * (1 + 2 + ... + k) <= T`, i.e., `d * k * (k+1) / 2 <= T`. Solve quadratic to get `k = floor((sqrt(1 + 8*T/d) - 1) / 2)`. If the total removed across workers in `T` is at least `height`, `T` is feasible. This leads to a monotonic predicate suitable for binary search on `T`.

## Approach (binary search on time)

1. **Feasibility check:** For time `mid`, sum `k(mid, d)` for all workers until reaching `height` or exhausting workers. `k(mid, d) = floor((sqrt(1 + 8 * mid / d) - 1) / 2)` (equivalent algebraic form used in code).
2. **Binary search bounds:** Low = 1; High can be set generously (e.g., `1e16`) or tighter via max worker time and average load; current code uses `1e16` in [Solution](3296.%20Minimum%20Number%20of%20Seconds%20to%20Make%20Mountain%20Height%20Zero/Solution.java#L4-L21) and a tighter bound in [Solution2](3296.%20Minimum%20Number%20of%20Seconds%20to%20Make%20Mountain%20Height%20Zero/Solution.java#L23-L49).
3. While `lo < hi`, test mid; if feasible, move `hi = mid`, else `lo = mid + 1`.
4. Return `lo` as minimal feasible time.

## Complexity

- Feasibility: O(m) over workers; binary search adds a `log` factor over time range → O(m log W).
- Space: O(1).

## Edge Cases

- Single worker: time reduces to solving the quadratic directly; binary search still works.
- Height = 0 → answer is 0 (not present in code, but logically).
- Large `T` or `times[i]` use `long`/double; guard overflow by ordering operations (as in code).

## Alternate Approaches

- Derive an upper bound `hi` tighter: distribute work evenly to get `h = ceil(height / m)`, then `hi = max(times) * h * (h+1)/2` (as in Solution2) to shrink iterations.
- Without binary search, a priority queue simulation would be too slow; the monotone predicate is the key.
