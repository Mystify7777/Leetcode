# How & Why — 167. Two Sum II (Sorted Array)

## Problem

Given a sorted (non-decreasing) array numbers and a target, find the indices of two numbers whose sum equals target. Indices are 1-based, and there is exactly one solution; you may not reuse the same element.

## Intuition

Sorting unlocks a monotonic behavior for pair sums: pairing the smallest with the largest gives a sum that moves predictably. If the sum is too small, we need a larger left value; if too large, we need a smaller right value. This suggests a two-pointer scan from both ends.

## Algorithm (Two Pointers)

1. Set left = 0, right = n - 1.
2. While left < right:
   - sum = numbers[left] + numbers[right].
   - If sum == target: return (left + 1, right + 1).
   - If sum < target: increment left to increase the sum.
   - Else (sum > target): decrement right to decrease the sum.
3. If no pair found (in general use), return not found. In this problem, a solution is guaranteed.

### Pseudocode

```java
left = 0
right = len(numbers) - 1
while left < right:
    s = numbers[left] + numbers[right]
    if s == target:
        return left + 1, right + 1
    elif s < target:
        left += 1
    else:
        right -= 1
```

## Complexity

- Time: O(n) — each step moves a pointer inward; at most n - 1 moves.
- Space: O(1) — uses constant extra memory.

## Edge Cases

- Duplicates: handled naturally; pointers still move by sum comparison.
- Negatives/zeros: algorithm works for any integers as long as the array is sorted.
- Minimal length: n = 2 is trivially checked.

## Why This Works (Correctness)

Let sum = numbers[left] + numbers[right].
    - If sum < target, any pair with left and an index < right will be <= current sum (since those right-side elements are <= numbers[right]). To increase the sum, we must move left rightward to a larger value.
    - If sum > target, any pair with right and an index > left will be >= current sum (since those left-side elements are >= numbers[left]). To decrease the sum, we must move right leftward to a smaller value.
This monotone adjustment guarantees progress toward the target without missing the solution.

## Alternatives

- Binary search per element: for each i, binary-search target - numbers[i] in i+1..n-1. Time O(n log n), Space O(1).
- Hash map: O(n) time but O(n) space; unnecessary given the sorted property and two-pointer O(n)/O(1) solution.

## Quick Tests

- numbers = [2, 7, 11, 15], target = 9 -> (1, 2).
- numbers = [1, 2, 3, 4], target = 7 -> (3, 4).
- numbers = [-3, -1, 0, 2, 5], target = 1 -> (2, 4).

## Notes

- Return 1-based indices as required.
- Works in streaming fashion if numbers can be accessed from both ends.
