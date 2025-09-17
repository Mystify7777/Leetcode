# How_Why.md

## Problem

Evaluate the value of an arithmetic expression in **Reverse Polish Notation (RPN)**.
  
- Valid operators: `+`, `-`, `*`, `/`  
- Each operand may be an integer or another expression.  
- Division between two integers truncates toward zero.  

---

## How (Step-by-step Solution)

### Approach 1: Iterative with Stack

1. Initialize an empty stack `S`.
2. Iterate through each token in `tokens`:
   - If the token is a **number**, push it onto the stack.
   - If the token is an **operator**:
     - Pop the top two numbers (`b = pop()`, `a = pop()`).
     - Apply the operation (`a op b`).
     - Push the result back onto the stack.
3. After processing all tokens, the final result is the single element left in the stack.

---

### Approach 2: Recursive Parsing (Alternative)

- Start from the **end** of the tokens.
- Each time we encounter an operator, recursively evaluate the last two operands.
- Each time we encounter a number, return it.
- Works like a recursive tree evaluation of the expression.

---

## Why (Reasoning)

- **RPN properties**: No parentheses needed, order of operations is implicit.  
  - When an operator appears, the last two operands on the stack are its arguments.  
- **Stack works perfectly** because RPN is left-to-right and naturally forms a last-in-first-out evaluation order.  

- Recursive solution is elegant but less efficient due to call stack usage.  
- Iterative stack-based solution is simpler, more common, and efficient.

---

## Complexity Analysis

- **Time Complexity**: O(n) → each token processed once.  
- **Space Complexity**: O(n) in the worst case (all numbers in the stack).  

---

## Example Walkthrough

### Input

```text
tokens = ["2","1","+","3","*"]
```

### Execution

- Push 2 → stack: [2]

- Push 1 → stack: [2, 1]

- `+` → pop 1 and 2 → 2+1=3 → push → stack: [3]

- Push 3 → stack: [3, 3]

- `*` → pop 3 and 3 → 3*3=9 → push → stack: [9]

### Output

`9`

---

## Alternate Approaches

1. Recursive (from right to left)

    - Elegant but may hit recursion limits for large inputs.

    - Matches expression tree evaluation naturally.

2. Deque instead of Stack

    - Using ArrayDeque instead of Stack (preferred in Java as Stack is legacy).

    - Slight performance improvement.

## Notes

- Division must truncate toward zero → Java’s integer division already satisfies this.

- Be careful with operand order:

  - For a - b, ensure a is the first popped value.

  - For a / b, ensure the same.

---
