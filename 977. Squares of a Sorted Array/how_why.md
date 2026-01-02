# How_Why.md: Squares of a Sorted Array

## Problem

Given an integer array `nums` sorted in **non-decreasing order**, return an array of the **squares of each number** sorted in non-decreasing order.

**Example:**

```s
Input: nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]

Input: nums = [-7,-3,2,3,11]
Output: [4,9,4,9,121]
```

---

## Approach: Two Pointers (Fill from Right)

### Idea

* In a sorted array with **negative numbers on the left** and **positive numbers on the right**, the **largest squares are at the edges**

* Use **two pointers** at both ends:
  - Compare absolute values at left and right
  - Place the larger square at the **end** of the result array
  - Move the corresponding pointer inward

* Fill the result array from **right to left**

### Code

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] res = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        
        // Fill result array from right to left
        for (int i = nums.length - 1; i >= 0; i--) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                res[i] = nums[left] * nums[left];
                left++;
            } else {
                res[i] = nums[right] * nums[right];
                right--;
            }
        }
        
        return res;
    }
}
```

### Why This Works

* **Key Insight:** The largest values in the squared array come from the **edges** (most negative or most positive)

* **Example:** nums = [-4, -1, 0, 3, 10]

  ```s
  Squares: [16, 1, 0, 9, 100]
  
  Step 1: Compare |-4| = 4 vs |10| = 10
    10 > 4 → res[4] = 100, right = 3
  
  Step 2: Compare |-4| = 4 vs |3| = 3
    4 > 3 → res[3] = 16, left = 1
  
  Step 3: Compare |-1| = 1 vs |3| = 3
    3 > 1 → res[2] = 9, right = 2
  
  Step 4: Compare |-1| = 1 vs |0| = 0
    1 > 0 → res[1] = 1, left = 2
  
  Step 5: Only |0| = 0 left
    res[0] = 0
  
  Result: [0, 1, 9, 16, 100] ✓
  ```

* **Time Complexity:** **O(n)** - single pass through array
* **Space Complexity:** **O(1)** - only output array (not counting it)

---

## Approach 2: Divide and Place

### Idea*

Find the **pivot point** where negative ends and positive begins, then merge two sorted halves

### Code*

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] res = new int[nums.length];
        
        // Find pivot: where negatives end
        int pivot = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                pivot = i;
                break;
            }
        }
        
        // If all negative or all positive
        if (pivot == -1) pivot = nums.length;
        
        int left = pivot - 1;    // Last negative
        int right = pivot;        // First positive
        
        for (int i = nums.length - 1; i >= 0; i--) {
            if (left < 0) {
                res[i] = nums[right] * nums[right];
                right++;
            } else if (right >= nums.length) {
                res[i] = nums[left] * nums[left];
                left--;
            } else if (Math.abs(nums[left]) > nums[right]) {
                res[i] = nums[left] * nums[left];
                left--;
            } else {
                res[i] = nums[right] * nums[right];
                right++;
            }
        }
        
        return res;
    }
}
```

---

## Approach 3: Simple Square and Sort (Naive)

### Code**

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] res = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i] * nums[i];
        }
        
        Arrays.sort(res);
        return res;
    }
}
```

**Time Complexity:** **O(n log n)** - due to sorting
**Space Complexity:** **O(1)** or **O(n)** depending on sort implementation

---

## Comparison

| Approach | Time | Space | Notes |
| ---------- | ------ | ------- | ------- |
| Two Pointers | O(n) | O(1) | **Optimal**, leverages sorted input |
| Divide & Place | O(n) | O(1) | More complex, same complexity |
| Square & Sort | O(n log n) | O(1) | Simpler but slower |

---

## Why This Approach

* **Optimal:** Linear time complexity
* **Clever:** Exploits the mathematical property that edges have largest squares
* **Efficient:** No sorting needed
* **Interview Ready:** Shows understanding of two-pointer technique
* **Practical:** Works with negative numbers naturally

**Key Takeaway:** When working with sorted data, two-pointer technique often yields optimal solutions!
