# 3689. Maximum Total Subarray Value I

## Problem in short
You must pick **exactly `k` subarrays** (they can overlap, repeat, be any length — including length 1) from `nums`. The **value** of a subarray is `max(subarray) - min(subarray)`. Maximize the **sum** of these `k` values.

## Key Insight (the "why")
This looks like it might require picking `k` *different, cleverly chosen* subarrays, but it doesn't — the optimal strategy is almost embarrassingly simple:

- The value of **any** subarray can never exceed `globalMax - globalMin` (the max and min of the *entire* array), because a subarray's own max is at most the global max, and its own min is at least the global min, so `subarrayMax - subarrayMin <= globalMax - globalMin` always.
- That upper bound is **always achievable** — just pick the whole array (or any subarray that happens to contain both the position of the global max and the position of the global min) as one of your subarrays. Its value is exactly `globalMax - globalMin`.
- Since subarrays are allowed to **repeat** (nothing in the problem says the `k` subarrays must be distinct), you can simply pick that same best-possible subarray **all `k` times**.

So the maximum possible sum is just `k` copies of the best single value you could ever get: `k * (globalMax - globalMin)`. There's no combinatorial search needed at all — the whole problem collapses to finding the array's max and min.

## Line-by-line

```java
int max = Integer.MIN_VALUE;
int min = Integer.MAX_VALUE;

for (int num : nums) {
    max = Math.max(num, max);
    min = Math.min(num, min);
}
```
A single linear pass finds the global maximum and minimum of `nums`.

```java
return (long) k * (max - min);
```
Multiply the best achievable single-subarray value (`max - min`) by `k`, since that same best value can be reused for every one of the `k` picks. The cast to `long` matters here: `k` and `(max - min)` are both `int`, and their product could overflow a 32-bit `int` for large inputs, so the multiplication needs to happen in `long` arithmetic to stay correct (the return type of the method is `long` for exactly this reason).

## Step-by-step example
`nums = [4, 2, 9, 1, 7]`, `k = 3`

- `max = 9`, `min = 1` → best single subarray value = `9 - 1 = 8` (achieved by the whole array, or any sub-range spanning from the `9` to the `1`).
- Since we get to pick `k = 3` subarrays and they can repeat, pick that same range 3 times: total = `3 * 8 = 24`.

## Complexity
- **Time:** `O(n)` — one pass to find max and min.
- **Space:** `O(1)`.

## Why this wouldn't work if subarrays had to be distinct or non-overlapping
If the problem instead required the `k` subarrays to be **distinct** or **non-overlapping**, this simple formula would break down — you'd then need to actually search for the `k` best *different* ranges (a much harder combinatorial problem), since you couldn't just reuse the single best range `k` times. The fact that repetition is allowed here is what collapses the whole problem down to one linear scan.