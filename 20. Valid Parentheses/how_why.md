
# How & Why: LeetCode 20 - Valid Parentheses

This solution uses a Stack, a classic data structure for this type of problem, to validate the order and pairing of parentheses.

---

## Problem Restatement

You are given a string `s` containing just the characters '(', ')', '{', '}', '[' and ']'. Your task is to determine if the input string is valid.

An input string is valid if:
- Open brackets must be closed by the same type of brackets.
- Open brackets must be closed in the correct order.
- Every close bracket has a corresponding open bracket of the same type.

### Example

**Input:**
```
s = "()[]{}"
```
**Output:**
```
true
```

---

## How to Solve

The problem involves matching pairs in a nested structure, which is a perfect use case for a Stack. A stack follows a "Last-In, First-Out" (LIFO) principle, which mirrors how parentheses must be closed. The innermost opening bracket must be the first one to be closed.

1. **Initialize a Stack:** Create an empty stack to keep track of the open brackets we encounter.
2. **Iterate Through the String:** Process each character of the string one by one.
3. **Handle Characters:**
    - If you see an opening bracket ('(', '{', '['), push it onto the stack.
    - If you see a closing bracket (')', '}', ']'), you must check if it matches the most recently opened one.
    - Check if the stack is empty. If it is, there's no open bracket to match, so the string is invalid.
    - Peek at the top of the stack. If the closing bracket is the correct partner for the opening bracket at the top of the stack, then pop the stack (as the pair is now successfully closed).
    - If it's not the correct partner, the string is invalid.
4. **Final Check:** After iterating through the entire string, if the stack is empty, it means every opening bracket found a matching closing bracket. The string is valid. If the stack is not empty, it means there are unclosed opening brackets, so the string is invalid.

### Implementation

```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // If it's an opening bracket, push it onto the stack.
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } 
            // If it's a closing bracket...
            else {
                // ...but the stack is empty, there's no matching open bracket.
                if (stack.isEmpty()) {
                    return false;
                }
                // Check if the top of the stack forms a valid pair.
                char top = stack.peek();
                if ((c == ')' && top == '(') ||
                    (c == '}' && top == '{') ||
                    (c == ']' && top == '[')) {
                    // If it's a valid pair, pop the opening bracket.
                    stack.pop();
                } else {
                    // Mismatched bracket type.
                    return false;
                }
            }
        }

        // If the stack is empty at the end, all brackets were matched.
        return stack.isEmpty();
    }
}
```

---

## Why This Works

- **LIFO Principle:** The nested structure of parentheses requires that the last one opened must be the first one closed. For example, in `[ ( { } ) ]`, the `{` is opened last, so it must be closed first. A stack naturally enforces this LIFO order.
- **State Management:** The stack acts as a memory of which brackets are currently "open" and waiting for a partner. Pushing adds to this memory, and popping signifies a successful match and removal.
- **Completeness Check:** The final check (`stack.isEmpty()`) is crucial. It ensures that there are no leftover, unclosed brackets like in the string "(".

---

## Complexity Analysis

- **Time Complexity:** $O(n)$, where $n$ is the length of the string. We iterate through the string once, and each stack operation (push, pop, peek) takes constant time.
- **Space Complexity:** $O(n)$. In the worst-case scenario, the string consists only of opening brackets (e.g., "((((("), and the stack would need to store all $n$ characters.

---

## Example Walkthrough

**Input:**
```
s = "{[]}"
```

**Process:**

- Initial: `stack = []`.
- char = '{': It's an opening bracket. Push onto stack. `stack = ['{']`.
- char = '[': It's an opening bracket. Push onto stack. `stack = ['{', '[']`.
- char = ']': It's a closing bracket. Stack is not empty. `stack.peek()` is '['. This is a valid pair. Pop the stack. `stack = ['{']`.
- char = '}': It's a closing bracket. Stack is not empty. `stack.peek()` is '{'. This is a valid pair. Pop the stack. `stack = []`.
- Loop finishes.

**Final Check:** Is the stack empty? Yes. Return true.

---

## Key Insight

This problem is the quintessential example of when to use a stack. Whenever a problem involves matching pairs, checking for balanced structures, or handling operations that require a "last-in, first-out" order of processing (like nested function calls or XML/HTML tag validation), a stack should be the first data structure that comes to mind.