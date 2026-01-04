# How\_Why.md: Four Divisors

## Problem

Given an integer array `nums`, return the sum of all numbers that have **exactly 4 divisors**.

A number has exactly 4 divisors in these two cases:

1. **Case 1:** $p^3$ where $p$ is a prime number (divisors: 1, p, p², p³)
2. **Case 2:** $p \times q$ where $p$ and $q$ are distinct prime numbers (divisors: 1, p, q, p×q)

**Example:**

```s
Input: nums = [21,4,7]
Output: 32
Explanation:
- 21 = 3 × 7 (divisors: 1, 3, 7, 21) → 1 + 3 + 7 + 21 = 32
- 4 = 2² (divisors: 1, 2, 4) → only 3 divisors
- 7 = 7 (divisors: 1, 7) → only 2 divisors
```

---

## Brute-force Approach

### Idea

* For each number, count all divisors by checking every integer from 1 to n.
* If divisor count equals 4, add the sum of divisors.

### Code

```java
public int sumFourDivisorsBF(int[] nums) {
    int totalSum = 0;
    
    for (int num : nums) {
        int divisorSum = 0;
        int divisorCount = 0;
        
        // Count all divisors
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                divisorCount++;
                divisorSum += i;
            }
        }
        
        if (divisorCount == 4) {
            totalSum += divisorSum;
        }
    }
    
    return totalSum;
}
```

### Limitation

* **Time complexity:** O(n × m) where m is the maximum number in array.
* Extremely slow for large numbers (checking up to 10⁵ divisors per number).

---

## Optimized Approach (Prime Factorization)

### Idea*

1. **Understand the structure:** A number has exactly 4 divisors only in two cases:
   - **Case 1:** $p^3$ (cube of a prime)
   - **Case 2:** $p \times q$ (product of two distinct primes)

2. **Case 1 Check:** Cube Root
   - Find the cube root of the number.
   - Check if it's a perfect cube of a prime.
   - If $p^3 = n$ and $p$ is prime, divisors are: 1, p, p², p³
   - Sum = $1 + p + p^2 + p^3$

3. **Case 2 Check:** Prime Factors
   - Find the smallest divisor in range [2, √n].
   - Check if both divisor and quotient are prime and distinct.
   - If $p \times q = n$ with $p \neq q$ and both prime, divisors are: 1, p, q, n
   - Sum = $1 + p + q + n$

### Code*

```java
public int sumFourDivisors(int[] nums) {
    int res = 0;
    
    for (int num : nums) {
        int sum = sumOne(num);
        if (sum != -1) {
            res += sum;
        }
    }
    
    return res;
}

private int sumOne(int n) {
    // Case 1: p^3 (cube of a prime)
    int p = (int) Math.round(Math.cbrt(n));
    if ((long) p * p * p == n && isPrime(p)) {
        return 1 + p + p * p + p * p * p;
    }
    
    // Case 2: p * q (product of two distinct primes)
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) {
            int a = i;
            int b = n / i;
            // Check if both are prime and distinct
            if (a != b && isPrime(a) && isPrime(b)) {
                return 1 + a + b + n;
            }
            return -1; // Found a factor but not a valid case
        }
    }
    
    return -1; // No valid case
}

private boolean isPrime(int x) {
    if (x < 2) return false;
    for (int i = 2; i * i <= x; i++) {
        if (x % i == 0) return false;
    }
    return true;
}
```

### Example Walkthrough

**Example 1:** $n = 21$

1. **Check Case 1:**
   - $\sqrt[3]{21} \approx 2.76$ → Not a perfect cube

2. **Check Case 2:**
   - $i = 2$: $21 \mod 2 \neq 0$
   - $i = 3$: $21 \mod 3 = 0$ ✓
     - $a = 3, b = 7$
     - $a \neq b$ ✓
     - `isPrime(3)` ✓
     - `isPrime(7)` ✓
   - **Result:** $1 + 3 + 7 + 21 = 32$

**Example 2:** $n = 8$

1. **Check Case 1:**
   - $\sqrt[3]{8} = 2$ ✓
   - $2 \times 2 \times 2 = 8$ ✓
   - `isPrime(2)` ✓
   - **Result:** $1 + 2 + 4 + 8 = 15$

**Example 3:** $n = 4$

1. **Check Case 1:**
   - $\sqrt[3]{4} \approx 1.59$ → Not a perfect cube

2. **Check Case 2:**
   - $i = 2$: $4 \mod 2 = 0$ ✓
     - $a = 2, b = 2$
     - $a = b$ ✗ (not distinct)
   - **Return:** $-1$ (not a valid case)

### Time Complexity Analysis

* **For Case 1:** O(1) - just compute cube root
* **For Case 2:** O(√n × √p) where p is a potential prime
  - Check divisors up to √n: O(√n)
  - Check if prime: O(√p) where p ≤ √n
* **Overall:** O(n × √m × √√m) where n is array length, m is max number
  - Much faster than O(n × m) brute force!

### Space Complexity

* O(1) - only using constant extra space

---

## Why This Approach Works

1. **Mathematical guarantee:** A number has exactly 4 divisors **only** in the two cases we check.
   - No other factorization pattern gives exactly 4 divisors.

2. **Early termination:**
   - If Case 1 fails, we move to Case 2.
   - If we find any divisor in Case 2 that fails the prime check, we return immediately.

3. **Optimization:** Instead of checking all divisors, we only check:
   - One number (cube root) for Case 1
   - Numbers up to √n for Case 2

---

## Key Insights

1. **Mathematical structure:** Understanding the factorization patterns leads to an optimal solution.
2. **Prime checking:** Efficient primality testing is crucial (O(√p)).
3. **Early exit:** Returning -1 when conditions fail avoids unnecessary computation.
4. **Cube root handling:** Use `Math.round()` to handle floating-point precision issues.
