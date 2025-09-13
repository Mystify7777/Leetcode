
# How & Why: LeetCode 169 - Majority Element

This solution uses a HashMap to efficiently count the frequency of each element, allowing it to find the majority element in a single pass.

---

## Problem Restatement

You are given an array `nums` of size $n$. Your task is to find and return the majority element. The majority element is defined as the element that appears more than $\lfloor n / 2 \rfloor$ times.

A key piece of information is that you can always assume the majority element exists in the array.

### Example

**Input:**
```
nums = [2, 2, 1, 1, 1, 2, 2]
```
**Output:**
```
2 (Appears 4 times, which is more than 7 / 2 = 3.5)
```

---

## How to Solve

A reliable way to solve this is by counting the occurrences of each number. A HashMap is a perfect tool for this, as it can store each number and its corresponding frequency.

1. **Initialize:** Create a HashMap to store counts, a variable `res` to hold the current majority element candidate, and a `majority` variable to track the highest frequency seen so far.
2. **Iterate and Count:** Loop through each number `n` in the input array `nums`.
3. **Update Frequency:** For each number, update its count in the HashMap. The `getOrDefault` method is useful here to handle numbers seen for the first time.
4. **Check for New Majority:** After updating the count for the current number, check if its new frequency is greater than the majority count seen so far.
5. **Update Candidate:** If the current number's count is higher, it becomes the new majority element candidate. Update `res` to this number and `majority` to its new, higher count.
6. **Return Result:** Since the problem guarantees that a majority element exists, the `res` variable will hold the correct answer after the loop finishes.

### Implementation

```java
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        int res = 0;
        int majority = 0;

        for (int n : nums) {
            hash.put(n, 1 + hash.getOrDefault(n, 0));
            if (hash.get(n) > majority) {
                res = n;
                majority = hash.get(n);
            }
        }

        return res;
    }
}
```

---

## Why This Works

- **Guaranteed Existence:** The problem statement guarantees that an element appears more than $n / 2$ times. This is crucial because it means that no other element can have a frequency close to it. Therefore, a simple frequency count is sufficient; the element with the highest count must be the majority element.
- **Efficient Lookups:** The HashMap provides average $O(1)$ time complexity for insertions and lookups (`put` and `get`). This allows the algorithm to process the entire array in a single, efficient pass.
- **Real-time Tracking:** By checking and updating the majority candidate inside the loop, the algorithm avoids a second pass to find the maximum frequency after counting is complete, making the code more concise.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$, where $n$ is the number of elements in the array. We iterate through the array once.
- **Space Complexity:** $O(n)$. In the worst-case scenario where the majority element appears only at the end, the HashMap might have to store up to $n / 2$ unique elements.

---

## Example Walkthrough

**Input:**
```
nums = [3, 2, 3]
```

**Process:**

- Initial: `hash = {}`, `res = 0`, `majority = 0`.
- `n = 3`: `hash` becomes `{3: 1}`. `hash.get(3)` (1) is > `majority` (0). `res` becomes 3, `majority` becomes 1.
- `n = 2`: `hash` becomes `{3: 1, 2: 1}`. `hash.get(2)` (1) is not > `majority` (1). No change.
- `n = 3`: `hash` becomes `{3: 2, 2: 1}`. `hash.get(3)` (2) is > `majority` (1). `res` becomes 3, `majority` becomes 2.
- Loop finishes.

**Output:**
```
Return res, which is 3.
```

---

## Alternate Approaches

### 1. Boyer-Moore Voting Algorithm ✨
   - **How:** This is a clever, optimal algorithm. It maintains a candidate and a count. Iterate through the array: if the count is 0, pick a new candidate. If the current number is the candidate, increment the count; otherwise, decrement it. The candidate remaining at the end is the majority element.
   - **Complexity:** $O(n)$ time and an amazing $O(1)$ space.

---

## Optimal Choice

The Boyer-Moore Voting Algorithm is the superior solution in terms of space complexity and is a common follow-up question in interviews. However, the HashMap approach is very intuitive, easy to implement, and a perfectly valid way to solve the problem.

---

## Key Insight

The problem can be solved by simply finding the most frequent element, because the definition of a majority element (more than $n/2$) guarantees it will have the highest frequency. A HashMap is the most direct data structure for tracking frequencies of arbitrary items.