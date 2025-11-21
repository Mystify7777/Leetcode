# Recap

Given a string `s`, return the number of **unique palindromic subsequences of length three** that are a subsequence of `s`. A palindrome of length 3 has the form `aba` where the first and last characters are the same. Note that subsequences do not need to be contiguous.

## Intuition

For a length-3 palindrome `aba`, the first and last characters must be the same. The key insight is to iterate through each letter of the alphabet and find the **first and last occurrence** of that letter in the string. Then, count how many **distinct characters** appear between these two positions—each distinct character can serve as the middle character of a unique palindrome.

## Approach

1. **Create a pair array** of size 26 (one for each lowercase letter) to store the first and last occurrence indices of each character.
   - Initialize all pairs to `(-1, -1)`.

2. **Scan the string once** to populate first and last occurrences:
   - For each character at index `i`:
     - If it's the first time seeing this character, set `firstOcc = i`.
     - Otherwise, update `secondOcc = i` (keeps getting updated to the last occurrence).

3. **For each letter** (iterate through the 26 pairs):
   - If both `firstOcc` and `secondOcc` are valid (not `-1`) and different:
     - Use a `HashSet` to count distinct characters between `firstOcc + 1` and `secondOcc - 1`.
     - Add the size of the set to the answer (each distinct middle character forms a unique palindrome).

4. Return the total count.

**Example:** For `s = "aabca"`:

- Letter `a`: first at index 0, last at index 4. Between them: `{'a', 'b', 'c'}` → 3 palindromes: `aaa`, `aba`, `aca`.
- Letter `b`: appears only once, skip.
- Letter `c`: appears only once, skip.
- Total: 3 unique palindromes.

## Code (Java)

```java
class Pair {
    int firstOcc;
    int secondOcc;
    Pair(int firstOcc, int secondOcc) {
        this.firstOcc = firstOcc;
        this.secondOcc = secondOcc;
    }
}

class Solution {
    public int countPalindromicSubsequence(String s) {
        Pair arr[] = new Pair[26];
        for (int i = 0; i < 26; i++) {
            arr[i] = new Pair(-1, -1);
        }
        
        for (int i = 0; i < s.length(); i++) {
            if (arr[s.charAt(i) - 'a'].firstOcc == -1) {
                arr[s.charAt(i) - 'a'].firstOcc = i;
            } else {
                arr[s.charAt(i) - 'a'].secondOcc = i;
            }
        }
        
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].firstOcc != -1 && arr[i].secondOcc != -1) {
                HashSet<Character> hs = new HashSet<>();
                for (int j = arr[i].firstOcc + 1; j < arr[i].secondOcc; j++) {
                    hs.add(s.charAt(j));
                }
                cnt += hs.size();
            }
        }
        return cnt;
    }
}
```

## Correctness

- **Subsequence property:** We only care about first and last occurrences of outer characters, not all pairs, because choosing the outermost positions maximizes the range for middle characters.

- **Uniqueness:** For each fixed outer character `c`, the palindromes `cXc` are uniquely determined by the middle character `X`. Using a `HashSet` ensures we count each distinct middle character exactly once.

- **Completeness:** By iterating through all 26 possible outer characters and counting distinct middle characters for each, we enumerate all possible unique length-3 palindromes.

- **No double counting:** Different outer characters produce disjoint sets of palindromes (e.g., `aba` and `cac` are different because outer characters differ).

## Complexity

- **Time:** `O(n)` to scan the string and populate first/last occurrences, plus `O(26 * n)` worst case to check middle characters (at most 26 letters, each checking at most `n` positions) = `O(n)` overall.
- **Space:** `O(26)` for the pair array plus `O(26)` for the HashSet = `O(1)` (constant with respect to input size).

## Edge Cases

- **String length < 3:** No palindromes possible, return `0`.
- **All same character** (e.g., `"aaaa"`): Only one unique palindrome `aaa`, return `1`.
- **No repeated characters** (e.g., `"abc"`): No character appears twice, return `0`.
- **Two occurrences with no gap** (e.g., `"aa"`): `secondOcc - firstOcc = 1`, no middle characters, return `0`.
- **Maximum distinct palindromes:** String like `"abcdefghijklmnopqrstuvwxyza"` where `a` is at both ends with 25 distinct characters in between → 25 palindromes for letter `a`.

## Takeaways

- **Fixing outer elements** and varying inner elements is a common pattern for palindrome counting problems.
- **First and last occurrence** strategy optimally captures the widest range for middle characters.
- **HashSet for distinctness** is the idiomatic way to count unique elements in a range.
- **Alphabet-based iteration** (26 letters) keeps complexity bounded regardless of string length.
- This approach generalizes: for longer palindromes, fix outer characters and recursively count inner structures.
