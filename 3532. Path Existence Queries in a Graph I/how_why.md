# 3753. Total Waviness of Numbers in Range II

## What changed from "I" to "II"
Same definition as before: waviness of a number = count of "middle" digits (excluding the very first and very last digit) that are strictly bigger than *both* neighbors (a **peak**) or strictly smaller than *both* neighbors (a **valley**). `totalWaviness(num1, num2)` sums this over every integer in `[num1, num2]`.

The "II" version simply pushes the bounds on `num1`/`num2` much higher — large enough that even `int` isn't safe (everything here is `long`), and large enough that the earlier `helper()`-with-a-`for`-loop style still works *conceptually*, but needs to be written more carefully/efficiently. Two solutions are given: a clean **digit-DP** (`Solution2`, easiest to understand and trust) and a **closed-form combinatorial** version (`Solution`, faster, but far more intricate).

---

## Solution2 — Digit DP (the "textbook" approach)

### Why digit DP fits here
We want, for numbers `0` to `num`, both:
1. How many numbers there are (not actually needed for the final answer, but needed internally to combine sub-counts), and
2. Their **total waviness**.

Digit DP builds numbers digit-by-digit (left to right) while tracking just enough state to know (a) whether we're still bounded by `num`'s own digits (`tight`), (b) whether we've placed a non-zero digit yet (`isStarted`, to correctly ignore leading zeros), and (c) the **last two digits placed** (`lastDigit`, `secLastDigit`) — because waviness only ever depends on a digit and its two neighbors, so remembering the last two digits is exactly enough context to know, once we place the *next* digit, whether the *previous* digit was a peak or valley.

### The state
```java
dp(idx, tight, isStarted, lastDigit, secLastDigit, s)
```
- `idx`: which digit position we're filling in.
- `tight` (0/1): are we still constrained to be `≤` `num`'s digits so far, or already strictly smaller (free to place any digit 0-9 from here)?
- `isStarted` (0/1): has a non-zero digit been placed yet? (handles variable-length numbers uniformly, e.g. `007` should be treated as `7`).
- `lastDigit`, `secLastDigit`: the two most recently placed real digits (`10` is used as a sentinel meaning "not yet placed" — since a real digit is `0-9`).

The function returns a pair `{count, totalWaviness}` for **all valid completions from this state onward**.

### The recursion
```java
int limit = tight == 1 ? s.charAt(idx) - '0' : 9;
for (int d = 0; d <= limit; d++) {
```
Try every digit `d` allowed at this position (bounded by `num`'s own digit if still tight).

```java
int nextTight = (tight == 1 && d == limit) ? 1 : 0;
int nextStarted = (isStarted == 1 || d > 0) ? 1 : 0;
```
Standard digit-DP bookkeeping: stay tight only if we matched the limit exactly; become "started" once a non-zero digit appears (or we already had started).

```java
if (nextStarted == 1) {
    nextLast = d;
    nextSecLast = lastDigit;
    if (secLastDigit != 10 && lastDigit != 10) {
        if ((lastDigit > secLastDigit && lastDigit > d) || (lastDigit < secLastDigit && lastDigit < d)) {
            wavinessContribution = 1;
        }
    }
}
```
This is the heart of it: once we place digit `d`, the *previous* digit (`lastDigit`) now has both of its neighbors known (`secLastDigit` on one side, `d` on the other). If both of those neighbors exist (`!= 10`, i.e. we're at least 3 real digits deep) and `lastDigit` is strictly greater than both, or strictly less than both, that previous digit was a peak/valley — add `1` to the waviness of *every number that reaches this state*.

```java
long[] res = dp(idx + 1, nextTight, nextStarted, nextLast, nextSecLast, s);
totalCount += res[0];
totalWaviness += res[1] + wavinessContribution * res[0];
```
Recurse for the rest of the digits, then combine: the sub-tree contributes its own waviness (`res[1]`) *plus* `wavinessContribution` copies of `1` — one for **every** number counted in that sub-tree (`res[0]` of them), since the peak/valley we just detected applies equally to all of them regardless of what digits come after.

### Memoization
```java
memoCount[idx][tight][isStarted][lastDigit][secLastDigit]
```
Because the state space (`idx`, `tight`, `isStarted`, `lastDigit`, `secLastDigit`) is small and reused constantly across different digit choices deeper in the recursion, results are cached — this is what turns an exponential digit-by-digit search into a polynomial-time DP.

### Complexity
- **States:** `O(digits × 2 × 2 × 11 × 11)`, each computed in `O(10)` work (looping `d`).
- **Time:** `O(digits²)` roughly (digits × 10 choices × already-memoized lookups) — effectively `O(log(num))` with a small constant, easily fast enough even for huge `num`.
- **Space:** `O(digits × 2 × 2 × 11 × 11)` for the memo tables.

This solution is slower than `Solution` below in practice (more overhead per digit), but it's much easier to verify as correct — a good "sanity check" implementation.

---

## Solution — Closed-form combinatorial counting

### The overall idea (same spirit as the "I" version)
Just like before, waviness only depends on **triples of consecutive digits**, so the total waviness of all numbers `< x` can be computed by sliding a 3-digit window across every possible position and, for each position, algebraically counting how many numbers have a peak/valley there — without ever looping over individual numbers.

```java
public long totalWaviness(long num1, long num2) {
    return solve(num2 + 1) - solve(num1);
}
```
`solve(x)` is defined as the total waviness of all numbers from `0` to `x - 1` (a "count strictly less than x" prefix function). So `solve(num2+1)` covers `0..num2` and `solve(num1)` covers `0..num1-1`; subtracting leaves exactly `[num1, num2]` — algebraically the same trick as `helper(num2) - helper(num1-1)` from the earlier version, just shifted by one to keep the boundary condition (`< x`) consistent.

### Sliding the window incrementally instead of recomputing it
```java
for (long left = x / 10, right = x % 10, p10 = 1; left >= 10; ) {
    int d  = (int)(left % 10);        // "mid" digit of the window
    int dl = (int)(left / 10 % 10);   // "left" digit of the window
    int dr = (int)(right / p10);      // "right" digit of the window
    left /= 10;
    ...
    p10 *= 10;
    right += d * p10;
}
```
Rather than recomputing `x / 10^e` from scratch every iteration (as the "I" solution did), this keeps two running numbers:
- `left` = the digits of `x` **not yet processed** (shrinks by one digit each loop),
- `right` = the digits of `x` **already processed**, reassembled back into a number (grows by one digit each loop, via `right += d * p10`).

At each step, the window is `(dl, d, dr)` = (the digit above, the digit at the current position, the digit just below) — exactly the "left/mid/right" triple from before, just extracted via a rolling pair of accumulators instead of fresh division each time. This avoids recomputing large powers of 10 or re-dividing the (potentially huge, `long`-sized) `x` from scratch at every digit — a meaningful efficiency detail once `x` can be very large.

### The four "stages" — boundary handling from finest to coarsest
Just like the "I" version needed a correction for the *one* partial ("boundary") cycle at the top of the number, this version — because it slides window-by-window while tracking exact digits on *both* sides — needs to handle boundary corrections at **multiple granularities** simultaneously: numbers that match `x`'s digits above the window exactly but diverge at the window itself, or within the window, or just at its rightmost digit. The four stages correspond to these increasingly coarse levels of divergence:

- **Stage 1 — vary only the rightmost digit of the window** (`dr` fixed digits above `dl, d`; only the digit exactly at the window's low end changes). This handles the finest boundary case: numbers identical to `x` down through `dl, d`, differing only at the window's last digit.
- **Stage 2 — vary the middle digit of the window** (with the digit below it now fully free 0–9), for middle-digit values strictly less than `x`'s actual middle digit. The `up`, `45 - up*(up+1)/2` terms are pre-computed triangular-number sums (`45 = 0+1+...+9`) standing in for "how many of the free choices below still produce a peak/valley," instead of re-deriving that count with a loop.
- **Stage 3 — vary the left digit of the window** (with both the middle and right digits of the window now fully free), for left-digit values strictly less than `x`'s actual left digit at that position. The cubic-looking terms (`dl*(dl+1)*(dl-1)/6`, etc.) are closed-form sums-of-squares/sums-of-products formulas — the same "count wavy triples" combinatorics as the "I" version's `570`/`45`, just generalized into a formula parameterized by how many values are still allowed for the left digit.
- **Stage 4 — everything above the window is fully free.** `(900 - 9*10*11/3)` is (analogous to the `570` constant before) the number of wavy triples per full, unconstrained cycle of `1000` combinations, scaled by however many full cycles (`left / 10`) fit below `x`'s own prefix.

Each stage subtracts `45 * p10` in specific cases (`if (left < 10) ans -= 45 * p10;`, `if (left >= 10) ans -= 45 * p10;`) — the same leading-zero correction as before: a window sitting at the very top of a number can't have a leading digit of `0`, so the wavy-count contribution from that invalid case must be removed, exactly like the `-45` in the "I" solution's `helper`.

### Why split into stages instead of one formula (like "I" did)?
The "I" solution recomputed `left/mid/right` fresh from `num` every iteration via division, then used one combined formula (`count = maxPrefix*570 - 45 + ...`) that only needed to compare against the *current* prefix once. Here, because the window is tracked incrementally with two separate accumulators (`left`, `right`) — and because the numbers involved can be enormous — the boundary logic is decomposed into these four independent stages so each piece only ever needs simple digit-by-digit comparisons (no re-deriving a multi-term boundary formula fresh each time), which keeps the per-iteration work small and avoids recomputation.

### Complexity
- **Time:** `O(log x)` — one loop iteration per digit position, constant work each.
- **Space:** `O(1)`.

---

## Comparing the two solutions
| | `Solution` (closed-form) | `Solution2` (digit DP) |
|---|---|---|
| Time | `O(log x)`, tiny constant | `O(digits² × small constant)` via memoized states |
| Space | `O(1)` | `O(digits × 2 × 2 × 11 × 11)` for memo tables |
| Clarity | Dense, hand-derived combinatorial formulas | Clear, mechanical, easy to verify against brute force |
| Best for | Production/competitive speed | Understanding *why* the fast formulas are correct |

A natural way to trust `Solution`'s formulas is to test them against `Solution2` (or the "I" version's brute force) on small ranges — since both ultimately answer the exact same question, any mismatch would point to an error in the closed-form derivation.