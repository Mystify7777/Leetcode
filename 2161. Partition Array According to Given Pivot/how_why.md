# 2161. Partition Array According to Given Pivot

## Problem in short
Rearrange `nums` into a new array where:
- all elements `< pivot` come first, in their **original relative order**,
- then all elements `== pivot`,
- then all elements `> pivot`, also in their **original relative order**.

(Stability matters — you can't just sort or freely reorder within each group.)

---

## Solution2 — classic counting-based 3-way partition (two passes)

### Key Insight
If you know in advance **how many** elements fall into each of the three groups, you immediately know exactly which index range each group occupies in the output:
- "less than" elements go to indices `[0, smaller)`
- "equal" elements go to indices `[smaller, smaller + equal)`
- "greater than" elements go to indices `[smaller + equal, n)`

Since you're writing each group's elements to their designated range **in the same order you encounter them** (left to right through the original array), relative order within each group is automatically preserved — this is the same idea as a stable counting sort.

### Line-by-line
```java
for (int num : nums){
    if(num > pivot) larger++;
    else if(num < pivot) smaller++;
    else equal++;
}
```
Pass 1: count how many elements fall into each of the three buckets.

```java
int i = 0;
int j = smaller;
int k = smaller + equal;
```
Set up three write-pointers, one per bucket, positioned at the start of their respective output ranges: `i` for "smaller" (starts at `0`), `j` for "equal" (starts right after all the smaller elements), `k` for "larger" (starts right after smaller + equal elements).

```java
for (int num : nums){
    if(num > pivot){ answer[k] = num; k++; }
    else if(num < pivot){ answer[i] = num; i++; }
    else{ answer[j] = num; j++; }
}
```
Pass 2: walk through the original array again, in order, and drop each element into its bucket's next available slot. Because we scan left-to-right and only ever advance each bucket's pointer forward, elements within the same bucket land in the output in the same relative order they appeared in the input.

### Complexity
- **Time:** `O(n)` — two linear passes.
- **Space:** `O(n)` for the output array (`O(1)` extra beyond that).

---

## Solution — single-pass, two-pointers-from-both-ends (no counting needed)

### Key Insight
Instead of first counting how many elements are smaller/larger and then computing output ranges, this version fills the output array **from both ends inward, simultaneously**, in a single pass:
- A pointer `l` (starting at `0`) fills "less than pivot" elements as they're found scanning **left to right**.
- A pointer `r` (starting at `n-1`) fills "greater than pivot" elements as they're found scanning **right to left**.
- Whatever's left unfilled in the middle, once both scans finish, must be exactly the "equal to pivot" elements — filled in with `pivot` directly, no need to ever have located them individually.

### Why a single combined loop still preserves order for both groups
```java
for(int i=0,j=nums.length-1; i<nums.length; i++,j--){
    if(nums[i]<pivot) ans[l++]=nums[i];
    if(nums[j]>pivot) ans[r++ /* r-- */]=nums[j];
}
```
(note: it's `ans[r--]`, decrementing `r`)

`i` scans the array forward (`0 → n-1`) checking for "less than pivot" elements and appending them to the growing prefix at `l`. `j` scans the array **backward** (`n-1 → 0`) *at the same time*, checking for "greater than pivot" elements — but writes them to a **shrinking** region from `r = n-1` downward.

Here's the subtle but important part: since `j` moves right-to-left and `r` also moves right-to-left (both decreasing together), the *first* "greater" element found (the rightmost one in the array) lands in the *rightmost* output slot; the *next* one found (further left in the array) lands in the next slot to its left; and so on. Scanning backward while also writing backward means the relative left-to-right order among "greater" elements is preserved in the output — two "reversals" (scan direction and write direction) cancel out.

Because `i` and `j` sweep the *entire* array together in one loop (`i` from the left, `j` from the right, meeting in the middle after `n` iterations), **every single element gets checked exactly once from the "less than" side (via `i`) and exactly once from the "greater than" side (via `j`)** — just at different iterations. Since no element can be both less than *and* greater than the pivot, there's never a conflicting double-write.

```java
while(l<=r) ans[l++]= pivot;
```
After the main loop, indices `[0, l)` hold all "less than" elements and indices `(r, n)` hold all "greater than" elements — correctly positioned, just like in `Solution2`, except the boundary between them was never explicitly counted. Whatever gap remains between `l` and `r` (inclusive) is exactly big enough to hold the "equal to pivot" elements, so it's simply filled with `pivot`.

### The static block — a JIT warm-up trick, not part of the algorithm
```java
static {for(int i=0; i<300;i++) pivotArray(new int[1],0);}
```
This has nothing to do with correctness of the partitioning logic. It's a common competitive-programming trick on judges like LeetCode: calling the method many times *before* the actual graded call encourages the JVM's Just-In-Time (JIT) compiler to compile `pivotArray` down to optimized native code ahead of time, so the real, timed invocation doesn't pay the cost of running on the slower interpreter/warm-up path. It can shave measurable time off the reported runtime on LeetCode's judge, though it's irrelevant to the algorithm itself and wouldn't matter in a real production setting.

### Complexity
- **Time:** `O(n)` — a single combined pass, plus the (bounded) fill-in loop for equal elements.
- **Space:** `O(n)` for the output array (`O(1)` extra).

---

## Comparing the two
| | `Solution2` | `Solution` |
|---|---|---|
| Passes over `nums` | 2 (count, then place) | 1 (place from both ends at once) |
| Needs bucket sizes ahead of time | Yes (`smaller`, `equal`, `larger` counts) | No — boundaries fall out naturally from where `l` and `r` end up |
| Readability | Very straightforward, easy to verify | Cleverer, requires a moment's thought to see why order is preserved |
| Extra tricks | None | JIT warm-up static block (judge-specific, not algorithmic) |

Both are correct, stable, `O(n)`-time solutions; `Solution` trades a bit of readability for doing the whole rearrangement in one sweep instead of two.

## Step-by-step example
`nums = [9, 12, 5, 10, 14, 3, 10]`, `pivot = 10`

- Less than 10: `9, 5, 3` → in order.
- Equal to 10: `10, 10` → count 2.
- Greater than 10: `12, 14` → in order.

**Solution2:** `smaller=3, equal=2, larger=2` → `i=0,j=3,k=5` → scanning again places `9→0, 12→5, 5→1, 10→3, 14→6, 3→2, 10→4` → result `[9,5,3,10,10,12,14]`.

**Solution:** `l` fills `9,5,3` into positions `0,1,2`; `r` fills `14,12` into positions `6,5` (in that order, right to left); after the loop `l=3, r=4`, and the gap `[3,4]` is filled with `10,10` → same result `[9,5,3,10,10,12,14]`.