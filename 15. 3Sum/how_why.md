
# How\_Why.md: 3Sum

## Problem

Given an integer array `nums`, return **all unique triplets** `[a, b, c]` such that:

```java
a + b + c == 0
```

* Triplets must be **unique** (no duplicates).
* Each triplet can be returned in any order.

**Example:**

```java
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

---

## Brute-force Approach

### Idea

* Check **all possible triplets** `(i, j, k)` in `nums`.
* If `nums[i] + nums[j] + nums[k] == 0`, add to result.
* Use a **set** to avoid duplicate triplets.

### Code

```java
public List<List<Integer>> threeSumBF(int[] nums) {
    Set<List<Integer>> result = new HashSet<>();
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }
    }
    return new ArrayList<>(result);
}
```

### Example Walkthrough

* Input: `[-1,0,1,2,-1,-4]`
* Triplets checked:

  * `(-1,0,1)` → sum = 0 → add
  * `(-1,-1,2)` → sum = 0 → add
  * Others → skip
* Returns `[[-1,-1,2], [-1,0,1]]`

### Limitation

* **Time complexity:** O(n³)
* Inefficient for large arrays (n ≈ 10³–10⁴)

---

## Optimized Approach (Two-pointer)

### Idea_

1. **Sort the array**.
2. Fix `nums[i]` as the first element of the triplet.
3. Use **two pointers** `j` and `k` to find pairs such that:

   ```java
   nums[i] + nums[j] + nums[k] == 0
   ```
4. Move pointers intelligently:

   * `sum < 0` → move `j` right
   * `sum > 0` → move `k` left
5. Skip duplicates for `i`, `j`, `k`.

### Code_

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> output = new ArrayList<>();
    Arrays.sort(nums); // sort first

    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue; // skip duplicates

        int j = i + 1, k = nums.length - 1;
        while (j < k) {
            int sum = nums[i] + nums[j] + nums[k];
            if (sum == 0) {
                output.add(Arrays.asList(nums[i], nums[j], nums[k]));

                while (j < k && nums[j] == nums[j + 1]) j++; // skip duplicates
                while (j < k && nums[k] == nums[k - 1]) k--;

                j++;
                k--;
            } else if (sum < 0) {
                j++;
            } else {
                k--;
            }
        }
    }
    return output;
}
```

### Example Walkthrough_

* Input: `[-1,0,1,2,-1,-4]`

1. Sort: `[-4,-1,-1,0,1,2]`
2. `i=0`, `nums[i]=-4`, `j=1`, `k=5` → sum = -3 → `j++`
3. `i=1`, `nums[i]=-1`, `j=2`, `k=5` → sum = -1 → `j++`
4. `j=3`, `k=5` → sum = 0 → add `[-1,0,1]`
5. `j=4`, `k=4` → break
6. `i=2` → skip duplicate `-1`
7. `i=3` → `nums[i]=0`, `j=4`, `k=5` → sum = 3 → `k--` → break

* Output: `[[-1,-1,2], [-1,0,1]]`

### Advantages

* **Time complexity:** O(n²) → sorting O(n log n) + two-pointer O(n²)
* **Space complexity:** O(1) extra (excluding result)

---

## Key Takeaways

1. **Brute-force:** simple but O(n³) → impractical.
2. **Two-pointer:** efficient for sorted arrays, avoids duplicates naturally.
3. Always **sort the array first** to use the two-pointer technique effectively.

---
