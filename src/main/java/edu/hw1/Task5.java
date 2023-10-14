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
        if (number < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        return isPalindromeDescendant(String.valueOf(number));
    }

    private static boolean isPalindromeDescendant(String number) {
        if (number.length() == 1) {
            return false;
        }
        if (isPalindrome(number)) {
            return true;
        }
        if (number.length() % 2 != 0) {
            throw new IllegalArgumentException(String.format(
                "{%s isn`t a palindrome, it`s impossible to get its descendant since it contains odd number of digits",
                number
            )
            );
        }
        return isPalindromeDescendant(getDescendantOfNumber(number));
    }

    private Task5() {

    }

    private static String getDescendantOfNumber(String number) {
        StringBuilder descendant = new StringBuilder();
        for (int i = 0; i < number.length() - 1; i += 2) {
            descendant.append(
                Character.getNumericValue(number.charAt(i))
                    + Character.getNumericValue(number.charAt(i + 1)));
        }
        return descendant.toString();
    }

}
