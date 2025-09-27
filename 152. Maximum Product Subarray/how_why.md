# How_Why.md – Maximum Product Subarray (152)

## ✅ Problem Recap

Given an integer array `nums`, find the **contiguous subarray** (containing at least one number) which has the **largest product**.

* Input: `nums = [2,3,-2,4]`
* Output: `6` (subarray `[2,3]`)

---

## Approach: Prefix & Suffix Products

### Key Insight

* Negative numbers can **flip the sign** of a product.
* To handle negatives:

  1. Track **prefix product** (from left)
  2. Track **suffix product** (from right)
* Reset product to `1` if zero is encountered (to start a new subarray).

---

### Step 1: Initialize variables

```java
int l = 1; // left product
int r = 1; // right product
int ans = nums[0];
```

* `l` → prefix product from left
* `r` → prefix product from right
* `ans` → stores maximum product so far

---

### Step 2: Iterate through array

```java
for (int i = 0; i < n; i++) {
    l = l == 0 ? 1 : l;
    r = r == 0 ? 1 : r;
    l *= nums[i];
    r *= nums[n-1-i];
    ans = Math.max(ans, Math.max(l, r));
}
```

* Reset `l` or `r` to `1` if zero is encountered (because zero breaks the product).
* Update `l` by multiplying current number from left.
* Update `r` by multiplying current number from right.
* Update `ans` with the maximum of `ans`, `l`, and `r`.

---

### Step 3: Example Walkthrough

#### Input

```
nums = [2,3,-2,4]
```

#### Iteration

| i | l (prefix) | r (suffix) | ans |
| - | ---------- | ---------- | --- |
| 0 | 2          | 4          | 4   |
| 1 | 6          | -2         | 6   |
| 2 | -12        | 3          | 6   |
| 3 | -48        | 2          | 6   |

* Maximum product = `6` → from subarray `[2,3]`

---

### Step 4: Why Prefix + Suffix Works

* Negative numbers may reduce a prefix product.
* A large product might **start after the first negative**.
* By checking **from both ends**, we ensure all subarrays are considered.

---

### Complexity

| Metric | Complexity |
| ------ | ---------- |
| Time   | O(n)       |
| Space  | O(1)       |

* Only one pass from left to right and one from right to left (simultaneously).

---

This method is elegant because it **avoids tracking max/min at each step** explicitly while still accounting for negative numbers.

---
