# How Why Explanation - 1855. Maximum Distance Between a Pair of Values

## Problem

Given two non-increasing arrays `nums1` and `nums2`, find the maximum `j - i` such that:

- `0 <= i < nums1.length`
- `0 <= j < nums2.length`
- `i <= j`
- `nums1[i] <= nums2[j]`

Return that maximum distance.

## Intuition

Because both arrays are non-increasing, two pointers are enough:

- `j` moves through `nums2` from left to right.
- `i` only moves forward when current pair becomes invalid (`nums1[i] > nums2[j]`).

This works because once `nums1[i] > nums2[j]`, that `i` cannot pair with current `j`; increasing `i` is the only way to restore validity.

## Approach (two pointers)

1. Start with `i = 0`, `j = 1`.
2. For each `j` while in bounds:
	- if `nums1[i] > nums2[j]`, increment `i`.
	- always advance `j` in the loop.
3. After scan, answer is `j - i - 1` (the best valid distance seen at the furthest feasible `j`).

This is exactly the implementation in [1855. Maximum Distance Between a Pair of Values/Solution.java](1855.%20Maximum%20Distance%20Between%20a%20Pair%20of%20Values/Solution.java#L4-L13).

## Complexity

- Time: O(n + m), where `n = nums1.length`, `m = nums2.length`.
- Space: O(1).

## Edge Cases

- If no valid pair extends beyond equal indices, result is `0`.
- If arrays are length 1 and valid, result is `0`.
- Large arrays still run linearly since each pointer moves only forward.

## Alternate Approaches

- **Binary search per `i`:** For each `i`, find farthest valid `j` in `nums2` using binary search. Complexity O(n log m).
- **Two pointers (used here):** Better linear solution due to sorted (non-increasing) property.
