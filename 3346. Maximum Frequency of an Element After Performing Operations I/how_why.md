# how_why.md — **3346. Maximum Frequency of an Element After Performing Operations I** (Java)

---

## Problem (short)

Given an array `nums`, an integer `k` (a value-range limit), and `numOps` (number of operations available).
You can perform an operation on one element to change its value, **but you may only convert an element to a target value if the element's original value lies within `±k` of that target**. Each conversion uses one operation.
Return the maximum possible frequency (count) of any single value after performing up to `numOps` such conversions.

> We will:
>
> 1. show a **brute-force** approach (and its limits),
> 2. show a **count + prefix** approach (your "B1" style), and
> 3. show a **sliding-window / unique-values** approach (the preferred, robust approach).
>    Each approach has a short worked example and time/space complexity.

---

## 1) Brute force (naive)

**Idea.** For each distinct target value `t`, check every element of `nums` and count how many are already `t` and how many are within `[t-k, t+k]` (convertible). Then compute `freq = count(t) + min(numOps, convertibles - count(t))`. Track the best.

**Why naive?**

* You iterate over all distinct targets and for each you scan the whole array → O(n²) in worst case (if many distinct values).
* Simple but slow for `n` up to tens of thousands.

**Java (sketch)**

```java
// Not recommended for large inputs
int brute(int[] nums, int k, int numOps) {
    Set<Integer> uniq = new HashSet<>();
    for (int v : nums) uniq.add(v);
    int best = 0;
    for (int t : uniq) {
        int cnt = 0, convertible = 0;
        for (int v : nums) {
            if (v == t) cnt++;
            if (Math.abs(v - t) <= k) convertible++;
        }
        best = Math.max(best, cnt + Math.min(numOps, convertible - cnt));
    }
    return best;
}
```

**Complexity.** Time `O(n * u)` where `u` = #distinct values (worst `O(n²)`), Space `O(u)`.

**Tiny example walkthrough**

```c
nums = [1,2,4], k = 1, numOps = 1
targets: 1,2,4
target 1: cnt=1, convertible in [0..2]={1,2} => convertible=2 => freq=1+min(1,1)=2
target 2: cnt=1, convertible in [1..3]={1,2} => freq=2
target 4: cnt=1, convertible in [3..5]={4}   => freq=1
answer = 2
```

**Limitation.** When value range large or `n` large, double-loop is too slow.

---

## 2) Count + Prefix approach (B1) — your (value-range) approach

**Idea.** If values are not huge, build a frequency array `count[value]`. Build prefix-sums `pref` so that for any integer range `[L, R]` we can get the number of elements in that range in `O(1)`. For each candidate value `i` (iterate over possible values), compute:

* `freq = count[i]` (already equal to `i`)
* `total_in_range = pref[minVal] - pref[...]` i.e. number of elements in `[i-k, i+k]`
* We can convert at most `min(numOps, total_in_range - freq)` more elements to `i`
* `res = max(res, freq + min(numOps, total_in_range - freq))`

This is exactly the code pattern you posted earlier.

**When to use.**

* Best when `max(nums)` is small (or `max - min + 1` is manageable).
* Very fast `O(maxVal + n)` and simple constant-time range queries.

**Java (count + prefix)**

```java
import java.util.*;

class Solution {
    public int maxFrequency(int[] nums, int k, int numOps) {
        int maxNum = Arrays.stream(nums).max().orElse(0);
        int minNum = Arrays.stream(nums).min().orElse(0);
        int offset = Math.max(0, minNum); // optional — if negatives exist, shift

        // choose safe array size: (maxNum + k) - (minNum - k) + 1  => but we keep it simple
        int range = maxNum - minNum + k + 3;
        int[] cnt = new int[range];

        // shift by (minNum - k) so index maps into 0..
        int shift = minNum - k;
        for (int v : nums) {
            cnt[v - shift]++;
        }

        // prefix sum
        for (int i = 1; i < cnt.length; i++) cnt[i] += cnt[i - 1];

        int res = 0;
        for (int t = minNum - k; t <= maxNum + k; t++) {
            int idx = t - shift;
            // range [t-k, t+k] -> indices [idx - k, idx + k]  (clamp)
            int L = Math.max(0, idx - k);
            int R = Math.min(cnt.length - 1, idx + k);
            int totalInRange = cnt[R] - (L > 0 ? cnt[L - 1] : 0);

            // actual freq at value t (if inside original span)
            int freq = 0;
            int tIdx = t - shift;
            if (tIdx >= 0 && tIdx < cnt.length) {
                freq = cnt[tIdx] - (tIdx > 0 ? cnt[tIdx - 1] : 0);
            }
            res = Math.max(res, freq + Math.min(numOps, totalInRange - freq));
        }
        return res;
    }
}
```

**Complexity.**

* Time: `O(range + n)` where `range ≈ maxNum - minNum + k`
* Space: `O(range)`
* Works extremely well when `range` is modest. Fails (memory/time) if values are up to 1e9.

**Example walkthrough (same as earlier)**

```c
nums = [1,2,4,5,5], k=1, numOps=2
minNum=1, maxNum=5
Consider t = 5:
  range [4..6] contains values {4,5,5} -> totalInRange = 3
  freq@5 = 2
  can convert = min(2, 3-2) = 1
  freq after = 2 + 1 = 3
t=2:
  range [1..3] contains {1,2} total = 2, freq@2=1 => convert min(2,1)=1 => freq=2
Answer = 3
```

**What this approach does well**

* Constant-time range queries after prefix sums.
* Very fast when value-range bounded.

**What to watch out for**

* If `maxNum - minNum` is large (~10⁶+ or up to 10⁹), this will allocate huge arrays and is not feasible.
* Need to carefully shift indices when `minNum` can be negative.

---

## 3) Sliding-window on sorted *unique* values (SW1) — robust & memory-friendly (recommended)

**Idea (compact).**
Sort unique values and keep their cumulative counts. Instead of allocating a huge `count[]` across full value range, iterate distinct sorted values `vals[]`. For each right index `r` (candidate target `vals[r]`) use two pointers to find the leftmost `l` such that `vals[r] - vals[l] <= k`. Then the number of elements in `[vals[l], ..., vals[r]]` equals `pref[r] - pref[l-1]`. For target `vals[r]`:

* `totalInRange = number of elements whose value ∈ [vals[r]-k, vals[r]+k]`. But values > vals[r] and ≤ vals[r]+k are not covered by window to the left; to be safe, we can treat each candidate target as `vals[r]` and consider only `vals[l..r]` (values ≤ vals[r] and ≥ vals[r]-k). That suffices because if some numbers are larger than `vals[r]` but ≤ `vals[r]+k`, they will be considered when r moves to those larger values.
* `freq = count[vals[r]]`
* `res = max(res, freq + min(numOps, totalInRange - freq))`

This approach uses `O(u log u + u)` time where `u` is number of unique values — much smaller memory and safe even when values large.

**Why it works.**

* We only need to count elements that are convertible to `vals[r]`: these must lie in `[vals[r]-k, vals[r]+k]`. By iterating `r` as sorted unique values and using `l` to bound `vals[r]-k`, we capture all values ≤ `vals[r]` and ≥ `vals[r]-k`.
* Values > `vals[r]` but ≤ `vals[r]+k` will be handled later when `r` increases to those values; thus scanning all `r` covers all candidate targets.

**Java (sliding-window on unique sorted values)**

```java
import java.util.*;

class Solution {
    public int maxFrequency(int[] nums, int k, int numOps) {
        // Frequency map of values
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int v : nums) freqMap.put(v, freqMap.getOrDefault(v, 0) + 1);

        // Sorted unique values
        int u = freqMap.size();
        int[] vals = new int[u];
        int idx = 0;
        for (int v : freqMap.keySet()) vals[idx++] = v;
        Arrays.sort(vals);

        // prefix counts over unique values
        int[] pref = new int[u];
        for (int i = 0; i < u; i++) {
            int cnt = freqMap.get(vals[i]);
            pref[i] = cnt + (i > 0 ? pref[i - 1] : 0);
        }

        int res = 0;
        int left = 0;
        for (int right = 0; right < u; right++) {
            // move left until vals[right] - vals[left] <= k
            while (left <= right && vals[right] - vals[left] > k) left++;

            int totalInRange = pref[right] - (left > 0 ? pref[left - 1] : 0);
            int freqAtTarget = freqMap.get(vals[right]);
            res = Math.max(res, freqAtTarget + Math.min(numOps, totalInRange - freqAtTarget));
        }
        return res;
    }
}
```

**Complexity.**

* Time: `O(n + u log u)` (build map + sort uniques + two-pointer sweep)
* Space: `O(u)` (freq map + arrays)
* Works well when values are large or sparse.

**Example walkthrough (same input)**

```java
nums = [1,2,4,5,5], k=1, numOps=2
freqMap: {1:1, 2:1, 4:1, 5:2}
vals = [1,2,4,5]
pref = [1,2,3,5]

r=0 (val=1): left=0 (1-1<=1)
  total = pref[0]=1, freq=1 -> res = 1 + min(2,0) = 1

r=1 (val=2): left=0 (2-1<=1)
  total = pref[1]=2, freq=1 -> res = max(1, 1+min(2,1)) = 2

r=2 (val=4): move left until 4 - vals[left] <= 1 -> left moves to index 2 (since 4-2=2>1)
  total = pref[2] - pref[1] = 3 - 2 = 1 , freq=1 -> res remains 2

r=3 (val=5): left moves to index 2 since 5-2=3>1, 5-4=1 ok -> left=2
  total = pref[3] - pref[1] = 5 - 2 = 3 (values 4,5,5)
  freq@5 = 2
  res = max(2, 2 + min(2, 1)) = 3
answer = 3
```

---

## 4) Comparison (short)

| Metric                    | Count + Prefix (value-range array) |          Sliding window on unique values |
| ------------------------- | ---------------------------------: | ---------------------------------------: |
| Time (typical)            |                     `O(range + n)` |    `O(n + u log u)` (u = #unique values) |
| Space                     |           `O(range)` (can be huge) |                         `O(u)` (compact) |
| Works best when           |         value span small and dense | values large or sparse; memory-sensitive |
| Implementation complexity |       easy but careful with shifts |     slightly more code (sort and prefix) |
| Numerical safety          | can blow for large values (memory) |                  robust for large values |

**Rule of thumb:** Use **count + prefix** if `maxNum - minNum + k` is small (say ≤ few million depending on memory). Otherwise use the **sliding-window** variant.

---

## 5) Do / Don't (practical interview hints)

**Do**

* First check value-range `max - min`. If small, `count+prefix` is ideal (O(1) range query).
* Use a frequency `Map` + sorted unique values when range is large.
* Always test a few small examples manually (duplicates, negative values, all same).
* Consider index-shift carefully if using arrays when values may be negative.

**Don't**

* Don't allocate arrays of size ~1e9; that will OOM.
* Don't assume operations cost depends on distance — here each convertible element costs 1 operation regardless of how close inside the ±k window it is.
* Don't forget duplicates — use frequency counts rather than only unique-value windows.

---

## 6) Final short conclusion

* **Brute force** is simple but `O(n²)` in worst case — only good for tiny `n`.
* **Count + Prefix** is super-fast if value-range is small/dense.
* **Sliding-window on unique values** is the **balanced, robust** solution recommended for general inputs (good time and memory).

---
