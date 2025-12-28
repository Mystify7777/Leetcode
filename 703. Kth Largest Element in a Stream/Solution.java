// 703. Kth Largest Element in a Stream
class KthLargest {
    private int k;
    private PriorityQueue<Integer> minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>(k);
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.offer(num);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
        }
    }

    public int add(int val) {
        if (minHeap.size() < k) {
            minHeap.offer(val);
        } else if (val > minHeap.peek()) {
            minHeap.offer(val);
            minHeap.poll();
        }
        return minHeap.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */

 /**
 class KthLargest {
    int[] heap;
    int size;

    public KthLargest(int k, int[] nums) {
        this.size = 0;
        this.heap = new int[k];
        for (int num: nums)
            add(num); 
    }
    
    public int add(int val) {
        if (!isFull()) {
            offer(val);
        } else {
            int peek = peek();
            if (peek < val) {
                poll();
                offer(val);
            }
        }

        return peek();
    }

    int peek() {
        return heap[0];
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == heap.length;
    }
    
    int poll() {
        int result = heap[0];

        int half = --size / 2;
        int k = 0;
        int x = heap[size]; // delete and siftDown it

        // sift down
        while (k < half) {
            int child = 2 * k + 1;
            int c = heap[child];
            int right = child + 1;
            if (right < size && c > heap[right]) {
                c = heap[child = right];
            }
            
            if (x <= c)
                break;

            heap[k] = c;
            k = child;
        }

        heap[k] = x;

        return result;
    }

    void offer(int x) {
        int k = size;

        while (k > 0) {
            int parent = (k - 1) / 2;
            int e = heap[parent];

            if (x >= e)
                break;

            heap[k] = e;
            k = parent;
        }

        heap[k] = x;

        size++;
    } 
}
  */