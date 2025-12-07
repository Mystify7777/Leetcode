# 1523. Count Odd Numbers in an Interval Range — how/why

## Recap

Given two integers `low` and `high` representing a range, count how many odd numbers are in the inclusive range `[low, high]`.

## Intuition

Rather than iterate through each number, we can use a mathematical approach:

- **Total count**: `high - low + 1` numbers in the range.
- **Odd pattern**: Numbers alternate between odd and even. In any contiguous range, the count of odd numbers depends on whether the endpoints are odd or even.
- **Key cases**:
  - If both `low` and `high` are odd: the sequence starts and ends with odd, so odd count = `(total + 1) / 2`.
  - Otherwise: the odd and even counts are balanced or have one more even, so odd count = `total / 2`.

Example: `[1, 5]` → `1(odd), 2, 3(odd), 4, 5(odd)` → 3 odds. Both endpoints odd: `5 - 1 + 1 = 5`, `5/2 + 1 = 3` ✓

Example: `[1, 6]` → `1(odd), 2, 3(odd), 4, 5(odd), 6` → 3 odds. Only low is odd: `6 - 1 + 1 = 6`, `6/2 = 3` ✓

## Approach

1. Compute `total = high - low + 1` (count of all numbers in the range).
2. Check parity of both endpoints:
   - If both `low` and `high` are odd: return `total / 2 + 1`.
   - Otherwise: return `total / 2`.

**Intuition behind the formula**:

- When both endpoints are odd, the range starts and ends with an odd number, so we have one extra odd compared to the balanced case.
- In other cases (even-odd, odd-even, even-even), the odds and evens are balanced, so odd count = `total / 2` (integer division).

## Code (Java)

```java
class Solution {
    public int countOdds(int low, int high) {
        int total = high - low + 1;
        
        if (low % 2 != 0 && high % 2 != 0) {
            return total / 2 + 1;
        } else {
            return total / 2;
        }
    }
}
```

## Correctness

- **Both odd case**: If `low` is odd and `high` is odd, the sequence has the form: odd, even, odd, even, ..., odd. This is an odd number of elements starting and ending with odd, giving us `(total + 1) / 2` odds. Integer division `total / 2 + 1` is equivalent.

- **Other cases**:
  - `low` even, `high` even: `even, odd, even, ..., even` → odds = `total / 2`.
  - `low` odd, `high` even: `odd, even, ..., even` → odds = `total / 2`.
  - `low` even, `high` odd: `even, odd, ..., odd` → odds = `total / 2`.

- **Integer division**: For any total `n`, `n/2` (integer division) gives us the expected count in non-both-odd cases.

- **Verification**: Works for single-element ranges (e.g., `low = high = 3` → `total = 1`, both odd → `1/2 + 1 = 1` ✓).

## Complexity

- **Time**: `O(1)` constant time computation.
- **Space**: `O(1)` constant space.

## Edge Cases

- Single odd number: `low = high = 3` → `total = 1`, both odd → return `1`.
- Single even number: `low = high = 4` → `total = 1`, not both odd → return `0`.
- Low odd, high even: `[1, 4]` → `total = 4`, return `4/2 = 2` (1, 3 are odd) ✓
- Low even, high odd: `[2, 5]` → `total = 4`, return `4/2 = 2` (3, 5 are odd) ✓
- Both even: `[2, 6]` → `total = 5`, return `5/2 = 2` (3, 5 are odd) ✓
- Large range: Mathematical formula works for any size range.
- Negative numbers: Parity rules apply equally to negative numbers (e.g., `-3` is odd, `-4` is even).

## Takeaways

- **Parity mathematics**: Recognizing alternating patterns in ranges enables closed-form solutions.
- **Endpoint analysis**: The parity of boundaries determines the overall count structure.
- **Integer division insight**: `n/2` for balanced cases, `n/2 + 1` when both endpoints are odd.
- **Mathematical optimization**: Avoid loops when a formula exists; reduces complexity from O(n) to O(1).
- This pattern generalizes: counting evens is symmetric (`total - odds`), and similar logic applies to other modular arithmetic problems.

## Alternative (Brute Force Iteration, O(n))

```java
class Solution {
    public int countOdds(int low, int high) {
        int count = 0;
        for (int i = low; i <= high; i++) {
            if (i % 2 != 0) {
                count++;
            }
        }
        return count;
    }
}
```

**Trade-off**: Brute force is straightforward and easy to understand, iterating through every number and checking oddness. However, it's O(n) time complexity, becoming inefficient for very large ranges (e.g., `[1, 10^9]`). The mathematical formula approach is O(1) and works instantly for any range size. Use the formula in production; use iteration for clarity or when teaching the concept.
