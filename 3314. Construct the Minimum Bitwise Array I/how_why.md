
# How & Why: LeetCode 3314 - Construct the Minimum Bitwise Array I

## Problem

For each `nums[i]`, find the smallest integer `x` such that `x | (x + 1) == nums[i]`. If no such `x` exists, output `-1` for that index. Return the array of answers.

## Intuition

- Property: If `x | (x+1) = n`, then `n` must be odd (LSB set), because `x` and `x+1` differ at the least significant 0 of `x` which becomes 1 in `x+1`.
- Given odd `n`, the valid `x` is obtained by clearing the highest zero-to-one carry bit above the least-significant block of 1s in `n`—effectively: turn the lowest `01` pattern (from LSB upward) into `00` while keeping lower bits as in `n`.

## Brute Force Approach

- **Idea:** For each `n`, brute-force decrement from `n` downward, test `x | (x+1) == n`.
- **Complexity:** Potentially $O(n)$ per element value; infeasible.

## My Approach (Bit Manipulation) — from Solution.java

- **Idea:**
	- If `n` is even, no solution → `-1`.
	- For odd `n`, locate the lowest `0` bit that has `1`s below it (i.e., the bit flipped when adding 1). This bit mask is `((n + 1) & ~n) >> 1` (carry source shifted down one). Clear it in `n` to get the minimal `x`.
	- Formula: `x = n & ~(((n + 1) & ~n) >> 1)`
- **Complexity:** Time $O(1)$ per element; Space $O(1)$.
- **Core snippet:**

```java
if ((n & 1) == 0) x = -1; // even n impossible
else x = n & ~(((n + 1) & ~n) >> 1);
```

## Most Optimal Approach

- The bit-mask formula is constant time and optimal. Any alternative would also be O(1) per element.

## Edge Cases

- Even `n` → return `-1`.
- `n = 1` → `x = 1 & ~(2 & ~1 >> 1) = 1` (since 1|2 = 3? Actually 1|2=3, not 1; so formula yields 1|2=3≠1, but for n=1 the only odd with no higher bit set, the carry mask is 1, x becomes 0; 0|1=1 valid) → output 0.
- Large values: use `int` per problem; no overflow in the bit ops.

## Comparison Table

| Approach | Idea | Time | Space | Notes |
| --- | --- | --- | --- | --- |
| Brute force search | Decrement and test | O(V) | O(1) | Too slow |
| Bit mask formula (used) | Clear the carry source bit | O(1) | O(1) | Minimal and fast |

## Example Walkthrough

`n = 11 (1011b)`

- `n` odd. `n+1 = 1100b`, `~n = ...0100b`, `(n+1)&~n = 0100b`, shift >>1 → `0010b`.
- `x = 1011 & ~0010 = 1001 (9)`. Check: `9 | 10 = 1011 (11)` valid; minimal.

## Insights

- `((n+1) & ~n)` isolates the lowest 0-bit that turns to 1 when incrementing; clearing one bit below it yields the minimal `x` that still ORs with `x+1` to recover `n`.

## References to Similar Problems

- 3200-series bit tricks; general bit isolation: `n & -n`, `~n & (n+1)` patterns
