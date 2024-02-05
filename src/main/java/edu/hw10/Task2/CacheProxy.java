package edu.hw10.Task2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CacheProxy implements InvocationHandler {
    private final Map<MethodInf, Object> cachedResults;
    private final Path tempFile;
    private final Object target;

    public static <T> T create(Object target, Class<T> classInf) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, new CacheProxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        if (method.isAnnotationPresent(Cache.class)) {
            MethodInf methodInf = new MethodInf(method, args.clone());
            if (method.getAnnotation(Cache.class).persist() && !cachedResults.containsKey(methodInf)) {
                writeCacheToFile(method, args, result);
            }
            cachedResults.putIfAbsent(methodInf, result);
            return cachedResults.get(methodInf);
        }
        return result;
    }

    public Path gerPathToCache() {
        return tempFile;
    }

    private void writeCacheToFile(Method method, Object[] args, Object result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Method_Name", method.getName());
        jsonObject.put("Result", result);
        JSONArray jsonArray = new JSONArray();
        for (Object currentObject : args) {
            jsonArray.add(currentObject);
        }
        jsonObject.put("Args", jsonArray);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile.toString(), true))) {
            bufferedWriter.write(jsonObject.toJSONString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CacheProxy(Object target) {
        this.target = target;
        cachedResults = new HashMap<>();
        try {
            tempFile = Files.createTempFile(target.getClass().getName(), ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record MethodInf(Method method, Object[] args) {

    }
}
