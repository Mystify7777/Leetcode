# How Why Explanation - 401. Binary Watch

## Problem

Given a binary watch with 4 LEDs for hours (0–11) and 6 LEDs for minutes (0–59), return all possible times the watch could represent when exactly `k` LEDs are on. Format minutes as two digits.

## Intuition

There are 10 LEDs total. Any valid time corresponds to a 10-bit number where the top 4 bits form the hour and the lower 6 bits form the minutes. We just need all 10-bit patterns with exactly `k` bits set that decode to a legal hour (<12) and minute (<60).

## Brute Force (Feasible)

- Enumerate all `2^10 = 1024` patterns, filter by bit count and validity. This is already small, but we can enumerate only combinations of `k` bits using Gosper’s hack.

## Approach (Enumerate k-bit combinations with Gosper’s hack)

1. If `k == 0`, the only time is `"0:00"`.
2. Initialize `q` as the smallest number with `k` bits set: `(1 << k) - 1`. The largest such combo within 10 bits is `q << (10 - k)`.
3. Loop over all `k`-bit combinations in 10 positions using Gosper’s hack to get the next combination:
	- Extract minutes: `min = q & 0b111111` (lower 6 bits).
	- Extract hours: `hour = q >> 6` (upper 4 bits).
	- If `hour < 12` and `min < 60`, record the formatted time.
	- Advance `q` with `r = q & -q; n = q + r; q = (((q ^ n) / r) >> 2) | n;`.
4. Return the collected times.

Why it works: Each 10-bit pattern with `k` set bits uniquely represents a choice of LEDs on. Splitting the bits respects the watch layout; Gosper’s hack walks all `k`-bit masks without touching others, keeping the loop small.

## Complexity

- Time: $O(\binom{10}{k})$ combinations checked; upper-bounded by 1024.
- Space: $O(1)$ extra besides the output list.

## Optimality

Enumerating only `k`-bit patterns avoids scanning all 1024 when `k` is small. The bounds are tiny either way; the hack is a neat constant-factor improvement.

## Edge Cases

- `k = 0` → only `"0:00"`.
- `k > 8` (hours max 4 bits, minutes 6 bits): still fine; invalid combos are filtered by range checks.
- Leading zero in minutes must be kept (e.g., `"3:07"`).

## Comparison Table

| Aspect | Gosper combinations (Solution) | DFS over hour/min bits (Solution2) |
| --- | --- | --- |
| Enumeration | Next k-bit mask in 10 bits | Recursively choose bits for hours then minutes |
| Time | $O(\binom{10}{k})$ | Similar order |
| Space | $O(1)$ extra | $O(k)$ recursion stack |
| Implementation | Bit-hack, compact | More verbose, clearer splits |

## Key Snippet (Next combination)

```java
int r = q & -q;
int n = q + r;
q = (((q ^ n) / r) >> 2) | n;
```

## Example Walkthrough

`k = 1`

- Start `q = 0000000001`: hour=0, min=1 → "0:01"
- Next `q = 0000000010`: "0:02", ...
- Bits in hour region yield times like `0001000000` → hour=16? invalid; `0000100000` → hour=8, min=0 valid → "8:00". All 10 one-bit positions are tried; valid ones collected.

## Insights

- The 10-bit packing mirrors the watch’s 4/6 LED split, simplifying validity checks to two range comparisons.
- Gosper’s hack is a standard trick for iterating fixed-popcount masks efficiently.

## References

- Solution implementation in [401. Binary Watch/Solution.java](401.%20Binary%20Watch/Solution.java)
