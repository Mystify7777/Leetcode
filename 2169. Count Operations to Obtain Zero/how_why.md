# 2169. Count Operations to Obtain Zero

## Recap

You are given two positive integers `num1` and `num2`. While both are non‑zero, perform one operation:
If `num1 >= num2`, set `num1 = num1 - num2`; else set `num2 = num2 - num1`. Count how many operations occur until one becomes zero. Return that count.

## Intuition

The described process is exactly the subtraction form of the Euclidean algorithm for computing `gcd(num1, num2)`, except instead of jumping directly with division we repeatedly subtract the smaller from the larger and count each subtraction.

Naively simulating one subtraction per step can be very slow in worst cases (e.g. `(10^9, 1)` would need ~10^9 steps). But in the Euclidean algorithm we know that instead of subtracting `k` times we can jump in one step by computing the quotient `larger / smaller`, because subtracting `smaller` from `larger` `q` times is equivalent to doing `larger %= smaller` and counting `q` operations.

So every phase becomes:

1. Ensure `(larger, smaller)` ordering.
2. Add `larger / smaller` to the answer (number of single-subtraction operations that would have happened).
3. Replace `larger` by `larger % smaller` (the remainder after all those virtual subtractions).
4. Repeat after swapping to keep consistent ordering.

When one becomes zero we stop. This telescopes the long chain of repeated subtractions down to the standard division steps of Euclid's algorithm with aggregated counts.

## Approach

We keep two variables (`n1`, `n2`). During each loop iteration while both are > 0:

- Accumulate `n1 / n2` into the counter (we invariantly keep `n1 >= n2` just before dividing by performing a swap from previous iteration).
- Set `n1 %= n2` (simulate all those subtractions at once).
- Swap `n1` and `n2` (so next iteration `n1` is the current larger number, unless remainder was zero and we exit).

The loop ends when either becomes zero. The accumulated count is the answer.

## Code (Java)

```java
class Solution {
    public int countOperations(int n1, int n2) {
        int c = 0;
        while (n1 > 0 && n2 > 0) {
            c += n1 / n2;   // number of times we would subtract n2 from n1
            n1 %= n2;       // simulate those subtractions in one step
            // swap to make next iteration start with larger in n1 (unless n1 is 0)
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }
        return c;
    }
}
```

## Correctness

Each subtraction operation in the original process removes exactly `smaller` from `larger`. Doing it `q = larger / smaller` times produces `larger - q * smaller = larger % smaller` and counts exactly `q` operations. No other path could appear between those repeated subtractions, because the smaller doesn't change until the larger drops below it. Thus aggregating with division plus remainder preserves the total count.

The swap maintains the invariant that the first variable (`n1`) is the larger one at the top of the loop (unless termination). Eventually one becomes zero because Euclid's algorithm always terminates; when a remainder becomes zero we add the final quotient that empties the larger number.

## Complexity

Let `num1 >= num2` initially. The number of iterations equals the number of steps in the Euclidean algorithm, which is `O(log min(num1, num2))`. Each iteration does constant work. So:

- Time: `O(log min(num1, num2))`
- Space: `O(1)`

## Edge Cases

- `num1 == num2`: One iteration returns 1 (subtract once and one becomes zero via remainder logic).
- One number is 1: Answer is the other number (reduced in one loop with division).
- Large disparity (e.g. `(10^9, 2)`) handled quickly via division rather than huge subtraction loop.

## Alternative (Naive Simulation)

```java
int count = 0;
while (num1 > 0 && num2 > 0) {
    if (num1 >= num2) num1 -= num2; else num2 -= num1;
    count++;
}
```

This can degrade to `O(max(num1, num2))` operations; too slow for large inputs.

## Why Swapping Works

After reducing `n1` with `n1 %= n2`, the (former) `n2` is still the smaller of the previous pair. By swapping we prepare for the next division with the new larger value in `n1`. If the remainder was zero, we exit next loop check. This matches Euclid's `(a, b) -> (b, a % b)` pattern exactly.

## Takeaways

- Counting repeated subtractions is equivalent to counting quotients in the Euclidean algorithm.
- Replace long chains of identical operations with arithmetic compression (division + remainder).
- Swapping plus modulus mirrors the canonical gcd reduction while tracking total operation count.
