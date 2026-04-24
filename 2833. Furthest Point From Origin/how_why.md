# How Why - Explanation 2833. Furthest Point From Origin

[2833. Furthest Point From Origin](https://leetcode.com/problems/furthest-point-from-origin/)

## Problem

You start at position 0 on a number line. The string `moves` contains:

- `L`: move left by 1
- `R`: move right by 1
- `_`: can be replaced by either `L` or `R`

Choose replacements for all `_` to maximize the final distance from 0. Return that maximum distance.

## Intuition

Let:

- `dist = (#R - #L)` after applying only fixed moves.
- `blanks = count of '_'`.

Each blank changes the final position by exactly `+1` or `-1`. To maximize distance from 0, push every blank in the same direction as the current signed displacement:

- if `dist >= 0`, use blanks as `R`
- if `dist < 0`, use blanks as `L`

So the best possible absolute final position is:

$$
|dist| + blanks
$$

## Approach (Single Pass Count)

1. Scan the string once.
2. Update:
	- `dist--` for `L`
	- `dist++` for `R`
	- `blanks++` for `_`
3. Return `Math.abs(dist) + blanks`.

This is exactly what the implementation does in [2833. Furthest Point From Origin/Solution.java](2833.%20Furthest%20Point%20From%20Origin/Solution.java#L3-L14).

## Why This Is Correct

Suppose the fixed moves give position `dist`.

Each blank contributes either `+1` or `-1`. Let the total contribution of all blanks be `x`, where `x` has same parity as `blanks` and satisfies `|x| <= blanks`. Final position is `dist + x`.

To maximize `|dist + x|`, choose `x` with the same sign as `dist` and largest magnitude, i.e., `x = sign(dist) * blanks` (or either sign when `dist = 0`). Then:

$$
|dist + x| = |dist| + blanks
$$

No other `x` can exceed this because `|dist + x| <= |dist| + |x| <= |dist| + blanks`.

Hence the formula is optimal.

## Complexity

- Time: $O(n)$, where $n = moves.length()$.
- Space: $O(1)$.

## Edge Cases

- All blanks (for example, `"___"`): answer is number of blanks.
- No blanks: answer is just `|#R - #L|`.
- Balanced fixed moves (`dist = 0`): use all blanks one way, answer is `blanks`.

## Key Insight

You never need to try assignments explicitly. Count net fixed displacement and number of wildcards, then apply:

$$
answer = |(#R - #L)| + (\#\_)
$$
