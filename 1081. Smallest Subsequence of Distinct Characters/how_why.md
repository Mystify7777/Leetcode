# Smallest Subsequence of Distinct Characters

**LeetCode 1081**

---

# Problem Overview

Given a string

```
s
```

construct the **lexicographically smallest subsequence** that contains **every distinct character exactly once**.

A **subsequence** is formed by deleting zero or more characters without changing the order of the remaining characters.

---

## Example

Input

```
s = "bcabc"
```

Distinct characters

```
a
b
c
```

Possible subsequences

```
bca
bac
cab
abc
...
```

The smallest lexicographically is

```
abc
```

Output

```
"abc"
```

---

## Example 2

Input

```
s = "cbacdcbc"
```

Output

```
"acdb"
```

---

# Why Brute Force Fails

One approach is

```
Generate every subsequence

↓

Keep only those containing every distinct character once

↓

Choose the smallest
```

A string of length

```
N
```

has

```
2^N
```

subsequences.

This is completely infeasible.

---

# Key Observation

While scanning the string from left to right,

we want the answer to remain as small as possible.

Suppose the current answer is

```
d
```

and we encounter

```
b
```

Since

```
b < d
```

it would be better if

```
b
```

appeared earlier.

However,

we can remove

```
d
```

only if another

```
d
```

still appears later.

Otherwise,

we would lose that character forever.

This observation naturally leads to a **Monotonic Stack**.

---

# Data Structures

The algorithm maintains three pieces of information.

---

## 1. Frequency Array

```
count[c]
```

stores

```
How many occurrences of c
remain to be processed.
```

Initially,

every character frequency is counted.

As we traverse the string,

the count is decremented.

---

## 2. Used Array

```
used[c]
```

indicates whether

```
c
```

is already present in the answer.

This prevents duplicates.

---

## 3. Stack

The stack stores the current subsequence.

It always represents the best answer constructed so far.

The second implementation uses a `StringBuilder` as a stack,

while the first uses Java's `Stack<Character>`.

Both follow exactly the same algorithm.

---

# Step 1: Count Frequencies

Before processing,

count every character.

Example

```
cbacdcbc
```

Frequency

```
a → 1

b → 2

c → 4

d → 1
```

---

# Step 2: Process Characters

For every character

```
c
```

first decrement its remaining frequency.

```java
--count[c];
```

This means

```
count[c]
```

now represents

```
Occurrences remaining after this position.
```

---

# Step 3: Skip Duplicates

If the character is already in the stack,

ignore it.

```java
if (used[c])
    continue;
```

Since every distinct character should appear only once,

there is no reason to insert it again.

---

# Step 4: Maintain Lexicographical Order

This is the core idea.

While the stack is not empty,

we ask three questions.

---

## Condition 1

```
Top > Current ?
```

If

```
Top <= Current
```

then the current order is already optimal.

Stop.

---

## Condition 2

Does the top character appear again later?

```
count[top] > 0
```

If not,

we cannot remove it,

otherwise it disappears permanently.

---

## Condition 3

If both conditions hold,

remove the top character.

```java
used[top] = false;
stack.pop();
```

Repeat until no more improvements are possible.

---

# Example Walkthrough

Input

```
cbacdcbc
```

---

Read

```
c
```

Stack

```
c
```

---

Read

```
b
```

Current

```
b < c
```

Another

```
c
```

exists later.

Remove

```
c
```

Stack

```
b
```

---

Read

```
a
```

Again

```
a < b
```

Another

```
b
```

exists.

Remove

```
b
```

Stack

```
a
```

---

Read

```
c
```

Append

```
c
```

Stack

```
ac
```

---

Read

```
d
```

Append

```
acd
```

---

Read

```
c
```

Already used.

Skip.

---

Read

```
b
```

Top

```
d
```

Although

```
b < d
```

there is **no future d**.

So

```
d
```

must stay.

Append

```
b
```

Stack

```
acdb
```

---

Read

```
c
```

Already used.

Skip.

Final answer

```
acdb
```

---

# Why We Cannot Always Pop

Suppose

```
stack

=

ab

Current

=

a
```

If

```
b
```

never appears again,

removing it would make it impossible to include every distinct character.

Therefore,

we only pop characters that still have future occurrences.

---

# Why the Algorithm Produces the Smallest Answer

Whenever a smaller character appears,

the algorithm tries to move it earlier.

It removes larger characters **only when they can safely be reinserted later**.

Thus,

every pop operation improves the lexicographical order without losing any required character.

No further improvement is possible when processing finishes.

---

# Correctness

The algorithm maintains two invariants throughout the scan:

1. Every character appears at most once in the stack.
2. The stack is always the smallest lexicographical subsequence possible using the characters processed so far.

Whenever a larger character at the top can still appear later, replacing it with a smaller current character leads to a lexicographically smaller valid subsequence.

If the larger character does **not** appear again, it must remain.

Therefore, after processing the entire string, the stack contains every distinct character exactly once and is the lexicographically smallest valid subsequence.

---

# Complexity Analysis

Let

```
N = length of the string
```

---

## Frequency Counting

```
O(N)
```

---

## Traversal

Each character is

- pushed at most once
- popped at most once

Therefore

```
O(N)
```

---

## Total Time Complexity

```
O(N)
```

---

## Space Complexity

Frequency array

```
O(1)
```

Used array

```
O(1)
```

Stack

```
O(26)
```

Since the alphabet consists of lowercase English letters,

the auxiliary space is effectively

```
O(1)
```

(or `O(K)` where `K` is the alphabet size).

---

# Comparing the Two Implementations

## Solution 1

Uses

- `Stack<Character>`
- frequency array of size `27`
- `seen[]` array

It explicitly models a stack.

---

## Solution 2

Uses

- `StringBuilder` as a stack
- frequency array of size `128`
- `used[]` array

Instead of

```java
stack.pop()
```

it performs

```java
sb.setLength(sb.length() - 1);
```

The helper

```java
last(sb)
```

retrieves the top element.

---

Although the implementations differ slightly,

their logic is identical.

Both:

1. Count remaining frequencies.
2. Skip duplicate characters.
3. Remove larger characters that can safely appear later.
4. Append the current character.

---

# Summary

The solution uses a **monotonic stack** to build the answer greedily.

1. Count how many times each character appears.
2. Scan the string from left to right.
3. Skip characters already included.
4. While the current character is smaller than the stack's top **and** the top appears later, remove the top.
5. Insert the current character.
6. The stack finally contains every distinct character exactly once in the smallest possible lexicographical order.

By ensuring each character is pushed and popped at most once, the algorithm achieves an optimal **O(N)** time complexity.