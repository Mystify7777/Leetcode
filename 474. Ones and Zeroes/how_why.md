# 474. Ones and Zeroes

## Recap

Given a list of binary strings `strs` and two integers `m` and `n`, pick the maximum number of strings such that the total count of `0`s used is at most `m` and the total count of `1`s used is at most `n`.

## Intuition

This is a classic 0/1 knapsack with two capacities: number of zeros and number of ones. Each string is an item with a “weight” of `(zeros, ones)` and a “value” of `1` (picking that string counts as one). We want the maximum number of items subject to both capacity limits. As with knapsack, we process items once and update the DP table backwards to avoid reusing the same item multiple times.

## Approach (2D 0/1 Knapsack)

- Let `dp[i][j]` be the maximum number of strings we can pick using at most `i` zeros and `j` ones.
- For each string, count `(z, o)` = number of zeros and ones.
- Update `dp` in reverse order for `i` from `m` down to `z` and `j` from `n` down to `o`:
  - `dp[i][j] = max(dp[i][j], dp[i - z][j - o] + 1)`.
- The reverse iteration ensures each string is used at most once (0/1 choice).

## Code (Java)

```java
class Solution {
    public int findMaxForm(String[] S, int M, int N) {
        int[][] dp = new int[M+1][N+1];
        for (String str : S) {
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray())
                if (c == '0') zeros++;
                else ones++;
            for (int i = M; i >= zeros; i--)
                for (int j = N; j >= ones; j--)
                    dp[i][j] = Math.max(dp[i][j], dp[i-zeros][j-ones] + 1);
        }
        return dp[M][N];
    }
}
```

## Correctness

- Base: `dp[0..m][0..n]` initialized to 0 is valid—choosing nothing yields value 0.
- Transition: When considering a string with cost `(z, o)`, we either skip it (keep `dp[i][j]`) or take it (if capacities permit) to get `dp[i - z][j - o] + 1`. Taking the max preserves optimality as in 0/1 knapsack.
- Reverse iteration over `i` and `j` ensures each string contributes at most once in transitions (no double counting), which matches the 0/1 requirement.

## Complexity

- Let `L = strs.length`. Counting zeros/ones per string is linear in its length; denote average length by `k`.
- Time: `O(L * (k + m * n))` — counting plus filling the `m*n` DP for each string.
- Space: `O(m * n)` for the DP table.

## Edge Cases

- `m = 0` and/or `n = 0`: only strings composed solely of the other bit can be chosen.
- A string with `(z > m)` or `(o > n)` is automatically unpickable and contributes nothing.
- Empty `strs`: answer is `0`.

## Takeaways

- Two-dimensional capacities map cleanly to a 2D DP knapsack.
- Reverse iteration is the key trick for 0/1 constraints when updating in-place.
- Each string gives value 1, so the DP naturally maximizes the count of chosen strings under the bit budgets.

