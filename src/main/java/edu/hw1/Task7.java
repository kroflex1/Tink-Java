package edu.hw1;


public class Task7 {

    public static int rotateRight(int n, int shift) {
        checkValues(n, shift);
        return rotate(n, shift);

    }

    public static int rotateLeft(int n, int shift) {
        checkValues(n, shift);
        return rotate(n, -shift);
    }

    private static int rotate(int value, int shift) {
        char[] binaryValue = Integer.toBinaryString(value).toCharArray();
        char[] binaryValueCopy = binaryValue.clone();
        if (shift % binaryValue.length == 0) {
            return value;
        }
        int newPositionForSymbol;
        for (int i = 0; i < binaryValue.length; ++i) {
            newPositionForSymbol = (i + shift) % binaryValue.length;
            newPositionForSymbol =
                newPositionForSymbol < 0 ? binaryValue.length + newPositionForSymbol : newPositionForSymbol;
            binaryValue[newPositionForSymbol] = binaryValueCopy[i];
        }
        return Integer.parseInt(new String(binaryValue), 2);
    }

    private static void checkValues(int value, int shift) {
        if (value < 0 || shift < 0) {
            throw new IllegalArgumentException("The number must be greater than or equal to zero");
        }
    }

    private Task7() {

    }
}


