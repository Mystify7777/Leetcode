# How Why Explanation - 693. Binary Number with Alternating Bits

## Problem

Given a positive integer `n`, return `true` if its binary representation has alternating bits (no two adjacent bits are equal), otherwise `false`.

## Intuition

For an alternating pattern like `101010...` or `010101...`, if you XOR the number with itself right-shifted by 1, every adjacent pair becomes `1`, producing a mask of consecutive 1s (e.g., `11111`). A number that is all 1s satisfies `x & (x+1) == 0`. So we can detect alternation with a couple of bitwise operations.

## Brute Force (Not Used)

- Walk through bits from LSB upward, comparing each bit with the previous. Time $O(\text{bit length})$, space $O(1)$.

## Approach (XOR + all-ones check)

1. Compute `x = n ^ (n >> 1)`. If `n` is alternating, `x` becomes a sequence of all 1s the same length as `n`'s bits.
2. Check if `x` is of the form `111...1` by testing `x & (x + 1) == 0`.
3. Return the result of that test.

Why it works: XOR with a 1-bit shift sets a 1 wherever adjacent bits differ. Alternating bits differ everywhere, so `x` is all 1s; any deviation introduces a 0 somewhere, breaking the all-ones property.

## Complexity

- Time: $O(1)$ for fixed-width integers.
- Space: $O(1)$.

## Optimality

Only two word-level operations; this is as fast as it gets on fixed-width integers. The brute-force scan is also $O(1)$ for 32 bits but more instructions.

## Edge Cases

- Single-bit numbers (1): alternating by definition.
- Highest bit patterns still work; shift uses sign bit in Java, but XOR logic holds for positive `n` (per problem statement).

## Comparison Table

| Aspect | XOR/all-ones test (Solution) | Bit-by-bit scan |
| --- | --- | --- |
| Time | $O(1)$ | $O(\text{bits})$ (constant 32) |
| Operations | Few word ops | Loop with branches |
| Readability | Concise | Very clear |

## Key Snippet (Java)

```java
int x = n ^ (n >> 1);
return (x & (x + 1)) == 0;
```

## Example Walkthrough

`n = 5 (101b)`

- `n >> 1 = 10b`
- `x = 101 ^ 010 = 111`
- `x & (x+1) = 111 & 1000 = 0` → alternating.

`n = 7 (111b)`

- `n >> 1 = 11b`
- `x = 111 ^ 011 = 100`
- `x & (x+1) = 100 & 101 = 100 ≠ 0` → not alternating.

## Insights

- The `x & (x+1)` trick is a standard test for “x is all 1s”. Combining it with the XOR shift captures alternation in two lines.

## References

- Solution implementation in [693. Binary Number with Alternating Bits/Solution.java](693.%20Binary%20Number%20with%20Alternating%20Bits/Solution.java)
