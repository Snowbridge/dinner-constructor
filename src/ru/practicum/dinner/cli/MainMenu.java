package ru.practicum.dinner.cli;

import ru.practicum.dinner.components.MealsRepository;
import ru.practicum.dinner.services.DefaultMenuGenerator;
import ru.practicum.dinner.services.DinnerConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        int amount = queryIntFromStdin("Введите количество наборов, которые нужно сгенерировать");
        List<String> mealTypes = new ArrayList<>(mealsRepository.getAvailableMealTypes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mealTypes.size(); i++) {
            sb.append(String.format("%d - %s ", i + 1, mealTypes.get(i)));
        }

        String chosenTypesString = queryStringFromStdin("Введите номера типов блюд через запятую [" + sb.toString().trim() + "]");

        Set<String> chosenTypes = Arrays.stream(chosenTypesString.split(","))
                .map(it -> {
                    if (it.isEmpty()) {
                        throw new IllegalArgumentException("Номер типа блюда не может быть пустым");
                    }
                    int chosenTypeIndex = Integer.parseInt(it.trim());
                    return mealTypes.get(chosenTypeIndex - 1);
                })
                .collect(Collectors.toSet());

        dinnerConstructor.generateLunchSets(chosenTypes, amount)
                .forEach((lunchSetName, meals) -> {
                    println(lunchSetName);
                    println(meals);
                });
    }

    private void addNewMeal() {
        String mealType = queryStringFromStdin("Введите тип блюда");
        if (mealType == null || mealType.isEmpty()) {
            throw new IllegalArgumentException("Тип блюда не может быть пустым");
        }

        String mealName = queryStringFromStdin("Введите название блюда");
        if (mealName == null || mealName.isEmpty()) {
            throw new IllegalArgumentException("Название блюда не может быть пустым");
        }

        mealsRepository.addMeal(mealType, mealName);
    }

    private void listMeals() {
        for (String mealType : mealsRepository.getAvailableMealTypes()) {
            println("# " + mealType + ":");
            mealsRepository.getMealsByType(mealType)
                    .forEach(it -> println("\t" + it));
        }
    }
}
