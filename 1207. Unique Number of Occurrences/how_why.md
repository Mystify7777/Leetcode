# 1207. Unique Number of Occurrences – How & Why

## Problem Statement

Given an integer array `arr`, determine if the number of occurrences of each value in the array is **unique**.
Return `true` if every occurrence count is unique; otherwise, return `false`.

---

## 1. Brute-force / Sorting Approach

```java
Arrays.sort(arr);
int[] v = new int[arr.length];
int idx = 0;

for (int i = 0; i < arr.length; i++) {
    int cnt = 1;
    while (i + 1 < arr.length && arr[i] == arr[i + 1]) {
        cnt++;
        i++;
    }
    v[idx++] = cnt;
}

Arrays.sort(v);

for (int i = 1; i < v.length; i++) {
    if (v[i] == v[i - 1]) return false;
}
return true;
```

### How it works

1. Sort the array so identical numbers are adjacent.
2. Count occurrences of each distinct number and store in `v`.
3. Sort `v` to group equal counts together.
4. Compare consecutive elements to check for duplicates.

### Limitations

* **Time complexity:** O(n log n) for sorting.
* **Space complexity:** O(n) for `v`.
* Sorting is unnecessary; we only care about **frequency uniqueness**, not order.

### Example Walkthrough

Input: `[1,2,2,1,1,3]`

* Sorted: `[1,1,1,2,2,3]`
* Frequency array `v`: `[3,2,1]`
* After sorting `v`: `[1,2,3]`
* All unique → return `true`.

---

## 2. HashMap + HashSet Approach

```java
Map<Integer, Integer> freq = new HashMap<>();
for (int x : arr) freq.put(x, freq.getOrDefault(x,0)+1);

Set<Integer> s = new HashSet<>();
for (int x : freq.values()) s.add(x);

return freq.size() == s.size();
```

### How it works_

1. Count occurrences using a HashMap `freq`.
2. Insert all counts into a HashSet `s`.
3. If sizes match, all counts are unique.

### Advantages

* **Time complexity:** O(n)
* **Space complexity:** O(n)
* Much simpler and readable.
* No sorting needed.

### Example

* `arr = [1,2,2,1,1,3]`
* `freq = {1:3, 2:2, 3:1}`
* `s = {1,2,3}`
* `freq.size() == s.size()` → true.

---

## 3. Frequency Array Optimization (Your Latest Approach)

```java
int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
for (int num : arr) { min = Math.min(min,num); max = Math.max(max,num); }

int[] freq = new int[max-min+1];
for (int num : arr) freq[num-min]++;

boolean[] seen = new boolean[arr.length+1];
for (int count : freq) {
    if (count > 0) {
        if (seen[count]) return false;
        seen[count] = true;
    }
}
return true;
```

### How it works__

1. Compute `min` and `max` to handle negative numbers.
2. Create a **frequency array** `freq` of size `(max-min+1)` to count each number.

   * Index mapping: `num -> num - min`.
3. Use a boolean array `seen` to track which counts have appeared.
4. If a count appears twice → return `false`.
5. Otherwise → return `true`.

### Why this is efficient

* Avoids HashMap overhead.
* **Time complexity:** O(n + range)

  * `n` for counting, `range = max-min` for iterating `freq`.
* **Space complexity:** O(range + n)
* Works even for negative numbers.

### Example_

* Input: `[1,2,2,1,1,3]`
* `min = 1, max = 3` → `freq = [3,2,1]`
* `seen = [false, false, false, false, false, false, false]`
* Iterating `freq` → marks `1,2,3` in `seen` → all unique → return `true`.

---

### Summary of Approaches

| Approach                        | Time Complexity | Space Complexity | Notes                        |
| ------------------------------- | --------------- | ---------------- | ---------------------------- |
| Sort + array                    | O(n log n)      | O(n)             | Simple but slower            |
| HashMap + HashSet               | O(n)            | O(n)             | Cleanest and most readable   |
| Frequency array + boolean array | O(n + range)    | O(n + range)     | Optimized, no hashing needed |

---

### When to use your frequency array method

* Small range or mostly dense numbers.
* Want to **avoid HashMap** (e.g., for coding contests or very performance-critical code).

---
