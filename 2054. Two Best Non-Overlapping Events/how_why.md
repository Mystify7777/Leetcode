# How_Why.md – Two Best Non-Overlapping Events (LeetCode 2054)

## ❌ Brute Force Idea

You could try all pairs of events and keep the best non-overlapping sum.

- For each event `i`, compare with every event `j > i` and check if they overlap.
- Track the maximum value of `value[i] + value[j]` when they do not overlap.

Why it’s inefficient:

- Pairwise checking is `O(n^2)` and times out for `n` up to `1e5`.
- Even adding simple pruning doesn’t change the quadratic worst case.

---

## ✅ Optimal Approach: Sort + Prefix Max + Binary Search

Core idea: if events are sorted by end time, then for any event `i`, the best partner to pair with from the left is simply the maximum-value event whose end time is strictly less than `start[i]`.

Steps:

1. Sort events by `end` ascending.
2. Build `maxUpTo[i]`: the maximum event value among events `0..i`.
3. For each event `i`, binary search the last index `j < i` with `end[j] < start[i]`.
    - Candidate sum = `value[i] + (j == -1 ? 0 : maxUpTo[j])`.
    - Track the global maximum over all `i`.

This yields `O(n log n)` time and `O(n)` space.

---

## 🧠 Why This Works

- Sorting by `end` ensures all valid partners for event `i` lie to its left (since they must end before `start[i]`).
- Among those, only the maximum-value event matters; we don’t need to know which one specifically—just its value. Hence the prefix max array.
- Binary search over ends is valid because ends are monotonic after sorting.

Note on non-overlap: the problem requires strict separation—events `a` and `b` are compatible only if `end[a] < start[b]` or `end[b] < start[a]`. Equality does not count as non-overlap. The binary search uses `< start[i]` accordingly.

---

## 🔍 Example

Events as `[start, end, value]`:

```math
[[1,3,4], [2,4,3], [3,10,2], [5,6,5]]
```

1) Sort by end:

    ```math
            [[1,3,4], [2,4,3], [5,6,5], [3,10,2]]
    ends:      3        4        6        10
    ```

2) Prefix max `maxUpTo` over values:

    ```java
    values:     4, 3, 5, 2
    maxUpTo:    4, 4, 5, 5
    ```

3) For each event `i`, binary search last `j` with `end[j] < start[i]`:

    - `i=0` ([1,3,4]): no left event → best = 4
    - `i=1` ([2,4,3]): `end < 2` none → best = 3
    - `i=2` ([5,6,5]): last `end < 5` is `end=4` at `j=1` → sum = 5 + maxUpTo[1] = 5 + 4 = 9
    - `i=3` ([3,10,2]): last `end < 3` none → best with this = 2

Answer = 9.

---

## ⏱️ Complexity

- Time: `O(n log n)` (sort + `n` binary searches)
- Space: `O(n)` (prefix max array)

---

## 🔁 Equivalent Variant (Suffix Max on Starts)

An equivalent implementation sorts by `start`, precomputes a suffix max of values, and for each event finds the first index with `start > end[i]` via binary search, then pairs `value[i]` with the best to its right. This mirrors the same `O(n log n)` complexity and can be simpler in some codebases.

---

## ✅ Key Takeaways

- Sort intervals to expose monotonic structure for binary search.
- Reduce search to a single number via prefix/suffix maxima.
- Strict inequality matters here: use `end < start`.
