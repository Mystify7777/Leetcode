# 3300. Minimum Element After Replacement With Digit Sum

The task is to replace each number by the sum of its digits and return the minimum result after applying that operation to every number.

## Idea

For each number, compute its digit sum and keep track of the smallest value.

The provided solution uses arithmetic to extract digit contributions without converting to a string:
- `n / 10`, `n / 100`, `n / 1000`, and `n / 10000` are used to estimate the digit-sum reduction pattern.
- The result for each number is evaluated and the minimum is kept in `res`.

## Why it works

The transformation is applied independently to every element, so the final answer is simply the minimum transformed value across the array.

## Complexity

- Time: `O(n)`
- Space: `O(1)`
