# 1526. Minimum Number of Increments on Subarrays to Form a Target Array — how and why

## Problem recap

You start with an array of zeros of the same length as the target array. In each operation, you can choose any contiguous subarray and increment every element in that subarray by 1. The goal is to transform the zero array into the target array using the minimum number of such operations.

## Core intuition

Think of each operation as "painting" a contiguous segment by raising it by 1. To minimize the total number of operations, we should reuse operations as much as possible.

Key observation: when you move from one element to the next in the target array:

- If the next element is **higher**, you need additional operations to "lift" it up. The extra cost is `target[i] - target[i-1]`.
- If the next element is **lower or equal**, you can reuse some of the previous operations that were already "painting" the previous element. No additional cost is needed for the drop or plateau.

In other words, you only pay for **increases** as you scan left to right. The first element always requires `target[0]` operations to build from zero.

## Approach — greedy single pass

1. Initialize the result with `target[0]` (the cost to build the first element from 0).
2. Iterate from index 1 to the end.
3. For each index `i`, if `target[i] > target[i-1]`, add the difference `target[i] - target[i-1]` to the result.
4. If `target[i] <= target[i-1]`, add 0 (you can reuse existing operations).

This greedy approach works because we're building the array from left to right, and any "peak" requires new operations while "valleys" benefit from already-applied operations.

## Implementation (matches `Solution.java`)

```java
class Solution {
    public int minNumberOperations(int[] A) {
        int res = A[0];
        for (int i = 1; i < A.length; ++i)
            res += Math.max(A[i] - A[i - 1], 0);
        return res;
    }
}
```

The loop scans the array once, accumulating only the positive differences.

## Why this works

Imagine building the target array layer by layer from bottom to top. Each operation adds a horizontal "bar" covering some contiguous range:

- The first element requires `A[0]` bars stacked vertically.
- When moving to `A[i]`, if `A[i]` is taller than `A[i-1]`, you need `A[i] - A[i-1]` additional bars starting at position `i`.
- If `A[i]` is shorter or equal, the bars that were already covering `A[i-1]` can extend to cover `A[i]` as well, so no new bars are needed.

By summing the first element plus all positive differences, you count exactly the minimum number of bars (operations) required to build the entire array.

This is equivalent to counting the total "upward steps" in the array's profile.

## Complexity

- **Time:** O(n) — single pass through the array.
- **Space:** O(1) — only a single accumulator variable is used.

## Example

Suppose `target = [1, 2, 3, 2, 1]`.

- Start: `res = 1` (cost to build the first element).
- Index 1: `2 - 1 = 1` → `res = 1 + 1 = 2`
- Index 2: `3 - 2 = 1` → `res = 2 + 1 = 3`
- Index 3: `2 - 3 = -1` → max(0, -1) = 0 → `res = 3 + 0 = 3`
- Index 4: `1 - 2 = -1` → max(0, -1) = 0 → `res = 3 + 0 = 3`
- Return 3.

Visualizing the operations:

1. Operation covering [0..2] increments all to 1: `[1, 1, 1, 0, 0]`
2. Operation covering [1..2] increments to: `[1, 2, 2, 0, 0]`
3. Operation covering [2] increments to: `[1, 2, 3, 0, 0]`

Then, the existing operations naturally extend to cover the decreasing tail, resulting in the target `[1, 2, 3, 2, 1]` with strategic overlaps. Total operations = 3.

Another example: `target = [3, 1, 1, 2]`.

- Start: `res = 3`
- Index 1: `1 - 3 = -2` → max(0, -2) = 0 → `res = 3`
- Index 2: `1 - 1 = 0` → max(0, 0) = 0 → `res = 3`
- Index 3: `2 - 1 = 1` → `res = 3 + 1 = 4`
- Return 4.

## Edge cases to consider

- Single element array: returns `target[0]` (the base case).
- All elements equal: returns `target[0]` (no increases after the first).
- Strictly increasing array: returns the last element (sum of all increments).
- Strictly decreasing array: returns the first element (no additional operations needed after the peak).

## Takeaways

- The problem reduces to counting "upward steps" in the target array.
- A greedy left-to-right scan is optimal because each increase must be paid for, while decreases/plateaus reuse existing operations.
- The solution is elegant, runs in linear time, and uses constant space.
- This technique generalizes to problems where you build structures incrementally and want to maximize reuse of operations.
