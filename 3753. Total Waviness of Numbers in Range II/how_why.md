# Total Waviness of Numbers in Range II

**LeetCode 3753**

---

# Problem Overview

Given two integers `num1` and `num2`, we need to compute the **total waviness** of every number in the range:

```
[num1, num2]
```

The waviness of a number is defined as the number of **internal digits** that form either a:

- **Peak**
- **Valley**

For every digit except the first and last:

```
a b c
```

The middle digit `b` contributes **1** to the waviness if

```
Peak:
b > a && b > c

Valley:
b < a && b < c
```

Otherwise it contributes **0**.

Example

```
535

3 is not checked (first digit)

Digit 3 in the middle:

5 3 5

3 < 5
3 < 5

Valley

waviness = 1
```

Example

```
24242

4 -> Peak
2 -> Valley
4 -> Peak

waviness = 3
```

The goal is

```
Σ waviness(x)

for every x in [num1, num2]
```

---

# Why Brute Force Fails

The obvious solution is

```
for every number
    compute waviness
```

Complexity

```
Range Size × Number of Digits
```

If

```
num2 = 10^15
```

there are trillions of numbers.

Impossible.

We need something that counts many numbers simultaneously.

---

# Observation

Instead of asking

> What is the waviness of this number?

we ask

> How many numbers have a peak/valley at each position?

This transforms the problem into a counting problem.

Counting problems are exactly what **Digit DP** is designed for.

---

# Prefix Function

Like many range Digit DP problems,

instead of directly computing

```
[num1, num2]
```

we define

```
solve(x)
```

meaning

```
Total waviness of every number

0...

x
```

Then

```
Answer

=

solve(num2)

-

solve(num1-1)
```

This is standard prefix subtraction.

---

# Digit DP Idea

Suppose

```
num = 543278
```

We build the number digit by digit.

At every position we remember enough information to know whether a future digit creates a peak or valley.

---

# State Definition

Our DP state is

```
dp(
    index,
    tight,
    started,
    lastDigit,
    secondLastDigit
)
```

Meaning

We have already fixed digits before `index`.

---

## index

Current digit being processed.

---

## tight

```
1
```

Current prefix is identical to the original number.

Therefore we cannot exceed the current digit.

```
0
```

We are already smaller.

We may freely choose

```
0..9
```

---

## started

Leading zeros are ignored.

Before the first non-zero digit,

```
001234
```

should behave exactly like

```
1234
```

This flag tells us whether the actual number has started.

---

## lastDigit

The previous digit.

Needed because waviness depends on neighbors.

---

## secondLastDigit

The digit before lastDigit.

Needed because when choosing

```
newDigit
```

we immediately know whether

```
secondLastDigit

lastDigit

newDigit
```

forms

```
Peak

or

Valley
```

---

# Transition

Suppose current digits are

```
7 3
```

Now we choose

```
5
```

We immediately know

```
7 3 5
```

Middle digit

```
3
```

is a valley.

So

```
Contribution = 1
```

Otherwise

```
Contribution = 0
```

Then recurse.

---

# What Does DP Return?

Each state returns

```
(count,
 totalWaviness)
```

instead of only one value.

---

## count

How many valid numbers exist below this state.

---

## totalWaviness

Combined waviness of all those numbers.

---

Suppose recursion returns

```
count = 120

waviness = 450
```

If the current transition contributes

```
+1
```

then every one of those

```
120
```

numbers gains one extra waviness.

So

```
newWaviness

=

450

+

1 × 120
```

This is exactly what the implementation does.

```java
totalWaviness += res[1] + wavinessContribution * res[0];
```

---

# Why We Need Count

Imagine

Current prefix

```
132
```

Remaining suffix

```
****
```

There may be

```
12,543
```

possible completions.

If

```
3
```

is already confirmed to be a peak,

then every completion contains that peak.

Instead of adding

```
1
```

twelve thousand times,

we simply do

```
count × 1
```

Huge saving.

---

# Memoization

Many states repeat.

Example

```
index = 5

tight = 0

started = 1

last = 7

secondLast = 3
```

This state can be reached through many prefixes.

Without memoization

the same subtree is recomputed repeatedly.

Therefore we cache

```
count
```

and

```
waviness
```

separately.

```java
memoCount

memoWaviness
```

The next visit simply returns the cached answer.

---

# Base Case

When

```
index == length
```

the number has been completely formed.

Return

```
count = 1

waviness = 0
```

because

there is exactly one completed number,

and no more digits remain to create peaks or valleys.

---

# Complexity

Let

```
D = number of digits
```

Possible states

```
D

×

2 (tight)

×

2 (started)

×

11 (last)

×

11 (secondLast)
```

Approximately

```
D × 484
```

Each state tries

```
10 digits
```

Overall

```
O(D × 484 × 10)

≈ O(D)
```

Since

```
D ≤ 19
```

for a 64-bit integer,

this is extremely fast.

---

# Why the Formula Works

Whenever we place a new digit,

the previous digit now has both neighbors available.

That means we can immediately determine whether

```
previous digit
```

is

- Peak
- Valley
- Neither

That contribution is permanent.

Every recursive completion inherits it.

Thus

```
Current Contribution

+

Future Contributions
```

becomes

```
res[1]
+
contribution × res[0]
```

which correctly accumulates the total waviness across every valid number.

---

# About the First Solution

The repository also contains another implementation:

```java
Solution
```

Unlike the Digit DP solution, it does **not** enumerate digit states.

Instead, it derives a set of mathematical counting formulas that directly compute the number of peaks and valleys contributed at each digit position by analyzing decimal cycles (similar to digit-counting formulas used for counting occurrences of a digit from `1` to `N`).

Its stages count contributions from progressively larger digit groups, avoiding recursion entirely.

This approach runs in linear time with respect to the number of digits and is extremely fast, but the derivation is highly mathematical and significantly harder to understand and derive compared to the Digit DP solution.

For learning purposes, the Digit DP implementation is generally preferred because the same technique applies to a wide variety of digit-counting problems beyond this specific one.

---

# Summary

The algorithm works because:

1. Convert the range query into two prefix queries.
2. Build numbers one digit at a time.
3. Keep the last two digits in the state.
4. When placing a new digit, determine whether the previous digit becomes a peak or valley.
5. Return both the number of valid completions and their accumulated waviness.
6. Use memoization so every DP state is solved only once.
7. Subtract two prefix answers to obtain the final range result.

This transforms an impossible brute-force search over trillions of numbers into a computation involving only a few thousand DP states.

