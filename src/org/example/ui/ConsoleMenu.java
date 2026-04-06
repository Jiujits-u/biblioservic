package org.example.ui;

import org.example.service.FileDictionaryService;
import org.example.validator.DigitKeyValidator;
import org.example.validator.LatinKeyValidator;

import java.util.Scanner;

public class ConsoleMenu {

    private FileDictionaryService latinService =
            new FileDictionaryService("latin_dictionary.txt", new LatinKeyValidator());

    private FileDictionaryService digitService =
            new FileDictionaryService("digit_dictionary.txt", new DigitKeyValidator());

    private Scanner scanner = new Scanner(System.in);

    public void start() {

        latinService.loadFromFile();
        digitService.loadFromFile();

        while (true) {

            System.out.println("\n=== Dictionary Menu ===");
            System.out.println("1. Show latin dictionary");
            System.out.println("2. Show digit dictionary");
            System.out.println("3. Add entry to latin dictionary");
            System.out.println("4. Add entry to digit dictionary");
            System.out.println("5. Exit");

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    latinService.getAll().forEach(System.out::println);
                    break;

                case "2":
                    digitService.getAll().forEach(System.out::println);
                    break;

                case "3":
                    addEntry(latinService);
                    break;

                case "4":
                    addEntry(digitService);
                    break;

                case "5":
                    latinService.saveToFile();
                    digitService.saveToFile();
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
            System.out.println("Added");
        } else {
            System.out.println("Invalid key or duplicate");
        }
    }
}