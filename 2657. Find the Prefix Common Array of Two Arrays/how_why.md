# 2657. Find the Prefix Common Array of Two Arrays

The key observation is that we only need to know how many values have appeared in both prefixes up to each index.

## Approach

Use a frequency array of size `n + 1`, where `freq[x]` tracks how many times value `x` has been seen across both arrays so far.

For each index `i`:
- Increment `freq[A[i]]`.
- If it becomes `2`, then `A[i]` is now common to both prefixes, so increase the `common` counter.
- Do the same for `B[i]`.
- Store `common` in `ans[i]`.

## Why it works

A value contributes to the prefix common count exactly when it has appeared once in each array within the processed prefixes. The first time the combined frequency reaches `2`, that value is now present in both prefixes, and we count it once.

## Complexity

- Time: `O(n)`
- Space: `O(n)`
