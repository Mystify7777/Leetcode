# 414. Third Maximum Number

## Problem Statement

Given an integer array `nums`, return the **third distinct maximum** number in this array. If the third maximum does not exist, return the **maximum** number.

**Example 1:**

- Input: `nums = [3, 2, 1]`
- Output: `1`
- Explanation: The third distinct maximum is 1.

**Example 2:**

- Input: `nums = [1, 2]`
- Output: `2`
- Explanation: The third distinct maximum does not exist, so return the maximum number (2).

**Example 3:**

- Input: `nums = [2, 2, 3, 1]`
- Output: `1`
- Explanation: Note that the third distinct maximum is 1, not 2. Duplicates are ignored.

---

## Approach: Three Variable Tracking

### Why This Approach?

- We only need to track **three distinct maximum values**
- No need to sort the entire array (which would be O(n log n))
- Single pass through the array with **O(n) time** and **O(1) space**
- Handles duplicates by skipping them

### How It Works

```text
1. Maintain three variables: max1, max2, max3
2. For each number in the array:
   - Skip if it's a duplicate (already seen)
   - If greater than max1, cascade all values down
   - Else if greater than max2, cascade max2 to max3
   - Else if greater than max3, update max3
3. Return max3 if it exists, otherwise return max1
```

### Step-by-Step Example

**Input:** `[3, 2, 1, 4, 2]`

| Step | Current Number | max1 | max2 | max3 | Action |
| ------ | --------------- | ------ | ------ | ------ | -------- |
| Init | - | null | null | null | Initialize |
| 1 | 3 | 3 | null | null | First number becomes max1 |
| 2 | 2 | 3 | 2 | null | Less than max1, becomes max2 |
| 3 | 1 | 3 | 2 | 1 | Less than max2, becomes max3 |
| 4 | 4 | 4 | 3 | 2 | Greater than max1, cascade all |
| 5 | 2 | 4 | 3 | 2 | Duplicate of max3, skip |

**Result:** max3 = 2 (third distinct maximum)

---

## Implementation Details

### Using Integer Objects (null-safe)

**Why use `Integer` instead of `int`?**

- Allows us to use `null` to check if a value has been set
- Avoids confusion with `Integer.MIN_VALUE` which could be an actual input

```java
public int thirdMax(int[] nums) {
    Integer max1 = null;
    Integer max2 = null;
    Integer max3 = null;
    
    for (Integer n : nums) {
        // Skip duplicates
        if (n.equals(max1) || n.equals(max2) || n.equals(max3)) 
            continue;
        
        // Update max1 and cascade
        if (max1 == null || n > max1) {
            max3 = max2;
            max2 = max1;
            max1 = n;
        } 
        // Update max2 and cascade
        else if (max2 == null || n > max2) {
            max3 = max2;
            max2 = n;
        } 
        // Update max3
        else if (max3 == null || n > max3) {
            max3 = n;
        }
    }
    
    // Return max3 if it exists, otherwise max1
    return max3 == null ? max1 : max3;
}
```

### Key Points

1. **Duplicate Handling:**
   - Use `.equals()` to check if the current number is already tracked
   - Skip duplicates to ensure we're tracking distinct values

2. **Cascading Updates:**
   - When a new max1 is found, shift max1 → max2 → max3
   - When a new max2 is found, shift max2 → max3
   - This maintains the order of top 3 maximums

3. **Return Logic:**
   - If `max3 == null`, we don't have 3 distinct numbers
   - Return `max1` (the maximum) in that case

---

## Alternative Approach: Using Long.MIN_VALUE

```java
public int thirdMax(int[] nums) {
    long max = Long.MIN_VALUE;
    long secondMax = Long.MIN_VALUE;
    long thirdMax = Long.MIN_VALUE;
    
    for(int n : nums){
        // Skip duplicates
        if(n == max || n == secondMax || n == thirdMax) 
            continue;
            
        if(n > max){
            thirdMax = secondMax;
            secondMax = max;
            max = n;
        }
        else if(n > secondMax){
            thirdMax = secondMax;
            secondMax = n;
        }
        else if(n > thirdMax){
            thirdMax = n;
        }
    }
    
    if(thirdMax != Long.MIN_VALUE)
        return (int)thirdMax;
    else 
        return (int)max;
}
```

**Why `Long.MIN_VALUE`?**

- Integer range is `-2^31` to `2^31 - 1`
- Using `Long.MIN_VALUE` ensures even `Integer.MIN_VALUE` in input works
- No ambiguity between "not set" and actual minimum integer value

---

## Time & Space Complexity Analysis

| Aspect | Complexity | Explanation |
| -------- | ----------- | ------------- |
| **Time** | O(n) | Single pass through the array |
| **Space** | O(1) | Only 3 variables regardless of input size |

This is optimal since we must examine every element at least once.

---

## Edge Cases to Consider

1. **Less than 3 distinct numbers:**
   - `[1, 2]` → Return max1 (2)
   - `[5, 5, 5]` → Return max1 (5)

2. **Exactly 3 distinct numbers:**
   - `[1, 2, 3]` → Return 1

3. **Array with duplicates:**
   - `[2, 2, 3, 1]` → Skip duplicate 2, return 1

4. **Negative numbers:**
   - `[-1, -2, -3]` → Return -3

5. **Integer.MIN_VALUE in array:**
   - Using `Integer` objects handles this correctly
   - Alternative approach uses `Long.MIN_VALUE` sentinel

6. **Single element:**
   - `[1]` → Return 1 (max1)

---

## Common Mistakes to Avoid

1. **Using `==` instead of `.equals()` for Integer comparison:**
   - Integer caching makes this work for -128 to 127, but fails outside
   - Always use `.equals()` for Integer objects

2. **Using `Integer.MIN_VALUE` as sentinel:**
   - Fails when the array contains `Integer.MIN_VALUE`
   - Use `null` or `Long.MIN_VALUE` instead

3. **Not handling duplicates:**
   - Must skip duplicates to count only distinct maximums

4. **Forgetting to cascade values:**
   - When updating max1, must shift max1 → max2 → max3

---

## Related Problems

- `215. Kth Largest Element in an Array`
- `703. Kth Largest Element in a Stream`
- `347. Top K Frequent Elements`
- `Finding maximum/minimum in array problems`
