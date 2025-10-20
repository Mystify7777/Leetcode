# ✅ **How_Why.md**

## **1. Problem Summary**

You start with a variable `X = 0`. Each operation in the array is one of these four forms:

```java
"++X", "X++"  → X increases by 1
"--X", "X--"  → X decreases by 1
```

Return the final value of `X` after performing all operations.

---

## **2. Brute Force Idea**

**What it does:**
Check each string. If it contains `"++"` then `X++`, else if it contains `"--"` then `X--`.

**Code idea:**

```java
for (String op : operations) {
    if (op.contains("+")) x++;
    else x--;
}
```

**Limitation:**
Works fine, but:

* `contains()` has overhead
* More characters checked than necessary
* Not elegant / not optimal for competitive coding

**Example Walkthrough**
Operations: `["X++", "--X", "X++"]`

```java
X = 0
"X++" → X = 1
"--X" → X = 0
"X++" → X = 1
Result → 1
```

---

## **3. Optimized Approach (Your Trick)**

Key observation:
The **middle character** in every operation is either `'+'` or `'-'`.

| Operation    | Middle char | ASCII | Contribution   |
| ------------ | ----------- | ----- | -------------- |
| "++X", "X++" | `+`         | `43`  | `44 - 43 = +1` |
| "--X", "X--" | `-`         | `45`  | `44 - 45 = -1` |

So instead of checking the whole string, do this:

```java
x += (44 - op.charAt(1));
```

Because:

* If `charAt(1)` is `'+'` → `44 - 43 = +1`
* If `charAt(1)` is `'-'` → `44 - 45 = -1`

This reduces the whole logic to a **single arithmetic instruction**.

**Optimized Code**

```java
class Solution {
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for(String o : operations) x += (44 - o.charAt(1));
        return x;
    }
}
```

**Example Walkthrough**

```java
ops = ["++X", "X--", "X++"]

x = 0
"++X" → 44 - '+'(43) = +1 → x = 1
"X--" → 44 - '-'(45) = -1 → x = 0
"X++" → +1 → x = 1

Result = 1
```

---

## **4. What to Do / What Not to Do**

✅ Prefer reading the **minimal required character** instead of scanning entire string
✅ Use ASCII tricks when the pattern is guaranteed and fixed
❌ Don’t overuse character tricks if input format is not strict
❌ Don’t use `contains("+")` or multiple `if` checks unless needed

---

## **5. Final Takeaway**

When operations are **pattern-fixed and predictable**, reduce the logic to the smallest character-based condition. It leads to cleaner, faster competitive code.

---
