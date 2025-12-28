//705. Design HashSet

class Node{
    Node next;
    int val;
    public Node(int key){
        this.val=key;
        this.next=null;
    }
}
class MyHashSet {
    Node head;
    public MyHashSet() {
       head=null; 
    }
    
    public void add(int key) {
        if(head==null) 
        {
            head= new Node(key);
            return;
        }
        boolean doesExists=contains(key);
        if(!doesExists){
            Node temp=head;
            while(temp.next!=null)
                temp=temp.next;
            
            temp.next=new Node(key);
        }
    }
    
    public void remove(int key) {
        if(head==null) return;
        if(head.val==key){
            head=head.next;
            return;
        }
        
        Node temp=head;
        while(temp.next!=null){
            if(temp.next.val==key)
                temp.next=temp.next.next;
            else
                temp=temp.next;
        }
    }
    
    public boolean contains(int key) {
        if(head==null) return false;
        if(head.val==key) return true;
        
        Node temp=head;
        while(temp.next!=null){
            if(temp.next.val==key) return true;
            temp=temp.next;
        }
        return false;
    }
}


/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */

 /**
 	int size = (int)Math.pow(10, 6)+1;
	boolean[] flag;
    public MyHashSet() {
		flag = new boolean[size];
	}
    
    public void add(int key) {
        flag[key]=true;
    }
    
    public void remove(int key) {
        flag[key]=false;
    }
    
    public boolean contains(int key) {
        return flag[key];
    }
  */

  /**
  class MyHashSet {
    private boolean[] set;

    public MyHashSet() {
        set = new boolean[1000001];
    }
    
    public void add(int key) {
        set[key] = true;
    }
    
    public void remove(int key) {
        set[key] = false;
    }
    
    public boolean contains(int key) {
        return set[key];
    }
}
   */