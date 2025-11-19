# Recap

You are given an array `bits` that can be decoded into characters using the following rules: `0` represents a 1-bit character, and `10` or `11` represent 2-bit characters. The array always ends with `0`. Return `true` if the last character decoded is a 1-bit character (i.e., the final `0` stands alone), otherwise return `false`.

## Intuition

Since the encoding is unambiguous (any `1` must be followed by another bit to form a 2-bit character), we can greedily decode from left to right. If we consume bits and land exactly on the last index (`n-1`), that final `0` was decoded as a standalone 1-bit character. If we skip over it (landing at `n`), the final `0` was part of a 2-bit character ending the previous step.

## Approach

1. Initialize `i = 0` to track the current decoding position.
2. While `i < n - 1` (before the last bit):
   - If `bits[i] == 0`, it's a 1-bit character: move `i += 1`.
   - If `bits[i] == 1`, it's a 2-bit character: move `i += 2`.
   - This can be written as `i += bits[i] + 1`.
3. After the loop, check if `i == n - 1`:
   - `true`: We stopped exactly at the last bit, so it's a standalone 1-bit character.
   - `false`: We skipped past it (`i == n`), so the last `0` was part of a 2-bit character.

## Code (Java)

```java
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = 0;
        while (i < n - 1)
            i += bits[i] + 1;
        return i == n - 1;
    }
}
```

## Correctness

- The encoding guarantees that every `1` starts a 2-bit character, so greedy decoding is always correct.
- The loop processes all bits except the last one.
- If we land on `n-1`, the last `0` wasn't consumed by any prior 2-bit character, so it's a 1-bit character.
- If we land on `n`, the previous step consumed the last `0` as part of a 2-bit character (e.g., `10` or `11` ending at the last position, though `11` would violate the "ends with 0" constraint, so effectively `10`).
- The problem guarantees the last bit is `0`, ensuring valid decoding.

## Complexity

- Time: `O(n)` single pass through the array.
- Space: `O(1)` auxiliary.

## Edge Cases

- `bits = [0]`: Single 1-bit character, returns `true`.
- `bits = [1, 0]`: Single 2-bit character, the final `0` is part of it, returns `false`.
- `bits = [1, 1, 0]`: `11` is a 2-bit character, final `0` is standalone, returns `true`.
- `bits = [1, 1, 1, 0]`: `11` then `10`, final `0` is part of the second 2-bit character, returns `false`.
- Long sequences: handled efficiently in linear time.

## Takeaways

- Greedy decoding works when the encoding is prefix-free (unambiguous).
- The trick `i += bits[i] + 1` elegantly handles both cases: `0 + 1 = 1` step, `1 + 1 = 2` steps.
- Checking the final position after decoding reveals whether the last bit was consumed or stands alone.
- Problems involving unique encodings often admit simple linear scans without backtracking.
