# Rank Transform of an Array

**LeetCode 1331**

---

# Problem Overview

Given an integer array

```
arr
```

replace every element with its **rank**.

The ranking rules are:

1. The smallest element gets rank **1**.
2. Equal elements receive the **same rank**.
3. Larger elements receive larger ranks.
4. Ranks should be consecutive without gaps.

---

## Example

Input

```
arr = [40, 10, 20, 30]
```

Sorted order

```
10
20
30
40
```

Ranks

```
10 -> 1
20 -> 2
30 -> 3
40 -> 4
```

Output

```
[4,1,2,3]
```

---

## Example 2

Input

```
[100,100,100]
```

Since all values are equal,

```
100 -> Rank 1
```

Output

```
[1,1,1]
```

---

## Example 3

Input

```
[37,12,28,9,100,56,80,5,12]
```

Unique sorted values

```
5
9
12
28
37
56
80
100
```

Ranks

```
5   -> 1
9   -> 2
12  -> 3
28  -> 4
37  -> 5
56  -> 6
80  -> 7
100 -> 8
```

Final answer

```
[5,3,4,2,8,6,7,1,3]
```

---

# Observation

The rank of a number depends only on

```
How many distinct numbers are smaller than it.
```

Therefore,

we first need the numbers in sorted order.

---

# Approach

The solution consists of four steps.

```
Clone array

↓

Sort

↓

Remove duplicates

↓

Binary search each original element
```

---

# Step 1: Clone the Array

```java
int[] sorted = arr.clone();
```

The original order must be preserved because the final ranks should appear in the same positions.

So we work on a copy.

---

# Step 2: Sort the Copy

```java
Arrays.sort(sorted);
```

Now all numbers appear in ascending order.

Example

```
Original

40 10 20 30

↓

Sorted

10 20 30 40
```

---

# Step 3: Remove Duplicates

The sorted array may contain repeated values.

Example

```
1 2 2 2 5 5 7
```

We only need

```
1 2 5 7
```

The code performs this in-place.

```java
int m = 0;

for (int x : sorted) {
    if (m == 0 || sorted[m - 1] != x) {
        sorted[m++] = x;
    }
}
```

---

## How It Works

Suppose

```
sorted

=

1 2 2 2 5 5 7
```

Initially

```
m = 0
```

Read

```
1

↓

Store

1

m=1
```

Read

```
2

↓

Previous =1

Different

↓

Store

2

m=2
```

Read

```
2

↓

Previous =2

Duplicate

↓

Skip
```

Read

```
2

↓

Duplicate

↓

Skip
```

Read

```
5

↓

Different

↓

Store

5

m=3
```

Read

```
5

↓

Duplicate

↓

Skip
```

Read

```
7

↓

Store

7

m=4
```

Result

```
1 2 5 7
```

---

# Step 4: Copy Only Unique Elements

```java
int[] unique = Arrays.copyOf(sorted, m);
```

Now

```
unique
```

contains only distinct sorted numbers.

Example

```
5
9
12
28
37
56
80
100
```

---

# Step 5: Binary Search

For every element in the original array,

find its position in

```
unique
```

```java
Arrays.binarySearch(unique, arr[i])
```

Since arrays use

```
0-based indexing
```

add one.

```java
rank = index + 1
```

---

## Example

Unique array

```
5
9
12
28
37
56
80
100
```

Searching

```
37
```

returns

```
4
```

Therefore

```
Rank

=

4+1

=

5
```

---

# Why Binary Search?

The unique array is already sorted.

Searching linearly would require

```
O(N)
```

per element.

Binary Search reduces this to

```
O(log N)
```

---

# Correctness

The algorithm works because:

- Sorting places numbers in ascending order.
- Removing duplicates ensures identical values share the same rank.
- Binary Search finds the exact position of each value among the distinct sorted values.
- Since the smallest unique value is at index `0`, adding one produces the correct rank.

Thus every number receives

```
(number of distinct smaller values) + 1
```

which is exactly the definition of rank.

---

# Complexity Analysis

Let

```
N = arr.length
```

## Sorting

```
O(N log N)
```

---

## Removing Duplicates

```
O(N)
```

---

## Binary Search

Performed for every element.

```
N × O(log N)

=

O(N log N)
```

---

## Overall Time Complexity

```
O(N log N)
```

---

## Space Complexity

Clone of the array

```
O(N)
```

Unique array

```
O(N)
```

Overall

```
O(N)
```

---

# Why This Approach?

An alternative solution uses a `HashMap`:

1. Copy and sort the array.
2. Assign ranks while traversing the sorted array.
3. Store each value and its rank in a map.
4. Replace every original element using the map.

That approach also runs in

```
O(N log N)
```

This implementation instead avoids maintaining an additional hash map by leveraging the sorted unique array and Java's built-in `Arrays.binarySearch()`. While each lookup costs `O(log N)` instead of average `O(1)`, the overall complexity remains `O(N log N)` and the implementation stays concise and straightforward.

---

# Summary

The solution follows a simple pipeline:

1. Clone the original array.
2. Sort the clone.
3. Remove duplicate values.
4. Keep only the unique sorted elements.
5. Use binary search to find the position of every original element.
6. Convert that position into a 1-based rank.

By combining sorting with binary search, the algorithm efficiently assigns the correct rank to every element while ensuring equal values receive the same rank.
