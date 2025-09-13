# How & Why: LeetCode 171 - Excel Sheet Column Number

This solution treats the Excel column title as a base-26 number and converts it to its base-10 equivalent in a single pass.

---

## Problem Restatement

You are given a string `columnTitle` that represents the column title as it appears in an Excel sheet (e.g., "A", "B", "C", ..., "Z", "AA", "AB", ...). Your task is to return its corresponding column number.

### Examples

- "A" → 1
- "B" → 2
- "Z" → 26
- "AA" → 27
- "AB" → 28

---

## How to Solve

The problem is equivalent to converting a number from a base-26 system to a base-10 (decimal) system. The "digits" are the letters 'A' through 'Z', corresponding to values 1 through 26.

Think about how we interpret a regular number like "123". It's $(1 \times 10 + 2) \times 10 + 3$. We can apply the exact same logic here, but with a base of 26.

1. **Initialize a counter (`count`) to 0.**
2. **Iterate through the `columnTitle` string from left to right.**
3. **For each character:**
    - Calculate its numeric value (e.g., 'A' is 1, 'B' is 2, ..., 'Z' is 26). This can be done with `character - 'A' + 1`.
    - Update the total count by "shifting" the existing value one place to the left (multiplying by 26) and adding the new character's value. The formula is: `count = count * 26 + num`.
4. **Return the final count after the loop is complete.**

### Implementation

```java
class Solution {
    public int titleToNumber(String columnTitle) {
        int count = 0;
        for (int i = 0; i < columnTitle.length(); i++) {
            // Convert character to its 1-26 value
            int num = columnTitle.charAt(i) - 'A' + 1;
            // "Shift" the existing count and add the new value
            count = count * 26 + num;
        }
        return count;
    }
}
```

---

## Why This Works

This method is a standard algorithm for converting a number from any base to base-10.

By starting from the leftmost "digit" (character), each step effectively multiplies the accumulated value by the base (26). This correctly positions the previous digits in their higher-value places.

For example, when processing "AB":
- For 'A': `count = 0 * 26 + 1 = 1`.
- For 'B': `count = 1 * 26 + 2 = 28`.

For "ZY":
- For 'Z': `count = 0 * 26 + 26 = 26`.
- For 'Y': `count = 26 * 26 + 25 = 676 + 25 = 701`.

This perfectly mimics place-value notation and correctly computes the final decimal number.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$, where $n$ is the length of the `columnTitle` string. We iterate through the string exactly once.
- **Space Complexity:** $O(1)$. We only use a few variables to store the count and loop index, so the memory usage is constant.

---

## Example Walkthrough

**Input:**
```
columnTitle = "FXSHRXW"
```

**Process:**

- Initial: `count = 0`
- 'F': `num = 6`. `count = 0 * 26 + 6 = 6`
- 'X': `num = 24`. `count = 6 * 26 + 24 = 156 + 24 = 180`
- 'S': `num = 19`. `count = 180 * 26 + 19 = 4680 + 19 = 4699`
- 'H': `num = 8`. `count = 4699 * 26 + 8 = 122174 + 8 = 122182`
- 'R': `num = 18`. `count = 122182 * 26 + 18 = 3176732 + 18 = 3176750`
- 'X': `num = 24`. `count = 3176750 * 26 + 24 = 82595500 + 24 = 82595524`
- 'W': `num = 23`. `count = 82595524 * 26 + 23 = 2147483624 + 23 = 2147483647`
- Loop finishes.

**Output:**
```
Return count, which is 2147483647.
```

---

## Key Insight

The core concept is to recognize that this is not just a string manipulation problem, but a number system conversion problem. By identifying the base (26) and the value of each "digit" (A=1 to Z=26), the solution becomes a straightforward mathematical calculation.