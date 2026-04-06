package org.example;

import org.example.service.FileDictionaryService;
import org.example.validator.LatinKeyValidator;

public class Main {

    public static void main(String[] args) {

        FileDictionaryService service =
                new FileDictionaryService("latin_dictionary.txt", new LatinKeyValidator());

        service.loadFromFile();

        System.out.println("Before adding:");
        service.getAll().forEach(System.out::println);

        System.out.println("\nAdd valid entry 'zxcv' = 'newWord':");
        boolean added1 = service.addEntry("zxcv", "newWord");
        System.out.println("Added: " + added1);

        System.out.println("\nAdd duplicate key 'abcd':");
        boolean added2 = service.addEntry("abcd", "duplicate");
        System.out.println("Added: " + added2);

        System.out.println("\nAdd invalid key 'abc12':");
        boolean added3 = service.addEntry("abc12", "wrong");
        System.out.println("Added: " + added3);

        service.saveToFile();

        System.out.println("\nAfter adding:");
        service.getAll().forEach(System.out::println);
    }
}