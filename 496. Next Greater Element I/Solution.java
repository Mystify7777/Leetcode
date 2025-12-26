// 496. Next Greater Element I

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] nextGreater = new int[10001];
        Stack<Integer> stack = new Stack<>();

        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums2[i]) {
                stack.pop();
            }
            nextGreater[nums2[i]] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums2[i]);
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = nextGreater[nums1[i]];
        }

        return nums1;
    }
}

//explain please 

//why???

/**
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] map = new int[10001];
        int[] stack = new int[nums2.length];
        int top = -1;

        for(int i = nums2.length - 1; i >= 0; i--) {
            if (top == -1) {
                stack[++top] = nums2[i];
                map[nums2[i]] = -1;
            } else {
                while((top != -1) && (nums2[i] >= stack[top])) {
                    top--;
                }

                if (top == -1) {
                    stack[++top] = nums2[i];
                    map[nums2[i]] = -1;
                } else {
                    map[nums2[i]] = stack[top];
                    stack[++top] = nums2[i];
                }
            }
        }

        int[] ans = new int[nums1.length];
        for(int i = 0; i < nums1.length; i++) {
            ans[i] = map[nums1[i]];
        }

        return ans;
    }
}
 */