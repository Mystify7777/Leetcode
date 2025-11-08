# 1611. Minimum One Bit Operations to Make Integers Zero — how and why

## Problem recap

Starting from an integer `n`, you can perform the following operation any number of times: choose an index `i` (0-based) and flip the `i`-th bit and all lower bits (`0..i-1`). The goal is to transform `n` to `0` with the minimum number of operations. Return that minimum.

Example: `n = 3 (011₂)` → min operations = 2.

## Core intuition

This process is tightly connected to Gray code. If we list numbers in Gray-code order, successive values differ by exactly one bit. The minimal number of operations to go from `n` to `0` equals the index of `n` in that Gray-code sequence, which has a neat closed form.

Define `f(n)` as the answer. A classic identity is:

> f(n) = n ⊕ (n >> 1) ⊕ (n >> 2) ⊕ (n >> 3) ⊕ ... (until shifts become 0)

That is, XOR-fold `n` with all its right shifts. This gives the inverse Gray-code mapping count and equals the minimum operations.

There is also a recursive relation using the highest set bit:

- Let `msb = 1 << k` be the highest power of two ≤ n, and write `n = msb + x` with `0 ≤ x < msb`.
- Then `f(n) = (2 * msb - 1) - f(x)`.

## Approach 1 — iterative XOR fold (O(log n))

We can compute `f(n)` by XORing `n` with its successive right shifts until `n` becomes 0.

### Implementation (concise)

```java
class Solution {
	public int minimumOneBitOperations(int n) {
		int res = 0;
		while (n > 0) {
			res ^= n;
			n >>= 1;
		}
		return res;
	}
}
```

This matches the Gray-code inversion: each XOR-with-shift accumulates the contribution of higher bits onto lower ones in exactly the way the operation allows us to cancel them.

## Approach 2 — highest-bit recursion (same result)

Use the relation `f(msb + x) = (2*msb - 1) - f(x)` where `msb` is the highest power of two in `n`.

```java
class Solution {
	public int minimumOneBitOperations(int n) {
		if (n == 0) return 0;
		int msb = Integer.highestOneBit(n); // largest power of two ≤ n
		return (msb << 1) - 1 - minimumOneBitOperations(n ^ msb);
	}
}
```

This peels the most significant block and recurses on the remainder.

## About the provided implementation

Your file uses an equivalent low-bit accumulation with alternating signs (processing set bits from low to high):

```java
class Solution {
	public int minimumOneBitOperations(int n) {
		int multiplier = 1;
		int res = 0;
		while (n > 0) {
			// Add or subtract the block size contributed by the lowest set bit span
			res += (n ^ (n - 1)) * multiplier;
			multiplier = -multiplier;  // flip sign each set-bit block
			n &= n - 1;                // remove lowest set bit
		}
		return Math.abs(res);
	}
}
```

Notes:

- The term `(n ^ (n - 1))` isolates a mask of the lowest set-bit block (all ones up to that bit). Alternating `+/-` accumulates the correct contribution, yielding the same result as the XOR-fold method.
- Parentheses around `(n ^ (n - 1))` are important to ensure the intended order relative to `* multiplier`.

## Why this works

Think in Gray code. Let `G(i) = i ^ (i >> 1)` be the Gray code of `i`. The inverse mapping that gives the rank of `n` in Gray order is accomplished by XOR-folding down the shifts of `n`. That rank is exactly the minimal number of one-bit-difference steps needed to reach 0 under the allowed operation, hence `f(n)`.

Equivalently, the recursion partitions the problem at the most significant bit: transforming `msb + x` to `0` is like first walking the entire Gray-code block of size `2*msb` down to the boundary, then mirroring the steps needed for `x`.

## Complexity

- Time: O(log n) — proportional to the number of bits
- Space: O(1) iterative; O(log n) recursion depth for the recursive variant

## Examples

- `n = 0` → `0`
- `n = 1 (001₂)` → `1`
- `n = 2 (010₂)` → `3`
- `n = 3 (011₂)` → `2`
- `n = 4 (100₂)` → `7`
- `n = 7 (111₂)` → `5`

These match the standard sequence for this problem.

## Edge cases and notes

- Input fits in 32-bit integer; result also fits in 32-bit
- Iterative XOR-fold is simple and avoids recursion
- Be careful with operator precedence if using arithmetic with bitwise operators; add parentheses where intended

## Takeaways

- The problem is a Gray-code inversion in disguise
- Three equivalent ways to compute the answer: XOR-fold, highest-bit recursion, or alternating-sum over lowest-set-bit blocks
- All run in O(log n) time and constant space (iterative)
