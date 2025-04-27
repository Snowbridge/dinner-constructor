package ru.practicum.dinner.components;

import ru.practicum.dinner.dto.Meal;
import ru.practicum.dinner.dto.MealType;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class MealsRepository {
    private static MealsRepository instance;
    private final TreeMap<MealType, Set<Meal>> meals;

    private MealsRepository() {
        meals = new TreeMap<>(Comparator.comparingInt(MealType::getOrder));
    }

    public static MealsRepository getInstance() {
        if (instance == null) {
            instance = new MealsRepository();
        }
        return instance;
    }

    public Set<MealType> getAvailableMealTypes() {
        return meals.keySet();
    }

    public Set<Meal> getMealsByType(MealType type) {
        return meals.get(type);
    }

    public void addMeal(MealType mealType, Meal meal) {
        if (!meals.containsKey(mealType)) {
            boolean duplicateOrderFound = meals.keySet().stream().anyMatch(type -> type.getOrder() == mealType.getOrder());
            if (duplicateOrderFound) {
                throw new IllegalArgumentException("Тип блюда с таким порядковым номером уже добавлен в меню");
            }
            meals.put(mealType, new HashSet<>());
        }
        meals.get(mealType).add(meal);
    }

    public MealType getMealTypeByName(String mealTypeName) {
        return meals.keySet()
                .stream()
                .filter(type -> type.getName().equals(mealTypeName))
                .findFirst()
                .orElseGet(() -> {
                    int maxOrder = meals.keySet()
                            .stream()
                            .mapToInt(MealType::getOrder)
                            .max()
                            .orElse(0);
                    return new MealType(mealTypeName, 1 + maxOrder);
                });
    }

    public void clear() {
        meals.clear();
    }

    public boolean isEmpty() {
        return meals.isEmpty();
    }
}
