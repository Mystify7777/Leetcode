# 1925. Count Square Sum Triples — how/why

## Recap

Given an integer `n`, count the number of triples `(a, b, c)` where `1 ≤ a ≤ b < c ≤ n` and `a² + b² = c²` (Pythagorean triples). Return the count modulo `10^9 + 7` (though this problem doesn't require it given small constraints).

## Intuition

Rather than checking all O(n³) possible triples, we can use the **Euclidean formula for generating Pythagorean triples**:

For any two coprime integers `u > v ≥ 1` where `u` and `v` have opposite parity (one odd, one even), the triple:

- `a = u² - v²`
- `b = 2uv`
- `c = u² + v²`

forms a **primitive Pythagorean triple** (where gcd(a,b,c) = 1).

All Pythagorean triples are multiples of primitive triples. If `(a, b, c)` is primitive, then `(ka, kb, kc)` for any `k ≥ 1` is also a triple.

**Algorithm**:

1. Generate primitive triples using the Euclidean formula (with conditions: `u > v`, `gcd(u,v) = 1`, opposite parity).
2. For each primitive triple `(a, b, c)`, count how many multiples `k·(a, b, c)` fit within the range `[1, n]` (i.e., `k·c ≤ n`).
3. Each primitive triple with `c ≤ n` contributes `⌊n / c⌋` valid multiples.
4. Since the formula generates `(a, b, c)` in non-sorted form, we need to count both `(a, b, c)` and `(b, a, c)` as valid triples (factor of 2).

## Approach

**Euclidean Formula with Multiple Counting**:

1. Iterate through all valid pairs `(u, v)` where `2 ≤ u ≤ √n` and `1 ≤ v < u`.
2. For each pair, check:
   - `(u - v) & 1 == 1`: Ensure opposite parity (one odd, one even). Bitwise AND checks if `u - v` is odd.
   - `gcd(u, v) == 1`: Ensure coprimality (generates primitive triples).
3. Compute `c = u² + v²`. If `c > n`, skip.
4. For each primitive triple, count multiples: `⌊n / c⌋`.
5. Multiply by 2 because the formula generates one ordering; we consider both `(a, b, c)` and `(b, a, c)`.
6. Return total count.

**Why multiply by 2**: The Euclidean formula produces `a = u² - v²` and `b = 2uv`. For `(a, b)` to satisfy `a ≤ b` as required, the formula may need reordering. By counting multiples of both orientations, we ensure all valid triples with the constraint `a ≤ b < c` are captured.

## Code (Java)

```java
class Solution {
    public int countTriples(int n) {
        int res = 0;
        for (int u = 2; u * u <= n; u++) {
            for (int v = 1; v < u; v++) {
                if (((u - v) & 1) == 0 || gcd(u, v) != 1) continue;
                int c = u * u + v * v;
                if (c > n) continue;

                res += 2 * (n / c);
            }
        }
        return res;
    }

    int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
```

## Correctness

- **Euclidean formula completeness**: Every primitive Pythagorean triple is generated exactly once by this formula under the given conditions (opposite parity, coprime).

- **Opposite parity condition**: `(u - v) & 1 == 1` ensures `u` and `v` have opposite parity. This is necessary because:
  - If both are odd or both are even, then `a = u² - v²` and `b = 2uv` share a common factor, making the triple non-primitive.

- **Coprimality**: `gcd(u, v) == 1` ensures the generated triple is primitive.

- **Multiple counting**: For each primitive triple `(a, b, c)`, there are `⌊n / c⌋` multiples `(k·a, k·b, k·c)` with `k·c ≤ n`.

- **Factor of 2**: The Euclidean formula produces values `a = u² - v²` and `b = 2uv`. Since either could be smaller, we count both orderings `(a, b, c)` and `(b, a, c)` to ensure we capture all triples satisfying `a ≤ b < c`.

- **Constraint check**: Loop condition `u * u <= n` ensures we only generate triples with `c = u² + v² ≤ n`.

## Complexity

- **Time**: O(√n · √n · log(√n)) = O(n · log n). Nested loops iterate O(√n × √n) times; each GCD call is O(log n).
  - In practice, the inner loop terminates early when `c > n`, making it faster.
- **Space**: O(1) auxiliary space.

## Edge Cases

- `n = 1, 2, 4`: No Pythagorean triples possible (smallest is 3-4-5). Return 0.
- `n = 5`: Includes triple (3, 4, 5) and (4, 3, 5). Count = 2.
- `n = 15`: Includes (3, 4, 5), (6, 8, 10), (5, 12, 13), and their reflections. Count = 4.
- Large `n`: Algorithm scales well due to √n bound on `u`.
- Multiples of the same primitive triple: Counted correctly via `⌊n / c⌋`.

## Takeaways

- **Euclidean formula**: Generates all primitive Pythagorean triples efficiently; understanding this formula is key to solving this problem in better than brute-force time.
- **Bitwise parity check**: `(u - v) & 1` is a fast way to check if `u - v` is odd, avoiding modulo operations.
- **Multiple scaling**: Primitive triples extend to infinitely many multiples; counting them is efficient via division.
- **Symmetry in ordering**: The problem requires `a ≤ b < c`, but the Euclidean formula may produce either `a < b` or `a > b`, necessitating the factor of 2.
- **Number theory meets combinatorics**: Blending number-theoretic generation with combinatorial counting yields an elegant solution.

## Alternative (Brute Force, O(n³))

```java
class Solution {
    public int countTriples(int n) {
        int count = 0;
        for (int a = 1; a <= n; a++) {
            for (int b = a; b <= n; b++) {
                for (int c = b + 1; c <= n; c++) {
                    if (a * a + b * b == c * c) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
```

**Trade-off**: Brute force checks all O(n³) triples directly, making it straightforward but extremely slow for large `n`. The Euclidean formula approach generates only valid triples, achieving O(n log n) complexity. Use the formula approach in production; brute force is useful for small inputs or verification during development.
