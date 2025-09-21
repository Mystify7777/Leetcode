# how\_why.md

## Problem

We need to design a **Movie Rental System** that supports the following:

1. **Initialization**

   * Given `n` shops and a list of entries `[shop, movie, price]` representing available movies at each shop.

2. **Operations**

   * `search(movie)`
     → Return up to **5 shops** offering the movie, ordered by `(price ↑, shop ↑)`.
   * `rent(shop, movie)`
     → Move this movie from **available** → **rented**.
   * `drop(shop, movie)`
     → Move this movie from **rented** → **available**.
   * `report()`
     → Return up to **5 rented movies**, ordered by `(price ↑, shop ↑, movie ↑)`.

Constraints:

* Multiple shops may have the same movie at different prices.
* We must efficiently maintain orderings and fast lookups.

---

## How (Approach)

The core challenge is:

* Efficiently tracking **available copies per movie**.
* Efficiently tracking **all rented copies**.
* Supporting both **sorted retrievals** (top-5) and **fast updates** (rent/drop).

### Data Structures Used

1. **`TreeSet<Node>`**

   * Keeps copies ordered by `(price, shop, movie)`.
   * Used for:

     * `availableByMovie[movie]` → available shops for that movie.
     * `rentedSet` → all currently rented movies.

2. **`Map<Long, Node>`**

   * Quick lookup for a movie copy given `(shop, movie)`.
   * Key generated as:

     ```java
     key = (long) shop << 32 ^ movie
     ```

3. **`Node` class**

   * Holds `{shop, movie, price}`.
   * Comparison strictly orders by `price → shop → movie`.

---

### Operations

1. **`search(movie)`**

   * Look up the `TreeSet` of available copies for this movie.
   * Iterate through the first 5 elements (cheapest & lexicographically smallest shops).
   * `O(log n)` updates, `O(5)` retrieval.

2. **`rent(shop, movie)`**

   * Remove this node from `availableByMovie[movie]`.
   * Insert into `rentedSet`.
   * Both operations are `O(log n)`.

3. **`drop(shop, movie)`**

   * Remove node from `rentedSet`.
   * Insert back into `availableByMovie[movie]`.
   * Again, `O(log n)`.

4. **`report()`**

   * Iterate over first 5 elements of `rentedSet`.
   * Returns `[shop, movie]`.
   * `O(5)` retrieval.

---

## Why (Reasoning)

* We need **sorted retrievals** → hence `TreeSet` is ideal.
* We need **fast updates** (insert/remove) → balanced BST behind `TreeSet` ensures `O(log n)`.
* We need **fast lookup** by `(shop, movie)` → extra `HashMap` (`byPair`) prevents scanning.
* Ordering criteria differ slightly:

  * Available copies: `(price, shop)`
  * Rented copies: `(price, shop, movie)`
    → The same comparator (`CMP`) works since it already includes `movie`.

This design perfectly balances **speed** (logarithmic) with **ordering** (TreeSet auto-sorts).

---

## Complexity Analysis

| Operation | Time Complexity    | Space Complexity |
| --------- | ------------------ | ---------------- |
| `search`  | O(5) + O(1) lookup | O(n)             |
| `rent`    | O(log n)           | O(n)             |
| `drop`    | O(log n)           | O(n)             |
| `report`  | O(5)               | O(n)             |

Efficient enough for problem constraints (multiple operations, up to `10^5`).

---

## Example

```java
MovieRentingSystem mrs = new MovieRentingSystem(3,
    new int[][]{
        {0, 1, 5}, {0, 2, 6}, {0, 3, 7},
        {1, 1, 4}, {2, 1, 7}
    });

// search movie 1
System.out.println(mrs.search(1)); // [1, 0, 2]

// rent shop=1, movie=1
mrs.rent(1, 1);

// report rented
System.out.println(mrs.report()); // [[1, 1]]

// drop it
mrs.drop(1, 1);

// report rented again
System.out.println(mrs.report()); // []
```

---

## Best Method

The current `TreeSet + HashMap` approach is already the **optimal solution**:

* **Supports ordering naturally.**
* **Efficient rent/drop transitions.**
* **Avoids scanning with direct lookup.**

---
