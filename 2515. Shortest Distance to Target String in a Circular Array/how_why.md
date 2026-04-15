# 2515. Shortest Distance to Target String in a Circular Array - How Why Explanation

[2515. Shortest Distance to Target String in a Circular Array](https://leetcode.com/problems/shortest-distance-to-target-string-in-a-circular-array/)

## Goal

Given a circular array of words, return the minimum number of steps from `start` to any index containing `target`. If `target` never appears, return `-1`.

## Idea in 2 lines

- In a circle, distance `i` from `start` corresponds to exactly two candidate indices: clockwise and counterclockwise.
- Check both directions for increasing `i`; the first hit is guaranteed to be the minimum distance.

## Algorithm (matches `Solution`)

1. Let `n = words.length`.
2. For `i` from `0` to `n/2`:
	- clockwise index: `(start + i) % n`
	- counterclockwise index: `(start - i + n) % n`
	- if either equals `target`, return `i`.
3. If no match is found, return `-1`.

## Why it works

- At each radius `i`, every location at circular distance `i` from `start` is checked (both sides).
- Distances are tested in increasing order, so the first match must be optimal.
- Limiting to `n/2` is sufficient because larger distances mirror shorter paths around the circle.

## Complexity

- Time: `O(n)` in the worst case.
- Space: `O(1)`.

## Note

The code uses bitwise OR (`|`) on booleans to evaluate both checks each iteration. Logical OR (`||`) would also work here.
