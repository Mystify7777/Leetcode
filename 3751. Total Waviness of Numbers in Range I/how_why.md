# 3751. Total Waviness of Numbers in Range I

## Problem in short
For a number, look at its digits and, excluding the **first** and **last** digit, count how many digits are a **peak** (strictly bigger than both neighbors) or a **valley** (strictly smaller than both neighbors). That count is the number's **waviness**. Numbers with fewer than 3 digits have waviness `0` (no "middle" digit exists).

`totalWaviness(num1, num2)` = sum of waviness over every integer in `[num1, num2]`.

---

## Solution2 — the brute-force reference solution

```java
for (int i = num1; i <= num2; i++) {
    totalWaviness += getWaviness(i);
}
```
This is the direct translation of the definition:
- Convert each number to a string.
- Skip numbers with fewer than 3 digits (`waviness = 0`).
- For every "middle" character (index `1` to `length-2`), compare it to its immediate left and right neighbor. If it's strictly greater than both → peak; strictly less than both → valley; either case increments `waviness`.

This is correct and easy to verify, but it's **O((num2 - num1) × digits)** — for the "I" version of this problem (small ranges) that's fine, but it doesn't scale (this is exactly why a "II" version of the problem exists with much larger bounds, requiring the digit-counting approach below).

---

## Solution — the fast combinatorial / digit-DP approach

### Step 1 — Turn it into a prefix-sum problem
```java
return (int) (helper(num2) - helper(num1 - 1));
```
Just like counting-in-range problems usually do, define `helper(x)` = total waviness summed over **all** integers from `1` to `x`. Then the answer for `[num1, num2]` is `helper(num2) - helper(num1 - 1)`. This reduces the problem to: *efficiently compute the sum of waviness for every number from 1 to x.*

### Step 2 — Swap "loop over numbers" for "loop over digit-triple positions"
Waviness only ever looks at **consecutive triples of digits**. Instead of iterating number-by-number, `helper` iterates over each possible **digit-position window** in the number `num` — the units/tens/hundreds triple, then the tens/hundreds/thousands triple, and so on — and for each window asks: *"across all numbers from 1 to num, how many times does this exact window form a peak or valley?"* Summing that count over all windows gives the total waviness across the whole range in one pass, without visiting a single number individually.

```java
for (int pow10 = 1; num >= pow10 * 100; pow10 *= 10) {
    long maxPrefix = num / (pow10 * 1000);
    long tmp = num / pow10;
    int left  = (int) (tmp / 100 % 10);
    int mid   = (int) (tmp / 10 % 10);
    int right = (int) (tmp % 10);
    ...
}
```
For a given `pow10 = 10^e`, this window looks at 3 digit positions of `num` itself:
- `right` = digit at place `10^e` (the "current" units-like digit of the window)
- `mid`   = digit at place `10^(e+1)`
- `left`  = digit at place `10^(e+2)`
- `maxPrefix` = whatever digits sit **above** this window (`num / 10^(e+3)`)
- The loop condition `num >= pow10*100` just guarantees this window (3 digits above position `e`) actually exists inside `num`.

So as `pow10` climbs `1 → 10 → 100 → ...`, the window slides from the low end of the number to the high end, covering every possible peak/valley position exactly once — matching the definition in `getWaviness`.

### Step 3 — Count wavy triples in a "full cycle" (the magic numbers 570 and 45)
Fix the window position. As the digits **below** and **above** it vary freely, the triple `(a, b, c)` at that window cycles through all `1000` combinations `000` to `999`, each appearing once per 1000 consecutive integers. So the natural question is: *of the 1000 possible digit triples `(a,b,c)` with each digit 0–9, how many are peak or valley?*

- **Peaks** (`b>a and b>c`): for each middle digit `b`, there are `b` choices for `a` (0..b-1) and `b` choices for `c` — `b²` combos. Summed for `b = 0..9`: `0²+1²+...+9² = 285`.
- **Valleys** (`b<a and b<c`): symmetric — `(9-b)²` combos for each `b`, also summing to `285`.
- **Total wavy triples per full cycle of 1000 = 285 + 285 = 570.** This is exactly the `570` constant in the code.

That's why `maxPrefix * 570` appears: every time the digits above the window complete one full cycle (0 through 999 for the window+suffix), you get another `570` wavy hits, and there are `maxPrefix` such complete cycles below `num`'s own prefix value.

**The `45` correction:** the very first cycle (`prefix = 0`, meaning there are no actual digits above this window) corresponds to numbers whose window sits at the very **top** of the number — i.e., `left` would be the number's own leading digit. A leading digit can never be `0` (no leading zeros in normal decimal representation), so any "wavy" triple counted in that first cycle with `left = 0` doesn't correspond to a real number and must be discarded.
- With `a (left) = 0` fixed: valley is impossible (`b<0` can't happen), only peaks count: `b>0` and `b>c`, summed over `b=1..9` gives `c` choices `= b`, so `Σ b (b=1..9) = 45`.
- Hence `count = maxPrefix*570 - 45` — take all full cycles, then subtract the 45 invalid leading-zero-left cases that were wrongly included in the very first cycle.

### Step 4 — Handle the partial ("boundary") cycle exactly
`num`'s own prefix isn't a full cycle — it only goes from `000` up to the actual `(left, mid, right)` triple that `num` itself has, and only up to `num`'s own trailing digits for the exact matching case. The block of code:
```java
count += (121 + left * 15 - left * left) * left / 3;
count += (left + mid) * Math.max(mid - left - 1, 0) / 2;
count += (19 - Math.min(left, mid)) * Math.min(left, mid) / 2;
if (left < mid) {
    count += Math.min(mid, right);
} else if (left > mid) {
    count += Math.max(right - mid - 1, 0);
}
```
is a closed-form (derived once, then hard-coded as formulas) count of how many wavy triples `(a, b, c)` are **lexicographically smaller** than `(left, mid, right)` — split into the standard digit-DP cases:
- `a < left` (any `b, c`) — handled by the first polynomial term.
- `a == left` and `b < mid` — split further depending on whether `mid` is above or below `left` (since that changes whether the fixed `a = left` makes a peak/valley more or less likely) — the second and third terms.
- `a == left`, `b == mid`, and `c < right` — the final `if/else`, which only matters when `(left, mid)` already fixes whether the triple is a potential peak (`left < mid`) or valley (`left > mid`) shape, since that determines what values of `c` still keep it wavy.

Each of these captures, algebraically, the same "count peaks + valleys" logic as Step 3, but restricted to triples strictly less than the current one instead of all 1000.

### Step 5 — Score the exact triple itself
```java
result += count * pow10;
if ((left - mid) * (mid - right) < 0) {
    long maxSuffix = num % pow10;
    result += maxSuffix + 1;
}
```
- `count * pow10`: every one of the `count` wavy triples found (across all full and partial cycles) is compatible with **any** value of the digits below the window (there are `pow10` such free lower digits), so each contributes `pow10` different actual numbers.
- `(left - mid) * (mid - right) < 0` is a compact algebraic test for "is `mid` a peak or valley relative to `left`/`right`?" — if `left > mid` and `mid < right` (valley) or `left < mid` and `mid > right` (peak), one factor is positive and the other negative, so the product is negative. This elegantly replaces writing out both comparisons explicitly.
- If the exact triple itself is wavy, then it applies to every number sharing this exact `(left, mid, right)` at this window **and** whose lower digits don't exceed `num`'s own — that's `num % pow10 + 1` numbers (suffixes `0` through `num % pow10`, inclusive).

Everything is accumulated into `result`, and after the loop finishes (once the window would go past the top of the number), `result` holds the total waviness of every integer from `1` to `num`.

---

## Why this is so much faster
- **Solution2 (brute force):** iterates every number in the range and every digit inside it — `O((num2−num1) × log(num2))`. Fine for small ranges, far too slow if the range spans up to, say, `10^9` or more.
- **Solution (digit counting):** `helper(x)` only loops over the **number of digit positions** in `x` (`O(log x)`), doing constant-time arithmetic per position. Both `helper` calls together run in `O(log(num2))` — completely independent of how large the *range* `num2 - num1` is. This is the classic trick for range-counting problems: replace "count things per number" with "count things per digit position, using closed-form combinatorics for how often each pattern occurs."

## Complexity
- **Solution2:** Time `O((num2 − num1) × d)` where `d` is the digit count; Space `O(d)` for the string conversion.
- **Solution:** Time `O(log(num2))` (a small constant number of iterations, bounded by the digit count of `num2`); Space `O(1)`.