package org.example.service;

import org.example.model.DictionaryEntry;
import org.example.validator.KeyValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileDictionaryService implements DictionaryService {

    private final List<DictionaryEntry> entries = new ArrayList<>();
    private final String filePath;
    private final KeyValidator validator;

    public FileDictionaryService(String filePath, KeyValidator validator) {
        this.filePath = filePath;
        this.validator = validator;
    }

    @Override
    public void loadFromFile() {
        entries.clear();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Файл " + filePath + " не найден, будет создан новый словарь.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    if (!key.isEmpty() && !value.isEmpty()) {
                        entries.add(new DictionaryEntry(key, value));
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + filePath);
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            for (DictionaryEntry entry : entries) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + filePath);
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
        return entries.removeIf(entry -> entry.getKey().equals(key));
    }
}