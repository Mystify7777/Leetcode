# Recap

Teemo attacks at specific times given by `timeSeries`. Each attack applies poison for `duration` seconds starting at that time. If a new attack occurs before the previous poison ends, the poison effect overlaps (does not stack) — you should count the total poisoned time as the length of the union of all poison intervals.

Goal: Compute the total number of seconds Ashe is poisoned.

## Intuition

Treat each attack as an interval: `[timeSeries[i], timeSeries[i] + duration)` (half-open). The total poisoned time is the sum of lengths of these intervals with overlaps merged.

Instead of explicitly merging intervals, notice a simpler rule:

- For each consecutive pair of attacks at `t = timeSeries[i]` and `next = timeSeries[i+1]`, the contribution from the current attack is `min(duration, next - t)`. This captures overlap automatically:
  - If `next - t >= duration`, intervals do not overlap → contribution is `duration`.
  - If `next - t < duration`, intervals overlap → only the non-overlapping part `next - t` contributes.
- After processing pairs, add `duration` for the last attack (it always contributes its full duration).

## Approach

1. Initialize `total = 0`.
2. For each `i` from `0` to `timeSeries.length - 2`:
   - Add `min(duration, timeSeries[i+1] - timeSeries[i])` to `total`.
3. Add `duration` for the last attack (if the array is non-empty).
4. Return `total`.

This yields the union length without building interval structures.

## Code (Java)

```java
// 495. Teemo Attacking
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int total = 0;
        for (int i = 0; i < timeSeries.length - 1; i++) {
            // If next attack happens before current poison fully expires, only non-overlapping part adds
            if (timeSeries[i + 1] <= timeSeries[i] + duration - 1) {
                total += timeSeries[i + 1] - timeSeries[i];
            } else { // No overlap: full duration contributes
                total += duration;
            }
        }
        // Last attack always contributes full duration if any attacks exist
        if (timeSeries.length > 0) {
            total += duration;
        }
        return total;
    }
}
```

Equivalent concise implementation using `min`:

```java
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
    int total = 0;
        for (int i = 0; i < timeSeries.length - 1; i++) {
            total += Math.min(duration, timeSeries[i + 1] - timeSeries[i]);
        }
        return timeSeries.length == 0 ? 0 : total + duration;
    }
}
```

## Complexity

- Time: `O(n)` — single pass over `timeSeries`.
- Space: `O(1)` — constant extra space.

## Example

Input: `timeSeries = [1, 4]`, `duration = 2`

- Intervals: `[1,3)` and `[4,6)` → no overlap
- Total: `2 + 2 = 4`

Input: `timeSeries = [1, 2]`, `duration = 2`

- Intervals: `[1,3)` and `[2,4)` → overlap on `[2,3)`
- Contribution: `min(2, 2 - 1) = 1` for first + `2` for last = `3`

## Edge Cases

- `timeSeries.length == 0` → `0` (no attacks, no poison).
- Large gaps between attacks → each contributes full `duration`.
- Dense attacks (differences < `duration`) → overlaps trimmed via `min` rule.

## Why This Works

The `min(duration, next - current)` rule sums exactly the non-overlapping increment added by each interval to the union. Adding `duration` for the final interval completes the union length, matching interval-merging behavior without extra data structures.
