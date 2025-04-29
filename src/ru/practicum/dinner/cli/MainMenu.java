package ru.practicum.dinner.cli;

/*
The file if in UTF-8 with LF-type end lines, I've checked it three times.
If the cyrillic is still broken then this particular file is just damned by Practicum engine, I give up.
*/

import ru.practicum.dinner.components.MealsRepository;
import ru.practicum.dinner.dto.Meal;
import ru.practicum.dinner.dto.MealType;
import ru.practicum.dinner.services.Converter;
import ru.practicum.dinner.services.DefaultMenuGenerator;
import ru.practicum.dinner.services.DinnerConstructor;

import java.util.Set;

public class MainMenu extends AbstractReplRunner {
    private final MealsRepository mealsRepository;
    private final DinnerConstructor dinnerConstructor;

    public MainMenu() {
        super(
                "Главное меню",
                new String[]{
                        "Добавить новое блюдо",
                        "Посмотреть список всех блюд",
                        "Сгенерировать комбинации блюд",
                        "Заполнить стандартный список блюд"
                }
        );
        mealsRepository = MealsRepository.getInstance();
        dinnerConstructor = DinnerConstructor.getInstance();
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                addNewMeal();
                break;
            case 2:
                listMeals();
                break;
            case 3:
                generateSets();
                break;
            case 4:
                DefaultMenuGenerator.getInstance().generate();
                break;
        }
    }

    private void generateSets() {
        if (mealsRepository.isEmpty())
            throw new IllegalStateException("Не возможно сгенерировать наборы из пустого меню");

        int amount = queryIntFromStdin("Введите количество наборов, которые нужно сгенерировать");

        Set<MealType> availableMealTypes = mealsRepository.getAvailableMealTypes();
        String numberedMealTypesString = Converter.mealTypesToNumberedString(availableMealTypes);

        String chosenTypesString = queryStringFromStdin("Введите номера типов блюд через запятую [" + numberedMealTypesString + "]");
        Set<MealType> chosenMealTypes = Converter.commaSeparatedNumbersToMealTypes(availableMealTypes, chosenTypesString);

        dinnerConstructor.generateLunchSets(chosenMealTypes, amount)
                .forEach((lunchSetName, meals) -> {
                    println(lunchSetName);
                    meals.forEach(this::printMeal);
                    int cost = meals.stream().mapToInt(Meal::getPrice).sum();
                    println("\t Стоимость: " + cost + " ракушек");
                });
    }

    private void addNewMeal() {
        String mealTypeName = queryStringFromStdin("Введите тип блюда");
        if (mealTypeName == null || mealTypeName.isEmpty()) {
            throw new IllegalArgumentException("Тип блюда не может быть пустым");
        }

        MealType mealType = mealsRepository.getMealTypeByName(mealTypeName);

        String mealName = queryStringFromStdin("Введите название блюда");
        if (mealName == null || mealName.isEmpty()) {
            throw new IllegalArgumentException("Название блюда не может быть пустым");
        }

        String mealDescription = queryStringFromStdin("Введите описание блюда");
        int mealPrice = queryIntFromStdin("Введите цену блюда");

        mealsRepository.addMeal(mealType, new Meal(mealName, mealDescription, mealPrice));
    }

    private void listMeals() {
        for (MealType mealType : mealsRepository.getAvailableMealTypes()) {
            println(String.format("$ %s:", mealType));
            mealsRepository.getMealsByType(mealType)
                    .forEach(this::printMeal);
        }
    }

    private void printMeal(Meal meal) {
        println(
                String.format(
                        "\t%s\n\t\t%s\n\t\t%d ракушек",
                        meal.getName(),
                        meal.getDescription(),
                        meal.getPrice()
                )
        );
    }
}
