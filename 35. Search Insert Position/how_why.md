# 35. Search Insert Position

## Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with O(log n) runtime complexity.

## Approach: Binary Search

### Why Binary Search?

- The array is **sorted**, which is the key indicator for using binary search
- We need **O(log n)** time complexity, and binary search provides exactly that
- Linear search would be O(n), which doesn't meet the requirement

### How It Works

1. **Initialize Two Pointers**
   - `left` = 0 (start of array)
   - `right` = nums.length - 1 (end of array)

2. **Binary Search Loop**
   - While `left <= right`, calculate the middle index: `mid = left + (right - left) / 2`
   - Note: We use `left + (right - left) / 2` instead of `(left + right) / 2` to avoid potential integer overflow

3. **Three Cases**
   - **nums[mid] == target**: Found! Return `mid`
   - **nums[mid] > target**: Target is in the left half, so `right = mid - 1`
   - **nums[mid] < target**: Target is in the right half, so `left = mid + 1`

4. **Loop Exit**
   - When the loop exits, `left` is the insertion position
   - Why? Because `left` always moves forward past elements smaller than target
   - And `right` always moves backward past elements larger than target
   - When they cross, `left` points to the correct insertion position

### Example Walkthrough

**Example: nums = [1,3,5,6], target = 5**

```java
Initial: left=0, right=3
Iteration 1: mid=1, nums[1]=3 < 5, left=2
Iteration 2: mid=2, nums[2]=5 == 5, return 2
```

**Example: nums = [1,3,5,6], target = 2**

```java
Initial: left=0, right=3
Iteration 1: mid=1, nums[1]=3 > 2, right=0
Iteration 2: mid=0, nums[0]=1 < 2, left=1
Loop exits: left=1, right=0, return 1
```

**Example: nums = [1,3,5,6], target = 7**

```java
Initial: left=0, right=3
Iteration 1: mid=1, nums[1]=3 < 7, left=2
Iteration 2: mid=2, nums[2]=5 < 7, left=3
Iteration 3: mid=3, nums[3]=6 < 7, left=4
Loop exits: left=4, right=3, return 4
```

### Why Return `left` at the End?

When the loop exits without finding the target:

- All elements from index 0 to `right` are smaller than target
- All elements from index `left` to end are greater than target
- Therefore, `left` is exactly where the target should be inserted

## Complexity Analysis

- **Time Complexity**: O(log n)
  - Binary search halves the search space in each iteration
  
- **Space Complexity**: O(1)
  - Only using a constant amount of extra space for variables

## Key Insights

1. **Sorted Array → Think Binary Search**: Whenever you see a sorted array, consider binary search first
2. **The `left` pointer's final position is the answer**: This is a common pattern in binary search problems
3. **Overflow Prevention**: Using `left + (right - left) / 2` is safer than `(left + right) / 2`
4. **Handles All Edge Cases**: Works for targets smaller than all elements, larger than all elements, and in between
