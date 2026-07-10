# 3691. Maximum Total Subarray Value II

## What changed from "I" to "II"
Same value definition (`max(subarray) - min(subarray)`), and you still pick `k` subarrays to maximize the sum of their values — but here the `k` subarrays must be treated as **distinct ranges** (you can't just take the single best range and reuse it `k` times, the way the "I" version could). This is why the trivial `k * (globalMax - globalMin)` formula no longer applies, and both solutions here do real algorithmic work.

## The core idea shared by both solutions

### Insight 1: for a fixed starting point, extending the range never hurts
For any fixed left endpoint `l`, as the right endpoint `r` grows from `l` to `n-1`, the value `max(nums[l..r]) - min(nums[l..r])` can only **stay the same or increase** — extending a window can only reveal a new max or a new min (or neither), never take one away. So:
- The **best possible** subarray starting at `l` is always `[l, n-1]` (extend all the way to the end).
- More generally, for a fixed `l`, the sequence of values as `r` decreases from `n-1` down to `l` is **non-increasing** — a naturally sorted (descending) list of candidate values, one "list" per starting index `l`.

### Insight 2: this is a "top-k across many sorted lists" problem
With `n` such descending lists (one per starting index `l`, ranging over `r = n-1, n-2, ..., l`), the task becomes: *pick the `k` largest values across all these lists combined, summed together.* This is the classic **heap-based merge** pattern (the same idea used in "k pairs with smallest sums" or "smallest range covering k lists"):
- Seed a max-heap with the **best candidate from each list** (i.e., `[l, n-1]` for each `l`).
- Repeatedly pop the current largest value, add it to the answer, and push that same list's **next-best candidate** (shrink `r` by one) back onto the heap.
- After `k` pops, you've obtained the sum of the `k` largest subarray values overall — without ever generating all `O(n²)` subarrays up front.

The remaining engineering problem is: how do you evaluate `max(nums[l..r]) - min(nums[l..r])` for an arbitrary range **quickly**, since this needs to happen up to `O(k)` times? Both solutions answer this with a **Sparse Table** (a classic Range Maximum/Minimum Query structure supporting `O(1)` queries after `O(n log n)` preprocessing), just built differently.

---

## Solution2 — straightforward sparse table + `PriorityQueue`

### Sparse table (RMQ) setup
```java
for(int i = 0;i<n;i++){
    max[i][0] = nums[i];
    min[i][0] = nums[i];
}
for(int j = 1;j<=M;j++){
    for(int i = 0;i+(1<<j)<=n;i++){
        max[i][j] = Math.max(max[i][j-1], max[i+(1<<(j-1))][j-1]);
        min[i][j] = Math.min(min[i][j-1], min[i+(1<<(j-1))][j-1]);
    }
}
```
Standard sparse table construction: `max[i][j]` / `min[i][j]` store the max/min over the window of length `2^j` starting at index `i`, built by combining two half-length windows (`2^(j-1)` each) computed at the previous level. This is precomputed once, in `O(n log n)` time and space.

```java
int query(int l,int r){
    int len = r-l+1;
    int k = log[len];
    return Math.max(max[l][k],max[r-(1<<k)+1][k])-
          Math.min(min[l][k],min[r-(1<<k)+1][k]);
}
```
To answer `max(nums[l..r]) - min(nums[l..r])` in `O(1)`: pick the largest power of two `2^k` that fits inside the range length, and combine **two overlapping windows** of that length — one starting at `l`, one ending at `r`. Because `max`/`min` are idempotent under overlap (taking the max of overlapping ranges is still correct even if some elements are counted twice), this always covers the whole `[l, r]` range in a single `O(1)` combination — no partial-block handling needed. `log[]` is precomputed once so `log[len]` (the floor of `log2(len)`) is also `O(1)` per query.

### The heap-based top-k merge
```java
PriorityQueue<int[]> heap = new PriorityQueue<int[]>((a,b)-> b[0]-a[0]);
for(int i = 0;i<Math.min(n,k);i++){
    heap.offer(new int[]{query(i,n-1),i,n-1});
}
```
Seed the heap with the best candidate (`[i, n-1]`) for each starting index `i` — but only up to `min(n, k)` of them, since you'll never need more than `k` distinct starting points to make `k` picks (you might revisit the same starting point multiple times by shrinking `r`, so seeding all `n` starting points up front would be wasted work when `k < n`).

```java
long res = 0;
while(k>0 && !heap.isEmpty()){
    int[] top = heap.poll();
    res += top[0];
    if(top[2]-1>=top[1]){
        top[0] = query(top[1],top[2]-1);
        top[2]--;
        heap.offer(top);
    }
    k--;
}
```
Pop the current largest value and add it to `res`. Then, if this starting point (`top[1] = l`) still has room to shrink (`r - 1 >= l`), compute the next-best candidate for that same `l` (one step smaller `r`) and push it back — continuing that list's descending sequence. If `r` has shrunk down to `l` itself (a single-element subarray, value `0`), there's nothing left to push for that starting point; it simply isn't replaced.

### Complexity
- **Preprocessing:** `O(n log n)` time and space for the sparse table.
- **Main loop:** `O(k log n)` — each of the `k` pops/pushes does an `O(1)` range query plus an `O(log n)` heap operation (heap size stays bounded by roughly `min(n,k)`).
- **Total:** `O(n log n + k log n)`.

---

## Solution — a heavily hand-optimized version of the same idea

This solution implements *exactly* the same algorithm as `Solution2` (blocked sparse table + top-k heap merge), but every component is rewritten for raw speed: less memory, no object allocation in the hot loop, and no `PriorityQueue` comparator overhead. None of these changes affect *correctness* — they're all about constant-factor performance.

### 1. A "blocked" sparse table instead of a per-index one
```java
static final int BLOCK_SHIFT = 3;
static final int BLOCK_SIZE = 1 << BLOCK_SHIFT; // 8
```
Instead of building a sparse table over every individual index (`O(n log n)` space), this groups elements into fixed-size blocks of `8`, computes each block's max/min directly with a linear scan, and only builds the sparse table **over the blocks** (`O((n/8) log(n/8))` space — a meaningful reduction, especially for large `n`).

```java
private int rangeValue(int l, int r) {
    ...
    if (bl == br) { /* l and r in the same block: just scan directly */ }
    // otherwise:
    // 1. scan the leftover part of l's block
    // 2. scan the leftover part of r's block
    // 3. O(1) sparse-table query over the fully-contained blocks in between
}
```
A range query now does a **small linear scan** (at most `2 * BLOCK_SIZE ≈ 16` elements, for the partial blocks at each end) plus an `O(1)` sparse-table lookup for whatever whole blocks lie fully between them. This trades a tiny constant amount of scanning for a much smaller sparse table to build and store — a classic space/time tradeoff (essentially a two-level "sqrt/block decomposition + sparse table" hybrid).

### 2. A hand-rolled heap over packed `long` values instead of `PriorityQueue<int[]>`
```java
private long pack(long val, int l, int r) {
    return (val << 32) | ((long) l << PACK_SHIFT) | r;
}
```
Rather than storing `{value, l, r}` as a boxed `int[]` (with allocation and comparator-call overhead on every heap operation), all three numbers are packed into a **single `long`**: the value occupies the high 32 bits, `l` occupies the next 16 bits, `r` occupies the low 16 bits. Since `val` sits in the most-significant bits, **comparing two packed longs directly** (`heap[right] > heap[child]`) is equivalent to comparing by `val` first — giving correct max-heap ordering with a plain numeric comparison, no comparator object needed at all. (This packing scheme assumes indices fit in 16 bits, i.e. `n` up to `65535` — reasonable given this problem's constraints.)

```java
heap = new long[n + 2];
buildInitialHeap(nums);
```
The heap is backed by a raw `long[]` array — no per-node object, no autoboxing.

### 3. Building the initial heap in `O(n)` instead of `O(n log n)`
```java
int sfxMax = nums[n - 1];
int sfxMin = nums[n - 1];
heap[n - 1] = pack(0, n - 1, n - 1);
for (int i = n - 2; i >= 0; i--) {
    ...
    heap[i] = pack(sfxMax - sfxMin, i, n - 1);
}
...
for (int i = (n >> 1) - 1; i >= 0; i--) {
    siftDown(i);
}
```
Instead of inserting each starting point's initial candidate one at a time (`n` inserts × `O(log n)` each = `O(n log n)`), this computes **all** `n` initial candidate values directly with a single backward sweep (tracking a running suffix max/min — since every candidate here is a suffix `[i, n-1]`, its value is just "max/min of everything from `i` to the end," computable incrementally), fills the whole array at once, then heapifies it bottom-up in one pass — the standard `O(n)` "build-heap" technique (cheaper than `n` sequential insertions).

### 4. Popping and replacing in one operation
```java
private void replaceTop(long replacement) {
    // sift the replacement value down from the root directly
}
```
Rather than removing the top element and then separately inserting a new one (two `O(log n)` operations), `replaceTop` directly sifts the *new* packed value down from the root in a single pass — functionally a "pop + push" fused into one cheaper traversal, since the heap only ever needs its root replaced (either with the shrunk-range candidate, or, if that starting index is exhausted, with whatever was last in the heap array, shrinking the effective heap size by one).

### Main loop (same algorithm as `Solution2`, just operating on packed values)
```java
while (k-- > 0 && heapSize > 0) {
    long top = heap[0];
    long val = top >> 32;
    if (val == 0) break; // no more positive-value ranges left; further picks add nothing
    ans += val;
    int l = (int) ((top >> PACK_SHIFT) & PACK_MASK);
    int r = (int) (top & PACK_MASK);
    ...
}
```
Unpack the top entry's value, `l`, and `r`; add the value to the answer; then either shrink `r` by one and recompute that range's value (pushing the next candidate for the same `l`, exactly like `Solution2`), or, if `r` has already reached `l`, drop that entry from the heap for good. The `if (val == 0) break;` is a small early exit: once the best remaining candidate is worth `0`, every remaining subarray value is also `0` (since it's a max-heap), so further picks can't improve the answer.

### Complexity
- **Preprocessing:** `O(n)` for block max/min plus `O((n/8) log(n/8))` for the block-level sparse table — asymptotically the same as `Solution2` but with a much smaller constant.
- **Main loop:** `O(k log n)`, same as `Solution2`, but each operation is a cheap array/bit-manipulation instead of object allocation and comparator calls.
- **Total:** `O(n + k log n)` with a significantly smaller constant factor — this is what lets it run meaningfully faster than `Solution2` on large inputs, despite implementing the identical algorithm.

---

## Comparing the two
| | `Solution2` | `Solution` |
|---|---|---|
| RMQ structure | Full sparse table, `O(n log n)` space | Blocked sparse table (block size 8), much smaller space, small linear scan per query |
| Heap | `PriorityQueue<int[]>` (boxed, comparator-based) | Raw `long[]` heap with bit-packed `(value, l, r)`, hand-written sift operations |
| Initial heap build | `min(n,k)` individual `offer()` calls | Full `O(n)` suffix sweep + one bottom-up heapify |
| Algorithmic idea | Top-k merge across per-`l` descending sequences | **Identical** top-k merge — just faster constants |

Both solutions are correct implementations of the same "top-k across n sorted-by-construction lists" idea; `Solution` is essentially `Solution2` rewritten with competitive-programming-style micro-optimizations (blocking, bit-packing, manual heap management) to reduce constant factors, not to change the underlying algorithm.

## Step-by-step example
`nums = [3, 8, 1, 6]`, `k = 3`

Best candidate per starting index (always extending to `n-1 = 3`):
- `l=0`: `[3,8,1,6]` → max−min = `8−1=7`
- `l=1`: `[8,1,6]` → `8−1=7`
- `l=2`: `[1,6]` → `6−1=5`
- `l=3`: `[6]` → `0`

Heap starts with these 4 values (or `min(n,k)=3` of them in `Solution2`'s seeding). Pop `7` (say `l=0,r=3`) → `ans=7`; shrink to `[0,2] = [3,8,1]` → value `8−1=7`, push back. Pop `7` again (either the `l=1` entry or the newly shrunk `l=0` entry — both tie at `7`) → `ans=14`. Pop the next largest (`7` or `5` depending on tie-breaking) → `ans=21` (if the third `7` is available) or `19`. After `k=3` pops, return the accumulated sum — the exact numeric result depends on which of the tied `7`s get popped first, but the total is guaranteed to be the sum of the **3 largest** achievable subarray values.