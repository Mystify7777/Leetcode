# 1665. Minimum Initial Energy to Finish Tasks

## Why sorting works

Each task is `[actual, minimum]`:
- You must have at least `minimum` energy to start it.
- After doing it, energy decreases by `actual`.

To minimize initial energy, we should do tasks with larger `(minimum - actual)` first. These tasks are more "demanding" up front compared to what they consume, so postponing them can force a larger energy top-up later.

That leads to a greedy strategy:
1. Sort tasks by descending `(minimum - actual)`.
2. Simulate energy through tasks.
3. Whenever current energy is below `minimum`, add just enough extra initial energy.

## Approaches in `Solution.java`

- `Solution1`:
  - Sorts by descending `(minimum - actual)`.
  - Tracks a running balance and a `loan` (extra initial energy needed).
  - Returns `start + loan`.

- `Solution2`:
  - Equivalent greedy idea in a compact formula form.
  - Uses sorted order and keeps answer as `ans = max(ans + actual, minimum)`.

- `Solution`:
  - Greedy simulation with explicit `energy` (initial required so far) and `curr` (current balance).
  - If `curr < minimum`, increase `energy` by the gap.

## Complexity

- Time: `O(n log n)` due to sorting.
- Space: `O(1)` extra (ignoring sort implementation details).
