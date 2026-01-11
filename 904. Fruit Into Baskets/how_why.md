# 904. Fruit Into Baskets - How & Why

## Problem Description

You have a row of trees; fruits[i] is the fruit type on tree i. You have two baskets and can collect exactly one fruit from each tree while moving left to right. Each basket can hold only one fruit type (unlimited quantity). Find the maximum number of fruits you can pick in one continuous segment with at most two distinct types.

---

## Approaches (from simple to optimal)

### 1) Brute Force (enumerate all subarrays)

- For every start i, expand end j and track distinct types in a tiny set.
- If distinct <= 2, update best length; stop when it reaches 3.
- Time: O(n^2)
- Space: O(1) (set size <= 3)

**Sketch (Java):**

```java
int ans = 0;
for (int i = 0; i < n; i++) {
    Set<Integer> seen = new HashSet<>();
    for (int j = i; j < n; j++) {
        seen.add(fruits[j]);
        if (seen.size() <= 2) ans = Math.max(ans, j - i + 1);
        else break;
    }
}
return ans;
```

---

### 2) Sliding Window with HashMap (Solution2)

- Pointers start/end plus a frequency map of counts in window.
- Expand end, add fruits[end].
- While map has 3 types, shrink from start, removing zero counts.
- Track max window length.
- Time: O(n) average; Space: O(1) practical (<= 3 keys).

Why it works: Standard at-most-K-distinct window; shrinking restores validity; greedy expansion maximizes length.

---

### 3) Two-Run Index Sliding Window (Solution, most optimal)

Exploit that we only need the last two runs of identical fruits.

Track indices:

- i: start of previous run (older type).
- j: start of current run (newer type).
- l: left boundary of window.
- r: scanning pointer.

Steps:

1) Collapse the first run so j is at the first different fruit; set l = i, r = j + 1.
2) While r < n:
   - If fruits[r] == fruits[j]: extend current run; r++.
   - Else if fruits[r] == fruits[i]: swap roles of runs (i = j; j = r); r++.
   - Else (third type): ans = max(ans, r - l); set l = j; i = j; j = r; r++.
3) Final ans = max(ans, r - l).

Why it works:

- Invariant: window [l, r) holds at most two types, represented by runs at i and j.
- On a third type, the longest suffix with one type is the run starting at j; keeping it and adding the new type yields the best valid continuation.
- Pointers move forward only, so O(n) time and O(1) space.

---

## Example Walkthrough (Two-Run)

Input: [1, 2, 1, 2, 3, 2, 2]

| Step | r | fruits[r] | Action | Window [l, r) | i, j |
|------|---|-----------|--------|---------------|------|
| init | 2 | 1 | first change at j=1, r=2 | [0,2) | 0,1 |
| 1 | 3 | 2 | extend | [0,3) | 0,1 |
| 2 | 4 | 3 | third type: record 4, shift l->j | [1,4) | 1,4 |
| 3 | 5 | 2 | extend | [1,5) | 1,4 |
| 4 | 6 | 2 | extend | [1,6) | 1,4 |
| end | - | - | final max | len=5 | - |

Best = 5 (e.g., subarray [2, 1, 2, 3, 2]).

---

## Comparison

| Aspect | Brute Force | HashMap Window (Solution2) | Two-Run Window (Solution) |
|--------|-------------|----------------------------|---------------------------|
| Time | O(n^2) | O(n) | O(n) |
| Space | O(1) | O(1) | O(1) |
| Constant factors | High | Medium (hash) | Lowest |
| Implementation | Simple | Simple/standard | Trickier indices |
| When to use | Tiny inputs only | General, easy to code | Performance-focused |

---

## Which approach to choose?

- Best: Two-Run Window — same asymptotic as hashmap but better constants and cache locality.
- Fallback: HashMap Window — simpler to reason about; great when clarity matters more than small perf.
- Avoid: Brute Force — only for teaching or very small n.

---

## Edge Cases

- All elements identical -> return n.
- n <= 2 -> return n.
- Alternating two types -> window grows to full length.
- Long run then new type -> window resets to the last run boundary.
- Single element -> 1.

---

## Mapping to Provided Code

- Class Solution2: HashMap sliding window (start/end, frequency map, shrink on size==3).
- Class Solution: Two-Run window (i, j, l, r pointers) maintaining last-two-run invariant.
