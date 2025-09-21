# Problem Recap

We are given an integer array `nums` and an integer `k`. We want to **count the number of continuous subarrays whose sum equals `k`**.

---

## 1. Standard HashMap Approach (Readable Version)

```java
public int subarraySum(int[] nums, int k) {
    int count = 0, sum = 0;
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // base case: prefix sum 0 occurs once

    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (map.containsKey(sum - k)) 
            count += map.get(sum - k); // found subarrays ending at i with sum=k
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
}
```

### How it works

1. Compute **prefix sums**: `sum[i] = nums[0] + ... + nums[i]`.
2. A subarray `[j+1..i]` sums to `k` if `sum[i] - sum[j] = k`.
3. So we store all previous prefix sums in a map.
4. Each time we see `sum[i]`, we check if `sum[i]-k` exists in the map → if yes, we found `map.get(sum[i]-k)` subarrays ending at `i` with sum `k`.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

---

## 2. The "Low-level" Version

This one is tricky, but let’s demystify it:

### Key observations

* `hashtable` is a custom array representing an **open-addressed hash table**.

  * Keys are stored at even indices (`i`), values/counts at odd indices (`i+1`).
* `MIXER = 0x9E3779BA` is a constant used to **mix the bits** of the key (a simple hash function).
* `mask` is computed to get a table size as a power-of-two minus 1 for bitmasking.
* `zeros` counts the number of times prefix sum equals 0 (special case).
* `diff = sum - k` is the prefix sum difference we need to look for.
* The while loops handle **collision resolution** using **linear probing**.

---

### Rough Walkthrough

1. **Iterate `nums`** and maintain `sum` (prefix sum).
2. Compute `diff = sum - k`. If `diff != 0`, we want to see if there is a previous prefix sum equal to `diff`.

   * Use `diff * MIXER & mask` to hash it into the table.
   * If the slot is empty → stop (no match).
   * If the slot has `key == diff` → increment `res` by count at `i+1`.
   * Else → move to next slot (`i = i + 2 & mask`).
3. After checking `diff`, store/update `sum` in the hash table using the same hash + probing mechanism.
4. Special case: if `sum == 0`, increment `zeros`.

---

### Why is this “fast”?

* Avoids `HashMap` overhead (boxing/unboxing of `Integer` objects).
* Uses a **preallocated array** and **bitwise operations** for hashing.
* Linear probing with bitmasking is much faster in tight loops.
* Essentially a **manual, highly optimized HashMap for integers**.

---

### Comparison

| Feature                 | Standard HashMap | Low-level array |
| ----------------------- | ---------------- | --------------- |
| Readability             | High             | Very low        |
| Time Complexity         | O(n)             | O(n)            |
| Space Complexity        | O(n)             | O(n)            |
| Performance             | Moderate         | Very fast       |
| Complexity to implement | Easy             | Very hard       |

---

### TL;DR

* **Conceptually**, both do the same thing: count previous prefix sums that match `sum - k`.
* The second version is just a **super-optimized implementation** for competitive programming or performance-critical cases.

---
