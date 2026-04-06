package org.example.ui;

import org.example.model.DictionaryEntry;
import org.example.service.FileDictionaryService;
import org.example.validator.DigitKeyValidator;
import org.example.validator.LatinKeyValidator;

import java.util.Scanner;

public class ConsoleMenu {

    private final FileDictionaryService latinService =
            new FileDictionaryService("latin_dictionary.txt", new LatinKeyValidator());

    private final FileDictionaryService digitService =
            new FileDictionaryService("digit_dictionary.txt", new DigitKeyValidator());

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        latinService.loadFromFile();
        digitService.loadFromFile();

        while (true) {
            System.out.println("\n===== Сервис словарей =====");
            System.out.println("1. Показать оба словаря");
            System.out.println("2. Выбрать латинский словарь");
            System.out.println("3. Выбрать цифровой словарь");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printDictionary("Латинский словарь", latinService);
                    printDictionary("Цифровой словарь", digitService);
                    break;
                case "2":
                    dictionaryActions("Латинский словарь", latinService);
                    break;
                case "3":
                    dictionaryActions("Цифровой словарь", digitService);
                    break;
                case "0":
                    latinService.saveToFile();
                    digitService.saveToFile();
                    System.out.println("Работа программы завершена.");
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    private void dictionaryActions(String title, FileDictionaryService service) {
        while (true) {
            System.out.println("\n--- " + title + " ---");
            System.out.println("1. Показать содержимое");
            System.out.println("2. Добавить запись");
            System.out.println("3. Найти запись по ключу");
            System.out.println("4. Удалить запись по ключу");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printDictionary(title, service);
                    break;
                case "2":
                    addEntry(service);
                    break;
                case "3":
                    findEntry(service);
                    break;
                case "4":
                    removeEntry(service);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    private void addEntry(FileDictionaryService service) {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        if (key.isEmpty()) {
            System.out.println("Ключ не может быть пустым.");
            return;
        }

        System.out.print("Введите перевод: ");
        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            System.out.println("Значение не может быть пустым.");
            return;
        }

        boolean added = service.addEntry(key, value);

        if (added) {
            service.saveToFile();
            System.out.println("Запись успешно добавлена.");
        } else {
            System.out.println("Ключ не соответствует формату или уже существует.");
        }
    }

    private void findEntry(FileDictionaryService service) {
        System.out.print("Введите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        if (key.isEmpty()) {
            System.out.println("Ключ не может быть пустым.");
            return;
        }

        DictionaryEntry entry = service.findByKey(key);

        if (entry != null) {
            System.out.println("Найдено: " + entry);
        } else {
            System.out.println("Запись не найдена.");
        }
    }

    private void removeEntry(FileDictionaryService service) {
        System.out.print("Введите ключ для удаления: ");
        String key = scanner.nextLine().trim();

        if (key.isEmpty()) {
            System.out.println("Ключ не может быть пустым.");
            return;
        }

        boolean removed = service.removeByKey(key);

        if (removed) {
            service.saveToFile();
            System.out.println("Запись удалена.");
        } else {
            System.out.println("Запись не найдена.");
        }
    }

    private void printDictionary(String title, FileDictionaryService service) {
        System.out.println("\n--- " + title + " ---");

        if (service.getAll().isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }

        for (DictionaryEntry entry : service.getAll()) {
            System.out.println(entry);
        }
    }
}