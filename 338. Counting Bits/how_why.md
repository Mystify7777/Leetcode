# How & Why – 338. Counting Bits

## Problem

Given a number `num`, return an array `ans` of length `num + 1` where `ans[i]` is the number of **1’s** in the binary representation of `i`.

---

## 1. Brute Force Approach

**Idea:**
For every integer `i` from `0` to `num`, count the number of `1`s in its binary form.

**Pseudocode:**

```java
for i in [0..num]:
    ans[i] = countOnes(i)   // repeatedly check bits or use builtin function
```

**Walkthrough (num = 5):**

* `0 -> 000 -> 0 ones`
* `1 -> 001 -> 1 one`
* `2 -> 010 -> 1 one`
* `3 -> 011 -> 2 ones`
* `4 -> 100 -> 1 one`
* `5 -> 101 -> 2 ones`

Result → `[0,1,1,2,1,2]`.

**Complexity:**

* Time: O(n · log n) (since counting bits per number can take up to log n steps).
* Space: O(n).

---

## 2. The Given Approach (DP + Bit Manipulation)

**Key observation:**
The number of `1`s in `i` can be built from a smaller number:

```java
f[i] = f[i >> 1] + (i & 1)
```

* `i >> 1`: drops the last bit, whose 1-count is already known.
* `i & 1`: tells if the last bit is `1` (odd/even check).

**Algorithm:**

1. Initialize `f[0] = 0`.
2. For each `i` in `[1..num]`, compute `f[i]` using the formula above.
3. Return array `f`.

**Walkthrough (num = 5):**

* `f[0] = 0`
* `f[1] = f[0] + 1 = 1`
* `f[2] = f[1] + 0 = 1`
* `f[3] = f[1] + 1 = 2`
* `f[4] = f[2] + 0 = 1`
* `f[5] = f[2] + 1 = 2`

Result → `[0,1,1,2,1,2]`.

**Complexity:**

* Time: O(n)
* Space: O(n)

---

## 3. Optimized Version (Same Time, Smaller Memory)

If only streaming output is needed (not storing everything):

* Compute each `f[i]` on the fly and print/append it.
* This reduces **space** to O(1) (besides output storage).

Also, if a **built-in bit counter** exists (like `Integer.bitCount()` in Java), the brute force can be simplified, but time complexity stays O(n·log n).

---

✅ **Final Takeaway:**
The DP recurrence `f[i] = f[i >> 1] + (i & 1)` reuses previously computed values, turning an O(n·log n) solution into O(n). That’s why this problem is a classic example of **DP with bit manipulation**.

---
