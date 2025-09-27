# How_Why.md – Pascal’s Triangle (LeetCode 118)

---

## 🔹 Problem

Generate the first `numRows` rows of **Pascal’s Triangle**.
Each row starts and ends with `1`, and every inner element is the sum of the two numbers above it.

---

## 🔸 Approach 1 – Brute Force (Recomputing Each Value)

### Idea

* Each element at position `(r, c)` can be computed using the **binomial coefficient**:

  ```
  C(r, c) = r! / (c! * (r-c)!)
  ```
* Compute every value directly using factorials.

### Pseudocode

```java
for r in 0..numRows-1:
    row = []
    for c in 0..r:
        row.add( factorial(r) / (factorial(c) * factorial(r-c)) )
    triangle.add(row)
```

### Walkthrough (numRows = 5)

* Row 2: C(2,0)=1, C(2,1)=2, C(2,2)=1 → `[1,2,1]`
* Row 4: C(4,0)=1, C(4,1)=4, C(4,2)=6, C(4,3)=4, C(4,4)=1 → `[1,4,6,4,1]`

### Limitations

* Factorial computation is expensive (O(n) per value).
* Risk of **integer overflow** for large rows.
* Overall **O(numRows³)** time if factorials are recomputed.

---

## 🔸 Approach 2 – Your Iterative DP Approach ✅

### Idea

* Build the triangle row by row.
* Each new row is formed from the previous row:

  ```
  row[j] = prevRow[j-1] + prevRow[j]
  ```

### Code (your solution)

```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int rowNum = 1; rowNum < numRows; rowNum++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(rowNum - 1);

            row.add(1);
            for (int j = 1; j < rowNum; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            row.add(1);

            triangle.add(row);
        }
        return triangle;
    }
}
```

### Walkthrough (numRows = 5)

* Start: [[1]]
* Row 1: [1,1]
* Row 2: [1,2,1]
* Row 3: [1,3,3,1]
* Row 4: [1,4,6,4,1]

Output:

```
[
 [1],
 [1,1],
 [1,2,1],
 [1,3,3,1],
 [1,4,6,4,1]
]
```

### Complexity

* **Time:** O(numRows²) (filling ~1+2+…+numRows entries).
* **Space:** O(numRows²) (storing all rows).

---

## 🔸 Approach 3 – Optimized for k-th Row Only (LeetCode 119)

### Idea

* If only the **k-th row** is needed:

  * We don’t need to store all rows.
  * Compute values iteratively using:

    ```
    C(r, c) = C(r, c-1) * (r-c+1) / c
    ```
* This allows computing each row in **O(k)** time and space.

### Example (row = 4)

Start with [1].

* C(4,1) = 1 * (4-1+1)/1 = 4 → [1,4]
* C(4,2) = 4 * (4-2+1)/2 = 6 → [1,4,6]
* C(4,3) = 6 * (4-3+1)/3 = 4 → [1,4,6,4]
* Add last 1 → [1,4,6,4,1]

### Complexity

* **Time:** O(k)
* **Space:** O(k)

---

## 🚀 Summary

* **Brute Force (factorials):** Simple but slow, risk of overflow.
* **Iterative DP (your solution):** Best for generating full triangle, O(n²). ✅
* **Binomial Iterative (row only):** Best for single row, O(n).

---
