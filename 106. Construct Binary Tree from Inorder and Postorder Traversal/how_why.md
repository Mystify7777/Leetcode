# How_Why.md – Merge Intervals (LeetCode 56)

## ❌ Brute Force Idea

We’re given a list of intervals (start, end), and we need to merge any overlapping ones.

**Naïve approach:**

* For each interval, check against all others to see if they overlap.
* If they do, merge them and repeat until no more merges are possible.

Example:

```
Input:  [[1,3],[2,6],[8,10],[15,18]]
Step 1: merge [1,3] & [2,6] → [1,6]
Step 2: no overlap with [8,10], [15,18]
Output: [[1,6],[8,10],[15,18]]
```

**Drawbacks:**

* Overlap checks = O(n²).
* Repeated scanning makes it slow for large n.

---

## ✅ Approach 1 – Sorting + Merging (Greedy)

Your first solution:

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    List<int[]> merged = new ArrayList<>();
    int[] prev = intervals[0];

    for (int i = 1; i < intervals.length; i++) {
        int[] interval = intervals[i];
        if (interval[0] <= prev[1]) {
            prev[1] = Math.max(prev[1], interval[1]); // merge
        } else {
            merged.add(prev);
            prev = interval;
        }
    }

    merged.add(prev);
    return merged.toArray(new int[merged.size()][]);
}
```

**How it works:**

1. Sort intervals by start time.
2. Traverse once:

   * If current interval overlaps with the previous, merge them.
   * Otherwise, add the previous one to the result.
3. Complexity = **O(n log n)** (sorting dominates).
4. Space = **O(n)** for result list.

---

## ✅ Approach 2 – Comparator + Greedy

Slight variation with explicit comparator + `ans.get(last)` logic:

```java
if (ans.isEmpty() || intervals[i][0] > ans.get(ans.size()-1)[1]) {
    ans.add(new int[]{intervals[i][0], intervals[i][1]});
} else {
    ans.get(ans.size()-1)[1] = Math.max(ans.get(ans.size()-1)[1], intervals[i][1]);
}
```

Same complexity, just a slightly different coding style.

---

## 🚀 Approach 3 – Interval Mapping

The third solution avoids sorting by using a **map of start → max end**:

1. Track for each start index the farthest end (`mp[start]`).
2. Traverse sequentially to expand active intervals (`have` = current max end).
3. Close interval when reaching `have`.

```java
int[] mp = new int[max + 1];
for (int i = 0; i < intervals.length; i++) {
    int start = intervals[i][0];
    int end = intervals[i][1];
    mp[start] = Math.max(end + 1, mp[start]);
}
```

**Pros:** Clever, avoids sorting.
**Cons:** Needs large auxiliary array (`max+1` size). Bad if intervals range is large (e.g. up to 10⁹).
Works well only when input values are **small bounded integers**.

---

## 🔎 Example Walkthrough

Input:

```
[[1,3],[2,6],[8,10],[15,18]]
```

Sorted:

```
[[1,3],[2,6],[8,10],[15,18]]
```

Step by step:

* Start = [1,3]
* Compare with [2,6] → overlap → merge → [1,6]
* Compare with [8,10] → no overlap → add [1,6], continue
* Compare with [15,18] → no overlap → add [8,10], add [15,18]

Output:

```
[[1,6],[8,10],[15,18]]
```

---

## ✅ Key Takeaways

* **Brute Force**: O(n²), too slow.
* **Sorting + Greedy**: O(n log n), optimal & widely used.
* **Mapping-based approach**: O(n + maxVal), but impractical unless interval values are small.

For interviews/contests, **sorting + greedy merge is the standard solution**.

---
