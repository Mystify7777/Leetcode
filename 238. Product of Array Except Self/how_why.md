# How_Why.md

## Problem

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the **product of all the elements of `nums` except `nums[i]`**.  
You must solve it **without using division** and in O(n) time.

---

## How (Step-by-step Solution)

### Approach: Prefix & Suffix Products

1. Initialize an output array `ans` with all 1’s.
2. **Left pass**:  
   - Maintain a running product `curr = 1`.  
   - For each index `i`, set `ans[i] *= curr`.  
   - Update `curr *= nums[i]`.  
   - After this pass, each `ans[i]` contains product of elements **to the left** of `i`.
3. **Right pass**:  
   - Reset `curr = 1`.  
   - Traverse from right to left.  
   - For each index `i`, multiply `ans[i] *= curr`.  
   - Update `curr *= nums[i]`.  
   - After this pass, each `ans[i]` also incorporates product of elements **to the right** of `i`.

Thus `ans[i] = product of nums[0..i-1] * product of nums[i+1..n-1]`.

---

## Why (Reasoning)

- Division isn’t allowed, so we compute left and right products separately.  
- `ans[i]` is built by multiplying:
  - The product of all elements before `i` (from left pass).  
  - The product of all elements after `i` (from right pass).  
- This avoids extra space (except output array).

---

## Complexity Analysis

- **Time Complexity**: O(n) → two linear passes.  
- **Space Complexity**: O(1) extra (ignoring output array).  

---

## Example Walkthrough

### Input

`nums = [1, 2, 3, 4]`

### Left Pass

``` java
ans = [1, 1, 1, 1]
curr = 1

i=0: ans[0] = 1 → ans=[1,1,1,1]; curr=11=1
i=1: ans[1] = 1 → ans=[1,1,1,1]; curr=12=2
i=2: ans[2] = 2 → ans=[1,1,2,1]; curr=23=6
i=3: ans[3] = 6 → ans=[1,1,2,6]; curr=64=24

```

### Right Pass

``` java
curr = 1

i=3: ans[3] = 1 → ans=[1,1,2,6]; curr=14=4
i=2: ans[2] = 4 → ans=[1,1,8,6]; curr=43=12
i=1: ans[1] = 12 → ans=[1,12,8,6]; curr=122=24
i=0: ans[0] = 24 → ans=[24,12,8,6]; curr=241=24
```

### Output

`[24, 12, 8, 6]`

---

## Alternate Approaches

1. **Using Division** (not allowed in this problem) → Compute total product, divide by `nums[i]`.  
2. **Extra Space** → Store prefix and suffix arrays separately, then multiply.  

✅ **Best choice**: Prefix + Suffix in one pass each (space-efficient).
