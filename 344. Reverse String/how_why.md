# How & Why: LeetCode 344 - Reverse String

-----

## Problem Restatement

You are given an array of characters, `char[] s`. Your task is to reverse the order of the characters within this array. The reversal must be done **in-place**, meaning you cannot create a new array to store the result.

-----

## How to Solve

The most efficient way to solve this is using the **two-pointer technique**:

  - Place one pointer (`left`) at the beginning of the array and another (`right`) at the end.
  - Swap the characters at the `left` and `right` positions.
  - Move the `left` pointer forward and the `right` pointer backward, repeating the swap until the pointers meet or cross in the middle.

### Implementation

```java
public void reverseString(char[] s) {
    int left = 0;
    int right = s.length - 1;
    
    while (left < right) {
        // Swap the characters
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        
        // Move pointers towards the center
        left++;
        right--;
    }
}
```

-----

## Why This Works

1.  **Symmetrical Swapping**: By swapping the first character with the last, the second with the second-to-last, and so on, we symmetrically build the reversed array from the outside in.
2.  **In-Place Modification**: This method directly manipulates the original array, using only a single temporary variable for the swap. This satisfies the $O(1)$ space constraint.
3.  **Correct Termination**: The loop condition `left < right` ensures that we stop exactly when the middle is reached. For odd-length arrays, the middle element remains untouched (as it should), and for even-length arrays, the pointers cross after the last swap.

-----

## Complexity Analysis

  - **Time Complexity**: $O(n)$, where `n` is the length of the array. We perform approximately `n/2` swaps.
  - **Space Complexity**: $O(1)$. The operation is performed in-place with no extra space proportional to the input size.

-----

## Example Walkthrough

Input:

```
s = ['h', 'e', 'l', 'l', 'o']
```

**Process:**

1.  **Initial**: `left = 0` ('h'), `right = 4` ('o').
      - Swap `s[0]` and `s[4]`. `s` becomes `['o', 'e', 'l', 'l', 'h']`.
      - `left` becomes 1, `right` becomes 3.
2.  **Next**: `left = 1` ('e'), `right = 3` ('l').
      - Swap `s[1]` and `s[3]`. `s` becomes `['o', 'l', 'l', 'e', 'h']`.
      - `left` becomes 2, `right` becomes 2.
3.  **End**: Now `left` is not less than `right` (`2 < 2` is false). The loop terminates.

**Output:**

```
['o', 'l', 'l', 'e', 'h']
```

-----

## Alternate Approaches

1.  **Recursion**:
      - A function that swaps the outer two elements and then calls itself on the inner subarray.
      - Time: $O(n)$, Space: $O(n)$ due to the function call stack.
2.  **Using a Stack**:
      - Push all characters from the array onto a stack, then pop them back into the array in LIFO (Last-In, First-Out) order.
      - Time: $O(n)$, Space: $O(n)$ to store the characters in the stack.

### Optimal Choice

The **two-pointer approach** is the best solution. It is simple, intuitive, and perfectly meets the problem's constraint of an in-place modification with constant extra space. The other methods are functionally correct but fail the space complexity requirement.

-----

## Key Insight

The problem is a classic example of in-place array manipulation. The key insight is that a reversal can be achieved by symmetrically swapping elements from the ends of the array toward the center.