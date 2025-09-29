
# 438. Find All Anagrams in a String — How & Why

## Problem Restated

You are given two strings `s` and `p`. Find all the start indices in `s` where an anagram of `p` begins.

**Example:**

```java

Input: s = "cbaebabacd", p = "abc"
Output: [0, 6]
Explanation:

* "cba" at index 0 is an anagram of "abc"
* "bac" at index 6 is an anagram of "abc"

```

---

## 1. Brute Force Approach

**Idea**:  

- Generate every substring of length `p.length()` in `s`.
- Sort both substring and `p` and compare.  
- If equal → valid anagram start index.

**Walkthrough (s="cbaebabacd", p="abc")**:

- Substrings of length 3: `"cba"`, `"bae"`, `"aeb"`, …, `"acd"`.
- Sort `"cba"` → `"abc"`, matches `p`.
- Sort `"bac"` → `"abc"`, matches `p`.

**Complexity**:

- Substring extraction: **O(n * m log m)** (n = |s|, m = |p|).
- Very slow for large inputs.

---

## 2. Sliding Window + Frequency Arrays (Your Solution)

**Key Insight**:  
Two strings are anagrams if their character counts match.  

- Maintain two frequency arrays of size 26 (for lowercase letters):
  - `freq1` → current sliding window in `s`
  - `freq2` → counts of `p`
- Use a sliding window of length `p.length()`:
  - Add new char entering window.
  - Remove old char leaving window.
  - Compare arrays each step.

**Step-by-step Walkthrough**:

```java

s = "cbaebabacd", p = "abc"
p.length = 3

Initialize:
freq2 = [a:1, b:1, c:1]
freq1 = [c:1, b:1, a:1]  → matches at index 0

Slide window:
remove 'c', add 'e'
window = "bae"
freq1 = [a:1, b:1, e:1] → not match

Slide window:
remove 'b', add 'b'
window = "aeb"
freq1 = [a:1, b:1, e:1] → not match

…

At index 6:
window = "bac"
freq1 = [a:1, b:1, c:1] → matches again

```java
Result = `[0, 6]`.

**Complexity**:
- Each window update: **O(1)**.
- Frequency comparison: **O(26) ≈ O(1)**.
- Overall: **O(n)** time, **O(26) ≈ O(1)** space.

---

## 3. Optimized Variant — Sliding Window with Match Count
Instead of calling `Arrays.equals` at every step:
- Maintain a count of how many characters currently match `freq2`.
- When adding/removing chars:
  - Update only affected entries.
  - If all 26 match → valid anagram.

**Tradeoff**:
- Slightly trickier to implement.
- Faster in practice (since avoids repeated array comparison).

---

## ✨ Conclusion
- **Brute Force**: Sort substrings, too slow.  
- **Sliding Window with Arrays** (Your Code): Clean and efficient **O(n)**.  
- **Sliding Window with Match Count**: More optimized, avoids repeated full-array checks.  

✅ Your implementation is already optimal and clear.

---
