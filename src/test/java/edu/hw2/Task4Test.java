package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import edu.hw2.Task4.CallingInfoManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task4Test {

    private static class ClassForTest {
        private static List<CallingInfo> method1() {
            return method2();
        }

        private static List<CallingInfo> method2() {
            return method3();
        }

        private static List<CallingInfo> method3() {
            return CallingInfoManager.callingInfo();
        }

    }

    @Test
    @DisplayName("Проверка работы метода callingInfo")
    void testCallingInfoManager1() {
        List<CallingInfo> expected = new ArrayList<CallingInfo>() {};
        expected.add(new CallingInfo("CallingInfoManager", "callingInfo"));
        expected.add(new CallingInfo("ClassForTest", "method3"));
        expected.add(new CallingInfo("ClassForTest", "method2"));
        expected.add(new CallingInfo("ClassForTest", "method1"));
        List<CallingInfo> actual = ClassForTest.method1();
        actual = actual.stream().limit(4).collect(Collectors.toList());
        Assertions.assertIterableEquals(expected, actual);
    }
}
