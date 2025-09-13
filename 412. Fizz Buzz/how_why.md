
# How & Why: LeetCode 412 - Fizz Buzz

This solution uses a single loop and a series of chained ternary operators to efficiently generate the FizzBuzz sequence.

---

## Problem Restatement

You are given an integer $n$. Your task is to generate a list of strings representing the numbers from 1 to $n$. However, there are special rules:

- For multiples of three, output "Fizz" instead of the number.
- For multiples of five, output "Buzz" instead of the number.
- For numbers which are multiples of both three and five, output "FizzBuzz".

### Example

**Input:**
```
n = 5
```
**Output:**
```
["1", "2", "Fizz", "4", "Buzz"]
```

---

## How to Solve

This is a classic programming problem that tests basic loop and conditional logic. The most direct approach is to:

1. **Initialize a list to store the results.**
2. **Loop from 1 to $n$:** For each number $i$ in the range:
    - Check for "FizzBuzz": First, check if $i$ is divisible by both 3 and 5. A simple way to do this is to check for divisibility by their least common multiple, which is 15 ($i \% 15 == 0$).
    - Check for "Buzz": If not, then check if $i$ is divisible by 5 ($i \% 5 == 0$).
    - Check for "Fizz": If not, then check if $i$ is divisible by 3 ($i \% 3 == 0$).
    - Default to the Number: If none of the above conditions are met, simply use the number itself (converted to a string).
3. **Add the result for the current number to the list.**
4. **Return the list after the loop is complete.**

### Implementation

```java
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> ans = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            // A concise way to write the if-else logic using ternary operators
            ans.add(
                i % 15 == 0 ? "FizzBuzz" :
                i % 5 == 0  ? "Buzz" :
                i % 3 == 0  ? "Fizz" :
                String.valueOf(i)
            );
        }
        return ans;
    }
}
```

---

## Why This Works

- **Order of Operations is Key:** The solution works because it checks the most specific condition first. By checking for divisibility by 15 (3 * 5) before checking for 5 or 3 individually, it correctly handles numbers that are multiples of both. If it checked for $i \% 3 == 0$ first, a number like 15 would incorrectly result in "Fizz" instead of "FizzBuzz".
- **Modulus Operator:** The modulus operator ($\%$) is the perfect tool for this job. It returns the remainder of a division, so $i \% x == 0$ is a clean and efficient way to check if $i$ is perfectly divisible by $x$.
- **Conciseness:** The use of chained ternary operators (`condition ? val_if_true : val_if_false`) provides a compact way to express the series of if-else if-else checks.

---

## Complexity Analysis

- **Time Complexity:** $O(n)$. We loop from 1 to $n$ once, and each operation inside the loop is a constant-time check.
- **Space Complexity:** $O(n)$. We need to store $n$ elements in the result list.

---

## Example Walkthrough

**Input:**
```
n = 15
```

**Process:**

- $i = 1$: No conditions met. Add "1".
- $i = 2$: No conditions met. Add "2".
- $i = 3$: $3 \% 3 == 0$. Add "Fizz".
- $i = 4$: No conditions met. Add "4".
- $i = 5$: $5 \% 5 == 0$. Add "Buzz".
- ...
- $i = 10$: $10 \% 5 == 0$. Add "Buzz".
- ...
- $i = 15$: $15 \% 15 == 0$. Add "FizzBuzz".

**Output:**
```
["1", "2", "Fizz", "4", "Buzz", ..., "14", "FizzBuzz"]
```

---

## Alternate Approaches

While the provided solution is the standard, there are other ways to structure the logic:

### 1. If-Else If-Else Chain
   - This is the most common alternative to the ternary operator and is often considered more readable by beginners. The logic remains identical.

### 2. String Concatenation
   - Initialize an empty string. If $i \% 3 == 0$, append "Fizz". If $i \% 5 == 0$, append "Buzz". If the string is still empty after these checks, then add the number. This approach avoids the $i \% 15$ check but involves more string operations.

---

## Optimal Choice

The provided single-loop with ordered conditional checks (whether using ternaries or if-else statements) is the optimal and universally accepted solution. It is efficient, clear, and directly solves the problem.

---

## Key Insight

The fundamental insight for solving FizzBuzz correctly is to handle the compound condition (FizzBuzz) before the individual conditions (Fizz or Buzz). This principle of checking the most specific case first is a common pattern in programming.