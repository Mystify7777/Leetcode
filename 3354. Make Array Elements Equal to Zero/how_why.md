# 3354. Make Array Elements Equal to Zero — how and why

## Problem recap

You’re given an integer array `nums`. For every index `i` where `nums[i] == 0`, you may start a process that repeatedly reduces elements on the left or right by one per step (per the problem’s rules). A start at `i` is valid if following the rules can make the whole array become all zeros. The task is to count how many such valid starts exist across all zero positions, with at most two directions per zero (left-first or right-first).

Key observation from the rules: starting at a zero index `i` is feasible depending only on the balance between the total sum of elements to its left and to its right.

## Core intuition

Think of all positive values as “units” that need to be consumed. Starting at a zero position `i`, you can consume units from the left or the right, one at a time. To finish exactly at zero everywhere:

- If the sum on the left equals the sum on the right, both orders are possible (left-first and right-first), yielding 2 valid selections.
- If the sums differ by exactly 1, only one order is possible (you must start on the heavier side), yielding 1 valid selection.
- Otherwise, it’s impossible to consume everything without getting stuck, yielding 0 valid selections.

This reduces to balancing the two sides around every zero index.

## Approach 1 — prefix/suffix sums (used in code)

We precompute for each index `i`:

- `left[i]`: sum of `nums[0..i-1]`
- `right[i]`: sum of `nums[i+1..n-1]`

Then for each index `i` with `nums[i] == 0`:

- If `left[i] == right[i]`, add 2 to the answer.
- Else if `|left[i] - right[i]| == 1`, add 1.
- Else, add 0.

This is linear time and memory.

## Approach 2 — single pass with running sum (equivalent)

Track the total sum `S = sum(nums)`. Sweep left to right keeping a running prefix sum `pref` including the current index. At a zero index `i`, the sums on both sides are:

- Left = `pref` (since `nums[i] == 0`)
- Right = `S - pref`

So the conditions above become:

- If `2 * pref == S` → add 2
- Else if `|S - 2 * pref| == 1` → add 1

This variant uses O(1) extra space.

## Implementation (matches `Solution.java`)

```java
class Solution {
	public int countValidSelections(int[] nums) {
		int n = nums.length, count = 0;
		int[] left = new int[n];
		int[] right = new int[n];

		// Build prefix (left) and suffix (right) sums around each index
		for (int i = 1; i < n; i++) {
			left[i] = left[i - 1] + nums[i - 1];
			right[n - i - 1] = right[n - i] + nums[n - i];
		}

		// Evaluate only zero positions
		for (int i = 0; i < n; i++) {
			if (nums[i] != 0) continue;
			int L = left[i], R = right[i];
			if (L == R) count += 2;
			else if (Math.abs(L - R) == 1) count += 1;
		}
		return count;
	}
}
```

Equivalent O(1)-space pass (shown in comments in the source) computes the same condition via `pref` and `S`.

## Why this works

Each step of the allowed process removes exactly one unit from either the left or the right side of the pivot index. To end with all zeros, the total number of units taken from left and right must match what’s available on those sides.

- If both sides have the same total, you can start on either side and alternate as needed. There are 2 distinct valid starts (choose left-first or right-first).
- If the sides differ by exactly 1, you’re forced to start on the heavier side to avoid getting stuck early. That yields exactly 1 valid start.
- If the difference is 2 or more, no start can finish consuming all units.

Thus, validity depends solely on the side sums relative to each zero index.

## Complexity

- Time: O(n)
- Space: O(n) for prefix/suffix (or O(1) with the running-sum variant)

## Example

Suppose `nums = [1, 0, 1]`.

- At `i = 1` (the only zero): left sum = 1, right sum = 1 → equal → contributes 2.
- Total answer = 2.

Another example: `nums = [2, 0, 1]`.

- At `i = 1`: left sum = 2, right sum = 1 → difference is 1 → contributes 1.
- Total answer = 1.

## Edge cases to consider

- No zeros in the array → answer is 0.
- All zeros → every index is a zero; both side sums are 0 for all indices → contributes 2 per index.
- Large arrays with big values → handle sums in `int` safely if constraints ensure sum fits; otherwise switch to `long`.

## Takeaways

- The problem reduces to a pure balance check around zero indices.
- You can implement it with either prefix/suffix arrays or a single O(1)-space running-sum pass.
- The per-zero contribution is in {0, 1, 2} based on side-sum equality and off-by-one difference.

---
