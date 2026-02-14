# How Why Explanation - 799. Champagne Tower

## Problem

Pour `poured` cups of champagne into a tower of glasses. Each glass holds 1 cup; any overflow splits equally to the two glasses below. Return how full the glass at `queryRow, queryGlass` is (capped at 1).

## Intuition

This is a flow DP. At each row, overflow is `(amount - 1) / 2` (but not negative) and is poured into the two children. We can simulate row by row until the target row. Space can be optimized to one rolling array because each row depends only on the previous.

## Brute Force (Not Used)

- Model each cup with BFS/recursion and memo per position. Same complexity as row DP but more overhead.

## Approach (Row DP / Rolling Array)

1. Initialize the top cup with `poured`.
2. For each row up to `queryRow - 1`, compute overflow for each cup and add it to the two cups below. Set current cup to the overflow (for next row), zeroing non-overflow cases.
3. After processing rows, the value at `queryGlass` is the amount in that cup; cap at 1.

Why it works: Flow only moves downward; every cup’s content is determined solely by its parents’ overflows. Rolling one-dimensional DP suffices because row `i+1` depends only on row `i`.

## Complexity

- Time: $O(queryRow^2)$ (triangular number of cups up to that row).
- Space: $O(queryRow)$ with a rolling array (`col+2` in Solution2); $O(row)$ list per row in the first variant.

## Optimality

The quadratic time matches the number of reachable cups. Rolling array achieves optimal linear space for this dependency pattern.

## Edge Cases

- `poured = 0` → answer 0.
- Early rows may have no overflow; subsequent rows stay 0.
- Large `poured`: cap the returned value at 1.

## Comparison Table

| Aspect | List per row (Solution) | Rolling array (Solution2) |
| --- | --- | --- |
| Space | $O(row)$ list | $O(col)$ array |
| Update order | Left-to-right using previous row | Right-to-left in-place to avoid contamination |
| Code style | Clear two-parent addition | In-place overwrite with reverse loop |

## Key Snippet (Rolling Array)

```java
double[] dp = new double[col + 2];
dp[0] = poured;
for (int i = 0; i < row; i++) {
	for (int j = Math.min(i, col); j >= 0; j--) {
		if (dp[j] > 1) {
			double spill = (dp[j] - 1) / 2.0;
			dp[j] = spill;
			dp[j + 1] += spill;
		} else dp[j] = 0;
	}
}
return Math.min(1, dp[col]);
```

## Example Walkthrough

`poured = 4`, target `(row=2, col=1)`

- Row0: [4]
- Row1: each child gets (4-1)/2 = 1.5 → [1.5, 1.5]
- Row2: left child gets (1.5-1)/2 = 0.25, right child gets 0.25; middle gets 0.25+0.25 = 0.5. Answer = 0.5.

## Insights

- Reverse iteration on the rolling array prevents double-adding spill to already-updated positions.
- Capping at 1 is only for the return; internal values can exceed 1 to carry overflow.

## References

- Solution implementation in [799. Champagne Tower/Solution.java](799.%20Champagne%20Tower/Solution.java)
