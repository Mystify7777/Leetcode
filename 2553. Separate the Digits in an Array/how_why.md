# 2553. Separate the Digits in an Array — Explanation

Problem: Given an array of non-negative integers, return an array of all the digits in the same order they appear when each number is written in base-10.

Approaches

- **String conversion (Solution)**: Convert each integer to a string and append each character's numeric value to a list, then convert the list to an `int[]`.
  - Time: O(T) where T is total number of digits across all numbers.
  - Space: O(T) for the output and intermediate list.

- **Arithmetic preallocation (Solution2)**: First count total digits to allocate final array, then fill it from right to left by extracting digits with modulo/division. This avoids intermediate lists.
  - Time: O(T)
  - Space: O(T) (output array only, no auxiliary list)

Notes
- Both solutions preserve the original left-to-right digit ordering per number.
- `Solution2` is slightly more memory-efficient in practice because it avoids boxing/unboxing and an intermediate `List<Integer>`.
