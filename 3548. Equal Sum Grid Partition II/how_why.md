# 3548. Equal Sum Grid Partition II - How Why Explanation

## Goal

Split the grid by a single straight cut (horizontal between two rows or vertical between two columns) into two non‑empty parts whose sums can be made equal, optionally by removing **one** cell from the heavier side. Return whether this is possible.

## Idea in 3 lines

- The cut creates a top/bottom or left/right rectangle pair; compute running sums to know each side’s total for every possible cut.
- If sums are already equal, done. Otherwise let `diff = |sumA - sumB|`; equality is possible iff the heavier side contains a cell with value `diff` that can be removed while keeping the side non-empty.
- Track value frequencies on each side as the cut sweeps, so membership checks for `diff` are O(1).

## Algorithm (matches `Solution`)

1. Compute `total` and initialize frequency arrays for the “below/right” side with all grid values. Size is 100001 (per constraints on cell values).
2. Sweep horizontal cuts (between rows):
	- Move one row at a time from bottom to top side, updating `sumTop`, and shifting row values from `bottom` freq to `top` freq.
	- If `sumTop == total - sumTop`, return true.
	- Else `diff = |sumTop - (total - sumTop)|`; if `diff` is within range and the heavier side has a removable cell of value `diff` (checked via `check`), return true.
3. Repeat the same logic for vertical cuts, sweeping columns and using `left`/`right` freq arrays.
4. If no cut works, return false.

## Removability check (`check`)

- Reject if the side is a single cell (cannot remove and stay non-empty).
- If the side is a single row, removal must be at an end cell to leave a contiguous row (similarly for a single column).
- Otherwise, existence of value `diff` in the side’s frequency array suffices.

## Why it works

- Any valid partition is either horizontal or vertical; sweeping all cut positions enumerates them.
- When sums differ, removing one cell of value `diff` from the heavier side exactly balances totals.
- Frequency tracking on each side lets us answer “does the heavier side contain value `diff`?” in O(1) as the cut moves, and the edge-case rules ensure both sides stay non-empty and contiguous after removal.

## Complexity

- Time: O(m * n) to accumulate sums and sweep both directions.
- Space: O(V) for frequency arrays, where `V = 100001` (value bound).
