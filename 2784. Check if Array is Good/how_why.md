# 2784. Check if Array is Good

An array is "good" when it contains every value from `1` to `n - 1` exactly once and the value `n` exactly twice, where `n` is `nums.length - 1`.

## Approach 1: Set-based validation

`Solution` checks the array in one pass:
- Reject any number larger than `n = nums.length - 1`.
- Track seen values in a set.
- Allow exactly one duplicate, and only for value `n`.

This works because the required shape of a good array is very specific: all values from `1..n-1` must appear once, and `n` must appear twice.

## Approach 2: Frequency counting

`Solution2` first finds the maximum value, then checks:
- The array length must be `max + 1`.
- Every value from `1` to `max - 1` appears exactly once.
- `max` appears exactly twice.

This version is straightforward and uses the constraints to keep the frequency array small.

## Complexity

- Time: `O(n)`
- Space: `O(n)` for the set or frequency array
