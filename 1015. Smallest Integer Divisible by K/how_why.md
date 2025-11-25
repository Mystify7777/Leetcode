# Recap

Given a positive integer `K`, find the length of the smallest positive integer `N` consisting only of the digit `1` that is divisible by `K`. Return the length of `N`. If there is no such `N`, return `-1`.

Note: `N` may be very large, so you only need to return the length, not the number itself. Examples: `1`, `11`, `111`, `1111`, etc. (called repunits).

## Intuition

We want to find the smallest repunit (number with only 1s) divisible by `K`. We can build repunits incrementally: `1`, `11`, `111`, etc. Each new repunit is formed by `new = old * 10 + 1`. Since we only care about divisibility, we track remainders modulo `K`. If we see remainder `0`, we found a solution. If we see a repeated remainder, we're in a cycle and no solution exists.

## Approach

1. Initialize `rem = 0` (remainder) and `length = 0`.
2. Use a boolean array `seen[K]` to track visited remainders.
3. Loop indefinitely:
   - Increment `length`.
   - Update remainder: `rem = (rem * 10 + 1) % K`.
   - If `rem == 0`: return `length` (found divisible repunit).
   - If `seen[rem]`: return `-1` (cycle detected, no solution).
   - Mark `seen[rem] = true`.
4. The loop terminates in at most `K` iterations (since there are only `K` possible remainders).

**Key insight:** Building `111...1` digit by digit is equivalent to `rem = (rem * 10 + 1) % K` at each step. If remainder becomes `0`, the current repunit is divisible by `K`.

**Why cycles mean no solution:** If remainder `r` repeats, all subsequent remainders will repeat in the same pattern (deterministic), so we'll never reach `0`.

**Mathematical note:** A repunit is divisible by `K` only if `gcd(K, 10) = 1` (i.e., `K` is coprime with 10, meaning `K` is not divisible by 2 or 5). However, the algorithm handles this naturally by detecting cycles.

## Code (Java)

```java
class Solution {
    public int smallestRepunitDivByK(int k) {
        boolean[] seen = new boolean[k];
        int rem = 0, length = 0;
        
        while (true) {
            length++;
            rem = (rem * 10 + 1) % k;
            
            if (rem == 0) return length;
            if (seen[rem]) return -1;
            
            seen[rem] = true;
        }
    }
}
```

## Correctness

- **Repunit construction:** Starting with `0` and repeatedly computing `(rem * 10 + 1) % K` simulates building `1`, `11`, `111`, etc., while keeping only remainders.
  
- **Divisibility check:** When `rem == 0`, the current repunit of length `length` is divisible by `K`.

- **Cycle detection:** Since remainders are in `[0, K-1]`, after at most `K` steps we must either find `rem == 0` or revisit a remainder. If we revisit, the sequence will loop forever without reaching `0`, so return `-1`.

- **Termination:** Guaranteed within `K` iterations.

- **Correctness of coprimality:** If `K` is even or divisible by 5, repunits (which end in 1) cannot be divisible by `K` (since repunits are always odd and not divisible by 5). The algorithm detects this via cycle.

## Complexity

- **Time:** `O(K)` — at most `K` iterations (one per possible remainder).
- **Space:** `O(K)` — boolean array to track seen remainders.

## Edge Cases

- `K = 1`: repunit `1` is divisible, return `1`.
- `K = 2`: even number, repunit (odd) never divisible, return `-1`.
- `K = 5`: repunit ends in 1, never divisible by 5, return `-1`.
- `K = 3`: `111 = 3 * 37`, return `3`.
- `K = 7`: `111111 = 7 * 15873`, return `6`.
- Large `K` coprime with 10: solution exists; algorithm finds it efficiently.
- `K` divisible by 2 or 5: no solution, detected by cycle.

## Takeaways

- **Modular arithmetic:** Track remainders instead of full values to avoid overflow and handle large numbers.
- **Cycle detection:** When operating in a finite state space (remainders mod `K`), revisiting a state means entering an infinite loop.
- **Repunit divisibility:** A number consisting only of 1s has special properties; divisibility by `K` requires `gcd(K, 10) = 1`.
- **Pigeonhole principle:** With `K` possible remainders and infinite repunits, either we hit `0` or cycle within `K` steps.
- This technique generalizes to finding patterns in modular sequences.

## Alternative (Early Exit Optimization)

```java
class Solution {
    public int smallestRepunitDivByK(int k) {
        if (k % 2 == 0 || k % 5 == 0) return -1;  // Early exit
        
        boolean[] seen = new boolean[k];
        int rem = 0, length = 0;
        
        while (true) {
            length++;
            rem = (rem * 10 + 1) % k;
            
            if (rem == 0) return length;
            if (seen[rem]) return -1;
            
            seen[rem] = true;
        }
    }
}
```

**Trade-off:** Adds a quick check for even `K` or `K` divisible by 5, saving iterations. However, the main algorithm is already efficient (`O(K)`), so the optimization is minor.
