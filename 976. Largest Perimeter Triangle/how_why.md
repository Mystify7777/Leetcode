# How_Why.md – Largest Perimeter Triangle (976)

## ✅ Problem Recap

We are given an array of integers `nums` representing side lengths.
We need to form a **triangle** with the **largest perimeter** possible.

**Triangle Property**
For any three sides `a, b, c` (where `c` is the largest):

$$
a + b > c
$$

If this property is satisfied, the three sides can form a valid triangle.

---

## Approach: Sort + Greedy

### Step 1: Sort the array

```java
Arrays.sort(nums);
```

* Sorting helps us check **largest values first**.
* Larger sides → potentially larger perimeter.

---

### Step 2: Traverse from the back

```java
for (int i = nums.length-1; i > 1; i--) {
    if (nums[i] < nums[i-1] + nums[i-2])
        return nums[i] + nums[i-1] + nums[i-2];
}
```

* Start from the **largest element** (`nums[i]`).
* Check if the **two previous numbers** (`nums[i-1], nums[i-2]`) with `nums[i]` can form a triangle.
* If yes → return their perimeter immediately (greedy works because sorting ensures this is the maximum perimeter).

---

### Step 3: Return 0 if no triangle found

```java
return 0;
```

* If no valid triple found, no triangle can be formed.

---

## Example Walkthrough

#### Input

```
nums = [2,1,2]
```

#### Steps

1. Sort → `[1,2,2]`
2. Start from end:

   * Check `(2,2,1)` → `2 < 2+1 → valid`
   * Perimeter = `5`

#### Output

```
5
```

---

#### Another Example

```
nums = [1,2,1]
```

* Sort → `[1,1,2]`
* Largest side = `2`
* Check `(2,1,1)` → `2 < 1+1 → false`
* No valid triangle → return `0`.

---

## Complexity

| Metric | Complexity           |
| ------ | -------------------- |
| Time   | O(n log n) (sorting) |
| Space  | O(1) (in-place sort) |

---

✅ Elegant greedy solution: sort → check from largest → return first valid perimeter.

---

