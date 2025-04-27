package ru.practicum.dinner.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MealsRepository {
    private static MealsRepository instance;
    private final HashMap<String, Set<String>> meals;

    private MealsRepository() {
        meals = new HashMap<>();
    }

    public static MealsRepository getInstance() {
        if (instance == null) {
            instance = new MealsRepository();
        }
        return instance;
    }

    public Set<String> getAvailableMealTypes() {
        return meals.keySet();
    }

    public Set<String> getMealsByType(String type) {
        return meals.get(type);
    }

    public void addMeal(String mealType, String mealName) {
        if (!meals.containsKey(mealType)) {
            meals.put(mealType, new HashSet<>());
        }
        meals.get(mealType).add(mealName);
    }

    public void generateDefaultMeals() {
        // Напитки
        HashSet<String> defaultMeals = new HashSet<>();
    }
}
