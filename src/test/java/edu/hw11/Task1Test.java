package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    void test() {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
        try {
            assertEquals(dynamicType.newInstance().toString(), "Hello, ByteBuddy!");
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
