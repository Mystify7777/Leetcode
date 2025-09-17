// 2353. Design a Food Rating System
/*
import javafx.util.Pair;
import java.util.*;

class FoodRatings {
    private Map<String, Pair<String, Integer>> foodInfo = new HashMap<>();
    private Map<String, SortedSet<Pair<Integer, String>>> sortedCuisine = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; i++) {
            String food = foods[i];
            String cuisine = cuisines[i];
            int rating = ratings[i];

            foodInfo.put(food, new Pair<>(cuisine, rating));
            sortedCuisine
                .computeIfAbsent(cuisine, k -> new TreeSet<>(Comparator
                    .<Pair<Integer, String>, Integer>comparing(Pair::getKey)
                    .reversed()
                    .thenComparing(Pair::getValue)))
                .add(new Pair<>(rating, food));
        }        
    }
    
    public void changeRating(String food, int newRating) {
        Pair<String, Integer> info = foodInfo.get(food);
        String cuisine = info.getKey();
        int oldRating = info.getValue();

        SortedSet<Pair<Integer, String>> sortedList = sortedCuisine.get(cuisine);
        sortedList.remove(new Pair<>(oldRating, food));
        sortedList.add(new Pair<>(newRating, food));

        foodInfo.put(food, new Pair<>(cuisine, newRating));        
    }
    
    public String highestRated(String cuisine) {
        SortedSet<Pair<Integer, String>> sortedList = sortedCuisine.get(cuisine);
        return sortedList.isEmpty() ? "" : sortedList.first().getValue();        
    }
}
    */

/**
 * Your FoodRatings object will be instantiated and called as such:
 * FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
 * obj.changeRating(food,newRating);
 * String param_2 = obj.highestRated(cuisine);
 */

 import java.util.*;

class FoodRatings {

    // Helper class to store food and rating
    static class Food {
        String name;
        int rating;

        Food(String name, int rating) {
            this.name = name;
            this.rating = rating;
        }
    }

    // Maps each food to its cuisine and rating
    private Map<String, String> foodToCuisine;
    private Map<String, Integer> foodToRating;

    // Maps cuisine to sorted foods
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

        // Remove old record
        cuisineFoods.get(cuisine).remove(new Food(food, oldRating));

        // Add updated record
        cuisineFoods.get(cuisine).add(new Food(food, newRating));

        // Update map
        foodToRating.put(food, newRating);
    }

    public String highestRated(String cuisine) {
        return cuisineFoods.get(cuisine).first().name;
    }
}
