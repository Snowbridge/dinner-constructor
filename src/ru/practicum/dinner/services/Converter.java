package ru.practicum.dinner.services;

import ru.practicum.dinner.dto.MealType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Converter {

    private Converter() {
        throw new RuntimeException("Utility class");
    }

    public static String mealTypesToNumberedString(Set<MealType> mealTypes) {
        return mealTypes.stream()
                .map(mealType -> String.format("%d - %s", mealType.getOrder(), mealType.getName()))
                .collect(Collectors.joining(", "));
    }

    public static Set<MealType> commaSeparatedNumbersToMealTypes(Set<MealType> availableMealTypes, String chosenTypesString) {
        return Arrays.stream(chosenTypesString.split(","))
                .map(it -> {
                    if (it.isEmpty()) {
                        throw new IllegalArgumentException("Номер типа блюда не может быть пустым");
                    }
                    int chosenTypeIndex = Integer.parseInt(it.trim());
                    return availableMealTypes.stream()
                            .filter(mealType -> mealType.getOrder() == chosenTypeIndex)
                            .findFirst()
                            .orElseThrow();
                })
                .collect(Collectors.toSet());
    }
}
