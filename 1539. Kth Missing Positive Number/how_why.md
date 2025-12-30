# 1539. Kth Missing Positive Number

## Problem

Given a strictly increasing array of positive integers `arr` and an integer `k`, return the k-th positive integer that is missing from `arr`.

## Approach (linear pass, adjust k)

- Walk the array; for each value `i`:
  - If `i <= k`, it means `i` is not missing before position `k`, so the answer is shifted right by one → increment `k`.
  - If `i > k`, the k-th missing lies before `i`; break and return `k`.
- If the loop finishes, `k` already accounts for all seen numbers; return `k`.

## Why this works

- `k` represents the target missing value assuming the array had no elements.
- When we see a number `i` that is less than or equal to our current `k`, it occupies one of the numbers we were counting as missing, so the missing target moves forward by one.
- The first time we encounter `i > k`, none of the array elements cover `k`, so `k` is exactly the k-th missing number.

## Example

`arr = [2,3,4,7,11], k = 5`

- Start `k=5`
- `i=2` (<=5) → k=6
- `i=3` (<=6) → k=7
- `i=4` (<=7) → k=8
- `i=7` (<=8) → k=9
- `i=11` (>9) → break, answer=9

## Complexity

- Time: O(n) for one pass over `arr`.
- Space: O(1).

## Edge considerations

- If `arr` starts beyond 1 (e.g., `[5,6]`), loop never shifts `k` for early missing numbers; answer is the initial `k` adjusted as we proceed.
- After the loop, returning `k` covers the case where the k-th missing is beyond the largest array element.
