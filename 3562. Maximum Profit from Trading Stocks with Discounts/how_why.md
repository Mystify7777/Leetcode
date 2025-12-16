# 3562. Maximum Profit from Trading Stocks with Discounts — how/why

## Recap

Given `n` stocks organized in a tree hierarchy (represented by `hierarchy` edges), each stock `i` has:

- `present[i]`: current purchase price
- `future[i]`: future selling price
- A discount: if you buy a parent stock, its children can be purchased at half price (floor division)

You have a `budget` to spend. Maximize profit = sum of `(future[i] - cost[i])` for all purchased stocks, where `cost[i]` is either `present[i]` (no discount) or `present[i] / 2` (if parent was bought).

## Intuition

This is a **tree DP with budget constraint**. Key decisions at each node:

- Whether to buy the current stock (affects discount for children)
- How to allocate remaining budget among children

**State design**: For each node `u`, track two scenarios:

- `dp[u][0][b]`: max profit in subtree of `u` using budget `b`, when `u`'s parent was **not** bought (no discount on `u`)
- `dp[u][1][b]`: max profit in subtree of `u` using budget `b`, when `u`'s parent **was** bought (discount on `u`)

**Transition**: At node `u`, consider:

1. **Not buying `u`**: Children see `u` as not bought (no discount for them) → aggregate `dp[child][0][*]`
2. **Buying `u`**: Pay cost (discounted or not), gain profit, children see `u` as bought (discount for them) → aggregate `dp[child][1][*]`

For each scenario, merge children's DP arrays using knapsack-style budget allocation.

## Approach

**Tree DP with Knapsack Merging**:

1. Build adjacency list from `hierarchy` (convert to 0-based indices).
2. DFS from root (node 0):
   - For each node `u`, recursively compute `dp` for all children.
   - For `parentBought ∈ {0, 1}`:
     - Compute `price` of `u`: `present[u]` if parent not bought, `present[u] / 2` if bought.
     - Compute `profit` from buying `u`: `future[u] - price`.
     - **Option 1 (Don't buy `u`)**: Merge all `dp[child][0][*]` using knapsack.
     - **Option 2 (Buy `u`)**: Spend `price`, then merge all `dp[child][1][*]` using knapsack, add `profit`.
     - Take max of both options for each budget level.
3. Answer: `max(dp[0][0][b])` for all `b ≤ budget`.

**Knapsack merging**: For merging children, iteratively combine DP arrays:

- Start with `base[0] = 0`, rest = 0.
- For each child, create `next[b1 + b2] = max(base[b1] + child[b2])` for all valid splits.
- Update `base = next`.

## Code (Java)

```java
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
```

## Correctness

- **State coverage**: Each node considers both scenarios (parent bought or not), ensuring discount propagation is accurate.

- **Knapsack merging**: Iteratively combines children's contributions, trying all budget splits `b1 + b2` to find optimal allocation.

- **Discount handling**: When buying `u`, children see `parentBought = 1`, getting discounted prices in their transitions.

- **Base case**: Leaf nodes (no children) correctly initialize DP arrays to 0.

- **Answer extraction**: Root node with `parentBought = 0` (no parent exists) gives final answer; we take max across all budget levels ≤ `budget`.

## Complexity

- **Time**: O(n · budget² · k) where k is average number of children per node. Each node processes O(budget²) transitions for each child.
  - Worst case: O(n · budget² · n) for star graph.
  - Typical: O(n · budget²) for balanced trees.
- **Space**: O(n · budget) for DP arrays.

## Edge Cases

- Single node (root only): Check if buying root with `budget` yields positive profit.
- No budget: Can't buy anything → return 0.
- All stocks unprofitable at full price: May still profit with discounts.
- Deep tree: Discount chains can significantly reduce costs.
- Budget too small to buy even discounted stocks: Return 0.
- Budget large enough to buy entire tree: Limited by profitability, not budget.

## Takeaways

- **Tree DP with context**: Passing parent state (bought/not bought) through recursion enables correct discount calculation.
- **Knapsack merging**: Combining children's contributions is a bounded knapsack problem; iterative merging avoids exponential enumeration.
- **State dimensionality**: Tracking `[node][parentState][budget]` cleanly separates concerns.
- **Discount propagation**: Children depend on parent decisions; two DP arrays per node (one per parent state) capture this dependency.
- **Budget constraint optimization**: Similar patterns apply to other problems with hierarchical discounts or dependent costs.

## Alternative (NEG_INF Initialization for Invalid States, O(n · budget²))

```java
class Solution {
    private static final int NEG_INF = -1_000_000_000;
    private int n, B;
    private int[] present, future;
    private List<Integer>[] children;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        this.n = n;
        this.present = present;
        this.future = future;
        this.B = budget;
        children = new ArrayList[n];
        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        for (int[] e : hierarchy) {
            int u = e[0] - 1;
            int v = e[1] - 1;
            children[u].add(v);
        }
        int[][] rootDP = dfs(0);
        int ans = 0;
        for (int b = 0; b <= B; b++) {
            ans = Math.max(ans, rootDP[0][b]);
        }
        return ans;
    }

    private int[][] dfs(int u) {
        int[] curNot = new int[B + 1];
        int[] curBuy = new int[B + 1];
        Arrays.fill(curNot, NEG_INF);
        Arrays.fill(curBuy, NEG_INF);
        curNot[0] = 0;
        curBuy[0] = 0;

        for (int v : children[u]) {
            int[][] child = dfs(v);
            int[] nextNot = new int[B + 1];
            Arrays.fill(nextNot, NEG_INF);
            for (int b = 0; b <= B; b++) {
                if (curNot[b] == NEG_INF) continue;
                for (int x = 0; x <= B - b; x++) {
                    if (child[0][x] == NEG_INF) continue;
                    nextNot[b + x] = Math.max(nextNot[b + x], curNot[b] + child[0][x]);
                }
            }
            curNot = nextNot;

            int[] nextBuy = new int[B + 1];
            Arrays.fill(nextBuy, NEG_INF);
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
        int cu1 = present[u] / 2;
        int gu0 = future[u] - cu0;
        int gu1 = future[u] - cu1;

        int[] dp0 = new int[B + 1];
        int[] dp1 = new int[B + 1];

        for (int b = 0; b <= B; b++) {
            dp0[b] = curNot[b];
            dp1[b] = curNot[b];
        }

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
```

**Trade-off**: Using `NEG_INF` to mark invalid/unreachable states allows cleaner separation of "not yet computed" vs "valid zero profit." This avoids accidentally using uninitialized values when merging. However, it requires careful filtering during transitions (skip `NEG_INF` values). The first approach (initializing to 0) is simpler but assumes all states are reachable; the second approach is more robust for complex constraints. Use `NEG_INF` when state reachability is non-trivial; use direct initialization for simpler DP formulations.
