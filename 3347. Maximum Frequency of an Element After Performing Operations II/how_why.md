# 3347 — **Maximum Frequency of an Element After Performing Operations II** — HOW & WHY

> Short statement (informal):
> You are given an integer array `nums`. For each element `v = nums[i]` we can consider that element can be moved (changed) to any integer in the closed interval `[v - k, v + k]` by **spending one operation** on that element. You have at most `numOperations` such extra operations available. What is the **maximum frequency (count)** of any single integer value you can achieve in the array after up to `numOperations` operations?
> (We will use the interpretation that changing a number to any integer inside its `[v-k, v+k]` interval costs `1` operation for that element; this matches the approach implemented in the provided solution.)

---

## Table of contents

1. Brute-force (B1) — idea + code + limitation
2. Sweep-line / coverage (O1) — your approach — idea + code + why it’s good
3. Sorted + binary search (O2) — alternate optimized approach — idea + code
4. Example walkthrough (S-sized example, 6 elements) — show B1, O1, O2 step-by-step
5. Comparison (O1 vs O2) — complexity, memory, when to pick which
6. Interview tips — what to do / not to do, how to think about this problem
7. Final summary

---

## 1) Brute-force (B1)

### Idea

Try every possible integer target `t` that could be the final value. For each `t`, count:

* `cnt(t)` = how many elements are already equal to `t` (no operation needed).
* `cover(t)` = how many elements `v` satisfy `|v - t| <= k` (these elements can be turned into `t` with 1 operation).
* Using up to `numOperations` you can convert at most `min(cover(t) - cnt(t), numOperations)` additional elements into `t`.

So the frequency achievable at `t` is `cnt(t) + min(cover(t) - cnt(t), numOperations)`.

To brute force, pick `t` over a suitable integer range (for safety: from `min(nums) - k` to `max(nums) + k`) and compute the two counts by scanning the array.

### Java (brute-force)

```java
// Brute-force (very slow when range or n is large)
int bruteMaxFrequency(int[] nums, int k, int numOps) {
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int v : nums) { min = Math.min(min, v); max = Math.max(max, v); }
    int best = 0;
    for (int t = min - k; t <= max + k; t++) {
        int cnt = 0, cover = 0;
        for (int v : nums) {
            if (v == t) cnt++;
            if (Math.abs(v - t) <= k) cover++;
        }
        best = Math.max(best, cnt + Math.min(cover - cnt, numOps));
    }
    return best;
}
```

### Limitation

* **Time:** O(n * range). If `nums` values are large or `k` is large the `range` (`max - min + 2k`) may be huge — too slow.
* **Space:** O(1) extra.
* Not acceptable for big `n` or large value domain.

---

## 2) Sweep-line / coverage (O1) — **Your approach**

### Core idea (high-level)

Each original element `v` defines an interval of target values that it can reach: `[v - k, v + k]`. If we want to know, for a **candidate target** `t`, how many elements can reach `t`, we need the number of intervals that cover `t`. That is a standard interval coverage problem.

We can transform each `[L, R]` into two events:

* `+1` at `L`
* `-1` at `R + 1` (so prefix sum on sorted event points yields coverage at each integer point).

We also keep the exact counts of elements originally equal to each integer `t` (`cntPoints[t]`). For every point `t` (we iterate over the sorted unique event points), the coverage `cover(t)` is the current prefix sum. The achievable frequency at `t` is:

```c
cnt(t) + min(cover(t) - cnt(t), numOperations)
```

(covered but not already equal elements are `cover(t) - cnt(t)` and each such conversion costs 1; limited by `numOperations`).

### Complexity

* Build events: O(n)
* Iterate over sorted unique points: O(m log m) where `m` ≲ `3n` (each `v` contributes `v`, `v-k`, `v+k+1`). So overall ~ O(n log n).
* Space: O(n) for maps/sets.

### Why it’s good

* We never iterate over every possible integer value in `[min - k, max + k]` — only the meaningful points (event boundaries and original values).
* Works well when values are sparse or when `k` is large but `n` is moderate.
* Clean and simple logic once you see interval events → prefix sums.

### Java (your version, cleaned and commented)

```java
class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        Map<Integer, Integer> pointsCover = new HashMap<>(); // event map: pos -> delta
        Map<Integer, Integer> cntPoints = new HashMap<>();  // how many nums exactly equal to pos
        TreeSet<Integer> points = new TreeSet<>();          // sorted unique points to visit

        for (int num : nums) {
            cntPoints.put(num, cntPoints.getOrDefault(num, 0) + 1);
            int L = num - k;
            int Rplus = num + k + 1; // R + 1 for difference array
            pointsCover.put(L, pointsCover.getOrDefault(L, 0) + 1);
            pointsCover.put(Rplus, pointsCover.getOrDefault(Rplus, 0) - 1);
            points.add(L);
            points.add(Rplus);
            points.add(num); // ensure original values are evaluated
        }

        int res = 0;
        int runningCover = 0;
        for (int point : points) {
            runningCover += pointsCover.getOrDefault(point, 0);
            int already = cntPoints.getOrDefault(point, 0);
            int canConvert = runningCover - already; // candidates that can be converted to `point`
            if (canConvert < 0) canConvert = 0;
            int freq = already + Math.min(canConvert, numOperations);
            res = Math.max(res, freq);
        }
        return res;
    }
}
```

---

## 3) Sorted + binary-search counts (O2) — alternate optimized approach

### Core idea

If you sort the values and compress counts by value, for a candidate `t` you can quickly find how many values fall into `[t-k, t+k]` using binary search (or prefix sums on the sorted unique values). So iterate candidate `t` only over distinct values from `nums` (or distinct endpoints) and compute `cover(t)` in O(log n) per `t`.

More concretely:

1. Sort `nums`.
2. For each distinct candidate `t` (we can iterate distinct `v` from `nums` or edges `num-k` etc.), find:

   * left index `L = lower_bound(nums, t - k)`
   * right index `R = upper_bound(nums, t + k) - 1`
   * `cover = R - L + 1`
   * `already = count of nums equal to t` (from a freq map or using bounds)
   * `freq = already + min(cover - already, numOperations)`
3. Keep max.

### Complexity

* Sorting: O(n log n)
* For each candidate (≤ n distinct), binary search is O(log n) → overall O(n log n).
* Space: O(n) for sorted array + possible frequency map.

### Java (skeleton)

```java
class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        Arrays.sort(nums);
        int n = nums.length;
        // optional: build distinct values + counts
        List<Integer> distinct = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (int v : nums) {
            if (distinct.isEmpty() || distinct.get(distinct.size()-1) != v) {
                distinct.add(v);
                counts.add(1);
            } else {
                counts.set(counts.size()-1, counts.get(counts.size()-1) + 1);
            }
        }

        // prefix sums on original sorted nums or on counts expanded into positions
        int res = 0;
        // We'll binary search on `nums` directly for ranges
        for (int tCandidate : distinct) {
            int L = lowerBound(nums, tCandidate - k);
            int R = upperBound(nums, tCandidate + k) - 1;
            int cover = (R >= L) ? (R - L + 1) : 0;
            int already = countFor(distinct, counts, tCandidate);
            int freq = already + Math.min(cover - already, numOperations);
            res = Math.max(res, freq);
        }
        return res;
    }
    private int lowerBound(int[] a, int x) { /* standard implementation */ }
    private int upperBound(int[] a, int x) { /* standard implementation */ }
    private int countFor(List<Integer> distinct, List<Integer> counts, int val) { /* map-style lookup */ }
}
```

### When O2 helps

* When `nums` is dense and `k` is moderate, or when you prefer binary-search based reasoning.
* Simpler to implement when you already have sorted `nums` and standard lower/upper bound utilities.

---

## 4) Example walkthrough (size **S** — 6 elements)

**Input**

```c
nums = [1, 2, 2, 4, 7, 8]
k = 2
numOperations = 2
```

Interpretation: each element `v` can be changed to any integer in `[v-2, v+2]` at cost 1 (one operation on that element). You may do up to 2 such changes.

### Brute-force (B1) walkthrough (illustrative)

We examine candidate targets `t` (we’ll pick a few):

* `t = 2`:

  * `cnt(2)` = elements equal to 2 → `2`.
  * `cover(2)` = elements whose interval covers 2:

    * 1 → `[ -1, 3 ]` covers 2 → yes
    * 2 → `[0,4]` covers 2 → yes (two elements)
    * 4 → `[2,6]` covers 2 → yes
    * 7 → `[5,9]` no
    * 8 → `[6,10]` no
      => `cover(2) = 4`. `canConvert = cover - cnt = 4 - 2 = 2`.
      Using up to `numOperations=2`, we can convert 2 additional numbers → `freq = 2 + min(2,2) = 4`.

* `t = 4`:

  * `cnt(4) = 1`
  * `cover(4)`: 1? `[ -1, 3]` no; 2? `[0,4]` yes (two elements); 4? yes; 7? `[5,9]` no; 8? no → cover = 3
  * `canConvert = 3 - 1 = 2` → `freq = 1 + min(2,2) = 3`.

* `t = 6`:

  * `cnt(6) = 0`
  * `cover(6)`: only 4 (`[2,6]`) and 7 (`[5,9]`) and 8 (`[6,10]`) cover 6 → cover = 3
  * `canConvert = 3 - 0 = 3` → limited by `numOperations=2` → `freq = 0 + 2 = 2`.

From checking likely points, the best found was `t = 2` with frequency `4`. So brute force would return `4`.

### Sweep-line (O1) walkthrough

Build events for each `v`:

* v=1: add `+1` at `1-2 = -1`, add `-1` at `1+2+1 = 4`
* v=2: `+1` at `0`, `-1` at `5` (two such events because two 2's)
* v=4: `+1` at `2`, `-1` at `7`
* v=7: `+1` at `5`, `-1` at `10`
* v=8: `+1` at `6`, `-1` at `11`

Also `cntPoints`: count of exact values: `{1:1, 2:2, 4:1, 7:1, 8:1}`

Sort unique points and sweep (we only need points where events or original values exist):
The sorted points might be `{-1, 0, 1, 2, 4, 5, 6, 7, 10, 11}` — process prefix sums:

* point = -1: runningCover = +1 (from v=1). cnt(-1)=0 ⇒ freq = 0 + min(1,2) = 1
* point = 0: runningCover += +1 (v=2) => 2. cnt(0)=0 → freq = min(2,2)=2
* point = 1: no event delta here, runningCover=2. cnt(1)=1 → canConvert=1 → freq = 1 + min(1,2) = 2
* point = 2: +1 (v=4) => runningCover=3. cnt(2)=2 => canConvert=1 => freq = 2 + min(1,2)=3
* point = 4: -1 (v=1's -1 event at 4) => runningCover = 2. cnt(4)=1 => canConvert =1 => freq=2
  *(note: careful: events at same coordinate may sum; real implementation uses all actual event keys)*
* point = 5: events: -1 (two v=2 events) and +1 (v=7) => net change, etc.
* … continuing you will find the max freq computed is `4` (at integer `2` as seen in brute-force).

This procedure obtains the same `4` but by visiting only O(n) meaningful points and using prefix sums.

### Sorted + binary search (O2) walkthrough

Sorted `nums` = `[1,2,2,4,7,8]`. For each distinct `t ∈ {1,2,4,7,8}`:

* `t=2`: lowerBound for `2-2 = 0` → index 0; upperBound for `2+2 = 4` → index of first >4 is 4 → R=3 → `cover = 4 - 0 = 4`, `already = 2`, `freq=2+min(2,2)=4`.
* `t=4`: lowerBound of `2` → idx 1; upperBound of `6` → idx 4 → cover = 4-1 = 3 → already=1 → freq=3
* etc. Result = 4.

All three approaches agree on the answer `4`. O1 and O2 get there much faster than brute force for bigger inputs.

---

## 5) O1 vs O2 — Comparison

| Aspect          |                                                        Sweep-line (O1) | Sorted + binary (O2)                                                                       |
| --------------- | ---------------------------------------------------------------------: | ------------------------------------------------------------------------------------------ |
| Time complexity |                                            O(n log n) (maps / TreeSet) | O(n log n) (sorting + binary searches)                                                     |
| Space           |                                                      O(n) (maps + set) | O(n) (sorted array + optional freq arrays)                                                 |
| Simplicity      |                            Medium — event logic needs care (R+1 event) | Medium — uses sorting + binary search which is very standard                               |
| Best when       | Many intervals overlap, value range is large / sparse — events are few | Values dense and you already expect to sort or you want to use standard lower/upper bounds |
| Edge cases      |             Must carefully handle `R + 1` events, negative coordinates | Careful with bounds and duplicate counts                                                   |

**Bottom line:** both O1 and O2 are reasonable; choose based on which primitives you’re more comfortable with:

* If you like interval reasoning and difference arrays → O1.
* If you prefer sorting+binary search and prefix counts → O2.

---

## 6) Interview tips — what to do / what not to do

**Do**

* Convert element reachability to intervals `[v-k, v+k]`. Thinking in intervals often simplifies “can turn to t” questions.
* Reduce the candidate points you check: only event/unique points (O1) or only distinct `nums` values (O2).
* Keep both `cover(t)` (how many intervals include `t`) and `cnt(t)` (how many are already equal to `t`) to compute the exact benefit from `numOperations`.
* Use difference-array / sweep-line pattern (`+1` at L, `-1` at R+1) when the domain of integers is large but `n` is moderate.
* Use binary search on a sorted array when counts per value and range queries are needed.

**Don’t**

* Don’t naively iterate every integer in `[min-k, max+k]` — that can be enormous.
* Don’t forget that converting an element already equal to `t` costs 0 — only covered-but-not-equal elements cost operations.
* Avoid off-by-one mistakes on the `R + 1` event when building difference arrays.

---

## 7) Final summary

* **Brute force (B1)** is conceptually direct — evaluate each possible target `t` and compute `cover` and `cnt` — but it is usually too slow.
* **Sweep-line (O1)** converts each number into an interval and uses difference events; sweep the sorted event points to obtain cover counts. Matches your provided solution and is efficient: O(n log n).
* **Sorted + binary (O2)** sorts `nums` and uses binary search / prefix counts to compute `cover(t)` quickly for each candidate `t`. Also O(n log n) and simpler if you already sorted `nums`.
* Both optimized approaches avoid scanning every integer in the value domain and are acceptable choices; the best one depends on your comfort with intervals vs sorting+binary techniques.

---
