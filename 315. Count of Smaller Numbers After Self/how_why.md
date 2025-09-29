# 315. Count of Smaller Numbers After Self — How & Why

## Problem Restated

For each element in an integer array `nums`, count how many numbers **to its right** are smaller than it.  
Example:

```java

Input: [5, 2, 6, 1]
Output: [2, 1, 1, 0]

```

---

## 1. Brute Force Approach

**Idea**:  
For each element, scan all elements to its right and count how many are smaller.

**Pseudocode**:

```java

for i in 0..n-1:
count = 0
for j in i+1..n-1:
if nums[j] < nums[i]:
count++
result[i] = count

```

**Example Walkthrough (nums = [5, 2, 6, 1])**:

- i=0 → [2,6,1] → 2 smaller → result[0]=2  
- i=1 → [6,1] → 1 smaller → result[1]=1  
- i=2 → [1] → 1 smaller → result[2]=1  
- i=3 → [] → 0 → result[3]=0  

Final = `[2, 1, 1, 0]`

**Complexity**:

- Time: **O(n²)**
- Space: **O(1)**

**Limitation**: Too slow for large arrays.

---

## 2. Divide & Conquer Approach (Your Code)

This solution modifies **merge sort** to count smaller elements while sorting.

**Key Idea**:

- Split array into halves.
- Recursively sort both halves.
- During merge:
  - If `nums[left] > nums[right]`, then `nums[right]` is smaller than **all remaining elements in left half**.
  - Increase the count for that left element accordingly.

**Steps**:

1. Wrap each element with its original index.
2. Perform merge sort:
   - Left and right halves sorted individually.
   - Merge them while counting.
3. Store counts in a result array.

**Example Walkthrough (nums = [5, 2, 6, 1])**:

- Left half [5,2] → sorted [2,5], counts: result[0]=1  
- Right half [6,1] → sorted [1,6], counts: result[2]=1  
- Merge [2,5] & [1,6]:
  - 1 < 2 → increment `numElemsRightArrayLessThanLeftArray=1`
  - Place 2 → add +1 → result[1]=1
  - Place 5 → add +1 → result[0]=2
  - Place 6
- Final result = `[2,1,1,0]`

**Complexity**:

- Time: **O(n log n)**
- Space: **O(n)**

**Why better**: Efficient counting during merge eliminates the need for nested loops.

---

## 3. Optimized Alternative — Binary Indexed Tree (Fenwick Tree)

Another way is to process numbers **from right to left** while maintaining frequencies of seen numbers in a BIT/Fenwick tree.

**Steps**:

1. Coordinate compress the numbers.
2. For each number from right to left:
   - Query how many numbers smaller than it are already seen.
   - Update the BIT with this number.
3. Reverse results at the end.

**Complexity**:

- Time: **O(n log n)**
- Space: **O(n)**

---

## ✨ Conclusion

- **Brute Force**: Simple but too slow.  
- **Merge Sort (Your Solution)**: Smart divide-and-conquer with counting → **O(n log n)**.  
- **Fenwick Tree**: Another O(n log n) method, often faster in practice for large inputs.  

The merge sort solution is both correct and efficient.

---
