# How_Why.md — Median of Two Sorted Arrays (LeetCode 4)

---

## ❌ Brute Force Approach

### **Idea**

Merge the two sorted arrays into one sorted array,
then directly pick the median.

### **Implementation**

* Use two pointers to merge like in merge sort.
* After merging, pick:

  * middle element if odd length
  * average of two middle elements if even length

### **Why It’s Bad**

* Needs **O(m + n)** time and **O(m + n)** extra space.
* Fails to meet the **O(log(min(m, n)))** constraint from the problem.

---

## ✅ Optimized Approach — Binary Search Partitioning

### **Core Idea**

We can divide both arrays such that:

* All elements on the **left partitions** are ≤ all elements on the **right partitions**.
* The **combined left half** has exactly the same number of elements as (or one more than) the right half.

If we can find such partitions,
the **median** is determined by the **max of left** and **min of right** elements.

---

### **Algorithm Intuition**

Let’s denote:

```c
nums1 = [1, 3]
nums2 = [2]
```

We want to find two partitions:

```c
|  Left Half  |  Right Half  |
nums1:  [1] | [3]
nums2:  [2] | []
```

**Left half:** `[1, 2]`
**Right half:** `[3]`

Since all left ≤ all right,
the median = `max(1, 2)` = **2.0**

---

### **Steps**

1. Always binary search the **smaller array** to minimize iterations.
2. Let:

   ```c
   partitionX = (low + high) / 2
   partitionY = (x + y + 1)/2 - partitionX
   ```

   (ensures left half of combined array has correct size)
3. Define boundary elements:

   ```c
   maxLeftX = nums1[partitionX - 1] or -∞
   minRightX = nums1[partitionX] or +∞
   maxLeftY = nums2[partitionY - 1] or -∞
   minRightY = nums2[partitionY] or +∞
   ```
4. Check:

   * ✅ If `maxLeftX ≤ minRightY` **and** `maxLeftY ≤ minRightX` → correct partition found.

     * If total length even → median = average of middle two.
     * If odd → median = max of left half.
   * ❌ Else adjust search:

     * If `maxLeftX > minRightY` → move left.
     * Else → move right.

---

### **Example Walkthrough**

**Input**

```c
nums1 = [1, 3]
nums2 = [2]
```

**Step 1:**
`x = 2, y = 1` → swap to ensure `nums1` is smaller.
So we call `findMedianSortedArrays([2], [1, 3])`.

**Step 2:**
Binary search on `nums1`:

```c
low = 0, high = 1
```

**Iteration 1:**

```c
partitionX = 0
partitionY = (1 + 2 + 1)/2 - 0 = 2
```

```c
maxLeftX = -∞, minRightX = 2
maxLeftY = nums2[1] = 3, minRightY = +∞
```

Condition fails since `maxLeftY (3) > minRightX (2)` → move right.

**Iteration 2:**

```java
partitionX = 1
partitionY = 1
```

```c
maxLeftX = 2, minRightX = +∞
maxLeftY = 1, minRightY = 3
```

✅ Both conditions satisfied.

Since total (3) is odd → median = max(2, 1) = **2.0**

---

### **Code**

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int x = nums1.length;
        int y = nums2.length;
        
        if (x > y) {
            return findMedianSortedArrays(nums2, nums1); // Ensure nums1 is smaller
        }

        int low = 0, high = x;

        while (low <= high) {
            int partitionX = (low + high) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX;

            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ((x + y) % 2 == 0) {
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                } else {
                    return Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                high = partitionX - 1;
            } else {
                low = partitionX + 1;
            }
        }

        throw new IllegalArgumentException();
    }
}

---

### **Complexity Analysis**

| Type     | Complexity          | Explanation                    |
| -------- | ------------------- | ------------------------------ |
| ⏱ Time   | `O(log(min(m, n)))` | Binary search on smaller array |
| 💾 Space | `O(1)`              | Constant extra variables       |

---

### **Key Insights**

* Median depends on the **partition point**, not full merging.
* Always binary search on the smaller array to minimize iterations.
* Using sentinels (`±∞`) simplifies boundary handling elegantly.

---
