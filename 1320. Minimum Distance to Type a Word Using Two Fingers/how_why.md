# 1320. Minimum Distance to Type a Word Using Two Fingers - How Why Explanation

## Goal

Type the given uppercase word on a 6-column keyboard (`A..Z`) using two fingers, minimizing total Manhattan movement.

## Idea in 3 lines

- Let one finger be the one that typed the previous character; track where the other finger is.
- DP state stores the minimum cost after typing up to index `i`, keyed by the other finger position.
- For each next character, either move the previous-typing finger or move the other finger and swap roles.

## Keyboard distance

For letters encoded as `0..25`:

- row = `x / 6`, col = `x % 6`
- distance = `|r1-r2| + |c1-c2|`

This matches `cal(a, b)` in `Solution`.

## DP (matches `Solution`)

State meaning:

- After typing `word[i-1]`, `dp[j]` = minimum cost where one finger is on `word[i-1]`, and the other finger is on letter `j`.

Transition from previous char `p = word[i-1]` to current char `t = word[i]`:

1. Keep other finger at `j`, move active finger `p -> t`:
	- `ndp[j] = dp[j] + dist(p, t)`
2. Move other finger `j -> t` and make it the active finger:
	- the previous active finger stays at `p`
	- `ndp[p] = min(ndp[p], dp[j] + dist(j, t))`

Swap `dp` and `ndp` per step. Final answer is `min(dp[j])`.

## Why it works

- At each character, exactly two meaningful choices exist: type with the same finger as last time or with the other finger.
- The state `(last typed position, other finger position)` captures all information needed for future decisions.
- Taking minimum over all transitions gives optimal substructure, so one pass over characters with 26 states is sufficient.

## Complexity

- Time: `O(n * 26)`
- Space: `O(26)` using rolling arrays (`dp`, `ndp`)

## Note on `Solution2`

`Solution2` is the same optimization idea with a slightly different encoding (`store[27]`) and update order, still achieving near-constant state size per character.
