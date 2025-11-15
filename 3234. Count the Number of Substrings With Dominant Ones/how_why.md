# 3234. Count the Number of Substrings With Dominant Ones

## Recap

Given a binary string `s`, count substrings whose number of ones is greater than or equal to the square of the number of zeros. Formally, for a substring, let `one` be the count of `'1'` and `zero` be the count of `'0'`; it is dominant iff `one >= zero^2`.

## Key Ideas

- If `one >= zero^2`, then `zero <= sqrt(one)`. In particular, a dominant substring cannot contain too many zeros compared to its ones.
- Prefix sums let us get `one` in `O(1)` time for any substring; then `zero = length - one`.
- We can accelerate enumeration by skipping ranges of `j` that are guaranteed non-dominant or guaranteed dominant once we know `(one, zero)` at a given `(i, j)`.

## Approach (Prefix sums + bounded skipping)

We precompute `prefix[i] = number of ones in s[0..i]`. For each start `i`, we try end `j = i..n-1` and compute:

- `one = prefix[j] - (i == 0 ? 0 : prefix[i - 1])`
- `zero = (j - i + 1) - one`

Then compare `one` with `zero^2`:

- If `zero^2 > one`: not dominant. Increasing `j` by 1 will increase either `one` or `zero` by at most 1; to reach dominance we need at least `zero^2 - one` more ones lengthwise. We can safely skip ahead close to that gap: `j += (zero^2 - one - 1)`.
- If `zero^2 == one`: exactly dominant; count it and continue with `j+1`.
- If `zero^2 < one`: dominant; not only current `j` works, but the next few `j` also will until zeros catch up. Since we need `zero <= floor(sqrt(one))`, let `t = floor(sqrt(one))`; then as long as the zero count stays `<= t`, the condition holds. With current `zero`, we can skip ahead by `diff = t - zero` and add those `diff` extra dominant ends at once.

This reduces many consecutive checks into O(1) blocks, improving over naive O(n^2) enumeration in practice.

## Code (Java)

```java
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length(); 
        int[] prefix = new int[n]; // prefix sums of '1's

        prefix[0] = ((int)(s.charAt(0) - '0')) == 1 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + (((int)(s.charAt(i) - '0')) == 1 ? 1 : 0);
        }

        int ans = 0;

        for (int i = 0; i < n; i++) {
            int one = 0;  // ones in current substring
            int zero = 0; // zeros in current substring

            for (int j = i; j < n; j++) {
                one = prefix[j] - (i == 0 ? 0 : prefix[i - 1]);
                zero = (j - i + 1) - one;
              
                // Case 1: not dominant yet
                if ((zero * zero) > one) {
                    j += (zero * zero - one - 1);
                } 
                // Case 2: exactly dominant
                else if ((zero * zero) == one) {
                    ans++; 
                } 
                // Case 3: already dominant; bulk-count next few ends
                else { 
                    ans++; 
                    int t = (int) Math.sqrt(one);
                    int diff = t - zero;
                    int nextj = j + diff;

                    if (nextj >= n) {
                        ans += (n - j - 1);
                    } else {
                        ans += diff; 
                    }

                    j = nextj; // jump forward
                }
            }
        }
        return ans;
    }
}
```

## Correctness sketch

- Using prefix sums, `one` and `zero` are exact for any substring `s[i..j]`.
- When `zero^2 > one`, the deficit `(zero^2 - one)` lower-bounds how many additional ones would be needed to possibly meet the threshold; advancing `j` by `zero^2 - one - 1` cannot bridge the gap fully, so we safely skip checks that must still be non-dominant.
- When `zero^2 < one`, let `t = floor(sqrt(one))`. Any extension that keeps zeros `<= t` stays dominant, so counting the next `diff = t - zero` positions in bulk is valid. If we hit the array end before that, we add the remaining ends.
- Combined, these skips are conservative: they may reduce checks, but they never overcount or miss valid substrings because they only batch-count ranges that are provably all non-dominant or all dominant.

## Complexity

- Worst-case naive enumeration is O(n^2). With the skips, it performs far fewer inner iterations in typical strings, often close to O(n sqrt n) behavior in practice, but worst-case remains O(n^2).
- Space: O(n) for the prefix array.

## Alternate Faster Method (√-decomposition over zero positions)

The commented alternative keeps an array of indices of zeros and enumerates only up to ~`sqrt(n)` zeros for each right end. Key facts:

- If a substring is dominant, it contains at most `⌊sqrt(length)⌋` zeros; thus per right endpoint, only a bounded (≈ √n) set of candidate zero counts must be inspected.
- For each block between consecutive zeros, we can count how many left starts yield dominance, subtracting those that lack enough ones.

This yields an O(n √n) approach in the worst case and is known to pass within constraints (`|s| ≤ 4e4`).

## Edge cases

- All ones: every substring is dominant; answer = `n * (n + 1) / 2`.
- All zeros: only substrings with length 0 (none) have ones ≥ zero^2; answer = 0.
- Short strings (`n ≤ 2`): manually matches the comparisons.

## Takeaways

- The condition `one ≥ zero^2` implies a strong bound on zeros: `zero ≤ ⌊√one⌋`.
- Prefix sums with careful skipping are a practical speedup over raw O(n^2) checks.
- A more robust approach enumerates only O(√n) zeros per endpoint using zero indices and inclusion-exclusion-like counting.

