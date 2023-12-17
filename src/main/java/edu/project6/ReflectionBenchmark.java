package edu.project6;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    private final static int MEASUREMENT_TIME = 60;
    private final static String METHOD_NAME = "name";
    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private ReflectionBenchmark.StudentNameGetter lambdaFactory;

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(2)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(2))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(MEASUREMENT_TIME))
            .build();

        new Runner(options).run();
    }

    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");

        method = student.getClass().getMethod(METHOD_NAME);

        MethodHandles.Lookup publicLookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);
        methodHandle = publicLookup.findVirtual(Student.class, METHOD_NAME, methodType);

        lambdaFactory = getLambdaFactory();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHande(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole bh) {
        String name = lambdaFactory.getName(student);
        bh.consume(name);
    }

    private ReflectionBenchmark.StudentNameGetter getLambdaFactory()
        throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        ReflectionBenchmark.StudentNameGetter getter = (StudentNameGetter) LambdaMetafactory.metafactory(
            lookup,
            "getName",
            MethodType.methodType(StudentNameGetter.class),
            MethodType.methodType(String.class, Student.class),
            lookup.findVirtual(Student.class, METHOD_NAME, MethodType.methodType(String.class)),
            MethodType.methodType(String.class, Student.class)
        ).getTarget().invokeExact();

        return getter;
    }

    record Student(String name, String surname) {
    }

    private interface StudentNameGetter {
        String getName(Student student);
    }
}
