# How_Why.md: N-Repeated Element in Size 2N Array

## Problem

You are given an integer array `nums` of length `2n` where the array contains `n + 1` **unique** elements, and one element is repeated **exactly n times**.

Return the element that is repeated `n` times.

**Example:**

```java
Input: nums = [1,2,3,3]
Output: 3

Input: nums = [2,1,1,1]
Output: 1

Input: nums = [5,1,5,5,5]
Output: 5
```

---

## Approach: Early Termination with Gap Check

### Idea

* Since the repeated element appears **n times** in an array of size **2n**, it must appear **at least once** in every window of size 3

* **Key Insight:** Any element repeated n times in a 2n-sized array cannot have a gap larger than 2 between any two occurrences

* Check first n-2 elements: if any element appears within distance 2, it's the repeated element

### Code

```java
class Solution {
    public int repeatedNTimes(int[] A) {
        // Check the first n-2 elements
        // If an element repeats, it must appear within distance 2
        for (int i = 0; i < A.length - 2; i++) {
            if (A[i] == A[i + 1] || A[i] == A[i + 2]) {
                return A[i];
            }
        }
        
        // If not found in first n-2 elements, 
        // the last element must be the repeated one
        return A[A.length - 1];
    }
}
```

### Why This Works

* **Mathematical Proof:**
  - Array length = 2n
  - One element appears n times
  - If we check n-2 elements with a window of size 3, we cover positions [0, n]
  - At least one of the first n-2 elements must appear again within distance 2

* **Pigeonhole Principle:**
  - If an element appears n times in 2n positions
  - Max gap between occurrences = 2
  - Otherwise, we'd have more than 2n - n = n positions for gaps

* **Example:** nums = [1,2,3,3] (n=2, length=4)

  ```s
  Check A[0]=1 with A[1]=2 and A[2]=3: No match
  A[0] == A[1]? No (1 != 2)
  A[0] == A[2]? No (1 != 3)
  
  Check A[1]=2 with A[2]=3 and A[3]=3: Found!
  A[1] == A[2]? No (2 != 3)
  A[1] == A[3]? No (2 != 3)
  
  Not found in loop, return A[3] = 3 ✓
  ```

* Time Complexity: **O(n)** - at most check n-2 elements
* Space Complexity: **O(1)** - constant space

---

## Alternative Approach 1: HashSet

### Code*

```java
class Solution {
    public int repeatedNTimes(int[] A) {
        HashSet<Integer> set = new HashSet<>();
        
        for (int num : A) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }
        
        return -1; // Should never reach here
    }
}
```

**Pros:**

- Simple and straightforward
- Works in all cases

**Cons:**

- Uses O(n) extra space
- Doesn't leverage the mathematical property

---

## Alternative Approach 2: HashMap for Frequency

### Code**

```java
class Solution {
    public int repeatedNTimes(int[] A) {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int num : A) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                return entry.getKey();
            }
        }
        
        return -1;
    }
}
```

---

## Comparison

| Approach | Time | Space | Notes |
| ---------- | ------ | ------- | ------- |
| Gap Check | O(n) | O(1) | **Optimal**, uses mathematical insight |
| HashSet | O(n) | O(n) | Simple, practical |
| HashMap | O(n) | O(n) | More verbose |

---

## Why This Approach (Gap Check)

* **Optimal:** O(1) space complexity
* **Clever:** Uses mathematical constraint of the problem
* **Fast:** Early termination in most cases
* **Interview Ready:** Shows deep problem understanding
* **Practical:** Leverages unique problem properties

**Key Takeaway:** Always analyze the problem constraints to find optimized solutions!
