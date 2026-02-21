# How Why Explanation - 762. Prime Number of Set Bits in Binary Representation

## Problem

Given two integers `left` and `right`, count how many integers in the inclusive range `[left, right]` have a prime number of set bits in their binary representation.

## Intuition

The number of set bits in any `int` up to $10^6$ is small (≤ 20). We can iterate through the range, count bits, and test primality of that count. Primality checks are cheap for such small numbers.

## Brute Force (Used; straightforward)

- Loop `i` from `left` to `right`:
	- `setBits = Integer.bitCount(i)`
	- If `setBits` is prime, increment answer.
- Complexity is dominated by the loop length; bitCount is O(1) using CPU/popcount.

## Approach (Popcount + small prime test)

1. Initialize `count = 0`.
2. For each `i` in `[left, right]`:
	 - `bits = Integer.bitCount(i)`
	 - If `isPrime(bits)`, increment `count`.
3. Return `count`.

Why it works: The property depends only on the popcount of each number. Popcount is constant-time; primality for small `bits` is trivial.

## Complexity

- Time: $O((right - left + 1) \cdot B)$, with constant $B$ for popcount and tiny prime test; effectively linear in range length.
- Space: $O(1)$.

## Optimality

Given we must inspect each number to know its popcount, linear time is optimal. A small lookup table for primes up to 32 could replace `isPrime`, but asymptotically the same.

## Edge Cases

- `left == right`: single number check.
- Very small counts (0,1) are non-prime; handled in `isPrime`.
- Range endpoints near `Integer.MAX_VALUE` still fine; popcount works on signed ints.

## Comparison Table

| Aspect | Popcount + trial prime (Solution) | Precomputed prime mask (alternative) |
| --- | --- | --- |
| Prime check | Trial up to sqrt(bits) | Table/bitmask for counts ≤ 32 |
| Time | $O(n)$ | $O(n)$ |
| Code | Simple | Slightly shorter per-iter |

## Key Snippet (Java)

```java
int ans = 0;
for (int i = left; i <= right; i++) {
		int b = Integer.bitCount(i);
		if (isPrime(b)) ans++;
}
```

## Example Walkthrough

`left = 6 (110)`, `right = 10 (1010)`

- 6 → popcount 2 (prime) ✓
- 7 → 3 (prime) ✓
- 8 → 1 (not)
- 9 → 2 (prime) ✓
- 10 → 2 (prime) ✓
Answer = 4.

## Insights

- Popcount reduces the problem to tiny integers; you could precompute `primeMask = 0b10100010100010100010100010100010` to check primes via `(primeMask & (1 << bits)) != 0` for micro-optimization.

## References

- Solution implementation in [762. Prime Number of Set Bits in Binary Representation/Solution.java](762.%20Prime%20Number%20of%20Set%20Bits%20in%20Binary%20Representation/Solution.java)
