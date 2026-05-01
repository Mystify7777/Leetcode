# How Why - Explanation 396. Rotate Function

[396. Rotate Function](https://leetcode.com/problems/rotate-function/)

## Problem

For an array `A`, define the rotate function:

$$
F(k) = \sum_{i=0}^{n-1} i \cdot B_k[i]
$$

where `B_k` is `A` rotated clockwise by `k` positions. Return the maximum value among all `F(k)`.

## Intuition

Brute force would rebuild every rotation and recompute the weighted sum from scratch, which is too slow.

The key observation is that consecutive rotations are related by a simple recurrence:

$$
F(k) = F(k-1) + \text{sum}(A) - n \cdot \text{last element moved to front}
$$

So once we know `F(0)`, every other rotation can be updated in O(1).

## Deriving the Recurrence

Let `sum` be the total sum of the array.

When rotating once more:

- every existing element shifts one position to the right, which increases the weighted sum by the total sum,
- the element that wraps from the end to index `0` loses `n` units of weight compared to its previous contribution.

Combining those effects gives the recurrence used in both classes in [396. Rotate Function/Solution.java](396.%20Rotate%20Function/Solution.java).

## Approach

1. Compute `sum` of all elements.
2. Compute `F(0) = 0*A[0] + 1*A[1] + ... + (n-1)*A[n-1]`.
3. Set `max = F(0)`.
4. For each `k` from `1` to `n-1`, update `F` using the recurrence.
5. Track the maximum value seen.

## Why This Is Correct

`F(0)` is the weighted sum for the original array.

Each next rotation is derived exactly from the previous one by shifting all values one step and moving the last element to the front. The recurrence accounts for both changes, so it produces the exact `F(k)` for every `k`.

Since the algorithm evaluates every `F(k)` through that exact recurrence and keeps the largest one, the returned answer is the maximum rotate function value.

## Complexity

- Time: `O(n)`.
- Space: `O(1)`.

## Edge Cases

- Single-element array: every rotation is the same, so the answer is `0`.
- All values equal: the recurrence still works and all rotations tie.
- Negative values: fine, because the recurrence is purely arithmetic.

## Notes on the Two Implementations

- `Solution2` uses the standard recurrence with a straightforward loop over rotations.
- `Solution` computes the same values with equivalent logic and an explicit maximum update.
