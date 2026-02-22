# 868. Binary Gap — How/Why

## Problem

Find the maximum distance between two consecutive `1` bits in the binary representation of `n`. Return `0` if there is fewer than two `1`s.

## Intuition

Ignore trailing zeros so the first bit seen is a `1`, then scan bits from LSB to MSB counting zeros between `1`s. Track the largest gap found.

## Approach

- Strip trailing zeros with `n >>= Integer.numberOfTrailingZeros(n)` so the scan starts at the least-significant `1`.
- If only one `1` remains, return `0` immediately.
- Maintain `gap` (zeros since last `1`) and `maxGap`.
- While scanning bits:
  - When a `1` is hit, update `maxGap` with the current `gap`, reset `gap` to `0`.
  - When a `0` is hit, increment `gap`.
  - Shift right to process the next bit.
- After the loop, return `maxGap + 1` because a gap of `k` zeros corresponds to a distance of `k + 1` between the bounding `1`s.

## Correctness

- Stripping trailing zeros does not change distances between `1`s, but ensures the first bit processed is a `1`, so the initial `gap` is well-defined.
- Each right-shift moves to the next more significant bit, so the scan visits bits in order.
- `gap` counts zeros since the last `1`; when another `1` is found, the distance between these `1`s is `gap + 1`, and taking the maximum over the scan yields the largest binary gap.
- If only one `1` exists, the algorithm returns `0`, matching the definition.

## Complexity

- Time: $O(\log n)$ — one pass over the bit-length of `n`.
- Space: $O(1)$.

## Edge Cases

- `n` has only one `1` (e.g., powers of two): early return `0`.
- Trailing zeros: removed up front so they do not inflate a gap.
- Consecutive `1`s: gap of `0` zeros yields distance `1`.

## Alternatives

- Track positions of `1` bits via indices (bit counter) instead of zero counts; yields similar complexity but needs an extra variable for last position.

## Key Code

- Strip trailing zeros and scan bits: [868. Binary Gap/Solution.java](868.%20Binary%20Gap/Solution.java#L5-L19)
