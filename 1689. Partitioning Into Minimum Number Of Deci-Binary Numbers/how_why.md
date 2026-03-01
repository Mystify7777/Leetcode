# How Why Explanation - 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers

## Problem

Given a decimal string `n`, split it into the fewest "deci-binary" numbers (digits only 0 or 1) whose sum equals `n`. Return that minimum count.

## Intuition

Each digit of the sum is formed by stacking 0/1 digits from all summands. The number of summands needed at position `i` is exactly the digit value `n[i]`, because at most one `1` can come from each summand in that column. Therefore the answer is the largest digit anywhere in `n`.

## Approach

- Scan the string, track the maximum digit seen.
- Early-exit if a `9` is found since that is the maximum possible digit.
- Return the maximum digit. Implementation in [1689. Partitioning Into Minimum Number Of Deci-Binary Numbers/Solution.java](1689.%20Partitioning%20Into%20Minimum%20Number%20Of%20Deci-Binary%20Numbers/Solution.java#L4-L11).

## Complexity

- Time: O(len(n)).
- Space: O(1).

## Edge Cases

- Single digit: answer is that digit.
- Contains `9`: answer is 9 immediately.
- All zeros: answer is 0 (though constraints typically avoid leading zeros for the whole number, interior zeros are fine).

## Alternate Approaches

- **Explicit construction:** Build `maxDigit` deci-binary strings by scanning `n` once and appending `'1'` to the first `digit` strings at each column. Confirms optimality and provides actual summands.
- **Column-wise counting:** For each digit `d`, add `d` to a running maximum; same result but framed as “how many ones are needed in this column” rather than a max lookup.
- **DP (overkill):** You could model each position with carry as a state, but it collapses to tracking only the maximum digit, so it adds complexity without benefit.
