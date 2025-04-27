package ru.practicum.dinner.dto;

import java.util.Objects;

public class MealType {
    private final String name;
    private final int order;

    public MealType(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealType mealType)) return false;
        return order == mealType.order && Objects.equals(name, mealType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order);
    }
}
