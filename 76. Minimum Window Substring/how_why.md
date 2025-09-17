# How_Why.md

## Problem

Given two strings `s` and `t`, return the **minimum window substring** of `s` such that every character in `t` (including duplicates) is included in the window.  
If no such substring exists, return the empty string `""`.

---

## How (Step-by-step Solution)

### Approach: Sliding Window with HashMap

1. **Frequency Map of `t`**  
   Build a hashmap `charCount` that stores the frequency of each character in `t`.

2. **Initialize Variables**
   - `targetCharsRemaining = t.length()` → total number of characters we still need to match.
   - `minWindow = {0, Integer.MAX_VALUE}` → stores best window (start, end).
   - `startIndex = 0` → left pointer of sliding window.

3. **Expand Window (Right Pointer `endIndex`)**
   - For each character `ch = s.charAt(endIndex)`:
     - If `ch` is in `charCount` and `charCount[ch] > 0`, decrement `targetCharsRemaining`.
     - Update `charCount[ch]--`.

4. **Contract Window (Left Pointer `startIndex`)**
   - Once `targetCharsRemaining == 0`, we have a valid window.
   - Try to shrink the window from the left:
     - While the start character can be removed (count < 0), update `charCount[start]++` and move `startIndex++`.

5. **Update Minimum Window**
   - If the current window is smaller than the previously recorded minimum, update `minWindow`.

6. **Restore Window Validity**
   - Before moving forward, restore counts for the character at `startIndex` and increase `targetCharsRemaining`.

7. **Return Result**
   - If a valid window exists, return substring from `minWindow[0]` to `minWindow[1]`.
   - Else return `""`.

---

## Why (Reasoning)

- The problem requires **both coverage (all chars of `t`) and minimality (smallest window)**.  
- The **sliding window technique** is perfect because:
  - Expanding right ensures coverage.
  - Shrinking left ensures minimality.
- HashMap ensures we track exact frequencies of characters, including duplicates.

---

## Complexity Analysis

- **Time Complexity**: O(n), where `n = s.length()`.  
  Each character is visited at most twice (once by `endIndex`, once by `startIndex`).  
- **Space Complexity**: O(m), where `m = t.length()` (hashmap size for required characters).

---

## Example Walkthrough

### Input

```s = "ADOBECODEBANC"```
```t = "ABC"```

### Steps

- Build `charCount = {A:1, B:1, C:1}`  
- Expand window until all chars covered: "ADOBEC" (valid)  
- Shrink from left: "DOBEC" → "OBEC" → "BEC" (still valid)  
- Update minWindow = "BEC"  
- Continue expanding and shrinking → final minWindow = "BANC".  

### Output

```"BANC"```

---

## Alternate Approaches

1. **Brute Force**: Generate all substrings, check validity → O(n³). Not feasible.  
2. **Sliding Window (Optimal)**: O(n) time with hashmap tracking → efficient and widely used in substring problems.

✅ **Optimal Method**: Sliding Window with HashMap.
