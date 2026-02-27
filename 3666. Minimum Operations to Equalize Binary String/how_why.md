# How Why Explanation - 3666. Minimum Operations to Equalize Binary String

## Problem

You have a binary string `s` of length `n` and an integer `k`. In one operation you must flip exactly `k` distinct indices (0 -> 1, 1 -> 0). Find the minimum operations to make the string all `'1'`; return `-1` if impossible.

## Intuition

The state that matters is the number of zeros `z`. Flipping `i` zeros (and `k - i` ones) changes `z` to `z' = z + k - 2i`; all reachable `z'` for a move form a contiguous range with parity `(z + k) % 2`. That observation lets us reason purely about counts, not positions. We do not BFS those states; instead we derive tight lower bounds on how many operations are needed and reconcile them with the parity constraints.

## Approach (closed-form math)

1. Count zeros `z`. If `z == 0`, we are done.
2. If `n == k`, every move flips the whole string. It takes 1 move when the string is all zeros, otherwise it is impossible.
3. Let `base = n - k` (the size of the complement of any chosen set).
4. **Odd-op scenario:** finishing with a `k`-flip that clears the remaining zeros. We need enough `k`-flips to cover all zeros `ceil(z / k)` and enough intervening flips to clean the ones we turn into zeros, which costs `ceil((n - z) / base)` using the complementary size. The minimal odd count is the max of those bounds, rounded up to the nearest odd.
5. **Even-op scenario:** finishing with a “cleanup” flip after the last zero-clearing flip. Both the zero-clearing and cleanup phases are bounded by `ceil(z / k)` and `ceil(z / base)` respectively; take their max and round to the nearest even.
6. Parity feasibility: after `t` moves the zero count parity is `(z + t * k) % 2`. Odd-count plans are only viable when `(k & 1) == (z & 1)`. Even-count plans require `z` even. Pick the minimum feasible candidate; otherwise return `-1`.

The resulting O(1) formula matches the state-graph reasoning without explicit search; see [3666. Minimum Operations to Equalize Binary String/Solution.java](3666.%20Minimum%20Operations%20to%20Equalize%20Binary%20String/Solution.java#L4-L41).

## Complexity

- Time: O(n) to count zeros, then O(1) math.
- Space: O(1).

## Edge Cases

- All ones: 0 moves.
- `n == k`: only possible when the string is all zeros (1 move) or all ones (0 moves).
- Parity mismatch (`k` even with odd `z`, or `k` odd with impossible parity): return `-1`.
- Large `n` (up to 1e5) is safe because we only scan once.
