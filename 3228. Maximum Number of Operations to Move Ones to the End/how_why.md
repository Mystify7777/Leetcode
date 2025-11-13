# 3228. Maximum Number of Operations to Move Ones to the End

## Recap

You have a binary string `s`. In one operation you choose an index `i` where `s[i] == '1'` and `s[i+1] == '0'`, then move that `'1'` rightward until it hits the end of the string or another `'1'`. Count the maximum number of operations you can perform.

## Key Observation

Each operation moves a `'1'` across a contiguous block of `'0'`s. Crucially, once a `'1'` is moved to the right, it can never be moved again (it either reaches the end or is immediately adjacent to another `'1'`). This means every `'1'` is moved **at most once**, and the total operations equals the sum of **how many zeros each `'1'` jumps over** before stopping.

Alternatively, think of it this way: whenever we encounter a transition from `'1'` to `'0'` (a "boundary" where operations are possible), all the `'1'`s to the left will eventually be shifted across that zero block. Each such transition contributes `count_of_ones_seen_so_far` operations, because each of those `'1'`s will cross that zero boundary exactly once during its rightward journey.

## Intuition (Left-to-Right Scan)

Scan the string from left to right:

- Track `ones` = count of `'1'`s encountered so far.
- When we see a `'0'` immediately following a `'1'` (i.e., `s[i-1] == '1'` and `s[i] == '0'`), we've hit the start of a zero block. Every `'1'` counted so far will cross this zero block in some operation, contributing `ones` to the total.
- Accumulate these contributions across all zero blocks that follow a `'1'`.

## Approach

```text
Initialize ones = 0, result = 0
For each index i from 0 to n-1:
  if s[i] == '1':
    ones++
  else if i > 0 and s[i-1] == '1':
    result += ones  // all prior ones will cross this zero block
return result
```

## Code (Java)

```java
class Solution {
    public int maxOperations(String s) {
        int ones = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1')
                ones++;
            else if (i > 0 && s.charAt(i - 1) == '1')
                res += ones;
        }
        return res;
    }
}
```

## Correctness

- Each `'1'` can only be moved once (rightward until it can't move further).
- When a `'1'` moves, it crosses all zeros between itself and the next `'1'` (or end).
- A transition from `'1'` to `'0'` marks the start of a zero block. All `'1'`s to the left will cross this block exactly once. By accumulating `ones` at each such transition, we count every crossing operation.
- Consecutive zeros after the first are effectively part of the same block; only the first `'0'` after a `'1'` triggers the count (via the condition `s[i-1] == '1'`).

## Complexity

- Time: `O(n)` — single pass over the string.
- Space: `O(1)` — only counters.

## Edge Cases

- All `'1'`s: no `'0'`s to cross → result = `0`.
- All `'0'`s: no `'1'`s to move → result = `0`.
- String ending with `'0'`s: the final zero block is counted when we see the transition from the last `'1'`.
- String starting with `'0'`s: no transition from `'1'` yet, so no operations added initially.

## Example Walkthrough

**Input**: `s = "1001101"`

| i | s[i] | ones | Transition `1→0`? | res update | res |
|---|------|------|-------------------|------------|-----|
| 0 | 1    | 1    | no                | —          | 0   |
| 1 | 0    | 1    | yes (s[0]='1')    | res += 1   | 1   |
| 2 | 0    | 1    | no (s[1]='0')     | —          | 1   |
| 3 | 1    | 2    | no                | —          | 1   |
| 4 | 1    | 3    | no                | —          | 1   |
| 5 | 0    | 3    | yes (s[4]='1')    | res += 3   | 4   |
| 6 | 1    | 4    | no                | —          | 4   |

**Output**: `4` ✓

## Alternative Approach (Right-to-Left)

The commented alternate solution scans from right to left, counting blocks of `'1'`s and zeros:

- Each time we finish a block of `'1'`s (moving leftward into a block of `'0'`s), we increment a `running` counter.
- For each `'1'` encountered in that block, we add `running` (number of zero blocks to its right) to the result.

This is effectively counting "how many zero blocks lie to the right of each `'1'`," which equals the number of times that `'1'` will be moved. Both approaches yield the same answer.

## Takeaways

- Counting transitions (`1 → 0`) and accumulating prior `'1'`s elegantly tracks operation totals without simulating moves.
- Each `'1'` contributes to the answer exactly once, when it crosses the zero block immediately to its right.
- Linear scan with simple counters achieves optimal `O(n)` time and `O(1)` space.
