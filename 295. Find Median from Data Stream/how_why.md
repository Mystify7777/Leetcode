# How\_Why.md — Find Median from Data Stream (LeetCode 295)

---

## ❌ Brute Force (Baseline)

### Idea

* Store all incoming numbers in a list.
* Each time `findMedian()` is called:

  * Sort the list.
  * If odd length → return middle element.
  * If even length → return average of two middle elements.

### Example

Insert `[5,3,8]` → sort → `[3,5,8]` → median = 5.

### Limitation

* Sorting every time is **O(n log n)**.
* If `findMedian()` is called frequently, this is inefficient.

---

## ⚡ Approach 1: Two Heaps (Balanced Min-Heap + Max-Heap) ✅ (your first code)

### Idea

* Use two heaps to keep the data balanced:

  * `small` (max-heap): lower half of numbers.
  * `large` (min-heap): upper half of numbers.
* Insertion keeps heaps balanced so size difference ≤ 1.
* Median:

  * If even total → average of tops.
  * If odd total → top of max-heap.

### Example

Insert `1`: small = \[1], large = \[]. Median = 1.
Insert `2`: small = \[1], large = \[2]. Median = (1+2)/2 = 1.5.
Insert `3`: small = \[2,1], large = \[3]. Median = 2.

### Complexity

* `addNum`: O(log n) (heap push/pop).
* `findMedian`: O(1).

### Notes

* Elegant and optimal.
* Your version toggles with a `boolean even` flag to keep balance cleanly.

---

## ⚡ Approach 2: Two Heaps with Explicit Rebalancing ✅ (your second code)

### Idea

* Same as above but more verbose.
* After inserting, rebalance heaps so that:

  * Either sizes are equal, or
  * Max-heap has one more element.
* Median is taken directly from heap tops.

### Example

Insert `5`: max = \[5].
Insert `10`: max = \[5], min = \[10]. Median = (5+10)/2 = 7.5.
Insert `1`: move elements to rebalance → max = \[5,1], min = \[10]. Median = 5.

### Complexity

* Same as above.
* Slightly more bookkeeping, but equivalent in efficiency.

---

## ⚡ Approach 3: Counting Sort Trick with Hash Array 🚀 (the last code you didn’t get)

### Idea

* Since problem constraints are limited (say values in `[-100000,100000]`), use a **counting array** `hashVals` of size `200001`.
* Keep track of `median` value and a counter `medianCount2x` representing how many elements have been seen around the median.
* Insertions just bump counts in O(1).
* Median update moves left or right depending on new element and balance.

### Example

Insert `5`: median = 5.
Insert `7`: shift right → median = 5 (count tracking ensures balance).
Insert `3`: shift left → median = 5 or average of (3,5), depending on parity.

### Complexity

* `addNum`: O(1).
* `findMedian`: O(1) amortized.
* But uses **O(K)** memory (K = range of possible numbers, here 200001).

### Notes

* Extremely fast for fixed numeric range.
* Not general-purpose — if input numbers were very large or unbounded, it wouldn’t work.

---

## 🏆 Summary

* **Brute Force (sort each time):** O(n log n), too slow.
* **Two Heaps (Approach 1 & 2):** Best general solution, O(log n) per insertion, O(1) median.
* **Counting Sort (Approach 3):** O(1) operations but huge memory cost, only works if number range is bounded.

👉 For interviews or general purpose: **Two Heaps** is the correct choice.
👉 For competitive programming with bounded input: **Counting Sort trick** is a clever optimization.

---
