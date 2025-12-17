# 3573. Best Time to Buy and Sell Stock V — how/why

## Recap

You are given an array `prices` and an integer `k`. In one transaction you can either:

- Go long: buy one share, later sell it once for profit `(sell − buy)`; or
- Go short: sell one share first, later buy it back once for profit `(sell − buy)` as well, since we model profit consistently.

At most `k` transactions are allowed in total, and you cannot overlap actions (you must close a position before opening another). Compute the maximum total profit.

This problem generalizes classic “Best Time to Buy and Sell Stock” with up to `k` transactions and adds symmetric short capability; the provided solutions treat long/short symmetrically.

## Intuition

For stock problems with a transaction cap `k`, dynamic programming over:

- Time index `i`
- Remaining transactions `t`
- Position state

is standard. Here, the position state can be:

- Neutral: no open position
- Holding-long: you are in a long position
- Holding-short: you are in a short position

From Neutral, you may either stay Neutral, open a long (pay price), or open a short (receive price in the profit accounting). From a holding state, you may close the position and consume one transaction, or keep holding. This yields transitions that consider both long and short sides with the same `k` budget.

## Approach

Two DP formulations are shown in `Solution.java`.

1) Bottom-up DP with 3 states per day and `k` budget (reverse time sweep):

    - State `next[t][0]`: best profit at next day with `t` remaining, Neutral.
    - State `next[t][1]`: best profit at next day with `t` remaining, Holding-long.
    - State `next[t][2]`: best profit at next day with `t` remaining, Holding-short.

    For day `i`, remaining `t`, and state `decider ∈ {0,1,2}`:

    - `dontTake = next[t][decider]` (do nothing, move to next day same state).
    - `take` depends on action:
    - If `t > 0` and `decider == 1` (Holding-long), we can close long: `take = prices[i] + next[t-1][0]` (realize `+prices[i]`).
    - If `t > 0` and `decider == 2` (Holding-short), we can close short: `take = -prices[i] + next[t-1][0]` (realize `-prices[i]`).
        - If `decider == 0` (Neutral), we can open long or open short without consuming a transaction yet (closing consumes it): `take = max(prices[i] + next[t][2], -prices[i] + next[t][1])` — switch to the opposite holding state.

    Then: `curr[t][decider] = max(take, dontTake)`. Swap `curr` and `next` moving backward in time. Answer is `next[k][0]` (day −1, neutral, `k` left).

2) Optimized O(k·n) DP over days with rolling arrays and precomputed best entry values:

- Let `dp_prev[i]` = best profit up to day `i` with up to `t-1` transactions completed.
- Let `dp_cur[i]` = best profit up to day `i` with up to `t` transactions.
- Maintain two running bests for opening positions at day `i`:
  - `best_buy = max over j<i of (dp_prev[j] - prices[j])` for entering a long before closing at `i`.
  - `best_short = max over j<i of (dp_prev[j] + prices[j])` for entering a short before closing at `i`.
- Transition at day `i`:
  - Keep previous: `dp_cur[i] = dp_cur[i-1]`
  - Close long: `dp_cur[i] = max(dp_cur[i], best_buy + prices[i])`
  - Close short: `dp_cur[i] = max(dp_cur[i], best_short - prices[i])`
- Update running bests using `dp_prev[i-1]`.
- After finishing day `i`, roll arrays and continue for `t = 1..k`.

Both formulations produce the same maximum and handle long and short symmetrically.

## Code (Java, state-DP version)

```java
class Solution {

    public static long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        long[][] curr = new long[k + 1][3];
        long[][] next = new long[k + 1][3];

        for (int T = 0; T <= k; T++) {
            next[T][0] = 0;                 // Neutral
            next[T][1] = Integer.MIN_VALUE; // Holding-long
            next[T][2] = Integer.MIN_VALUE; // Holding-short
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int T = 0; T <= k; T++) {
                for (int s = 0; s < 3; s++) {
                    long take = Integer.MIN_VALUE;
                    long dontTake = next[T][s];

                    if (T > 0) {
                        if (s == 1) {
                            take = prices[i] + next[T - 1][0];   // close long
                        } else if (s == 2) {
                            take = -prices[i] + next[T - 1][0];  // close short
                        } else { // s == 0, open long or short
                            take = Math.max(
                                prices[i] + next[T][2],
                                -prices[i] + next[T][1]
                            );
                        }
                    }

                    curr[T][s] = Math.max(take, dontTake);
                }
            }
            long[][] temp = next;
            curr = temp;
            next = curr;
        }

        return next[k][0];
    }
}
```

## Worked Example

- Prices: `[3, 2, 4]`, `k = 1`.
- Using the O(k·n) formulation (long/short both allowed):
  - Initialize `dp_prev = [0, 0, 0]` for `t = 0`.
  - For `t = 1`:

    - Day 0:

      - `best_buy = -3`, `best_short = 3`, `dp_cur[0] = 0`.

    - Day 1 (price 2):
      - `a = dp_cur[0] = 0` (no close), `b = best_buy + 2 = -1` (close long), `c = best_short - 2 = 1` (close short) → `dp_cur[1] = 1`.
      - Update `best_buy = max(-3, dp_prev[0] - 2) = -2`, `best_short = max(3, dp_prev[0] + 2) = 3`.
    - Day 2 (price 4):
      - `a = dp_cur[1] = 1`, `b = best_buy + 4 = 2` (close long from day 1), `c = best_short - 4 = -1` → `dp_cur[2] = 2`.
  - Answer: `2` (best is buy at 2, sell at 4).

## Correctness

- **State coverage**: The three-state model (Neutral, Holding-long, Holding-short) captures all mutually exclusive situations at any day. Exactly one is true.
- **Transaction counting**: Only closing a position consumes one transaction, matching the constraint of up to `k` round trips.
- **Symmetry**: Long and short are handled symmetrically: closing long adds `+price`, closing short adds `-price` to the profit accumulator.
- **Optimal substructure**: At day `i` the choice `max(take, dontTake)` relies solely on optimal values at day `i+1` (or at previous days in the optimized version), satisfying DP requirements.
- **Initialization**: Neutral is 0 profit; holding states start at negative infinity to prevent illegal “already holding” gains at the end of the array.

## Complexity

- State-DP version: `O(n · k · 3)` time and `O(k · 3)` space with two layers.
- Optimized version: `O(n · k)` time and `O(n)` space per layer (rolled to `O(n)`), with small constants.

## Edge Cases

- `k == 0` or `n < 2`: no transactions possible → result `0`.
- All prices equal: best profit `0`.
- Monotone increasing: optimal is repeated long entries and exits subject to `k`.
- Monotone decreasing: optimal is repeated short entries and exits subject to `k`.
- Very large `k`: effectively unlimited transactions; optimized DP still caps with `k` layers.

## Takeaways

- **Unified long/short modeling**: Treat both sides with symmetric transitions; only closing consumes a transaction.
- **Layered DP over transactions**: Classic technique for `k`-bounded trading problems.
- **Rolling arrays / running bests**: Convert `O(n·k·state)` to `O(n·k)` by maintaining best entry candidates.
- **Careful initialization**: Use negative infinity for impossible holding states to avoid accidental gains.

## Alternative (O(k·n) with running bests)

```java
class Solution {
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        if (n < 2 || k == 0) return 0;

        long[] dp_prev = new long[n];
        long[] dp_cur = new long[n];

        for (int t = 1; t <= k; t++) {
            long best_buy = -prices[0];
            long best_short = prices[0];
            dp_cur[0] = 0;

            for (int i = 1; i < n; i++) {
                long a = dp_cur[i - 1];                 // no close today
                long b = best_buy + prices[i];           // close long today
                long c = best_short - prices[i];         // close short today
                dp_cur[i] = Math.max(Math.max(a, b), c);

                best_buy = Math.max(best_buy, dp_prev[i - 1] - prices[i]);
                best_short = Math.max(best_short, dp_prev[i - 1] + prices[i]);
            }

            long[] temp = dp_prev;
            dp_prev = dp_cur;
            dp_cur = temp;
        }

        return dp_prev[n - 1];
    }
}
```

**Trade-off**: The running-bests method is simpler and faster in practice with `O(k·n)` time, while the explicit 3-state sweep is more explicit about states and helpful for reasoning/debugging. Both yield the same optimal profit.
