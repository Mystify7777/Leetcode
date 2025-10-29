# 3370. Smallest Number With All Set Bits — how and why

## Problem recap

Given a positive integer `n`, find the smallest number greater than or equal to `n` that has all bits set to 1 in its binary representation. In other words, find the smallest number ≥ `n` of the form `2^k - 1` (which is binary `111...1` with `k` ones).

## Core intuition

Numbers with all bits set are exactly those of the form `2^k - 1`:

- 1 = 2^1 - 1 = `1` (binary)
- 3 = 2^2 - 1 = `11` (binary)
- 7 = 2^3 - 1 = `111` (binary)
- 15 = 2^4 - 1 = `1111` (binary)
- ...

These numbers have a nice recurrence: `next = current * 2 + 1`. Starting from 1, this builds the sequence 1 → 3 → 7 → 15 → 31 → ...

The task is to keep doubling-plus-one until we reach or exceed `n`.

## Approach — iterative construction

Start with `res = 1`. While `res < n`, update `res = res * 2 + 1`. Once `res >= n`, return `res`.

This builds the smallest all-set-bits number that is not smaller than `n`.

## Implementation (matches `Solution.java`)

```java
class Solution {
    public int smallestNumber(int n) {
        int res = 1;
        while (res < n)
            res = res * 2 + 1;
        return res;
    }
}
```

The loop iterates at most O(log n) times, since each iteration roughly doubles `res`.

## Why this works

Each iteration transforms `res` into the next number with all bits set:

- `res * 2` shifts the bits left (e.g., `111` becomes `1110`)
- Adding 1 fills in the rightmost bit (e.g., `1110` becomes `1111`)

So `res * 2 + 1` always produces a number with one more bit set than before. Starting from 1, this generates the sequence of all-ones numbers. We stop as soon as `res >= n`, guaranteeing `res` is the smallest such number.

## Complexity

- **Time:** O(log n) — each iteration roughly doubles `res`, so we need at most log₂(n) iterations.
- **Space:** O(1) — only a single variable `res` is used.

## Example

Suppose `n = 5`.

- Start: `res = 1` (binary `1`)
- Iteration 1: `res = 1 * 2 + 1 = 3` (binary `11`) < 5
- Iteration 2: `res = 3 * 2 + 1 = 7` (binary `111`) >= 5 → stop
- Return 7.

Another example: `n = 7`.

- Start: `res = 1`
- Iteration 1: `res = 3` < 7
- Iteration 2: `res = 7` >= 7 → stop
- Return 7 (since 7 already has all bits set).

## Edge cases to consider

- `n = 1` → returns 1 immediately (since `res = 1` already equals `n`).
- `n` is already of the form `2^k - 1` → returns `n` itself.
- Large `n` → the loop iterates log₂(n + 1) times at most, so performance remains excellent even for large integers.

## Takeaways

- Numbers with all bits set form a simple geometric sequence: 1, 3, 7, 15, 31, ...
- The recurrence `res = res * 2 + 1` efficiently generates this sequence.
- Since we only need the first value in the sequence that's >= `n`, an iterative approach is optimal.
- This solution is both simple and efficient, running in logarithmic time with constant space.
