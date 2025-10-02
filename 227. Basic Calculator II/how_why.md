# How_Why.md – Basic Calculator II (LeetCode 227)

## ❌ Brute Force Idea

We want to evaluate a string math expression like:

```
"3+2*2"
```

Allowed operators: `+ - * /` (integer division, no parentheses).

**Naïve attempt**:

* Directly scan and compute left to right.
* But this breaks operator precedence: `"3+2*2"` would become `((3+2)*2)=10` instead of correct `3+(2*2)=7`.

So brute force fails because **operator precedence** (`*` and `/` before `+` and `-`) is ignored.

---

## ✅ Approach 1 – Using a Stack (O(n), O(n) space)

Idea:

* Keep a stack.
* Traverse the string, build numbers.
* When hitting an operator or end of string:

  * If previous operator is `+`, push number.
  * If `-`, push `-number`.
  * If `*` or `/`, pop from stack, apply operation, push result.
* At end, sum stack values.

**Code (Stack-based):**

```java
class Solution {
    public int calculate(String s) {
        Stack<Integer> st = new Stack<>();
        int num = 0;
        char operator = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (isOperator(c) || i == s.length() - 1) {
                if (operator == '+') st.push(num);
                else if (operator == '-') st.push(-num);
                else if (operator == '*') st.push(st.pop() * num);
                else if (operator == '/') st.push(st.pop() / num);

                num = 0;
                operator = c;
            }
        }

        int ans = 0;
        while (!st.isEmpty()) ans += st.pop();
        return ans;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
```

---

## ✅ Approach 2 – Optimized (O(n), O(1) space)

We can avoid the stack by keeping track of:

* `last` → the most recent number affected by `*` or `/`.
* `sum` → cumulative sum of evaluated `+` and `-` parts.
* At end, add `last` to `sum`.

### Example Walkthrough: `"3+2*2"`

* Start: `sum=0, last=0, num=0, operator='+'`
* Read `3` → operator `+`: `sum=0, last=3`.
* Read `+`.
* Read `2` → operator `+`: `sum=3, last=2`.
* Read `*`.
* Read `2` → operator `*`: `last=2*2=4`.
* End of string: `sum+last=3+4=7`. ✅

### Code (Optimized):

```java
class Solution {
    public int calculate(String s) {
        int num = 0;
        char operator = '+';
        int last = 0, sum = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (isOperator(c) || i == s.length() - 1) {
                if (operator == '+') {
                    sum += last;
                    last = num;
                } else if (operator == '-') {
                    sum += last;
                    last = -num;
                } else if (operator == '*') {
                    last *= num;
                } else if (operator == '/') {
                    last /= num;
                }

                num = 0;
                operator = c;
            }
        }
        return sum + last;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
```

---

## 📊 Complexity

* **Time:** O(n), one pass over string.
* **Space:**

  * Stack solution: O(n).
  * Optimized solution: O(1).

---

## ✅ Key Takeaways

* Operator precedence is the main challenge.
* Stack solution is easier to write/debug.
* Optimized solution tracks last evaluated value to eliminate stack usage.

---
