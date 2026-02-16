# How Why Explanation - 190. Reverse Bits

## Problem

Given a 32-bit unsigned integer, return the bit pattern reversed (bit 0 becomes bit 31) as a 32-bit unsigned result.

## Intuition

You can reverse bits by pulling off the least-significant bit one at a time and pushing it into the result from the most-significant side. Or you can swap fixed-size blocks with masks to reverse all bits in a handful of operations.

## Brute Force (Not Used)

- Convert to a 32-length binary string, reverse it, parse back. Works but adds string overhead.

## Approach A (Loop and shift)

1. Initialize `res = 0`.
2. Repeat 32 times: left-shift `res` by 1, append the current LSB of `n` if set, then right-shift `n` by 1.
3. Return `res`.

Why it works: Each iteration consumes one input bit from LSB to MSB and appends it to the growing result, which is shifted toward MSB each step.

## Approach B (Mask/block swaps)

Apply fixed masks to swap progressively smaller blocks:

- Swap 16-bit halves: `((x & 0xffff0000) >>> 16) | ((x & 0x0000ffff) << 16)`
- Swap bytes: `0xff00ff00` / `0x00ff00ff`
- Swap nibbles: `0xf0f0f0f0` / `0x0f0f0f0f`
- Swap bit pairs: `0xcccccccc` / `0x33333333`
- Swap single bits: `0xaaaaaaaa` / `0x55555555`

Why it works: Each stage reverses within its block size; composing all stages fully reverses the 32-bit word in $O(1)$ time.

## Complexity

- Loop/shifting: Time $O(32)$, Space $O(1)$.
- Mask swapping: Time $O(1)$ (constant passes), Space $O(1)$.

## Optimality

Both are constant-time/space. The masked version is branchless and fast; the loop is simplest to read and avoids memorizing masks.

## Edge Cases

- Input 0 or all-ones return the same pattern reversed (identical for all-ones).
- Sign bit is just another bit; 32 fixed iterations handle Java’s arithmetic shift when using the loop method.

## Comparison Table

| Aspect | Loop shift (Solution) | Masked swaps (encryptedSolution) |
| --- | --- | --- | --- |
| Ops | 32 iterations | 5 mask stages | Branchless |
| Branches | One conditional per iter | None | Micro-optimized |
| Readability | High | Low | Faster |

## Key Snippet (Loop)

```java
int res = 0;
for (int i = 0; i < 32; i++) {
    res <<= 1;
    if ((n & 1) != 0) res++;
    n >>= 1;
}
return res;
```

## Example Walkthrough

`n = 00000000000000000000000000000101 (5)`

- Iter0: res=0→1, n=2
- Iter1: res=2, n=1
- Iter2: res=5, n=0
- Remaining shifts move bits left; final result has the original bits in reversed positions with leading 1s in the topmost two slots.

## Insights

- Mask sequence is a classic bit hack; the loop is interview-friendly if masks aren’t memorized.
- Fixed 32 iterations avoid unsigned-right-shift nuances; the loop works for any int input.

## References

- Solution implementations in [190. Reverse Bits/Solution.java](190.%20Reverse%20Bits/Solution.java)
