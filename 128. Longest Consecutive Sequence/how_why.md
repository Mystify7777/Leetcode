
# How\_Why.md – Longest Consecutive Sequence (LeetCode 128)

## Problem

Given an unsorted array `nums`, return the length of the **longest consecutive elements sequence**.

⚠️ The catch:

* Sequence must be consecutive integers (increasing by 1).
* Must run in **O(n)** time ideally.

---

## Brute Force Approach

### Idea

* For every element, try to check if `x+1, x+2, ...` exist by scanning the array each time.
* Keep track of the longest streak.

### Complexity

* Time = **O(n²)** (for each element, scanning the whole array).
* Space = **O(1)**.

### Example

```java
nums = [100, 4, 200, 1, 3, 2]

Pick 100 → no streak.  
Pick 4 → see if 5 exists? No → streak=1.  
Pick 200 → no streak.  
Pick 1 → check 2,3,4 → streak=4.  
Pick 3 → already part of streak.  

Answer = 4.
```

✅ Works but far too slow when `n` is large.

---

## Your Approach (Sorting)

### Idea_

1. Sort the array.
2. Walk through the sorted numbers.

   * If consecutive (`nums[i+1] == nums[i]+1`) → extend streak.
   * If duplicate → skip.
   * Else reset streak.
3. Track the maximum streak length.

### Complexity_

* Time = **O(n log n)** (sorting dominates).
* Space = **O(1)** (in-place) or **O(log n)** (sort recursion).

### Example Walkthrough_

```java
nums = [100, 4, 200, 1, 3, 2]

After sort → [1, 2, 3, 4, 100, 200]

Walk:
1→2→3→4 → streak=4
break at 100 → reset
100→200 → no streak

Answer = 4
```

✅ Much better than brute force, but sorting prevents **O(n)** performance.

---

## Optimized Approach (HashSet)

### Idea__

* Put all numbers in a HashSet → O(1) lookup.
* Only start counting a streak if the current number is a "sequence start" (i.e., `num-1` not in set).
* From that number, keep checking `num+1, num+2...`.

### Complexity__

* Time = **O(n)** (each number visited at most once).
* Space = **O(n)** (HashSet).

### Example Walkthrough__

```java
nums = [100, 4, 200, 1, 3, 2]
Set = {100, 4, 200, 1, 3, 2}

Check 100 → start (99 not in set) → streak=1
Check 4 → not start (3 exists) → skip
Check 200 → start (199 not in set) → streak=1
Check 1 → start (0 not in set) → streak=4 (1,2,3,4)

Answer = 4
```

✅ Single pass, true **O(n)**, best approach.

---

## Conclusion

* **Brute force** → O(n²), too slow.
* **Sorting** (your method) → O(n log n), simple and works fine.
* **HashSet-based** → O(n), optimal and passes large test cases.

👉 If interview expects "optimal", always go for **HashSet approach**. But your sorted version is still elegant and easy to explain.

---
