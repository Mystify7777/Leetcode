# 3740. Minimum Distance Between Three Equal Elements I - How Why Explanation

## Goal

Find the minimum distance measure among three equal elements in the array. The solution keeps track of recent occurrences of each value and updates the answer whenever a third matching element appears.

## Idea in 3 lines

- For each number, only the last two seen positions matter.
- When the same value appears again, the earliest of those two prior positions and the current position define the span for three equal elements.
- Store the two positions compactly in one integer to keep the solution O(1) per element.

## Algorithm (matches `Solution`)

1. Create an array `last2` indexed by value, where each entry stores two recent positions packed into one `int`.
2. Sweep the array from left to right.
3. For the current value `val` at position `i`:
	 - Decode the packed state for `val`.
	 - `old` is the oldest of the last two seen positions.
	 - `cur` is the most recent seen position.
	 - Update the packed state so the current position becomes the newest one.
4. If `old > 0`, we have seen this value at least three times; update the answer with `(currentPos - old) * 2`.
5. If no value appears three times, return `-1`.

## Why it works

- A triple of equal values is fully determined by its earliest and latest positions; the middle occurrence only proves that three equal elements exist.
- Once the third occurrence arrives, the distance from the oldest previous occurrence to the current position is the relevant span to minimize.
- Tracking only the last two positions is sufficient because any older positions can never produce a smaller span than the most recent triple candidate.

## Bit-packing trick

- The code stores two positions in one integer:
	- low 8 bits: older position (`old`)
	- higher bits: newer position (`cur`)
- On each visit, it shifts the current newest position down to become the new `old`, and writes the current position into the high bits.
- This avoids using a pair or object per value while still preserving both recent positions.

## Complexity

- Time: O(n), one pass over the array.
- Space: O(n) for the `last2` array.
