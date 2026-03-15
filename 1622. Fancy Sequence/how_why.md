# 1622. Fancy Sequence - How Why Explanation

## Problem

Design a data structure with operations under mod $10^9+7$:

- `append(val)`: append `val` to the sequence
- `addAll(inc)`: add `inc` to every element
- `multAll(m)`: multiply every element by `m`
- `getIndex(i)`: return sequence[i] or -1 if out of bounds

## Intuition

Applying additions/multiplications lazily avoids touching all elements. The overall effect on any stored value is an affine transform: `x -> a * x + b (mod M)`. Maintain global `a` (multiplicative factor) and `b` (additive offset). When appending, store the value *de-transformed* by applying the inverse of the current transform so future queries can re-apply the latest transform.

## Approach (affine lazy with modular inverse)

- Keep globals `a` (starts 1) and `b` (starts 0).
- `multAll(m)`: update `a = a * m`, `b = b * m` (mod).
- `addAll(inc)`: update `b = b + inc` (mod).
- `append(val)`: store base value `base = (val - b) * inv(a) (mod)`, where `inv(a)` is modular inverse under MOD.
- `getIndex(i)`: if in range, return `(a * stored[i] + b) (mod)`, else -1.

Implementation in [1622. Fancy Sequence/Solution.java](1622.%20Fancy%20Sequence/Solution.java#L4-L45) uses fast exponentiation for inverse (`a^(MOD-2)`).

## Alternate Implementation

- [Fancy2](1622.%20Fancy%20Sequence/Solution.java#L47-L104) precomputes inverses for small `m` and keeps a running `rmul = 1 / a` to avoid pow per append. Logic is the same affine idea with cached inverses.

## Complexity

- Each op O(1); `append` needs modular inverse (pow) in the first version, O(log MOD), but still effectively O(1) for constraints. Fancy2 makes it constant with cached inverses.
- Space: O(n) for stored values.

## Edge Cases

- Empty sequence: `getIndex` returns -1.
- Multiplying by 0 collapses `a` to 0; inverse would be undefined—problem constraints avoid invalid `m` (typically 0..100), and Fancy2 caches inverses accordingly.
- Values always kept mod M to prevent overflow.
