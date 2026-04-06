package org.example.service;

import org.example.model.DictionaryEntry;

import java.util.List;

public interface DictionaryService {

    void loadFromFile();

    void saveToFile();

    List<DictionaryEntry> getAll();

    DictionaryEntry findByKey(String key);

    boolean addEntry(String key, String value);

    boolean removeByKey(String key);
}