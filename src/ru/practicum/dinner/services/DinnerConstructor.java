package ru.practicum.dinner.services;

import ru.practicum.dinner.components.MealsRepository;
import ru.practicum.dinner.dto.Meal;
import ru.practicum.dinner.dto.MealType;

import java.util.*;

public class DinnerConstructor {

    private static final int ITERATIONS_LIMIT = 100;

    static DinnerConstructor instance;
    final MealsRepository mealsRepository;

    private DinnerConstructor() {
        mealsRepository = MealsRepository.getInstance();
    }

    public static DinnerConstructor getInstance() {
        if (instance == null) {
            instance = new DinnerConstructor();
        }
        return instance;
    }

    public Map<String, List<Meal>> generateLunchSets(Set<MealType> chosenTypes, int amount) {
        int iterationsCounter = 0;

        Map<String, List<Meal>> lunchSets = new TreeMap<>();
        while (lunchSets.size() < amount && iterationsCounter < ITERATIONS_LIMIT) {
            List<Meal> currentLunchSet = generateSet(chosenTypes);
            if (!lunchSets.containsValue(currentLunchSet)) {
                lunchSets.put(
                        "Комбо " + (lunchSets.size() + 1),
                        currentLunchSet
                );
            }

            iterationsCounter++; // если столько уникальных комбинаций не возможно составить, то когда-то надо остановиться
        }
        return lunchSets;
    }

    private List<Meal> generateSet(Set<MealType> chosenTypes) {
        Random rand = new Random();
        List<Meal> lunchSet = new ArrayList<>(chosenTypes.size());
        chosenTypes.stream()
                .sorted(Comparator.comparingInt(MealType::getOrder))
                .forEach(mealType -> {
                    Set<Meal> meals = mealsRepository.getMealsByType(mealType);
                    Meal currentMeal = meals.stream()
                            .skip(rand.nextLong(0, meals.size()))
                            .findFirst().orElseThrow();
                    lunchSet.add(currentMeal);
                });
        return lunchSet;
    }
}
