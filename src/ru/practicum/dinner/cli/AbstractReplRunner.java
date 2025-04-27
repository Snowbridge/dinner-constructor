package ru.practicum.dinner.cli;

import java.util.*;

/**
 * REPL - Read-Eval-Print-Loop
 * класс, инкапсулирующий общую логику для консольного меню приложения:
 * 1. Вывод меню
 * 2. Запрос команды
 * 3. Вызов обработчиков команд
 * 4. Обработка несуществующих команд и исключений
 */
public abstract class AbstractReplRunner {
    static final int EXIT_COMMAND = 0;
    final ArrayList<String> menu;
    final Scanner scanner = new Scanner(System.in);
    final String menuHeader;

    int level = 0;

    public AbstractReplRunner(String menuHeader, String[] menu) {
        this.menuHeader = menuHeader;
        this.menu = new ArrayList<>();
        this.menu.add("Выход"); // нулевой элемент - это в любом меню всегда "выход"
        this.menu.addAll(Arrays.asList(menu));
    }

    public void run() {
        while (true) {
            println(menuHeader);
            printMenu();

            try {
                int command = queryIntFromStdin("Введите команду", 0, menu.size() - 1);
                if (command == EXIT_COMMAND) {
                    return;
                }
                if (command > 0 && command < menu.size()) {
                    processCommand(command);
                } else
                    println("Не известная команда");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                println("Введены не корректные данные");
            } catch (Exception e) {
                println(e.getMessage());
            }
        }

    }

    // обработка команд реализуется в конкретных классах-имплементациях
    abstract void processCommand(int command);

    void printMenu() {
        for (int i = 1; i < menu.size(); i++) {
            println(String.format("%d. %s", i, menu.get(i)));
        }
        println("0. Выход");
        println("");
    }

    int queryIntFromStdin(String prompt) {
        print(prompt + ": ");
        return scanner.nextInt();
    }

    int queryIntFromStdin(String prompt, int min, int max) {
        int value = queryIntFromStdin(String.format("%s [%d..%d]", prompt, min, max));
        if (value < min || value > max) {
            throw new IllegalArgumentException("Значение должно быть в диапазоне " + min + ".." + max);
        }
        return value;
    }

    String queryStringFromStdin(String prompt) {
        print(prompt + ": ");
        return scanner.nextLine();
    }

    String queryEnumFromStdin(String prompt, Set<String> enumValues) {
        String value = queryStringFromStdin(prompt);
        if (!enumValues.contains(value)) {
            throw new StringIndexOutOfBoundsException("Значение не входит в перечень допустимых " + enumValues);
        }
        return value;
    }

    void print(String line) {
        String prefix = "";
        if (level > 0) {
            prefix = " ".repeat(level) + "> ";
        }
        System.out.print(prefix + line);
    }

    void println(String line) {
        print(line + System.lineSeparator());
    }
}
