# Recap

Given a binary array `A` (elements are 0 or 1). For every prefix `A[0..i]`, interpret the bits as a binary integer and determine whether that number is divisible by 5. Return a list of booleans `result` where `result[i]` is `true` iff the prefix value is divisible by 5.

## Intuition

Computing the full integer for long prefixes causes overflow; but divisibility by 5 depends only on the value modulo 5. Appending a bit is equivalent to left-shifting (multiply by 2) and adding the new bit. So we can update the running remainder without storing the entire number.

## Approach

1. Maintain `rem = currentValue % 5`, start at 0.
2. For each bit `b` in the array:
   - Update: `rem = (rem * 2 + b) % 5`.
   - Append `rem == 0` to output.
3. Return the collected boolean list.

Because we reduce modulo 5 each step, the integer never grows large.

## Code (Java)

```java
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> res = new ArrayList<>(A.length);
        int rem = 0;
        for (int b : A) {
            rem = (rem * 2 + b) % 5;
            res.add(rem == 0);
        }
        return res;
    }
}
```

## Correctness

Let `V_i` be the integer value of prefix `A[0..i]`. Then `V_{i+1} = 2 * V_i + b`. Taking modulo 5: `V_{i+1} % 5 = (2 * (V_i % 5) + b) % 5`. Thus the remainder after processing `i+1` bits depends only on the previous remainder and the new bit. By induction, tracking `rem` is sufficient to decide divisibility (`rem == 0`). We never need the full numeric value.

## Complexity

* Time: `O(n)` — single pass.
* Space: `O(n)` for output; `O(1)` auxiliary.

## Edge Cases

* Empty array: return empty list.
* Leading zeros: harmless (left shift of zero remains zero).
* All zeros: every prefix divisible → all `true`.
* Very long input: safe—only remainder tracked.

## Takeaways

* When checking divisibility, keeping the running remainder often suffices.
* Binary stream accumulation: left-shift → multiply by 2.
* Modulo reduction each step prevents overflow and preserves correctness.
* Pattern generalizes: for any modulus `m`, use `(rem * 2 + b) % m`.

## Alternative (Python)

```python
def prefixesDivBy5(A):
    res = []
    rem = 0
    for b in A:
        rem = (rem * 2 + b) % 5
        res.append(rem == 0)
    return res
```

Equivalent logic; choose language as needed.
