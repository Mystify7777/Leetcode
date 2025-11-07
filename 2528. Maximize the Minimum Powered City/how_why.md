# 2528. Maximize the Minimum Powered City — how and why

## Problem recap

You have `n` cities in a line, each with some power stations. A city at index `i` has a power level equal to the sum of all stations within range `r` (i.e., from cities `[i-r, i+r]` inclusive, clipped to valid indices).

You can add up to `k` additional power stations anywhere. The goal is to **maximize the minimum power level** across all cities.

In other words: place k new stations optimally such that the city with the weakest power becomes as strong as possible.

## Core intuition

This is a **binary search on the answer** problem combined with **greedy verification**:

1. **Binary search:** The answer (minimum power level) is monotonic. If we can achieve minimum power `x`, we can also achieve any value less than `x`. Binary search finds the maximum achievable minimum.

2. **Greedy verification:** For a given target minimum power:
   - Scan cities left to right
   - When a city has power below the target, greedily place new stations **as far right as possible** (at position `i + r`) to cover the current city and benefit future cities
   - Use a difference array to track the cumulative effect of added stations efficiently

3. **Sliding window for initial power:** Calculate each city's initial power using a sliding window over the stations array.

## Approach — binary search with greedy placement

### Step 1: Calculate initial power using sliding window

For each city `i`, its power is the sum of `stations[max(0, i-r) .. min(n-1, i+r)]`.

Use a sliding window:

- Start with window covering `[0, r]`
- For each city `i`, the window covers `[i-r, i+r]`
- Slide by removing `stations[i-r]` and adding `stations[i+r+1]`

### Step 2: Binary search the answer

Search space: `[0, max(power) + k]`

- `low = 0`: minimum possible answer
- `high = max(power) + k`: maximum possible answer (put all k stations benefiting one city)

For each candidate `mid`, check if we can make every city have power ≥ `mid` using ≤ `k` stations.

### Step 3: Greedy verification (canReach)

For a target minimum power:

1. Scan cities left to right
2. Track cumulative added power from previously placed stations using a difference array
3. When city `i` has `power[i] + curAdd < target`:
   - Calculate shortfall: `need = target - (power[i] + curAdd)`
   - Place `need` stations at position `i + r` (rightmost position covering city `i`)
   - These stations affect cities `[i, min(n-1, i + 2*r)]`
   - Update difference array to track when the effect ends
4. If total stations used exceeds `k`, return false

**Why place at `i + r`?** Stations at `i + r` cover both city `i` and future cities up to `i + 2*r`, maximizing reuse.

### Implementation (matches `Solution.java`)

```java
class Solution {
    public long maxPower(int[] stations, int r, long k) {
        int n = stations.length;
        long[] power = new long[n];
        long window = 0;
        
        // Calculate initial power using sliding window
        for (int j = 0; j <= Math.min(n - 1, r); j++) {
            window += stations[j];
        }
        
        for (int i = 0; i < n; i++) {
            power[i] = window;
            int removeIdx = i - r;
            if (removeIdx >= 0) window -= stations[removeIdx];
            int addIdx = i + r + 1;
            if (addIdx < n) window += stations[addIdx];
        }
        
        // Binary search for maximum achievable minimum power
        long low = 0;
        long high = Arrays.stream(power).max().orElse(0L) + k;
        long best = 0;
        
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (canReach(power, r, k, mid)) {
                best = mid;
                low = mid + 1; // Try larger minimum
            } else {
                high = mid - 1;
            }
        }
        return best;
    }
    
    private boolean canReach(long[] power, int r, long k, long target) {
        int n = power.length;
        long used = 0L;
        long[] diff = new long[n + 1]; // Difference array for added stations' effect
        long curAdd = 0L;
        
        for (int i = 0; i < n; i++) {
            curAdd += diff[i]; // Apply scheduled effect changes
            long total = power[i] + curAdd;
            
            if (total < target) {
                long need = target - total;
                used += need;
                if (used > k) return false;
                
                curAdd += need; // Effect starts immediately
                int end = Math.min(n, i + 2 * r + 1); // Effect ends here
                diff[end] -= need;
            }
        }
        return true;
    }
}
```

## Detailed explanation of the greedy verification

**Difference array technique:**

Instead of updating a range `[i, i+2*r]` directly (which would be O(r) per city), we use a difference array:

- `curAdd` tracks the cumulative effect of all stations placed so far that still affect the current city
- `diff[i]` stores scheduled changes to `curAdd` at position `i`
- When we place `need` stations at position `i+r`, they affect cities `[i, i+2*r]`
- We increment `curAdd` immediately (effect starts at city `i`)
- We schedule `diff[i + 2*r + 1] -= need` to remove the effect after city `i + 2*r`

**Why this works:**

Greedy placement at `i + r` is optimal because:

1. It maximizes future coverage (benefits cities up to `i + 2*r`)
2. It's the rightmost position that can help city `i`
3. Scanning left-to-right ensures we fix cities in order, and earlier fixes benefit later cities

## Why the solution works

**Binary search correctness:**

If we can achieve minimum power `x`, we can achieve any smaller value (just use a subset of the stations). This monotonicity makes binary search valid.

**Greedy optimality:**

The greedy strategy of always placing stations as far right as possible is optimal because:

- It maximizes overlap with future cities
- Earlier cities are already fixed and won't benefit from delaying placement
- The left-to-right scan ensures we never miss fixing a city

**Difference array efficiency:**

By using a difference array, we update ranges in O(1) instead of O(r), reducing the verification from O(n·r) to O(n).

## Complexity

- **Time:** O(n log(max_power + k))
  - Calculate initial power: O(n)
  - Binary search: O(log(max_power + k)) iterations
  - Each verification: O(n)
  - Total: O(n log(max_power + k))
- **Space:** O(n) for power and difference arrays

## Example walkthrough

Suppose `stations = [1, 2, 4, 5, 0]`, `r = 1`, `k = 2`.

### Step 1: Calculate initial power

Each city covers `[i-1, i+1]`:

- City 0: `stations[0..1]` = 1 + 2 = 3
- City 1: `stations[0..2]` = 1 + 2 + 4 = 7
- City 2: `stations[1..3]` = 2 + 4 + 5 = 11
- City 3: `stations[2..4]` = 4 + 5 + 0 = 9
- City 4: `stations[3..4]` = 5 + 0 = 5

Initial power: `[3, 7, 11, 9, 5]`

### Step 2: Binary search

- `low = 0`, `high = 11 + 2 = 13`
- Try `mid = 6`:
  - City 0 needs 6 - 3 = 3 more → place 3 at pos 1 (affects cities 0-2)
  - City 1 now has 7 + 3 = 10 ≥ 6 ✓
  - Cities 2, 3, 4 already ≥ 6 ✓
  - Used 3 stations, but k = 2 → **cannot reach 6**
- Try `mid = 4`:
  - City 0 needs 4 - 3 = 1 more → place 1 at pos 1
  - All cities now ≥ 4
  - Used 1 station ≤ 2 ✓ → **can reach 4**
- Try `mid = 5`:
  - City 0 needs 5 - 3 = 2 → place 2 at pos 1
  - Used 2 stations ≤ 2 ✓ → **can reach 5**
- Try `mid = 6`: already know cannot reach

Answer: **5**

## Edge cases to consider

- `k = 0`: No new stations, return minimum of initial power
- `r = 0`: Each city only counts its own station
- `r ≥ n`: All cities share all stations
- All cities already have high power: return `min(power)`
- Single city: place all k stations at that city

## Takeaways

- **Binary search on answer** is powerful when the answer space is monotonic
- **Greedy placement** at the rightmost valid position maximizes future benefit
- **Difference arrays** efficiently handle range updates
- Sliding window optimizes initial power calculation from O(n·r) to O(n)
- Left-to-right scanning with greedy fixes ensures no city is left behind
- The combination of binary search (logarithmic) and linear verification yields excellent time complexity
