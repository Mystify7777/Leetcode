// 630. Course Schedule III
// https://leetcode.com/problems/course-schedule-iii/
//copypasted

class Solution {
    public int scheduleCourse(int[][] C) {
        Arrays.sort(C, (a,b) -> a[1] - b[1]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);
        int total = 0;
        for (int[] course : C) {
            int dur = course[0], end = course[1];
            if (dur + total <= end) {
                total += dur;
                pq.add(dur);
            } else if (pq.size() > 0 && pq.peek() > dur) {
                total += dur - pq.poll();
                pq.add(dur);
            }
        }
        return pq.size();
    }
}
/**
class Solution {
    public int scheduleCourse(int[][] courses) {
        int n = courses.length;
        long[] c = new long[n];
        n = 0;
        for (int[] x : courses) {
            c[n++] = x[0] | (long)x[1] << 32;
        }
        Arrays.sort(c);
        int r = 0;
        IntPriorityQueue q = new IntPriorityQueue(n);
        
        for (long x : c) {
            int a = (int)x;
            int b = (int)(x >> 32);
            r += a;
            q.offer(-a);
            if (b < r) {
                r += q.poll();
            }
        }
        return q.size();
    }
}

class IntPriorityQueue {
    int[] queue;
    int size;
    
    IntPriorityQueue(int n) {
        this.queue = new int[n];
    }
    
    void offer(int n) {
        int i = size;
        siftup(i, n);
        size = i + 1;
    }
    
    int peek() {
        return queue[0];
    }
    
    int size() {
        return this.size;
    }
    
    boolean isEmpty() {
        return size == 0;
    }
    
    int poll() {
        int[] q = queue;
        int r = q[0];
        int n = --size;
        int x = q[n];
        q[n] = 0;
        if(n > 0)
            siftdown(0, x, n);
        return r;
    }
    
    void siftup(int k, int x) {
        int[] q = queue;
        while(k > 0) {
            int p = (k - 1) >> 1;
            int e = q[p];
            if(x >= e)
                break;
            q[k] = e;
            k = p;
        }
        q[k] = x;
    }
    
    void siftdown(int k, int x, int n) {
        int[] q = queue;
        int h = (n >> 1);
        while(k < h) {
            int c = (k << 1) + 1;
            int e = q[c];
            int r = c + 1;
            if(r < n && q[r] < e)
                e = q[c = r];
            if(x <= e) {
                break;
            }
            q[k] = e;
            k = c;
        }
        q[k] = x;
    }
}
 */