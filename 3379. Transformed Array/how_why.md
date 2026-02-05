# How Why Explanation - 3379. Transformed Array

## Problem

Given a circular array `nums`, build a transformed array `ans` where `ans[i] = nums[(i + nums[i]) mod n]` (with wraparound). Return `ans`.

## Intuition

Each position jumps forward or backward by `nums[i]` steps on the circle. We just compute the target index with modular arithmetic that handles negative offsets correctly, then copy the value from `nums` at that wrapped index.

## Brute Force (Not Used)

- You could simulate step-by-step moves, but since the jump length is known, direct indexing is simpler.
- Complexity would still be $O(n)$ but with extra overhead.

## Approach (Direct Modular Indexing)

1. Let `n = nums.length`.
2. For each `i` in `[0, n)` compute `idx = (i + nums[i]) % n`.
3. Adjust for negatives: `(idx + n) % n` gives the correct wrapped index in `[0, n)`.
4. Set `ans[i] = nums[idx]`.
5. Return `ans`.

Why it works: modular arithmetic on a circle maps any forward/backward shift to the correct position; adding `n` before `% n` fixes negative results.

## Complexity

- Time: $O(n)$.
- Space: $O(n)$ for the output array.

## Optimality

One pass is optimal because each output depends on its own index only. The modulo trick is minimal overhead.

## Edge Cases

- Negative `nums[i]`: need the `(x % n + n) % n` pattern.
- Zero: `ans[i] = nums[i]` (self-reference).
- Single-element array: always returns the same single value.

## Comparison Table

| Aspect | Compact modulo (Solution) | Branching version (Solution2) |
| --- | --- | --- |
| Negative handling | `(i + nums[i]) % n + n) % n` | Separate branch for `<0` | 
| Code size | Short | Longer | 
| Complexity | $O(n)$ / $O(n)$ | $O(n)$ / $O(n)$ |

## Key Snippet (Java)

```java
int n = nums.length;
int[] ans = new int[n];
for (int i = 0; i < n; i++) {
	int idx = ((i + nums[i]) % n + n) % n;
	ans[i] = nums[idx];
}
```

## Example Walkthrough

`nums = [3, -2, 1, 0]`, `n = 4`

- `i=0`: idx = (0+3)%4 = 3 -> ans[0] = nums[3] = 0
- `i=1`: idx = (1-2)=-1 -> wrapped to 3 -> ans[1] = nums[3] = 0
- `i=2`: idx = (2+1)%4 = 3 -> ans[2] = 0
- `i=3`: idx = (3+0)%4 = 3 -> ans[3] = 0
Result: `[0, 0, 0, 0]`.

## Insights

- The double-mod trick `(x % n + n) % n` is a standard, branch-free way to normalize negative indices.
- Each position is independent; no graph traversal or cycles need to be explored.

## References

- Solution implementation in [3379. Transformed Array/Solution.java](3379.%20Transformed%20Array/Solution.java)
