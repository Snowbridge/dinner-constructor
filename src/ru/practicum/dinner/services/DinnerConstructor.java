package ru.practicum.dinner.services;

import ru.practicum.dinner.components.MealsRepository;

import java.util.*;

public class DinnerConstructor {

    private static final int ITERATIONS_LIMIT = 100;

    static DinnerConstructor instance;
    MealsRepository mealsRepository;

    private DinnerConstructor() {
        mealsRepository = MealsRepository.getInstance();
    }

    public static DinnerConstructor getInstance() {
        if (instance == null) {
            instance = new DinnerConstructor();
        }
        return instance;
    }

    public Map<String, String> generateLunchSets(Collection<String> chosenTypes, int amount) {
        Map<String, String> lunchSets = new HashMap<>();
        for (int i = 0; lunchSets.size() < amount; i++) {
            String set = generateSet(chosenTypes);
            if (!lunchSets.containsValue(set)) {
                lunchSets.put(
                        "Комбо " + (lunchSets.size() + 1),
                        set
                );
            }
            if (i > ITERATIONS_LIMIT * amount) {
                break; // если столько уникальных комбинаций невозможно составить, то когда-то надо остановиться
            }
        }
        return lunchSets;
    }

    private String generateSet(Collection<String> chosenTypes) {
        Random rand = new Random();
        ArrayList<String> lunchSet = new ArrayList<>(chosenTypes.size());
        for (String type : chosenTypes) {
            Set<String> meals = mealsRepository.getMealsByType(type);
            String currentMeal = meals.stream()
                    .skip(rand.nextLong(0, meals.size()))
                    .findFirst().orElseThrow();
            lunchSet.add(currentMeal);
        }
        return lunchSet.toString();
    }
}
