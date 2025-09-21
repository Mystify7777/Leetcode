# How\_Why.md: Letter Combinations of a Phone Number (LeetCode 17)

## Problem

Given a string containing digits from `2-9`, return all possible letter combinations that the number could represent, using the classic phone keypad mapping:

```java
2 → "abc", 3 → "def", 4 → "ghi", 5 → "jkl", 6 → "mno", 7 → "pqrs", 8 → "tuv", 9 → "wxyz"
```

**Example:**

```java
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

---

## Brute-force Approach

### Idea

* Recursively generate all possible combinations by concatenating letters for each digit.
* At each step, add a letter corresponding to the current digit to a new string and pass it to the next recursion.

### Code

```java
private List<String> pd(String p, String up) {
    if (up.isEmpty()) {
        List<String> list = new ArrayList<>();
        list.add(p);
        return list;
    }

    int digit = up.charAt(0) - '0';
    int start = (digit - 2) * 3;
    int end = start + 3;
    if (digit == 7) end = start + 4;
    if (digit == 8) { start = 19; end = 22; }
    if (digit == 9) { start = 22; end = 26; }

    List<String> result = new ArrayList<>();
    for (int i = start; i < end; i++) {
        char ch = (char) ('a' + i);
        result.addAll(pd(p + ch, up.substring(1)));
    }
    return result;
}
```

### Example Walkthrough

For `digits = "23"`:

1. Start with `p = ""`, `up = "23"`.
2. First digit `'2'` → letters `"abc"`.
3. Recursively append `'a'` → `p = "a"`, `up = "3"`.
4. Next digit `'3'` → letters `"def"`.
5. Generate `"ad"`, `"ae"`, `"af"`.
6. Repeat for `'b'` and `'c'`.

**Observation:** At every recursion:

* New string `p + ch` is created.
* New list is created and merged via `addAll()`.

### Limitation

* **String concatenation is expensive** in Java (`O(n)` for length of `p`).
* **New list creation and `addAll` merging** for each recursive call is memory and time intensive.
* Complexity blows up for larger input: **O(4^N \* N)** instead of O(4^N).

---

## Optimized Approach (Your Older Solution)

### Idea_

* Use a **single StringBuilder** to build combinations in-place.
* Pass a single result list reference instead of creating new lists.
* Avoid repeated string copying.

### Code_

```java
private void backtrack(String digits, int idx, StringBuilder comb, List<String> res, Map<Character, String> digitToLetters) {
    if (idx == digits.length()) {
        res.add(comb.toString());
        return;
    }

    String letters = digitToLetters.get(digits.charAt(idx));
    for (char letter : letters.toCharArray()) {
        comb.append(letter);
        backtrack(digits, idx + 1, comb, res, digitToLetters);
        comb.deleteCharAt(comb.length() - 1);
    }
}
```

### Walkthrough

For `digits = "23"`:

1. Start with empty `StringBuilder`.
2. Append `'a'` → recurse.
3. Append `'d'` → recursion base reached → add `"ad"` to `res`.
4. Backtrack: delete last char → `"a"`.
5. Append `'e'` → add `"ae"` → backtrack.
6. Continue until all combinations generated.

**Observation:**

* **No repeated string creation.**
* **Single `res` list accumulates results.**
* Complexity: **O(4^N)**, optimal.

---

## Summary of Differences

| Aspect          | Brute-force (`p + ch` + `addAll`)                       | Optimized (StringBuilder + single list) |
| --------------- | ------------------------------------------------------- | --------------------------------------- |
| String Handling | Creates new string each recursion                       | Uses one StringBuilder (in-place)       |
| List Handling   | Creates new list each recursion, merges with `addAll()` | Single list accumulates results         |
| Memory Usage    | High due to repeated objects                            | Minimal                                 |
| Time Complexity | O(4^N \* N)                                             | O(4^N)                                  |
| Performance     | Slow, especially for large N                            | Fast, scales well                       |

---

### Key Takeaways

1. **Avoid repeated string concatenation in recursion** → use mutable structures (`StringBuilder`) for in-place changes.
2. **Avoid creating temporary lists in recursion** → pass a single result list reference.
3. Small changes like these drastically reduce runtime for combinatorial problems.

---
