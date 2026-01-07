
# 242. Valid Anagram — how/why

## Recap

Given two strings `s` and `t`, determine if `t` is an anagram of `s` (same character multiset, possibly different order). Only lowercase English letters are used.

## Intuition

Two strings are anagrams iff every character appears the same number of times in both. Instead of sorting, we can **count** each letter in `s`, subtract using `t`, and verify all totals return to zero.

## Approach (Counting Array)

1) Create `count[26]` initialized to zero.
2) For each char in `s`, increment `count[c - 'a']`.
3) For each char in `t`, decrement `count[c - 'a']`.
4) If any entry is nonzero, the counts differ → not an anagram; otherwise true.

Early exit: if lengths differ, immediately return false.

## Code (Java)

```java
class Solution {
	public boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) return false;

		int[] count = new int[26];
		for (char c : s.toCharArray()) count[c - 'a']++;
		for (char c : t.toCharArray()) count[c - 'a']--;

		for (int x : count) if (x != 0) return false;
		return true;
	}
}
```

## Correctness

- Each increment records one occurrence from `s`; each decrement cancels one matching occurrence from `t`.
- If strings are anagrams, every character count nets to zero, so the final array is all zeros.
- Any nonzero entry means a character appears a different number of times, so the strings cannot be anagrams.

## Complexity

- Time: `O(n)` over string length; single passes for counting.
- Space: `O(1)` extra; fixed 26-sized array.

## Edge Cases

- Different lengths → immediately false.
- Empty strings → true (both empty counts zero).
- Repeated single character ("aaaa" vs "aaaa") handled by counts.
- Non-overlapping characters ("abc", "def") quickly detected by nonzero counts.

## Alternative (Sorting)

Sort both strings and compare for equality. Simpler to reason about but costs `O(n log n)` time and `O(1)` or `O(n)` space depending on implementation; counting is strictly faster for fixed alphabet.
