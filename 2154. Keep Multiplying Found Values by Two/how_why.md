## Recap

You are given an array of integers `nums` and an integer `original`. You need to keep multiplying `original` by 2 as long as the current value exists in the array. Return the final value of `original` after this process.

## Intuition

The naive approach would repeatedly search for `original`, double it, and repeat until no match is found. However, the bit manipulation solution exploits the fact that all values we care about are `original * 2^i` for some `i >= 0`. By collecting which powers of 2 are achievable (as multiples of `original` present in `nums`), we can directly compute the final result by finding the lowest missing power.

## Approach

1. Initialize `bits = 0` to track which powers of 2 (as multipliers of `original`) are present.
2. For each `num` in `nums`:
   - Skip if `num` is not divisible by `original`.
   - Compute `n = num / original`.
   - Check if `n` is a power of 2: `(n & (n - 1)) == 0`.
   - If yes, set the corresponding bit: `bits |= n`.
3. Increment `bits` by 1. This sets the lowest 0-bit (the first missing power).
4. Extract the lowest set bit using `bits & -bits` to find the first missing power of 2.
5. Return `original * (bits & -bits)`.

**Example:** If `original = 5` and we find `5, 10, 40` in the array:

- `5/5 = 1` (2^0), `10/5 = 2` (2^1), `40/5 = 8` (2^3)
- `bits = 1 | 2 | 8 = 0b1011` (binary: powers 0, 1, 3 present; power 2 missing)
- `bits++ = 0b1100`
- `bits & -bits = 0b0100 = 4` (the missing power is 2^2)
- Result: `5 * 4 = 20`

## Code (Java)

```java
class Solution {
    public int findFinalValue(int[] nums, int original) {
        int bits = 0;
        for (int num : nums) {
            if (num % original != 0) continue;
            int n = num / original;
            if ((n & (n - 1)) == 0)
                bits |= n;
        }
        bits++;
        return original * (bits & -bits);
    }
}
```

## Correctness

- The algorithm identifies all multiples of `original` that are `original * 2^i` for valid powers `i`.
- The bitmask `bits` accumulates these powers: if `original * 2^i` is present, bit `i` (viewing the power as a value) is set.
- Incrementing `bits` fills the rightmost gap (lowest 0-bit), effectively finding the first missing power of 2 multiplier.
- Extracting the lowest set bit from the incremented value gives exactly `2^k`, where `k` is the first missing power.
- Multiplying by `original` yields the correct final value.

## Complexity

- Time: `O(n)` single pass over the array.
- Space: `O(1)` auxiliary.

## Edge Cases

- `original` not in array: return `original` (bits remains 0, `bits++ = 1`, `1 & -1 = 1`, result is `original * 1`).
- All consecutive powers present: correctly finds the next missing one.
- Large powers within integer range: bit operations handle up to 2^30 safely.
- Array contains non-multiples or odd multiples of `original`: safely skipped.

## Takeaways

- Bit manipulation can elegantly encode set membership for powers of 2.
- The trick `n & (n - 1) == 0` checks if `n` is a power of 2.
- Incrementing a bitmask fills the rightmost gap; `x & -x` isolates the lowest set bit.
- When the problem involves repeated doubling, think about encoding the sequence as powers of 2 in a bitmask.

## Alternative Approach

The simpler brute-force method uses a `HashSet` for constant-time lookups:

```java
class Solution {
    public int findFinalValue(int[] nums, int original) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        while (set.contains(original)) {
            original *= 2;
        }
        return original;
    }
}
```

- Time: `O(n + k)` where `k` is the number of doublings (typically small).
- Space: `O(n)` for the set.
- Trade-off: Simpler to understand but uses extra space; bit manipulation is `O(1)` space and more elegant for this specific problem structure.
