
# How_Why.md – Find Triangular Sum of an Array (LeetCode 2221)

## ❌ Brute Force Idea

We’re asked to repeatedly reduce an array until only **one number remains**, where each new row is built as:

```
a[i] = (a[i] + a[i+1]) % 10
```

**Naïve approach:**

* Keep modifying the array until only one element remains.
* Example:

  ```
  nums = [1,2,3,4,5]
  → [3,5,7,9] → [8,2,6] → [0,8] → [8]
  ```
* Return `8`.

**Drawback:**

* Each step reduces size by 1, so time = O(n²).
* Works fine for small `n` (≤1000), but **inefficient for large arrays**.

---

## ✅ Approach 1 – Recursive / Iterative Simulation

Your first solution:

```java
public int triangularSum(int[] nums) {
    return find(nums, nums.length);
}

private int find(int[] a, int n) {
    if (n == 1) return a[0];
    for (int i = 0; i < n - 1; i++) {
        a[i] = (a[i] + a[i + 1]) % 10;
    }
    return find(a, n - 1);
}
```

* At each recursive call, shrink the array size by 1.
* Base case: length = 1 → return element.
* Complexity = **O(n²)** (same as brute force), but elegant recursion.
* Memory: **O(1)** (in-place), plus recursion stack.

Good for `n ≤ 1000`.

---

## 🚀 Approach 2 – Binomial Theorem + Modular Arithmetic

The second solution exploits math:

Observation:
The final number is essentially:

```
ans = Σ ( C(n-1, i) * nums[i] ) mod 10
```

where `C(n-1, i)` is the binomial coefficient.

Why?
Because each reduction step is like expanding `(nums[0] + nums[1] + …)` with coefficients following Pascal’s triangle.

### Challenge

We need `C(n-1, i) mod 10`.
But `10 = 2 × 5`, so we can use **Chinese Remainder Theorem**:

1. Compute `C(n-1, i) mod 2` → easy using bitwise (`(i & (n-1-i)) == 0`).
2. Compute `C(n-1, i) mod 5` → use **Lucas theorem mod 5**.
3. Combine to get result mod 10.

### Complexity

* **O(n log n)** (due to digit decomposition in Lucas theorem).
* Memory: **O(1)**.
* Can handle large `n` (up to 10⁶).

---

## 🔎 Example Walkthrough

`nums = [1,2,3,4,5]`

### Brute Force

```
[1,2,3,4,5]
→ [3,5,7,9]
→ [8,2,6]
→ [0,8]
→ [8]
Answer = 8
```

### Binomial Method

`n = 5 → N = 4`
Final = Σ C(4,i) * nums[i] mod 10

```
= 1*1 + 4*2 + 6*3 + 4*4 + 1*5
= 1 + 8 + 18 + 16 + 5 = 48
48 mod 10 = 8
```

Matches.

---

## ✅ Key Takeaways

* The problem reduces to **binomial expansion modulo 10**.
* **Brute force**: simple O(n²).
* **Optimized (Lucas + CRT)**: O(n log n), works for large `n`.
* In practice, since LeetCode constraints are small (`n ≤ 1000`), the recursive/brute force is enough.

---

Would you like me to also write the **optimized CRT-based solution** in a cleaner, contest-ready Java form (without the extra reference code and debugging)?
