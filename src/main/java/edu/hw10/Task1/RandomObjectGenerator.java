package edu.hw10.Task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public class RandomObjectGenerator {
    private final static Map<Class<?>, Supplier<?>> BLANKS = new HashMap<>() {{
        put(Integer.class, () -> 1);
        put(int.class, () -> 1);
        put(Double.class, () -> 1.0);
        put(double.class, () -> 1.0);
        put(Short.class, () -> 1);
        put(short.class, () -> 1);
        put(Long.class, () -> 1);
        put(long.class, () -> 1);
        put(Boolean.class, () -> true);
        put(boolean.class, () -> true);
        put(String.class, () -> "test");
    }};

    public <T> T nextObject(Class<T> classInfo) {
        Constructor<T> constructor = getPublicConstructor(classInfo);
        if (constructor != null) {
            return initializeObjectUsingConstructor(constructor);
        }
        throw new IllegalArgumentException("This class does not have public constructor");
    }

    public Object nextObject(Class<?> classInfo, String regexForFactoryMethodName) {
        Method factoryMethod = getFactoryMethod(classInfo, regexForFactoryMethodName);
        if (factoryMethod != null) {
            return initializeObjectUsingFactoryMethod(factoryMethod);
        }
        throw new IllegalArgumentException("Not found public static factory method in this class");
    }

    private <T> T initializeObjectUsingConstructor(Constructor<T> constructor) {
        try {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
            Object[] parameters = initializeParameters(constructor.getParameterTypes());
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object initializeObjectUsingFactoryMethod(Method factoryMethod) {
        Object[] parameters = initializeParameters(factoryMethod.getParameterTypes());
        try {
            return factoryMethod.invoke(null, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] initializeParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (BLANKS.containsKey(parameterTypes[i])) {
                parameters[i] = BLANKS.get(parameterTypes[i]).get();
            } else {
                parameters[i] = BLANKS.getOrDefault(parameterTypes[i], null);
            }
        }
        return parameters;
    }

    @Nullable
    private Method getFactoryMethod(Class<?> classInfo, String regexForFactoryMethodName) {
        Pattern pattern = Pattern.compile(regexForFactoryMethodName);
        Method factoryMethod = null;
        for (Method method : classInfo.getDeclaredMethods()) {
            int methodModifiers = method.getModifiers();
            if (pattern.matcher(method.getName()).find() && !method.getReturnType().equals(void.class)
                && Modifier.isPublic(methodModifiers) && Modifier.isStatic(methodModifiers)) {
                factoryMethod = method;
                break;
            }
        }
        return factoryMethod;
    }

    @Nullable
    private <T> Constructor<T> getPublicConstructor(Class<T> classInfo) {
        Constructor<T>[] constructors = (Constructor<T>[]) classInfo.getConstructors();
        Constructor<T> resultConstructor = null;
        int minNumberOfParameters = Integer.MAX_VALUE;
        for (Constructor<T> currentConstructor : constructors) {
            if (currentConstructor.getParameterCount() < minNumberOfParameters
                && Modifier.isPublic(currentConstructor.getModifiers())) {
                minNumberOfParameters = currentConstructor.getParameterCount();
                resultConstructor = currentConstructor;
            }
        }
        return resultConstructor;
    }
}
