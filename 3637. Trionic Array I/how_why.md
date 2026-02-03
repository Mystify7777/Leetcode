# How Why Explanation - 3637. Trionic Array I

## Problem

An array is **trionic** if it has three strict phases: strictly increasing, then strictly decreasing, then strictly increasing again (each phase non-empty). Return `true` if the given array is trionic, else `false`.

## Intuition

We just need to locate the two turning points where the trend flips. Scan once to find where the initial increase stops (`p`), then where the following decrease stops (`q`), and finally verify the tail is strictly increasing. Enforcing non-empty segments ensures valid phase lengths.

## Brute Force (Not Used)

- Try every pair of split points `(p, q)` and check monotonicity of the three parts.
- Complexity: $O(n^3)$ with naive checks; even with prefix checks, $O(n^2)$, unnecessary.

## Approach (Single Pass for Phase Boundaries)

1. Starting at index 0, advance while `nums[i] < nums[i+1]` to find the end of the first increasing run at `p`. Require `p > 0` (non-empty increase).
2. From `p`, advance while `nums[i] > nums[i+1]` to find the end of the decreasing run at `q`. Require `q > p` (non-empty decrease).
3. Require `q < n-1` (needs a trailing segment). Check that from `q` onward the array is strictly increasing; if any `nums[i] >= nums[i+1]`, fail.
4. If all conditions pass, return `true`.

Why it works: Each phase is strictly monotone. The earliest points where monotonicity breaks uniquely determine the phase boundaries if they exist; linear scanning enforces strictness and non-emptiness.

## Complexity

- Time: $O(n)$ single pass.
- Space: $O(1)$.

## Optimality

We must inspect the array; one pass is asymptotically optimal. No sorting or extra data structures needed.

## Edge Cases

- Length < 3: impossible to form three non-empty phases.
- Purely monotone arrays: fail because middle phase is empty.
- Plateaus (equal adjacent values): violate strictness, so return false.

## Comparison Table

| Aspect | Forward boundary scan (Solution2) | Peak/valley detection (Solution) |
| --- | --- | --- |
| Method | Find first non-increase `p`, first non-decrease `q`, then verify tail | Track earliest peak and latest valley; confirm strict decrease between |
| Passes | 1 linear scan | 1 linear scan |
| Checks | Explicit non-empty segments, tail increase | Strict decreasing mid-segment |
| Complexity | $O(n)$ / $O(1)$ | $O(n)$ / $O(1)$ |

## Key Snippet (Java)

```java
int p = 0;
while (p < n - 1 && nums[p] < nums[p + 1]) p++;
if (p == 0) return false; // need initial rise

int q = p;
while (q < n - 1 && nums[q] > nums[q + 1]) q++;
if (q == p || q == n - 1) return false; // need middle fall and trailing part

for (int i = q; i < n - 1; i++) {
    if (nums[i] >= nums[i + 1]) return false;
}
return true;
```

## Example Walkthrough

`nums = [1, 3, 2, 1, 4, 5]`

- Increase until `3` (p=1), decrease until `1` at index 3 (q=3), then `1 < 4 < 5` increases. All segments non-empty; return true.

`nums = [2, 2, 1, 3]` fails because equal adjacent values break strictness.

## Insights

- Strictness means any equal neighbor immediately invalidates trionic shape.
- The first break from increasing and the first break from decreasing are enough to decide; no need to search all partitions.

## References

- Solution implementation in [3637. Trionic Array I/Solution.java](3637.%20Trionic%20Array%20I/Solution.java)
