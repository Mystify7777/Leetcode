# 3121. Count the Number of Special Characters II

A character is special if its lowercase form appears before its uppercase form, and both appear in the string.

## Approach 1: Bit/boolean tracking (`Solution`)

- Track seen lowercase and uppercase letters separately.
- For each character, update the corresponding slot.
- A character counts as special only when both cases are present.

This version uses compact indexed state to detect whether both forms exist.

## Approach 2: First-occurrence indices (`Solution2`)

- Record the first position where each uppercase letter appears.
- Record the last position where each lowercase letter appears.
- A letter is special if both exist and the lowercase position is before the uppercase position.

This directly matches the problem condition.

## Example Walkthrough

Suppose `word = "aaAbBcC"`.

We check each letter:

- `a`: lowercase appears before uppercase `A`, so `a` is special.
- `b`: lowercase appears before uppercase `B`, so `b` is special.
- `c`: lowercase appears before uppercase `C`, so `c` is special.

So the answer is `3`.

How the two solutions see it:

- `Solution` marks both lowercase and uppercase bits/flags for `a`, `b`, and `c`, then counts the overlap.
- `Solution2` records the lowercase and uppercase positions and confirms that the lowercase index is smaller than the uppercase index for each of `a`, `b`, and `c`.

## Complexity

- Time: `O(n)`
- Space: `O(1)`
