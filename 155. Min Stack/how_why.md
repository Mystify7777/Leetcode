# How & Why — LeetCode 155: Min Stack

Design a stack that supports `push`, `pop`, `top`, and `getMin` in constant time.

---

## Problem

Implement a `MinStack` class with the following methods:
- `push(int val)` — push element onto the stack.
- `pop()` — remove the element on top of the stack.
- `top()` — get the top element.
- `getMin()` — retrieve the minimum element in the stack in O(1) time.

---

## Key idea

Keep track of the minimum at every stack level. Two common ways:

1. Maintain a second stack that tracks minimums.
2. Store the current minimum alongside each element (as a pair or in node fields).

Both allow O(1) time for all operations; memory overhead is O(n).

---

## Approach A — Two stacks (value stack + min stack)

- `s` stores all pushed values.
- `mins` stores the current minimum value at or below each level.

push(val):
- `s.push(val)`
- if `mins` is empty or `val <= mins.peek()` then `mins.push(val)`

pop():
- if `s.pop() == mins.peek()` then `mins.pop()`

top(): `s.peek()`

getMin(): `mins.peek()`

### Java (two-stack)

```java
import java.util.Deque;
import java.util.ArrayDeque;

class MinStack {
    private Deque<Integer> s;
    private Deque<Integer> mins;

    public MinStack() {
        s = new ArrayDeque<>();
        mins = new ArrayDeque<>();
    }

    public void push(int val) {
        s.push(val);
        if (mins.isEmpty() || val <= mins.peek()) mins.push(val);
    }

    public void pop() {
        int top = s.pop();
        if (top == mins.peek()) mins.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return mins.peek();
    }
}
```

---

## Approach B — Single stack storing pairs (value + current min)

Store a pair `(val, currentMin)` for each pushed element. The `currentMin` is the minimum value for the stack at and below that entry. This avoids managing a second stack explicitly.

### Java (single-stack pairs)

```java
import java.util.Deque;
import java.util.ArrayDeque;

class MinStack {
    private static class Pair { int val, min; Pair(int v, int m) { val = v; min = m; } }
    private Deque<Pair> s;

    public MinStack() {
        s = new ArrayDeque<>();
    }

    public void push(int val) {
        if (s.isEmpty()) s.push(new Pair(val, val));
        else s.push(new Pair(val, Math.min(val, s.peek().min)));
    }

    public void pop() {
        s.pop();
    }

    public int top() {
        return s.peek().val;
    }

    public int getMin() {
        return s.peek().min;
    }
}
```

---

## Why this works

- Both methods attach the current minimum value to each stack level. When popping, the minimum automatically reverts to the previous value.
- All operations use constant-time stack primitives.

---

## Complexity

- Time: O(1) for `push`, `pop`, `top`, `getMin`.
- Space: O(n) additional for storing mins (either via `mins` stack or per-node pair).

---

## Example walkthrough

Operations:

```java
MinStack obj = new MinStack();
obj.push(-2);
obj.push(0);
obj.push(-3);
obj.getMin(); // -> -3
obj.pop();
obj.top();    // -> 0
obj.getMin(); // -> -2
```

State by step (using single-stack pairs representation):

| Step | Operation      | Stack (top -> bottom)         | getMin() |
|------|----------------|-------------------------------|----------|
| 1    | push(-2)       | [(-2, -2)]                    | -2       |
| 2    | push(0)        | [(0, -2), (-2, -2)]           | -2       |
| 3    | push(-3)       | [(-3, -3), (0, -2), (-2,-2)]  | -3       |
| 4    | getMin()       | ...                           | -3       |
| 5    | pop()          | [(0, -2), (-2,-2)]            | -2       |
| 6    | top()          | ...                           | top -> 0 |
| 7    | getMin()       | ...                           | -2       |

Result sequence: `-3`, then after pop `top() = 0`, `getMin() = -2`.

---

## Alternatives and notes

- A linked-list node that stores `min` with each node is equivalent to the pair approach.
- There's also a clever single-stack trick that stores differences to avoid extra space in some languages, but it's more complex and less readable.

---

✅ Recommendation: Use the single-stack pair approach for clarity, or the two-stack approach if you prefer separate structures.


