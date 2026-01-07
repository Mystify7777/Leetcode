# 944. Delete Columns to Make Sorted

## Problem Understanding

Given an array of strings where all strings have the same length, we need to find the minimum number of columns to delete such that the remaining columns are sorted in lexicographic order from top to bottom.

### Example

```java
Input: strs = ["ca","bb","ac"]
Output: 1
```

Explanation:
If we delete the first column, we get ["a","b","c"] which is sorted.
If we delete the second column, we get ["c","b","a"] which is NOT sorted.
So the minimum number of deletions is 1.

## Solution Approach

### Algorithm Overview

We iterate through each column (from left to right) and check if that column is sorted in ascending order. If it's not sorted, we increment the deletion count.

### Step-by-Step Logic

1. **Edge case handling**: Return 0 if the input array is empty or null
2. **Iterate through columns**: For each column index (0 to length of first string)
3. **Check if column is sorted**:
   - Start with the character from the first string at that column
   - For each subsequent string, compare its character at that column with the previous character
   - If we find `current_char < previous_char`, the column is NOT sorted, so increment deletions and break
   - Otherwise, update `previous_char` for the next iteration
4. **Return** the total number of unsorted columns (deletions needed)

### Code Walkthrough

```java
public int minDeletionSize(String[] strs) {
    if(strs == null || strs.length == 0) return 0;
    int deletions = 0;
    
    // Iterate through each column
    for(int column = 0; column < strs[0].length(); column++){
        char character = strs[0].charAt(column);  // Start with first row
        
        // Check if this column is sorted
        for(int word = 0; word < strs.length; word++){
            if(strs[word].charAt(column) < character){
                // Not sorted! Delete this column
                deletions++;
                break;  // No need to check rest of column
            }
            character = strs[word].charAt(column);  // Update for next comparison
        }
    }
    
    return deletions;
}
```

## Complexity Analysis

- **Time Complexity**: O(m × n) where m is the number of strings and n is the length of each string
  - We examine each column once
  - For each column, we compare at most m characters
  
- **Space Complexity**: O(1)
  - We only use a few variables for tracking

## Key Insights

1. **Column-by-column approach**: Instead of trying to find the optimal set of columns to keep, we simply check each column independently
2. **Greedy works here**: A column either is or isn't sorted - if it's not sorted, it must be deleted
3. **Early termination**: Once we find the column is not sorted, we can break and move to the next column
4. **Why we update `character`**: We need to track the previous character to ensure each subsequent character is >= the previous one

## Alternative Approach (in comments)

The solution also includes a refactored version that:

- Converts strings to char arrays (slight optimization to avoid repeated `charAt()` calls)
- Uses a separate `isSort()` helper function for clarity
- May have slightly better performance due to fewer method calls

## Notes

- The char[] conversion variant reduces repeated `charAt()` calls and can improve cache locality, but allocates an extra matrix; both approaches remain `O(m*n)`.
- Small micro-opt: initialize `character` from row 0 and start the inner loop at row 1 to avoid a redundant self-compare.
- Early `break` on a bad column is key to avoiding unnecessary work.
