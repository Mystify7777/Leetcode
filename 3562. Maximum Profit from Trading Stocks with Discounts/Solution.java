//https://leetcode.com/problems/maximum-profit-from-trading-stocks-with-discounts/
// 3562. Maximum Profit from Trading Stocks with Discounts
class Solution {

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        // Build tree
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();
        for (int[] edge : hierarchy) {
            tree[edge[0] - 1].add(edge[1] - 1);
        }

        int[][][] dp = new int[n][2][budget + 1];  // [node][parentBought][budget]
        dfs(0, present, future, tree, dp, budget);

        // Answer is the max profit in dp[0][0][b] for any b <= budget
        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, dp[0][0][b]);
        }
        return ans;
    }

    private void dfs(int u, int[] present, int[] future, List<Integer>[] tree,
                            int[][][] dp, int budget) {
        // Base case: no children, init to 0
        for (int b = 0; b <= budget; b++) dp[u][0][b] = dp[u][1][b] = 0;

        // For each child, process recursively
        List<int[][]> childDPs = new ArrayList<>();
        for (int v : tree[u]) {
            dfs(v, present, future, tree, dp, budget);
            childDPs.add(new int[][]{dp[v][0], dp[v][1]});
        }

        // For parentNotBought and parentBought
        for (int parentBought = 0; parentBought <= 1; parentBought++) {
            int price = parentBought == 1 ? present[u] / 2 : present[u];
            int profit = future[u] - price;

            // Create DP array to fill for this u
            int[] curr = new int[budget + 1];

            // Option 1: don't buy u
            int[] base = new int[budget + 1];
            base[0] = 0;
            for (int[][] child : childDPs) {
                int[] next = new int[budget + 1];
                for (int b1 = 0; b1 <= budget; b1++) {
                    for (int b2 = 0; b1 + b2 <= budget; b2++) {
                        next[b1 + b2] = Math.max(next[b1 + b2], base[b1] + child[0][b2]);
                    }
                }
                base = next;
            }

            for (int b = 0; b <= budget; b++) {
                curr[b] = Math.max(curr[b], base[b]); // not buying u
            }

            // Option 2: buy u
            if (price <= budget) {
                int[] baseBuy = new int[budget + 1];
                baseBuy[0] = 0;
                for (int[][] child : childDPs) {
                    int[] next = new int[budget + 1];
                    for (int b1 = 0; b1 <= budget; b1++) {
                        for (int b2 = 0; b1 + b2 <= budget; b2++) {
                            next[b1 + b2] = Math.max(next[b1 + b2], baseBuy[b1] + child[1][b2]);
                        }
                    }
                    baseBuy = next;
                }

                for (int b = price; b <= budget; b++) {
                    curr[b] = Math.max(curr[b], baseBuy[b - price] + profit);
                }
            }

            dp[u][parentBought] = curr;
        }
    }
}

//alternate approach
/**
class Solution {
    private static final int NEG_INF = -1_000_000_000;
    private int n, B;
    private int[] present, future;
    private java.util.List<Integer>[] children;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        this.n = n;
        this.present = present;
        this.future = future;
        this.B = budget;
        // Build tree (0-based indices)
        children = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) children[i] = new java.util.ArrayList<>();
        for (int[] e : hierarchy) {
            int u = e[0] - 1;
            int v = e[1] - 1;
            children[u].add(v);
        }
        int[][] rootDP = dfs(0); // dp[0] is when parent not selected (no discount), which applies to root
        int ans = 0;
        for (int b = 0; b <= B; b++) {
            ans = Math.max(ans, rootDP[0][b]);
        }
        return ans;
    }

    // Returns dp arrays for node u:
    // dp[0][b]: max profit using exactly b budget in subtree of u, given u's parent is NOT selected (so u has no discount)
    // dp[1][b]: same, given u's parent IS selected (so u can be bought at discounted price)
    private int[][] dfs(int u) {
        // Merge children contributions for two scenarios:
        // curNot: u not selected -> children see parentSelected=0
        // curBuy: u selected     -> children see parentSelected=1
        int[] curNot = new int[B + 1];
        int[] curBuy = new int[B + 1];
        java.util.Arrays.fill(curNot, NEG_INF);
        java.util.Arrays.fill(curBuy, NEG_INF);
        curNot[0] = 0;
        curBuy[0] = 0;

        for (int v : children[u]) {
            int[][] child = dfs(v);
            // Merge into curNot using child[0]
            int[] nextNot = new int[B + 1];
            java.util.Arrays.fill(nextNot, NEG_INF);
            for (int b = 0; b <= B; b++) {
                if (curNot[b] == NEG_INF) continue;
                for (int x = 0; x <= B - b; x++) {
                    if (child[0][x] == NEG_INF) continue;
                    nextNot[b + x] = Math.max(nextNot[b + x], curNot[b] + child[0][x]);
                }
            }
            curNot = nextNot;

            // Merge into curBuy using child[1]
            int[] nextBuy = new int[B + 1];
            java.util.Arrays.fill(nextBuy, NEG_INF);
            for (int b = 0; b <= B; b++) {
                if (curBuy[b] == NEG_INF) continue;
                for (int x = 0; x <= B - b; x++) {
                    if (child[1][x] == NEG_INF) continue;
                    nextBuy[b + x] = Math.max(nextBuy[b + x], curBuy[b] + child[1][x]);
                }
            }
            curBuy = nextBuy;
        }

        int cu0 = present[u];
        int cu1 = present[u] / 2; // floor
        int gu0 = future[u] - cu0;
        int gu1 = future[u] - cu1;

        int[] dp0 = new int[B + 1];
        int[] dp1 = new int[B + 1];

        // If u is NOT bought, both dp0 and dp1 start with children when parentSelected=0
        for (int b = 0; b <= B; b++) {
            dp0[b] = curNot[b];
            dp1[b] = curNot[b];
        }

        // If u IS bought, children use curBuy (parentSelected=1 for children)
        for (int b = cu0; b <= B; b++) {
            if (curBuy[b - cu0] != NEG_INF) {
                dp0[b] = Math.max(dp0[b], curBuy[b - cu0] + gu0);
            }
        }
        for (int b = cu1; b <= B; b++) {
            if (curBuy[b - cu1] != NEG_INF) {
                dp1[b] = Math.max(dp1[b], curBuy[b - cu1] + gu1);
            }
        }

        return new int[][]{dp0, dp1};
    }
}
 */