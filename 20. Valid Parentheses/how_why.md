
# How\_Why.md: Valid Parentheses

## Problem

Given a string `s` containing only the characters `'('`, `')'`, `'{'`, `'}'`, `'['`, and `']'`, determine if the input string is **valid**.

A string is valid if:

1. Open brackets must be **closed by the same type** of brackets.
2. Open brackets must be **closed in the correct order**.

**Example:**

```java
Input: s = "([{}])"
Output: true
```

---

## Brute-force Approach

### Idea

* Continuously **replace matching pairs** `"()"`, `"[]"`, `"{}"` with empty string.
* Repeat until no changes occur.
* If the resulting string is empty → valid.

### Code

```java
public boolean isValidBF(String s) {
    String prev;
    do {
        prev = s;
        s = s.replace("()", "").replace("[]", "").replace("{}", "");
    } while (!s.equals(prev));
    return s.isEmpty();
}
```

### Example Walkthrough

* Input: `"([{}])"`

1. Replace `"{}"` → `"([])"`
2. Replace `"()"` → `"[]"`
3. Replace `"[]"` → `""`

* Result empty → valid

### Limitation

* **Time complexity:** O(n²) in worst case (due to repeated string replacements)
* **Space complexity:** O(n) for new strings
* Not efficient for long strings.

---

## Optimized Approach (Stack)

### Idea_

* Use a **stack** to track open brackets.
* For each character:

  1. If it’s an **opening bracket**, push to stack.
  2. If it’s a **closing bracket**, check if it matches the **top of the stack**.

     * If match → pop the stack
     * Else → invalid
* After processing all characters:

  * Stack empty → valid
  * Stack not empty → invalid

### Code_

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();

    for (char cur : s.toCharArray()) {
        if (!stack.isEmpty() && isPair(stack.peek(), cur)) {
            stack.pop();
        } else {
            stack.push(cur);
        }
    }
    
    return stack.isEmpty();        
}

private boolean isPair(char open, char close) {
    return (open == '(' && close == ')') ||
           (open == '{' && close == '}') ||
           (open == '[' && close == ']');
}
```

### Example Walkthrough_

* Input: `"([{}])"`

1. Stack empty → push `'('` → stack: `['(']`
2. `'['` → push → stack: `['(', '[']`
3. `'{'` → push → stack: `['(', '[', '{']`
4. `'}'` → top = `'{'` → match → pop → stack: `['(', '[']`
5. `']'` → top = `'['` → match → pop → stack: `['(']`
6. `')'` → top = `'('` → match → pop → stack: `[]`

* Stack empty → valid

### Advantages

* **Time complexity:** O(n)
* **Space complexity:** O(n) for the stack
* Handles long strings efficiently.

---

## Key Takeaways

1. Brute-force string replacement is simple but **inefficient**.
2. **Stack approach** is standard for matching nested structures.
3. Always check **stack emptiness** at the end to ensure all brackets are closed.

---
