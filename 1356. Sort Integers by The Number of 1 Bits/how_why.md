# 1356. Sort Integers by The Number of 1 Bits — How/Why

## Problem

Sort an integer array by the number of `1` bits in each element’s binary form; if two numbers have the same bit count, order them by numeric value ascending.

## Intuition

Define an ordering key `(popcount(x), x)`. A comparator using popcount first and value second directly mirrors the required ordering. Popcount via Brian Kernighan’s trick is cheap and avoids strings.

## Approach

- Box the input `int[]` into `Integer[]` to allow a custom comparator with `Arrays.sort`.
- Comparator:
	- Compute `bitCount` by repeatedly clearing the lowest set bit: `num &= num - 1` until zero.
	- Primary compare on `bitCount`; if equal, compare the numbers themselves.
- Sort with this comparator, then unbox back to `int[]`.

## Correctness

- Brian Kernighan’s loop decrements the number of set bits by one per iteration, so it returns the exact popcount.
- The comparator orders pairs by `(bitCount, value)`; `Arrays.sort` produces an array sorted by this key for all elements.
- Because the comparator’s key matches the problem’s ordering rule, the sorted output satisfies the specification.

## Complexity

- Time: $O(n \log n \cdot b)$ where $b \le 32$ is bits per int; popcount adds a constant factor. Sorting dominates.
- Space: $O(n)$ for boxing to `Integer[]`; comparator uses $O(1)`.

## Edge Cases

- Zeros have popcount 0 and come first.
- Duplicate values naturally remain together under the same key.
- Input is non-negative per problem constraints; if negatives appeared, numeric tie-break still orders them after popcount grouping.

## Alternatives

- Key-packing trick (see `Solution2`): add `popcount(x) * K` (with `K` larger than any value) to each element, sort in-place, then strip the offset. Saves boxing but assumes a safe `K` bound and uses custom quicksort.
- Use `Integer.bitCount` inside the comparator for brevity; asymptotics unchanged.

## Key Code

- Comparator-based sort with popcount tie-break: [1356. Sort Integers by The Number of 1 Bits/Solution.java](1356.%20Sort%20Integers%20by%20The%20Number%20of%201%20Bits/Solution.java#L4-L44)
