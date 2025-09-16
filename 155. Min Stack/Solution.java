/**
class MinStack {
    Stack<Integer> s = new Stack<>(), st = new Stack<>();
    public void push(int val) {
        s.push(val);
        if (st.isEmpty() || val <= st.peek()) st.push(val);
    }
    public void pop() {
        if (s.peek().equals(st.peek())) st.pop();
        s.pop();
    }
    public int top() { return s.isEmpty() ? -1 : s.peek(); }
    public int getMin() { return st.isEmpty() ? -1 : st.peek(); }
}
 */
class MinStack {

    private Node head;
      
    public MinStack() {
    }
    
    public void push(int val) {
       if(head == null)
            head = new Node(val, val, null);
        else
            head = new Node(val, Math.min(val, head.min), head);
    }
    
    public void pop() {
        head = head.next;
    }
    
    public int top() {
        return head.val;
    }
    
    public int getMin() {
        return head.min;
    }

    private class Node {
        int val;
        int min;
        Node next;

        Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
}
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */