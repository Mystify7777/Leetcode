# How_Why.md – Course Schedule III (LeetCode 630)

## ❌ Brute Force Idea

We are asked to maximize the number of courses we can take given `(duration, deadline)` pairs.

**Naïve approach**:

* Try all possible subsets of courses and check which ones fit the deadlines.
* Or recursively decide "take / skip" each course.

⚠️ This is exponential (`O(2^n)`) and completely infeasible since `n` can be up to `10^4`.

---

## ✅ Greedy + Priority Queue Approach (Optimal)

The key insight:

1. **Sort by deadline (`end`)** – because if a course has an earlier deadline, it must be decided sooner.
2. Maintain a running total of time spent.
3. Use a **max-heap (priority queue)** to store durations of the courses we’ve taken.

   * If adding a new course exceeds its deadline, drop the *longest duration* course taken so far (whether it’s the new one or an older one).
   * This ensures we keep the maximum number of courses while always respecting deadlines.

---

### Example Walkthrough

Input:

```
courses = [[100,200],[200,1300],[1000,1250],[2000,3200]]
```

1. Sort by deadline:
   → `[[100,200],[1000,1250],[200,1300],[2000,3200]]`

2. Process courses:

   * Take `[100,200]` → total=100, pq=[100] ✅
   * Try `[1000,1250]` → total=1100, pq=[1000,100] ✅ (still ≤1250)
   * Try `[200,1300]` → total=1300, pq=[1000,100,200] ✅ (exactly 1300)
   * Try `[2000,3200]` → total=3300 > 3200 ❌, so drop longest (1000).
     → new total=2300, pq=[2000,200,100]

Final pq size = 3 (courses taken).

---

### Code (Heap)

```java
class Solution {
    public int scheduleCourse(int[][] courses) {
        // Sort courses by deadline
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);

        // Max-heap for durations
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

        int total = 0;
        for (int[] course : courses) {
            int dur = course[0], end = course[1];

            if (total + dur <= end) {
                // Can take this course
                total += dur;
                pq.add(dur);
            } else if (!pq.isEmpty() && pq.peek() > dur) {
                // Replace longest course with this shorter one
                total += dur - pq.poll();
                pq.add(dur);
            }
        }

        return pq.size(); // maximum courses we can take
    }
}
```

---

## 📊 Complexity

* **Sorting:** `O(n log n)`
* **Heap operations:** each course goes into heap once → `O(n log n)`
* **Total:** `O(n log n)`
* **Space:** `O(n)` for heap

---

## ✅ Key Takeaways

* Always sort by deadline when scheduling tasks/courses.
* If schedule overflows, drop the longest-duration task → ensures optimal packing.
* This is a greedy + heap strategy, widely used in **interval scheduling optimization** problems.

---
