package edu.hw2.Task4;

import java.util.ArrayList;
import java.util.List;

public class CallingInfoManager {

    private static final int NUMBER_OF_SKIP_METHODS = 1;

    public static List<CallingInfo> callingInfo() {
        List<CallingInfo> callingInfoElements = new ArrayList<>();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = NUMBER_OF_SKIP_METHODS; i < stackTraceElements.length; ++i) {
            StackTraceElement currentElement = stackTraceElements[i];
            String className = removePackageInformationFromClassName(currentElement.getClassName());
            callingInfoElements.add(new CallingInfo(className, currentElement.getMethodName()));
        }
        return callingInfoElements;
    }

    private static String removePackageInformationFromClassName(String className) {
        StringBuilder correctedClassName = new StringBuilder();
        for (int i = className.length() - 1; i >= 0; --i) {
            char currentSymbol = className.charAt(i);
            if (currentSymbol != '.' && currentSymbol != '$') {
                correctedClassName.append(currentSymbol);
            } else {
                break;
            }
        }
        return correctedClassName.reverse().toString();
    }

    private CallingInfoManager() {

    }
}
