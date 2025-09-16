# How & Why — LeetCode 374: Guess Number Higher or Lower

You play a guessing game where the system picks a number between `1` and `n`. You can call the provided API:

```java
int guess(int num);
```

The API returns:
- `-1` : your guess is higher than the picked number
- `1`  : your guess is lower than the picked number
- `0`  : your guess is correct

Implement a function to find the picked number.

---

## Idea

The search space is ordered (1..n), so use binary search. Each call to `guess(mid)` tells you which half contains the target.

---

## Algorithm (binary search)

1. Initialize `low = 1`, `high = n`.
2. While `low <= high`:
   - `mid = low + (high - low) / 2` (avoids overflow).
   - `res = guess(mid)`.
   - If `res == 0`, return `mid`.
   - If `res < 0`, set `high = mid - 1`.
   - If `res > 0`, set `low = mid + 1`.
3. Return `-1` (theoretically unreachable if the API is correct).

---

## Java template

```java
/* The guess API is defined in the parent class.
   int guess(int num);
*/

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int low = 1, high = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = guess(mid);
            if (res == 0) return mid;
            else if (res < 0) high = mid - 1;  // mid too high
            else low = mid + 1;               // mid too low
        }
        return -1;
    }
}
```

---

## Why it works

- Each `guess` call halves the search space, so binary search guarantees finding the number in O(log n) steps.
- Using `low + (high - low) / 2` prevents integer overflow when `low` and `high` are large.

---

## Complexity

- Time: O(log n) — binary search iterations.
- Space: O(1) — constant extra space.

---

## Example walkthrough

Input: `n = 10`, picked number = `6`

1. low=1, high=10 → mid=5 → guess(5)=1 (too low) → low=6
2. low=6, high=10 → mid=8 → guess(8)=-1 (too high) → high=7
3. low=6, high=7 → mid=6 → guess(6)=0 → return 6

---

## Alternate approaches

- Linear scan: try every number from 1..n (O(n) time) — not efficient.
- Random guesses: expected worse performance and non-deterministic.

✅ Binary search is optimal for this problem.
