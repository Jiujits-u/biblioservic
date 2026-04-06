package org.example.validator;

public class DigitKeyValidator implements KeyValidator{
    @Override
    public boolean isValid(String key) {
        if (key == null) {
            return false;
        }
        return key.matches("\\d{5}");
    }
}
