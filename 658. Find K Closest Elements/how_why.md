# How_Why.md – Find K Closest Elements (658)

## ✅ Problem Recap

Given:

* A **sorted array** `arr`
* Integers `k` and `x`

Return the `k` integers in `arr` **closest to `x`**, sorted in ascending order.

* If two numbers are equally close, return the **smaller number** first.

---

## Approach: Binary Search + Sliding Window

### Idea

* The result is a **window of size `k`** in `arr`.
* Use **binary search** to find the **left boundary** of this window.
* Shift window left/right depending on which end is closer to `x`.

---

### Example Walkthrough

#### Input

```
arr = [1, 2, 3, 4, 5]
k = 4
x = 3
```

#### Step 1: Initialize binary search

```
left = 0
right = arr.length - k = 5 - 4 = 1
```

We are searching for `left` index where the window `[left, left + k - 1]` has the closest numbers.

---

#### Step 2: Binary search iterations

**Iteration 1:**

```
mid = left + (right - left) / 2 = 0
arr[mid] = 1
arr[mid + k] = arr[4] = 5
```

Compare distances to x:

```
x - arr[mid] = 3 - 1 = 2
arr[mid+k] - x = 5 - 3 = 2
```

* `2 > 2`? No → move `right = mid = 0`

---

#### Step 3: Left boundary found

```
left = 0
```

* Window: `arr[0..3] = [1, 2, 3, 4]` ✅
* Closest 4 numbers to `3` are `[1, 2, 3, 4]`

---

### Output

```
[1, 2, 3, 4]
```

---

## Complexity

| Metric | Complexity                      |
| ------ | ------------------------------- |
| Time   | O(log(n - k) + k)               |
| Space  | O(1) (not counting result list) |

* Binary search over `arr.length - k` positions → O(log(n-k))
* Copy k elements to result → O(k)

---

## Alternate Approach: Min-Heap

* Use a **priority queue** of size k:

  * Compare absolute distance to x.
  * Keep the k closest numbers.
* Simple but **slower** → O(n log k) time.
* Final output may need **sorting** → extra O(k log k).

---
