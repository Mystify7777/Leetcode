# Sorted GCD Pair Queries

**LeetCode 3312**

---

# Problem Overview

Given an integer array

```
nums
```

consider **every unordered pair**

```
(i, j)

where

i < j
```

For each pair, compute

```
gcd(nums[i], nums[j])
```

Collect all these GCD values into a list and sort the list in **ascending order**.

For every query

```
queries[i]
```

return the GCD value at that position in the sorted list.

---

# Example

```
nums = [2,4,6]
```

Pairs

```
(2,4) → gcd = 2

(2,6) → gcd = 2

(4,6) → gcd = 2
```

Sorted GCD list

```
2 2 2
```

Queries

```
0 → 2

1 → 2

2 → 2
```

---

# Why Brute Force Fails

Suppose

```
N = 100000
```

Number of pairs

```
N(N-1)/2

≈

5×10^9
```

Computing every GCD is impossible.

We need to count pairs **without generating them.**

---

# Key Observation

Instead of asking

> What is the GCD of every pair?

ask

> How many pairs have GCD exactly equal to g?

If we know this for every

```
g
```

then we can reconstruct the sorted list without explicitly storing it.

---

# Overall Approach

The solution performs four major steps.

```
Frequency

↓

Count multiples

↓

Count exact GCDs

↓

Prefix sums + Binary Search
```

---

# Step 1

## Frequency Array

Create

```
freq[x]
```

meaning

```
How many times x appears in nums.
```

Example

```
nums

=

2 2 3 6
```

Frequency

```
freq[2]=2

freq[3]=1

freq[6]=1
```

---

# Step 2

## Count Multiples

For every possible GCD

```
g
```

count how many numbers are divisible by

```
g
```

```java
for (int m = g; m <= max; m += g)
    cnt += freq[m];
```

Store

```
multiples[g]
```

Example

```
nums

=

2 3 4 6
```

For

```
g=2
```

Multiples

```
2

4

6
```

Count

```
3
```

Therefore

```
multiples[2]=3
```

---

# Step 3

## Count Pairs Divisible by g

If

```
k
```

numbers are divisible by

```
g
```

then

```
k choose 2
```

pairs have GCD **at least**

```
g
```

Formula

```
k(k-1)/2
```

Example

```
k=5

↓

5×4/2

=

10
```

pairs.

---

# But There's a Problem

Those pairs do **not necessarily have GCD exactly equal to g**.

Example

```
Numbers

6

12

18
```

All are divisible by

```
3
```

But every pair actually has

```
GCD

=

6
```

Some pairs are counted multiple times.

We must remove the overcounting.

---

# Step 4

## Inclusion-Exclusion

Process GCD values from

```
largest

↓

smallest
```

Initially

```
total

=

pairs divisible by g
```

Then subtract all pairs already assigned to larger multiples.

```java
for (int m = 2*g; m <= max; m += g)
    total -= exact[m];
```

Finally

```
exact[g]

=

pairs whose GCD is exactly g
```

---

# Why Processing Backwards Works

Suppose

```
g=2
```

Pairs divisible by

```
2
```

include pairs whose GCD is

```
2

4

6

8

...
```

If

```
exact[4]

exact[6]

exact[8]
```

are already known,

subtracting them leaves only

```
exactly 2
```

This is classic inclusion-exclusion.

---

# Example

Suppose

```
Pairs divisible by 2

=

20
```

Already known

```
GCD=4

↓

6 pairs

GCD=6

↓

3 pairs
```

Then

```
exact[2]

=

20

-

6

-

3

=

11
```

---

# Step 5

## Prefix Sum

Now we know

```
exact[g]
```

for every

```
g
```

Construct

```
pref[g]
```

meaning

```
Number of pairs

whose GCD ≤ g
```

```java
pref[g]

=

pref[g-1]

+

exact[g]
```

---

# Example

Suppose

```
exact

1→4

2→3

3→5
```

Then

```
pref

1→4

2→7

3→12
```

Meaning

```
First 4 values are GCD=1

Next 3 values are GCD=2

Next 5 values are GCD=3
```

---

# Step 6

## Binary Search

Each query asks

```
Which GCD appears at position k?
```

Instead of constructing the entire sorted list,

search inside

```
pref
```

We want the first

```
g
```

such that

```
pref[g]

≥

k+1
```

That

```
g
```

is exactly the answer.

---

# Example

Suppose

```
pref

1→5

2→8

3→12
```

Query

```
6
```

Since

```
pref[1]=5

<6+1
```

and

```
pref[2]=8

≥7
```

Answer

```
2
```

---

# Why Binary Search Works

The prefix array is monotonically increasing.

```
4

7

12

15

...
```

Monotonic arrays allow binary search.

Each query becomes

```
O(log M)
```

instead of scanning all GCD values.

---

# Correctness

The algorithm works because:

1. Every number divisible by `g` contributes to the pool of pairs whose GCD is **at least** `g`.
2. Inclusion-exclusion removes pairs already assigned to larger GCDs, leaving only pairs whose GCD is **exactly** `g`.
3. The prefix sum represents the sorted multiset of all GCD values without explicitly constructing it.
4. Binary search finds the smallest GCD whose cumulative count reaches the queried position.

Thus every query returns exactly the GCD value that would appear at that position in the sorted list.

---

# Complexity Analysis

Let

```
M = maximum value in nums
```

---

## Frequency

```
O(N)
```

---

## Counting Multiples

```
O(M log M)
```

This is the harmonic series over multiples.

---

## Inclusion-Exclusion

Also

```
O(M log M)
```

---

## Prefix Sum

```
O(M)
```

---

## Binary Search

Each query

```
O(log M)
```

Total

```
O(Q log M)
```

---

## Overall Time Complexity

```
O(N + M log M + Q log M)
```

---

## Space Complexity

Arrays

```
freq

multiples

exact

pref
```

Total

```
O(M)
```

---

# Why This Approach?

The naïve solution would generate every pair, compute every GCD, sort the results, and answer queries.

That requires

```
O(N²)
```

pairs,

which is infeasible.

Instead, this solution never constructs any pair.

It counts how many pairs belong to each possible GCD using number theory, then reconstructs the sorted order through cumulative counts.

---

# Summary

The algorithm transforms an impossible pair-generation problem into a counting problem.

1. Count the frequency of every value.
2. For every possible GCD, count how many numbers are divisible by it.
3. Compute the number of pairs with **exactly** that GCD using inclusion-exclusion.
4. Build prefix sums over the GCD counts.
5. Use binary search to answer each query directly from the cumulative counts.

This avoids generating or sorting billions of pair GCDs while still returning the exact GCD value for every queried position.