# 3652. Best Time to Buy and Sell Stock using Strategy — how/why

## Recap

Given arrays `prices` (stock prices) and `strat` (strategy: +1 for buy, -1 for sell), and an integer `k`, you can modify at most `k` consecutive positions from the original strategy to a "hedge" strategy (first half buy, second half sell). Find the maximum total profit.

- Original profit at position `i`: `strat[i] * prices[i]` (buy = `-prices[i]`, sell = `+prices[i]`).
- Hedge profit for a window of size `k`: first `k/2` positions buy (profit = `-prices[i]`), next `k/2` positions sell (profit = `+prices[i]`).
- Compute the maximum profit by choosing the best window to hedge (or none).

## Intuition

The base profit is the sum of `strat[i] * prices[i]` for all positions. We can optionally replace a consecutive window of `k` positions with a hedge strategy to potentially increase profit.

For each window `[i, i+k-1]`:

- Original window profit: `sum(strat[j] * prices[j])` for `j ∈ [i, i+k-1]`.
- Hedge window profit: `-sum(prices[j])` for `j ∈ [i, i+k/2-1]` + `sum(prices[j])` for `j ∈ [i+k/2, i+k-1]`.
- Change: `hedge_profit - original_profit`.

We want to find the window with maximum positive change (or 0 if all changes are negative).

**Sliding window optimization**: Instead of recalculating sums for each window, maintain running sums and slide the window, updating incrementally.

## Approach

**Sliding Window with Change Maximization**:

1. Compute `sp[i] = strat[i] * prices[i]` (original profit at each position).
2. Calculate `base` = total original profit (sum of all `sp[i]`).
3. Handle edge case: if `n == k` (entire array is the window):
   - Original window profit: `base`.
   - Hedge profit: buy first half, sell second half.
   - Change: `hedge - base`; return `base + max(0, change)`.
4. Initialize window at position 0:
   - `winOrig = sum(sp[i])` for `i ∈ [0, k-1]`.
   - `winMod = sum(prices[i])` for `i ∈ [k/2, k-1]` (only the sell part, buy part is negative).
   - `maxCh = winMod - winOrig`.
5. Slide window from position 1 to `n-k`:
   - Update `winOrig`: remove `sp[i-1]`, add `sp[i+k-1]`.
   - Update `winMod`: remove `prices[i-1+k/2]`, add `prices[i+k-1]`.
   - Update `maxCh = max(maxCh, winMod - winOrig)`.
6. Return `base + max(0, maxCh)`.

**Key insight**: `winMod` only tracks the sell portion (second half) because the buy portion (first half) contributes `-prices[i]`, and we're computing the net change. The formula simplifies the calculation.

## Code (Java)

```java
class Solution {
    public long maxProfit(int[] prices, int[] strat, int k) {
        int n = prices.length;
        int h = k / 2;
        long[] sp = new long[n];
        long base = 0;
        for (int i = 0; i < n; i++) {
            sp[i] = (long)strat[i] * prices[i];
            base += sp[i];
        }

        if (n == k) {
            long winOrig = base;
            long winMod = 0;
            for (int i = h; i < n; i++) winMod += prices[i];
            long change = winMod - winOrig;
            return base + Math.max(0, change);
        }

        long winOrig = 0;
        for (int i = 0; i < k; i++) winOrig += sp[i];

        long winMod = 0;
        for (int i = h; i < k; i++) winMod += prices[i];

        long maxCh = winMod - winOrig;

        for (int i = 1; i <= n - k; i++) {
            winOrig += sp[i + k - 1] - sp[i - 1];
            winMod += prices[i + k - 1] - prices[i - 1 + h];
            maxCh = Math.max(maxCh, winMod - winOrig);
        }

        return base + Math.max(0, maxCh);
    }
}
```

## Correctness

- **Base profit computation**: `sp[i] = strat[i] * prices[i]` correctly captures buy (negative) and sell (positive) profits.

- **Window initialization**: First window `[0, k-1]` correctly computes original and hedge profits.

- **Sliding window update**: When moving from window `[i-1, i+k-2]` to `[i, i+k-1]`:
  - Remove left element's contribution, add right element's contribution.
  - Both `winOrig` and `winMod` are updated consistently.

- **Edge case (`n == k`)**: Entire array is hedged; no sliding needed.

- **Maximum change**: Track the best improvement across all windows; if all changes are negative, use 0 (don't hedge).

- **Profit formula**: `winMod` represents the sell portion of the hedge. The full hedge profit calculation is implicitly handled by the difference `winMod - winOrig`.

## Complexity

- **Time**: O(n) for computing base profit and sliding the window once through the array.
- **Space**: O(n) for `sp` array storing original profits.

## Edge Cases

- `k == n`: Entire array is the window; handle separately without sliding.
- `k == 0`: No positions to hedge; return base profit.
- All `strat[i] = 1` (all buy): Hedge may improve if prices increase in the second half of the window.
- All `strat[i] = -1` (all sell): Hedge may improve if prices are lower in the first half.
- Optimal to not hedge: `maxCh < 0`; return base profit unchanged.
- Large price differences: Hedge can significantly increase profit if aligned with price trends.

## Takeaways

- **Sliding window optimization**: Avoid recalculating sums by maintaining running totals and updating incrementally.
- **Change-based DP**: Instead of computing absolute profits, compute the change/improvement from the base strategy.
- **Hedge strategy modeling**: Buy-then-sell pattern can be optimized by finding the best window placement.
- **Edge case handling**: Separate logic for `n == k` avoids off-by-one errors in sliding.
- **Incremental updates**: `a + new - old` pattern is efficient for sliding windows.

## Alternative (Incremental Accumulation, O(n))

```java
class Solution {
    public long maxProfit(int[] prices, int[] strategy, int k) {
        long sum = 0;
        int kk = k / 2, n = prices.length;
        long current = 0, max = 0;
        
        for (int i = 0; i < kk; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val;
        }
        
        for (int i = kk; i < k; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val - prices[i - kk];
        }
        
        max = Math.max(max, current);
        
        for (int i = k; i < n; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val - prices[i - kk] + prices[i - k] * strategy[i - k];
            max = Math.max(max, current);
        }
        
        return sum + max;
    }
}
```

**Trade-off**: This alternative processes the array in three phases (first half of window, second half of initial window, sliding remainder), computing the change incrementally as we go. It avoids precomputing the `sp` array and integrates profit accumulation with window sliding. The logic is more compact but slightly harder to follow. Use the first approach for clarity; use this for minimal memory overhead when `n` is very large.
