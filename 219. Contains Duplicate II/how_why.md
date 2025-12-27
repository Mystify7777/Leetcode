# 219. Contains Duplicate II - How & Why

## Problem Overview

Given an integer array `nums` and an integer `k`, return `true` if there are two distinct indices `i` and `j` such that:

- `nums[i] == nums[j]`
- `abs(i - j) <= k`

In other words, find if there are duplicate values within a sliding window of size `k`.

**Example:**

- `nums = [1,2,3,1], k = 3` → true (indices 0 and 3, difference is 3)
- `nums = [1,0,1,1], k = 1` → true (indices 2 and 3, difference is 1)
- `nums = [1,2,3,1,2,3], k = 2` → false (duplicates exist but not within k distance)

## Algorithm Explanation

### Key Insight

Use a **sliding window with a HashSet** to maintain elements within the current window of size `k`. The HashSet allows O(1) lookup to detect duplicates instantly.

### Step-by-Step Logic

1. **Initialize a HashSet** to store elements in the current window
2. **Iterate through the array:**
   - If window size exceeds `k`, remove the leftmost element (`nums[i-k-1]`)
   - Try to add current element to the set
   - If `add()` returns `false`, a duplicate exists within window → return `true`
3. **Return false** if no duplicates found within any window of size `k`

### Why This Works

- The HashSet maintains a sliding window of at most `k+1` elements
- When `i > k`, we remove `nums[i-k-1]` to ensure window size stays ≤ `k+1`
- `set.add()` returns `false` if element already exists, indicating a duplicate within distance `k`

### Time & Space Complexity

- **Time:** O(n) where n = length of array (single pass with O(1) operations)
- **Space:** O(min(n, k)) - HashSet stores at most k+1 elements

## Code Walkthrough

### Compact Version (from Solution.java)

```java
Set<Integer> set = new HashSet<Integer>();
for(int i = 0; i < nums.length; i++){
    if(i > k) set.remove(nums[i-k-1]);  // Maintain window size
    if(!set.add(nums[i])) return true;   // Duplicate found
}
return false;
```

### More Readable Version

```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Remove element that's now outside the window
            if (i > k) {
                window.remove(nums[i - k - 1]);
            }
            
            // Try to add current element
            // If it already exists (add returns false), we found a duplicate
            if (!window.add(nums[i])) {
                return true;
            }
        }
        
        return false;
    }
}
```

### Key Differences in Readable Version

1. **Named variable:** `window` instead of `set` - clearer intent
2. **Comments:** Explains each step
3. **Spacing:** Better visual separation of logic
4. **Same efficiency:** No performance difference, just readability

## Why the Compact Version is Clever

The compact version is elegant because:

1. **Single-line operations:** Each step is concise yet clear
2. **Immediate return:** Uses short-circuit evaluation with `!set.add()`
3. **Minimal variables:** Only uses necessary data structures
4. **Clean logic flow:** Window maintenance → duplicate check → continue

However, for production code, the **readable version is preferred** because:

- Easier for team members to understand
- Better for maintenance and debugging
- Self-documenting with meaningful names and comments

## Example Walkthrough

**Input:** `nums = [1,2,3,1], k = 3`

| i | nums[i] | i > k? | Remove? | Set Before Add | add() Result | Set After | Return? |
| --- | --------- | -------- | --------- | ---------------- | -------------- | ----------- | --------- |
| 0 | 1 | No | - | {} | true | {1} | No |
| 1 | 2 | No | - | {1} | true | {1,2} | No |
| 2 | 3 | No | - | {1,2} | true | {1,2,3} | No |
| 3 | 1 | No | - | {1,2,3} | **false** | {1,2,3} | **Yes ✅** |

**Result:** Duplicate `1` found at indices 0 and 3 (distance = 3 ≤ k) → Return true

**Input:** `nums = [1,2,3,4,5,1], k = 3`

| i | nums[i] | i > k? | Remove? | Set Before | add() | Set After | Return? |
| --- | --------- | -------- | --------- | ------------ | ------- | ----------- | --------- |
| 0 | 1 | No | - | {} | true | {1} | No |
| 1 | 2 | No | - | {1} | true | {1,2} | No |
| 2 | 3 | No | - | {1,2} | true | {1,2,3} | No |
| 3 | 4 | No | - | {1,2,3} | true | {1,2,3,4} | No |
| 4 | 5 | Yes | nums[0]=1 | {2,3,4} | true | {2,3,4,5} | No |
| 5 | 1 | Yes | nums[1]=2 | {3,4,5} | true | {3,4,5,1} | No |

**Result:** No duplicates within any window of size k → Return false

## Window Maintenance Explained

### Why `i > k` and `nums[i-k-1]`?

When `i = 4` and `k = 3`:

- Current element: `nums[4]`
- Window should contain: `nums[1], nums[2], nums[3], nums[4]` (4 elements, indices differ by ≤ 3)
- Need to remove: `nums[0]` which is `nums[i-k-1] = nums[4-3-1] = nums[0]`

**Visualization:**

```c
k = 3
i = 4: [X | 1 | 2 | 3 | 4] → Remove index 0
       ↑   ↑           ↑
     Remove |   Window   |
```

### Window Size

- The window maintains at most `k+1` elements
- This ensures any duplicate is at most `k` indices apart
- When we find a duplicate, the distance is guaranteed ≤ `k`

## Edge Cases

1. **k = 0** - No duplicates possible (each element alone in window)
   - Input: `[1,1], k=0` → false
2. **Empty array** - No duplicates → false
3. **Single element** - No duplicates → false
4. **k ≥ array length** - Window covers entire array, finds any duplicate
   - Input: `[1,2,1], k=10` → true
5. **All unique** - No duplicates → false
   - Input: `[1,2,3,4], k=2` → false
6. **Immediate duplicates** - Distance 0
   - Input: `[1,1], k=1` → true (indices 0,1, distance = 1)

## Alternative Approaches

### 1. HashMap with Indices (More intuitive but slightly slower)

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
            return true;
        }
        map.put(nums[i], i);
    }
    return false;
}
```

- **Pros:** More explicit about tracking indices
- **Cons:** Uses more memory (stores index, not just presence)

### 2. Brute Force (Not recommended)

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length && j <= i + k; j++) {
            if (nums[i] == nums[j]) return true;
        }
    }
    return false;
}
```

- **Time:** O(n × k) - Too slow for large inputs
- **Space:** O(1)

## Key Takeaways

1. **Sliding window + HashSet** is optimal for this problem
2. **Compact code can be elegant** but prioritize readability in production
3. **Window maintenance** is key: remove elements outside the k-range
4. **HashSet.add()** returns `false` if element exists - perfect for duplicate detection
5. **Space-time tradeoff:** O(k) space for O(n) time is worth it
6. The compact version demonstrates **clever use of boolean return values**

## Why Compact vs Readable?

**Use compact version when:**

- Writing competitive programming solutions
- Code golf or optimization challenges
- You understand the pattern deeply

**Use readable version when:**

- Working in a team
- Code will be maintained by others
- Clarity is more important than brevity
- During interviews (shows communication skills)

Both are correct and efficient - choose based on context! 🎯
