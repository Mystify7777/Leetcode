## How & Why: LeetCode 121 - Best Time to Buy and Sell Stock

-----

This solution finds the maximum profit in a single pass by tracking the lowest buy price seen so far and the maximum profit found so far.

-----

## Problem Restatement

You're given an array `prices` where `prices[i]` is the price of a stock on the `i`-th day. Your goal is to find the maximum profit you can achieve.

There are two main rules:

1.  You can only complete **one transaction** (i.e., one buy and one sell).
2.  You **must buy** the stock on a given day before you can sell it on a future day.

If you can't make any profit, the answer should be `0`.

-----

## How to Solve

The problem can be solved in a single, efficient pass through the price list. The core idea is to simulate the process day by day while keeping track of two key pieces of information:

1.  **The lowest price seen so far (`buy`)**: This is the best possible price you could have bought the stock for up to the current day.
2.  **The maximum profit found so far (`profit`)**: This is the best deal you could have made.

As you iterate through the days, you update these two variables. For each day, you ask:

  - Is today's price a new all-time low? If so, it's a better day to buy.
  - If not, would selling today (at the current price) give me a better profit than the best profit I've found so far?

### Implementation

```java
class Solution {
    public int maxProfit(int[] prices) {
        // Track the lowest price seen so far. Initialize with the first day's price.
        int buy = prices[0];
        // Track the maximum profit found. Initialize to 0.
        int profit = 0;

        // Iterate through the prices starting from the second day.
        for (int i = 1; i < prices.length; i++) {
            // Is today's price a new low? If so, update our buy price.
            if (prices[i] < buy) {
                buy = prices[i];
            } 
            // Otherwise, check if selling today gives a better profit.
            else if (prices[i] - buy > profit) {
                profit = prices[i] - buy;
            }
        }
        return profit;
    }
}
```

-----

## Why This Works

This one-pass approach is both simple and powerful. It works because it correctly mimics the logic of making a trade. At any given day `i`, the best possible profit you could make *by selling on that day* is `prices[i] - (the lowest price from day 0 to i-1)`.

  - The `buy` variable efficiently keeps track of this "lowest price so far".
  - The `profit` variable keeps a running record of the best transaction found across all days.

By only looking at past minimums, the logic never "cheats" by selling before it buys, and a single pass is enough to find the global maximum profit.

-----

## Complexity Analysis

  - **Time Complexity**: $O(n)$, where `n` is the number of days. We iterate through the `prices` array exactly once.
  - **Space Complexity**: $O(1)$. We only use two variables (`buy` and `profit`), so the memory usage is constant regardless of the input size.

-----

## Example Walkthrough

**Input:**

```
prices = [7, 1, 5, 3, 6, 4]
```

**Process:**

  - **Initial**: `buy = 7`, `profit = 0`.
  - **Day 1 (price=1)**: `1 < 7`. It's a new low. Update `buy = 1`.
  - **Day 2 (price=5)**: Not a new low. Check profit: `5 - 1 = 4`. `4 > 0`, so update `profit = 4`.
  - **Day 3 (price=3)**: Not a new low. Check profit: `3 - 1 = 2`. This is not greater than the current `profit` of 4.
  - **Day 4 (price=6)**: Not a new low. Check profit: `6 - 1 = 5`. `5 > 4`, so update `profit = 5`.
  - **Day 5 (price=4)**: Not a new low. Check profit: `4 - 1 = 3`. This is not greater than the current `profit` of 5.
  - The loop finishes.

**Output:**
Return the final `profit`, which is **5**.

-----

## Alternate Approaches

1.  **Brute Force**:
      - **How**: Use two nested loops to check every possible pair of buy and sell days. The outer loop picks the buy day, and the inner loop iterates through the subsequent days to find a sell day.
      - **Complexity**: $O(n^2)$ time, $O(1)$ space. This is too slow for large inputs and would likely result in a "Time Limit Exceeded" error.

### Optimal Choice

The **one-pass approach** is the optimal solution. It solves the problem with the best possible time and space complexity and is a classic example of a clean, efficient algorithm.

-----

## Key Insight

The crucial realization is that you don't need to know the future to find the best profit. You only need to know two things as you go: "What's the cheapest I could have bought the stock for up to this point?" and "What's the best profit I could have locked in so far?". By tracking just these two values, you can find the overall maximum profit in a single pass.