# How & Why – 812. Largest Triangle Area

## Problem

Given `points` in the 2D plane, return the largest area of any triangle formed by **three different points**.

---

## 1. Brute Force with Shoelace Formula ✅ (First version)

**Idea:**

* Any triangle area from points `(x1,y1), (x2,y2), (x3,y3)` can be computed using the **Shoelace Formula**:

$$
\text{Area} = \frac{1}{2} \cdot |x_1(y_2-y_3) + x_2(y_3-y_1) + x_3(y_1-y_2)|
$$

* Check **all triplets (i, j, k)**.
* Keep track of maximum area.

**Complexity:**

* O(n³) time (since all triplets).
* O(1) space.
* Works fine because `n ≤ 50`.

---

## 2. Alternative Attempt (Second version in your code)

This code:

```java
area(p1, p2) // computes trapezoid-like area
```

It tries to decompose the triangle into trapezoids, but it’s **not mathematically correct for arbitrary coordinates**. It might pass on some test cases but will **fail on edge cases** because trapezoid decomposition doesn’t guarantee exact triangle area unless the points align in a very specific way.

That’s why the **shoelace-based version** is the correct + widely accepted solution.

---

## Example Walkthrough

Input:

```
points = [[0,0], [0,1], [1,0], [0,2], [2,0]]
```

All possible triangles (just a few key ones):

1. (0,0), (0,1), (1,0)
   Area = 0.5

2. (0,0), (0,2), (2,0)
   Area = 2.0 ✅ (largest so far)

3. (0,1), (0,2), (2,0)
   Area = 2.0

4. (0,2), (1,0), (2,0)
   Area = 2.0

→ Maximum = **2.0**

---

## Takeaway

* ✅ The **shoelace formula (first version)** is correct and clean.
* ❌ The **trapezoid method (second version)** is not reliable for general cases.
* Brute-force O(n³) is acceptable because of small input size.

---
