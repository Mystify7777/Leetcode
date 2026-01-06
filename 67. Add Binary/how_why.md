# How & Why — LeetCode 67: Add Binary

Add two binary strings and return their sum as a binary string. Treat the inputs as binary numbers (without converting them to built-in integer types to avoid overflow).

---

## Idea

Simulate binary addition from the least significant digit (rightmost) to the most significant (leftmost) using two pointers and a carry. Build the result in reverse and then reverse it at the end.

---

## Algorithm (two-pointer)

1. Initialize pointers `i = a.length() - 1`, `j = b.length() - 1` and `carry = 0`.
2. Use a `StringBuilder sb` to accumulate digits (appending the computed bit at each step).
3. While `i >= 0` or `j >= 0` or `carry > 0`:
   - `sum = carry`.
   - If `i >= 0`, add `a.charAt(i) - '0'` to `sum` and `i--`.
   - If `j >= 0`, add `b.charAt(j) - '0'` to `sum` and `j--`.
   - Append `(sum % 2)` to `sb`.
   - Set `carry = sum / 2`.
4. Reverse `sb` and return `sb.toString()`.


---

## Java implementation

```java
class Solution {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.append((char) ('0' + (sum % 2)));
            carry = sum / 2;
        }

        return sb.reverse().toString();
    }
}
```

---

## Why this works

- Binary addition works like decimal addition but base 2. `sum % 2` gives the current bit, `sum / 2` gives the carry for the next position.
- By processing from right to left, we always use the least significant unprocessed digits and propagate carry correctly.
- Reversing the `StringBuilder` yields the correct most-significant-first order.

---

## Complexity

- Time: O(max(len(a), len(b))) — single pass across the longer input.
- Space: O(max(len(a), len(b))) — result size and StringBuilder.

---

## Example walkthrough

Input:

```
a = "1010"
b = "1011"
```

Process (right to left):

| Step | i (a[i]) | j (b[j]) | carry in | sum | bit written | carry out | sb (reversed so far) |
|------|----------|----------|----------:|-----:|-------------:|----------:|----------------------|
| 1    | 0        | 1        | 0         | 1    | 1            | 0         | "1"                 |
| 2    | 1        | 1        | 0         | 2    | 0            | 1         | "10"                |
| 3    | 0        | 0        | 1         | 1    | 1            | 0         | "101"               |
| 4    | 1        | 1        | 0         | 2    | 0            | 1         | "1010"              |
| end  | -        | -        | 1         | 1    | 1            | 0         | "10101"             |

Reverse `sb` -> "10101". Result = "10101".

Notes:

- We used characters to compute bits (`char - '0'`) to avoid integer parsing of whole strings.
- The loop condition includes `carry` to handle final carry-out.

---

## Alternate approaches

- Convert strings to big integers and add: not safe due to overflow in typical languages without BigInt support.
- Use bitwise simulation on integer types: possible only if numbers fit into integer types.

---

✅ The two-pointer simulation with carry is simple, efficient, and avoids integer overflow.

