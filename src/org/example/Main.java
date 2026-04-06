package org.example;
import org.example.model.DictionaryEntry;
import org.example.validator.DigitKeyValidator;
import org.example.validator.KeyValidator;
import org.example.validator.LatinKeyValidator;

public class Main {
    public static void main(String[] args) {
        KeyValidator latin = new LatinKeyValidator();
        KeyValidator digit = new DigitKeyValidator();
        System.out.println(latin.isValid("abcd")); //true
        System.out.println(latin.isValid("abc")); //false

        System.out.println(digit.isValid("12345")); //true
        System.out.println(digit.isValid("1234")); //false
    }
}
