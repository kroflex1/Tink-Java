package edu.hw1;

public class Task4 {

    public static String fixString(String word) {
        char[] symbols = word.toCharArray();
        for (int i = 0; i < symbols.length - 1; i += 2) {
            char temp = symbols[i];
            symbols[i] = symbols[i + 1];
            symbols[i + 1] = temp;
        }
        return new String(symbols);
    }

    private Task4() {

    }

}
