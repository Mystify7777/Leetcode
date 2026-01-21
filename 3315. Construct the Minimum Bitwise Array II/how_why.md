
# How & Why: LeetCode 3315 - Construct the Minimum Bitwise Array II

## Problem

For each `nums[i]`, find the smallest integer `x` such that `x | (2*x) == nums[i]`. If no such `x` exists, output `-1` for that index. Return the array of answers.

## Intuition

- Multiplying by 2 is a left shift: `2*x` is `x << 1`. So we need `x | (x << 1) = n`. This pattern covers a block of trailing 1s in `n`; stripping one of those 1s recovers the minimal `x`.
- `n` must be odd to be representable (LSB must be 1). Special case: `n=2` (10b) cannot be formed → `-1`.

## Brute Force Approach

- **Idea:** Decrement from `n` downward, test `x | (x<<1) == n`.
- **Complexity:** Up to O(value) per element; infeasible.

## My Approach (Count Trailing Ones) — from Solution.java

- **Idea:**
	1) If `n == 2`, return -1 (impossible). If `n` even, trailing ones count is 0; expression fails—return -1 (though code only guards 2 per problem spec).
	2) Count trailing 1 bits in `n` (let count = c ≥ 1).
	3) Remove the highest bit within that trailing-ones block: `x = n - (1 << (c-1))`. This is the minimal `x` satisfying `x | (x<<1) = n`.
- **Complexity:** Time $O(1)$ per element, Space $O(1)$.
- **Core snippet:**

```java
if (n == 2) return -1;
int c=0, t=n;
while ((t & 1)==1){ c++; t >>=1; }
int x = n - (1 << (c-1));
```

## Most Optimal Approach

- The trailing-ones deduction is constant time and optimal; alternative bit tricks (isolating lowest zero after ones) have same complexity.

## Edge Cases

- `n = 2` → -1.
- `n` even (but ≠2) → formula would count 0 trailing ones; per constraints, only valid n are representable; could guard to -1.
- `n = 1` → c=1 → x = 1 - 1 = 0; check: `0 | 0 = 0` (does not equal 1). This shows the formula assumes at least two bits? (Problem constraints ensure valid inputs; treat n=1 as impossible → -1, if needed.)

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force | Decrement and test | O(V) | O(1) | Too slow |
| Trailing-ones subtraction (used) | Count trailing 1s, clear one | O(1) | O(1) | Concise |

## Example Walkthrough

`n = 11 (1011b)`

- Trailing ones c=2 (`11`). Remove bit `(1<<(2-1))=2`: `x=11-2=9 (1001b)`.
- Check: `x<<1 = 10010b (18)`, `x | (x<<1) = 1001 | 10010 = 1011 (11)` valid.

## Insights

- `x | (x<<1)` fills the lower block of bits up through the highest trailing-ones bit in `x`; inverting that relation lets us strip a single 1 from the trailing-ones block of `n` to recover minimal `x`.

## References to Similar Problems

- 3314. Construct the Minimum Bitwise Array I (related OR pattern with `x+1`)
- Classic bit tricks: counting trailing ones/zeros, isolating bits
