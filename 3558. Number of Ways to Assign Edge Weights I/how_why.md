# 3558. Number of Ways to Assign Edge Weights I

## Problem in short
You're given a tree of `n+1` nodes (so exactly `n` edges). Assign each edge a weight of either `1` or `2`. A query asks: for a given pair `(u, v)`, how many weight assignments make the path from `u` to `v` have an **odd total weight**? (The problem guarantees all queries start from node `1`, making it a "root-to-node" path problem.) Return the count modulo `10^9 + 7`.

## Key Insight (the "why")

### Parity on a path
A path from node `1` to some node `v` consists of some number of edges, say `d` (the depth of `v` from root `1`). Each edge independently contributes either `1` or `2` to the total. The total is **odd** if and only if an **odd number of edges have weight `1`** (since weight `2` contributes an even amount and doesn't change parity, only the weight-`1` edges affect whether the sum is odd or even).

### Counting odd-parity assignments
For `d` edges, there are `2^d` total assignments (each edge picks `1` or `2`). Exactly **half** of them produce an odd total — this is a standard symmetry argument: swapping any single edge's weight between `1` and `2` flips the total's parity, giving a bijection between odd-sum and even-sum assignments. So the count of odd-sum assignments for a path of depth `d` is exactly `2^d / 2 = 2^(d-1)`.

### All queries start from node `1`
Since every query is rooted at node `1`, the answer for any query node `v` is `2^(depth(v) - 1)`. Since this is maximized at the deepest node, and the problem asks for a single global answer (not per-query — it wants the number of valid assignments for the *entire* tree), what matters is the **maximum depth** over all nodes. The deepest node has the most constrained path (the most edges), and `2^(maxDepth - 1)` is the answer.

Wait — re-reading: the problem gives edges and asks for a count across *all possible queries rooted at node 1*, implying the answer should work for the single hardest/deepest query. The formula `2^(maxDepth - 1)` is exactly this: whatever the longest root-to-leaf path is, that path has `maxDepth` edges, giving `2^(maxDepth - 1)` valid total-tree assignments that make *that* path odd. This is the bottleneck, and it's the reported answer.

---

## Solution — DFS depth + recursive fast exponentiation

### Building the tree and finding max depth
```java
List<Integer> adj[] = new ArrayList[n+2];
for(int i=0;i<n;i++){
    adj[node1].add(node2);
    adj[node2].add(node1);
}
```
Standard undirected adjacency list. The tree has `edges.length + 1` nodes (a tree with `n` edges has `n+1` nodes), so adjacency list size is `n+2` for safety.

```java
private int depth(int node, int parent, List<Integer> adj[]){
    int ret = 0;
    for(int x: adj[node]){
        if(x!=parent){
            ret = Math.max(ret, 1 + depth(x, node, adj));
        }
    }
    return ret;
}
```
Standard recursive DFS from node `1`. The `parent` parameter prevents following the edge back up to the caller (since the graph is undirected). At each node, the depth returned is `1 + max(depth of each child subtree)`. Leaf nodes return `0`. This computes the **height of the subtree** rooted at `node` — when called as `depth(1, -1, adj)`, it returns the maximum depth (longest root-to-leaf path length) in the whole tree.

```java
int dep = depth(1, -1, adj);
return (int)power(2, dep-1);
```
The max depth from node `1` is `dep`; the answer is `2^(dep - 1) mod (10^9 + 7)`.

### Recursive fast exponentiation
```java
long power(long x, long n){
    if(n==0) return 1;
    if(n==1) return x;
    long half = power(x, n/2);
    half = (half*half)%mod;
    if(n%2==1){ half = (half * x)%mod; }
    return half;
}
```
Classic **divide-and-conquer** (recursive) implementation of modular exponentiation. `x^n = (x^(n/2))^2` for even `n`, with an extra multiply by `x` for odd `n`. Each recursive call halves `n`, so depth is `O(log n)`. This computes `2^(dep-1) mod (10^9+7)` efficiently even when `dep` is large.

---

## Solution2 — parent-pointer tree + iterative depth memoization + iterative modpow

This solution avoids an adjacency list and DFS entirely, exploiting a specific property of how the edges are given.

### Inferring a parent-pointer tree from edge ordering
```java
for (int[] e : edges)
    if (e[0] > e[1]) tree[e[0]] = e[1];
    else tree[e[1]] = e[0];
```
This is a clever shortcut: it assumes the edges are given such that for each edge `(u, v)`, the **smaller-numbered node is the parent** of the larger-numbered one. Under this assumption (which holds for this problem's test cases — the tree is "naturally numbered" top-down from root `1`), `tree[child] = parent` builds a parent-pointer array directly, with `tree[1] = 0` (root has no parent, sentinel value `0`).

### Iterative depth computation with memoization
```java
for (int i = 1; i <= n; i++) {
    int idx = i, depth = 0;
    while (tree[idx] != 0 && !vis[idx]) {
        vis[idx] = true;
        idx = tree[idx];
        depth++;
    }
    depth += tree[idx]; // tree[idx] now holds previously computed depth for this ancestor
    tree[i] = depth;
    if (depth > max) max = depth;
}
```
This iterates over every node `i` from `1` to `n` and walks up the parent chain, counting edges until either (a) reaching the root (`tree[idx] == 0`) or (b) reaching a node already visited (`vis[idx]`), which means `tree[idx]` has already been updated to store that node's precomputed depth from the root.

When condition (b) fires, `tree[idx]` (the previously computed depth) is added to the edge count so far — this is the memoization: once a node's depth is known, any future traversal that hits it short-circuits rather than walking all the way to the root again. `tree[i] = depth` then stores node `i`'s depth for future use as the same shortcut. Over all `n` nodes, each edge is traversed at most twice (once when first discovered, once when a later traversal hits the memoized node), so the total work is `O(n)`.

### Iterative modular exponentiation
```java
private long modPow(long a, long b) {
    long res = 1;
    while (b > 0) {
        if ((b & 1) == 1) res = (res * a) % MOD;
        a = (a * a) % MOD;
        b >>= 1;
    }
    return res;
}
```
Iterative (loop-based) version of the same fast exponentiation. Processes the bits of `b` from LSB to MSB: if the current bit is set, multiply the running result by the current power of `a`; always square `a` and shift `b` right. Equivalent to `Solution`'s recursive version but avoids function-call overhead.

---

## Comparing the two

| | `Solution` | `Solution2` |
|---|---|---|
| Tree representation | Undirected adjacency list | Parent-pointer array (assumes child > parent in numbering) |
| Depth computation | Recursive DFS from root `1` — `O(n)` | Iterative walk-up with path memoization — `O(n)` amortized |
| Assumptions | None (works for any undirected tree) | Assumes smaller-numbered node is always the parent (specific to this problem's input format) |
| Modular exponentiation | Recursive divide-and-conquer | Iterative bit-by-bit |
| Robustness | Fully general | Fragile — would break on arbitrary edge orderings |

Both run in `O(n)` for the tree traversal and `O(log depth)` for the exponentiation. `Solution` is the safer, more general approach; `Solution2` exploits the input format as a shortcut to avoid building an adjacency list altogether.

## Step-by-step example
Tree: `edges = [[1,2],[1,3],[3,4],[3,5]]`

```
    1
   / \
  2   3
     / \
    4   5
```
- DFS from `1`: max depth = `2` (paths `1→3→4` and `1→3→5` both have length 2).
- Answer: `2^(2-1) = 2^1 = 2`.

This means there are exactly **2** assignments of edge weights `{1,2}` to all 4 edges that make the longest path (`1→3→4` or `1→3→5`) have an odd total.

## Complexity
- **Time:** `O(n)` for tree traversal, `O(log n)` for fast exponentiation → `O(n)` overall.
- **Space:** `O(n)` for the adjacency list or parent-pointer array.