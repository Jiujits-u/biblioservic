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
            System.out.println("\n=== Dictionary Menu ===");
            System.out.println("1. Show latin dictionary");
            System.out.println("2. Show digit dictionary");
            System.out.println("3. Add entry to latin dictionary");
            System.out.println("4. Add entry to digit dictionary");
            System.out.println("5. Find in latin dictionary");
            System.out.println("6. Find in digit dictionary");
            System.out.println("7. Remove from latin dictionary");
            System.out.println("8. Remove from digit dictionary");
            System.out.println("9. Show both dictionaries");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printDictionary("Latin dictionary", latinService);
                    break;
                case "2":
                    printDictionary("Digit dictionary", digitService);
                    break;
                case "3":
                    addEntry(latinService);
                    break;
                case "4":
                    addEntry(digitService);
                    break;
                case "5":
                    findEntry(latinService);
                    break;
                case "6":
                    findEntry(digitService);
                    break;
                case "7":
                    removeEntry(latinService);
                    break;
                case "8":
                    removeEntry(digitService);
                    break;
                case "9":
                    printDictionary("Latin dictionary", latinService);
                    printDictionary("Digit dictionary", digitService);
                    break;
                case "0":
                    latinService.saveToFile();
                    digitService.saveToFile();
                    System.out.println("Program finished");
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    private void addEntry(FileDictionaryService service) {
        System.out.print("Enter key: ");
        String key = scanner.nextLine();

        System.out.print("Enter value: ");
        String value = scanner.nextLine();

        boolean added = service.addEntry(key, value);

        if (added) {
            service.saveToFile();
            System.out.println("Added");
        } else {
            System.out.println("Invalid key or duplicate");
        }
    }

    private void findEntry(FileDictionaryService service) {
        System.out.print("Enter key to find: ");
        String key = scanner.nextLine();

        DictionaryEntry entry = service.findByKey(key);

        if (entry != null) {
            System.out.println("Found: " + entry);
        } else {
            System.out.println("Entry not found");
        }
    }

    private void removeEntry(FileDictionaryService service) {
        System.out.print("Enter key to remove: ");
        String key = scanner.nextLine();

        boolean removed = service.removeByKey(key);

        if (removed) {
            service.saveToFile();
            System.out.println("Removed");
        } else {
            System.out.println("Entry not found");
        }
    }

    private void printDictionary(String title, FileDictionaryService service) {
        System.out.println("\n--- " + title + " ---");

        if (service.getAll().isEmpty()) {
            System.out.println("Dictionary is empty");
            return;
        }

        service.getAll().forEach(System.out::println);
    }
}