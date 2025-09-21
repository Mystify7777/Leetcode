
# How\_Why.md – Sort Colors (LeetCode 75)

## Problem

We’re given an array `nums` containing only **0s, 1s, and 2s**.
We need to sort them **in-place** so that:

```c
all 0s → first
all 1s → middle
all 2s → last
```

without using the built-in sort.

---

## Brute Force Approach

### Idea

* Since there are only **3 distinct values**, the simplest brute force is:

  1. Count how many 0s, 1s, 2s there are.
  2. Overwrite the array with the correct number of each.

### Complexity

* Time = **O(n)** (two passes).
* Space = **O(1)**.

### Example

```java
Input: [2,0,2,1,1,0]
Count → {0:2, 1:2, 2:2}
Rebuild → [0,0,1,1,2,2]
```

✅ Works fine but does **two passes** through the array.

---

## Your Approach (Dutch National Flag Algorithm)

### Idea_

* Use **three pointers**:

  * `low` → position where next `0` should go.
  * `mid` → current index being checked.
  * `high` → position where next `2` should go.

* Rules:

  1. If `nums[mid] == 0`: swap with `low`, move both forward.
  2. If `nums[mid] == 1`: correct place → just move `mid`.
  3. If `nums[mid] == 2`: swap with `high`, move `high` backward (but keep `mid` to re-check).

### Complexity_

* Time = **O(n)** (single pass).
* Space = **O(1)**.

### Example Walkthrough_

```java
Input: [2,0,2,1,1,0]
Initial: low=0, mid=0, high=5
Step 1: nums[mid]=2 → swap mid,high → [0,0,2,1,1,2], high=4
Step 2: nums[mid]=0 → swap mid,low → [0,0,2,1,1,2], low=1, mid=1
Step 3: nums[mid]=0 → swap mid,low → [0,0,2,1,1,2], low=2, mid=2
Step 4: nums[mid]=2 → swap mid,high → [0,0,1,1,2,2], high=3
Step 5: nums[mid]=1 → mid=3
Step 6: nums[mid]=1 → mid=4 > high → stop
Output: [0,0,1,1,2,2]
```

✅ Sorted in **one pass**, in-place.

---

## Most Optimized Method

👉 The **Dutch National Flag algorithm** (your code) is the **optimal solution**:

* One pass through the array
* Constant space
* Elegant pointer-based swapping

The brute force counting sort also works but is less efficient in practice (2 passes).

---
