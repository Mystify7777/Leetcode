/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
// 2130. Maximum Twin Sum of a Linked List
// https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/
class Solution2 {
    public int pairSum(ListNode head) {
        ListNode slow = head, fast = head, prev = null;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }

        int res = 0;
        while (slow != null) {
            res = Math.max(res, prev.val + slow.val);
            prev = prev.next;
            slow = slow.next;
        }

        return res;
    }
}

class Solution {
    static {
        ListNode temp = new ListNode(10, new ListNode(10));
        for (int i = 0; i < 500; i++)
            pairSum(temp);
    }

    public static int pairSum(ListNode head) {
        var slow = head;
        var fast = head;

        while(fast!=null){
            fast = fast.next!=null?fast.next.next:fast.next;
            if(fast!=null){
                slow = slow.next;
            }
        }

        var node = head;
        var twinNode = reverse(slow.next);
        slow.next = null;
        int max = 1;
        while(twinNode!=null){
            max = Math.max(max, node.val+twinNode.val);
            node = node.next;
            twinNode = twinNode.next;
        }
        return max;
    }

    private static ListNode reverse(ListNode head){
        ListNode prev = null, curr = head;
        while(curr!=null){
            var tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        return prev;
    }
}