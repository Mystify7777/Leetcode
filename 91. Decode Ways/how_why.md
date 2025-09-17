# How_Why.md

## Problem

A message containing letters from `A-Z` is encoded as numbers using the mapping:
  
- `'A' -> "1"`, `'B' -> "2"`, …, `'Z' -> "26"`.

Given a string `s` containing only digits, return the **number of ways to decode it**.  

---

## How (Step-by-step Solution)

### Approach: Dynamic Programming (Bottom-Up)

1. **Define DP state**  
   - Let `dp[i]` = number of ways to decode the substring starting at index `i`.

2. **Base Case**  
   - `dp[n] = 1` → one valid decoding for an empty substring.

3. **Transition**  
   For each index `i` (iterating backwards):
   - If `s[i] == '0'` → cannot decode → `dp[i] = 0`.  
   - Otherwise:
     - **Single digit decode** → add `dp[i+1]`.  
     - **Two digit decode** (valid if `s[i..i+1]` is between "10" and "26") → add `dp[i+2]`.

4. **Answer**  
   - The result is stored in `dp[0]`.

---

## Why (Reasoning)

- Each character can be decoded either:
  1. Alone (`s[i]` as a letter), or  
  2. With the next character (`s[i..i+1]` as a letter, if valid).  

- This naturally leads to a recursive structure, but DP is used to **avoid recomputation**.  
- Bottom-up ensures we build from the simplest substrings to the whole string.

---

## Complexity Analysis

- **Time Complexity**: O(n) → each index processed once.  
- **Space Complexity**: O(n) → DP array of size `n+1`.  
- Can be optimized to **O(1) space** since we only need the last two states.

---

## Example Walkthrough

### Input

```text
s = "226"
```

### Step 1: Initialize

```text
n = 3
dp = [0, 0, 0, 1]   // dp[3] = 1 (empty string)

```

### Step 2: Fill DP backwards

- i = 2: s[2] = '6' → valid single decode
    → dp[2] = dp[3] = 1

- i = 1: s[1] = '2' → valid single decode (dp[2] = 1)

  - two-digit "26" is valid (dp[3] = 1)
    → dp[1] = 1 + 1 = 2

- i = 0: s[0] = '2' → valid single decode (dp[1] = 2)

  - two-digit "22" is valid (dp[2] = 1)
    → dp[0] = 2 + 1 = 3

### Final Answer

```text
dp[0] = 3

```

---

## Explanation

"226" can be decoded as:

- "2 2 6" → "BBF"

- "22 6" → "VF"

- "2 26" → "BZ"

---

## Alternate Approaches

1. Recursion + Memoization

    - Top-down recursive solution with caching.

    - Easier to reason about but slower due to recursion overhead.

2. Space Optimization

    - Use two variables instead of an array (prev1, prev2).

    - Keep only last two states to achieve O(1) space.

---
