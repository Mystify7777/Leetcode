# 3577. Count the Number of Computer Unlocking Permutations — how/why

## Recap

Given an array `comp` representing computer strengths, count the number of valid permutations of the remaining computers (all except the first) that satisfy the following constraint: for each computer at position `i`, the cumulative strength sum from positions `1` to `i` must be greater than the strength of the computer at position `0`. Return the count modulo `10^9 + 7`.

In simpler terms: the first computer's strength must be the unique minimum in the array. If so, any permutation of the remaining computers is valid, giving us `(n-1)!` permutations.

## Intuition

The key insight is recognizing the constraint structure:

- The computer at position `0` (with strength `comp[0]`) acts as a "guard."
- For any valid permutation to exist, `comp[0]` must be strictly less than all other computers' strengths.
- If `comp[0]` is the unique minimum, then no matter how we arrange the remaining computers, the cumulative sum condition is always satisfied (since we're adding values all larger than `comp[0]`).
- If `comp[0]` is not the unique minimum, no valid permutation exists.

**Therefore**: The answer is either `(n-1)!` (if `comp[0]` is unique minimum) or `0` (otherwise).

## Approach

**Validation + Factorial Computation**:

1. Extract the strength of the first computer: `first = comp[0]`.
2. Validate: Check if `first` is strictly less than all other elements. If any element `≤ first` exists, return `0`.
3. If validation passes, compute `(n-1)!` modulo `10^9 + 7`:
   - Iterate from `i = 2` to `n-1`.
   - Multiply: `fact = (fact * i) % MOD`.
4. Return the result.

**Why factorial**: With the constraint satisfied, any arrangement of the remaining `n-1` computers is valid. The number of such arrangements is `(n-1)!`.

## Code (Java)

```java
class Solution {
    static final int MOD = 1_000_000_007;

    public int countPermutations(int[] comp) {
        int n = comp.length;
        int first = comp[0];

        // Check that first is the unique minimum
        for (int i = 1; i < n; i++) {
            if (comp[i] <= first) return 0;
        }

        // Compute factorial (n-1)!
        long fact = 1;
        for (int i = 2; i < n; i++) {
            fact = (fact * i) % MOD;
        }

        return (int) fact;
    }
}
```

## Correctness

- **Unique minimum validation**: The loop checks that all elements except the first are strictly greater than `comp[0]`. If any element is `≤ first`, the constraint cannot be satisfied for any permutation, so we return `0`.

- **Factorial logic**: If `comp[0]` is the unique minimum, then for any permutation of the remaining `n-1` computers:
  - At position `i`, the cumulative sum includes `comp[0]` plus the sum of the first `i-1` elements from the permutation.
  - Since all remaining elements are > `comp[0]`, the cumulative sum grows and always exceeds any individual subsequent computer's strength.
  - Thus, all `(n-1)!` permutations are valid.

- **Modular arithmetic**: `(fact * i) % MOD` prevents overflow and ensures the result fits within the modulo constraint.

- **Factorial bounds**: For `n ≤ 10` or so, `(n-1)!` can be large but is always computed correctly with modular reduction.

## Complexity

- **Time**: O(n) for validation + O(n) for factorial = O(n) overall.
- **Space**: O(1) auxiliary space.

## Edge Cases

- Array of size 2: Single remaining computer. `comp[0] < comp[1]` → return `1! = 1`. Otherwise, return `0`.
- Array of size 1: No valid permutations (empty set). Return `1` (factorial of 0 is 1, though problem likely has n ≥ 2).
- `comp[0]` equals another element: Return `0` (not unique minimum).
- All elements except first are equal: If all > `comp[0]`, return `(n-1)!` regardless of duplicate values.
- Negative strengths: Logic works identically with negative numbers (comparison still valid).
- Large `n`: Factorial grows quickly; modular arithmetic prevents overflow.

## Takeaways

- **Constraint recognition**: Understanding that `comp[0]` being unique minimum completely determines the answer eliminates complex simulation.
- **Early termination**: Validating the unique minimum first allows immediate return of `0` if invalid, saving computation.
- **Combinatorial counting**: When all permutations are valid, the answer is simply `(n-1)!` without needing to enumerate them.
- **Modular factorial**: Computing factorials modulo a prime preserves correctness while preventing overflow.
- **Problem reduction**: This problem reduces from "count valid permutations" to "check one condition and compute factorial."

## Alternative (Permutation Enumeration, O(n! · n))

```java
class Solution {
    static final int MOD = 1_000_000_007;

    public int countPermutations(int[] comp) {
        int n = comp.length;
        int first = comp[0];

        // Validate first is unique minimum
        for (int i = 1; i < n; i++) {
            if (comp[i] <= first) return 0;
        }

        // Generate all permutations of remaining computers
        int[] remaining = new int[n - 1];
        for (int i = 1; i < n; i++) {
            remaining[i - 1] = comp[i];
        }

        int count = 0;
        count = countValidPermutations(remaining, 0, count);
        return count % MOD;
    }

    private int countValidPermutations(int[] arr, int index, int count) {
        if (index == arr.length) {
            // Check if this permutation is valid
            // (In this problem, all are valid after validation, so count++)
            return count + 1;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            count = countValidPermutations(arr, index + 1, count);
            swap(arr, index, i);
        }

        return count;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

**Trade-off**: Permutation enumeration explicitly generates all `(n-1)!` permutations and counts them, achieving correctness through exhaustive search. However, it's exponentially slow and impractical for even moderate `n`. The optimized approach recognizes that all permutations are valid (after validation) and directly computes `(n-1)!`, achieving O(n) time. Use the optimized solution in production; enumeration is useful only for small inputs or educational purposes.
