# How_Why.md

## Problem
We need to determine whether a given integer `n` is a **power of two**.  
Formally, return `true` if there exists an integer `x` such that `n == 2^x`.

---

## How (Step-by-step Solution)

### Approach: Bit Manipulation
1. A power of two in binary always has **exactly one bit set**.  
   - Example:  
     - `1` → `0001`  
     - `2` → `0010`  
     - `4` → `0100`  
     - `8` → `1000`  

2. The trick:  
   - For any power of two `n`, `n - 1` will flip all the bits after that set bit.  
   - Performing `n & (n - 1)` will always yield `0` if `n` is a power of two.

   Example:  
   - `n = 8 (1000)`  
   - `n - 1 = 7 (0111)`  
   - `n & (n - 1) = 1000 & 0111 = 0000`

3. Add a guard condition:  
   - Ensure `n > 0` because negative numbers and `0` are not powers of two.

---

## Why (Reasoning)

- Checking `(n & (n - 1)) == 0` exploits binary properties of powers of two.  
- This is more efficient than repeatedly dividing by 2 or looping.

---

## Complexity Analysis
- **Time Complexity**: `O(1)` → constant-time bitwise operation.  
- **Space Complexity**: `O(1)` → no extra memory used.

---

## Example Walkthrough

Input: `n = 16`  
- Binary: `10000`  
- `n - 1 = 15 (01111)`  
- `10000 & 01111 = 00000` → returns `true`.

Input: `n = 18`  
- Binary: `10010`  
- `n - 1 = 17 (10001)`  
- `10010 & 10001 = 10000` (not zero) → returns `false`.

---

## Alternate Approaches
1. **Division Method**:
   - While `n % 2 == 0`, divide `n` by 2.
   - At the end, check if `n == 1`.
   - Less efficient for very large numbers.

2. **Logarithm Method**:
   - Compute `log2(n)` and check if it is an integer.
   - Risky due to floating-point precision errors.

✅ **Optimal method** is the **bitwise check** `(n > 0 && (n & (n - 1)) == 0)`.
