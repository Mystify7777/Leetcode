# 3534. Path Existence Queries in a Graph II

## What changed from "I" to "II"
The graph is built the same way: an edge exists between indices `i` and `j` whenever `|nums[i] - nums[j]| <= maxDiff`. But "II" asks for the **minimum number of edges** (hops) on a path from `u` to `v`, not just whether one exists — and returns `-1` if no path exists. The range of `n` is also much larger here, ruling out BFS from scratch per query.

---

## The core idea shared by both solutions

### Insight 1: sort first, then think in sorted order
In "I," the array was already sorted, making connectivity trivial to read off directly. Here `nums` is **not** necessarily sorted, so the first step both solutions take is to sort the elements (keeping track of which original index each value came from). After sorting by value, the same observation from "I" kicks in: two nodes are directly connected if and only if they are within `maxDiff` of each other in value, and because the values are now sorted, all directly reachable neighbors of a node form a **contiguous range** in sorted order — specifically, the furthest right you can jump from position `i` is the last position `r` where the value difference from `nums[i]` still doesn't exceed `maxDiff`.

So after sorting, the problem becomes: starting at sorted position `a`, what is the minimum number of "jumps" to reach sorted position `b`, where from position `i` you can jump to any position in `[i+1, farthest[i]]` in one hop?

### Insight 2: binary lifting to answer "minimum jumps" in O(log n)
This is the classic **binary lifting** (also called "sparse table for ancestor/reachability") pattern. Precompute:
- `jump[0][i]` = the furthest sorted position reachable from `i` in **1 hop** (a direct edge).
- `jump[j][i]` = the furthest sorted position reachable from `i` in **2^j hops**, built from: `jump[j-1][jump[j-1][i]]` — the furthest you can reach in `2^(j-1)` hops from the furthest you can reach in another `2^(j-1)` hops starting at `i`.

Then for a query `(a, b)` (with `a < b` in sorted order), answer it greedily using the binary lifting table: try the biggest jump size first; if jumping `2^j` hops from the current position still doesn't reach or pass `b`, take that jump and add `2^j` to the step count. After all levels, one final hop (if reachable at all) finishes. The total number of hops is the minimum path length. This gives `O(log n)` per query after `O(n log n)` preprocessing.

---

## Solution — two-pointer sliding window for `jump[0]`, then standard binary lifting

### Step 1: sort and build the rank-mapping
```java
int[][] newNums = new int[n][2];
for (int i = 0; i < n; i++) newNums[i] = new int[]{nums[i], i};
Arrays.sort(newNums, (a, b) -> a[0] - b[0]);
int[] getI = new int[n];
for (int i = 0; i < n; i++) getI[newNums[i][1]] = i;
```
`newNums[i]` is the `i`-th element in sorted order, keeping its original index. `getI[v]` converts an original node index `v` to its position in the sorted array — needed at query time to translate the query's original indices into sorted positions.

### Step 2: compute `jump[0]` with a two-pointer sweep
```java
int r = 0;
for (int i = 0; i < n; i++) {
    if (r < i) r = i;
    while (r + 1 < n &&
           newNums[r + 1][0] - newNums[r][0] <= maxDiff &&
           newNums[r + 1][0] - newNums[i][0] <= maxDiff)
        r++;
    st[i][0] = r;
}
```
For each sorted position `i`, find the furthest `r` such that every consecutive pair in `[i..r]` differs by at most `maxDiff` AND the total span from `newNums[i]` to `newNums[r]` doesn't exceed `maxDiff`. The **two conditions in the while loop** both matter:
- `newNums[r+1][0] - newNums[r][0] <= maxDiff`: the *consecutive* gap is within range — if this fails, there's a break in the chain of direct edges, so you can't reach any further regardless of the total span.
- `newNums[r+1][0] - newNums[i][0] <= maxDiff`: the total span from `i` to `r+1` is still within `maxDiff` — if only this condition were checked, you might include positions where consecutive neighbors aren't connected.

Both conditions must hold because a "hop" here is a single direct edge, and a direct edge exists only between nodes within `maxDiff` of each other in value. The maximum extent of a single hop from `i` is the last `r` where you can draw a direct edge all the way to `r` — which requires every step along the chain to also be directly connected (since the graph has edges between *any* pair within `maxDiff`, not just neighbors, the total-span condition alone would suffice if values were dense, but the consecutive-gap condition guards against "gaps" inside the window that would break transitivity). The right pointer `r` never moves left across iterations, making this an `O(n)` two-pointer sweep overall.

### Step 3: binary lifting table
```java
for (int j = 1; j < 18; j++)
    for (int i = 0; i < n; i++)
        st[i][j] = st[st[i][j - 1]][j - 1];
```
Standard doubling: the farthest reachable in `2^j` hops from `i` = the farthest reachable in `2^(j-1)` hops starting from wherever `2^(j-1)` hops got you from `i`.

### Step 4: answering queries
```java
int a = getI[queries[i][0]], b = getI[queries[i][1]];
if (a > b) { int t = a; a = b; b = t; }
if (a == b) { ans[i] = 0; continue; }
int curr = a, steps = 0;
for (int j = 17; j >= 0; j--)
    if (st[curr][j] < b) { curr = st[curr][j]; steps += (1 << j); }
ans[i] = (st[curr][0] >= b) ? steps + 1 : -1;
```
Translate both nodes to sorted positions; ensure `a < b` (the path is undirected, so WLOG the smaller sorted position goes first). If they're the same node, distance is `0`.

Then apply **greedy binary lifting**: from `curr`, try the largest jump size `2^17` first. If even after `2^j` hops from `curr` you haven't yet reached or passed `b`, take that jump (accumulate `2^j` steps, update `curr` to wherever those hops landed you). Work down to `j=0`. After all 18 levels, `curr` is as close to `b` as possible without having reached it — check if one more hop (`st[curr][0]`) covers `b`: if yes, `steps + 1` is the answer; if not, no path exists (`-1`).

---

## Solution2 — same algorithm, different implementation details

### Bit-packed sorting instead of `int[][]`
```java
long[] sorted = new long[n];
for(int i = 0; i < n; i++) sorted[i] = (long)nums[i] << 32 | i;
Arrays.sort(sorted);
```
Packs `(value, originalIndex)` into a single `long` (high 32 bits = value, low 32 bits = index), so sorting `sorted[]` by `long` value automatically sorts by `nums` value — no comparator, no 2D array allocation. The rank array is built from the low 32 bits afterward: `rank[(int)sorted[i]] = i`.

### One-sided window condition for `jump[0]`
```java
while(right < n && (sorted[right] >>> 32) <= (sorted[i] >>> 32) + maxDiff) right++;
jumps[0][i] = right - 1;
```
This version only checks the **total span** from `sorted[i]`'s value: `sorted[right].value <= sorted[i].value + maxDiff`. It omits the consecutive-gap check from `Solution`. This is valid *if* the intended semantics is "from `i`, I can reach any `j` where `|value[i] - value[j]| <= maxDiff` in one hop" (a direct edge between any two nodes within `maxDiff` in value, not just consecutively-adjacent ones in sorted order). The right pointer again never moves left: `O(n)` total sweep.

### `calcJumps` — binary search instead of bit-greedy
```java
private int calcJumps(int[][] jumps, int a, int b, int right) {
    if(a == b) return 0;
    if(jumps[0][a] >= b) return 1;
    int left = 0;
    while(left < right) {
        int mid = left + right + 1 >>> 1;
        if(jumps[mid][a] < b) left = mid;
        else right = mid - 1;
    }
    return (1 << left) + calcJumps(jumps, jumps[left][a], b, left);
}
```
Instead of the top-down greedy bit loop (try `j=17` down to `j=0`), this uses **binary search** to find the largest `j` such that `jumps[j][a] < b` (i.e., `2^j` hops from `a` don't yet reach `b`), then takes that jump and **recurses**. This is equivalent to `Solution`'s greedy approach — both build the step count as a sum of powers of two, just structured differently (binary search + recursion vs. a top-down bit loop). The recursion depth is `O(log n)` and each level does `O(log n)` binary search work, so this is `O(log² n)` per query in the worst case vs. `Solution`'s `O(log n)` — a minor but real difference.

---

## Comparing the two
| | `Solution` | `Solution2` |
|---|---|---|
| Sorting | `int[n][2]` with comparator | Bit-packed `long[]`, natural sort (no comparator) |
| `jump[0]` window | Two conditions (consecutive gap + total span) | One condition (total span only) — different edge semantics |
| Query answering | Top-down bit loop: `O(log n)` per query | Binary search + recursion: `O(log² n)` per query |
| Overall | `O(n log n + q log n)` | `O(n log n + q log² n)` |

Both implement binary lifting over a value-sorted graph; the primary differences are packaging/encoding choices and the query-answering strategy.

## Complexity
- **Preprocessing:** `O(n log n)` — sort + `O(n)` two-pointer sweep + `O(n log n)` lifting table fill.
- **Per query:** `O(log n)` for `Solution`, `O(log² n)` for `Solution2`.
- **Space:** `O(n log n)` for the lifting table.

## Step-by-step example
`nums = [5, 1, 4, 2, 8]`, `maxDiff = 2`, query `(0, 4)` (original indices)

Sorted by value: `[(1,1), (2,3), (4,2), (5,0), (8,4)]` → sorted positions `[3, 0, 2, 1, 4]`

`jump[0]` (max span `maxDiff=2` from each position):
- pos 0 (val 1): can reach vals up to 3 → pos 1 (val 2) → `jump[0][0]=1`
- pos 1 (val 2): up to 4 → pos 2 (val 4) → `jump[0][1]=2`
- pos 2 (val 4): up to 6 → pos 3 (val 5) → `jump[0][2]=3`
- pos 3 (val 5): up to 7 → pos 3 only → `jump[0][3]=3`
- pos 4 (val 8): only itself → `jump[0][4]=4`

Query `(0,4)`: original index `0` → sorted pos `3`; original index `4` → sorted pos `4`. So `a=3, b=4`. One hop: `jump[0][3]=3 < 4`... `jump[1][3]=jump[0][3]=3 < 4`. After the greedy loop, `curr=3, steps=0`. Final check: `jump[0][3]=3 < 4` → no path? But wait, `jump[0][3]=3` means from pos 3 (val 5) we can reach at most pos 3 (val 7 is out of range since val at pos 4 is 8, and 8-5=3 > 2). So node 4 (val 8) is isolated — answer is `-1`. Correct: `|nums[0]-nums[4]| = |5-8| = 3 > maxDiff`.