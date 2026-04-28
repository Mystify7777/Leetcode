# How Why - Explanation 2033. Minimum Operations to Make a Uni-Value Grid

[2033. Minimum Operations to Make a Uni-Value Grid](https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/)

## Problem

Given an `m x n` grid of integers and an integer `x`, you may add or subtract `x` to any cell any number of times.

Return the minimum number of operations needed to make all cells equal, or `-1` if impossible.

## Intuition

All values must be congruent modulo `x` to be transformable into the same final value. If that holds, the cost to change a value `v` to target `t` is `|v - t| / x`.

Given the cost per value is proportional to absolute difference to `t`, the median minimizes the sum of absolute deviations. Thus choose `t` as the median of all cells (after flattening), then sum the per-cell operation counts.

## Approach (Flatten + Sort + Median)

1. Flatten the grid into a single list `values`.
2. Sort `values`.
3. Check feasibility: every `val` must satisfy `(val - values[0]) % x == 0` (all values share the same remainder mod `x`). If not, return `-1`.
4. Let `median = values[values.size() / 2]`.
5. Sum `|val - median| / x` for all values; return the sum.

This is implemented in [2033. Minimum Operations to Make a Uni-Value Grid/Solution.java](2033.%20Minimum%20Operations%20to%20Make%20a%20Uni-Value%20Grid/Solution.java#L3-L28).

## Why the median?

Minimizing the sum of absolute deviations is the classic median property: for a set of numbers, the median minimizes the sum of absolute distances to a target. Since each operation moves a value by `x`, minimizing total operations is equivalent to minimizing sum |v - t|.

## Complexity

- Time: `O(m * n log(m * n))` due to sorting.
- Space: `O(m * n)` for the flattened list.

## Edge Cases

- If any two values differ in remainder modulo `x`, return `-1`.
- Single-cell grid: zero operations.
- Use median, not mean — mean does not minimize sum of absolute deviations.

## Alternate Implementation

`AltSolution` in the same file uses a counting approach when values are bounded (frequency array and reconstruction) to avoid explicit sorting; it still selects the median and sums distances divided by `x`.

