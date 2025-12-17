//copypasted
//3573. Best Time to Buy and Sell Stock V
//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-v/
class Solution {
    public static long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        long[][] curr = new long[k + 1][3];
        long[][] next = new long[k + 1][3];

        for (int K = 0; K <= k; K++) {
            next[K][0] = 0;
            next[K][1] = Integer.MIN_VALUE;
            next[K][2] = Integer.MIN_VALUE;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int K = 0; K <= k; K++) {
                for (int decider = 0; decider < 3; decider++) {
                    long take = Integer.MIN_VALUE;
                    long dontTake = next[K][decider];

                    if (K > 0) {
                        if (decider == 1) {
                            take = prices[i] + next[K - 1][0];
                        } else if (decider == 2) {
                            take = -prices[i] + next[K - 1][0];
                        } else {
                            take = Math.max(
                                prices[i] + next[K][2],
                                -prices[i] + next[K][1]
                            );
                        }
                    }

                    curr[K][decider] = Math.max(take, dontTake);
                }
            }

            long[][] temp = next;
            next = curr;
            curr = temp;
        }

        return next[k][0];
    }
}


//optimized?
/**
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
                long a = dp_cur[i - 1];
                long b = best_buy + prices[i];
                long c = best_short - prices[i];

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
 */