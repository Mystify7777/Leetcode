# 1513. Number of Substrings With Only 1s

## Recap

Given a binary string `s`, count the number of substrings that contain only `'1'` characters. Return the answer modulo `10^9 + 7`.

## Intuition

Any contiguous block of `'1'`s of length `k` contributes `k * (k + 1) / 2` substrings (choosing start and end within that block). For example, `"111"` has 3 substrings of length 1, 2 of length 2, and 1 of length 3, totaling `3 + 2 + 1 = 6 = 3 * 4 / 2`.

We can either:

1. **Count on-the-fly**: Track the current run length `cnt`; each time we see a `'1'`, increment `cnt` and add it to the total (because extending the run by one adds `cnt` new substrings ending at the current position).
2. **Count per block**: When we finish a block (hit a `'0'` or end of string), add `cnt * (cnt + 1) / 2` to the answer and reset `cnt`.

Both yield the same result; your main solution uses the incremental approach, and the alternate uses block-wise counting.

## Approach (Incremental Counting)

Scan left to right:

- Maintain `cnt` = current length of the consecutive `'1'` run.
- If `s[i] == '1'`: increment `cnt`, then add `cnt` to the running total (mod `10^9 + 7`).
- If `s[i] == '0'`: reset `cnt = 0`.

At the end, return the total.

## Code (Java)

```java
class Solution {
    public int numSub(String s) {
        long cnt = 0, total = 0, mod = 1000000007;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                cnt++;
            } else {
                cnt = 0;
            }
            total = (total + cnt) % mod;
        }
        return (int) total;
    }
}
```

## Correctness

- Each `'1'` extends the current run by one. Adding `cnt` accounts for all substrings ending at position `i` within that run (there are exactly `cnt` such substrings: those starting at `i - cnt + 1, i - cnt + 2, ..., i`).
- When we hit a `'0'`, resetting `cnt` ensures we start fresh for the next block.
- Summing these contributions across the entire string correctly counts all substrings of consecutive `'1'`s without overlap or omission.

## Complexity

- Time: `O(n)` — single pass over the string.
- Space: `O(1)` — only constant counters.

## Alternative (Block-wise Counting)

The commented solution counts each block explicitly:

```java
long ans = 0, count = 0;
for (char c : s.toCharArray()) {
    if (c == '1') {
        count++;
    } else {
        ans += count * (count + 1) / 2;
        count = 0;
    }
}
ans += count * (count + 1) / 2; // final block if string ends with '1's
return (int) (ans % 1000000007);
```

This computes the combinatorial formula `k(k+1)/2` for each block of length `k`. Both approaches are equivalent; the incremental method applies modulo at each step, while the block-wise defers modulo until the end.

## Edge Cases

- All `'1'`s: one block of length `n` contributes `n(n+1)/2`.
- All `'0'`s: no blocks → answer = `0`.
- Alternating `"101010..."`: each isolated `'1'` contributes `1`.
- Single character `"1"`: answer = `1`.

## Takeaways

- For counting substrings of a homogeneous run, either increment-per-character or sum-per-block works.
- Incremental counting is simpler for modular arithmetic (apply mod at each step).
- Formula `k(k+1)/2` is central for counting contiguous subarrays/substrings from a segment of length `k`.
