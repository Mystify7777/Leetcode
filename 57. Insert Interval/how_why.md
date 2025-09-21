
# Problem: Insert Interval (LeetCode 57)

We are given a set of non-overlapping intervals sorted by their start times and a new interval. We need to insert the new interval into the set, merging overlapping intervals if necessary.

---

## 🔹 Brute Force Approach

**Idea**:

1. Add the new interval to the list of intervals.
2. Sort all intervals based on their start times.
3. Merge overlapping intervals one by one.

**Steps**:

* Append `newInterval` to the intervals list.
* Sort by start time.
* Use a stack or list to merge overlapping intervals.

**Complexity**:

* Sorting takes **O(n log n)**.
* Merging takes **O(n)**.
* Overall → **O(n log n)**.

✅ Simple to implement.
❌ Sorting adds unnecessary overhead since the original list is already sorted.

**Example**:
Intervals: `[[1,3], [6,9]]`, newInterval: `[2,5]`

* Add new interval → `[[1,3], [6,9], [2,5]]`
* Sort → `[[1,3], [2,5], [6,9]]`
* Merge → `[[1,5], [6,9]]`

---

## 🔹 Your Approach (Efficient Merge without Sorting)

**Idea**:
Since intervals are already sorted:

1. Add all intervals that end **before** newInterval starts.
2. Merge overlapping intervals with newInterval.
3. Add remaining intervals after newInterval.

**Steps**:

* Traverse intervals.
* If interval ends before newInterval starts → add to result.
* If interval overlaps → merge by updating `newInterval`.
* Insert final merged newInterval.
* Add all remaining intervals.

**Complexity**:

* Only a single pass: **O(n)**.
* No extra sorting.

**Example**:
Intervals: `[[1,3], [6,9]]`, newInterval: `[2,5]`

* First, `[1,3]` overlaps with `[2,5]` → merge into `[1,5]`
* Then `[6,9]` stays as is.
* Final → `[[1,5], [6,9]]`

---

## 🔹 Optimized Version (Same as Yours)

Actually, your method is already the optimal approach:

* Single traversal.
* Constant merging updates.
* **O(n)** time, **O(n)** space (for result).

Alternative micro-optimizations (not big improvements):

* Use an in-place list modification instead of creating a new list.
* If memory matters, return slices instead of copying.

---

✅ **Final Takeaway**:

* **Brute force**: Insert + sort + merge → **O(n log n)**.
* **Optimized** (your method): Direct merge using sorted property → **O(n)**.

---
