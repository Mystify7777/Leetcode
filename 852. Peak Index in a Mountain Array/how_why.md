# 852. Peak Index in a Mountain Array

## Problem Statement

An array `arr` is a mountain array if and only if:

- `arr.length >= 3`
- There exists some `i` with `0 < i < arr.length - 1` such that:
  - `arr[0] < arr[1] < ... < arr[i - 1] < arr[i]`
  - `arr[i] > arr[i + 1] > ... > arr[arr.length - 1]`

Given a mountain array `arr`, return the index `i` such that `arr[i]` is the peak element.

You must solve it in O(log n) time complexity.

## Approach: Binary Search

### Why Binary Search?

- The array has a **unimodal** property (increases then decreases)
- We need **O(log n)** time complexity
- We can eliminate half of the search space in each iteration based on the slope
- Linear search would be O(n), which doesn't meet the requirement

### How It Works

1. **Initialize Two Pointers**
   - `low` = 0 (start of array)
   - `high` = arr.length - 1 (end of array)

2. **Binary Search Loop**
   - While `low < high` (not `<=` because we're looking for a single peak)
   - Calculate middle index: `mid = low + (high - low) / 2`

3. **Two Cases Based on Slope**

   **Case 1: arr[mid] < arr[mid + 1]** (Ascending slope)
   - We're on the left side of the peak
   - The peak must be at `mid + 1` or to the right
   - Move `low = mid + 1`

   **Case 2: arr[mid] > arr[mid + 1]** (Descending slope)
   - We're on the right side of the peak OR at the peak
   - The peak could be at `mid` itself
   - Move `high = mid` (not `mid - 1` because `mid` could be the answer)

4. **Loop Exit**
   - When `low == high`, we've found the peak
   - Return `low` (or `high`, they're the same)

### Example Walkthrough

**Example: arr = [0,1,0]**

```java
Initial: low=0, high=2
Iteration 1: mid=1
  arr[1]=1 > arr[2]=0 (descending)
  high = 1
Loop exits: low=1, high=1, return 1 ✓
```

**Example: arr = [0,2,1,0]**

```java
Initial: low=0, high=3
Iteration 1: mid=1
  arr[1]=2 > arr[2]=1 (descending)
  high = 1
Loop exits: low=1, high=1, return 1 ✓
```

**Example: arr = [0,10,5,2]**

```java
Initial: low=0, high=3
Iteration 1: mid=1
  arr[1]=10 > arr[2]=5 (descending)
  high = 1
Loop exits: low=1, high=1, return 1 ✓
```

**Example: arr = [3,4,5,1]**

```java
Initial: low=0, high=3
Iteration 1: mid=1
  arr[1]=4 < arr[2]=5 (ascending)
  low = 2
Iteration 2: mid=2
  arr[2]=5 > arr[3]=1 (descending)
  high = 2
Loop exits: low=2, high=2, return 2 ✓
```

**Example: arr = [24,69,100,99,79,78,67,36,26,19]**

```java
Initial: low=0, high=9
Iteration 1: mid=4
  arr[4]=79 > arr[5]=78 (descending)
  high = 4
Iteration 2: mid=2
  arr[2]=100 > arr[3]=99 (descending)
  high = 2
Iteration 3: mid=1
  arr[1]=69 < arr[2]=100 (ascending)
  low = 2
Loop exits: low=2, high=2, return 2 ✓
```

### Why This Works

**Key Insight**: The comparison with `arr[mid + 1]` tells us which side of the peak we're on:

- If ascending (`arr[mid] < arr[mid + 1]`): Peak is definitely to the right
- If descending (`arr[mid] > arr[mid + 1]`): Peak is at `mid` or to the left

**Why `high = mid` and not `high = mid - 1`?**

- When we see a descending slope, `mid` itself could be the peak
- We can't exclude `mid` from consideration
- This is different from standard binary search where we look for an exact value

**Why loop condition is `low < high` and not `low <= high`?**

- We're converging to a single point (the peak)
- When `low == high`, we've found it
- No need to continue when they meet

## Complexity Analysis

- **Time Complexity**: O(log n)
  - Binary search halves the search space in each iteration
  - At most log₂(n) iterations
  
- **Space Complexity**: O(1)
  - Only using a constant amount of extra space for variables

## Key Insights

1. **Mountain Array Property**: Strictly increasing then strictly decreasing - this guarantees exactly one peak
2. **Slope-Based Decision**: Compare `arr[mid]` with `arr[mid + 1]` to determine which direction to search
3. **Inclusive Upper Bound**: Use `high = mid` (not `mid - 1`) because `mid` could be the peak
4. **No Equality Check Needed**: Unlike finding a target value, we don't need `arr[mid] == target` case
5. **Guaranteed to Find Peak**: The mountain array property ensures a peak exists, so we will always converge to it

## Alternative Approaches

- **Linear Search - O(n)**

```java
for (int i = 0; i < arr.length - 1; i++) {
    if (arr[i] > arr[i + 1]) {
        return i;
    }
}
```

- Simple but doesn't meet the O(log n) requirement
- Good for small arrays but not optimal

## Common Mistakes to Avoid

1. ❌ Using `high = mid - 1` when descending (might skip the peak)
2. ❌ Using `low <= high` as loop condition (causes unnecessary iteration)
3. ❌ Comparing `arr[mid]` with `arr[mid - 1]` without bounds checking
4. ❌ Returning `mid` inside the loop (should wait for convergence)
