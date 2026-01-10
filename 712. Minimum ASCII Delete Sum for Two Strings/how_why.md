# Why & How

## Intuition

We need the minimum total ASCII cost to delete characters so the two strings become identical. This is equivalent to **keeping** the longest common subsequence with the **maximum retained ASCII sum**; everything not kept must be deleted. Dynamic programming over prefixes lets us pick the cheapest path.

## Approach (1D DP on cost)

- Let `dp[j]` be the minimum delete cost to make `s1[0..i)` and `s2[0..j)` equal while iterating `i` forward.
- Initialize first row: cost to delete prefixes of `s2` alone.
- For each `i`, update `dp[0]` (delete from `s1`), then sweep `j`:
	- If chars match, carry the diagonal previous cost (`prev`).
	- Otherwise, delete either `s1[i-1]` or `s2[j-1]`, taking the cheaper sum.
- We keep only one row, so space is O(n).

## Correctness Sketch

Induction on prefix lengths.

- Base: `dp[0][0]=0`; first row/col represent deleting all chars from one side.
- Transition: For prefixes ending at `i,j`:
	- If characters equal, optimal cost is whatever made prefixes `i-1,j-1` equal (`prev`).
	- If not equal, any valid edit must delete one of the two last chars. We take `min(delete s1[i-1], delete s2[j-1])`, which enumerates all possibilities. Thus `dp[i][j]` is optimal.
- By induction, the final `dp[n][m]` is the global optimum.

## Complexity

- Time: O(m·n)
- Space: O(n) for the rolling row.

## Example

```
s1 = "sea"
s2 = "eat"

Initial dp (cost to delete from s2): [0, 'e'=101, 'ea'=202, 'eat'=318]
Iter i=1 ('s'): dp -> [115, 216, 317, 433]
Iter i=2 ('e'): dp -> [216, 115 (match), 216, 332]
Iter i=3 ('a'): dp -> [313, 214, 116 (match), 232]
Answer = 232 (delete 's' 115 and 't' 116, or 's' 115 and 't' 116)
```

## Alternative (2D keeping max retained sum)

`Solution2` computes the maximum ASCII sum of a common subsequence, then subtracts twice that from total ASCII of both strings. This uses full O(m·n) space and is symmetric but less space-efficient than the 1D version.

## When to use which

- Prefer 1D DP for lower memory on long strings.
- Use 2D version if you need to reconstruct the kept subsequence (not implemented here).
