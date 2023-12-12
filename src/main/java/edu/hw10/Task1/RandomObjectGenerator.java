package edu.hw10.Task1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public class RandomObjectGenerator {
    private final static Map<Class<?>, Function<Parameter, Object>> BLANKS = new HashMap<>() {{
        put(Integer.class, RandomObjectGenerator::getIntegerValue);
        put(int.class, RandomObjectGenerator::getIntegerValue);
        put(Double.class, (clazz) -> 1.0);
        put(double.class, (clazz) -> 1.0);
        put(Short.class, (clazz) -> 1);
        put(short.class, (clazz) -> 1);
        put(Long.class, (clazz) -> 1);
        put(long.class, (clazz) -> 1);
        put(Boolean.class, (clazz) -> true);
        put(boolean.class, (clazz) -> true);
        put(String.class, (clazz) -> "test");
    }};

    private final static ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

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
            Object[] parameters = initializeParameters(constructor.getParameters());
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object initializeObjectUsingFactoryMethod(Method factoryMethod) {
        Object[] parameters = initializeParameters(factoryMethod.getParameters());
        try {
            return factoryMethod.invoke(null, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] initializeParameters(Parameter[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            Parameter currentParameter = parameterTypes[i];
            if (BLANKS.containsKey(currentParameter.getType())) {
                parameters[i] = BLANKS.get(currentParameter.getType()).apply(currentParameter);
            } else {
                parameters[i] = null;
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

    private static Integer getIntegerValue(Parameter parameter) {
        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE - 1;
        try {
            Min minAnnotation = parameter.getAnnotation(Min.class);
            minValue = minAnnotation.minValue();
        } catch (NullPointerException e) {
        }
        try {
            Max minAnnotation = parameter.getAnnotation(Max.class);
            maxValue = minAnnotation.maxValue();
        } catch (NullPointerException e) {

        }
        return RANDOM.nextInt(minValue, maxValue + 1);
    }
}
