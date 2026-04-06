package org.example;

import org.example.service.FileDictionaryService;
import org.example.validator.LatinKeyValidator;

public class Main {

    public static void main(String[] args) {

        FileDictionaryService service =
                new FileDictionaryService("latin_dictionary.txt", new LatinKeyValidator());

        service.loadFromFile();

        service.getAll().forEach(System.out::println);
    }
}