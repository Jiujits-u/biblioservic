package org.example.service;

import org.example.model.DictionaryEntry;
import org.example.validator.KeyValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDictionaryService implements DictionaryService {

    private List<DictionaryEntry> entries = new ArrayList<>();
    private String filePath;
    private KeyValidator validator;

    public FileDictionaryService(String filePath, KeyValidator validator) {
        this.filePath = filePath;
        this.validator = validator;
    }

    @Override
    public void loadFromFile() {
        entries.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    entries.add(new DictionaryEntry(parts[0], parts[1]));
                }
            }

        } catch (IOException e) {
            System.out.println("File not found, new dictionary will be created");
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (DictionaryEntry entry : entries) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DictionaryEntry> getAll() {
        return entries;
    }

    @Override
    public DictionaryEntry findByKey(String key) {
        for (DictionaryEntry entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public boolean addEntry(String key, String value) {
        if (!validator.isValid(key)) {
            return false;
        }

        if (findByKey(key) != null) {
            return false;
        }

        entries.add(new DictionaryEntry(key, value));
        return true;
    }

    @Override
    public boolean removeByKey(String key) {
        return entries.removeIf(e -> e.getKey().equals(key));
    }
}