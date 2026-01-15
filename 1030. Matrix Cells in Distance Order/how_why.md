
# How & Why: LeetCode 1030 - Matrix Cells in Distance Order

## Problem

Given an `R x C` grid and a starting cell `(r0, c0)`, output all cell coordinates sorted by increasing Manhattan distance to `(r0, c0)`.

## Intuition

- Manhattan distance forms expanding “diamonds” around the start. We can either sort by distance or traverse/sweep those diamonds directly to emit cells in order.

## Brute Force Approach

- **Idea:** Generate all cells, sort by `|r - r0| + |c - c0|`.
- **Complexity:** Time $O(RC \log (RC))$, Space $O(RC)$.

## My Approach (Diamond Sweep) — from Solution.java

- **Idea:** Compute max possible distance `lim`. For each distance `d` from 1..lim, walk the four edges of the distance-`d` diamond, emitting in-bounds cells. Start with `(r0,c0)` at distance 0.
- **Complexity:** Time $O(RC)$, Space $O(1)$ extra (output array only).
- **Core snippet:**

```java
res[idx++] = new int[]{r0, c0};
int lim = Math.max(r0, R-r0-1) + Math.max(c0, C-c0-1);
for (int d = 1; d <= lim; d++) {
	int r = r0 - d, c = c0;
	// four sides of the diamond
	for (int k=d; k-- >0; ) { if (in(r,c)) res[idx++]=new int[]{r,c}; r++; c--; }
	for (int k=d; k-- >0; ) { if (in(r,c)) res[idx++]=new int[]{r,c}; r++; c++; }
	for (int k=d; k-- >0; ) { if (in(r,c)) res[idx++]=new int[]{r,c}; r--; c++; }
	for (int k=d; k-- >0; ) { if (in(r,c)) res[idx++]=new int[]{r,c}; r--; c--; }
}
```

## Most Optimal Approach

- The diamond sweep achieves linear time. An alternative is BFS from `(r0,c0)` with a queue and visited, also $O(RC)$, but with extra queue/visited memory.

## Edge Cases

- Single cell grid → only `(0,0)`.
- Start on an edge/corner: many diamond steps fall out of bounds; guard checks skip them.
- Very small R or C (e.g., 1 row/col) reduces to 1D ordering; sweep still works.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Sort by distance | Generate all cells, sort | O(RC log RC) | O(RC) | Simple but slower |
| BFS from start | Layered expansion by dist | O(RC) | O(RC) | Needs visited/queue |
| Diamond sweep (used) | Emit cells per distance ring | O(RC) | O(1) extra | Fast and memory-light |

## Example Walkthrough

`R=2, C=3, r0=1, c0=2`

- lim = max(1,0)+max(0,0)=1.
- Start: (1,2).
- Distance 1 diamond emits in-bounds: (0,2), (1,1), (0,1), (1,0), (0,0) → all cells covered in correct order.

## Insights

- Manhattan-distance levels are diamonds; sweeping ring by ring avoids sorting.

## References to Similar Problems

- 1162. As Far from Land as Possible (BFS layers by Manhattan distance)
- 542. 01 Matrix (multi-source BFS by distance)
