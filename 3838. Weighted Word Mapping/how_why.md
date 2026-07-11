# 3838. Weighted Word Mapping

**Link:** https://leetcode.com/problems/weighted-word-mapping/  
**Difficulty:** Easy  
**Topic Tags:** String, Array, Math

---

## Problem Summary

Given an array of `words` and a `weights` array of length 26 (one weight per letter `a–z`), for each word:
1. Sum the weights of all its characters.
2. Map that sum to a letter using `'z' - (sum % 26)`.

Return the resulting string of mapped letters.

---

## Intuition

Each word collapses to a single integer (the total weight of its characters), and that integer maps to a letter on a `z`-down scale — `sum % 26 == 0` → `'z'`, `sum % 26 == 1` → `'y'`, and so on. The `% 26` wraps large sums into a 0–25 range, and subtracting from `'z'` inverts the direction.

---

## Approach (Clean Solution)

```
for each word:
    sum = 0
    for each char c in word:
        sum += weights[c - 'a']   // index into weights by letter position
    result[word] = (char)('z' - sum % 26)
```

### Why `c - 'a'`?
`'a'` is ASCII 97. Subtracting it gives a 0-based index (a=0, b=1, … z=25), which aligns directly with the `weights` array.

### Why `sum % 26`?
There are only 26 possible output letters. Taking mod 26 wraps the sum into the range `[0, 25]` so the subtraction from `'z'` always produces a valid letter.

### Why `'z' - remainder`?
The problem defines the mapping as descending from `'z'`. Remainder 0 → `'z'`, remainder 1 → `'y'`, …, remainder 25 → `'a'`.

---

## Complexity

| | |
|---|---|
| **Time** | O(N × L) where N = number of words, L = average word length |
| **Space** | O(N) for the output |

---

## Clean Solution (Solution class)

```java
class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        int[] arr = new int[words.length];
        int sum = 0, j = 0;

        for (String word : words) {
            for (int i = 0; i < word.length(); i++)
                sum += weights[word.charAt(i) - 'a'];
            arr[j++] = sum % 26;
            sum = 0;
        }

        StringBuilder sb = new StringBuilder();
        for (int r : arr)
            sb.append((char)('z' - r));

        return sb.toString();
    }
}
```

---

## Optimised / Bit-Hack Solution (Solution2)

```java
class Solution2 {
    public String mapWordWeights(String[] words, int[] wt) {
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            int s = 0;
            for (int i = 0; i < word.length(); i++)
                s += wt[(word.charAt(i) & (1 << 5) - 1) - 1];

            sb.append((char)('z' - (s - ((s * 2521) >> (1 << 4)) * 26)));
        }

        return sb.toString();
    }
}
```

### Bit-hack breakdown

#### `word.charAt(i) & (1 << 5) - 1`

`1 << 5` = 32. `32 - 1` = 31 = `0b00011111`.

ANDing any ASCII letter with `0x1F` strips the case bit and returns the 1-based alphabet position:
- `'a'` (97 = `0b1100001`) & `0x1F` = `0b00001` = 1  
- `'A'` (65 = `0b1000001`) & `0x1F` = `0b00001` = 1  
- `'z'` (122 = `0b1111010`) & `0x1F` = `0b11010` = 26

Result is 1-based, so `- 1` converts it to a 0-based `weights` index.  
**Equivalent to** `c - 'a'` for lowercase, but works for uppercase too (case-insensitive).

#### `(s * 2521) >> (1 << 4)`

`1 << 4` = 16, so this is `(s * 2521) >> 16`.

This is a **fast modulo-by-26 approximation** using integer multiplication:

```
s % 26  ≈  s - floor(s / 26) * 26
         =  s - (s * (1/26)) * 26
```

`1/26` ≈ `2521 / 65536` (since 65536 = 2^16).  
`2521 / 65536 = 0.03848...` vs `1/26 = 0.03846...` — close enough for valid input ranges.

So `(s * 2521) >> 16` computes `floor(s / 26)`, and the full expression `s - floor(s/26) * 26` = `s % 26`.

**Why bother?** Integer division is slower than multiplication + shift on some architectures. This avoids the `%` operator entirely.

---

## Key Differences Between Solutions

| | Solution (clean) | Solution2 (bit-hack) |
|---|---|---|
| Letter index | `c - 'a'` | `c & 0x1F - 1` |
| Modulo | `sum % 26` | `s - (s * 2521 >> 16) * 26` |
| Case sensitive | Yes (lowercase only) | No (works for both) |
| Readability | ✅ Clear | ❌ Needs explanation |
| Speed | Negligible difference in practice | Micro-optimised |

---

## Common Mistakes

- **Off-by-one on index:** `weights[c - 'a']` not `weights[c - 'a' + 1]`. The array is 0-indexed.
- **Not resetting `sum`:** If you accumulate across words without resetting, every word after the first gives wrong results.
- **Forgetting `% 26`:** Without it, `'z' - sum` can produce characters outside `'a'–'z'` for heavy-weight words.
- **Bit-hack operator precedence:** `word.charAt(i) & (1 << 5) - 1` — the `- 1` binds to `(1 << 5)` first due to precedence, giving `& 31`. Writing `& 31` directly is safer and clearer.