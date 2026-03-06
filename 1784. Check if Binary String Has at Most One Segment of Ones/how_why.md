# How Why Explanation - 1784. Check if Binary String Has at Most One Segment of Ones

## Problem

Given a binary string `s`, determine if it contains at most one contiguous segment of `'1'`s (i.e., all 1s are consecutive with no 1s after a 0).

## Intuition

Once a `0` is seen, any later `1` would start a second segment and violate the condition. So a single left-to-right pass tracking whether we've already crossed the first zero suffices.

## Approach (one pass)

- Traverse characters; keep `seenZero` flag.
- On `0`, set `seenZero = true`.
- On `1`, if `seenZero` is already true, return `false` (a second segment appears).
- If the loop completes, return `true`. Implementation in [1784. Check if Binary String Has at Most One Segment of Ones/Solution.java](1784.%20Check%20if%20Binary%20String%20Has%20at%20Most%20One%20Segment%20of%20Ones/Solution.java#L4-L18).

## Complexity

- Time: O(n).
- Space: O(1).

## Edge Cases

- All 1s or all 0s → valid.
- Single char → always valid.
- Pattern like `110011` is invalid once the second `1` appears after zeros.

## Alternate Approaches

- **String search:** Check if `s.contains("01")` and ensure no `1` after that index (or equivalently `s.indexOf("01") == s.lastIndexOf("01")` and not followed by 1), but the simple pass is clearer.
- **Regex:** Test against `1*0*` (e.g., `s.matches("1*0*")`), but less efficient than a loop.
