# Find the Number of Subsequences With Equal GCD

**LeetCode 3336**

---

# Problem Overview

Given an integer array

```
nums
```

we need to form **two subsequences**.

Each element has exactly three choices:

- Ignore it
- Put it in the first subsequence
- Put it in the second subsequence

The two subsequences must satisfy

- both are non-empty
- both have the **same GCD**

Return the total number of valid pairs modulo

```
1,000,000,007
```

---

# Example

```
nums = [2,4]
```

Possible assignments

```
2 -> seq1
4 -> seq2

GCDs

2
4

Not equal
```

---

```
2 -> seq1
4 -> seq1

seq2 empty

Invalid
```

---

```
2 -> seq1
2 -> seq2

Impossible
```

Only assignments where

```
GCD(seq1) == GCD(seq2)
```

are counted.

---

# Observation

For every element there are exactly

```
3
```

choices.

```
Ignore

↓

Seq1

↓

Seq2
```

A brute force search explores

```
3^N
```

possibilities.

Even for

```
N = 30
```

this becomes enormous.

We need Dynamic Programming.

---

# Solution 1

## Memoized DP

This is the intuitive solution.

---

# State Definition

We process the array from left to right.

The DP state is

```
dp(index, gcd1, gcd2)
```

Meaning

```
We have processed

nums[0...index-1]

Current GCD of sequence1

=

gcd1

Current GCD of sequence2

=

gcd2
```

---

# Why Store GCD Instead of Entire Sequence?

The future only depends on the **current GCD**.

Suppose

```
Seq1

=

[12,18]

GCD

=

6
```

It doesn't matter how we obtained

```
6
```

Only the current GCD affects future transitions.

This dramatically reduces the state space.

---

# Representing an Empty Sequence

Initially,

both subsequences are empty.

Instead of introducing another boolean,

the solution uses

```
GCD = 0
```

to represent

```
No elements selected yet.
```

When the first number is inserted,

```
GCD(0,x)

=

x
```

which is handled explicitly.

---

# DP Transition

For every element,

there are three possibilities.

---

## 1. Ignore

```
solve(i+1,g1,g2)
```

Nothing changes.

---

## 2. Put into Sequence 1

New GCD

```
gcd(g1,current)
```

If

```
g1=0
```

the new GCD becomes

```
current
```

Transition

```
solve(i+1,newGcd,g2)
```

---

## 3. Put into Sequence 2

Exactly symmetric.

```
solve(i+1,g1,newGcd)
```

---

# Base Case

When

```
index==n
```

the entire array has been processed.

We accept only if

```
g1 != 0

g2 != 0

g1==g2
```

Otherwise

```
0
```

valid assignments.

---

# Why Memoization Works

Different assignment orders may reach the same

```
(index,g1,g2)
```

Once we know the answer for that state,

it never changes.

Therefore

```
dp[index][g1][g2]
```

stores the result.

Future visits simply reuse it.

---

# Complexity

Maximum GCD

```
200
```

State count

```
N × 201 × 201
```

Each state performs

```
3
```

transitions.

Overall

```
O(N × 200²)
```

which easily fits the constraints.

---

# Why GCD Shrinks the State Space

Notice

```
nums

=

[180,120,60]
```

Possible subsequences are enormous,

but their GCDs can only be

```
1...

200
```

The DP compresses many different subsequences into the same state.

---

# Solution 2

## Number Theory + Möbius Inversion

The second solution is dramatically different.

Instead of constructing subsequences,

it counts them mathematically.

This is much faster but considerably harder to derive.

---

# Main Idea

Instead of asking

> What is the exact GCD?

ask

> How many subsequences have every element divisible by g?

This is much easier to count.

---

# Counting Multiples

First,

compute

```
cnt[x]
```

meaning

```
How many numbers are multiples of x?
```

Example

```
nums

=

2 4 6 12
```

Then

```
cnt[2]=4

cnt[3]=2

cnt[4]=2

cnt[6]=2
```

This is computed efficiently using a sieve-like process.

---

# Why LCM Appears

Suppose

```
Sequence1

GCD multiple of g1

Sequence2

GCD multiple of g2
```

Any number belonging to **both** subsequences must be divisible by

```
LCM(g1,g2)
```

Hence

```
lcm(g1,g2)
```

naturally appears in the counting formula.

---

# Counting Assignments

Every element divisible by

```
LCM(g1,g2)
```

has

```
3
```

choices

```
Ignore

Seq1

Seq2
```

Therefore

```
3^count
```

appears.

Elements divisible by only one divisor contribute

```
2
```

choices,

leading to

```
2^count
```

terms.

These are precomputed as

```
pow2

pow3
```

---

# Why Möbius Inversion?

The counts obtained so far include

```
GCD multiple of g
```

rather than

```
GCD exactly g
```

Example

```
GCD

=

6
```

is also counted when considering

```
3
```

because

```
6

is divisible by

3
```

To isolate the exact GCD,

we must remove overcounting.

This is precisely what

**Möbius Inversion**

does.

---

# Möbius Function

The array

```
mu[]
```

stores the Möbius values.

Using

```
μ(n)
```

the algorithm performs inclusion-exclusion over multiples.

Conceptually,

it says

```
Count multiples

↓

Subtract larger multiples

↓

Add back overlaps

↓

Obtain exact GCD
```

---

# Final Formula

After counting all assignments,

the answer becomes

```
Σ μ(i) μ(j)

×

f(...)
```

where

```
f
```

counts assignments based on multiples,

and the Möbius coefficients remove all overcounting.

---

# Complexity

Counting multiples

```
O(M log M)
```

where

```
M ≤ 200
```

Möbius inversion

```
O(M² log M)
```

Still extremely fast because

```
M

=

200
```

---

# Comparing the Two Solutions

## Solution 1

Uses

- Dynamic Programming
- Recursive state transitions
- GCD compression

Advantages

- Easy to understand
- Easy to implement
- General technique applicable to many DP problems

---

## Solution 2

Uses

- Number theory
- Least Common Multiple
- Counting multiples
- Möbius inversion
- Inclusion-exclusion

Advantages

- Much faster
- Elegant mathematically

Disadvantages

- Significantly harder to derive
- Requires knowledge of advanced number theory

---

# Why Both Solutions Work

The DP solution explicitly builds every possible assignment while remembering only the current GCDs.

The mathematical solution never constructs subsequences at all. Instead, it counts how many assignments satisfy divisibility conditions and then uses Möbius inversion to isolate assignments whose GCDs are exactly equal.

Although they approach the problem from completely different directions, both compute the same quantity:

```
Number of ordered pairs of non-empty subsequences
whose GCDs are equal.
```

---

# Summary

The repository demonstrates two fundamentally different ways to solve the problem.

### Dynamic Programming

1. Process the array left to right.
2. Keep track of the current GCD of both subsequences.
3. For every element, choose one of three actions:
   - Ignore it
   - Add it to the first subsequence
   - Add it to the second subsequence
4. Memoize repeated states.
5. Count only states where both subsequences are non-empty and have the same GCD.

### Number-Theoretic Approach

1. Count how many numbers are divisible by each value.
2. Use LCM to determine common divisibility.
3. Count assignments using powers of 2 and 3.
4. Apply Möbius inversion to remove overcounting and recover counts for **exact** GCD values.

The first solution is ideal for understanding the problem, while the second showcases how powerful number-theoretic transformations can replace explicit state exploration.