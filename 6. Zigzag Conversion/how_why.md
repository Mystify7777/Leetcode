# How & Why — 6. Zigzag Conversion

## Problem

Convert string `s` into a zigzag pattern on `numRows` rows, then read row-by-row to produce the output string.

Example (`numRows = 3`):

```java
P   A   H   N
A P L S I I G
Y   I   R
```

Output: `PAHNAPLSIIGYIR`.

## Intuition

Two solid approaches:

- Simulate writing the zigzag by appending characters to each row as we move down and up.
- Use the cycle length `cycle = 2 * numRows - 2` to compute indices row-by-row without simulation.

If `numRows == 1` (or `numRows >= len(s)`), the zigzag is just `s`.

## Algorithm A (Simulate Rows)

1. Handle `numRows == 1` → return `s`.
2. Create an array `rows` of size `min(numRows, len(s))`.
3. Walk through `s`, maintaining `curRow` and `direction` (down or up).
4. Append each char to `rows[curRow]`; flip direction at the top and bottom.
5. Concatenate all `rows` to form the answer.

### Pseudocode

```java
if numRows == 1: return s

rows = ["" for _ in range(min(numRows, len(s)))]
curRow = 0
goingDown = False

for ch in s:
        rows[curRow] += ch
        if curRow == 0 or curRow == numRows - 1:
                goingDown = not goingDown
        curRow += 1 if goingDown else -1

return "".join(rows)
```

## Algorithm B (Index Math by Cycles)

- Let `cycle = 2 * numRows - 2`. For each row `r`:
  - Take vertical indices `i = r + k*cycle` while `i < len(s)`.
  - For middle rows (`0 < r < numRows-1`), also take diagonal indices `j = i + cycle - 2*r` if `j < len(s)`.

This directly collects characters in row order without building the zigzag explicitly.

## Complexity

- Time: O(n) — each character is processed once.
- Space: O(n) for the output; O(numRows) extra in simulation; O(1) extra in math approach (aside from the output string).

## Edge Cases

- `numRows == 1` → return `s`.
- `numRows >= len(s)` → return `s`.
- Very short strings → both methods reduce to trivial concatenation.

## Why This Works (Correctness)

- In simulation, we preserve the exact zigzag write order, then read rows left-to-right — matching the problem definition.
- In math indexing, a zigzag repeats every `cycle = 2*numRows-2` characters. Each row `r` receives characters at fixed offsets within each cycle: the vertical `r` and the diagonal `cycle - r` for middle rows, ensuring we collect the same sequence as the simulated pattern.

## Quick Tests

- `s = "PAYPALISHIRING", numRows = 3` → `PAHNAPLSIIGYIR`.
- `s = "PAYPALISHIRING", numRows = 4` → `PINALSIGYAHRPI`.
- `s = "A", numRows = 1` → `A`.
- `s = "AB", numRows = 1` → `AB`.

## Notes

- Simulation is simpler to implement and reason about; indexing is faster and avoids building intermediate rows.
- Pick the approach that fits your environment and preferred style.
