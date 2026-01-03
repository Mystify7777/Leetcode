# How_Why.md: Number of Ways to Paint N × 3 Grid

## Problem

You have a `grid` of size `n x 3` and you want to paint each cell of the grid with exactly one of the three colors: **Red**, **Yellow**, or **Green** while making sure that no two adjacent cells have the same color (i.e., no two cells that share vertical or horizontal sides have the same color).

Return the number of ways you can paint this grid. As the answer may be very large, return it **modulo** `10^9 + 7`.

**Example:**

```c
Input: n = 1
Output: 12

Input: n = 2
Output: 54
```

---

## Approach: Dynamic Programming with Pattern Counting

### Idea

* For each row, there are only **2 types of valid colorings**:
  - **Type A (ABA pattern):** 3 colors used, middle color different - e.g., RGB, RGR, YGY (6 patterns)
  - **Type B (ABC pattern):** All 3 colors different - e.g., RYG, RGY, YRG (6 patterns)

* **Key Insight:** The next row's pattern depends only on the current row's pattern type, not the specific colors

* **Transitions for next row:**
  - From Type A (ABA) → Can create **3 Type A** + **2 Type B** patterns = 5 ways
  - From Type B (ABC) → Can create **2 Type A** + **2 Type B** patterns = 4 ways

* Use DP to track counts: `A` = count of Type A patterns, `B` = count of Type B patterns

### Code

```java
class Solution {
    public int numOfWays(int n) {
        final int MOD = 1_000_000_007;
        long A = 6, B = 6;  // Row 1: 6 ABA patterns + 6 ABC patterns
        
        for (int i = 2; i <= n; i++) {
            long newA = (3 * A + 2 * B) % MOD;
            long newB = (2 * A + 2 * B) % MOD;
            A = newA;
            B = newB;
        }
        
        return (int) ((A + B) % MOD);
    }
}
```

### Why This Works

* **Pattern Classification:**
  - **ABA (Type A):** RGR, GRG, BRB, RBR, GBG, BGB = 6 patterns
  - **ABC (Type B):** RGB, RBG, GRB, GBR, BRG, BGR = 6 patterns

* **Transition Logic (Example: Current row is RGR - Type A):**
  
  Next row Type A possibilities:

  ```text
  Current: R G R
  Next:    G R G ✓ (different from RGR)
  Next:    B R B ✓
  Next:    G B G ✓
  ```

  → 3 Type A patterns
  
  Next row Type B possibilities:

  ```text
  Current: R G R
  Next:    G R B ✓
  Next:    B R G ✓
  ```

  → 2 Type B patterns

* **Example Walkthrough (n = 2):**

  ```m
  Row 1: A = 6, B = 6
  
  Row 2:
    newA = 3*6 + 2*6 = 18 + 12 = 30
    newB = 2*6 + 2*6 = 12 + 12 = 24
  
  Total = 30 + 24 = 54 ✓
  ```

* **Time Complexity:** **O(n)** - single loop
* **Space Complexity:** **O(1)** - only two variables

---

## Approach 2: Matrix Exponentiation (Optimized for Large N)

### Idea*

* Express the recurrence as matrix multiplication:

  ```math
  [A_i+1]   [3 2]   [A_i]
  [B_i+1] = [2 2] × [B_i]
  ```

* For row `n`, compute: `M^(n-1) × [6, 6]^T`

* Use **fast matrix exponentiation** to compute `M^n` in **O(log n)** time

### Code*

```java
class Solution {
    int mod = 1_000_000_007;
    
    public long[][] matrixMultiply(long[][] mat1, long[][] mat2) {
        long[][] ans = new long[mat1.length][mat2[0].length];
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat2[0].length; j++) {
                for (int k = 0; k < mat2.length; k++) {
                    ans[i][j] = (ans[i][j] + mat1[i][k] * mat2[k][j]) % mod;
                }
            }
        }
        return ans;
    }
    
    public long[][] matrixPower(long[][] mat, int n) {
        long[][] ans = {{1, 0}, {0, 1}};  // Identity matrix
        long[][] curr = mat;
        
        while (n != 0) {
            if ((n & 1) == 1) {
                ans = matrixMultiply(ans, curr);
            }
            curr = matrixMultiply(curr, curr);
            n >>= 1;
        }
        return ans;
    }
    
    public int numOfWays(int n) {
        long[][] mat = {{3, 2}, {2, 2}};
        long[][] multiplyMat = matrixPower(mat, n - 1);
        long[][] initialVector = {{6}, {6}};
        long[][] finalVector = matrixMultiply(multiplyMat, initialVector);
        return (int)((finalVector[0][0] + finalVector[1][0]) % mod);
    }
}
```

**Time Complexity:** **O(log n)** - matrix exponentiation
**Space Complexity:** **O(1)** - constant size matrices

---

## Approach 3: Optimized DP Formula

### Code**

```java
class Solution {
    public int numOfWays(int n) {
        long MOD = 1_000_000_007L;
        long halfA = 6, halfB = 6;
        
        for (int i = 1; i < n; i++) {
            long halfA2 = halfA * 3 + halfB * 2;
            long halfB2 = halfA2 - halfA;
            halfA = halfA2 % MOD;
            halfB = halfB2 % MOD;
        }
        
        return (int)((halfA + halfB) % MOD);
    }
}
```

**Note:** Uses algebraic optimization: `newB = newA - oldA`

---

## Comparison

| Approach | Time | Space | Notes |
| -------- | ---- | ----- | ----- |
| DP Pattern Counting | O(n) | O(1) | **Most intuitive**, clear pattern logic |
| Matrix Exponentiation | O(log n) | O(1) | **Optimal for large n**, complex |
| Optimized DP | O(n) | O(1) | Algebraic optimization, less readable |

---

## Why This Approach

* **Clever Pattern Recognition:** Instead of tracking 12 specific patterns, reduces to just 2 types
* **Efficient:** O(n) time with O(1) space
* **Scalable:** Matrix approach handles very large n efficiently
* **Mathematical:** Leverages recurrence relations and linear algebra
* **Interview Ready:** Shows strong problem decomposition skills

**Key Takeaway:** When problems have exponential possibilities, look for pattern types and recurrence relations to reduce complexity dramatically!
