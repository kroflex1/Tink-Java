package edu.hw1;

public class Task5 {

    public static boolean isPalindrome(String word) {
        for (int i = 0; i < word.length() / 2; ++i) {
            if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindromeDescendant(int number) {
        String currentNumber = String.valueOf(number);
        if (number < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        if (currentNumber.length() == 1) {
            return false;
        }
        if (isPalindrome(currentNumber)) {
            return true;
        }
        if (currentNumber.length() % 2 != 0) {
            throw new IllegalArgumentException(String.format(
                "{%d isn`t a palindrome, it`s impossible to get its descendant since it contains odd number of digits",
                number
            )
            );
        }
        return isPalindromeDescendant(getDescendantOfNumber(number));
    }

    private Task5() {

    }

    private static int getDescendantOfNumber(int number) {
        String currentNumber = String.valueOf(number);
        StringBuilder descendant = new StringBuilder();
        for (int i = 0; i < currentNumber.length() - 1; i += 2) {
            descendant.append(
                Character.getNumericValue(currentNumber.charAt(i))
                    + Character.getNumericValue(currentNumber.charAt(i + 1)));
        }
        return Integer.parseInt(descendant.toString());
    }

}
