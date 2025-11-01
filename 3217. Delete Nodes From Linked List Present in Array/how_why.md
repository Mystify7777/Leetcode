# 3217. Delete Nodes From Linked List Present in Array — how and why

## Problem recap

You're given a singly-linked list and an array `nums`. You need to delete all nodes from the linked list whose values appear in `nums`, and return the modified list.

For example, if the list is `[1, 2, 3, 4, 5]` and `nums = [2, 4]`, the result should be `[1, 3, 5]`.

## Core intuition

This is a linked list filtering problem. The key challenges are:

1. **Fast lookup:** We need to quickly check if a node's value exists in `nums`
2. **List reconstruction:** We need to maintain proper links while skipping deleted nodes

The solution uses a boolean frequency array for O(1) lookup, which is highly efficient when the values have a reasonable range.

## Approach — boolean array with dummy head

1. **Find the maximum value** in `nums` to determine the array size needed
2. **Build a boolean lookup array** where `freq[i] = true` means value `i` should be deleted
3. **Use a dummy head** to simplify edge cases (like deleting the original head)
4. **Traverse the list** and only link nodes whose values are NOT in the frequency array
5. **Terminate properly** by setting the final node's next to null
6. **Return** `dummy.next` as the new head

## Implementation (matches `Solution.java`)

```java
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        // Find max value to determine array size
        int max = -1;
        for (int num : nums) {
            max = num > max ? num : max;
        }
        
        // Build boolean lookup array
        boolean[] freq = new boolean[max + 1];
        for (int num : nums) freq[num] = true;

        // Use dummy head to simplify edge cases
        ListNode temp = new ListNode();
        ListNode current = temp;

        // Traverse and filter
        while (head != null) {
            // Keep node if value is out of range OR not marked for deletion
            if (head.val >= freq.length || freq[head.val] == false) {
                current.next = head;
                current = current.next;
            }
            head = head.next;
        }

        // Terminate the new list properly
        current.next = null;
        return temp.next;
    }
}
```

## Why this works

**Dummy head technique:**

The dummy node `temp` acts as a placeholder before the actual head. This eliminates special-case handling when the original head needs to be deleted. We always have a stable starting point.

**Boolean array lookup:**

By marking `freq[value] = true` for all values in `nums`, we get O(1) lookup time. The condition `head.val >= freq.length` handles values larger than `max`, which should be kept (not in `nums`).

**Proper termination:**

Setting `current.next = null` is crucial. Without this, if the last nodes were deleted, the new tail would still point to deleted nodes, creating a corrupted list.

## Complexity

- **Time:** O(n + m + k) where:
  - n = length of `nums` (to find max and build freq array)
  - m = length of linked list (to traverse)
  - k = max value in `nums` (to allocate freq array)
  
  In practice, this is O(n + m) if k is reasonable.

- **Space:** O(k) for the boolean frequency array, where k = max value in `nums`

## Alternative approach — using HashSet

**Your question:** *"Can't we use a set to solve it too? instead of using frequency array? also how would it differ?"*

**Answer:** Yes! Using a `HashSet<Integer>` is actually a more common and often better approach. Here's how:

```java
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        // Build HashSet for O(1) lookup
        Set<Integer> toDelete = new HashSet<>();
        for (int num : nums) {
            toDelete.add(num);
        }

        // Use dummy head
        ListNode dummy = new ListNode();
        ListNode current = dummy;

        // Traverse and filter
        while (head != null) {
            if (!toDelete.contains(head.val)) {
                current.next = head;
                current = current.next;
            }
            head = head.next;
        }

        current.next = null;
        return dummy.next;
    }
}
```

**Comparison:**

| Aspect | Boolean Array (your solution) | HashSet |
|--------|------------------------------|---------|
| **Time complexity** | O(1) lookup | O(1) average lookup |
| **Space complexity** | O(max value) | O(number of unique values) |
| **Best when** | Values are small and dense (e.g., 0-1000) | Values are large or sparse (e.g., [1, 1000000]) |
| **Worst case** | Wastes space if max is huge but few values | Hash collisions rare but possible |
| **Code simplicity** | Slightly more complex (find max, bounds check) | Simpler and more intuitive |

**When to use each:**

- **Boolean array:** When values are guaranteed to be in a small, dense range (like 0-10000). More memory-efficient for dense data.
- **HashSet:** When values can be arbitrary or very large. Safer default choice. Only uses memory proportional to the number of unique values to delete.

**Example where HashSet wins:**

If `nums = [1, 1000000]`, the boolean array wastes 1 million entries, while HashSet only uses 2 entries.

**Example where boolean array wins:**

If `nums` contains all values 0-10000, the boolean array is more compact and faster (no hashing overhead).

## Example walkthrough

Suppose `head = [1, 2, 3, 4, 5]` and `nums = [2, 4]`.

1. Find max = 4, create `freq = [false, false, true, false, true]` (indices 0-4)
2. Mark: `freq[2] = true`, `freq[4] = true`
3. Traverse:
   - Node 1: `freq[1] = false` → keep → link it
   - Node 2: `freq[2] = true` → skip
   - Node 3: `freq[3] = false` → keep → link it
   - Node 4: `freq[4] = true` → skip
   - Node 5: `5 >= freq.length` → keep → link it
4. Set `current.next = null`
5. Return result: `[1, 3, 5]`

## Edge cases to consider

- Empty `nums`: keep all nodes
- All nodes should be deleted: return null (dummy.next is null)
- Head needs deletion: dummy node handles this elegantly
- Values in list larger than max in `nums`: the `head.val >= freq.length` check handles this
- Duplicate values in `nums`: doesn't matter (boolean array handles it naturally)

## Takeaways

- Dummy head is a classic technique for simplifying linked list modifications
- Choice between boolean array and HashSet depends on the value range:
  - Small dense range → boolean array
  - Large sparse range → HashSet
- Always remember to terminate the new list with `current.next = null`
- For interview settings, HashSet is usually the safer default unless constraints specifically favor boolean arrays
