
# How_Why.md

## Problem

You are designing a system to **track food ratings**.

Each food has:

* A **name** (`String`)
* A **cuisine** (`String`)
* A **rating** (`int`)

The system should support:

1. **`changeRating(food, newRating)`** – update the rating of a given food.
2. **`highestRated(cuisine)`** – return the name of the highest-rated food for that cuisine.

   * If multiple foods have the same highest rating, return the **lexicographically smallest** food name.

Constraints:

* Up to **2 × 10⁵ operations** → solution must be efficient.
* Ratings are updated frequently, so we need **fast updates and queries**.

---

## How (Step-by-step Solution)

### Approach 1: Brute Force (Inefficient)

* Store all foods in a `HashMap<food, rating>`.
* For `highestRated(cuisine)` → scan all foods in that cuisine to find the maximum.
* **Time Complexity**:

  * `changeRating`: O(1)
  * `highestRated`: O(n) in worst case (too slow for large inputs).

❌ Not feasible for constraints.

---

### Approach 2: Priority Queue (Heap) + Lazy Deletion

* Maintain a **max-heap per cuisine** storing `(rating, foodName)`.
* For `changeRating`, push the new `(rating, food)` onto the heap.
* For `highestRated`, pop invalid entries until the top matches the latest rating.
* **Time Complexity**:

  * `changeRating`: O(log n)
  * `highestRated`: O(log n) (sometimes more due to lazy pops).
* **Space Complexity**: O(n).

⚠️ Efficient but heaps can grow large since old entries remain until queried.

---

### Approach 3: Balanced BST (`TreeSet` in Java) ✅

* Maintain a **TreeSet per cuisine** sorted by:

  1. Rating (descending),
  2. Food name (ascending).
* Also maintain two HashMaps:

  * `foodToCuisine` → find cuisine quickly.
  * `foodToRating` → track current rating.
* For `changeRating`:

  * Remove old entry from that cuisine’s TreeSet.
  * Insert updated entry.
  * Update HashMaps.
* For `highestRated`:

  * Just return the **first element** of the TreeSet.

**Time Complexity**:

* `changeRating`: O(log n) (remove + insert into TreeSet).
* `highestRated`: O(1) (TreeSet’s first element).
* **Space Complexity**: O(n).

✅ This is clean, efficient, and avoids lazy deletion.
📌 This is the method we implemented.

---

## Why (Reasoning)

* The problem requires **efficient updates** (rating changes) and **efficient max queries** (highest-rated food).
* Scanning (brute force) is too slow.
* Heaps are efficient but require lazy deletion, which complicates logic.
* A **balanced BST (TreeSet)**:

  * Maintains sorted order dynamically.
  * Supports **fast insert, delete, max lookup**.
  * Perfect fit for this problem.

That’s why the **TreeSet + HashMap hybrid approach** is the most balanced and reliable.

---

## Complexity Analysis

* **Time Complexity**:

  * `changeRating`: O(log n)
  * `highestRated`: O(1)
* **Space Complexity**: O(n) (store foods in TreeSets + HashMaps).

This works efficiently even for the largest constraints.

---

## Example Walkthrough

### Input

```java
foods    = ["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"]
cuisines = ["korean", "japanese", "japanese", "greek", "japanese", "korean"]
ratings  = [9, 12, 8, 15, 14, 7]
```

### Step 1: Initialization

* Korean: {kimchi: 9, bulgogi: 7} → highest = kimchi
* Japanese: {miso: 12, sushi: 8, ramen: 14} → highest = ramen
* Greek: {moussaka: 15} → highest = moussaka

### Step 2: Queries

* `highestRated("korean")` → `"kimchi"`
* `highestRated("japanese")` → `"ramen"`
* `changeRating("sushi", 16)` → update TreeSet.
* `highestRated("japanese")` → `"sushi"`

---

## Best Method

👉 **Use `TreeSet` per cuisine + HashMaps (`foodToCuisine`, `foodToRating`)**.
It provides a clean, balanced solution with optimal efficiency.

### Implementation (Best Approach)

```java
import java.util.*;

class FoodRatings {
    static class Food {
        String name;
        int rating;
        Food(String name, int rating) {
            this.name = name;
            this.rating = rating;
        }
    }

    private Map<String, String> foodToCuisine;
    private Map<String, Integer> foodToRating;
    private Map<String, TreeSet<Food>> cuisineFoods;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodToCuisine = new HashMap<>();
        foodToRating = new HashMap<>();
        cuisineFoods = new HashMap<>();

        for (int i = 0; i < foods.length; i++) {
            String food = foods[i];
            String cuisine = cuisines[i];
            int rating = ratings[i];

            foodToCuisine.put(food, cuisine);
            foodToRating.put(food, rating);

            cuisineFoods
                .computeIfAbsent(cuisine, k -> new TreeSet<>(
                    (a, b) -> a.rating == b.rating
                        ? a.name.compareTo(b.name)
                        : b.rating - a.rating))
                .add(new Food(food, rating));
        }
    }

    public void changeRating(String food, int newRating) {
        String cuisine = foodToCuisine.get(food);
        int oldRating = foodToRating.get(food);

        cuisineFoods.get(cuisine).remove(new Food(food, oldRating));
        cuisineFoods.get(cuisine).add(new Food(food, newRating));

        foodToRating.put(food, newRating);
    }

    public String highestRated(String cuisine) {
        return cuisineFoods.get(cuisine).first().name;
    }
}
```

---
