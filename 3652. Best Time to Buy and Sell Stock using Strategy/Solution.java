// 3652. Best Time to Buy and Sell Stock using Strategy
//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-using-strategy/
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

//better appraoch?
/**
class Solution {
    public long maxProfit(int[] prices, int[] strategy, int k) {
        long sum = 0;
        int kk = k / 2, n = prices.length;
        long current = 0, max = 0;
        for(int i = 0; i < kk; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val;
        }
        for(int i = kk; i < k; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val - prices[i - kk];
        }
        max = Math.max(max, current);
        for(int i = k; i < n; i++) {
            int val = prices[i] * strategy[i];
            sum += val;
            current += prices[i] - val - prices[i - kk] + prices[i - k] * strategy[i - k];
            max = Math.max(max, current);
        }
        return sum + max;
    }
}
 */