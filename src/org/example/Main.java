package org.example;

import org.example.model.DictionaryEntry;
import org.example.service.FileDictionaryService;
import org.example.validator.LatinKeyValidator;

public class Main {

    public static void main(String[] args) {

        FileDictionaryService service =
                new FileDictionaryService("latin_dictionary.txt", new LatinKeyValidator());

        service.loadFromFile();

        System.out.println("All entries:");
        service.getAll().forEach(System.out::println);

        System.out.println("\nSearch by key 'qwer':");
        DictionaryEntry found = service.findByKey("qwer");
        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("Entry not found");
        }

        System.out.println("\nRemove key 'test':");
        boolean removed = service.removeByKey("test");
        System.out.println("Removed: " + removed);

        service.saveToFile();

        System.out.println("\nEntries after удаления:");
        service.getAll().forEach(System.out::println);
    }
}