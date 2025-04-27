package ru.practicum.dinner.services;

import ru.practicum.dinner.components.MealsRepository;
import ru.practicum.dinner.dto.Meal;
import ru.practicum.dinner.dto.MealType;

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
        mealsRepository.clear();
        generateFirstMeals(mealsRepository.getMealTypeByName("Первое"));
        generateMainMeals(mealsRepository.getMealTypeByName("Второе"));
        generateSnacks(mealsRepository.getMealTypeByName("Закуски"));
        generateDrinks(mealsRepository.getMealTypeByName("Напитки"));
        generateDeserts(mealsRepository.getMealTypeByName("Десерт"));
        mealsRepository.addMeal(
                mealsRepository.getMealTypeByName("Блюдо от шефа"),
                new Meal(
                        "Бубенцы Лесного Духа",
                        "Секретное блюдо дня, которое Грум готовит только для избранных. Ингредиенты зависят от того, что удалось найти в лесу сегодня.",
                        121
                )
        );
    }

    private void generateDeserts(MealType mealType) {
        Arrays.asList(
                new Meal("Медовые пряники \"Лесной Пчеловод\"", "Пряники с медом и корицей, украшенные символами удачи.", 4),
                new Meal("Ягодный пирог \"Лисья Нора\"", "Сладкий пирог с начинкой из лесных ягод: малины, ежевики и голубики.", 7),
                new Meal("Крем из молока единорога (на самом деле - козьего)", "Нежный крем с добавлением ванили и меда.", 6)
        ).forEach(it ->
                mealsRepository.addMeal(mealType, it)
        );
    }

    private void generateSnacks(MealType mealType) {
        Arrays.asList(
                new Meal("Хлеб с маслом и травами", "Свежевыпеченный хлеб с ароматным маслом, смешанным с лесными травами.", 3),
                new Meal("Соленья из бочки", "Огурцы, помидоры, грибы и другие дары леса, маринованные в специальном рассоле.", 4),
                new Meal("Жареные желуди \"Дубовый Путь\"", "Соленые и обжаренные желуди, подсушенные на сковороде. Идеальная закуска к элю.", 2),
                new Meal("Сыр \"Лесной Туман\"", "Плотный сыр с ореховым привкусом, завернутый в листья малины.", 5)
        ).forEach(it ->
                mealsRepository.addMeal(mealType, it)
        );
    }

    private void generateMainMeals(MealType mealType) {
        Arrays.asList(
                new Meal("Жаркое \"Дикий Зверь\"", "Медленно томленное мясо кабана с картофелем, морковью и специями. Подается с соусом из лесных ягод.", 10),
                new Meal("Шашлык из птицы Феникс (или просто куропатки)", "Маринованные кусочки птицы, обжаренные на вертеле над открытым огнем. Подается с лесным салатом.", 9),
                new Meal("Пирог \"Лесной Дар\"", "Слоеный пирог с начинкой из грибов, сыра и зелени. Подается горячим.", 8),
                new Meal("Стейк \"Клыкастой Свиньи\"", "Толстый кусок мяса, приправленный диким чесноком и перцем. Подается с запеченными овощами.", 12),
                new Meal("Котлеты \"Лесной Барсук\"", "Котлеты из мяса, которое шеф называет \"барсучьим\", но никто точно не знает. Подается с гречневой кашей.", 11)
        ).forEach(it ->
                mealsRepository.addMeal(mealType, it)
        );
    }

    private void generateFirstMeals(MealType mealType) {
        Arrays.asList(
                new Meal("Похлёбка Охотника", "Густой суп из лесных грибов, дикого лука и мяса неизвестного зверя (Грум говорит, что это зайцы). Подается с ломтем черного хлеба.", 6),
                new Meal("Уха из воооот такого палтуса", "Уха из свежей рыбы, выловленной в реке неподалеку. Приправлена диким укропом и щепоткой магической соли.", 7),
                new Meal("Крем-суп из тыквы и мандрагоры", "Нежный сливочный суп с ароматом осени. Говорят, он помогает видеть сны без кошмаров.", 5)
        ).forEach(it ->
                mealsRepository.addMeal(mealType, it)
        );
    }

    private void generateDrinks(MealType mealType) {
        Arrays.asList(
                        new Meal("Эль \"Тропа Странника\"", "Крепкий янтарный эль, сваренный в деревушке за три лиги отсюда. Помогает согреться холодными ночами.", 3),
                        new Meal("Медовуха \"Пчелиный Шепот\"", "Сладкая, с нотками трав и ягод. Приправленная мятой и каплей лесной магии.", 4),
                        new Meal("Чай Лесного Друида", "Сбор из целебных трав: мята, шалфей, зверобой и немного магического мха. Успокаивает душу и тело.", 2),
                        new Meal("Коньяк \"Огненный Корень\"", "Крепкий напиток, настоянный на коре векового дуба и перца-дракона. Для самых стойких.", 8),
                        new Meal("Вода из Родника Вечности", "Источник, расположенный в глубине леса, известен своей чистотой и легендами о долголетии.", 1)
                )
                .forEach(it ->
                        mealsRepository.addMeal(mealType, it)
                );
    }
}