# 🔎 Example Walkthrough – 1039. Minimum Score Triangulation of Polygon

## Input

```java
values = [1, 3, 1, 4, 1, 5]
n = 6
```

We want to triangulate a 6-sided convex polygon labeled with these values.
Answer will be `dp[0][5]`.

---

## Step 1: DP Table Setup

We define:

* `dp[i][j]` = minimum score to triangulate polygon between vertex `i` and `j`.
* If `j - i < 2` → no triangle → cost = 0.

Initialize a 6×6 table with all zeros:

```c
dp =
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
```

---

## Step 2: Process intervals of increasing length

### Length = 2 (3 vertices → one triangle)

For `(i, j)` with `j - i = 2`, only one triangle possible `(i, i+1, j)`.

* `dp[0][2] = 1*3*1 = 3`
* `dp[1][3] = 3*1*4 = 12`
* `dp[2][4] = 1*4*1 = 4`
* `dp[3][5] = 4*1*5 = 20`

Table now:

```c
0 0 3 0 0 0
0 0 0 12 0 0
0 0 0 0 4 0
0 0 0 0 0 20
0 0 0 0 0 0
0 0 0 0 0 0
```

---

### Length = 3 (4 vertices)

Now we pick a middle point `k` between `i` and `j`.

* `dp[0][3]`: vertices `[0..3]` → options:

  * k=1 → cost = dp[0][1] + dp[1][3] + 1*3*4 = 0+12+12=24
  * k=2 → cost = dp[0][2] + dp[2][3] + 1*1*4 = 3+0+4=7 ✅
    → dp[0][3] = 7

* `dp[1][4]`: vertices `[1..4]` → options:

  * k=2 → cost = 0+4+3*1*1=7
  * k=3 → cost = 12+0+3*4*1=24
    → `dp[1][4] = 7`

* `dp[2][5]`: vertices `[2..5]` → options:

  * k=3 → cost = 0+20+1*4*5=40
  * k=4 → cost = 4+0+1*1*5=9 ✅
    → `dp[2][5] = 9`

Update:

```c
0 0 3 7 0 0
0 0 0 12 7 0
0 0 0 0 4 9
0 0 0 0 0 20
0 0 0 0 0 0
0 0 0 0 0 0
```

---

### Length = 4 (5 vertices)

* `dp[0][4]`: vertices `[0..4]`

  * k=1 → 0+7+1*3*1=10
  * k=2 → 3+4+1*1*1=8 ✅
  * k=3 → 7+0+1*4*1=11
    → `dp[0][4] = 8`

* `dp[1][5]`: vertices `[1..5]`

  * k=2 → 0+9+3*1*5=24
  * k=3 → 12+20+3*4*5=92
  * k=4 → 7+0+3*1*5=22 ✅
    → `dp[1][5] = 22`

Table:

```c
0 0 3 7 8 0
0 0 0 12 7 22
0 0 0 0 4 9
0 0 0 0 0 20
0 0 0 0 0 0
0 0 0 0 0 0
```

---

### Length = 5 (whole polygon)

Finally `dp[0][5]`: vertices `[0..5]`

* k=1 → `dp[0][1]+dp[1][5]+1*3*5 = 0+22+15=37`
* k=2 → `dp[0][2]+dp[2][5]+1*1*5 = 3+9+5=17` ✅
* k=3 → `dp[0][3]+dp[3][5]+1*4*5 = 7+20+20=47`
* k=4 → `dp[0][4]+dp[4][5]+1*1*5 = 8+0+5=13` ✅✅ (smaller)

So → `dp[0][5] = 13`

---

## Final Answer

```c
Minimum Score = 13
```

---

### 🎯 Visual Intuition

Best triangulation is:

* Use diagonals `(0,2)`, `(0,4)`, `(2,4)`
  This creates triangles:
* (0,1,2) → 3
* (0,2,4) → 1
* (2,3,4) → 4
* (0,4,5) → 5
  Total = **13**

---

✅ Now you can **see step-by-step** how DP builds up the solution!

---
