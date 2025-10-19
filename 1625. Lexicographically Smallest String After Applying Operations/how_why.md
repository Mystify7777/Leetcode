
# Lexicographically Smallest String After Operations — How & Why

## 🧩 Problem Summary

You are given a numeric string `s`. You may apply **two operations any number of times**:

1. **Add `a` to all digits at odd indices** (mod 10)  
2. **Rotate the string right by `b` positions**

Your goal:  
➡️ **Find the lexicographically smallest string reachable by these operations.**

---

## 🔴 Brute Force — Why It Fails

A naive idea is:

- Try all possible sequences of add & rotate operations
- Track the smallest string

But because each position can be modified infinitely and rotations loop, the search space **explodes**.

| Issue | Reason |
|---------|--------|
| Infinite add cycles | every digit → +a → cycle of length `10/gcd(a,10)` |
| Too many states | rotations × digit-mod loops × string length |

❌ **Time blows up → not practical**

---

## 🟡 Working Approach — BFS (Correct + Practical)

### 💡 Key Insight

- Both operations **always produce repeatable states**
- Once a string has been seen before, any further operations from it are redundant
- So we treat each generated string as a **graph node**
- Perform **BFS**, track `visited`, and keep updating the **smallest string so far**

### 🛠️ Pseudocode

```java

queue = [s]
visited = {s}
best = s

while queue not empty:
cur = queue.pop()

best = min(best, cur)

next1 = applyAdd(cur)
next2 = applyRotate(cur)

if next1 not visited: push
if next2 not visited: push
```

---

## ✏️ Example Walkthrough (New Example)

Let:

```java

s = "83071"
a = 3
b = 2

```

Start:

```java

best = "83071"
queue = ["83071"]

```

Step 1 — Apply operations:

| Operation | Result |
|-----------|---------|
| Add to odd indices | 86001 |
| Rotate right by 2 | 71830 |

Update:

```java

visited = {83071, 86001, 71830}
best = "71830"
queue = ["86001", "71830"]

```

Next BFS layers generate more strings, and **the smallest lexicographic one eventually wins**. BFS guarantees we explore all valid states **without repetition**.

✅ **Always correct**  
⚠️ Might still explore many states, but `visited` keeps it finite.

---

## 🟢 Faster Approach — GCD Optimization (Why It Works)

### 🧠 Observations

1. **Rotation cycles**  
   A rotation by `b` repeats every `gcd(b, n)` positions → we only need to check that many rotations.

2. **Digit add cycles**
   Adding `a` repeatedly cycles the digit every `10 / gcd(a, 10)` steps.

So the total meaningful states are bounded by:

```yaml

rotation_states × add_states

```

This dramatically cuts search space.

### 🌟 Result

- Much faster
- Zero BFS overhead
- Still finds the best string

---

## 📌 Final Comparison

| Approach | Correct | Speed | Notes |
|----------|:-------:|:------:|-------|
| Brute Force | ✅ | ❌ | infinite-like state space |
| BFS + Visited | ✅ | ✅ | clean & reliable |
| GCD-Optimized | ✅ | 🌟 FAST | best for large input |

---

## ✅ What to Do

✔️ Use hashing / visited to avoid cycles  
✔️ Compare lexicographically at each step  
✔️ Exploit rotation + digit add periodicity

## ❌ What NOT to Do

✘ Don’t recurse or brute-simulate infinitely  
✘ Don’t re-visit states  
✘ Don’t assume rotation or add creates infinite uniques — they loop

---

## 🧾 Key Takeaways

- The problem is a **state-graph search**
- BFS works because **we detect repeated states**
- GCD math lets us skip unnecessary states
- The lexicographically smallest result will always be found by exploring **only the unique reachable strings**

---
