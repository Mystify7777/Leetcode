# 2211. Count Collisions on a Road — how/why

## Recap

You have a string `directions` where each character represents a car's direction on a straight road:

- `'L'`: car moving left
- `'R'`: car moving right
- `'S'`: car stationary

Cars move simultaneously. Collisions happen when:

- A right-moving car (`'R'`) catches a car not moving right (i.e., `'S'` or `'L'`).
- A left-moving car (`'L'`) meets a car moving right (`'R'`).

Return the number of collisions.

## Intuition

Key observation: Only cars moving in opposite directions or toward a stationary car collide. Cars moving left cannot catch anything if they start before a right-moving car. So:

1. **Skip leading left-movers**: They won't collide (nothing ahead to hit).
2. **Track right-movers**: Count consecutive `'R'` cars; they will eventually collide if a non-right-mover appears.
3. **Count collisions**:
   - When encountering `'S'`: each `'R'` from the right collides with it → add `carsFromRight`.
   - When encountering `'L'`: each `'R'` from the right collides, plus the `'L'` itself collides → add `carsFromRight + 1`.
   - Reset counter after collision.

## Approach

1. **Skip leading `'L'` cars**: Position pointer at first non-`'L'` character.
2. **Iterate through remaining cars**:
   - If `'R'`: increment `carsFromRight` counter (these cars are waiting to collide).
   - If `'S'`: all `carsFromRight` collide with this stationary car → add `carsFromRight` to result, reset counter.
   - If `'L'`: all `carsFromRight` collide with this car, and this `'L'` also collides → add `carsFromRight + 1` to result, reset counter.
3. Return total collision count.

**Example**: `"RRLS"` → Skip no leading `'L'`. R: counter=1. R: counter=2. L: collision = 2+1=3. S: collision += 0 (no R after L). Total = 3.

## Code (Java)

```java
class Solution {
    public int countCollisions(String dir) {
        int res = 0, n = dir.length(), i = 0, carsFromRight = 0;
        
        // Skip leading 'L' cars (they never collide)
        while (i < n && dir.charAt(i) == 'L') i++;
        
        // Process remaining cars
        for ( ; i < n; i++) {
            if (dir.charAt(i) == 'R') {
                carsFromRight++;
            } else {
                // 'S' or 'L' causes collision
                res += (dir.charAt(i) == 'S') ? carsFromRight : carsFromRight + 1;
                carsFromRight = 0;
            }
        }
        
        return res;
    }
}
```

## Correctness

- **No collisions for leading `'L'`**: A left-moving car at the start has nothing to its left, so it never collides.

- **`'R'` counter**: Right-moving cars can only collide with something ahead. We count them until we find a non-right-mover.

- **`'S'` collision**: Each `'R'` behind a stationary car collides with it (the `'S'` itself doesn't move, so it doesn't collide with other cars, just those hitting it).

- **`'L'` collision**: Each `'R'` behind a left-moving car collides (opposite directions). Additionally, the `'L'` car itself collides by moving into the congestion of `'R'` cars.

- **Reset after collision**: Once cars collide (forming a "traffic jam"), they stop moving. New `'R'` cars after this start a fresh count.

- **Trailing `'R'`**: After the loop, any remaining `carsFromRight` don't collide (no obstacle ahead), so they don't contribute to the result.

## Complexity

- **Time**: `O(n)` — single pass through the string.
- **Space**: `O(1)` — constant extra variables.

## Edge Cases

- All `'L'`: no collisions, return 0.
- All `'R'`: no collisions (all move right, no obstacles), return 0.
- All `'S'`: no collisions (all stationary), return 0.
- Single car: no collisions, return 0.
- `"RLS"`: R → counter=1. L → collision = 1+1=2. S → collision += 0. Total = 2.
- `"RRSS"`: R → counter=2. R → counter=3. S → collision += 3. S → collision += 0. Total = 3.
- Leading and trailing `'L'`: leading skipped, trailing don't collide.

## Takeaways

- **Directional movement simplification**: Identify "active" directions (those that can cause collisions) vs. "passive" ones (those that just absorb impact).
- **Counter-based tracking**: Use counters to accumulate obstacles of one type and resolve them when encountering the opposite type.
- **Early termination**: Skip leading obstacles that provably don't participate (leading `'L'` cars).
- **Greedy collision modeling**: Process collisions as soon as they're detectable without simulating motion.
- This pattern applies to other directional collision problems (e.g., asteroids, vehicles, particles).

## Alternative (Trim Edges)

```java
class Solution {
    public int countCollisions(String dir) {
        int count = 0;
        int left = 0, right = dir.length() - 1;
        char[] s = dir.toCharArray();
        
        // Skip leading 'L' cars
        while (left < s.length && s[left] == 'L') {
            left++;
        }
        
        // Skip trailing 'R' cars
        while (right >= 0 && s[right] == 'R') {
            right--;
        }
        
        // All non-'S' cars between left and right collide
        for (int i = left; i <= right; i++) {
            if (s[i] != 'S') count++;
        }
        
        return count;
    }
}
```

**Trade-off**: This approach is simpler conceptually (all cars in the middle section except stationary ones collide) but less intuitive about *how* collisions happen. Time complexity is still `O(n)`, but it uses character array conversion. The counter-based approach is more mechanical and easier to extend if collision rules change.
