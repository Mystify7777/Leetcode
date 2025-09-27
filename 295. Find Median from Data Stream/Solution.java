// 295. Find Median from Data Stream
class MedianFinder {
private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
        private PriorityQueue<Integer> large = new PriorityQueue<>();
        private boolean even = true;

    public MedianFinder() {
        
    }
    
    public void addNum(int num) {
    if (even) {
        large.offer(num);
        small.offer(large.poll());
    } else {
        small.offer(num);
        large.offer(small.poll());
    }
    even = !even;
}
    
    public double findMedian() {
    if (even)
        return (small.peek() + large.peek()) / 2.0;
    else
        return small.peek();
}
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */

 /**
 class MedianFinder {
    PriorityQueue<Integer> min;
    PriorityQueue<Integer> max;

    public MedianFinder() {
        min = new PriorityQueue<>();
        max =
            new PriorityQueue<>(
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer a, Integer b) {
                        return b - a;
                    }
                }
            );
    }

    public void addNum(int num) {
        int s1 = max.size();
        int s2 = min.size();
        if (s1 == s2) {
            if (max.size() == 0) {
                max.add(num);
            } else {
                if (num <= min.peek()) {
                    max.add(num);
                } else { 
                    min.add(num);
                    max.add(min.poll());
                }
            }
        } else {
            if (num <= max.peek()) {
                max.add(num);
                min.add(max.poll());
            } else {
                min.add(num);
            }
        }
    }

    public double findMedian() {
        int s1 = max.size();
        int s2 = min.size();
        // System.out.println(max);
        // System.out.println(min);
        if (s1 == s2) {
            return (min.peek() + max.peek() * 1.0) / 2;
        }
        return max.peek() * 1.0;
    }
}
  */
/**
didn't get this code..
class MedianFinder {

    int[] hashVals;
    int median;
    int medianCount2x;

    public MedianFinder() {
        hashVals = new int[200001];
        median = Integer.MIN_VALUE;
        medianCount2x = 1;
    }
    
    public void addNum(int num) {
        hashVals[num+100000]++;
        if (median != Integer.MIN_VALUE) {
            if (num > median) {
                if (medianCount2x < 2*hashVals[median+100000]) {
                    medianCount2x++;
                } else {
                    medianCount2x = 1;
                    while (hashVals[++median+100000] == 0) {}
                }
            } else if (num < median) {
                if (medianCount2x > 1) {
                    medianCount2x--;
                } else {
                    while (hashVals[--median + 100000] ==0) {}
                    medianCount2x = 2*hashVals[median+100000];
                }
            } else {
                medianCount2x++;
            }
        } else {
            median = num;
        }
    }
    
    public double findMedian() {
        if (medianCount2x % 2 == 0) {
            if (2*hashVals[median+100000] > medianCount2x) {
                return median;
            } else {
                int median2 = median;
                while (hashVals[++median2 + 100000] == 0) {}
                return (median + median2)/2.0;
            }
        } else {
            return median;
        }
    }
} */
