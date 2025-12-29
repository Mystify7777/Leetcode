# 367. Valid Perfect Square - Explanation

## Problem Understanding

Given a positive integer `num`, return `true` if `num` is a perfect square, otherwise return `false`.

**Perfect Square**: An integer that is the square of another integer.

- Examples: 1 (1²), 4 (2²), 9 (3²), 16 (4²), 25 (5²), etc.

**Constraint**: You must **not use** any built-in library functions like `sqrt()`.

**Examples:**

```java
Input: num = 16
Output: true (4 × 4 = 16)

Input: num = 14
Output: false (no integer n where n² = 14)
```

## Approach: Newton's Method (Babylonian Method)

The solution uses **Newton's Method** for finding square roots, which is an iterative algorithm that converges quickly to the square root.

### Mathematical Foundation

Newton's method for finding the square root of `x` uses the formula:

$$r_{n+1} = \frac{1}{2}(r_n + \frac{x}{r_n})$$

Or equivalently:

$$r_{n+1} = \frac{r_n + \frac{x}{r_n}}{2}$$

**How it works:**

- Start with an initial guess `r` (in this case, `r = x`)
- If `r² > x`, then `r` is too large
- Compute a better approximation using the formula above
- Repeat until `r² ≤ x`

### Why This Works

This is a special case of Newton-Raphson method for finding roots of equations.

For finding $\sqrt{x}$, we want to solve: $f(r) = r^2 - x = 0$

Newton's formula: $r_{n+1} = r_n - \frac{f(r_n)}{f'(r_n)}$

Applying this:

- $f(r) = r^2 - x$
- $f'(r) = 2r$
- $r_{n+1} = r - \frac{r^2 - x}{2r} = r - \frac{r}{2} + \frac{x}{2r} = \frac{r + \frac{x}{r}}{2}$

The method **converges quadratically**, meaning the number of correct digits roughly doubles with each iteration.

## Code Walkthrough

```java
public boolean isPerfectSquare(int x) {
    long r = x;  // Start with initial guess (use long to prevent overflow)
    
    // Iterate until r² ≤ x (r converges to floor(sqrt(x)))
    while (r * r > x) {
        r = (r + x/r) / 2;  // Newton's method update formula
    }
    
    // Check if r is exactly the square root
    return r * r == x;
}
```

### Key Implementation Details

1. **Use `long` instead of `int`**:
   - When computing `r * r`, the result might exceed `Integer.MAX_VALUE`
   - Using `long` prevents overflow issues

2. **Initial guess `r = x`**:
   - Simple starting point
   - For any `x ≥ 1`, we have `√x ≤ x`, so `r = x` is always an overestimate

3. **Loop condition `r * r > x`**:
   - Continue while our guess is too large
   - When loop exits, `r² ≤ x`, meaning `r ≤ √x`

4. **Integer division `x/r`**:
   - Uses integer division (truncates toward zero)
   - Combined with the averaging formula, this ensures convergence to the floor of the square root

5. **Final check `r * r == x`**:
   - If `r² = x`, then `r = √x` exactly → perfect square
   - If `r² < x`, then `√x` is not an integer → not a perfect square

## Example Trace

### Example 1: num = 16 (perfect square)

```math
Initial: r = 16, target = 16

Iteration 1:
  r² = 16×16 = 256 > 16
  r = (16 + 16/16) / 2 = (16 + 1) / 2 = 8

Iteration 2:
  r² = 8×8 = 64 > 16
  r = (8 + 16/8) / 2 = (8 + 2) / 2 = 5

Iteration 3:
  r² = 5×5 = 25 > 16
  r = (5 + 16/5) / 2 = (5 + 3) / 2 = 4

Iteration 4:
  r² = 4×4 = 16 ≤ 16 (loop exits)

Final check: 4² = 16 ✓
Return: true
```

### Example 2: num = 14 (not a perfect square)

```math
Initial: r = 14, target = 14

Iteration 1:
  r² = 14×14 = 196 > 14
  r = (14 + 14/14) / 2 = (14 + 1) / 2 = 7

Iteration 2:
  r² = 7×7 = 49 > 14
  r = (7 + 14/7) / 2 = (7 + 2) / 2 = 4

Iteration 3:
  r² = 4×4 = 16 > 14
  r = (4 + 14/4) / 2 = (4 + 3) / 2 = 3

Iteration 4:
  r² = 3×3 = 9 ≤ 14 (loop exits)

Final check: 3² = 9 ≠ 14 ✗
Return: false
```

## Complexity Analysis

**Time Complexity**: O(log log N)

- Newton's method has quadratic convergence
- Number of iterations grows very slowly (logarithmically with respect to the number of digits)
- For practical purposes, converges in ~5-6 iterations even for very large numbers
- Much faster than binary search which is O(log N)

**Space Complexity**: O(1)

- Only uses a single variable `r`
- No additional data structures

## Alternative Approaches

### 1. Binary Search: O(log N)

```java
public boolean isPerfectSquare(int num) {
    long left = 1, right = num;
    
    while (left <= right) {
        long mid = left + (right - left) / 2;
        long square = mid * mid;
        
        if (square == num) return true;
        else if (square < num) left = mid + 1;
        else right = mid - 1;
    }
    
    return false;
}
```

**Pros**: Easier to understand, predictable performance
**Cons**: Slower than Newton's method (O(log N) vs O(log log N))

### 2. Mathematical Property (Odd Numbers Sum): O(√N)

Perfect squares are sums of consecutive odd numbers:

- 1 = 1
- 4 = 1 + 3
- 9 = 1 + 3 + 5
- 16 = 1 + 3 + 5 + 7

```java
public boolean isPerfectSquare(int num) {
    int i = 1;
    while (num > 0) {
        num -= i;
        i += 2;
    }
    return num == 0;
}
```

**Pros**: Simple, clever
**Cons**: O(√N) time - slow for large numbers

### 3. Linear Search: O(√N)

```java
public boolean isPerfectSquare(int num) {
    for (long i = 1; i * i <= num; i++) {
        if (i * i == num) return true;
    }
    return false;
}
```

**Cons**: Too slow for large inputs

## Comparison of Methods

| Method | Time Complexity | Space | Intuition | Speed for N=10⁹ |
| -------- | ---------------- | ------- | ----------- | ----------------- |
| Newton's Method | O(log log N) | O(1) | Iterative approximation | ~5 iterations |
| Binary Search | O(log N) | O(1) | Divide and conquer | ~30 iterations |
| Odd Sum | O(√N) | O(1) | Mathematical property | ~31,623 iterations |
| Linear Search | O(√N) | O(1) | Brute force | ~31,623 iterations |

## Key Insights

1. **Newton's Method Superiority**: For square root problems, Newton's method is typically the fastest iterative approach

2. **Overflow Prevention**: Using `long` is crucial - many candidates forget this and get wrong answers for large inputs

3. **Integer vs Float**: We work entirely in integer arithmetic, avoiding floating-point precision issues

4. **Convergence Speed**: The method converges extremely fast - typically only 5-6 iterations even for `Integer.MAX_VALUE`

5. **Floor Property**: The algorithm naturally converges to `floor(√x)`, which is exactly what we need

## Edge Cases

- **num = 1**: Returns `true` (1 is a perfect square)
- **num = 2**: Returns `false`
- **Large perfect square** (e.g., 2147395600 = 46340²): Returns `true`
- **Integer.MAX_VALUE** (2147483647): Returns `false` (not a perfect square)
- **Powers of 2 that are perfect squares** (1, 4, 16, 64, etc.): All return `true`

## Why Not Use Built-in sqrt()?

The problem explicitly forbids it, but even if allowed:

- `Math.sqrt()` uses floating-point arithmetic
- Floating-point has precision issues for large integers
- Integer-based methods are more reliable for this specific problem

## Connection to Other Problems

This technique (Newton's method) is useful for:

- **Sqrt(x)** - LeetCode 69 (find integer square root)
- **Pow(x, n)** - Related exponentiation problems
- **Any root-finding problem** where iterative refinement is needed

Newton's method is a fundamental algorithm in numerical computing and appears in many optimization and computational geometry problems.
