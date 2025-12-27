# 2402. Meeting Rooms III - How & Why

## Problem Overview

Given `n` meeting rooms and a list of meetings with start and end times, determine which room is used the most. If a meeting cannot fit into any free room, it must be scheduled for the earliest available room (which may mean the meeting extends beyond its intended end time).

## Algorithm Explanation

### Key Insight

- Sort meetings by start time
- For each meeting, try to find a free room (where `busy[j] <= start`)
- If no free room exists, find the room that becomes free earliest and schedule it there (extending the duration)
- Track how many meetings each room hosts

### Step-by-Step Logic

1. **Sort meetings by start time** - Process meetings chronologically to avoid conflicts

2. **For each meeting:**
   - Try to find a room that's free at the start time
   - Keep track of which room becomes free earliest (in case all rooms are busy)
   - If a free room is found, assign the meeting and update when it becomes free
   - If no free room exists, assign to the earliest available room and extend it

3. **Find the room with most bookings** - Return the room index with the highest count

### Time & Space Complexity

- **Time:** O(m × n) where m = number of meetings, n = number of rooms (due to nested loops)
- **Space:** O(n) for the count and busy arrays

## Why is the 2nd Implementation Faster?

**Both implementations are fundamentally the same algorithm**, but the 2nd one is faster due to **minor micro-optimizations**:

### Key Differences

1. **Variable Declaration Order**
   - 1st: Declares `max` and `res` before the loop, then updates them
   - 2nd: Declares them after processing meetings
   - **Impact:** Very minimal (compiler optimization likely handles this)

2. **Loop Iteration Style**
   - 1st: Traditional `for (int i = 0; i < meetings.length; i++)`
   - 2nd: Enhanced `for (int[] meeting : meetings)`
   - **Impact:** The enhanced for-loop creates an implicit iterator, but for arrays, it's typically optimized to be equally fast. However, the 2nd version is cleaner and compiler may optimize it better

3. **Variable Naming & Clarity**
   - 2nd: Uses `int start = meeting[0], end = meeting[1]` which is more explicit
   - **Impact:** Slightly better readability, which can help compiler optimizations

4. **Logic Flow**
   - Both have identical logic
   - **Impact:** The difference is negligible at the algorithm level

### Real Performance Difference

The actual speedup is likely due to:

- **JVM JIT Compilation**: The 2nd version's cleaner code structure may be easier for the JVM's Just-In-Time compiler to optimize
- **Branch Prediction**: Modern CPUs predict branches better with cleaner code patterns
- **Cache Locality**: The 2nd version might have better CPU cache utilization due to simpler code flow

### Conclusion

The 2nd implementation isn't algorithmically faster—it's **practically faster** due to:

- Cleaner code = better compiler optimizations
- Enhanced for-loop over traditional array access
- Better variable initialization strategy

Both have the same Big O complexity, but the 2nd version provides better **constant factors** in real-world execution.

## Code Walkthrough

```java
// Sort meetings by start time
Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

// Process each meeting
for (int[] meeting : meetings) {
    int start = meeting[0], end = meeting[1];
    long earliest = Long.MAX_VALUE;  // Earliest free time found so far
    int roomIndex = -1;              // Room with earliest free time
    boolean assigned = false;        // Whether meeting was assigned to free room
    
    // Find best room
    for (int i = 0; i < n; i++) {
        // Track room with earliest free time
        if (busy[i] < earliest) {
            earliest = busy[i];
            roomIndex = i;
        }
        // If room is free, assign here
        if (busy[i] <= start) {
            busy[i] = end;
            count[i]++;
            assigned = true;
            break;  // Early exit optimization
        }
    }
    
    // If no free room, use the earliest one (extend duration)
    if (!assigned) {
        busy[roomIndex] += (end - start);
        count[roomIndex]++;
    }
}

// Find room with max bookings
int max = 0, res = 0;
for (int i = 0; i < n; i++) {
    if (count[i] > max) {
        max = count[i];
        res = i;
    }
}
return res;
```

## Example Walkthrough

**Input:** `n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]`

| Meeting | Start | End | Busy State | Assignment                   |
|---------|-------|-----|----------- |------------------------------|
| [0,10]  | 0     | 10  | [10, ∞]    | Room 0 at time 10            |
| [1,5]   | 1     | 5   | [10, 5]    | Room 1 at time 5             |
| [2,7]   | 2     | 7   | [10, 7]    | Room 1 at time 7             |
| [3,4]   | 3     | 4   | [10, 11]   | Room 1 at time 11 (extended) |

**Result:** Room 1 has 3 meetings, Room 0 has 1 → Return 1
