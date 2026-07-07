# 3161. Block Placement Queries

The problem asks whether a block of a given size can be placed before a given position while respecting blocked cells.

## Approach 1: Reverse processing with a segment tree (`Solution`)

- Process queries in reverse.
- Keep track of obstacle positions using a `TreeSet`.
- Maintain a segment tree over gaps between consecutive obstacles.
- For a type-2 query, the answer depends on the largest available gap up to the obstacle before `x`, plus the free space from that obstacle to `x`.
- For a type-1 query in reverse, remove the obstacle and merge its neighboring gaps.

This works because reversing the queries turns insertions into deletions, which are easier to maintain with gap updates.

## Approach 2: Reverse processing with arrays + BIT (`Solution2`)

- Build obstacle markers first.
- Use `left` and `right` arrays to jump to the nearest active obstacle on each side.
- Maintain the maximum gap ending at each obstacle using a Fenwick tree / BIT.
- When removing an obstacle in reverse, reconnect the neighboring gap and update the BIT.
- For a type-2 query, compare the best gap before `x` and the free stretch from the last obstacle to `x`.

## Why it works

The key observation is that the set of blocked positions only changes by adding/removing obstacles.
By processing backward, we convert additions into removals and can maintain gap lengths incrementally.

## Example Walkthrough

Suppose the queries are:
- `[1, 4]` meaning place an obstacle at position `4`
- `[1, 8]` meaning place an obstacle at position `8`
- `[2, 7, 3]` meaning check whether a block of size `3` can fit before position `7`

If we process forward, the obstacles are at `0`, `4`, and `8`.
The open gaps are:
- `0 -> 4` has length `4`
- `4 -> 8` has length `4`

For the type-2 query at `x = 7`:
- The last obstacle at or before `7` is `4`.
- The free stretch from `4` to `7` has length `3`.
- The best gap seen so far is `4`.
- So a block of size `3` fits.

When the queries are processed in reverse:
- We first answer the type-2 query using the current gap structure.
- Then we remove obstacle `8` and merge the gap around it.
- Then we remove obstacle `4` and merge again.

This reverse view makes each update local: removing one obstacle only affects the two neighboring gaps.

## Complexity

- Segment tree version: `O(q log M)` time, `O(M)` space.
- BIT version: `O(q log M)` time, `O(M)` space.
- Here `M` is the coordinate limit.
