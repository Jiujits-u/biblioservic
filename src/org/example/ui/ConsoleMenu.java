package org.example.ui;

import org.example.model.DictionaryEntry;
import org.example.service.FileDictionaryService;
import org.example.validator.DigitKeyValidator;
import org.example.validator.KeyValidator;
import org.example.validator.LatinKeyValidator;

import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        while (true) {
            System.out.println("\n===== Сервис словарей =====");
            System.out.println("1. Выбрать латинский словарь (4 буквы)");
            System.out.println("2. Выбрать цифровой словарь (5 цифр)");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    openDictionary(new LatinKeyValidator(), "Латинский словарь");
                    break;
                case "2":
                    openDictionary(new DigitKeyValidator(), "Цифровой словарь");
                    break;
                case "0":
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void openDictionary(KeyValidator validator, String title) {

        System.out.print("Введите путь к файлу словаря: ");
        String path = scanner.nextLine().trim();

        FileDictionaryService service =
                new FileDictionaryService(path, validator);

        service.loadFromFile();


        while (true) {
            System.out.println("\n--- " + title + " ---");
            System.out.println("1. Показать содержимое");
            System.out.println("2. Добавить запись");
            System.out.println("3. Найти запись");
            System.out.println("4. Удалить запись");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printAll(service);
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
                    service.saveToFile();
                    return;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void printAll(FileDictionaryService service) {
        if (service.getAll().isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }

        for (DictionaryEntry entry : service.getAll()) {
            System.out.println(entry);
        }
    }

    private void addEntry(FileDictionaryService service) {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        System.out.print("Введите перевод: ");
        String value = scanner.nextLine().trim();

        if (service.addEntry(key, value)) {
            service.saveToFile();
            System.out.println("Добавлено.");
        } else {
            System.out.println("Ошибка: ключ неверный или уже существует.");
        }
    }

    private void findEntry(FileDictionaryService service) {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        DictionaryEntry entry = service.findByKey(key);

        if (entry != null) {
            System.out.println("Найдено: " + entry);
        } else {
            System.out.println("Не найдено.");
        }
    }

    private void removeEntry(FileDictionaryService service) {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        if (service.removeByKey(key)) {
            service.saveToFile();
            System.out.println("Удалено.");
        } else {
            System.out.println("Не найдено.");
        }
    }
}