# 2751. Robot Collisions - How Why Explanation

## Goal

Given robots with positions, healths, and directions (`L`/`R`), simulate 1D collisions. When a right-moving meets a left-moving robot:

- The lower health robot is destroyed; the higher health robot loses 1 health.
- Equal health destroys both.
Return remaining robots’ healths in original order.

## Idea in 3 lines

- Sort robots by position; only `R` to the left can collide with a `L` coming from the right.
- Use a stack of right-moving robots; when a left-moving robot is processed, resolve collisions against the stack top until it dies or the stack empties.
- Update health on each collision; remove dead robots. Survivors are those never killed (or emptied stack) after the sweep.

## Algorithm (matches `Solution`)

1. Sort indices by position ascending.
2. Sweep in order:
	 - If direction is `R`, push index onto stack.
	 - If direction is `L`, while stack not empty:
		 - Compare healths of stack top (right) and current (left).
		 - If right < left: pop right (dies), decrement left health by 1, continue.
		 - If right > left: decrement right health by 1, mark left dead, break.
		 - If equal: pop right, mark left dead, break.
3. After sweep, collect healths of robots still marked alive in original index order.

## Why it works

- Sorting ensures collision order matches real movement: a left mover can only meet previously seen rights to its left.
- Stack keeps right movers in position order; collisions resolve from the nearest right mover outward, matching physics.
- Health updates mirror the problem’s rules; repeated while-loop handles chain collisions.

## Complexity

- Time: O(n log n) for sorting + O(n) for the sweep.
- Space: O(n) for the stack and status arrays.

## Example

Positions: [1, 3, 5], Healths: [3, 2, 4], Directions: [R, L, L]

- Sort order is already [1(R3), 3(L2), 5(L4)].
- Start: stack = []
	- 1(R3): push idx0 → stack [0]
	- 3(L2): collide with idx0 (R3)
		- R3 > L2 → R health drops to 2, L dies. stack [0], healths [2, X, 4]
	- 5(L4): stack [0] has R2
		- R2 < L4 → R dies, L becomes 3; stack []
		- stack empty → L survives with health 3
- Survivors in original order: idx0 dead, idx1 dead, idx2 alive with 3 → [0, 0, 3].
