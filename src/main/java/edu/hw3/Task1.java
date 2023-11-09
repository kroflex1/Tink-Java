package edu.hw3;

public class Task1 {

    private static final int START_CODE_FOR_LOWERCASE_LETTER = 'a';
    private static final int END_CODE_FOR_LOWERCASE_LETTER = 'z';
    private static final int START_CODE_FOR_UPPERCASE_LETTER = 'A';
    private static final int END_CODE_FOR_UPPERCASE_LETTER = 'Z';

    public static String atbash(String word) {
        StringBuilder encryptedWord = new StringBuilder();
        for (int i = 0; i < word.length(); ++i) {
            encryptedWord.append(getMirrorSymbol(word.charAt(i)));
        }
        return encryptedWord.toString();
    }

    private static char getMirrorSymbol(char letter) {
        if (isLatinLetter(letter)) {
            if ((int) letter >= START_CODE_FOR_LOWERCASE_LETTER) {
                return getMirrorLowercaseLatinLetter(letter);
            }
            return Character.toUpperCase(getMirrorLowercaseLatinLetter(Character.toLowerCase(letter)));
        }
        return letter;
    }

    private static char getMirrorLowercaseLatinLetter(char lowerSymbol) {
        int middleCode = (END_CODE_FOR_LOWERCASE_LETTER - START_CODE_FOR_LOWERCASE_LETTER + 1) / 2;
        if (lowerSymbol <= START_CODE_FOR_LOWERCASE_LETTER + middleCode) {
            return (char) (END_CODE_FOR_LOWERCASE_LETTER - ((int) lowerSymbol - START_CODE_FOR_LOWERCASE_LETTER));
        }
        return (char) (START_CODE_FOR_LOWERCASE_LETTER + (END_CODE_FOR_LOWERCASE_LETTER - (int) lowerSymbol));
    }

    private static boolean isLatinLetter(char letter) {
        return (letter >= START_CODE_FOR_UPPERCASE_LETTER && letter <= END_CODE_FOR_UPPERCASE_LETTER)
            || (letter >= START_CODE_FOR_LOWERCASE_LETTER && letter <= END_CODE_FOR_LOWERCASE_LETTER);
    }

    private Task1() {

    }
}
