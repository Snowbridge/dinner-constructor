package ru.practicum.dinner.services;

import ru.practicum.dinner.components.MealsRepository;

import java.util.Arrays;

public class DefaultMenuGenerator {
    static DefaultMenuGenerator instance;
    private final MealsRepository mealsRepository;

    private DefaultMenuGenerator() {
        mealsRepository = MealsRepository.getInstance();
    }

    public static DefaultMenuGenerator getInstance() {
        if (instance == null) {
            instance = new DefaultMenuGenerator();
        }
        return instance;
    }

    public void generate() {
        generateDrinks();
        generateFirstMeals();
        generateMainMeals();
        generateSnacks();
        generateDeserts();
        mealsRepository.addMeal("Блюдо от шефа", "Бубенцы Лесного Духа");
    }

    private void generateDeserts() {
        Arrays.asList(
                "Медовые пряники \"Лесной Пчеловод\"",
                "Ягодный пирог \"Лисья Нора\"",
                "Крем из молока единорога (на самом деле - козьего)",
                "Медовый сет \"Пятачок, неси ружьё!\""
        ).forEach(it ->
                mealsRepository.addMeal("Первое", it)
        );
    }

    private void generateSnacks() {
        Arrays.asList(
                "Хлеб с маслом и травами",
                "Соленья из бочки",
                "Жареные желуди \"Дубовый Путь\"",
                "Сыр \"Лесной Туман\""
        ).forEach(it ->
                mealsRepository.addMeal("Закуски", it)
        );
    }

    private void generateMainMeals() {
        Arrays.asList(
                "Жаркое \"Дикий Зверь\"",
                "Шашлык из птицы Феникс (или просто куропатки)",
                "Пирог \"Лесной Дар\"",
                "Стейк \"Клыкастой Свиньи\"",
                "Котлеты \"Лесной Барсук\""
        ).forEach(it ->
                mealsRepository.addMeal("Второе", it)
        );
    }

    private void generateFirstMeals() {
        Arrays.asList(
                "Похлёбка Охотника",
                "Уха из воооот такого палтуса",
                "Крем-суп из тыквы и мандрагоры",
                "Гороховый суп \"Песнь ветра\"",
                "Бульон с яйцом \"Тупая курица\""
        ).forEach(it ->
                mealsRepository.addMeal("Первое", it)
        );
    }

    private void generateDrinks() {
        Arrays.asList(
                        "Эль \"Тропа Странника\"",
                        "Медовуха \"Пчелиный Шепот\"",
                        "Чай Лесного Друида",
                        "Коньяк \"Огненный Корень\"",
                        "Вода из Родника Вечности")
                .forEach(it ->
                        mealsRepository.addMeal("Напитки", it)
                );
    }
}