# 2574. Left and Right Sum Differences

## Problem in short
For each index `i`, compute:
- `leftSum[i]` = sum of all elements **before** index `i`
- `rightSum[i]` = sum of all elements **after** index `i`
- `answer[i] = |leftSum[i] - rightSum[i]|`

Return the `answer` array.

## Key Insight (the "why")
You don't need to recompute a sum from scratch for every index — that would be `O(n²)` (for each `i`, re-summing everything to its left and everything to its right). Since `leftSum[i]` and `rightSum[i]` only change incrementally as `i` moves forward by one, both can be maintained with a **running total** in a single pass (or two passes), turning the problem into `O(n)`.

There's also a neat algebraic shortcut used in `Solution`: once you know the array's **total sum**, you don't need to separately track `rightSum` — it's just `totalSum - leftSum - nums[i]` (everything, minus what's to the left, minus the current element itself).

---

## Solution — one running total + a formula for the other side

```java
int totalSum = 0;
for(int i=0;i<n;i++){
    totalSum += nums[i];
}
```
First pass: compute the sum of the entire array once.

```java
int leftSum = 0;
for(int i=0;i<n;i++){
    int rightSum = totalSum - leftSum - nums[i];
    ...
    leftSum += nums[i];
}
```
Second pass: `leftSum` is maintained incrementally, starting at `0` (nothing is to the left of index `0`) and growing by `nums[i]` *after* processing index `i` (so at the time `rightSum` is computed for index `i`, `leftSum` still correctly holds only the sum of elements strictly before `i`).

`rightSum` at index `i` is derived, not tracked separately: `totalSum` (everything) minus `leftSum` (everything strictly to the left) minus `nums[i]` (the current element itself) leaves exactly the sum of everything strictly to the right.

```java
if(leftSum > rightSum){
    answer[i] = leftSum - rightSum;
}else{
    answer[i] = rightSum - leftSum;
}
```
This is just `Math.abs(leftSum - rightSum)` written out manually as an if/else, avoiding a function call.

---

## Solution2 — explicit prefix and suffix sum arrays

```java
long pre[]=new long[n];
long suf[]=new long[n];
pre[0]=0;
suf[n-1]=0;
for(int i=1;i<n;i++){
    pre[i]=pre[i-1]+arr[i-1];
}
for(int i=n-2;i>=0;i--){
    suf[i]=suf[i+1]+arr[i+1];
}
```
This builds two full arrays instead of one running variable:
- `pre[i]` = sum of everything **before** index `i` (`pre[0] = 0` since nothing precedes the first element; each subsequent `pre[i]` adds the element just before it, `arr[i-1]`, to the previous prefix sum).
- `suf[i]` = sum of everything **after** index `i` (`suf[n-1] = 0` since nothing follows the last element; built from the right end backward, each `suf[i]` adds the element just after it, `arr[i+1]`, to the next suffix sum).

```java
for(int i=0;i<n;i++){
    res[i]=(int)Math.abs(pre[i]-suf[i]);
}
```
Once both arrays are fully built, the answer at each index is simply the absolute difference — using `Math.abs` directly this time, and using `long` arithmetic (then casting down to `int`) to safely avoid overflow while summing, even though the final subtraction is guaranteed to fit back into an `int` per the problem's constraints.

---

## Comparing the two

| | `Solution` | `Solution2` |
|---|---|---|
| Passes over the array | 2 (one for total, one for the answer) | 3 (one for `pre`, one for `suf`, one for the answer) |
| Extra space | `O(1)` — just a couple of scalars | `O(n)` — two full-length arrays |
| Arithmetic type | `int` throughout | `long` for the sums, cast to `int` at the end |

Both are `O(n)` time and produce identical results; `Solution` is the leaner version (constant extra space), while `Solution2` is arguably easier to read since `pre[i]` and `suf[i]` are named exactly what they represent, at the cost of allocating two extra arrays.

## Step-by-step example
`nums = [10, 4, 8, 3]`

Total sum = `25`.

| i | leftSum (before) | rightSum (after) | answer |
|---|---|---|---|
| 0 | 0 | 4+8+3=15 | 15 |
| 1 | 10 | 8+3=11 | 1 |
| 2 | 14 | 3 | 11 |
| 3 | 22 | 0 | 22 |

`answer = [15, 1, 11, 22]`

## Complexity
- **Solution:** Time `O(n)`, Space `O(1)` (excluding the output array).
- **Solution2:** Time `O(n)`, Space `O(n)` (for the `pre` and `suf` arrays).