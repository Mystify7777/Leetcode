# How\_Why.md: Subsets (LeetCode 78)

## Problem

Given a set of **distinct integers** `nums`, return **all possible subsets (the power set)**.
The solution **must not contain duplicate subsets**.

**Example:**

```java
Input: nums = [1,2,3]
Output:
[
  [], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]
]
```

---

## Brute-force Approach

### Idea

* Generate all possible subsets using **recursion/backtracking**.
* For each element, **choose to include it or exclude it**.
* Continue recursively for all elements.

### Code

```java
public List<List<Integer>> subsetsBF(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
    result.add(new ArrayList<>(path));
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        backtrack(nums, i + 1, path, result);
        path.remove(path.size() - 1);
    }
}
```

### Example Walkthrough

* Input: `[1,2]`
* Start with `[]`
* Include 1 → `[1]`

  * Include 2 → `[1,2]`
  * Exclude 2 → `[1]`
* Exclude 1 → `[2]`
* Exclude both → `[]`
* Result: `[[], [1], [1,2], [2]]`

### Limitations

* Recursion may cause **stack overflow** if `nums` is large.
* O(2^n) subsets → recursion overhead may be slightly higher than iterative methods.

---

## Iterative Approach (Your Approach)

### Idea_

* Start with **empty subset `[]`**.
* For each number in `nums`, **add it to all existing subsets** to form new subsets.
* This avoids recursion and builds the power set iteratively.

### Code_

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    res.add(new ArrayList<>()); // start with empty subset

    for (int num : nums) {
        int size = res.size();
        for (int i = 0; i < size; i++) {
            List<Integer> subset = new ArrayList<>(res.get(i));
            subset.add(num);
            res.add(subset);
        }
    }
    return res;
}
```

### Example Walkthrough_

* Input: `[1,2]`
* Start: `[[]]`
* Add 1: `[[], [1]]`
* Add 2: `[[], [1], [2], [1,2]]` ✅

### Advantages

* **No recursion**, stack-safe.
* Clear iterative logic.
* Efficient for small to medium `n`.

---

## Bitmask Approach (Alternative Optimized)

### Idea__

* Represent subsets as **binary numbers** of length `n`.
* If `nums = [1,2,3]`, there are `2^3 = 8` subsets:

  ```java
  000 → []
  001 → [1]
  010 → [2]
  011 → [1,2]
  ...
  111 → [1,2,3]
  ```

### Code__

```java
public List<List<Integer>> subsetsBitmask(int[] nums) {
    int n = nums.length;
    List<List<Integer>> res = new ArrayList<>();
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if ((mask & (1 << j)) != 0) {
                subset.add(nums[j]);
            }
        }
        res.add(subset);
    }
    return res;
}
```

### Complexity

* **Time Complexity:** O(n \* 2^n) → each of 2^n subsets can have up to n elements.
* **Space Complexity:** O(2^n \* n) → stores all subsets.

---

### Key Takeaways

1. **Backtracking** is intuitive and clean, but recursive.
2. **Iterative subset building** is stack-safe and simple.
3. **Bitmasking** is compact and easy to understand if thinking in terms of binary.

---

