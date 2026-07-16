# Sum of GCD of Formed Pairs

**LeetCode 3867**

---

# Problem Overview

Given an integer array

```
arr
```

we first transform it into a new array.

For every index

```
i
```

replace

```
arr[i]
```

with the GCD of

```
Maximum element seen so far

and

arr[i]
```

Formally,

```
pref[i] = gcd(prefixMaximum, arr[i])
```

where

```
prefixMaximum

=

max(arr[0...i])
```

After this transformation,

sort the new array.

Finally,

pair

- the smallest with the largest,
- the second smallest with the second largest,
- and so on,

and return the sum of the GCD of every pair.

---

# Example

Suppose

```
arr

=

[6,4,9,3]
```

---

## Step 1

Compute prefix maximums

```
6
6
9
9
```

---

## Step 2

Replace every element

```
gcd(6,6)=6

gcd(6,4)=2

gcd(9,9)=9

gcd(9,3)=3
```

New array

```
[6,2,9,3]
```

---

## Step 3

Sort

```
[2,3,6,9]
```

---

## Step 4

Form pairs

```
2 9

3 6
```

---

## Step 5

Compute GCD

```
gcd(2,9)=1

gcd(3,6)=3
```

Answer

```
1+3

=

4
```

---

# Key Observation

The problem is divided into three completely independent tasks.

1. Compute prefix maximums.
2. Transform every element using GCD.
3. Pair smallest with largest after sorting.

No optimization trick is required.

The implementation simply follows these steps.

---

# Step 1

## Compute Prefix Maximum

Maintain

```
mx
```

representing the largest value seen so far.

Initially

```
mx = 0
```

For every element

```
mx = max(mx, arr[i])
```

Example

```
6

↓

6

↓

9

↓

9
```

---

# Step 2

## Compute the New Value

Instead of storing the prefix maximum,

store

```
gcd(mx, arr[i])
```

```java
prefi[i] = gcd(mx, arr[i]);
```

Example

```
mx = 9

current = 3

↓

gcd(9,3)=3
```

This creates the transformed array.

---

# Why Use Prefix Maximum?

The problem definition explicitly requires each element to be combined with the largest value encountered before it (including itself).

Therefore,

each transformed value depends only on

```
Current element

+

Largest prefix element
```

Nothing else.

---

# Step 3

## Sort

```java
Arrays.sort(prefi);
```

Sorting places numbers in ascending order.

Example

```
6 2 9 3

↓

2 3 6 9
```

---

# Step 4

## Pair Smallest with Largest

Two pointers are used.

```
i

↓

Smallest

j

↓

Largest
```

Example

```
2 3 6 9

^

      ^
```

Pairs

```
2 9

3 6
```

---

# Step 5

## Compute Pair GCD

For every pair

```
sum += gcd(prefi[i], prefi[j]);
```

Move

```
i++

j--
```

until both pointers meet.

---

# Why Pair This Way?

The problem statement specifically asks us to pair

```
Smallest

↓

Largest
```

rather than adjacent elements.

Therefore,

after sorting,

the pairing order is fixed.

No searching or optimization is necessary.

---

# GCD Function

The implementation uses the classic Euclidean Algorithm.

```java
while (b != 0) {
    int temp = b;
    b = a % b;
    a = temp;
}
```

This repeatedly replaces

```
(a,b)

↓

(b,a%b)
```

until

```
b

=

0
```

At that point,

```
a
```

is the greatest common divisor.

---

# Correctness

The algorithm directly follows the problem specification.

1. Compute the prefix maximum at every index.
2. Replace each element by the GCD of itself and the current prefix maximum.
3. Sort the transformed array.
4. Pair the smallest and largest remaining values.
5. Sum the GCD of every formed pair.

Since every step exactly matches the required procedure, the final result is correct.

---

# Complexity Analysis

Let

```
N = arr.length
```

---

## Prefix Transformation

Each element is processed once.

```
O(N)
```

---

## Sorting

```
O(N log N)
```

---

## Pairing

Each element is visited once.

```
O(N)
```

---

## Total Time Complexity

```
O(N log N)
```

The sorting step dominates.

---

# Space Complexity

The implementation stores

```
prefi[]
```

which requires

```
O(N)
```

extra space.

---

# Alternative Implementation

The repository also contains another solution:

```java
Solution2
```

Instead of creating a separate array,

it transforms the original array directly.

```java
A[i] = gcd(A[i], max);
```

The remaining steps are identical:

1. Sort.
2. Pair smallest with largest.
3. Sum the GCDs.

Both implementations have the same

```
O(N log N)
```

time complexity.

The only difference is that one uses a separate array while the other modifies the original array in place.

---

# Summary

The solution is a straightforward implementation of the required process:

1. Traverse the array while maintaining the prefix maximum.
2. Replace each value with the GCD of itself and the current prefix maximum.
3. Sort the transformed values.
4. Pair the smallest element with the largest, the second smallest with the second largest, and so on.
5. Compute and accumulate the GCD of each pair.

The only non-linear operation is sorting, giving an overall complexity of

```
O(N log N)
```

with

```
O(N)
```

additional space.