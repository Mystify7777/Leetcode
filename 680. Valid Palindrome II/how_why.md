# How & Why: LeetCode 680 - Valid Palindrome II

---

## Problem Restatement
We are given a string `s` and need to determine whether it can become a palindrome after deleting **at most one character**.

---

## How to Solve

### Step 1: Two-Pointer Technique
We use two pointers:
- `left` starting at index 0.
- `right` starting at the last index.

We move them towards each other while checking characters.

### Step 2: Handle Mismatch
- If `s[left] == s[right]`, continue moving inward.
- If mismatch occurs (`s[left] != s[right]`):
  - Try skipping the left character → check if substring `s[left+1..right]` is palindrome.
  - Try skipping the right character → check if substring `s[left..right-1]` is palindrome.
  - If either case forms a palindrome, return `true`.

```java
if (s.charAt(left) != s.charAt(right)) {
    return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
}
```

### Step 3: Helper Function
The helper `isPalindrome(s, left, right)` checks if a substring is a palindrome by standard two-pointer comparison.

### Step 4: Return Result
If no mismatch is found, the string is already a palindrome, so return `true`.

---

## Why This Works
- A valid palindrome mismatch can only occur **once** if we are allowed at most one deletion.
- By checking both possible deletions (`left` or `right`), we cover all scenarios.
- The helper ensures correctness without scanning the whole string multiple times unnecessarily.

---

## Complexity Analysis
- **Time Complexity**: O(n), where n = length of `s`.
  - Each character is checked once.
  - At most one extra palindrome check is performed.
- **Space Complexity**: O(1), no extra storage used.

---

## Example Walkthrough
Input:
```
s = "abca"
```

Process:
- Compare `a` and `a` → ok.
- Compare `b` and `c` → mismatch.
- Check `isPalindrome("bca", left+1)` → `bca` → false.
- Check `isPalindrome("abc", right-1)` → `aba` → true.

Output:
```
true
```

---

## Alternate Approaches
1. **Brute Force Deletion**:
   - Remove each character once and check if the result is a palindrome.
   - Complexity: O(n²), too slow for large inputs.

2. **Recursive Palindrome Check**:
   - Use recursion instead of an explicit helper.
   - Equivalent logic, but may be less efficient due to stack overhead.

### Optimal Choice
The **two-pointer + helper check approach** is optimal:
- Linear time.
- Constant space.
- Directly models the “skip one character” requirement.

---

## Key Insight
The key is realizing that at most **one mismatch is allowed**. When it occurs, we only need to check two possible substrings to confirm validity.

