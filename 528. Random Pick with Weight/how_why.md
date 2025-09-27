# How_Why.md – Random Pick with Weight

## Problem Restatement

You’re given an array `w` where each `w[i]` represents a weight. The task is to implement a function `pickIndex()` that returns an index randomly, but with **probability proportional to its weight**.

Example:
`w = [1, 3]`

* Index `0` should be picked with probability `1 / (1+3) = 25%`
* Index `1` should be picked with probability `3 / (1+3) = 75%`

---

## 1. Brute-Force Approach

**Idea**
Expand the array based on weights. For example:
`w = [2, 3, 5]` → Expanded array = `[0,0,1,1,1,2,2,2,2,2]`
Pick a random index from this expanded array.

**Code Sketch**

```java
class Solution {
    private List<Integer> nums = new ArrayList<>();
    private Random rand = new Random();

    public Solution(int[] w) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i]; j++) {
                nums.add(i);
            }
        }
    }

    public int pickIndex() {
        int n = rand.nextInt(nums.size());
        return nums.get(n);
    }
}
```

**Walkthrough Example**
`w = [1, 3]` → expanded `[0, 1, 1, 1]`

* Random pick from size `4`.
* `0` has 1/4 probability, `1` has 3/4 probability.

**Limitations**

* Uses **O(totalWeight)** memory.
* Very inefficient when weights are large (`w[i]` can be up to 10^5).

---

## 2. Prefix Sum + Linear Scan (Better)

**Idea**
Instead of expanding the array, compute **prefix sums**:
For `w = [2, 3, 5]` → prefix = `[2, 5, 10]`

* Pick a random integer `n` in `[1, totalSum]`.
* Find the first prefix ≥ `n`.

**Code Sketch**

```java
class Solution {
    private int[] prefix;
    private int total;
    private Random rand;

    public Solution(int[] w) {
        prefix = new int[w.length];
        rand = new Random();
        int running = 0;
        for (int i = 0; i < w.length; i++) {
            running += w[i];
            prefix[i] = running;
        }
        total = running;
    }

    public int pickIndex() {
        int target = rand.nextInt(total) + 1; // range [1, total]
        for (int i = 0; i < prefix.length; i++) {
            if (target <= prefix[i]) return i;
        }
        return -1;
    }
}
```

**Walkthrough Example**
`w = [2, 3, 5]` → prefix = `[2, 5, 10]`

* Random pick in `[1,10]`.
* If `target=1,2 → index 0`
* If `3,4,5 → index 1`
* If `6,7,8,9,10 → index 2`

**Limitations**

* Each pick requires **O(n)** scan.

---

## 3. Prefix Sum + Binary Search (Optimized)

**Idea**
Same prefix sum as before, but use **binary search** instead of linear scan.

* Time to pick index reduces from **O(n)** to **O(log n)**.

**Optimized Code**

```java
class Solution {
    private int[] prefix;
    private int total;
    private Random rand;

    public Solution(int[] w) {
        prefix = new int[w.length];
        rand = new Random();
        int running = 0;
        for (int i = 0; i < w.length; i++) {
            running += w[i];
            prefix[i] = running;
        }
        total = running;
    }

    public int pickIndex() {
        int target = rand.nextInt(total) + 1; // [1, total]
        int lo = 0, hi = prefix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
```

**Walkthrough Example**
`w = [1,3]` → prefix = `[1,4]`

* Random in `[1,4]`.
* If `1 → index 0`, if `2,3,4 → index 1`.

**Why It’s Optimal**

* Preprocessing: **O(n)** (once).
* Each `pickIndex()`: **O(log n)**.
* Memory: **O(n)** (just prefix array).

---

✅ Final Choice: **Prefix Sum + Binary Search** → Efficient and scalable.

---
