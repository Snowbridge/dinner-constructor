package ru.practicum.dinner.cli;

import ru.practicum.dinner.components.MealsRepository;

public class MainMenu extends AbstractReplRunner {
    private final MealsRepository mealsRepository;

    public MainMenu() {
        super(
                "Главное меню",
                new String[]{
                        "Добавить новое блюдо",
                        "Посмотреть список всех блюд",
                        "Сгенерировать комбинации блюд"
                }
        );
        mealsRepository = MealsRepository.getInstance();
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
        }
    }

    private void generateSets() {
        throw new RuntimeException("Not implemented yet");
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
