# How & Why: LeetCode 2197 - Replace Non-Coprime Numbers in Array

---

## Problem Restatement
We are given an integer array `nums`. We need to repeatedly replace adjacent numbers that are **not coprime** (i.e., `gcd(a, b) > 1`) with their **least common multiple (LCM)**. The process continues until no adjacent non-coprime numbers remain. Finally, return the resulting array.

---

## How to Solve

### Step 1: Use a Stack to Simulate Merging
We process the array from left to right and maintain a **stack**:
- Push the current number onto the stack.
- While the top of the stack and the current number are non-coprime (`gcd > 1`), merge them into their LCM.
- Replace the top of the stack with this merged number.

This ensures that the array always remains valid as we process.

### Step 2: LCM Calculation
We calculate LCM using:
```
LCM(a, b) = (a / gcd(a, b)) * b
```
This avoids integer overflow compared to `(a * b) / gcd(a, b)`.

### Step 3: Continue Until Stable
Keep merging while the new number and the top of the stack remain non-coprime. Once they are coprime, push the number onto the stack and move on.

---

## Why This Works
- The stack ensures we only merge adjacent numbers.
- Each merge reduces the pair into one number (LCM), preserving divisibility.
- By iteratively merging, we guarantee no non-coprime pairs are left at the end.

This approach mimics the real merging process without repeatedly scanning the array, making it efficient.

---

## Complexity Analysis
- **Time Complexity**: O(n log M), where n = length of `nums` and M = max value of numbers.
  - Each element is pushed/popped at most once.
  - gcd computation is log-based.
- **Space Complexity**: O(n) for the stack.

---

## Example Walkthrough
Input:
```
nums = [6, 4, 3, 2, 7, 6, 2]
```

Process:
- Start with 6 → stack = [6]
- Next = 4, gcd(6,4)=2 → merge → lcm=12 → stack=[12]
- Next = 3, gcd(12,3)=3 → merge → lcm=12 → stack=[12]
- Next = 2, gcd(12,2)=2 → merge → lcm=12 → stack=[12]
- Next = 7, gcd(12,7)=1 → push → stack=[12,7]
- Next = 6, gcd(7,6)=1 → push → stack=[12,7,6]
- Next = 2, gcd(6,2)=2 → merge → lcm=6 → stack=[12,7,6]

Final Output:
```
[12, 7, 6]
```

---

## Alternate Approaches
1. **Repeated Array Scanning**:
   Continuously scan the array and merge adjacent non-coprimes until stable. Very inefficient (O(n²) or worse).

2. **Linked List Simulation**:
   Instead of a stack, simulate merges using linked list traversal. More complicated with no efficiency gain.

### Optimal Choice
The **stack-based approach** is optimal:
- Ensures O(n log M) performance.
- Easy to implement.
- Directly models the merging process.

---

## Key Insight
The key observation is that merging must be done **immediately when a non-coprime pair appears**, because delaying it does not change the outcome. Using a stack guarantees correctness and efficiency.

