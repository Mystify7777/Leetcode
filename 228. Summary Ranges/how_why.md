# How & Why: LeetCode 228 - Summary Ranges

---

## Problem Restatement
We are given a sorted integer array `nums` without duplicates. We need to return the **smallest list of ranges** that cover all the numbers exactly.

- A range is represented as:
  - `"a"` if the range contains only one number.
  - `"a->b"` if the range contains multiple consecutive numbers from `a` to `b`.

---

## How to Solve

### Step 1: Handle Edge Case
If `nums` is empty, return an empty list.
```java
if (nums.length == 0) return result;
```

### Step 2: Iterate Through Array
Use two pointers `i` and `j`:
- `i` marks the start of the current range.
- `j` expands forward as long as consecutive numbers are found.

```java
while (i < nums.length) {
    int start = nums[i];
    int j = i;
    while (j + 1 < nums.length && nums[j + 1] == nums[j] + 1) {
        j++;
    }
    // range found from start to nums[j]
    ...
    i = j + 1; // move to next possible range
}
```

### Step 3: Format Range
- If the range is a single number, add `"start"`.
- If multiple numbers, add `"start->end"`.

```java
if (nums[j] == start) {
    result.add(String.valueOf(start));
} else {
    result.add(start + "->" + nums[j]);
}
```

---

## Why This Works
- The array is sorted and without duplicates.
- Expanding `j` ensures we capture maximal consecutive ranges.
- Using string formatting captures both single-number and multi-number ranges.
- Each number is processed exactly once, ensuring efficiency.

---

## Complexity Analysis
- **Time Complexity**: O(n), where n = length of `nums`. Each element is visited once.
- **Space Complexity**: O(1) extra (excluding result storage).

---

## Example Walkthrough
Input:
```
nums = [0,1,2,4,5,7]
```

Process:
- Start at `0`, consecutive until `2` → add `"0->2"`.
- Next start at `4`, consecutive until `5` → add `"4->5"`.
- Next start at `7` → single element → add `"7"`.

Output:
```
["0->2","4->5","7"]
```

---

## Alternate Approaches
1. **Two-pointer + Direct Append**:
   Keep `start` and append range whenever a gap is detected.
   This is slightly more compact but logically the same.

2. **StringBuilder Optimization**:
   Instead of concatenation, use `StringBuilder` for efficiency in languages with expensive string operations. In Java, since concatenation inside loops is optimized, this is less critical.

### Optimal Choice
The **two-pointer expansion approach** is optimal:
- Simple and clear.
- Linear time.
- Matches problem constraints directly.

---

## Key Insight
The key idea is to group **maximal consecutive subsequences** into ranges and format them correctly depending on whether the range length is 1 or more.

