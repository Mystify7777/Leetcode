# 3120. Count the Number of Special Characters I

A character is special if both its lowercase and uppercase forms appear in the string.

## Idea

Use bit masks:
- `lower` tracks which lowercase letters appear.
- `upper` tracks which uppercase letters appear.
- A bit set in both masks means that letter appears in both cases.

Then count the set bits in `lower & upper`.

## Why it works

Each bit position represents one letter from `a` to `z`.
If the same position is set in both masks, then that letter has appeared as both lowercase and uppercase at least once.

## Complexity

- Time: `O(n)`
- Space: `O(1)`
