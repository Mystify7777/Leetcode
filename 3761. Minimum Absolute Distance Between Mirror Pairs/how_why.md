# 3761. Minimum Absolute Distance Between Mirror Pairs - How Why Explanation

[Problem Link 🔗](https://leetcode.com/problems/minimum-absolute-distance-between-mirror-pairs/)

## Goal

Find the minimum distance `j - i` such that `i < j` and `nums[i]` is the digit-reverse of `nums[j]`. Return `-1` if no such pair exists.

## Idea in 3 lines

- Scan left to right, treating current index as `j`.
- Use a hash map to remember where each reversed value last appeared from the left side.
- For current value `x`, if map contains `x`, then some earlier index has reverse equal to `x`, so update the minimum distance.

## Algorithm (matches `Solution`)

1. Keep `seen: reversedValue -> latest index`.
2. For each position `i` with value `x`:
	- If `seen` contains key `x`, candidate answer is `i - seen.get(x)`.
	- Compute `rev = reverseDigits(x)`.
	- Store `seen.put(rev, i)` so future elements can match against this one.
3. If no candidate was found, return `-1`; otherwise return the minimum distance.

## Why it works

- Condition `nums[i] == reverse(nums[j])` is equivalent to `reverse(nums[i]) == nums[j]`.
- By storing `reverse(nums[i])` at index `i`, we can test a future `nums[j]` in O(1) map lookup.
- Keeping the latest index for each key is optimal for minimizing `j - i` at the moment we process `j`.

## Complexity

- Time: O(n * d), where `d` is number of digits per number (digit reversal cost); effectively O(n) for bounded integer size.
- Space: O(n) in worst case for the map.

## Note

This implementation reverses digits with repeated `% 10` and `/ 10`, and uses one pass with no sorting.
 