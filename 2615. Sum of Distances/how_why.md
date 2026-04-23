# How Why Explanation - 2615. Sum of Distances

## Problem

For each index `i` in `nums`, compute:

`ans[i] = sum of |i - j| for all j where nums[j] == nums[i] and j != i`.

Return the full `ans` array.

## Intuition

Indices with different values do not matter to each other, so process each value group independently.

If positions for one value are sorted as `p0, p1, ..., p(m-1)`, then for position `pk`:

- contribution from left side = `k * pk - sum(left positions)`
- contribution from right side = `sum(right positions) - (m-k-1) * pk`

Using running sums, each group can be processed in linear time.

## Approach (group indices + prefix/suffix sums)

1. Build a map: `value -> list of indices`.
2. For each index list `pos`:
    - compute total sum of all indices in that group
    - iterate through the list with running `leftSum`
    - derive `rightSum = total - leftSum - currentIndex`
    - compute:
        - `left = currentIndex * i - leftSum`
        - `right = rightSum - currentIndex * (m - i - 1)`
    - set `ans[currentIndex] = left + right`
    - update `leftSum`

This matches [2615. Sum of Distances/Solution.java](2615.%20Sum%20of%20Distances/Solution.java#L3-L34).

## Why It Works

For fixed `currentIndex = pos[i]`:

- every left position contributes `currentIndex - leftPos`
- every right position contributes `rightPos - currentIndex`

Summing these separately gives the two formulas above. Since every equal-value index is counted once in left or right partition, the total is exact.

## Complexity

- Time: O(n), because each index is inserted once and processed once.
- Space: O(n), for grouped positions and result array.

## Edge Cases

- Value appears once: distance is `0`.
- All elements equal: each index gets sum of distances to all other indices.
- Large indices/sums: `long` is required to avoid overflow.

## Alternate Approach

The second implementation keeps, per value, counts/sums on the left and right while scanning once:

- `s1, c1` for visited side
- `s2, c2` for unvisited side

Then directly computes current answer as:

`(s2 - s1) + i * c1 - i * c2`

This is also O(n) time and O(n) space. See [2615. Sum of Distances/Solution.java](2615.%20Sum%20of%20Distances/Solution.java#L36-L67).
