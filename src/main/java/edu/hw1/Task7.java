package edu.hw1;

public class Task7 {

    public static int rotateRight(int n, int shift) {
        checkValues(n, shift);
        String binaryValue = Integer.toBinaryString(n);
        for (int i = 0; i < shift; ++i) {
            binaryValue = shiftRight(binaryValue);
        }
        return Integer.parseInt(binaryValue, 2);
    }

    public static int rotateLeft(int n, int shift) {
        checkValues(n, shift);
        String binaryValue = Integer.toBinaryString(n);
        for (int i = 0; i < shift; ++i) {
            binaryValue = shiftLeft(binaryValue);
        }
        return Integer.parseInt(binaryValue, 2);
    }

    private static String shiftLeft(String binaryValue) {
        char[] newBinaryValue = binaryValue.toCharArray();
        char firstSymbol = newBinaryValue[0];
        for (int i = 0; i < newBinaryValue.length - 1; ++i) {
            newBinaryValue[i] = newBinaryValue[i + 1];
        }
        newBinaryValue[newBinaryValue.length - 1] = firstSymbol;
        return new String(newBinaryValue);
    }

    private static String shiftRight(String binaryValue) {
        char[] newBinaryValue = binaryValue.toCharArray();
        char lastSymbol = newBinaryValue[newBinaryValue.length - 1];
        for (int i = newBinaryValue.length - 1; i >= 1; --i) {
            newBinaryValue[i] = newBinaryValue[i - 1];
        }
        newBinaryValue[0] = lastSymbol;
        return new String(newBinaryValue);
    }

    private static void checkValues(int value, int shift) {
        if (value < 0 || shift < 0) {
            throw new IllegalArgumentException("The number must be greater than or equal to zero");
        }
    }

    private Task7() {

    }
}


