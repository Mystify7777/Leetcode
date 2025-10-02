# How_Why.md – Find Minimum in Rotated Sorted Array II (LeetCode 154)

## ❌ Brute Force Idea

We’re asked to find the minimum element in a rotated sorted array that may contain duplicates.

**Naive method:**

* Scan the entire array and return the minimum.

Example:

```java
int min = Integer.MAX_VALUE;
for (int x : nums) {
    min = Math.min(min, x);
}
return min;
```

* **Time complexity:** O(n)
* **Space complexity:** O(1)
* Works fine, but wastes effort since the array is mostly sorted and we can exploit binary search.

---

## ✅ Optimized Approach – Modified Binary Search

Key insights:

1. The array is sorted but rotated, so the minimum lies at the **pivot point**.
2. If `nums[mid] > nums[hi]` → the pivot is on the **right half**.
3. If `nums[mid] < nums[hi]` → the pivot is on the **left half (including mid)**.
4. If `nums[mid] == nums[hi]` → we can’t decide, so safely shrink the right boundary (`hi--`).

Your implementation:

```java
class Solution {
    public int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        
        if (nums[lo] < nums[hi]) return nums[lo]; // Already sorted
        
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            
            if (nums[mid] > nums[hi]) {
                lo = mid + 1;        // Minimum is in right half
            } else if (nums[mid] < nums[hi]) {
                hi = mid;            // Minimum is in left half
            } else {
                hi--;                // Skip duplicates
            }
        }
        
        return nums[hi];
    }
}
```

---

## 🔎 Example Walkthrough

Input:

```
nums = [2, 2, 2, 0, 1]
```

Steps:

1. lo=0, hi=4 → mid=2 → nums[mid]=2, nums[hi]=1 → nums[mid] > nums[hi]
   ⇒ Minimum is in right half → lo=3.
2. lo=3, hi=4 → mid=3 → nums[mid]=0, nums[hi]=1 → nums[mid] < nums[hi]
   ⇒ Minimum is in left half → hi=3.
3. lo=3, hi=3 → loop ends.

Answer = nums[hi] = **0** ✅

---

## 📊 Complexity Analysis

* **Best case:** O(log n) when duplicates are minimal.
* **Worst case:** O(n) when many duplicates force shrinking (`hi--`) one by one.
* **Space:** O(1).

---

## ✅ Key Takeaways

* Binary search can be adapted to handle rotated arrays with duplicates.
* The main trick: when `nums[mid] == nums[hi]`, safely shrink `hi`.
* Worst case degrades to O(n), but average is O(log n).

---
