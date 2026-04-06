package org.example.validator;

public class LatinKeyValidator implements  KeyValidator{
    @Override
    public boolean isValid(String key) {
        if (key == null) {
            return false;
        }

        return  key.matches("[a-zA-Z]{4}");
    }
}
