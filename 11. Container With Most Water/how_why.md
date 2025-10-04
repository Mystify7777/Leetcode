# How_Why.md — Container With Most Water (LeetCode 11)

---

## ❌ Brute Force Approach

### **Idea**

* Consider all pairs `(i, j)` of lines.
* Compute the area as:

  ```java
  area = min(height[i], height[j]) * (j - i)
  ```

* Keep track of the maximum area seen.

### **Limitation**

* Time Complexity: `O(n²)` — too slow for large inputs (up to 10⁵).
* We compute every possible pair even though many can’t possibly be the max.

---

## ✅ Optimized Approach — Two Pointer Technique

### **Why It Works**

* The **width** between the two pointers shrinks as we move inward.
* The **height** of water is limited by the shorter line.
* To maximize the area, we need **taller heights** while still having decent width.
* Hence, move the pointer at the **shorter line** inward — because only that can lead to a taller height and possibly a larger area.

---

### **Algorithm**

1. Initialize two pointers:

   ```java
   left = 0
   right = n - 1
   maxArea = 0
   ```

2. While `left < right`:

   * Calculate area = `(right - left) * min(height[left], height[right])`
   * Update `maxArea` if current area is greater.
   * Move pointer at the **shorter line** inward:

     * If `height[left] < height[right]` → `left++`
     * Else → `right--`
3. Return `maxArea`

---

### **Example Walkthrough**

```text
height = [1,8,6,2,5,4,8,3,7]

Step 1: left=0, right=8 → area = min(1,7)*(8-0)=7 → max=7
Step 2: Move left++ (since 1<7)
Step 3: left=1, right=8 → area = min(8,7)*(8-1)=49 → max=49
Step 4: Move right-- (since 8>7)
Step 5: left=1, right=7 → area = min(8,3)*(7-1)=18 → max=49
...
Final maxArea = 49
```

---

### **Code (Two Pointer)**

```java
class Solution {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;        
    }
}
```

---

### **Alternative Optimization**

Skip all smaller heights after moving the pointer:

```java
while (i < j && height[i] <= min) i++;
while (i < j && height[j] <= min) j--;
```

⏩ Skips redundant pairs where area will only decrease.

---

## 🧮 Complexity

* **Time:** `O(n)` — each pointer moves at most `n` times.
* **Space:** `O(1)` — only a few variables used.

---

## ✅ Key Insights

* The area depends on both width and min height.
* Width always decreases → to compensate, you must find a taller line.
* Moving the shorter pointer guarantees exploration of better possibilities.

---
