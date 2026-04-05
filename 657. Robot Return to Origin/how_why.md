# 657. Robot Return to Origin - How Why Explanation

## Goal

Given a move string using `U`, `D`, `L`, `R`, determine whether the robot ends at the origin `(0,0)` after performing all moves.

## Idea in 2 lines

- Track position with coordinates: `x` for left/right and `y` for up/down.
- The robot returns to origin exactly when final `x == 0` and `y == 0`.

## Algorithm (matches `Solution`)

1. Initialize `x = 0`, `y = 0`.
2. Scan each character in `moves`:
	- `U` => `y++`
	- `D` => `y--`
	- `R` => `x++`
	- `L` => `x--`
3. Return `x == 0 && y == 0`.

## Why it works

- Horizontal moves only affect `x`; vertical moves only affect `y`.
- Returning to origin means net horizontal displacement is zero and net vertical displacement is zero.
- The updates above compute exactly those net displacements.

## Complexity

- Time: O(n), where `n` is `moves.length()`.
- Space: O(1).

## Alternative (matches `Solution2`)

- Count frequencies of each move and check `count(U) == count(D)` and `count(L) == count(R)`.
- This is equivalent to checking zero net displacement.
