# Find Greatest Common Divisor of Array

**LeetCode 1979**

---

# Problem Overview

Given an integer array

```
nums
```

find the **Greatest Common Divisor (GCD)** of the **smallest** and **largest** elements in the array.

Return

```
gcd(min(nums), max(nums))
```

---

# Example 1

Input

```
nums = [2,5,6,9,10]
```

Smallest element

```
2
```

Largest element

```
10
```

GCD

```
gcd(2,10)

=

2
```

Output

```
2
```

---

# Example 2

Input

```
nums = [7,5,6,8,3]
```

Minimum

```
3
```

Maximum

```
8
```

GCD

```
gcd(3,8)

=

1
```

Output

```
1
```

---

# Key Observation

The problem **does not ask** for the GCD of all elements.

It specifically asks for the GCD of only

```
Minimum element

and

Maximum element.
```

Therefore,

the rest of the elements are only needed to determine these two values.

---

# Approach

The solution has two simple steps.

```
Find minimum and maximum

↓

Compute their GCD
```

---

# Step 1

## Find the Minimum and Maximum

Initialize

```java
int min = nums[0];
int max = nums[0];
```

Traverse the array once.

```java
if (nums[i] < min)
    min = nums[i];

if (nums[i] > max)
    max = nums[i];
```

Example

```
nums

=

5 2 8 6 3
```

Traversal

```
Start

min=5

max=5

↓

2

min=2

↓

8

max=8

↓

6

↓

3
```

Final values

```
min = 2

max = 8
```

---

# Step 2

## Compute the GCD

Now compute

```java
gcd(min, max)
```

using the Euclidean Algorithm.

---

# Euclidean Algorithm

The implementation repeatedly performs

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

The remaining value of

```
a
```

is the GCD.

---

## Example

Find

```
gcd(18,30)
```

Iteration 1

```
30

18
```

because

```
30 % 18 = 12
```

Now

```
18

12
```

Iteration 2

```
12

6
```

because

```
18 % 12 = 6
```

Iteration 3

```
6

0
```

Stop.

Answer

```
6
```

---

# Why the Euclidean Algorithm Works

Suppose

```
a > b
```

Every common divisor of

```
a

and

b
```

also divides

```
a % b
```

Therefore

```
gcd(a,b)

=

gcd(b,a%b)
```

Repeating this process keeps reducing the numbers until the remainder becomes zero.

At that point,

the current value is the greatest common divisor.

---

# Correctness

The algorithm directly follows the problem statement.

1. Find the smallest element.
2. Find the largest element.
3. Compute the GCD of those two values.

Since the problem asks for exactly this quantity, the returned value is correct.

---

# Complexity Analysis

Let

```
N = nums.length
```

---

## Finding Minimum and Maximum

Each element is visited once.

```
O(N)
```

---

## Computing GCD

The Euclidean Algorithm runs in

```
O(log(max))
```

which is very small compared to scanning the array.

---

## Overall Time Complexity

```
O(N)
```

---

## Space Complexity

Only a few integer variables are used.

```
O(1)
```

---

# Why Only the Minimum and Maximum?

A common misconception is that we need to compute the GCD of every element.

For example,

```
nums

=

6 12 18
```

The GCD of all elements is

```
6
```

But this problem is different.

It asks only for

```
gcd(min,max)

=

gcd(6,18)

=

6
```

Another example

```
nums

=

6 9 15
```

Minimum

```
6
```

Maximum

```
15
```

Answer

```
gcd(6,15)

=

3
```

Even though the middle element is never used in the GCD calculation.

---

# Comparing the Two Implementations

Both `Solution` and `Solution2` use the same algorithm:

1. Traverse the array to determine the minimum and maximum values.
2. Apply the Euclidean Algorithm to compute their GCD.

The only differences are stylistic:

- `Solution2` initializes the minimum as `1001`, leveraging the problem constraint that values are at most `1000`.
- `Solution` initializes both `min` and `max` using the first element of the array, which is a more general approach and works regardless of value constraints.

Both implementations have identical time and space complexity.

---

# Summary

The solution is straightforward:

1. Scan the array once to find the minimum and maximum elements.
2. Compute the GCD of these two numbers using the Euclidean Algorithm.
3. Return the result.

The algorithm runs in

```
O(N)
```

time with

```
O(1)
```

extra space, making it both simple and efficient.