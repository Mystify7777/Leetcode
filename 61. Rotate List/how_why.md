# How Why - Explanation 61. Rotate List

[61. Rotate List](https://leetcode.com/problems/rotate-list/)

## Problem

Given the head of a singly-linked list and an integer `k`, rotate the list to the right by `k` places and return the new head.

Example: `1 -> 2 -> 3 -> 4 -> 5` rotated right by `2` becomes `4 -> 5 -> 1 -> 2 -> 3`.

## Intuition

Rotating a list right by `k` is equivalent to:
1. Finding the node at position `len - k` from the head.
2. Breaking the link after that node.
3. Connecting the tail to the old head.

By using a dummy node and two pointers (one to find the total length and another to find the breaking point), we can do this efficiently.

## Approach (Two-Pointer with Dummy Node)

1. Handle edge cases: if head is null or single node, return as is.
2. Create a dummy node pointing to head.
3. Count the total length by traversing to the tail with the fast pointer.
4. Normalize `k = k % length` (handle cases where `k >= length`).
5. Move the slow pointer `length - k` steps forward to locate the breaking point.
6. Reconnect: make the tail point to the original head (circular), update the new head, and break the circle.
7. Return the new head.

This is implemented in [61. Rotate List/Solution.java](61.%20Rotate%20List/Solution.java).

## Why This Works

After rotating right by `k`, the list breaks at position `len - k` from the start. By moving the slow pointer to that position, we identify where to split the list. Temporarily making it circular and then breaking at the correct point accomplishes the rotation in one pass.

## Complexity

- Time: `O(n)` (single traversal to find length and locate the breaking point).
- Space: `O(1)` (only pointers, no extra data structures).

## Edge Cases

- `k = 0` or `k % len = 0`: no rotation, return original list.
- Single node: return as is.
- Empty list: return as is.
- `k > len`: normalize using modulo.

## Example Walkthrough

Input: `1 -> 2 -> 3 -> 4 -> 5`, `k = 2`

1. Total length = 5, `k = 2 % 5 = 2`.
2. Break position from head: `5 - 2 = 3`.
3. Move slow pointer 3 steps: points to node `3`.
4. Fast pointer is at node `5` (tail).
5. Make circular: `5.next = 1` (old head).
6. New head: `slow.next = 4`.
7. Break circle: `3.next = null`.
8. Result: `4 -> 5 -> 1 -> 2 -> 3`.
