# 2147. Number of Ways to Divide a Long Corridor — how/why

## Recap

Given a string `s` representing a corridor with seats ('S') and plants ('P'), count the number of ways to divide the corridor such that **each partition contains exactly 2 seats**. Return the count modulo `10^9 + 7`.

A valid division must satisfy:

- Each part has exactly 2 seats.
- If there's an odd number of seats, no valid division exists.
- If there are 0 seats, no valid division exists.

## Intuition

The key insight is recognizing that we can only divide **between pairs of consecutive seats**. Here's the reasoning:

- We need to group seats into pairs. If we have `2n` seats, we need `n` pairs, which means `n-1` dividing points.
- We can only divide between two consecutive "seat pairs." If seats are at indices `i` and `j` (with no other seats between them), we can insert a divider anywhere between `j` and the next seat.

**Example**: Corridor `"S__S__S__S"` has seats at positions 0, 3, 6, 9.

- Seats (0,3) form the first pair; seats (6,9) form the second pair.
- Between the pair (0,3) and pair (6,9), we can place a divider at position 4, 5, or 6.
- So there are 3 dividers possible (the gap size between the end of pair 1 and start of pair 2).

Wait, let me reconsider: we divide **after** the 2nd seat of pair i and **before** the 1st seat of pair i+1. The number of positions available is the gap between them.

Actually, the intuition is:

- Find all seat positions.
- If count is odd or 0, return 0.
- For each consecutive pair of "seat groups" (pairs), count the number of plants (gaps) between the second seat of one pair and the first seat of the next pair.
- Multiply all gap counts.

## Approach

**Greedy seat pairing with gap multiplication**:

1. Track seat count `k` as we iterate through the corridor.
2. When we encounter the 3rd, 5th, 7th... seats (odd-indexed seats beyond the second):
   - These are the "first seat of the next pair."
   - Calculate the number of gaps (plants + 1) between the previous seat and current seat.
   - Multiply result by this gap size.
3. At the end:
   - Check if `k` is even and > 0. If not, return 0.
   - Return the accumulated product modulo `10^9 + 7`.

**Variables**:

- `k`: count of seats encountered.
- `j`: index of the previous seat (marks the end of the current pair).
- `i`: current index in the string.
- `res`: accumulated product of gaps (initially 1).

**Key observation**: Between the 2nd and 3rd seats, we can place a divider at multiple positions. The number of such positions is the distance between them.

## Code (Java)

```java
class Solution {
    public int numberOfWays(String s) {
        long res = 1, j = 0, k = 0, mod = (long)1e9 + 7;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == 'S') {
                if (++k > 2 && k % 2 == 1)
                    res = res * (i - j) % mod;
                j = i;
            }
        }
        return k % 2 == 0 && k > 0 ? (int)res : 0;
    }
}
```

## Correctness

- **Seat counting**: `k` increments for each seat encountered.

- **Gap detection**: When `k > 2` and `k % 2 == 1`, we've just found the first seat of a new pair. The gap size is `i - j`, where `i` is the current seat and `j` is the previous seat (the 2nd seat of the previous pair).

- **Gap calculation**: The number of dividing positions between pair i and pair i+1 is the distance `(seat[2i+1] - seat[2i]) - 1 + 1 = seat[2i+1] - seat[2i]`. Actually, if the previous seat is at position `j` and current seat at position `i`, we can divide at positions `j+1, j+2, ..., i-1`, giving `i - j` positions (including the position right after `j` and right before `i`). Wait, let me reconsider:

  - If seats are at positions `j` and `i` (j < i), we can insert a divider:
    - Right after `j` (position `j+1`): we'd have [... S plant divider plant ... S ...]
    - All the way up to right before `i` (position `i-1`): [... S plant plant divider S ...]
    - Total: `i - j - 1` positions? No wait, the formula is `i - j`.
  
  Let's think concretely: if `j = 3` (seat) and `i = 7` (seat), with "S__S" in between (positions 3,4,5,6,7), we can divide at:
  - After 3: "S|__S" (position 4)
  - After 4: "S_|_S" (position 5)
  - After 5: "S__|S" (position 6)
  
  Total: 3 positions = 7 - 3 - 1 = 3. Hmm, the code uses `i - j`, so let me re-check...
  
  Actually, I think the interpretation is: we can place a divider at any position from `j+1` to `i`, which is `i - j` positions? Let me verify with the example again. Seats at 0 and 3:
  - Positions: 0='S', 1='_', 2='_', 3='S'
  - Divider can go at: 1, 2, or 3? If at 1: "S|__S", at 2: "S_|_S", at 3: "S__|S"?
  
  Wait, if we place divider at position `p`, it means we partition at `p`, so characters 0 to p-1 are in the left partition. So divider positions are 1, 2, 3 (exclusive right endpoint). That's 3 positions. If `i - j = 3 - 0 = 3`, it matches!

- **Even seat check**: `k % 2 == 0 && k > 0` ensures we have a valid even number of seats (and at least 2).

- **Modular arithmetic**: `res * (i - j) % mod` prevents overflow.

## Complexity

- **Time**: O(n) where n is the length of the corridor. Single pass through the string.
- **Space**: O(1) auxiliary space (only a few variables).

## Edge Cases

- No seats: `k = 0` → return 0.
- Single seat: `k = 1` (odd) → return 0.
- Two seats with gap: `k = 2` → return 1 (no intermediate divisions needed).
- Three seats: `k = 3` (odd) → return 0.
- Four seats with multiple gaps: `k = 4` → return product of gap sizes between pairs 1 and 2.
- All plants, no seats: `k = 0` → return 0.
- Consecutive seats (gap size 1): Gap multiplier is 1, doesn't change result.
- Large gap between seat pairs: Larger gap size increases result.

## Takeaways

- **Pairing + gap multiplication**: Recognizing that we pair seats and multiply gap sizes avoids explicit DP.
- **Parity check**: Odd seat counts make divisions impossible; early termination.
- **Greedy pairing**: Processing seats sequentially and reacting at the start of each new pair is efficient.
- **Modular multiplication**: `(a * b) % m = ((a % m) * (b % m)) % m` keeps intermediate results bounded.
- **Implicit divisor positions**: Gap size directly encodes the number of valid divider placements.

## Alternative (Explicit Pair Extraction, O(n))

```java
class Solution {
    public int numberOfWays(String corridor) {
        List<Integer> seats = new ArrayList<>();
        for (int i = 0; i < corridor.length(); i++) {
            if (corridor.charAt(i) == 'S') {
                seats.add(i);
            }
        }
        
        if (seats.size() % 2 != 0 || seats.size() == 0) return 0;
        
        long result = 1;
        long mod = (long)1e9 + 7;
        
        for (int i = 1; i < seats.size(); i += 2) {
            int secondSeatOfPair = seats.get(i);
            int firstSeatOfNextPair = seats.get(i + 1);
            long gaps = firstSeatOfNextPair - secondSeatOfPair;
            result = (result * gaps) % mod;
        }
        
        return (int)result;
    }
}
```

**Trade-off**: Explicit pair extraction is clearer and more readable; it first collects all seat positions, then explicitly pairs them and calculates gap sizes. However, it requires O(k) extra space for the seat list (where k is the number of seats). The optimized solution uses O(1) space and processes in a single pass, though it's less intuitive. Use explicit pairs for clarity in interviews; use single-pass for production.
