# How & Why: LeetCode 9 - Palindrome Number

---

## Problem Restatement
We are given an integer `x`. We need to determine whether it is a **palindrome number**. A palindrome number reads the same forward and backward.

- Example: `121` → palindrome.
- Example: `-121` → not a palindrome (negative sign breaks symmetry).

---

## How to Solve

### Step 1: Handle Negative Numbers
If `x < 0`, immediately return `false` because negative numbers cannot be palindromes due to the `-` sign.

```java
if (x < 0) return false;
```

### Step 2: Reverse the Number
We reverse the integer:
- Start with `rev = 0`.
- Extract digits one by one from `n = x` using `% 10`.
- Append to `rev` by multiplying current `rev` by 10 and adding the digit.
- Remove the digit from `n` by dividing by 10.

```java
int rev = 0;
int n = x;
while (n > 0) {
    rev = rev * 10 + n % 10;
    n /= 10;
}
```

### Step 3: Compare Original and Reversed
If the reversed number equals the original number, then it is a palindrome.

```java
return rev == x;
```

---

## Why This Works
- Reversing the number ensures we can directly check symmetry.
- If `rev == x`, then the digits of `x` are the same forward and backward.

---

## Complexity Analysis
- **Time Complexity**: O(log₁₀(x)) → proportional to the number of digits in `x`.
- **Space Complexity**: O(1), constant extra space.

---

## Example Walkthrough
Input:
```
x = 121
```

Process:
- Reverse 121 → `121`.
- Compare with original → `121 == 121` → true.

Output:
```
true
```

Input:
```
x = -121
```

Process:
- Negative → return false.

Output:
```
false
```

---

## Alternate Approaches

1. **String Conversion**
   - Convert integer to string, check if string is a palindrome.
   - Simple but uses extra space.
   - Complexity: O(n) time, O(n) space.

2. **Half Reversal Optimization**
   - Instead of reversing the entire number, reverse only half of it.
   - Stop when `rev >= n`.
   - Compare either exact halves or account for odd digit counts.
   - More efficient and avoids potential integer overflow.

### Optimal Choice
For simplicity, the **full reversal method** is fine and efficient within integer limits.
For larger numbers or stricter memory constraints, the **half reversal method** is more optimal.

---

## Key Insight
The palindrome property relies on **digit symmetry**. Reversing the number (or half of it) gives a direct way to check this symmetry.

