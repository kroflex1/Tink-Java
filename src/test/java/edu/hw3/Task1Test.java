package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {

    @ParameterizedTest
    @CsvSource(value = {
        "a, z",
        "b, y",
        "c, x",
        "d, w",
        "e, v",
        "f, u",
        "g, t",
        "h, s",
        "i, r",
        "j, q",
        "k, p",
        "l, o",
        "m,n"},
               ignoreLeadingAndTrailingWhitespace = true)
    @DisplayName("Проверка шифрования строчных и заглавных латинских букв")
    void testLatinLettersEncryption(String letter, String mirrorLetter) {
        String UpperLetter = letter.toUpperCase();
        String UpperMirrorLetter = mirrorLetter.toUpperCase();
        assertEquals(mirrorLetter, Task1.atbash(letter));
        assertEquals(letter, Task1.atbash(mirrorLetter));

        assertEquals(UpperMirrorLetter, Task1.atbash(UpperLetter));
        assertEquals(UpperLetter, Task1.atbash(UpperMirrorLetter));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ж", "л", "!", ".", ",", " "})
    @DisplayName("Проверка шифрования различных символов")
    void testSymbolsEncryption(String symbol) {
        assertEquals(symbol, Task1.atbash(symbol));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Hello world!, Svool dliow!",
        "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
    }, ignoreLeadingAndTrailingWhitespace = true)
    @DisplayName("Проверка шифрования предложений")
    void testEncryption(String line, String mirrorLine) {
        assertEquals(mirrorLine, Task1.atbash(line));
    }
}
